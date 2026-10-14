import { HttpInterceptorFn, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { ATIVIDADES_MOCK, EVENTOS_MOCK, USAR_MOCK } from './eventos.mock';

// Intercepta as chamadas GET de eventos e devolve dados fictícios.
export const mockInterceptor: HttpInterceptorFn = (req, next) => {
  if (!USAR_MOCK || req.method !== 'GET') return next(req);

  const caminho = new URL(req.url).pathname;

  if (caminho === '/api/eventos') {
    return of(new HttpResponse({ status: 200, body: EVENTOS_MOCK }));
  }

  const atividades = caminho.match(/^\/api\/eventos\/(\d+)\/atividades$/);
  if (atividades) {
    return of(new HttpResponse({ status: 200, body: ATIVIDADES_MOCK[Number(atividades[1])] ?? [] }));
  }

  const evento = caminho.match(/^\/api\/eventos\/(\d+)$/);
  if (evento) {
    const encontrado = EVENTOS_MOCK.find(e => e.id === Number(evento[1]));
    if (encontrado) return of(new HttpResponse({ status: 200, body: encontrado }));
  }

  if (/^\/api\/inscricoes\/(aluno|evento)\/\d+$/.test(caminho)) {
    return of(new HttpResponse({ status: 200, body: [] }));
  }

  return next(req);
};
