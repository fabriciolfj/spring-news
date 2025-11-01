package com.github.fabrluc.practicespring.grpcclient;

import com.github.fabrluc.practicespring.dto.SaudacaoDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaudacaoClientService {

    private final com.exemplo.grpc.SaudacaoServiceGrpc.SaudacaoServiceBlockingStub sub;
    private static final Logger log = LoggerFactory.getLogger(SaudacaoClientService.class);

    public SaudacaoDto sendSaudacao(final String message) {
        var response =  sub.saudar(com.exemplo.grpc.SaudacaoRequest.newBuilder()
                        .setNome(message)
                .build());


        return SaudacaoDto.builder()
                .message(response.getMensagem())
                .build();
    }

    public void sendVariasSaudacoes(final String message) {
        var result = sub.saudarMultiplo(com.exemplo.grpc.SaudacaoRequest.newBuilder()
                .setNome(message)
                .build());


        while (result.hasNext()) {
            log.info("receive {}", result.next());
        }
    }

}
