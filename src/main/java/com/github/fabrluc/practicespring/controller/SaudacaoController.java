package com.github.fabrluc.practicespring.controller;

import com.github.fabrluc.practicespring.dto.SaudacaoDto;
import com.github.fabrluc.practicespring.grpcclient.SaudacaoClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/saudacao")
@RequiredArgsConstructor
public class SaudacaoController {

    private final SaudacaoClientService saudacaoClientService;

    @GetMapping("/{name}")
    public SaudacaoDto createMessage(@PathVariable("name") final String name) {
        return saudacaoClientService.sendSaudacao(name);
    }


    @GetMapping("/varias/{name}")
    public void createVariasMessage(@PathVariable("name") final String name) {
        saudacaoClientService.sendVariasSaudacoes(name);
    }
}
