package com.github.fabrluc.practicespring.grpc;

import com.exemplo.grpc.SaudacaoRequest;
import com.exemplo.grpc.SaudacaoResponse;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class SaudacaoService extends com.exemplo.grpc.SaudacaoServiceGrpc.SaudacaoServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(SaudacaoService.class);

    @Override
    public void saudar(SaudacaoRequest request, StreamObserver<SaudacaoResponse> responseObserver) {
        log.info("Recebida requisição de saudação para: {}", request.getNome());

        // Cria a resposta
        SaudacaoResponse response = SaudacaoResponse.newBuilder()
                .setMensagem("Olá, " + request.getNome() + "! Bem-vindo ao Spring gRPC!")
                .setTimestamp(Instant.now().toEpochMilli())
                .build();

        // Envia a resposta
        responseObserver.onNext(response);

        // Completa a chamada
        responseObserver.onCompleted();

        log.info("Resposta enviada para: {}", request.getNome());
    }

    @Override
    public void saudarMultiplo(SaudacaoRequest request, StreamObserver<SaudacaoResponse> responseObserver) {
        log.info("Recebida requisição de saudação múltipla para: {}", request.getNome());

        // Envia 5 saudações diferentes
        String[] saudacoes = {
                "Olá", "Bom dia", "Seja bem-vindo", "Prazer em conhecê-lo", "Até logo"
        };

        for (int i = 0; i < saudacoes.length; i++) {
            SaudacaoResponse response = SaudacaoResponse.newBuilder()
                    .setMensagem(saudacoes[i] + ", " + request.getNome() + "!")
                    .setTimestamp(Instant.now().toEpochMilli())
                    .build();

            // Envia cada resposta
            responseObserver.onNext(response);

            log.info("Enviada saudação {}/{} para: {}", i + 1, saudacoes.length, request.getNome());

            // Pequena pausa para simular processamento
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                responseObserver.onError(e);
                return;
            }
        }

        // Completa o streaming
        responseObserver.onCompleted();
        log.info("Streaming de saudações concluído para: {}", request.getNome());
    }
}
