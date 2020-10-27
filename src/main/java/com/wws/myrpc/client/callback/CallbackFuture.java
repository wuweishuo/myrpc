package com.wws.myrpc.client.callback;

import java.util.concurrent.*;

public class CallbackFuture<T> implements Future<T>, Callback<T> {

    private T result;

    private Throwable throwable;

    private final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null || throwable != null;
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        if(throwable != null){
            throw new ExecutionException(throwable);
        }
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        latch.await(timeout, unit);
        if(throwable != null){
            throw new ExecutionException(throwable);
        }
        return result;
    }

    @Override
    public void setResult(Object result) {
        this.result = (T) result;
        latch.countDown();
    }

    @Override
    public void setError(Throwable throwable) {
        this.throwable = throwable;
        latch.countDown();
    }
}
