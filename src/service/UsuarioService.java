package org.generation.blogpessoal.service; // classe para todas as regras de negocios da aplicação :::: login/cadastro 

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64; 
import org.generation.blogpessoal.model.Usuario;
import org.generation.blogpessoal.model.UsuarioLogin;
import org.generation.blogpessoal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// A seguir: regra de negocios - funções ligadas ao login (consulta de bd,
	// geração de tk, novo cadastro -> geração de novo usu etc.

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
			return Optional.empty(); // consulta no bd para verificar se já há usu existente
		}
		usuario.setSenha(criptografarSenha(usuario.getSenha())); 
		// encriptação da senha e envio das demais info no bd
		
		return Optional.of(usuarioRepository.save(usuario)); 
		// apos o envio info e encripto senha, dá o save na bd via repository
																
	}

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) { 
		// verificação se o usuario existe na bd
																							
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario()); 
		// se sim, conferencia dos demais dados
																										

		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) { 
				// comparação entre senha digitada e a salva na bd
																							
				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get()
						.setToken(geradorBasicToken(usuarioLogin.get()
						.getUsuario(), usuarioLogin.get().getSenha())); // tk só armazena usu e senha
				usuarioLogin.get().setSenha(usuario.get().getSenha());
				
								
				return usuarioLogin;
			}
		}

		return Optional.empty(); 					// Se estiver presente:: 1 validar usu, 2 comparar senha de usu com a bd
	}

	// inclusão do met. atualizarUsuario
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {

		if (usuarioRepository.findById(usuario.getId()).isPresent()) {
			Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

			if (buscaUsuario.isPresent()) {				
				if (buscaUsuario.get().getId() != usuario.getId())
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe, tente novamente!", null);
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.of(usuarioRepository.save(usuario));
		} 
			
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!", null);		
	}	
	
	// :::::: 
	
	private boolean compararSenhas(String senhaDigitada, String senhaBD) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBD);
	}
	
	private String criptografarSenha(String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 			// recebe a senha e encripta
		return encoder.encode(senha);

	}

	private String geradorBasicToken (String usuario, String senha) {
		
		String token = usuario + ":" + senha; 
		byte[] TokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); 
		// Base64: tecnologia incorporada na geraçao do Tk + identif. da ling do teclado
		
		return "Basic " + new String (TokenBase64);
	}
	
	
}
