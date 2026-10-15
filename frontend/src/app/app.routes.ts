import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { CadastroComponent } from './pages/cadastro/cadastro.component';
import { ListaEventosComponent } from './pages/lista-eventos/lista-eventos.component';
import { DetalheEventoComponent } from './pages/detalhe-evento/detalhe-evento.component';
import { PainelAdminComponent } from './pages/painel-admin/painel-admin.component';
import { CadastroEventoComponent } from './pages/cadastro-evento/cadastro-evento.component';
import { ValidarContasComponent } from './pages/validar-contas/validar-contas.component';
import { MinhasInscricoesComponent } from './pages/minhas-inscricoes/minhas-inscricoes.component';
import { ContaComponent } from './pages/conta/conta.component';

export const routes: Routes = [
  { path: '', redirectTo: 'eventos', pathMatch: 'full' },
  { path: 'eventos', component: ListaEventosComponent },
  { path: 'eventos/:id', component: DetalheEventoComponent },
  { path: 'login', component: LoginComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'conta', component: ContaComponent },
  { path: 'minhas-inscricoes', component: MinhasInscricoesComponent },
  { path: 'admin', component: PainelAdminComponent },
  { path: 'admin/validacoes', component: ValidarContasComponent },
  { path: 'admin/eventos/novo', component: CadastroEventoComponent },
  { path: 'admin/eventos/:id/editar', component: CadastroEventoComponent },
  { path: '**', redirectTo: 'eventos' }
];
