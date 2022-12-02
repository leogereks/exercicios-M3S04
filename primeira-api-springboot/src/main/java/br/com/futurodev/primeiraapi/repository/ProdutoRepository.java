package br.com.futurodev.primeiraapi.repository;


import br.com.futurodev.primeiraapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("from Produto p where upper(p.descricao) like %?1%")
    List<Produto> getProdutosByDescricao(String descricao);


}
