package org.singularux.contacts.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Module
@InstallIn(SingletonComponent.class)
public class MultithreadingSingletonModule {

    @Provides
    @Singleton
    @BackgroundExecutorService
    public ExecutorService backgroundExecutorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
                new BackgroundThreadFactory("Background"));
    }

    @Provides
    @Singleton
    @BackgroundScheduler
    public Scheduler backgroundScheduler(
            @BackgroundExecutorService ExecutorService executorService) {
        return Schedulers.from(executorService, true, true);
    }

    @Provides
    @Singleton
    @IOExecutorService
    public ExecutorService ioExecutorService() {
        return new ThreadPoolExecutor(0,
                Runtime.getRuntime().availableProcessors() * 4,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new BackgroundThreadFactory("IO"));
    }

    @Provides
    @Singleton
    @IOScheduler
    public Scheduler ioScheduler(@IOExecutorService ExecutorService executorService) {
        return Schedulers.from(executorService, true, true);
    }

}
