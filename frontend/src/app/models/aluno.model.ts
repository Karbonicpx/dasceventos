export interface AlunoCadastro {
  nome: string;
  email: string;
  curso: string;
  registroAcademico: string;
  telefone?: string;
  senha: string;
}

export interface AlunoResposta {
  id: number;
  nome: string;
  email: string;
  curso: string;
  registroAcademico: string;
  telefone?: string;
  fotoPerfil?: string;
  dataCadastro: string;
}

export interface AdministradorCadastro {
  nome: string;
  email: string;
  registroAcademico: string;
  senha: string;
}

export interface AdministradorResposta {
  id: number;
  nome: string;
  email: string;
  registroAcademico: string;
  papel: 'SUPERADMIN' | 'DIRETORIA';
  aprovado: boolean;
  fotoPerfil?: string;
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
  papel?: 'SUPERADMIN' | 'DIRETORIA';
  fotoPerfil?: string;
  token?: string;
}
