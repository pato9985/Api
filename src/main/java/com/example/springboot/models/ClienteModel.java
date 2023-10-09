package com.example.springboot.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.hateoas.Link;

import jakarta.persistence.*;


@Entity
@Table(name ="TB_Cliente")
public class ClienteModel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCliente; 
    private String nome;
    private BigDecimal senha;
   
    public UUID getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public BigDecimal getSenha() {
        return senha;
    }
    public void setSenha(BigDecimal senha) {
        this.senha = senha;
    }
    public void add(Link withSelfRel) {
    }



}
