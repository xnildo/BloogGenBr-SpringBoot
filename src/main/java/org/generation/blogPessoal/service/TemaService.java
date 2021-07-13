package org.generation.blogPessoal.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.generation.blogPessoal.model.Tema;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.generation.blogPessoal.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemaService {

	@Autowired
	private TemaRepository temaRepository;
	
	@Autowired
	private PostagemRepository postagemRepository;
	
	/* Gera Trendtopics */

	public List<Tema> trendTopics(){
		
		List<Tema> temas = temaRepository.findAll();
		
		for (Tema tema : temas) {
	
			tema.setQtdTema(postagemRepository.countPosts2(tema.getId()));
		}
		
		Collections.sort(temas, Collections.reverseOrder(Comparator.comparing(Tema::getQtdTema)));
		return temas;
	}
}
