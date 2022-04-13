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

@Service // Classe Service (os metodos do controller de forma segura)
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// A seguir: regra de negocios - funções ligadas ao login (consulta de bd, geração de tk, novo cadastro -> geração de novo usu etc.

	private String criptografarSenha(String senha) { 														// funcao para encriptar senha do usu
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 			
		return encoder.encode(senha); 																		//Retorno da senha criptografada
	}
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) { 											//Função para cadastrar, public porque é usado em outras classes

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 								// consulta no bd para verificar se já há usu existente. Se sim, retorno é empty
			return Optional.empty(); 
		
		usuario.setSenha(criptografarSenha(usuario.getSenha())); 											// encriptação da senha e envio das demais info no bd
		return Optional.of(usuarioRepository.save(usuario)); 												// apos o envio info e encripto senha, dá o save na bd via repository
																
	}

	private boolean compararSenhas(String senhaDigitada, String senhaBD) { 									//Função para comparar as senhas obj/Banco
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 										//retira a codificação da senha
		
		return encoder.matches(senhaDigitada, senhaBD); 													// comparação senha digitada e armazenada bd
	}
	
	private String geradorBasicToken (String usuario, String senha) {
		
		String token = usuario + ":" + senha; 
		byte[] TokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); 				//Base64: tecnologia incorporada na geraçao do Tk + identif. da ling do teclado
		
		return "Basic " + new String (TokenBase64); 														//Returna o Head(tk) em forma de String
	}
	
	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) { 					//função do Login Usuario
																							
		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario()); 		//Verificação se usuario existe

		if (usuario.isPresent()) {
			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {  				// comparação entre senha digitada e a salva na bd
																											//Se sim inicia getters e setters atribuindo no UsuarioLogin(Objeto)															
				usuarioLogin.get().setId(usuario.get().getId());											//Comparação de ID atribuindo em Objeto
				usuarioLogin.get().setNome(usuario.get().getNome());										//Comparação de Nome atribuindo em Objeto
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(geradorBasicToken(usuarioLogin.get()									//Comparação de Usuario atribuindo em Objeto
						.getUsuario(), usuarioLogin.get().getSenha())); 											// tk só armazena usu e senha
				usuarioLogin.get().setSenha(usuario.get().getSenha());												//Comparação de senha atribuindo em Objeto
				
								
				return usuarioLogin;																				//Objeto populado é retornado para logar
			}
		}

		return Optional.empty(); 																					// Se estiver presente:: 1 validar usu, 2 comparar senha de usu com a bd
	}

	// inclusão do met. atualizarUsuario
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) { 													//Função para Atualizar busca um usuario no banco

		if (usuarioRepository.findById(usuario.getId()).isPresent()) { 												//Busca pelo ID 
			Optional<Usuario> buscar=usuarioRepository.findByUsuario(usuario.getUsuario());

			if (buscar.isPresent()) {				
				if (buscar.get().getId() != usuario.getId()) 														//Se o mesmo ID que foi buscado do Objeto
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe, tente novamente!", null);
			}
			
			usuario.setSenha(criptografarSenha(usuario.getSenha())); 												//Caso mude a senha ela é criptografada e salva
			return Optional.of(usuarioRepository.save(usuario)); 													//Salva no bd
		} 
			
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado, tente novamente!", null);		
	}	
		
}
