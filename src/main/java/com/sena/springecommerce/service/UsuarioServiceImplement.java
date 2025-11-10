package com.sena.springecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.springecommerce.model.Usuario;
import com.sena.springecommerce.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

	// Instacina de objeto de tipo autowired- es una autocableado para conectar las
	// funciones de IURepository o conectar con el repositorio Usuarios
	@Autowired
	private IUsuarioRepository usuarioRepository;

	// Override: es sobreponer, pasar a control manual, reemplazar, todo el
	// contenido de los metodos.
	@Override
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> get(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}

	@Override
	public void update(Usuario usuario) {
		// TODO Auto-generated method stub
		usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		usuarioRepository.deleteById(id);
	}

	@Override
	public Optional<Usuario> findById(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	
}
