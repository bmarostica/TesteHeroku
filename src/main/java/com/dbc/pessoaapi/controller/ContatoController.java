package com.dbc.pessoaapi.controller;

import com.dbc.pessoaapi.dto.ContatoCreateDTO;
import com.dbc.pessoaapi.dto.ContatoDTO;
import com.dbc.pessoaapi.entity.ContatoEntity;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import com.dbc.pessoaapi.service.ContatoService;
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
@RequestMapping("/contato")
@RequiredArgsConstructor
@Slf4j
public class ContatoController {

    private final ContatoService contatoService;


    @ApiOperation(value = "Cria um novo contato")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Contato criado"),
            @ApiResponse(code = 400, message = "Erro, informação inserida inválida"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @PostMapping("/{idPessoa}")
    public ContatoDTO create(@PathVariable("idPessoa") Integer idPessoa,
                             @RequestBody @Valid ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException {
        log.info("Criando contato...");
        ContatoDTO contats = contatoService.create(idPessoa, contatoCreateDTO);
        log.info("Contato criado com sucesso!");
        return contats;
    }

    @ApiOperation(value = "Atualiza o contato pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Contato atualizado"),
            @ApiResponse(code = 400, message = "Erro, informação inserida inválida"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @PutMapping("/{idContato}")
    public ContatoDTO update(@PathVariable("idContato") Integer id,
                                @RequestBody @Valid ContatoCreateDTO contatoCreateDTO) throws RegraDeNegocioException {
        log.info("Alterando contato...");
        ContatoDTO contats = contatoService.update(id, contatoCreateDTO);
        log.info("Contato alterado com sucesso!");
        return contats;
    }
    @ApiOperation(value = "Lista os contatos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de contatos retornado com sucesso"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @GetMapping
    public List<ContatoDTO> list(){
        return contatoService.list();
    }


    @ApiOperation(value = "Lista os contatos pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de contatos retornado com sucesso"),
            @ApiResponse(code = 400, message = "Erro, ID informado inválido"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @GetMapping("/byIdPessoa")
    public List<ContatoDTO> listByIdPessoa(@RequestParam("idPessoa") Integer idPessoa) throws RegraDeNegocioException {
        return contatoService.listByIdPessoa(idPessoa);
    }


    @ApiOperation(value = "Deleta um contato pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Contato atualizado"),
            @ApiResponse(code = 400, message = "Erro, ID informado inválido"),
            @ApiResponse(code = 500, message = "Exceção gerada.")
    })
    @DeleteMapping("/{idContato}")
    public void delete(@PathVariable("idContato") @Valid Integer id) throws RegraDeNegocioException {
        log.info("Deletando contato...");
        contatoService.delete(id);
        log.info("Contato deletedo com sucesso!");
    }
}
