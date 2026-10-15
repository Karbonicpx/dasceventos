import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

// Anexa o token de sessão da diretoria (quando houver) nas chamadas à API,
// necessário para as rotas administrativas protegidas no backend.
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const usuario = authService.usuarioAtual();

  if (usuario?.token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${usuario.token}` } });
  }

  return next(req);
};
