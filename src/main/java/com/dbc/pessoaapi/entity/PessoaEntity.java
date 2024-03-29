package com.dbc.pessoaapi.entity;


import com.dbc.pessoaapi.dto.DadosPessoaisDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaEntity {
    private Integer idPessoa;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String email;
}