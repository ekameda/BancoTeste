package com.teste.banco.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message="Nome não pode ser em Branco")
    @NotNull(message="Nome não pode ser Nulo")
    private String nome;

    @NotNull(message = "Idade não pode ser nula")
    @Min(value = 0, message = "Idade deve ser maior ou igual a 0")
    @Max(value = 150, message = "Idade deve ser menor ou igual a 150")
    private Integer idade;
    
    @NotBlank(message="Email não pode ser em Branco")
    @NotNull(message = "Email não pode ser nulo")
    private String email;

    @NotBlank(message="CPF não pode ser em Branco")
    @NotNull(message="CPF não pode ser Nulo")
    @Column(nullable = false, unique = true)
    private String cpf;

    @ManyToMany
    @JoinTable(name = "cliente_conta", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "conta_id"))
    private List<Conta> contas = new ArrayList<>();
}