package me.example.helloworld.controller;

import me.example.helloworld.GreeterGrpc;
import me.example.helloworld.HelloReply;
import me.example.helloworld.HelloRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GreeterController {
    private final GreeterGrpc.GreeterBlockingStub stub;

    public GreeterController(GreeterGrpc.GreeterBlockingStub stub) {
        this.stub = stub;
    }

    @GetMapping("/api/hello")
    public Map<String, String> sayHello(@RequestParam String name) {
        HelloReply reply = stub.sayHello(HelloRequest.newBuilder().setName(name).build());
        return Map.of("message", reply.getMessage());
    }
}
