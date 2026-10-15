import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import {
  AdministradorCadastro, AdministradorResposta, AlunoCadastro, AlunoResposta, LoginRequest, LoginResposta
} from '../models/aluno.model';

const API = 'http://localhost:8080/api/auth';
const STORAGE_KEY = 'dasceventos_usuario';

@Injectable({ providedIn: 'root' })
export class AuthService {

  constructor(private http: HttpClient) {}

  cadastrarAluno(dto: AlunoCadastro): Observable<AlunoResposta> {
    return this.http.post<AlunoResposta>(`${API}/cadastro-aluno`, dto);
  }

  buscarAluno(id: number): Observable<AlunoResposta> {
    return this.http.get<AlunoResposta>(`${API}/aluno/${id}`);
  }

  atualizarFotoAluno(id: number, fotoPerfil: string): Observable<AlunoResposta> {
    return this.http.put<AlunoResposta>(`${API}/aluno/${id}/foto`, { fotoPerfil });
  }

  cadastrarAdministrador(dto: AdministradorCadastro): Observable<AdministradorResposta> {
    return this.http.post<AdministradorResposta>(`${API}/cadastro-admin`, dto);
  }

  buscarAdmin(id: number): Observable<AdministradorResposta> {
    return this.http.get<AdministradorResposta>(`${API}/admin/${id}`);
  }

  atualizarFotoAdmin(id: number, fotoPerfil: string): Observable<AdministradorResposta> {
    return this.http.put<AdministradorResposta>(`${API}/admin/${id}/foto`, { fotoPerfil });
  }

  listarPendentes(): Observable<AdministradorResposta[]> {
    return this.http.get<AdministradorResposta[]>(`${API}/admin/pendentes`);
  }

  aprovarAdmin(id: number): Observable<AdministradorResposta> {
    return this.http.post<AdministradorResposta>(`${API}/admin/${id}/aprovar`, {});
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
    const usuario = this.usuarioAtual();
    if (usuario?.tipo === 'ADMINISTRADOR' && usuario.token) {
      // Invalida a sessão no backend também; não bloqueia o logout local se falhar.
      this.http.post(`${API}/logout`, {}).subscribe({ next: () => {}, error: () => {} });
    }
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

  ehSuperAdmin(): boolean {
    return this.usuarioAtual()?.papel === 'SUPERADMIN';
  }

  atualizarFotoNaSessao(fotoPerfil: string): void {
    const usuario = this.usuarioAtual();
    if (!usuario) return;
    this.salvarSessao({ ...usuario, fotoPerfil });
  }

  private salvarSessao(resposta: LoginResposta): void {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(resposta));
  }
}
