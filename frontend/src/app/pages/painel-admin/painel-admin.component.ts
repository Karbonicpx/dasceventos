import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { InscricaoService } from '../../services/inscricao.service';
import { AuthService } from '../../services/auth.service';
import { Evento, Inscricao } from '../../models/evento.model';

@Component({
  selector: 'app-painel-admin',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './painel-admin.component.html'
})
export class PainelAdminComponent implements OnInit {
  eventos: Evento[] = [];
  inscritosPorEvento: Record<number, Inscricao[]> = {};
  eventoExpandidoId?: number;

  constructor(
    private eventoService: EventoService,
    private inscricaoService: InscricaoService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.authService.ehAdministrador()) {
      this.router.navigate(['/login'], { queryParams: { tipo: 'admin' } });
      return;
    }
    this.carregarEventos();
  }

  carregarEventos(): void {
    this.eventoService.listar(true).subscribe(eventos => this.eventos = eventos);
  }

  verInscritos(evento: Evento): void {
    if (!evento.id) return;
    if (this.eventoExpandidoId === evento.id) {
      this.eventoExpandidoId = undefined;
      return;
    }
    this.eventoExpandidoId = evento.id;
    this.inscricaoService.listarPorEvento(evento.id).subscribe(
      inscritos => this.inscritosPorEvento[evento.id!] = inscritos
    );
  }

  excluir(evento: Evento): void {
    if (!evento.id) return;
    if (!confirm(`Remover definitivamente o evento "${evento.titulo}"? Essa ação não pode ser desfeita.`)) return;
    this.eventoService.excluir(evento.id).subscribe(() => this.carregarEventos());
  }

  cancelar(evento: Evento): void {
    if (!evento.id) return;
    if (!confirm(`Cancelar o evento "${evento.titulo}"? As inscrições confirmadas serão canceladas.`)) return;
    this.eventoService.cancelar(evento.id).subscribe(() => this.carregarEventos());
  }

  ehSuperAdmin(): boolean {
    return this.authService.ehSuperAdmin();
  }
}
