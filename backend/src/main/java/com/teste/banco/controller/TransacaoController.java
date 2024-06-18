package com.teste.banco.controller;

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

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.exception.TransacaoNotAcceptableException;
import com.teste.banco.service.TransacaoService;

@RestController
@RequestMapping("/api/transacao")
@CrossOrigin(origins = "http://localhost:4200")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @Autowired
    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<List<TransacaoDTO>> getAllTransacao() {
        try {
            List<TransacaoDTO> transactionDTOs = transacaoService.getAllTransacao();
            return ResponseEntity.ok(transactionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> getTransactionById(@PathVariable Long id) {
        try {
            TransacaoDTO transactionDTO = transacaoService.getTransacaoById(id);
            return ResponseEntity.ok(transactionDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<TransacaoDTO> createTransaction(@RequestBody TransacaoDTO transactionDTO) {
        try {
            TransacaoDTO savedTransacao = transacaoService.save(transactionDTO);
            return ResponseEntity.status(201).body(savedTransacao);
        } catch (TransacaoNotAcceptableException e) {
            return ResponseEntity.status(400).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<TransacaoDTO>> getTransacaoByContaId(@PathVariable Long contaId) {
        try {
            List<TransacaoDTO> transacao = transacaoService.getTransacaoByContaId(contaId);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
