package com.teste.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.banco.model.Transacao;
import com.teste.banco.service.ExtratoService;

@RestController
@RequestMapping("/api/extrato")
@CrossOrigin(origins = "http://localhost:4200")
public class ExtratoController {

    @Autowired
    private ExtratoService extratoService;

    @GetMapping("/{numeroConta}")
    public List<Transacao> getContaByNumeroConta(@PathVariable Long numeroConta){
        return extratoService.findByNumeroConta(numeroConta);        
    }    
}
