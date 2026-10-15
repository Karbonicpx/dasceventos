import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { EventoService } from '../../services/evento.service';
import { Atividade, Evento } from '../../models/evento.model';

const TAMANHO_MAXIMO_BANNER = 1.5 * 1024 * 1024;

@Component({
  selector: 'app-cadastro-evento',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cadastro-evento.component.html'
})
export class CadastroEventoComponent implements OnInit {
  evento: Evento = {
    titulo: '',
    descricao: '',
    data: '',
    horario: '',
    local: '',
    vagasTotais: 50,
    categoria: 'PALESTRA'
  };
  modoEdicao = false;
  erro = '';
  erroBanner = '';
  abaAtiva: 'INFO' | 'ATIVIDADES' = 'INFO';

  atividades: Atividade[] = [];
  novaAtividade: Atividade = { nome: '', tema: '', horario: '', duracaoMinutos: 30 };
  erroAtividade = '';
  atividadeEnviada = false;

  constructor(
    private eventoService: EventoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.modoEdicao = true;
      this.eventoService.buscar(id).subscribe(evento => this.evento = evento);
      this.carregarAtividades(id);
    }
  }

  get banners(): (string | undefined)[] {
    return [this.evento.fotoBanner1, this.evento.fotoBanner2, this.evento.fotoBanner3];
  }

  selecionarBanner(event: Event, indice: number): void {
    const input = event.target as HTMLInputElement;
    const arquivo = input.files?.[0];
    if (!arquivo) return;

    if (arquivo.size > TAMANHO_MAXIMO_BANNER) {
      this.erroBanner = 'Cada imagem do banner deve ter no máximo 1.5 MB.';
      input.value = '';
      return;
    }

    this.erroBanner = '';
    const leitor = new FileReader();
    leitor.onload = () => {
      const base64 = leitor.result as string;
      if (indice === 0) this.evento.fotoBanner1 = base64;
      else if (indice === 1) this.evento.fotoBanner2 = base64;
      else this.evento.fotoBanner3 = base64;
    };
    leitor.readAsDataURL(arquivo);
  }

  removerBanner(indice: number): void {
    if (indice === 0) this.evento.fotoBanner1 = undefined;
    else if (indice === 1) this.evento.fotoBanner2 = undefined;
    else this.evento.fotoBanner3 = undefined;
  }

  horarioAtividadeAntesDoEvento(): boolean {
    return !!this.novaAtividade.horario && !!this.evento.horario && this.novaAtividade.horario < this.evento.horario;
  }

  salvar(form: NgForm): void {
    this.erro = '';
    if (form.invalid) {
      this.erro = 'Preencha os campos destacados em vermelho antes de salvar.';
      return;
    }

    const requisicao = this.modoEdicao && this.evento.id
      ? this.eventoService.atualizar(this.evento.id, this.evento)
      : this.eventoService.cadastrar(this.evento);

    requisicao.subscribe({
      next: (evento) => {
        if (!this.modoEdicao && evento.id) {
          this.router.navigate(['/admin/eventos', evento.id, 'editar']);
        } else {
          this.router.navigate(['/admin']);
        }
      },
      error: (err) => this.erro = this.mensagemErro(err)
    });
  }

  carregarAtividades(eventoId: number): void {
    this.eventoService.listarAtividades(eventoId).subscribe(atividades => this.atividades = atividades);
  }

  adicionarAtividade(form: NgForm): void {
    this.atividadeEnviada = true;
    this.erroAtividade = '';
    if (form.invalid || !this.evento.id) {
      this.erroAtividade = 'Preencha os campos destacados em vermelho antes de adicionar.';
      return;
    }
    if (this.horarioAtividadeAntesDoEvento()) {
      this.erroAtividade = `O horário da atividade deve ser igual ou posterior ao início do evento (${this.evento.horario}).`;
      return;
    }
    this.eventoService.cadastrarAtividade(this.evento.id, this.novaAtividade).subscribe({
      next: () => {
        this.novaAtividade = { nome: '', tema: '', horario: '', duracaoMinutos: 30 };
        this.atividadeEnviada = false;
        this.carregarAtividades(this.evento.id!);
      },
      error: (err) => this.erroAtividade = this.mensagemErro(err)
    });
  }

  removerAtividade(atividade: Atividade): void {
    if (!this.evento.id || !atividade.id) return;
    this.eventoService.excluirAtividade(this.evento.id, atividade.id).subscribe(
      () => this.carregarAtividades(this.evento.id!)
    );
  }

  private mensagemErro(err: { status?: number; error?: { mensagem?: string } }): string {
    if (err.status === 401 || err.status === 403) {
      return 'Sua sessão da diretoria expirou ou não tem permissão para esta ação. Saia e entre novamente.';
    }
    return err.error?.mensagem ?? 'Não foi possível salvar. Tente novamente.';
  }
}
