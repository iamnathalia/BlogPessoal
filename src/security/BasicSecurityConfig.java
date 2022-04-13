package org.generation.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override // criação do metodo de acesso: usuario dev para tester
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { // segurança aplicada a todos os niveis

		auth.userDetailsService(userDetailsService);

		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder().encode("root"))
				.authorities("ROLE USER");

	}

	@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
				
		}

	@Override
		protected void configure(HttpSecurity http) throws Exception{
			
			http.authorizeRequests()
			.antMatchers("/usuarios/logar").permitAll()  
			.antMatchers("/usuarios/cadastrar").permitAll()	
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated() // requisições dentro da aplicacao necessitará de autenticação
			.and().httpBasic()  // metodos rest app CRUD usados
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //um token não é valido de forma indefinida, expira por sessão
			.and().cors() // aux. do crossorigin/controller
			.and().csrf().disable(); //permite modificações futuras sem solicitar mais um metodo de segurança
		
		
	}
	

}
