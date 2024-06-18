package com.teste.banco.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.exception.ResourceNotFoundException;
import com.teste.banco.exception.TransacaoNotAcceptableException;
import com.teste.banco.exception.TransacaoNotFoundException;
import com.teste.banco.mapper.TransacaoMapper;
import com.teste.banco.model.Conta;
import com.teste.banco.model.Transacao;
import com.teste.banco.repository.ContaRepository;
import com.teste.banco.repository.TransacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
    }

    public List<TransacaoDTO> getAllTransacao() {
        return transacaoRepository.findAll().stream()
                .map(TransacaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TransacaoDTO getTransacaoById(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transacao not found with ID: " + id));
        return TransacaoMapper.toDTO(transacao);
    }

    @Transactional
    public TransacaoDTO save(TransacaoDTO transacaoDTO) {
        Conta conta = contaRepository.findById(transacaoDTO.getContaId())
            .orElseThrow(() -> new TransacaoNotFoundException("Conta not found with ID: " + transacaoDTO.getContaId()));
        Transacao transacao = TransacaoMapper.toEntity(transacaoDTO);
        transacao.setConta(conta);
        transacao.setDataHora(LocalDateTime.now());

        updateValor(conta, transacao);

        contaRepository.save(conta);
        transacao = transacaoRepository.save(transacao);
        return TransacaoMapper.toDTO(transacao);
    }

    private void updateValor(Conta conta, Transacao transacao) {
        BigDecimal valor = BigDecimal.ZERO;

        switch (transacao.getTipo().toUpperCase()) {
            case "CREDIT" ->
                valor = conta.getSaldo().add(transacao.getValor());
            case "DEBIT" -> {
                if (conta.getSaldo().compareTo(transacao.getValor()) < 0) {
                    throw new TransacaoNotAcceptableException("Saldo Insuficiente na Conta.");
                }
                valor = conta.getSaldo().subtract(transacao.getValor());
            }
            default ->
                throw new TransacaoNotAcceptableException("Tipo de transacao Inválido.");
        }
        conta.setSaldo(valor);
    }

    public List<TransacaoDTO> getTransacaoByContaId(Long contaId) {
        return transacaoRepository.findAllTransacaoByContaId(contaId).stream()
            .map(TransacaoMapper::toDTO)
            .collect(Collectors.toList());
    }
}
