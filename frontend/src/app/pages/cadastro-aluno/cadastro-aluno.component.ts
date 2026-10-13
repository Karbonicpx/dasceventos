import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AlunoCadastro } from '../../models/aluno.model';

@Component({
  selector: 'app-cadastro-aluno',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cadastro-aluno.component.html'
})
export class CadastroAlunoComponent {
  dto: AlunoCadastro = { nome: '', email: '', curso: '', telefone: '', senha: '' };
  erro = '';
  sucesso = false;

  constructor(private authService: AuthService, private router: Router) {}

  cadastrar(): void {
    this.erro = '';
    this.authService.cadastrarAluno(this.dto).subscribe({
      next: () => {
        this.sucesso = true;
        setTimeout(() => this.router.navigate(['/login-aluno']), 1200);
      },
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível concluir o cadastro.'
    });
  }
}
