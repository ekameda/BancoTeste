package com.teste.banco.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double saldo;
        
    @Column(unique = true)
    private Long contaNumero;

    @ManyToMany(mappedBy = "contas")
    private List<Cliente> clientes = new ArrayList<>();

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        transacao.setConta(this);
    }

    public List<Transacao> obterTransacoes() {
        return transacoes;
    }    
    
}