import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { AdministradorResposta } from '../../models/aluno.model';

@Component({
  selector: 'app-validar-contas',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './validar-contas.component.html'
})
export class ValidarContasComponent implements OnInit {
  pendentes: AdministradorResposta[] = [];
  carregando = true;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    if (!this.authService.ehSuperAdmin()) {
      this.router.navigate(['/admin']);
      return;
    }
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.authService.listarPendentes().subscribe({
      next: (pendentes) => { this.pendentes = pendentes; this.carregando = false; },
      error: () => this.carregando = false
    });
  }

  aprovar(admin: AdministradorResposta): void {
    this.authService.aprovarAdmin(admin.id).subscribe(() => this.carregar());
  }
}
