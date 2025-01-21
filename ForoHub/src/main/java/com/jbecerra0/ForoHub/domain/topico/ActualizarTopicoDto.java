package com.jbecerra0.ForoHub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDto(@NotNull Long id, String mensaje, String titulo) {
}
