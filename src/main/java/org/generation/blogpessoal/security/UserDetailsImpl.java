package org.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.generation.blogpessoal.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(Usuario usuario) {
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}

	//--- Construtor Vazio (TESTES)
	public UserDetailsImpl() {}

	//get set 
	
	@Override
	public String getPassword() {
		return password;  				//para conseguir pegar a senha(Model) e responder como password
	}

	@Override
	public String getUsername() {
		return userName; 				//para conseguir pegar a usuario(Model) e responder como userName
	}
	
	// Metodo do nivel de segurança do usuario \/
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	// Met. Padrao de Basic Security \/
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}