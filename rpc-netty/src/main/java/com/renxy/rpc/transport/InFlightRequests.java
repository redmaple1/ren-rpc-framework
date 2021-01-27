package com.renxy.rpc.transport;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author renxiaoya
 * @date 2021-01-27
 **/
public class InFlightRequests implements Closeable {

    private static final long TIMEOUT_SEC = 10L;

    private final Semaphore semaphore = new Semaphore(10);

    private final Map<Integer, ResponseFuture> futureMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private final ScheduledFuture scheduledFuture;

    public InFlightRequests(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this::removeTimeoutFutures, TIMEOUT_SEC, TIMEOUT_SEC, TimeUnit.SECONDS);
    }

    public void put(ResponseFuture responseFuture) throws InterruptedException, TimeoutException {
        if (semaphore.tryAcquire(TIMEOUT_SEC, TimeUnit.SECONDS)) {
            futureMap.put(responseFuture.getRequestId(), responseFuture);
        } else {
            throw new TimeoutException();
        }
    }

    public ResponseFuture remove(int requestId) {
        ResponseFuture future = futureMap.remove(requestId);
        if (null != future) {
            semaphore.release();
        }
        return future;
    }

    private void removeTimeoutFutures() {
        futureMap.entrySet().removeIf(entry -> {
            if (System.nanoTime() - entry.getValue().getTimestamp() > TIMEOUT_SEC * 1000000000L) {
                semaphore.release();
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void close() throws IOException {
        scheduledFuture.cancel(true);
        scheduledExecutorService.shutdown();
    }
}
