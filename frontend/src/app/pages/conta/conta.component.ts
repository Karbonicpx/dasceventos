import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth.service';
import { AdministradorResposta, AlunoResposta, LoginResposta } from '../../models/aluno.model';

const TAMANHO_MAXIMO_BYTES = 1.5 * 1024 * 1024;

@Component({
  selector: 'app-conta',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './conta.component.html'
})
export class ContaComponent implements OnInit {
  usuario: LoginResposta | null = null;
  perfilAluno?: AlunoResposta;
  perfilAdmin?: AdministradorResposta;
  enviando = false;
  erro = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.usuario = this.authService.usuarioAtual();
    if (!this.usuario) {
      this.router.navigate(['/login']);
      return;
    }
    if (this.usuario.tipo === 'ALUNO') {
      this.authService.buscarAluno(this.usuario.id).subscribe(perfil => this.perfilAluno = perfil);
    } else {
      this.authService.buscarAdmin(this.usuario.id).subscribe(perfil => this.perfilAdmin = perfil);
    }
  }

  fotoAtual(): string | undefined {
    return this.perfilAluno?.fotoPerfil ?? this.perfilAdmin?.fotoPerfil;
  }

  iniciais(): string {
    const nome = this.perfilAluno?.nome ?? this.perfilAdmin?.nome ?? this.usuario?.nome ?? '';
    return nome.trim().charAt(0).toUpperCase() || '?';
  }

  selecionarFoto(event: Event): void {
    const input = event.target as HTMLInputElement;
    const arquivo = input.files?.[0];
    if (!arquivo || !this.usuario) return;

    if (arquivo.size > TAMANHO_MAXIMO_BYTES) {
      this.erro = 'A imagem deve ter no máximo 1.5 MB.';
      input.value = '';
      return;
    }

    this.erro = '';
    const leitor = new FileReader();
    leitor.onload = () => {
      const base64 = leitor.result as string;
      this.enviando = true;

      const requisicao: Observable<AlunoResposta | AdministradorResposta> = this.usuario!.tipo === 'ALUNO'
        ? this.authService.atualizarFotoAluno(this.usuario!.id, base64)
        : this.authService.atualizarFotoAdmin(this.usuario!.id, base64);

      requisicao.subscribe({
        next: (perfil) => {
          this.enviando = false;
          if (this.usuario!.tipo === 'ALUNO') this.perfilAluno = perfil as AlunoResposta;
          else this.perfilAdmin = perfil as AdministradorResposta;
          this.authService.atualizarFotoNaSessao(base64);
        },
        error: () => {
          this.enviando = false;
          this.erro = 'Não foi possível enviar a foto. Tente novamente.';
        }
      });
    };
    leitor.readAsDataURL(arquivo);
  }
}
