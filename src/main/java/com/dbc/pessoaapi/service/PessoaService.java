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
import org.apache.commons.lang3.StringUtils;
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
        dadosPessoaisClient.create(pessoaCreateDTO.getDadosPessoaisDTO());
        PessoaEntity pessoaEntity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity pessoaCriada = pessoaRepository.create(pessoaEntity);

        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaCriada, PessoaDTO.class);
        pessoaDTO.setDadosPessoaisDTO(dadosPessoaisClient.getPorCpf(pessoaDTO.getCpf()));

        //emailService.envioComTemplateAoCriar(pessoaDTO);

        return pessoaDTO;
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.list().stream()
                .map(pessoa -> {
                    PessoaDTO pessoaDTO = objectMapper.convertValue(pessoa, PessoaDTO.class);
                    pessoaDTO.setDadosPessoaisDTO(dadosPessoaisClient.getPorCpf(pessoa.getCpf()));
                    return pessoaDTO;
                })
                .collect(Collectors.toList());
    }

    public PessoaDTO update(Integer id,
                            PessoaCreateDTO pessoaCreateDTO) throws Exception {
        dadosPessoaisClient.update(pessoaCreateDTO.getCpf(), pessoaCreateDTO.getDadosPessoaisDTO());
        PessoaEntity entity = objectMapper.convertValue(pessoaCreateDTO, PessoaEntity.class);
        PessoaEntity pessoaEntity = pessoaRepository.update(id, entity);

        PessoaDTO pessoaDTO = objectMapper.convertValue(pessoaEntity, PessoaDTO.class);
        pessoaDTO.setDadosPessoaisDTO(dadosPessoaisClient.getPorCpf(pessoaEntity.getCpf()));

        //emailService.envioComTemplateAoAtualizar(pessoaDTO);

        return pessoaDTO;
    }

    public void delete(Integer id) throws Exception {
        PessoaEntity pessoaEntity = pessoaRepository.buscarPorId(id);

        dadosPessoaisClient.delete(pessoaEntity.getCpf());
        pessoaRepository.delete(id);

        //emailService.envioComTemplateAoDeletar(pessoaDTO);
    }

    public List<PessoaDTO> listByName(String nome) {
        return pessoaRepository.listByName(nome).stream()
                .filter(pessoa -> StringUtils.containsIgnoreCase(pessoa.getNome(), nome))
                .map(pessoa -> {
                    PessoaDTO pessoaDTO = objectMapper.convertValue(pessoa, PessoaDTO.class);
                    pessoaDTO.setDadosPessoaisDTO(dadosPessoaisClient.getPorCpf(pessoa.getCpf()));
                    return pessoaDTO;
                })
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
