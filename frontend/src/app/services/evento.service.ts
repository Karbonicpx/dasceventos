import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Atividade, Evento } from '../models/evento.model';

const API = 'http://localhost:8080/api/eventos';

@Injectable({ providedIn: 'root' })
export class EventoService {

  constructor(private http: HttpClient) {}

  listar(todos = false, busca?: string): Observable<Evento[]> {
    const params: Record<string, string | boolean> = { todos };
    if (busca) params['busca'] = busca;
    return this.http.get<Evento[]>(API, { params });
  }

  buscar(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${API}/${id}`);
  }

  cadastrar(evento: Evento): Observable<Evento> {
    return this.http.post<Evento>(API, evento);
  }

  atualizar(id: number, evento: Evento): Observable<Evento> {
    return this.http.put<Evento>(`${API}/${id}`, evento);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${API}/${id}`);
  }

  cancelar(id: number): Observable<Evento> {
    return this.http.post<Evento>(`${API}/${id}/cancelar`, {});
  }

  listarAtividades(eventoId: number): Observable<Atividade[]> {
    return this.http.get<Atividade[]>(`${API}/${eventoId}/atividades`);
  }

  cadastrarAtividade(eventoId: number, atividade: Atividade): Observable<Atividade> {
    return this.http.post<Atividade>(`${API}/${eventoId}/atividades`, atividade);
  }

  excluirAtividade(eventoId: number, atividadeId: number): Observable<void> {
    return this.http.delete<void>(`${API}/${eventoId}/atividades/${atividadeId}`);
  }
}
