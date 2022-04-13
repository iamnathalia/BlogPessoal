package org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity 																			// transforma em tabela
@Table(name = "tb_postagens") 														// nomeia a tabela
public class Postagem {

																					// esta classe é o objeto de postagem, composto por id, nn, text e data
	@Id 																			// informa que é um id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 							// base de dados = primary key autoincrement
	private Long id; 																// long é equivalente ao BigInt do SQL
																					// não permite que o campo fique em branco
	@Size(min = 10, max = 2000)
	private String titulo; 															// titulo, texto e data serão anexados em demais classes, portanto atenção

	@Size(min = 10, max = 5000) 													// define qnde de caracteres permitido para o campo abaixo referenciado... da																				// para usar só um ou outro também
	private String texto;

	@UpdateTimestamp 																// importa a data e horario local para salvar no banco de dados
	private LocalDateTime data;
	
	@ManyToOne
	@JsonIgnoreProperties ("postagem") 												// para não gerar recursividade, inclui-se o JsonIgnoreProperties e associe com a propriedade que se quer ignorar - parar de apresentar info (não gerar looping)
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties ("postagem") 				// relacionamento com a tabela usuario								
	private Usuario usuario;

	// inclusão de get e set em tema e criação de classe para tema

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
		
	
}

