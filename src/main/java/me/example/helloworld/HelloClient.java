package me.example.helloworld;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;

public class HelloClient {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        System.out.println("Will try to greet " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;

        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return;
        }
        System.out.println("Greeting: " + response.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        String user = "world";
        if (args.length > 0) {
            user = args[0];
        }

        HelloClient client = new HelloClient("localhost", 50051);
        try {
            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}
