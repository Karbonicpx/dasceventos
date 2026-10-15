export type CategoriaEvento = 'PALESTRA' | 'MINICURSO' | 'SEMANA_ACADEMICA' | 'CONGRESSO' | 'ACAO_SOCIAL' | 'ATENDIMENTO' | 'OUTROS';
export type StatusEvento = 'ATIVO' | 'CANCELADO';

export interface Evento {
  id?: number;
  titulo: string;
  descricao?: string;
  data: string;
  horario: string;
  local: string;
  vagasTotais: number;
  vagasDisponiveis?: number;
  categoria: CategoriaEvento;
  status?: StatusEvento;
  fotoBanner1?: string;
  fotoBanner2?: string;
  fotoBanner3?: string;
}

export interface Atividade {
  id?: number;
  nome: string;
  tema?: string;
  horario: string;
  duracaoMinutos: number;
}

export interface Inscricao {
  id: number;
  alunoId: number;
  alunoNome: string;
  alunoRegistroAcademico?: string;
  alunoFotoPerfil?: string;
  eventoId: number;
  eventoTitulo: string;
  dataInscricao: string;
  status: 'CONFIRMADA' | 'CANCELADA';
}
