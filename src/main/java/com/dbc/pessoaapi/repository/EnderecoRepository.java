package com.dbc.pessoaapi.repository;

import com.dbc.pessoaapi.entity.EnderecoEntity;
import com.dbc.pessoaapi.entity.TipoEndereco;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class EnderecoRepository {
    private static List<EnderecoEntity> enderecoEntities = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();
    private AtomicInteger COUNTER2 = new AtomicInteger();

    public EnderecoRepository() {
        enderecoEntities.add(new EnderecoEntity(COUNTER.incrementAndGet(), COUNTER2.incrementAndGet(), TipoEndereco.RESIDENCIAL, "Rua Gaspar Martins", 000, "CASA", "94869483", "Alvorada", "RS", "Brasil"));
        enderecoEntities.add(new EnderecoEntity(COUNTER.incrementAndGet(), COUNTER2.incrementAndGet(), TipoEndereco.COMERCIAL, "Rua Andaraí", 000, "PRÉDIO", "00153943", "Porto Alegre", "RS", "Brasil"));
        enderecoEntities.add(new EnderecoEntity(COUNTER.incrementAndGet(), COUNTER2.incrementAndGet(), TipoEndereco.RESIDENCIAL, "Rua Maris e Barros", 000, "CASA", "94621864", "Alvorada", "RS", "Brasil"));
    }

    public List<EnderecoEntity> list() {
        return enderecoEntities;
    }

    public EnderecoEntity listByIdEndereco(Integer idEndereco) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityProcurado = enderecoEntities.stream()
                .filter(enderecoEntity -> enderecoEntity.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereco não localizado!"));
        return enderecoEntityProcurado;
    }

    public List<EnderecoEntity> listByIdPessoa(Integer idPessoa) {
        return enderecoEntities.stream()
                .filter(pessoa -> pessoa.getIdPessoa().equals(idPessoa))
                .collect(Collectors.toList());
    }

    public EnderecoEntity create(EnderecoEntity enderecoEntity) {
        enderecoEntity.setIdEndereco(COUNTER.incrementAndGet());
        enderecoEntities.add(enderecoEntity);
        return enderecoEntity;
    }

    public EnderecoEntity update(Integer idEndereco, EnderecoEntity enderecoEntityAtualizar) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityProcurar = enderecoEntities.stream()
                .filter(enderecoEntity -> enderecoEntity.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereco não localizado"));
        enderecoEntityProcurar.setTipo(enderecoEntityAtualizar.getTipo());
        enderecoEntityProcurar.setLogradouro(enderecoEntityAtualizar.getLogradouro());
        enderecoEntityProcurar.setNumero(enderecoEntityAtualizar.getNumero());
        enderecoEntityProcurar.setComplemento(enderecoEntityAtualizar.getComplemento());
        enderecoEntityProcurar.setCep(enderecoEntityAtualizar.getCep());
        enderecoEntityProcurar.setCidade(enderecoEntityAtualizar.getCidade());
        enderecoEntityProcurar.setEstado(enderecoEntityAtualizar.getEstado());
        enderecoEntityProcurar.setPais(enderecoEntityAtualizar.getPais());

        return enderecoEntityProcurar;
    }

    public void delete(Integer idEndereco) throws RegraDeNegocioException {
        EnderecoEntity enderecoEntityProcurado = enderecoEntities.stream()
                .filter(enderecoEntity -> enderecoEntity.getIdEndereco().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Endereco não localizado!"));

        enderecoEntities.remove(enderecoEntityProcurado);
    }


}
