package br.utfpr.dasceventos.controller;

import br.utfpr.dasceventos.dto.EventoDTO;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public List<EventoDTO> listar(@RequestParam(name = "todos", defaultValue = "false") boolean todos) {
        List<Evento> eventos = todos ? eventoService.listarTodos() : eventoService.listarFuturos();
        return eventos.stream().map(EventoDTO::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public EventoDTO buscar(@PathVariable Long id) {
        return EventoDTO.fromEntity(eventoService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoDTO cadastrar(@Valid @RequestBody EventoDTO dto) {
        return EventoDTO.fromEntity(eventoService.cadastrar(dto));
    }

    @PutMapping("/{id}")
    public EventoDTO atualizar(@PathVariable Long id, @Valid @RequestBody EventoDTO dto) {
        return EventoDTO.fromEntity(eventoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        eventoService.excluir(id);
    }
}
