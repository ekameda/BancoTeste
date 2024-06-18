package com.teste.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.teste.banco.dto.ClienteDTO;
import com.teste.banco.dto.ContaDTO;
import com.teste.banco.exception.ClienteNotFoundException;
import com.teste.banco.exception.ContaNotFoundException;
import com.teste.banco.exception.CpfAlreadyExistsException;
import com.teste.banco.mapper.ClienteMapper;
import com.teste.banco.model.Cliente;
import com.teste.banco.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ContaService contaService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, ContaService contaService) {
        this.clienteRepository = clienteRepository;
        this.contaService = contaService;
    }

    public ClienteDTO salvarCliente(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CpfAlreadyExistsException("CPF já cadastrado");
        }
        try {
            cliente = clienteRepository.save(cliente);
            return ClienteMapper.toDTO(cliente);
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao salvar a cliente: " + e.getMessage(), e);
        }
    }

    public ClienteDTO buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não localizado com CPF: " + cpf));
        return ClienteMapper.toDTO(cliente);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado com id: " + id));
    }

    public ClienteDTO buscarClientePorConta(Long contaId) {
        ContaDTO contaDTO = contaService.findById(contaId);
        if (contaDTO == null) {
            throw new ContaNotFoundException("Conta não localizada com ID: " + contaId);
        }

        Long clienteId = contaDTO.getClientesIds().get(0);
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não localizado com ID: " + clienteId));
        return ClienteMapper.toDTO(cliente);
    }
}
