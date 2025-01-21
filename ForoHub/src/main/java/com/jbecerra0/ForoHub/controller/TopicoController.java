package com.jbecerra0.ForoHub.controller;

import com.jbecerra0.ForoHub.domain.curso.Curso;
import com.jbecerra0.ForoHub.domain.curso.CursoRepository;
import com.jbecerra0.ForoHub.domain.topico.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository topicoRepository;

    private final CursoRepository cursoRepository;

    public TopicoController(TopicoRepository topicoRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTopicoDto>> listar(@PageableDefault(size = 2) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(ListadoTopicoDto::new));
    }

    @PostMapping
    public ResponseEntity<RespuestaTopicoDto> crear(@RequestBody @Valid CreacionTopicoDto nuevoTopico, UriComponentsBuilder uriComponentsBuilder) {
        Curso curso = cursoRepository.findByNombre(nuevoTopico.nombreCurso()).orElseGet(
                () -> {
                    Curso nuevoCurso = new Curso();
                    nuevoCurso.setNombre(nuevoTopico.nombreCurso());
                    nuevoCurso.setTopicos(List.of());
                    return cursoRepository.save(nuevoCurso);
                }
        );

        Topico topico = topicoRepository.save(new Topico(nuevoTopico, curso));

        RespuestaTopicoDto respuestaTopicoDto = new RespuestaTopicoDto(
                topico.getId(),
                topico.getMensaje(),
                topico.getCurso().getNombre(),
                topico.getTitulo()
        );

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(url).body(respuestaTopicoDto);
    }

    @PutMapping
    public ResponseEntity<RespuestaTopicoDto> actualizar(@RequestBody @Valid ActualizarTopicoDto actualizacionTopico) {
        Topico topico = topicoRepository.findById(actualizacionTopico.id())
                .orElseThrow(() -> new EntityNotFoundException("Tópico con ID " + actualizacionTopico.id() + " no encontrado."));

        topico.actualizar(actualizacionTopico);

        return ResponseEntity.ok(
                new RespuestaTopicoDto(
                        topico.getId(),
                        topico.getMensaje(),
                        topico.getCurso().getNombre(),
                        topico.getTitulo()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tópico con ID " + id + " no encontrado.");
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
