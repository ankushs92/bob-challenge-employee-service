package com.takeaway.challenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class CustomAsyncConfig implements AsyncConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(CustomAsyncConfig.class);

    @Bean
    @Override
    public Executor getAsyncExecutor() {
        var cores = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(cores / 2);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> logger.error("Method {}. Ex : ", method, ex);
    }
}