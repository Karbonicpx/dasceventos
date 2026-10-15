import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { Evento } from '../../models/evento.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-lista-eventos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './lista-eventos.component.html'
})
export class ListaEventosComponent implements OnInit {
  eventos: Evento[] = [];
  carregando = true;
  termoBusca = '';

  constructor(private eventoService: EventoService, public authService: AuthService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.eventoService.listar(false, this.termoBusca).subscribe({
      next: (eventos) => { this.eventos = eventos; this.carregando = false; },
      error: () => this.carregando = false
    });
  }

  buscar(): void {
    this.carregar();
  }

  limparBusca(): void {
    this.termoBusca = '';
    this.carregar();
  }

  rotuloCategoria(categoria: string): string {
    const rotulos: Record<string, string> = {
      PALESTRA: 'Palestra',
      MINICURSO: 'Minicurso',
      SEMANA_ACADEMICA: 'Semana Acadêmica',
      CONGRESSO: 'Congresso',
      ACAO_SOCIAL: 'Ação Social',
      ATENDIMENTO: 'Atendimento',
      OUTROS: 'Outros'
    };
    return rotulos[categoria] ?? categoria;
  }
}
