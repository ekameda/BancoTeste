package com.teste.banco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.banco.model.Transacao;
import com.teste.banco.repository.TransacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> findByContaId(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }

    public List<Transacao> getAllTransacoes() {
        return transacaoRepository.findAll();
    }

    public Optional<Transacao> buscarPorId(Long id) {
        return transacaoRepository.findById(id);
    }

    @Transactional
    public Transacao save(Transacao entity) {
        return transacaoRepository.save(entity);
    }

}
