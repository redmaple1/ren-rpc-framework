package com.renxy.rpc.serialize.impl;

import com.renxy.rpc.nameservice.Metadata;
import com.renxy.rpc.serialize.Serializer;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 元数据序列化器
 * 每部分按照 size + 实际长度 进行序列化，以size开头可以清晰地为实际序列化申请对应的空间
 * Size of the map                      2 bytes
 *      Map entry:
 *          Key string:
 *              Length:                 2 bytes
 *              Serialized key bytes    variable length
 *          Value list:
 *              List size:              2 bytes
 *              item(URI):
 *                  Length:             2 bytes
 *                  serialized uri      variable length
 *              item(URI):
 *              ...
 *      Map entry:
 *      ...
 *
 * @author renxiaoya
 * @date 2021-01-22
 **/
public class MetadataSerializer implements Serializer<Metadata> {

    @Override
    public int size(Metadata entry) {
        return Short.BYTES +        // Size of the map:     2 bytes
                entry.entrySet().stream().mapToInt(this::entrySize).sum();
    }

    @Override
    public void serialize(Metadata entry, byte[] bytes, int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        buffer.putShort(toShortSafely(entry.size()));

        entry.forEach((k, v) -> {
            byte[] keyBytes = k.getBytes(StandardCharsets.UTF_8);
            buffer.putShort(toShortSafely(keyBytes.length));
            buffer.put(keyBytes);

            buffer.putShort(toShortSafely(v.size()));
            for (URI uri : v) {
                byte[] uriBytes = uri.toASCIIString().getBytes(StandardCharsets.UTF_8);
                buffer.putShort(toShortSafely(uriBytes.length));
                buffer.put(uriBytes);
            }
        });
    }

    @Override
    public Metadata parse(byte[] bytes, int offset, int length) {
        Metadata metadata = new Metadata();

        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        short sizeOfMap = buffer.getShort();
        for (int i = 0; i < sizeOfMap; i++) {
            short keyLength = buffer.getShort();
            byte[] keyBytes = new byte[keyLength];
            buffer.get(keyBytes);
            String key = new String(keyBytes, StandardCharsets.UTF_8);

            short uriListSize = buffer.getShort();
            List<URI> uriList = new ArrayList<>(uriListSize);
            for (int j = 0; j < uriListSize; j++) {
                short uriLength = buffer.getShort();
                byte[] uriBytes = new byte[uriLength];
                buffer.get(uriBytes);
                URI uri = URI.create(new String(uriBytes, StandardCharsets.UTF_8));
                uriList.add(uri);
            }

            metadata.put(key, uriList);
        }
        return metadata;
    }

    @Override
    public byte type() {
        return Types.TYPE_METADATA;
    }

    @Override
    public Class<Metadata> getSerializeClass() {
        return Metadata.class;
    }

    private int entrySize(Map.Entry<String, List<URI>> e) {
        // Map entry:
        return Short.BYTES +    // Key string length:   2 bytes
                e.getKey().getBytes(StandardCharsets.UTF_8).length +    // Serialized key bytes:    variable length
                Short.BYTES +   // List size:       2 bytes
                e.getValue().stream()   // Value list
                        .mapToInt(uri -> {
                            return Short.BYTES +    // Key string length:       2 bytes
                                    uri.toASCIIString().getBytes(StandardCharsets.UTF_8).length;    // Serialized uri bytes:    variable length
                        }).sum();
    }

    private short toShortSafely(int v) {
        assert v < Short.MAX_VALUE;
        return (short) v;
    }
}
