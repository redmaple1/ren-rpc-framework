package com.renxy.rpc.serialize;

/**
 * 通用序列化接口
 * @author renxiaoya
 * @date 2021-01-18
 **/
public class SerializeSupport {

    /**
     * 序列化
     * @param buffer 二进制流
     * @param <E> 序列化类型
     * @return
     */
    public static <E> E parse(byte[] buffer){
        return null;
    }

    public static <E> byte[] serialize(E entry){
        return null;
    }

}
