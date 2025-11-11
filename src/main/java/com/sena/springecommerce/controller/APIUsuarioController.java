package com.sena.springecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.springecommerce.model.Usuario;
import com.sena.springecommerce.service.IUsuarioService;



@RestController
@RequestMapping("/apiusarios")
public class APIUsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping
	public List<Usuario> getAllUsuarios(){
		return usuarioService.findAll();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Usuario> getUserById(@PathVariable Integer id){
		Optional<Usuario> usuario = usuarioService.get(id);
		return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/create")
	public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario){
		Usuario u = usuarioService.findById(1).get();
		
		
	}
	

}
