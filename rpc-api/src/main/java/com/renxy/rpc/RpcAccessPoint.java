package com.renxy.rpc;

import java.io.Closeable;
import java.net.URI;

/**
 * RPC对外提供的服务接口
 *
 * @author renxiaoya
 * @date 2021-01-18
 **/
public interface RpcAccessPoint extends Cloneable {

    /**
     * 客户端获取远程服务的引用
     *
     * @param uri          远程服务地址
     * @param serviceClass 服务接口类的class
     * @param <T>          服务接口的类型
     * @return 远程服务引用
     */
    <T> T getRemoteService(URI uri, Class<T> serviceClass);

    /**
     * 服务端注册服务的实现
     *
     * @param service      服务实现示例
     * @param serviceClass 服务接口类的class
     * @param <T>          服务接口的类型
     * @return 服务的地址
     */
    <T> URI addServiceProvider(T service, Class<T> serviceClass);

    /**
     * 服务端启动RPC框架，开始提供远程服务
     *
     * @return 服务实例，用于应用停止时安全关闭服务
     * @throws Exception 异常
     */
    Closeable startServer() throws Exception;

}
