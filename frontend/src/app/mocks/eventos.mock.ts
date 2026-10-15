import { Atividade, Evento } from '../models/evento.model';

// Dados fictícios inspirados nas divulgações reais do DASC no Instagram
// (@dasc.utfpr). Altere USAR_MOCK para false quando o backend estiver rodando.
export const USAR_MOCK = false;

export const EVENTOS_MOCK: Evento[] = [
  {
    id: 1,
    titulo: 'SECOMP+ 2026',
    descricao:
      'Semana Acadêmica de Computação do DASC: palestras com profissionais de mercado, oficinas práticas ' +
      'e a tradicional maratona de programação entre os cursos de SI e ECOMP.',
    data: '2026-11-03',
    horario: '08:30',
    local: 'Centro de Convenções - UTFPR Curitiba',
    vagasTotais: 300,
    vagasDisponiveis: 212,
    categoria: 'SEMANA_ACADEMICA'
  },
  {
    id: 2,
    titulo: 'COINTER - Congresso Internacional',
    descricao:
      'Congresso Internacional de Tecnologia da Informação, com apresentação de trabalhos científicos ' +
      'de alunos e pesquisadores convidados de outras instituições.',
    data: '2026-11-15',
    horario: '09:00',
    local: 'Auditório Principal - UTFPR Curitiba',
    vagasTotais: 200,
    vagasDisponiveis: 0,
    categoria: 'CONGRESSO'
  },
  {
    id: 3,
    titulo: 'Trote Solidário',
    descricao:
      'Recepção dos calouros de Sistemas de Informação e Engenharia da Computação com arrecadação de ' +
      'alimentos e agasalhos para instituições parceiras do DASC.',
    data: '2026-10-28',
    horario: '10:00',
    local: 'Pátio do Bloco A - UTFPR Curitiba',
    vagasTotais: 80,
    vagasDisponiveis: 53,
    categoria: 'ACAO_SOCIAL'
  },
  {
    id: 4,
    titulo: 'Grade na Hora',
    descricao:
      'Plantão de dúvidas do DASC para auxiliar os alunos no planejamento da grade e matrícula do ' +
      'próximo semestre, com monitores de cada período.',
    data: '2026-10-24',
    horario: '13:30',
    local: 'Sala de Convivência DASC - Bloco B',
    vagasTotais: 60,
    vagasDisponiveis: 38,
    categoria: 'ATENDIMENTO'
  },
  {
    id: 5,
    titulo: 'Introdução à Inteligência Artificial',
    descricao: 'Palestra aberta sobre fundamentos de IA, aprendizado de máquina e aplicações no mercado.',
    data: '2026-10-20',
    horario: '19:00',
    local: 'Auditório Central - UTFPR Curitiba',
    vagasTotais: 120,
    vagasDisponiveis: 87,
    categoria: 'PALESTRA'
  },
  {
    id: 6,
    titulo: 'Minicurso de Angular',
    descricao:
      'Minicurso prático para criar aplicações web modernas com Angular, componentes standalone, ' +
      'rotas e consumo de APIs REST.',
    data: '2026-10-22',
    horario: '14:00',
    local: 'Laboratório 3 - Bloco B',
    vagasTotais: 30,
    vagasDisponiveis: 4,
    categoria: 'MINICURSO'
  }
];

export const ATIVIDADES_MOCK: Record<number, Atividade[]> = {
  1: [
    { id: 1, nome: 'Credenciamento', horario: '08:30', duracaoMinutos: 30 },
    { id: 2, nome: 'Palestra de abertura', tema: 'Mercado de trabalho em TI', horario: '09:00', duracaoMinutos: 60 },
    { id: 3, nome: 'Maratona de Programação', tema: 'Algoritmos e estrutura de dados', horario: '13:00', duracaoMinutos: 240 },
    { id: 4, nome: 'Oficina de Cloud', tema: 'Containers e deploy com Docker', horario: '17:30', duracaoMinutos: 90 }
  ],
  2: [
    { id: 5, nome: 'Abertura e credenciamento', horario: '09:00', duracaoMinutos: 30 },
    { id: 6, nome: 'Sessão de artigos', tema: 'Inteligência artificial aplicada', horario: '09:30', duracaoMinutos: 120 },
    { id: 7, nome: 'Mesa redonda', tema: 'Pesquisa e inovação em SI', horario: '14:00', duracaoMinutos: 90 }
  ],
  3: [
    { id: 8, nome: 'Recepção dos calouros', horario: '10:00', duracaoMinutos: 30 },
    { id: 9, nome: 'Gincana solidária', tema: 'Arrecadação de alimentos e agasalhos', horario: '10:30', duracaoMinutos: 90 },
    { id: 10, nome: 'Integração com veteranos', horario: '12:00', duracaoMinutos: 60 }
  ],
  4: [
    { id: 11, nome: 'Orientação de matrícula', tema: 'Como montar a grade ideal', horario: '13:30', duracaoMinutos: 60 },
    { id: 12, nome: 'Atendimento individual', horario: '14:30', duracaoMinutos: 90 }
  ],
  5: [
    { id: 13, nome: 'Abertura', horario: '19:00', duracaoMinutos: 15 },
    { id: 14, nome: 'Palestra principal', tema: 'IA no dia a dia', horario: '19:15', duracaoMinutos: 60 },
    { id: 15, nome: 'Perguntas e respostas', horario: '20:15', duracaoMinutos: 30 }
  ],
  6: [
    { id: 16, nome: 'Configuração do ambiente', tema: 'Node e Angular CLI', horario: '14:00', duracaoMinutos: 45 },
    { id: 17, nome: 'Componentes e rotas', horario: '14:45', duracaoMinutos: 90 }
  ]
};
