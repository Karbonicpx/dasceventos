package br.utfpr.dasceventos.config;

import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.model.PapelAdministrador;
import br.utfpr.dasceventos.repository.AdministradorTokenRepository;
import br.utfpr.dasceventos.service.AcessoNegadoException;
import br.utfpr.dasceventos.service.NaoAutenticadoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Protege as rotas de gestão de eventos (criar/editar/remover/cancelar evento, cadastrar/remover
 * atividade, ver inscritos) e as rotas de validação de contas da diretoria, exigindo um token de
 * administrador aprovado no cabeçalho Authorization. Leitura (GET) de eventos/atividades continua
 * pública, já que alunos também precisam consultá-las.
 */
@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final AdministradorTokenRepository administradorTokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String metodo = request.getMethod();
        String uri = request.getRequestURI();

        // Preflight do CORS nunca carrega o header Authorization - precisa passar livre para
        // o navegador conseguir ver os headers de CORS e então enviar a requisição de verdade.
        if ("OPTIONS".equalsIgnoreCase(metodo)) {
            return true;
        }

        boolean apenasSuperAdmin = uri.equals("/api/auth/admin/pendentes") || uri.matches("/api/auth/admin/\\d+/aprovar");
        boolean protegido = apenasSuperAdmin
                || (uri.startsWith("/api/eventos") && !"GET".equalsIgnoreCase(metodo))
                || uri.matches("/api/inscricoes/evento/\\d+");

        if (!protegido) {
            return true;
        }

        Administrador administrador = extrairAdministrador(request);
        if (administrador == null || !administrador.isAprovado()) {
            throw new NaoAutenticadoException("Acesso restrito à diretoria do DASC.");
        }
        if (apenasSuperAdmin && administrador.getPapel() != PapelAdministrador.SUPERADMIN) {
            throw new AcessoNegadoException("Apenas o superadmin pode validar contas da diretoria.");
        }
        return true;
    }

    private Administrador extrairAdministrador(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        String token = header.substring("Bearer ".length());
        return administradorTokenRepository.findByToken(token).map(t -> t.getAdministrador()).orElse(null);
    }
}
