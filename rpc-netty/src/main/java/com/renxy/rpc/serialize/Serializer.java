package com.renxy.rpc.serialize;

/**
 * 序列化接口
 *
 * @author renxiaoya
 * @date 2021-01-19
 **/
public interface Serializer<T> {

    /**
     * 对象序列化之后的长度
     *
     * @param entry 需要序列化的对象
     * @return 长度
     */
    int size(T entry);

    /**
     * 将指定对象序列化成字节数组
     *
     * @param entry  待序列化的对象
     * @param bytes  存放序列化数据的字节数组
     * @param offset 数组的偏移量，从这个位置开始写入序列化数据
     * @param length 对象序列化后的长度，即{@link Serializer#size(java.lang.Object)}返回值
     */
    void serialize(T entry, byte[] bytes, int offset, int length);

    /**
     * 反序列化
     *
     * @param bytes  存放序列化数据的字节数组
     * @param offset 数组的偏移量，从这个位置开始写入序列化数据
     * @param length 对象序列化后的长度
     * @return 反序列化的对象
     */
    T parse(byte[] bytes, int offset, int length);

    /**
     * 一个字节，标识对象类型，每种类型的数据有不同的类型值
     *
     * @return 类型值
     */
    byte type();

    /**
     * 返回序列化对象的Class对象
     *
     * @return Class对象
     */
    Class<T> getSerializeClass();

}
