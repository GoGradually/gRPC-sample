package me.example.helloworld.compnent;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import me.example.helloworld.service.GreeterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GrpcServerLifecycle implements SmartLifecycle {
    private final Server server;
    private volatile boolean running = false;

    public GrpcServerLifecycle(GreeterService greeterService, @Value("${grpc.port:50051}") int port) {
        this.server = NettyServerBuilder.forPort(port)
                .addService(greeterService)
                .build();
    }
    @Override
    public void start() {
        try {
            server.start();
            running = true;
        } catch (IOException e){
            throw new IllegalStateException("Failed to start grpc server", e);
        }
    }

    @Override
    public void stop() {
        server.shutdown();
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
