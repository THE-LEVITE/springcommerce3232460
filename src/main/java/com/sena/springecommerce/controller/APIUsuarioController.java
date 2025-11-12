package com.sena.springecommerce.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public List<Usuario> getAllUsuarios() {
		return usuarioService.findAll();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Usuario> getUserById(@PathVariable Integer id) {
		return usuarioService.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) {
		if (usuario.getRol() == null) {
			usuario.setRol("USER");
		}
		Usuario savedUsuario = usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Usuario> updateUser(@PathVariable Integer id, @RequestBody Usuario userDetails) {
		Optional<Usuario> usuario = usuarioService.get(id);
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario existingUser = usuario.get();
		existingUser.setNombre(userDetails.getNombre());
		existingUser.setTelefono(userDetails.getTelefono());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setDireccion(userDetails.getDireccion());
		existingUser.setPassword(userDetails.getPassword() != null ? userDetails.getPassword() : existingUser.getPassword());
		if (userDetails.getRol() != null) {
			existingUser.setRol(userDetails.getRol());
		}
		usuarioService.update(existingUser);
		return ResponseEntity.ok(existingUser);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		Optional<Usuario> usuario = usuarioService.get(id);
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario u = usuario.get();
		if (!u.getRol().equals("USER")) {

		}
		usuarioService.delete(id);
		return ResponseEntity.ok().build();
	}

}
