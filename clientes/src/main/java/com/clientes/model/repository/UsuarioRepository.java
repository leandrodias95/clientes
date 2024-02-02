package com.clientes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.clientes.model.entity.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
UserDetails findByLogin(String login);
}
