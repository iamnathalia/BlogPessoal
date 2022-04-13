package org.generation.blogpessoal.controller;

import java.util.List;

import org.generation.blogpessoal.model.Postagem;
import org.generation.blogpessoal.repository.PostagemRepository;

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

@RestController 											// Indica que esta classe é um controller
@RequestMapping("/postagens") 								// /postagens é URI (?) essa classe sera acessada! Qnd vier uma requisicao para
															// /postagens, a requisição consumirá essa classe
@CrossOrigin("*") 											// o "*" indica que a API aceitará requisicao (aplicaçao frontend) de qualquer
															// origem
public class PostagemController {

	@Autowired 												// "direcionamento de dependencia" direcionamento da responsabilidade de instanciação da interface para o STS (serviços da interface >>> postagem / repository acessado a partir do controller)
	private PostagemRepository repository; 					// inclui a classe repository (interface)

	@GetMapping 											// sempre que tiver req externa, atraves da "/postagens" se o met. for GET, será iniciado o comando descrito
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll()); 	// o ok é o status 200 do http
	}

	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem) {      
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	} 
	 
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id){
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build()); 
															/* quando uma req. do tipo Get em "/postagem/" e passar um valor/atrib. (id) sera acessado este med. em que captura qual variavel esta recebendo dentro do path variable. 
															Sera retornado a interface em que "injetamos" com autowired, que convoca o met. findbyid - que pode devolver um obj do tipo postagem, ou mensagem de erro na requisição. 
															Path Variable pega um atributo do caminho da URI  */
	}
	
	@GetMapping("/titulo/{titulo}") 													// rota secundária para não entrar em conflito com metodo GET anterior
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo)); 
		
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put (@RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem));
		
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
	 
}


