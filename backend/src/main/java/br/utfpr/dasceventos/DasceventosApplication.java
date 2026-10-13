package br.utfpr.dasceventos;

import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.repository.AdministradorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DasceventosApplication {

	public static void main(String[] args) {
		SpringApplication.run(DasceventosApplication.class, args);
	}

	/**
	 * Cria um administrador padrão do DASC na primeira execução, caso a tabela
	 * esteja vazia, já que o cadastro de administrador não é exposto via API.
	 */
	@Bean
	public CommandLineRunner seedAdministrador(AdministradorRepository administradorRepository,
												PasswordEncoder passwordEncoder) {
		return args -> {
			if (administradorRepository.count() == 0) {
				Administrador admin = new Administrador();
				admin.setNome("Sofia Kawazoe");
				admin.setEmail("dasc@utfpr.edu.br");
				admin.setSenha(passwordEncoder.encode("dasc123"));
				administradorRepository.save(admin);
			}
		};
	}

}
