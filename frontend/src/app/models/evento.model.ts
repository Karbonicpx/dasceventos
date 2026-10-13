export type CategoriaEvento = 'PALESTRA' | 'MINICURSO' | 'SECOMP_MAIS' | 'COINTER' | 'TROTE_SOLIDARIO';

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
  eventoId: number;
  eventoTitulo: string;
  dataInscricao: string;
  status: 'CONFIRMADA' | 'CANCELADA';
}
