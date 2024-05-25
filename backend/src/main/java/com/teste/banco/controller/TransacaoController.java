package com.teste.banco.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.model.Conta;
import com.teste.banco.model.Transacao;
import com.teste.banco.repository.ContaRepository;
import com.teste.banco.repository.TransacaoRepository;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @GetMapping
    public List<Transacao> getAllTransacoes() {
        return transacaoRepository.findAll();
    }

     @PostMapping
    public Transacao createTransacao(@RequestBody Transacao transacao) {
        transacao.setDataHora(LocalDateTime.now());
        Conta conta = contaRepository.findById(transacao.getConta().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Conta not found"));

        if ("CREDITO".equalsIgnoreCase(transacao.getTipo())) {
            conta.setSaldo(conta.getSaldo() + transacao.getValor());
        } else if ("DEBITO".equalsIgnoreCase(transacao.getTipo())) {
            if (conta.getSaldo() < transacao.getValor()) {
                throw new RuntimeException("Saldo insuficiente.");
            }
            conta.setSaldo(conta.getSaldo() - transacao.getValor());
        } else {
            throw new RuntimeException("Tipo de transação inválido.");
        }

        conta.getTransacoes().add(transacao);
        contaRepository.save(conta);
        return transacaoRepository.save(transacao);
    }

    @GetMapping("/conta/{contaId}")
    public List<Transacao> getTransacoesByConta(@PathVariable Long contaId) {
        Conta conta = contaRepository.findById(contaId)
            .orElseThrow(() -> new ResourceNotFoundException("Conta not found"));
        return conta.getTransacoes();
    }
}
