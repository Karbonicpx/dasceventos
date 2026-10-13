import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AlunoCadastro, AlunoResposta, LoginRequest, LoginResposta } from '../models/aluno.model';

const API = 'http://localhost:8080/api/auth';
const STORAGE_KEY = 'dasceventos_usuario';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  cadastrarAluno(dto: AlunoCadastro): Observable<AlunoResposta> {
    return this.http.post<AlunoResposta>(`${API}/cadastro-aluno`, dto);
  }

  loginAluno(dto: LoginRequest): Observable<LoginResposta> {
    return this.http.post<LoginResposta>(`${API}/login-aluno`, dto).pipe(
      tap(resposta => this.salvarSessao(resposta))
    );
  }

  loginAdmin(dto: LoginRequest): Observable<LoginResposta> {
    return this.http.post<LoginResposta>(`${API}/login-admin`, dto).pipe(
      tap(resposta => this.salvarSessao(resposta))
    );
  }

  logout(): void {
    localStorage.removeItem(STORAGE_KEY);
  }

  usuarioAtual(): LoginResposta | null {
    const raw = localStorage.getItem(STORAGE_KEY);
    return raw ? JSON.parse(raw) : null;
  }

  estaLogado(): boolean {
    return this.usuarioAtual() !== null;
  }

  ehAdministrador(): boolean {
    return this.usuarioAtual()?.tipo === 'ADMINISTRADOR';
  }

  private salvarSessao(resposta: LoginResposta): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(resposta));
  }
}
