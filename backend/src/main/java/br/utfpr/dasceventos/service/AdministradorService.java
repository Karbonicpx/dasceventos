package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.dto.AdministradorCadastroDTO;
import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.model.AdministradorToken;
import br.utfpr.dasceventos.model.PapelAdministrador;
import br.utfpr.dasceventos.repository.AdministradorRepository;
import br.utfpr.dasceventos.repository.AdministradorTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final AdministradorTokenRepository administradorTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Administrador cadastrar(AdministradorCadastroDTO dto) {
        if (administradorRepository.existsByEmail(dto.email())) {
            throw new NegocioException("Já existe um administrador cadastrado com este e-mail.");
        }
        if (administradorRepository.existsByRegistroAcademico(dto.registroAcademico())) {
            throw new NegocioException("Já existe um administrador cadastrado com este registro acadêmico.");
        }
        Administrador administrador = new Administrador();
        administrador.setNome(dto.nome());
        administrador.setEmail(dto.email());
        administrador.setRegistroAcademico(dto.registroAcademico());
        administrador.setSenha(passwordEncoder.encode(dto.senha()));
        administrador.setPapel(PapelAdministrador.DIRETORIA);
        administrador.setAprovado(false);
        return administradorRepository.save(administrador);
    }

    /**
     * Autentica e abre uma nova sessão (token) para o administrador. Cada login cria o seu
     * próprio token, independente dos demais - entrar em outro lugar não derruba quem já
     * estava logado.
     */
    @Transactional
    public AdministradorToken autenticar(String email, String senha) {
        Administrador admin = administradorRepository.findByEmail(email)
                .orElseThrow(() -> new NegocioException("Conta não identificada."));
        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new NegocioException("Senha inválida.");
        }
        if (!admin.isAprovado()) {
            throw new NegocioException("Sua conta ainda está aguardando validação da diretoria do DASC.");
        }
        AdministradorToken sessao = new AdministradorToken();
        sessao.setToken(UUID.randomUUID().toString());
        sessao.setAdministrador(admin);
        return administradorTokenRepository.save(sessao);
    }

    @Transactional
    public void logout(String token) {
        administradorTokenRepository.deleteByToken(token);
    }

    public Optional<Administrador> buscarPorToken(String token) {
        return administradorTokenRepository.findByToken(token).map(AdministradorToken::getAdministrador);
    }

    public Administrador buscarPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrador não encontrado."));
    }

    public List<Administrador> listarPendentes() {
        return administradorRepository.findByAprovadoFalse();
    }

    @Transactional
    public Administrador aprovar(Long id) {
        Administrador administrador = buscarPorId(id);
        administrador.setAprovado(true);
        return administradorRepository.save(administrador);
    }

    @Transactional
    public Administrador atualizarFoto(Long id, String fotoPerfilBase64) {
        Administrador administrador = buscarPorId(id);
        administrador.setFotoPerfil(fotoPerfilBase64);
        return administradorRepository.save(administrador);
    }
}
