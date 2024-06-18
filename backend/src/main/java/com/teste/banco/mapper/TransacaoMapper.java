package com.teste.banco.mapper;

import com.teste.banco.dto.TransacaoDTO;
import com.teste.banco.model.Transacao;

public class TransacaoMapper {

    public static TransacaoDTO toDTO(Transacao transacao) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setId(transacao.getId());
        dto.setContaId(transacao.getConta().getId());
        dto.setTipo(transacao.getTipo());
        dto.setValor(transacao.getValor());
        dto.setDataHora(transacao.getDataHora());
        return dto;
    }

    public static Transacao toEntity(TransacaoDTO dto) {
        Transacao transacao = new Transacao();
        transacao.setId(dto.getId());
        transacao.setTipo(dto.getTipo());
        transacao.setValor(dto.getValor());
        transacao.setDataHora(dto.getDataHora());
        return transacao;
    }
}
