export interface AlunoCadastro {
  nome: string;
  email: string;
  curso: string;
  telefone?: string;
  senha: string;
}

export interface AlunoResposta {
  id: number;
  nome: string;
  email: string;
  curso: string;
  telefone?: string;
  dataCadastro: string;
}

export interface LoginRequest {
  email: string;
  senha: string;
}

export interface LoginResposta {
  id: number;
  nome: string;
  email: string;
  tipo: 'ALUNO' | 'ADMINISTRADOR';
}
