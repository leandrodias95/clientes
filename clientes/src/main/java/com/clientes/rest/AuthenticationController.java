package com.clientes.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.model.entity.Usuario;
import com.clientes.model.repository.UsuarioRepository;
import com.clientes.rest.dto.AuthenticationDTO;
import com.clientes.rest.dto.LoginResponseDTO;
import com.clientes.rest.dto.RegisterDTO;
import com.clientes.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password()); 
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((Usuario)auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if(this.usuarioRepository.findByLogin(data.login())!=null)return ResponseEntity.badRequest().build();
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password()); 
		Usuario newUser =  new Usuario(data.login(), encryptedPassword, data.role());
		
		this.usuarioRepository.save(newUser); 
		return ResponseEntity.ok().build();
	}
}
