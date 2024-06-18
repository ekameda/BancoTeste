package com.teste.banco.service;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.banco.model.Conta;
import com.teste.banco.model.Transacao;
import com.teste.banco.repository.ContaRepository;
import com.teste.banco.repository.TransacaoRepository;

@Service
public class ExtratoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    public List<Transacao> findByNumeroConta(Long contaNumero) {
        Conta conta = contaRepository.findByContaNumero(contaNumero);
        if (conta != null) {
            return transacaoRepository.findAllTransacaoByContaId(conta.getId());
        } else {
            return Collections.emptyList();
        }
    }
}

    
