package com.renxy.rpc.transport;

import com.renxy.rpc.transport.command.Command;

import java.util.concurrent.CompletableFuture;

/**
 * @author renxiaoya
 * @date 2021-01-27
 **/
public class ResponseFuture {

    private final int requestId;

    private final CompletableFuture<Command> future;

    /**
     * 单位：ns
     */
    private final long timestamp;

    public ResponseFuture(int requestId, CompletableFuture<Command> future) {
        this.requestId = requestId;
        this.future = future;
        this.timestamp = System.nanoTime();
    }

    public int getRequestId() {
        return requestId;
    }

    public CompletableFuture<Command> getFuture() {
        return future;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
