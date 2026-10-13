package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.dto.EventoDTO;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    public List<Evento> listarFuturos() {
        return eventoRepository.findByDataGreaterThanEqualOrderByDataAscHorarioAsc(LocalDate.now());
    }

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));
    }

    @Transactional
    public Evento cadastrar(EventoDTO dto) {
        Evento evento = new Evento();
        preencher(evento, dto);
        evento.setVagasDisponiveis(dto.vagasTotais());
        return eventoRepository.save(evento);
    }

    @Transactional
    public Evento atualizar(Long id, EventoDTO dto) {
        Evento evento = buscarPorId(id);
        int vagasOcupadas = evento.getVagasTotais() - evento.getVagasDisponiveis();
        preencher(evento, dto);
        int novasVagasDisponiveis = dto.vagasTotais() - vagasOcupadas;
        if (novasVagasDisponiveis < 0) {
            throw new NegocioException("O número de vagas totais não pode ser menor que o de inscritos atuais.");
        }
        evento.setVagasDisponiveis(novasVagasDisponiveis);
        return eventoRepository.save(evento);
    }

    @Transactional
    public void excluir(Long id) {
        Evento evento = buscarPorId(id);
        eventoRepository.delete(evento);
    }

    private void preencher(Evento evento, EventoDTO dto) {
        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setData(dto.data());
        evento.setHorario(dto.horario());
        evento.setLocal(dto.local());
        evento.setVagasTotais(dto.vagasTotais());
        evento.setCategoria(dto.categoria());
    }
}
