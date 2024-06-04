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
import com.teste.banco.service.ContaService;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @GetMapping
    public List<Conta> getAllContas() {
        return contaService.findAll();
    }

    @PostMapping
    public Conta createConta(@RequestBody Conta conta) {
        return contaService.save(conta);
    }

    @GetMapping("/{id}")
    public Conta getContaById(@PathVariable Long id) {
        return contaService.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Conta not found"));
    }
}
