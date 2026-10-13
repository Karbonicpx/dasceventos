import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { InscricaoService } from '../../services/inscricao.service';
import { AuthService } from '../../services/auth.service';
import { Atividade, Evento, Inscricao } from '../../models/evento.model';

@Component({
  selector: 'app-detalhe-evento',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './detalhe-evento.component.html'
})
export class DetalheEventoComponent implements OnInit {
  evento?: Evento;
  atividades: Atividade[] = [];
  minhaInscricao?: Inscricao;
  mensagem = '';
  erro = '';

  constructor(
    private route: ActivatedRoute,
    private eventoService: EventoService,
    private inscricaoService: InscricaoService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventoService.buscar(id).subscribe(evento => this.evento = evento);
    this.eventoService.listarAtividades(id).subscribe(atividades => this.atividades = atividades);

    const usuario = this.authService.usuarioAtual();
    if (usuario && usuario.tipo === 'ALUNO') {
      this.inscricaoService.listarPorAluno(usuario.id).subscribe(inscricoes => {
        this.minhaInscricao = inscricoes.find(i => i.eventoId === id && i.status === 'CONFIRMADA');
      });
    }
  }

  inscrever(): void {
    const usuario = this.authService.usuarioAtual();
    if (!usuario || !this.evento?.id) return;
    this.erro = '';
    this.inscricaoService.inscrever(usuario.id, this.evento.id).subscribe({
      next: (inscricao) => {
        this.minhaInscricao = inscricao;
        this.mensagem = 'Inscrição confirmada!';
        if (this.evento) this.evento.vagasDisponiveis = (this.evento.vagasDisponiveis ?? 1) - 1;
      },
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível concluir a inscrição.'
    });
  }

  cancelar(): void {
    const usuario = this.authService.usuarioAtual();
    if (!usuario || !this.evento?.id) return;
    this.inscricaoService.cancelar(usuario.id, this.evento.id).subscribe({
      next: () => {
        this.minhaInscricao = undefined;
        this.mensagem = 'Inscrição cancelada.';
        if (this.evento) this.evento.vagasDisponiveis = (this.evento.vagasDisponiveis ?? 0) + 1;
      },
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível cancelar a inscrição.'
    });
  }
}
