package br.utfpr.dasceventos.controller;

import br.utfpr.dasceventos.dto.InscricaoDTO;
import br.utfpr.dasceventos.service.InscricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscricoes")
@RequiredArgsConstructor
public class InscricaoController {

    private final InscricaoService inscricaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InscricaoDTO inscrever(@RequestParam Long alunoId, @RequestParam Long eventoId) {
        return InscricaoDTO.fromEntity(inscricaoService.inscrever(alunoId, eventoId));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@RequestParam Long alunoId, @RequestParam Long eventoId) {
        inscricaoService.cancelar(alunoId, eventoId);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<InscricaoDTO> listarPorAluno(@PathVariable Long alunoId) {
        return inscricaoService.listarPorAluno(alunoId).stream().map(InscricaoDTO::fromEntity).toList();
    }

    @GetMapping("/evento/{eventoId}")
    public List<InscricaoDTO> listarPorEvento(@PathVariable Long eventoId) {
        return inscricaoService.listarInscritosPorEvento(eventoId).stream().map(InscricaoDTO::fromEntity).toList();
    }
}
