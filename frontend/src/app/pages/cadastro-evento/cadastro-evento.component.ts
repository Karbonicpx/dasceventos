import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { Evento } from '../../models/evento.model';

@Component({
  selector: 'app-cadastro-evento',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cadastro-evento.component.html'
})
export class CadastroEventoComponent {
  evento: Evento = {
    titulo: '',
    descricao: '',
    data: '',
    horario: '',
    local: '',
    vagasTotais: 50,
    categoria: 'PALESTRA'
  };
  erro = '';

  constructor(private eventoService: EventoService, private router: Router) {}

  salvar(): void {
    this.erro = '';
    this.eventoService.cadastrar(this.evento).subscribe({
      next: () => this.router.navigate(['/admin']),
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível cadastrar o evento.'
    });
  }
}
