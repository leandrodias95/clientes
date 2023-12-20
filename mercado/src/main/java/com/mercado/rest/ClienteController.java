package com.mercado.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mercado.entity.Cliente;
import com.mercado.repository.ClienteRepository;

@RestController
@RequestMapping("/mercado/api/clientes")
public class ClienteController {
	
	private final ClienteRepository repository;
	
	public ClienteController(ClienteRepository repository) {
		this.repository = repository;
	}

	@PostMapping(value="insert")
	@ResponseStatus(HttpStatus.CREATED) //resposta da minha requisição
	public Cliente salvar(@RequestBody Cliente cliente) {
		return repository.save(cliente); 
	}
	
	@GetMapping("{id}")
	public Cliente acharPorId(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(()-> new  ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletarCliente(@PathVariable Long id) {
	 repository.findById(id).map(cliente->{ //map transforma um elemento em outro
		repository.delete(cliente);
		return cliente; 
	 })
	 .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
}
