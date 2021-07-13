package org.generation.blogPessoal.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(min = 2, max = 100)
	private String nome;
	
	@NotNull
	@Email
	private String login;
	
	@NotNull
	@Size(min = 8)
	private String senha;
	
	@Column(name = "dt_nascimento")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataNascimento; // Atributo adicional
	
	@OneToMany (mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List <Postagem> postagem;

	/** 
	 * Para efetuar os testes, precisamos criar dois métodos construtores
	 * 
	 * 1) Método construtor com todos os atributos, exceto o atributo postagem
	 * 
	 * 2) Método construtor vazio sem nenhum atributo
	*/

	public Usuario(long id, String nome, String login, String senha, LocalDate datanascimento) {
		this.id = id;
		this.nome = nome;
		this.login= login;
		this.senha = senha;
		this.dataNascimento = datanascimento;
	}

	public Usuario() {	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	

	public List<Postagem> getPostagem() {
		return postagem;
	}


	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}

}
