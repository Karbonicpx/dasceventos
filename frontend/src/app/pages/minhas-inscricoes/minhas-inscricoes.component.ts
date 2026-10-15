import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { InscricaoService } from '../../services/inscricao.service';
import { Inscricao } from '../../models/evento.model';

@Component({
  selector: 'app-minhas-inscricoes',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './minhas-inscricoes.component.html'
})
export class MinhasInscricoesComponent implements OnInit {
  inscricoes: Inscricao[] = [];
  carregando = true;

  constructor(
    private authService: AuthService,
    private inscricaoService: InscricaoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.usuarioAtual();
    if (!usuario || usuario.tipo !== 'ALUNO') {
      this.router.navigate(['/login']);
      return;
    }
    this.carregar(usuario.id);
  }

  carregar(alunoId: number): void {
    this.carregando = true;
    this.inscricaoService.listarPorAluno(alunoId).subscribe({
      next: (inscricoes) => {
        this.inscricoes = inscricoes.sort((a, b) => b.dataInscricao.localeCompare(a.dataInscricao));
        this.carregando = false;
      },
      error: () => this.carregando = false
    });
  }

  cancelar(inscricao: Inscricao): void {
    const usuario = this.authService.usuarioAtual();
    if (!usuario) return;
    if (!confirm(`Cancelar sua inscrição em "${inscricao.eventoTitulo}"?`)) return;
    this.inscricaoService.cancelar(usuario.id, inscricao.eventoId).subscribe(() => this.carregar(usuario.id));
  }
}
