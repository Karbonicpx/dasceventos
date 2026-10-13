import { Routes } from '@angular/router';
import { LoginAlunoComponent } from './pages/login-aluno/login-aluno.component';
import { CadastroAlunoComponent } from './pages/cadastro-aluno/cadastro-aluno.component';
import { LoginAdminComponent } from './pages/login-admin/login-admin.component';
import { ListaEventosComponent } from './pages/lista-eventos/lista-eventos.component';
import { DetalheEventoComponent } from './pages/detalhe-evento/detalhe-evento.component';
import { PainelAdminComponent } from './pages/painel-admin/painel-admin.component';
import { CadastroEventoComponent } from './pages/cadastro-evento/cadastro-evento.component';

export const routes: Routes = [
  { path: '', redirectTo: 'eventos', pathMatch: 'full' },
  { path: 'eventos', component: ListaEventosComponent },
  { path: 'eventos/:id', component: DetalheEventoComponent },
  { path: 'login-aluno', component: LoginAlunoComponent },
  { path: 'cadastro-aluno', component: CadastroAlunoComponent },
  { path: 'login-admin', component: LoginAdminComponent },
  { path: 'admin', component: PainelAdminComponent },
  { path: 'admin/eventos/novo', component: CadastroEventoComponent },
  { path: '**', redirectTo: 'eventos' }
];
