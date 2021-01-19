package com.renxy.rpc.serialize;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用序列化接口
 *
 * @author renxiaoya
 * @date 2021-01-18
 **/
public class SerializeSupport {

    /**
     * 序列化实现map
     * key：序列化类型
     * value：序列化实现
     */
    private static Map<Class<?>, Serializer<?>> serializerMap = new HashMap<>();

    /**
     * 序列化类型map
     * key：序列化实现类型值
     * value：序列化对象类型
     */
    private static Map<Byte, Class<?>> typeMap = new HashMap<>();

    /**
     * 序列化
     *
     * @param buffer 二进制流
     * @param <E>    序列化类型
     * @return
     */
    public static <E> E parse(byte[] buffer) {
        return null;
    }

    public static <E> byte[] serialize(E entry) {
        return null;
    }

}
