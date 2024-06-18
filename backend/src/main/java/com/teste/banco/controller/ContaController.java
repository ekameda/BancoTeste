package com.teste.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.dto.ContaDTO;
import com.teste.banco.exception.ContaNotFoundException;
import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.service.ContaService;

@RestController
@RequestMapping("/api/conta")
@CrossOrigin(origins = "http://localhost:4200")
public class ContaController {

    private final ContaService contaService;

    @Autowired
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> getAllContas() {
        try {
            List<ContaDTO> contaDTOs = contaService.findAll();
            return ResponseEntity.ok(contaDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@RequestParam Long idCliente, @RequestParam Long numeroConta) {
        try {
            ContaDTO contaDTO = contaService.criarConta(idCliente, numeroConta);
            return ResponseEntity.status(HttpStatus.CREATED).body(contaDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable Long id) {
        try {
            ContaDTO contaDTO = contaService.findById(id);
            return ResponseEntity.ok(contaDTO);
        } catch (ContaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/byNumero/{numero}")
    public ResponseEntity<ContaDTO> getContaByNumero(@PathVariable Long numero) {
        try {
            ContaDTO contaDTO = contaService.findByNumero(numero);
            return ResponseEntity.ok(contaDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
