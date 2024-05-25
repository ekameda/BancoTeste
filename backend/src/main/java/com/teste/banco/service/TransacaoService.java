package com.teste.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.banco.model.Transacao;
import com.teste.banco.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public List<Transacao> findByContaId(Long contaId) {
        return transacaoRepository.findByContaId(contaId);
    }
}
