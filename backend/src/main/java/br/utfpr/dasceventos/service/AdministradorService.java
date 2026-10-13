package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final PasswordEncoder passwordEncoder;

    public Administrador autenticar(String email, String senha) {
        Administrador admin = administradorRepository.findByEmail(email)
                .orElseThrow(() -> new NegocioException("Conta não identificada."));
        if (!passwordEncoder.matches(senha, admin.getSenha())) {
            throw new NegocioException("Senha inválida.");
        }
        return admin;
    }
}
