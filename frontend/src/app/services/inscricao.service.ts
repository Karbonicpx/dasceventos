import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inscricao } from '../models/evento.model';

const API = 'http://localhost:8080/api/inscricoes';

@Injectable({ providedIn: 'root' })
export class InscricaoService {

  constructor(private http: HttpClient) {}

  inscrever(alunoId: number, eventoId: number): Observable<Inscricao> {
    return this.http.post<Inscricao>(API, null, { params: { alunoId, eventoId } });
  }

  cancelar(alunoId: number, eventoId: number): Observable<void> {
    return this.http.delete<void>(API, { params: { alunoId, eventoId } });
  }

  listarPorAluno(alunoId: number): Observable<Inscricao[]> {
    return this.http.get<Inscricao[]>(`${API}/aluno/${alunoId}`);
  }

  listarPorEvento(eventoId: number): Observable<Inscricao[]> {
    return this.http.get<Inscricao[]>(`${API}/evento/${eventoId}`);
  }
}
