package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.dto.DadosPessoaisDTO;
import com.dbc.pessoaapi.service.DadosPessoaisService;
import feign.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados-pessoais")
@RequiredArgsConstructor
public class DadosPessoaisController {

    private final DadosPessoaisService dadosPessoaisService;

    @GetMapping
    public List<DadosPessoaisDTO> listar(){
        return dadosPessoaisService.listar();
    }

    @GetMapping("/{cpf}")
    public DadosPessoaisDTO getPorCpf(@Param("cpf") String cpf){
        return dadosPessoaisService.getPorCpf(cpf);
    }

    @PostMapping
    public DadosPessoaisDTO create(@RequestBody DadosPessoaisDTO dadosPessoaisDTO){
        return dadosPessoaisService.create(dadosPessoaisDTO);
    }
}
