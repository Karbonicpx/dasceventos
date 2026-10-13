package br.utfpr.dasceventos.service;

import br.utfpr.dasceventos.dto.AlunoCadastroDTO;
import br.utfpr.dasceventos.model.Aluno;
import br.utfpr.dasceventos.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Aluno cadastrar(AlunoCadastroDTO dto) {
        if (alunoRepository.existsByEmail(dto.email())) {
            throw new NegocioException("Já existe um aluno cadastrado com este e-mail.");
        }
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setCurso(dto.curso());
        aluno.setTelefone(dto.telefone());
        aluno.setSenha(passwordEncoder.encode(dto.senha()));
        return alunoRepository.save(aluno);
    }

    public Aluno autenticar(String email, String senha) {
        Aluno aluno = alunoRepository.findByEmail(email)
                .orElseThrow(() -> new NegocioException("Conta não identificada."));
        if (!passwordEncoder.matches(senha, aluno.getSenha())) {
            throw new NegocioException("Senha inválida.");
        }
        return aluno;
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado."));
    }
}
