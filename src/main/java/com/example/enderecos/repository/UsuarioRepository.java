package com.example.enderecos.repository;

import com.example.enderecos.models.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    Usuario getById(Long id);

    Optional<Usuario> findByEmailAllIgnoreCase(String email);

    Optional<Usuario> findByCpfAllIgnoreCase(String email);
}
