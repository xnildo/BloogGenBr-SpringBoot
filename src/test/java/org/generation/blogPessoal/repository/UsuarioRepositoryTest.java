package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Neste teste, além da anotação: @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT),
 * precisamos adicionar a anotação @TestInstance(TestInstance.Lifecycle.PER_CLASS)
 * 
 * Esta anotação, indica que os métodos start() e end() serão do tipo @BeforeAll e @AfterAll,
 * diferente do teste da Model que era do tipo @BeforeEach e não possuia o método end()
 *  
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
    
    @Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() throws ParseException {
	   
		LocalDate data = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		Usuario usuario = new Usuario(0, "João da Silva", "joao@email.com.br", "13465278", data);
		if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
			usuarioRepository.save(usuario);
		
		usuario = new Usuario(0, "Manuel da Silva", "manuel@email.com.br", "13465278", data);
		if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
			usuarioRepository.save(usuario);
		
		usuario = new Usuario(0, "Frederico da Silva", "frederico@email.com.br", "13465278", data);
		if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
			usuarioRepository.save(usuario);

        usuario = new Usuario(0, "Paulo Antunes", "paulo@email.com.br", "13465278", data);
        if(usuarioRepository.findByLogin(usuario.getLogin()) != null)
            usuarioRepository.save(usuario);
	}
	
	@Test
	@DisplayName("💾 Retorna o nome")
	public void findFirstByNomeRetornaNome() throws Exception {

		Usuario usuario = usuarioRepository.findByNome("João da Silva");
		assertTrue(usuario.getNome().equals("João da Silva"));
	}
	
	@Test
	@DisplayName("💾 Retorna 3 usuarios")
	public void findAllByNomeContainingIgnoreCaseRetornaTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
	}
	@Disabled
	@AfterAll
	public void end() {
		
		usuarioRepository.deleteAll();
		
	}
}
