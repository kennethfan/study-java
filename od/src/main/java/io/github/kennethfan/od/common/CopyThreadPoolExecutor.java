package io.github.kennethfan.od.common;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CopyThreadPoolExecutor implements ExecutorService {

    private ExecutorService delegate;

    public CopyThreadPoolExecutor(ExecutorService delegate) {
        this.delegate = delegate;
    }

    @Override
    public void shutdown() {
        this.delegate.shutdown();
        ;
    }

    @Override
    public List<Runnable> shutdownNow() {
        return this.delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.delegate.awaitTermination(timeout, unit);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.delegate.submit(WrapperCallable.of(task));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return this.delegate.submit(WrapperRunnable.of(task), result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return this.delegate.submit(WrapperRunnable.of(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.delegate.invokeAll(tasks.stream().map(task -> WrapperCallable.of(task)).collect(Collectors.toList()));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return this.delegate.invokeAll(tasks.stream().map(task -> WrapperCallable.of(task)).collect(Collectors.toList()), timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.delegate.invokeAny(tasks.stream().map(task -> WrapperCallable.of(task)).collect(Collectors.toList()));
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate.invokeAny(tasks.stream().map(task -> WrapperCallable.of(task)).collect(Collectors.toList()), timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        this.delegate.execute(WrapperRunnable.of(command));
    }


    static class WrapperRunnable implements Runnable {

        private Runnable task;

        private WrapperRunnable(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            try {
                // 获取当前线程所有threadlocals
                this.task.run();
            } finally {

            }
        }

        public static WrapperRunnable of(Runnable task) {
            return new WrapperRunnable(task);
        }
    }

    static class WrapperCallable<V> implements Callable<V> {

        private Callable<V> task;

        private WrapperCallable(Callable<V> task) {
            this.task = task;
        }

        @Override
        public V call() throws Exception {
            try {
                return this.task.call();
            } finally {

            }
        }

        public static <V> WrapperCallable<V> of(Callable<V> task) {
            return new WrapperCallable<>(task);
        }
    }





}
