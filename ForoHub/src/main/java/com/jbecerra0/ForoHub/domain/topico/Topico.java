package com.jbecerra0.ForoHub.domain.topico;

import com.jbecerra0.ForoHub.domain.curso.Curso;
import com.jbecerra0.ForoHub.domain.respuesta.Respuesta;
import com.jbecerra0.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Lob
    private String mensaje;

    private LocalDateTime fechaCreacion;

    private String status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico")
    private List<Respuesta> respuestas;

    public Topico(CreacionTopicoDto topico, Curso curso) {
        this.mensaje = topico.mensaje();
        this.curso = curso;
        this.titulo = topico.titulo();
        this.fechaCreacion = LocalDateTime.now();
    }

    public void actualizar(ActualizarTopicoDto actualizacionTopico) {
        if (actualizacionTopico.mensaje() != null) {
            this.mensaje = actualizacionTopico.mensaje();
        }

        if (actualizacionTopico.titulo() != null) {
            this.titulo = actualizacionTopico.titulo();
        }
    }
}
