import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { AdministradorCadastro, AdministradorResposta, AlunoCadastro, AlunoResposta } from '../../models/aluno.model';

type TipoConta = 'ALUNO' | 'ADMIN';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cadastro.component.html'
})
export class CadastroComponent implements OnInit {
  tipo: TipoConta = 'ALUNO';
  dtoAluno: AlunoCadastro = { nome: '', email: '', curso: '', registroAcademico: '', telefone: '', senha: '' };
  dtoAdmin: AdministradorCadastro = { nome: '', email: '', registroAcademico: '', senha: '' };
  erro = '';
  sucesso = false;

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

  cadastrar(form: NgForm): void {
    this.erro = '';
    if (form.invalid) {
      this.erro = 'Preencha os campos destacados em vermelho antes de continuar.';
      return;
    }
    const requisicao: Observable<AlunoResposta | AdministradorResposta> = this.tipo === 'ALUNO'
      ? this.authService.cadastrarAluno(this.dtoAluno)
      : this.authService.cadastrarAdministrador(this.dtoAdmin);

    requisicao.subscribe({
      next: () => {
        this.sucesso = true;
        if (this.tipo === 'ALUNO') {
          // Aluno já pode entrar na hora - segue automaticamente para o login.
          setTimeout(() => this.router.navigate(['/login']), 1200);
        }
        // Diretoria precisa aguardar aprovação: fica na tela, sem redirecionar sozinho,
        // até a pessoa ler o aviso e clicar para ir ao login.
      },
      error: (err) => this.erro = err.error?.mensagem ?? 'Não foi possível concluir o cadastro.'
    });
  }
}
