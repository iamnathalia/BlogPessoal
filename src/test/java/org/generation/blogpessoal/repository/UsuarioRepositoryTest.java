package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.generation.blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
		usuarioRepository.save(new Usuario(0L, "Robert Pattinson", "robpat@email.com", "boiolinn", "https://i.imgur.com/FETvs2O.jpg\\r\\n\r\n"));
		usuarioRepository.save(new Usuario(0L, "DJ Cleiton Jr", "cleiton@email.com", "bolinhodemurango", "https://i.imgur.com/FETvs2O.jpg\\r\\n\r\n"));
		usuarioRepository.save(new Usuario(0L, "Mc Naninha", "naninha@imperiobronze.com", "boiolina", "https://i.imgur.com/FETvs2O.jpg\\r\\n\r\n"));
		usuarioRepository.save(new Usuario(0L, "DJ Laurinha Lero", "laurita@email.com", "cachorrobravo", "https://i.imgur.com/FETvs2O.jpg\\r\\n\r\n"));
	}
	
	
	@Test
	@DisplayName("Retorna apenas um usuario")
	public void deveRetornarUmUsuario () {
		
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("cleiton@email.com");
				assertTrue(usuario.get().getUsuario().equals("cleiton@email.com"));
			
	}
	
	@Test
	@DisplayName("Retorna dois usuarios")
	public void deveRetornarDoisUsuarios () {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("DJ");
		assertEquals(2, listaDeUsuarios.size());
			
		assertTrue(listaDeUsuarios.get(0).getNome().equals("DJ Cleiton Jr"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("DJ Laurinha Lero"));
		
	}
	
	@BeforeAll
	public void end() {
		usuarioRepository.deleteAll();
	}

}
