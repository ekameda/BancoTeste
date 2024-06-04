package com.teste.banco.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.teste.banco.service.ContaService;
import com.teste.banco.service.TransacaoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ContaService contaService;

    @GetMapping
    public List<Transacao> getAllTransacoes() {
        return transacaoService.getAllTransacoes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> buscarTransacaoPorId(@PathVariable Long id) {
        Transacao transacao = transacaoService.buscarPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao not found"));
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    @Transactional
    public Transacao createTransacao(@RequestBody Transacao transacao) {
        transacao.setDataHora(LocalDateTime.now());
        Conta conta = contaService.findById(transacao.getConta().getId())
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
        conta.adicionarTransacao(transacao);
        contaService.save(conta);
        return transacaoService.save(transacao);
    }

    @GetMapping("/conta/{contaId}")
    public List<Transacao> getTransacoesByConta(@PathVariable Long contaId) {
        return transacaoService.findByContaId(contaId);
    }

}
