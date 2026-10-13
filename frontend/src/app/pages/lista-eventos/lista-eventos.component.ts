import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { Evento } from '../../models/evento.model';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-lista-eventos',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './lista-eventos.component.html'
})
export class ListaEventosComponent implements OnInit {
  eventos: Evento[] = [];
  carregando = true;

  constructor(private eventoService: EventoService, public authService: AuthService) {}

  ngOnInit(): void {
    this.eventoService.listar().subscribe({
      next: (eventos) => { this.eventos = eventos; this.carregando = false; },
      error: () => this.carregando = false
    });
  }

  rotuloCategoria(categoria: string): string {
    const rotulos: Record<string, string> = {
      PALESTRA: 'Palestra',
      MINICURSO: 'Minicurso',
      SECOMP_MAIS: 'SECOMP+',
      COINTER: 'COINTER',
      TROTE_SOLIDARIO: 'Trote Solidário'
    };
    return rotulos[categoria] ?? categoria;
  }
}
