package com.example.springboot.dtos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PedidoRecordDto 
(@NotBlank UUID idPedido, @NotBlank long idCliente, @NotNull float data, @NotNull String status,
@NotNull BigDecimal value, @NotNull String pagamento) {
}

