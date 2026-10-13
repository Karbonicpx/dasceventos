import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-aluno',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login-aluno.component.html'
})
export class LoginAlunoComponent {
  email = '';
  senha = '';
  erro = '';

  constructor(private authService: AuthService, private router: Router) {}

  entrar(): void {
    this.erro = '';
    this.authService.loginAluno({ email: this.email, senha: this.senha }).subscribe({
      next: () => this.router.navigate(['/eventos']),
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível entrar.'
    });
  }
}
