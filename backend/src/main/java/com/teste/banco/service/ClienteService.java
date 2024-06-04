package com.teste.banco.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.banco.exception.CpfAlreadyExistsException;
import com.teste.banco.model.Cliente;
import com.teste.banco.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) throws CpfAlreadyExistsException {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CpfAlreadyExistsException("CPF já cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Cliente findClienteById(Long id){
        return null;//clienteRepository.findClienteById(id);
    }


}
