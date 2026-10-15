import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

type TipoConta = 'ALUNO' | 'ADMIN';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  tipo: TipoConta = 'ALUNO';
  email = '';
  senha = '';
  erro = '';

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    if (this.route.snapshot.queryParamMap.get('tipo') === 'admin') {
      this.tipo = 'ADMIN';
    }
  }

  escolher(tipo: TipoConta): void {
    this.tipo = tipo;
    this.erro = '';
  }

  entrar(): void {
    this.erro = '';
    const dto = { email: this.email, senha: this.senha };
    const requisicao = this.tipo === 'ALUNO' ? this.authService.loginAluno(dto) : this.authService.loginAdmin(dto);
    requisicao.subscribe({
      next: () => this.router.navigate([this.tipo === 'ALUNO' ? '/eventos' : '/admin']),
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível entrar.'
    });
  }
}
