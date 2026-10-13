import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login-admin.component.html'
})
export class LoginAdminComponent {
  email = '';
  senha = '';
  erro = '';

  constructor(private authService: AuthService, private router: Router) {}

  entrar(): void {
    this.erro = '';
    this.authService.loginAdmin({ email: this.email, senha: this.senha }).subscribe({
      next: () => this.router.navigate(['/admin']),
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível entrar.'
    });
  }
}
