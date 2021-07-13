package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * A anotação@TestMethodOrder(MethodOrderer.OrderAnnotation.class) habilita a opção
 * de forçar o Junit a executar todos os testes na ordem pré definida pela pessoa 
 * desenvolvedora através da anotação @Order(numero)
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {
    
	/**
	 * 
	 *  Nas próximas 2 linhas após o comentário, foi injetado um objeto do tipo
	 *  TestRestTemplate que será utilizado para enviar uma requisição http para
	 *  a nossa API Rest, permitindo testar o funcionamento dos endpoints da nossa
	 *  classe UsuarioController
	 * 
	 */

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	/**
	 * Alteração: Foram criados 3 objetos Usuario:
	 * 1) usuario (Testar o método post) 
	 * 2) usuarioUpdate (Testar o método put)
	 * 3) usuarioAdmin (Criar o usuário para logar na API)
	 * 
	 * Injeção da classe Usuario Repository
	 */

	private Usuario usuario;
	
	private Usuario usuarioUpdate;
	
	
	@BeforeAll
	public void start() throws ParseException {

		LocalDate dataPost = LocalDate.parse("2000-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	       usuario = new Usuario(0, "Administrador", "admin@email.com.br", "admin123", dataPost);
	       //usuario = new Usuario(0, "João da Silva", "joao@email.com.br", "13465278", dataPost);
	        
	       	LocalDate dataPut = LocalDate.parse("2002-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        usuarioUpdate = new Usuario(11L, "João da Silva Souza", "joao2@email.com.br", "joao123", dataPut);
		}
		@Disabled
		@Test
	    @DisplayName("✔ Cadastrar Usuário!")
		public void deveRealizarPostUsuario() {

			HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuario);

			ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
			assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

		}
		
		@Test
	    @DisplayName("👍 Listar todos os Usuários!")
		public void deveMostrarTodosUsuarios() {
			ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
			assertEquals(HttpStatus.OK, resposta.getStatusCode());
		}
		
		
		@Test
	    @DisplayName("😳 Alterar Usuário!")
		public void deveRealizarPutUsuario() {

			HttpEntity<Usuario> request = new HttpEntity<Usuario>(usuarioUpdate);

			ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("admin@email.com.br", "admin123").exchange("/usuarios/alterar", HttpMethod.PUT, request, Usuario.class);
			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			
		}
	
}
