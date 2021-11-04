package com.dbc.pessoaapi.repository;

import com.dbc.pessoaapi.entity.ContatoEntity;
import com.dbc.pessoaapi.entity.TipoContato;
import com.dbc.pessoaapi.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class ContatoRepository {
    private static List<ContatoEntity> contatoEntities = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public ContatoRepository(){
        contatoEntities.add(new ContatoEntity(COUNTER.incrementAndGet(), 1, TipoContato.RESIDENCIAL, "51983456878", "WHATSAPP" ));
        contatoEntities.add(new ContatoEntity(COUNTER.incrementAndGet(), 1, TipoContato.COMERCIAL, "51983456878", "TRABALHO" ));
        contatoEntities.add(new ContatoEntity(COUNTER.incrementAndGet(), 2, TipoContato.RESIDENCIAL, "51983456878", "PESSOAL" ));
    }

    public ContatoEntity create(ContatoEntity contatoEntity){
        contatoEntity.setIdContato(COUNTER.incrementAndGet());
        contatoEntities.add(contatoEntity);
        return contatoEntity;
    }

    public List<ContatoEntity> list(){
        return contatoEntities;
    }

    public List<ContatoEntity> listByIdPessoa(Integer idPessoa){
        return contatoEntities.stream()
                .filter(pessoa -> pessoa.getIdPessoa().equals(idPessoa))
                .collect(Collectors.toList());
    }

    public ContatoEntity update(Integer id, ContatoEntity contatoEntityAtualizar) throws RegraDeNegocioException{
        ContatoEntity contatoEntityProcurado = contatoEntities.stream()
                .filter(contatoEntity -> contatoEntity.getIdContato().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Contato não localizado"));
        contatoEntityProcurado.setTipoContato(contatoEntityAtualizar.getTipoContato());
        contatoEntityProcurado.setNumero(contatoEntityAtualizar.getNumero());
        contatoEntityProcurado.setDescricao(contatoEntityAtualizar.getDescricao());

        return contatoEntityProcurado;
    }

    public void delete(Integer id) throws RegraDeNegocioException{
        ContatoEntity contatoEntityProcurado = contatoEntities.stream()
                .filter(contatoEntity -> contatoEntity.getIdContato().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Contato não localizado"));
        contatoEntities.remove(contatoEntityProcurado);
    }
}
