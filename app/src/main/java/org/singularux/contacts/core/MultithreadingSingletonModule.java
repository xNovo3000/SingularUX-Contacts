package org.singularux.contacts.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

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
    @IOExecutorService
    public ExecutorService ioExecutorService() {
        return Executors.newCachedThreadPool(new BackgroundThreadFactory("IO"));
    }

}
