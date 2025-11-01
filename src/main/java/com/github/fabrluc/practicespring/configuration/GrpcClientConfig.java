package com.github.fabrluc.practicespring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    public com.exemplo.grpc.SaudacaoServiceGrpc.SaudacaoServiceBlockingStub saudacaoStub(
            GrpcChannelFactory channelFactory) {
        return com.exemplo.grpc.SaudacaoServiceGrpc.newBlockingStub(
                channelFactory.createChannel("local")
        );
    }
}
