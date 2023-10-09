package com.example.springboot.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;


import org.springframework.hateoas.RepresentationModel;


@Entity
@Table(name = "TB_Prato")
public class PratoModel extends RepresentationModel<PratoModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID idPrato;
	private String prato;
	private String descrição;
	private BigDecimal value;

	public UUID getIdPrato() {
		return idPrato;
	}

	public void setIdPrato(UUID idPrato) {
		this.idPrato = idPrato;
	}

	public String getPrato() {
		return prato;
	}

	public void setPrato(String prato) {
		this.prato = prato;
	}

	public String getdescrição() {
		return descrição;
	}

	public void setdescrição(String descrição) {
		this.descrição = descrição;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
