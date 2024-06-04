package com.teste.banco.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.banco.model.Conta;
import com.teste.banco.model.Transacao;
import com.teste.banco.repository.ContaRepository;

import jakarta.transaction.Transactional;

@Service
public class ContaService {

    private static final Random random = new Random();
    
    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public Transacao criarTransacao(Long contaId, Transacao transacao) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        conta.adicionarTransacao(transacao);
        return transacao;
    }

    public List<Transacao> obterTransacoesDaConta(Long contaId) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        return conta.obterTransacoes();
    }

    public Optional<Conta> findById(Long id) {
        return contaRepository.findById(id);
    }
    
    public Conta findByNumero(Long numero){
        return contaRepository.findByContaNumero(numero);
    }
    
    public Conta save(Conta entity) {
        return contaRepository.save(entity);
    }

    @Transactional
    public Conta criarConta(Conta conta) {
        conta.setContaNumero(gerarNumeroContaUnico());
        return contaRepository.save(conta);
    }

    private Long gerarNumeroContaUnico() {
        Long numeroConta;
        do {
            numeroConta = Math.abs(random.nextLong());
        } while (contaRepository.existsByContaNumero(numeroConta));
        return numeroConta;
    }

    public List<Conta> findAll(){
        return contaRepository.findAll();
    }
}
