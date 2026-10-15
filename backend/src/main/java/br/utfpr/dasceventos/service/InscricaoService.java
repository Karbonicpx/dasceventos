package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.model.Aluno;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.model.Inscricao;
import br.utfpr.dasceventos.model.StatusEvento;
import br.utfpr.dasceventos.model.StatusInscricao;
import br.utfpr.dasceventos.repository.InscricaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final AlunoService alunoService;
    private final EventoService eventoService;

    @Transactional
    public Inscricao inscrever(Long alunoId, Long eventoId) {
        Aluno aluno = alunoService.buscarPorId(alunoId);
        Evento evento = eventoService.buscarPorId(eventoId);

        if (evento.getStatus() == StatusEvento.CANCELADO) {
            throw new NegocioException("Este evento foi cancelado e não aceita novas inscrições.");
        }

        Inscricao inscricao = inscricaoRepository.findByAlunoIdAndEventoId(alunoId, eventoId).orElse(null);
        if (inscricao != null && inscricao.getStatus() == StatusInscricao.CONFIRMADA) {
            throw new NegocioException("Aluno já está inscrito neste evento.");
        }
        if (evento.getVagasDisponiveis() <= 0) {
            throw new NegocioException("Não há vagas disponíveis para este evento.");
        }

        evento.setVagasDisponiveis(evento.getVagasDisponiveis() - 1);

        // Reaproveita a inscrição existente (ex.: uma cancelada anteriormente) em vez de criar
        // uma nova linha, já que (aluno_id, evento_id) é uma constraint única na tabela.
        if (inscricao == null) {
            inscricao = new Inscricao();
            inscricao.setAluno(aluno);
            inscricao.setEvento(evento);
        }
        inscricao.setStatus(StatusInscricao.CONFIRMADA);
        inscricao.setDataInscricao(LocalDateTime.now());
        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public void cancelar(Long alunoId, Long eventoId) {
        Inscricao inscricao = inscricaoRepository.findByAlunoIdAndEventoId(alunoId, eventoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada."));
        if (inscricao.getStatus() == StatusInscricao.CANCELADA) {
            throw new NegocioException("Esta inscrição já está cancelada.");
        }
        inscricao.setStatus(StatusInscricao.CANCELADA);
        Evento evento = inscricao.getEvento();
        evento.setVagasDisponiveis(evento.getVagasDisponiveis() + 1);
        inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarPorAluno(Long alunoId) {
        return inscricaoRepository.findByAlunoId(alunoId);
    }

    public List<Inscricao> listarInscritosPorEvento(Long eventoId) {
        return inscricaoRepository.findByEventoIdAndStatus(eventoId, StatusInscricao.CONFIRMADA);
    }
}
