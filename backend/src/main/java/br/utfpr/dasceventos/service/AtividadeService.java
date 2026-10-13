package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.dto.AtividadeDTO;
import br.utfpr.dasceventos.model.Atividade;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.repository.AtividadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final EventoService eventoService;

    public List<Atividade> listarPorEvento(Long eventoId) {
        return atividadeRepository.findByEventoIdOrderByHorarioAsc(eventoId);
    }

    @Transactional
    public Atividade cadastrar(Long eventoId, AtividadeDTO dto) {
        Evento evento = eventoService.buscarPorId(eventoId);
        Atividade atividade = new Atividade();
        atividade.setNome(dto.nome());
        atividade.setTema(dto.tema());
        atividade.setHorario(dto.horario());
        atividade.setDuracaoMinutos(dto.duracaoMinutos());
        atividade.setEvento(evento);
        return atividadeRepository.save(atividade);
    }

    @Transactional
    public void excluir(Long id) {
        if (!atividadeRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Atividade não encontrada.");
        }
        atividadeRepository.deleteById(id);
    }
}
