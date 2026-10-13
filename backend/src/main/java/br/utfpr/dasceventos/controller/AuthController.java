package br.utfpr.dasceventos.controller;

import br.utfpr.dasceventos.dto.AlunoCadastroDTO;
import br.utfpr.dasceventos.dto.AlunoRespostaDTO;
import br.utfpr.dasceventos.dto.LoginDTO;
import br.utfpr.dasceventos.dto.LoginRespostaDTO;
import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.model.Aluno;
import br.utfpr.dasceventos.service.AdministradorService;
import br.utfpr.dasceventos.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AlunoService alunoService;
    private final AdministradorService administradorService;

    @PostMapping("/cadastro-aluno")
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoRespostaDTO cadastrarAluno(@Valid @RequestBody AlunoCadastroDTO dto) {
        Aluno aluno = alunoService.cadastrar(dto);
        return AlunoRespostaDTO.fromEntity(aluno);
    }

    @PostMapping("/login-aluno")
    public LoginRespostaDTO loginAluno(@Valid @RequestBody LoginDTO dto) {
        Aluno aluno = alunoService.autenticar(dto.email(), dto.senha());
        return new LoginRespostaDTO(aluno.getId(), aluno.getNome(), aluno.getEmail(), "ALUNO");
    }

    @PostMapping("/login-admin")
    public LoginRespostaDTO loginAdmin(@Valid @RequestBody LoginDTO dto) {
        Administrador admin = administradorService.autenticar(dto.email(), dto.senha());
        return new LoginRespostaDTO(admin.getId(), admin.getNome(), admin.getEmail(), "ADMINISTRADOR");
    }
}
