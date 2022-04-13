
package org.generation.blogpessoal.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Table (name = "tb_usuarios")
public class Usuario {

		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		private Long id;
		
		private String nome;
		
		@Schema(example="email@email.com.br")
		@NotNull
		@Email
		private String usuario;
		
		@NotNull
		@Size(min=5 , max=100)
		private String senha;
		 
		private String foto;
		
	// 
		
		@OneToMany (mappedBy="usuario", cascade = CascadeType.REMOVE) // relação usu + atividades deste
		@JsonIgnoreProperties ("usuario")
		private List<Postagem> postagens;
		
		
		//METODOS CONSTRUTORES PARA TESTES
		//Seguir a mesma ordem das declarações dos atrib. de usu acima
		
		public Usuario (Long id, String nome, String usuario, String senha, String foto) { 
			this.id=id; 
			this.nome=nome;
			this.usuario=usuario;
			this.senha=senha;
			this.foto=foto;
		}	
		
		//CONSTRUTOR VAZIO 
		// Liberdade para testar o que for escolhido
		public Usuario () {}
		
		
		// GET-SET
		
		

		public String getNome() {
			return nome;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public String getFoto() {
			return foto;
		}

		public void setFoto(String foto) {
			this.foto = foto;
		}

		public List<Postagem> getPostagens() {
			return postagens;
		}

		public void setPostagens(List<Postagem> postagens) {
			this.postagens = postagens;
		}
		
	
}

