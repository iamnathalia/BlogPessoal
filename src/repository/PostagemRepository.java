package org.generation.blogpessoal.repository;

import java.util.List;
import org.generation.blogpessoal.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;																											//herança de uma outra interface, necessita de indicação dos parametros trabalhados 
import org.springframework.stereotype.Repository;
	
//declarar nos parametros solicitados no extends: tipo de entidade trabalhada (nome do model) e tipo de chave primaria (Long na versao primitiva)

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>  {
    public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);

}
