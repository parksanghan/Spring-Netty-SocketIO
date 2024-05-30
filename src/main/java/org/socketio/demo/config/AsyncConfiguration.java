package org.socketio.demo.config;

import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        final var executor  = new ThreadPoolTaskExecutorBuilder().corePoolSize(10).maxPoolSize(20)
                .threadNamePrefix("CustomTP-").build();
        executor.initialize();
        return executor;

    }
}
