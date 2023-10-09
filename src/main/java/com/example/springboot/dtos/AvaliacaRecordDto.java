package com.example.springboot.dtos;


import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record AvaliacaRecordDto (@NotBlank UUID idAvaliacao, @NotBlank UUID idCliente, @NotBlank UUID idPrato, @NotNull String comentario, @NotNull float classificacao, @NotNull float data) {
}
