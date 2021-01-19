package com.renxy.rpc.serialize.impl;

import com.renxy.rpc.serialize.Serializer;

import java.nio.charset.StandardCharsets;

/**
 * 字符串序列化实现
 *
 * @author renxiaoya
 * @date 2021-01-19
 **/
public class StringSerializer implements Serializer<String> {
    @Override
    public int size(String entry) {
        return entry.getBytes(StandardCharsets.UTF_8).length;
    }

    @Override
    public void serialize(String entry, byte[] bytes, int offset, int length) {

    }

    @Override
    public String parse(byte[] bytes, int offset, int length) {
        return null;
    }

    @Override
    public byte type() {
        return 0;
    }

    @Override
    public Class<String> getSerializeClass() {
        return null;
    }
}
