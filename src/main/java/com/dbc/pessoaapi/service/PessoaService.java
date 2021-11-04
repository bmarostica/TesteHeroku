package com.dbc.pessoaapi.service;

import com.dbc.pessoaapi.client.DadosPessoaisClient;
import com.dbc.pessoaapi.dto.DadosPessoaisDTO;
import com.dbc.pessoaapi.dto.PessoaCreateDTO;
import com.dbc.pessoaapi.dto.PessoaDTO;
import com.dbc.pessoaapi.entity.PessoaEntity;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import com.dbc.pessoaapi.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final DadosPessoaisClient dadosPessoaisClient;

    public PessoaDTO create(PessoaCreateDTO pessoaCreateDTO) throws Exception {
        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity pessoaCriada = pessoaRepository.create(pessoaEntity);
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaCriada, PessoaDTO.class);

        //emailService.envioComTemplateAoCriar(pessoaDTO);

        return pessoaDTO;
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.list().stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }

    public PessoaDTO update(Integer id,
                            PessoaCreateDTO pessoaCreateDTO) throws Exception {
        PessoaEntity entity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity atualizado = pessoaRepository.update(id, entity);

        PessoaDTO pessoaDTO = objectMapper.convertValue(atualizado, PessoaDTO.class);

        //emailService.envioComTemplateAoAtualizar(pessoaDTO);

        return pessoaDTO;
    }

    public void delete(Integer id) throws Exception {
        PessoaEntity pessoaEntity = pessoaRepository.buscarPorId(id);

        PessoaEntity entity = objectMapper.convertValue(pessoaEntity, PessoaEntity.class);
        PessoaDTO pessoaDTO = objectMapper.convertValue(entity, PessoaDTO.class);

        pessoaRepository.delete(id);

        //emailService.envioComTemplateAoDeletar(pessoaDTO);
    }

    public List<PessoaDTO> listByName(String nome) {
        return pessoaRepository.listByName(nome).stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, PessoaDTO.class))
                .collect(Collectors.toList());
    }


    public PessoaDTO buscarPorId(Integer id) throws RegraDeNegocioException {
        PessoaEntity pessoaEntity = pessoaRepository.buscarPorId(id);

        DadosPessoaisDTO dadosPessoaisDTO = dadosPessoaisClient.getPorCpf(pessoaEntity.getCpf());
        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);

        pessoaDTO.setDadosPessoaisDTO(dadosPessoaisDTO);

        return pessoaDTO;
    }
}
