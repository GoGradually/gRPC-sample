package me.example.helloworld.service;

import io.grpc.stub.StreamObserver;
import me.example.helloworld.GreeterGrpc;
import me.example.helloworld.HelloReply;
import me.example.helloworld.HelloRequest;
import org.springframework.stereotype.Service;

@Service
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String message = "Hello, " + request.getName();

        HelloReply reply = HelloReply.newBuilder().setMessage(message).build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
