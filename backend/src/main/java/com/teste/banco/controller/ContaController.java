package com.teste.banco.controller;

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
import com.teste.banco.repository.ContaRepository;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
public class ContaController {
    @Autowired
    private ContaRepository contaRepository;

    @GetMapping
    public List<Conta> getAllContas() {
        return contaRepository.findAll();
    }

    @PostMapping
    public Conta createConta(@RequestBody Conta conta) {
        if (conta.getClientes().size() > 2) {
            throw new RuntimeException("Conta não pode ter mais que 2 clientes.");
        }
        return contaRepository.save(conta);
    }

    @GetMapping("/{id}")
    public Conta getContaById(@PathVariable Long id) {
        return contaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Conta not found"));
    }
}
