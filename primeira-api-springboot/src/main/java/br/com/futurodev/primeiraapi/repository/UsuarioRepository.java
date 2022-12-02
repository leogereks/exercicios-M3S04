package br.com.futurodev.primeiraapi.repository;

import br.com.futurodev.primeiraapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { //CrudRepository
    @Query(value = "select u from Usuario u where u.nome like %?1%")
    ArrayList<Usuario> getUserByName(String nome);

    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

}
