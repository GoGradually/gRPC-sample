package me.example.helloworld.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.example.helloworld.GreeterGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Bean(destroyMethod = "shutdown")
    public ManagedChannel managedChannel(
            @Value("${grpc.client.host:localhost}") String host,
            @Value("${grpc.client.port:50051}") int port
    ) {
        return ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    @Bean
    public GreeterGrpc.GreeterBlockingStub greeterBlockingStub(ManagedChannel channel) {
        return GreeterGrpc.newBlockingStub(channel);
    }
}
