package br.utfpr.dasceventos.controller;

import br.utfpr.dasceventos.dto.AdministradorCadastroDTO;
import br.utfpr.dasceventos.dto.AdministradorRespostaDTO;
import br.utfpr.dasceventos.dto.AlunoCadastroDTO;
import br.utfpr.dasceventos.dto.AlunoRespostaDTO;
import br.utfpr.dasceventos.dto.FotoDTO;
import br.utfpr.dasceventos.dto.LoginDTO;
import br.utfpr.dasceventos.dto.LoginRespostaDTO;
import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.model.AdministradorToken;
import br.utfpr.dasceventos.model.Aluno;
import br.utfpr.dasceventos.service.AdministradorService;
import br.utfpr.dasceventos.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new LoginRespostaDTO(aluno.getId(), aluno.getNome(), aluno.getEmail(), "ALUNO", null, aluno.getFotoPerfil(), null);
    }

    @GetMapping("/aluno/{id}")
    public AlunoRespostaDTO buscarAluno(@PathVariable Long id) {
        return AlunoRespostaDTO.fromEntity(alunoService.buscarPorId(id));
    }

    @PutMapping("/aluno/{id}/foto")
    public AlunoRespostaDTO atualizarFotoAluno(@PathVariable Long id, @Valid @RequestBody FotoDTO dto) {
        return AlunoRespostaDTO.fromEntity(alunoService.atualizarFoto(id, dto.fotoPerfil()));
    }

    @PostMapping("/cadastro-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public AdministradorRespostaDTO cadastrarAdministrador(@Valid @RequestBody AdministradorCadastroDTO dto) {
        Administrador administrador = administradorService.cadastrar(dto);
        return AdministradorRespostaDTO.fromEntity(administrador);
    }

    @PostMapping("/login-admin")
    public LoginRespostaDTO loginAdmin(@Valid @RequestBody LoginDTO dto) {
        AdministradorToken sessao = administradorService.autenticar(dto.email(), dto.senha());
        Administrador admin = sessao.getAdministrador();
        return new LoginRespostaDTO(
                admin.getId(), admin.getNome(), admin.getEmail(), "ADMINISTRADOR",
                admin.getPapel().name(), admin.getFotoPerfil(), sessao.getToken()
        );
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            administradorService.logout(authorization.substring("Bearer ".length()));
        }
    }

    @GetMapping("/admin/{id}")
    public AdministradorRespostaDTO buscarAdmin(@PathVariable Long id) {
        return AdministradorRespostaDTO.fromEntity(administradorService.buscarPorId(id));
    }

    @PutMapping("/admin/{id}/foto")
    public AdministradorRespostaDTO atualizarFotoAdmin(@PathVariable Long id, @Valid @RequestBody FotoDTO dto) {
        return AdministradorRespostaDTO.fromEntity(administradorService.atualizarFoto(id, dto.fotoPerfil()));
    }

    @GetMapping("/admin/pendentes")
    public List<AdministradorRespostaDTO> listarPendentes() {
        return administradorService.listarPendentes().stream().map(AdministradorRespostaDTO::fromEntity).toList();
    }

    @PostMapping("/admin/{id}/aprovar")
    public AdministradorRespostaDTO aprovar(@PathVariable Long id) {
        return AdministradorRespostaDTO.fromEntity(administradorService.aprovar(id));
    }
}
