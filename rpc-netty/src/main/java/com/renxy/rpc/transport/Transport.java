package com.renxy.rpc.transport;

import com.renxy.rpc.transport.command.Command;

import java.util.concurrent.CompletableFuture;

/**
 * @author renxiaoya
 * @date 2021-01-25
 **/
public interface Transport {

    /**
     * 发送请求命令
     *
     * @param request 请求命令
     * @return 返回值是一个Future
     */
    CompletableFuture<Command> send(Command request);

}
