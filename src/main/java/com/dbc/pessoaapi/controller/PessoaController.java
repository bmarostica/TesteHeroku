package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.dto.PessoaCreateDTO;
import com.dbc.pessoaapi.dto.PessoaDTO;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import com.dbc.pessoaapi.service.PessoaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
@Slf4j
public class PessoaController {

    private final PessoaService pessoaService;

//    @GetMapping("/hello")
//    public String hello() {
//        return "Hello world!";
//    }

    @ApiOperation(value = "Cria uma pessoa nova")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa criada"),
            @ApiResponse(code = 400, message = "Erro, informação inserida inválida"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @PostMapping
    public PessoaDTO create(@RequestBody @Valid PessoaCreateDTO pessoaCreateDTO) throws Exception {
        log.info("Criando pessoa...");
        PessoaDTO people = pessoaService.create(pessoaCreateDTO);
        log.info("Pessoa criada com sucesso!");
        return people;
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de pessoas retornada com sucesso"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @ApiOperation(value = "Retorna uma lista de pessoas")
    @GetMapping
    public List<PessoaDTO> list() {
        return pessoaService.list();
    }



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de pessoas retornada com sucesso"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @ApiOperation(value = "Retorna uma lista de pessoas pelo nome procurado")
    @GetMapping("/byname")
    public List<PessoaDTO> listByName(@RequestParam("nome") String nome) {
        return pessoaService.listByName(nome);
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro, informação inserida inválida"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @ApiOperation(value = "Atualiza uma pessoa pelo ID informado")
    @PutMapping("/{idPessoa}")
    public PessoaDTO update(@PathVariable("idPessoa") Integer id,
                            @RequestBody @Valid PessoaDTO pessoaAtualizar) throws Exception {
        log.info("Alterando pessoa...");
        PessoaDTO people = pessoaService.update(id, pessoaAtualizar);
        log.info("Pessoa alterada com sucesso!");
        return people;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa Deletada com sucesso"),
            @ApiResponse(code = 400, message = "Erro, ID informado inválido"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @ApiOperation(value = "Deleta uma pessoa pelo ID informado")
    @DeleteMapping("/{idPessoa}")
    public void delete(@PathVariable("idPessoa") Integer id) throws Exception {
        log.info("Deletando pessoa...");
        pessoaService.delete(id);
        log.info("Pessoa deletada com sucesso!");
    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Pessoa localizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro, ID informado inválido"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @ApiOperation("Busca pessoa pelo ID informado")
    @GetMapping("/{id}")
    public PessoaDTO buscarPorId(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        log.info("Buscando pessoa...");
        PessoaDTO pessoaDTO = pessoaService.buscarPorId(id);
        log.info("Busca realizada com sucesso! ");

        return pessoaDTO;
    }
}
