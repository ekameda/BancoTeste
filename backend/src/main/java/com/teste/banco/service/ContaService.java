package com.teste.banco.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teste.banco.dto.ContaDTO;
import com.teste.banco.exception.ContaNotFoundException;
import com.teste.banco.mapper.ContaMapper;
import com.teste.banco.model.Cliente;
import com.teste.banco.model.Conta;
import com.teste.banco.repository.ContaRepository;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ClienteService clienteService;

    @Autowired
    public ContaService(ContaRepository contaRepository, ClienteService clienteService) {
        this.contaRepository = contaRepository;
        this.clienteService = clienteService;
    }

    public ContaDTO findById(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada com ID: " + id));
        return ContaMapper.toDTO(conta);
    }

    public List<ContaDTO> findAll() {
        List<Conta> contas = contaRepository.findAll();
        if (contas.isEmpty()) {
            throw new ContaNotFoundException("Nenhuma Conta encontrada");
        }
        return contas.stream()
                .map(ContaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContaDTO findByNumero(Long numero) {
        Conta conta = contaRepository.findByContaNumero(numero);
        if (conta == null) {
            throw new ContaNotFoundException("Conta não encontrada com número: " + numero);
        }
        return ContaMapper.toDTO(conta);
    }

    @Transactional
    public ContaDTO criarConta(Long idCliente, Long numeroConta) {
        validarParametros(idCliente, numeroConta);
        Cliente cliente = obterCliente(idCliente);
        Conta conta = criarNovaConta(numeroConta, cliente);
        contaRepository.save(conta);
        return ContaMapper.toDTO(conta);
    }

    private void validarParametros(Long idCliente, Long numeroConta) {
        if (numeroConta == null || numeroConta == 0L) {
            throw new IllegalArgumentException("Número da conta inválido");
        }
        if (idCliente == null || idCliente == 0L) {
            throw new IllegalArgumentException("Número do cliente inválido");
        }
    }

    private Cliente obterCliente(Long idCliente) {
        return clienteService.findById(idCliente);
    }

    private Conta criarNovaConta(Long numeroConta, Cliente cliente) {
        Conta conta = new Conta();
        conta.setContaNumero(numeroConta);
        conta.addCliente(cliente);
        cliente.addConta(conta);
        return conta;
    }
}