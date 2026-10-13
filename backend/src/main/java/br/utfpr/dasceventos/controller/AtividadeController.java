package br.utfpr.dasceventos.controller;

import br.utfpr.dasceventos.dto.AtividadeDTO;
import br.utfpr.dasceventos.service.AtividadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos/{eventoId}/atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeService atividadeService;

    @GetMapping
    public List<AtividadeDTO> listar(@PathVariable Long eventoId) {
        return atividadeService.listarPorEvento(eventoId).stream().map(AtividadeDTO::fromEntity).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AtividadeDTO cadastrar(@PathVariable Long eventoId, @Valid @RequestBody AtividadeDTO dto) {
        return AtividadeDTO.fromEntity(atividadeService.cadastrar(eventoId, dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long eventoId, @PathVariable Long id) {
        atividadeService.excluir(id);
    }
}
