package com.example.springboot.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_Pedido")
public class PedidoModel extends RepresentationModel<PratoModel> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID idPedido;
    private long idCliente;
	private float data;
	private String status;
	private BigDecimal value;
    private String pagamento;
    
    public UUID getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }
    public long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }
    public float getData() {
        return data;
    }
    public void setData(float data) {
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    public String getPagamento() {
        return pagamento;
    }
    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }


    
}