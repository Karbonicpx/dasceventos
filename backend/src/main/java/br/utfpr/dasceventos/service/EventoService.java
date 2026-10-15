package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.dto.EventoDTO;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.model.Inscricao;
import br.utfpr.dasceventos.model.StatusEvento;
import br.utfpr.dasceventos.model.StatusInscricao;
import br.utfpr.dasceventos.repository.EventoRepository;
import br.utfpr.dasceventos.repository.InscricaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InscricaoRepository inscricaoRepository;

    public List<Evento> listar(boolean todos, String busca) {
        List<Evento> base = (busca != null && !busca.isBlank())
                ? eventoRepository.findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(busca, busca)
                : eventoRepository.findAll();

        LocalDate hoje = LocalDate.now();
        return base.stream()
                .filter(e -> todos || !e.getData().isBefore(hoje))
                .sorted(Comparator.comparing(Evento::getData).thenComparing(Evento::getHorario))
                .toList();
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
        evento.setStatus(StatusEvento.ATIVO);
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
    public Evento cancelar(Long id) {
        Evento evento = buscarPorId(id);
        if (evento.getStatus() == StatusEvento.CANCELADO) {
            throw new NegocioException("Este evento já está cancelado.");
        }
        evento.setStatus(StatusEvento.CANCELADO);
        List<Inscricao> inscricoes = inscricaoRepository.findByEventoIdAndStatus(id, StatusInscricao.CONFIRMADA);
        inscricoes.forEach(i -> i.setStatus(StatusInscricao.CANCELADA));
        inscricaoRepository.saveAll(inscricoes);
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
        evento.setFotoBanner1(dto.fotoBanner1());
        evento.setFotoBanner2(dto.fotoBanner2());
        evento.setFotoBanner3(dto.fotoBanner3());
    }
}
