package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.client.DadosPessoaisClient;
import com.dbc.pessoaapi.dto.DadosPessoaisDTO;
import feign.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DadosPessoaisService {

    private final DadosPessoaisClient dadosPessoaisClient;

    public List<DadosPessoaisDTO> listar(){
        return dadosPessoaisClient.listar();
    }

    public DadosPessoaisDTO getPorCpf(String cpf){
        return dadosPessoaisClient.getPorCpf(cpf);
    }

    public DadosPessoaisDTO create(DadosPessoaisDTO dadosPessoaisDTO){
        return dadosPessoaisClient.create(dadosPessoaisDTO);
    }

    public DadosPessoaisDTO update(String cpf, DadosPessoaisDTO dadosPessoaisDTO){
        return dadosPessoaisClient.update(cpf, dadosPessoaisDTO);
    }

    public void delete(String cpf){
        dadosPessoaisClient.delete(cpf);
    }

}
