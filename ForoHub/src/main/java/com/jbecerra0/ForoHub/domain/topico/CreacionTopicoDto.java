package com.jbecerra0.ForoHub.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record CreacionTopicoDto(
        @NotBlank
        String mensaje,
        @NotBlank
        String nombreCurso,
        @NotBlank
        String titulo
) {
}
