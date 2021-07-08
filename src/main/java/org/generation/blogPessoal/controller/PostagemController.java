package org.generation.blogPessoal.controller;


import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/postagens")
@CrossOrigin("*")
public class PostagemController {
	
	@Autowired 
	private PostagemRepository postagemRepository;
	
	@GetMapping("/tudo")
	public ResponseEntity<List<Postagem>> getAll (){
		return ResponseEntity.ok(postagemRepository.findAll()); // OK = 200
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id) {
		return postagemRepository.findById(id)
			.map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo){
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping("/cadastrar")
	public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem){
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping("/editar")
	public ResponseEntity<Postagem> putPostagem(@RequestBody Postagem postagem){
		
		Optional<Postagem> postagemUpdate = postagemRepository.findById(postagem.getId());
		
		if (postagemUpdate.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
		}else{
			throw new ResponseStatusException(
		          	HttpStatus.NOT_FOUND, "Postagem não encontrada!", null);
		}
		
	}
			
	@DeleteMapping("/deletar/{id}")
	public void deletePostagem(@PathVariable long id) {
		
		Optional<Postagem> postagem = postagemRepository.findById(id);
		
		if (postagem.isPresent()) {
			postagemRepository.deleteById(id);
		}else{
			throw new ResponseStatusException(
		          	HttpStatus.NOT_FOUND, "Postagem não encontrada!", null);
		}
	}	
}
