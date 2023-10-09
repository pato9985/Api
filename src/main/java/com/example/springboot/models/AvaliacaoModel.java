package com.example.springboot.models;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "TB_Avalicao")
public class AvaliacaoModel extends RepresentationModel<PratoModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID idAvaliacao;
    private UUID idCliente;
    private UUID idPrato;
    private String comentario;
    private float classificacao;
	private float data;


    public UUID getIdAvaliacao() {
        return idAvaliacao;
    }
    public void setIdAvaliacao(UUID idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }
    public UUID getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(UUID idCliente) {
        this.idCliente = idCliente;
    }
    public UUID getIdPrato() {
        return idPrato;
    }
    public void setIdPrato(UUID idPrato) {
        this.idPrato = idPrato;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public float getClassificacao() {
        return classificacao;
    }
    public void setClassificacao(float classificacao) {
        this.classificacao = classificacao;
    }
    public float getData() {
        return data;
    }
    public void setData(float data) {
        this.data = data;
    }

}

    

