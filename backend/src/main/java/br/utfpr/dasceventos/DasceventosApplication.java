package br.utfpr.dasceventos;

import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.model.Atividade;
import br.utfpr.dasceventos.model.CategoriaEvento;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.repository.AdministradorRepository;
import br.utfpr.dasceventos.repository.EventoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class DasceventosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasceventosApplication.class, args);
	}

	/**
	 * Cria um administrador padrão do DASC na primeira execução, caso a tabela
	 * esteja vazia, já que o cadastro de administrador não é exposto via API.
	 */
	@Bean
	public CommandLineRunner seedAdministrador(AdministradorRepository administradorRepository,
												PasswordEncoder passwordEncoder) {
		return args -> {
			if (administradorRepository.count() == 0) {
				Administrador admin = new Administrador();
				admin.setNome("Sofia Kawazoe");
				admin.setEmail("dasc@utfpr.edu.br");
				admin.setSenha(passwordEncoder.encode("dasc123"));
				administradorRepository.save(admin);
			}
		};
	}

	/**
	 * Popula a tabela de eventos com uma amostra realista do calendário de
	 * extensão do DASC (mesmo conteúdo divulgado no Instagram @dasc.utfpr),
	 * caso o banco esteja vazio.
	 */
	@Bean
	public CommandLineRunner seedEventos(EventoRepository eventoRepository) {
		return args -> {
			if (eventoRepository.count() > 0) return;

			Evento secomp = novoEvento(eventoRepository, "SECOMP+ 2026",
					"Semana Acadêmica de Computação do DASC: palestras com profissionais de mercado, " +
							"oficinas práticas e a tradicional maratona de programação entre os cursos de SI e ECOMP.",
					LocalDate.of(2026, 11, 3), LocalTime.of(8, 30), "Centro de Convenções - UTFPR Curitiba",
					300, 212, CategoriaEvento.SECOMP_MAIS);
			addAtividade(secomp, "Credenciamento", null, LocalTime.of(8, 30), 30);
			addAtividade(secomp, "Palestra de abertura", "Mercado de trabalho em TI", LocalTime.of(9, 0), 60);
			addAtividade(secomp, "Maratona de Programação", "Algoritmos e estrutura de dados", LocalTime.of(13, 0), 240);
			addAtividade(secomp, "Oficina de Cloud", "Containers e deploy com Docker", LocalTime.of(17, 30), 90);
			eventoRepository.save(secomp);

			Evento cointer = novoEvento(eventoRepository, "COINTER - Congresso Internacional",
					"Congresso Internacional de Tecnologia da Informação, com apresentação de trabalhos " +
							"científicos de alunos e pesquisadores convidados de outras instituições.",
					LocalDate.of(2026, 11, 15), LocalTime.of(9, 0), "Auditório Principal - UTFPR Curitiba",
					200, 0, CategoriaEvento.COINTER);
			addAtividade(cointer, "Abertura e credenciamento", null, LocalTime.of(9, 0), 30);
			addAtividade(cointer, "Sessão de artigos", "Inteligência artificial aplicada", LocalTime.of(9, 30), 120);
			addAtividade(cointer, "Mesa redonda", "Pesquisa e inovação em SI", LocalTime.of(14, 0), 90);
			eventoRepository.save(cointer);

			Evento trote = novoEvento(eventoRepository, "Trote Solidário",
					"Recepção dos calouros de Sistemas de Informação e Engenharia da Computação com " +
							"arrecadação de alimentos e agasalhos para instituições parceiras do DASC.",
					LocalDate.of(2026, 10, 28), LocalTime.of(10, 0), "Pátio do Bloco A - UTFPR Curitiba",
					80, 53, CategoriaEvento.TROTE_SOLIDARIO);
			addAtividade(trote, "Recepção dos calouros", null, LocalTime.of(10, 0), 30);
			addAtividade(trote, "Gincana solidária", "Arrecadação de alimentos e agasalhos", LocalTime.of(10, 30), 90);
			addAtividade(trote, "Integração com veteranos", null, LocalTime.of(12, 0), 60);
			eventoRepository.save(trote);

			Evento gradeNaHora = novoEvento(eventoRepository, "Grade na Hora",
					"Plantão de dúvidas do DASC para auxiliar os alunos no planejamento da grade e " +
							"matrícula do próximo semestre, com monitores de cada período.",
					LocalDate.of(2026, 10, 24), LocalTime.of(13, 30), "Sala de Convivência DASC - Bloco B",
					60, 38, CategoriaEvento.GRADE_NA_HORA);
			addAtividade(gradeNaHora, "Orientação de matrícula", "Como montar a grade ideal", LocalTime.of(13, 30), 60);
			addAtividade(gradeNaHora, "Atendimento individual", null, LocalTime.of(14, 30), 90);
			eventoRepository.save(gradeNaHora);

			Evento palestraIa = novoEvento(eventoRepository, "Introdução à Inteligência Artificial",
					"Palestra aberta sobre fundamentos de IA e aprendizado de máquina, com exemplos " +
							"de aplicações usadas no mercado de tecnologia atualmente.",
					LocalDate.of(2026, 10, 20), LocalTime.of(19, 0), "Auditório Central - UTFPR Curitiba",
					120, 87, CategoriaEvento.PALESTRA);
			addAtividade(palestraIa, "Abertura", null, LocalTime.of(19, 0), 15);
			addAtividade(palestraIa, "Palestra principal", "IA no dia a dia", LocalTime.of(19, 15), 60);
			addAtividade(palestraIa, "Perguntas e respostas", null, LocalTime.of(20, 15), 30);
			eventoRepository.save(palestraIa);

			Evento minicursoAngular = novoEvento(eventoRepository, "Minicurso de Angular",
					"Minicurso prático para criar aplicações web modernas com Angular, componentes " +
							"standalone, rotas e consumo de APIs REST.",
					LocalDate.of(2026, 10, 22), LocalTime.of(14, 0), "Laboratório 3 - Bloco B",
					30, 4, CategoriaEvento.MINICURSO);
			addAtividade(minicursoAngular, "Configuração do ambiente", "Node e Angular CLI", LocalTime.of(14, 0), 45);
			addAtividade(minicursoAngular, "Componentes e rotas", null, LocalTime.of(14, 45), 90);
			eventoRepository.save(minicursoAngular);
		};
	}

	private Evento novoEvento(EventoRepository repo, String titulo, String descricao, LocalDate data,
							   LocalTime horario, String local, int vagasTotais, int vagasDisponiveis,
							   CategoriaEvento categoria) {
		Evento evento = new Evento();
		evento.setTitulo(titulo);
		evento.setDescricao(descricao);
		evento.setData(data);
		evento.setHorario(horario);
		evento.setLocal(local);
		evento.setVagasTotais(vagasTotais);
		evento.setVagasDisponiveis(vagasDisponiveis);
		evento.setCategoria(categoria);
		return evento;
	}

	private void addAtividade(Evento evento, String nome, String tema, LocalTime horario, int duracaoMinutos) {
		Atividade atividade = new Atividade();
		atividade.setNome(nome);
		atividade.setTema(tema);
		atividade.setEvento(evento);
		atividade.setHorario(horario);
		atividade.setDuracaoMinutos(duracaoMinutos);
		evento.getAtividades().add(atividade);
	}

}
