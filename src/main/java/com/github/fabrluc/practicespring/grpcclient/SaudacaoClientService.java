package com.github.fabrluc.practicespring.grpcclient;

import com.github.fabrluc.practicespring.dto.SaudacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaudacaoClientService {

    private final com.exemplo.grpc.SaudacaoServiceGrpc.SaudacaoServiceBlockingStub sub;

    public SaudacaoDto sendSaudacao(final String message) {
        var response =  sub.saudar(com.exemplo.grpc.SaudacaoRequest.newBuilder()
                        .setNome(message)
                .build());


        return SaudacaoDto.builder()
                .message(response.getMensagem())
                .build();
    }

}
