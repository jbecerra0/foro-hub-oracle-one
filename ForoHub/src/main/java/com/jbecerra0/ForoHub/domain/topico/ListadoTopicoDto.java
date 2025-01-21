package com.jbecerra0.ForoHub.domain.topico;

public record ListadoTopicoDto(Long id, String mensaje, String titulo, String nombreCurso) {
    public ListadoTopicoDto(Topico topico) {
        this(topico.getId(), topico.getMensaje(), topico.getTitulo(), topico.getCurso().getNombre());
    }
}
