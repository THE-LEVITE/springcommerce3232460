package com.sena.springecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.springecommerce.model.Orden;
import com.sena.springecommerce.model.Usuario;

@Service
public class OrdenServiceImplement implements IOrdenService{
	
	@Autowired
	private IOrdenService ordenService;

	@Override
	public Orden save(Orden orden) {
		// TODO Auto-generated method stub
		return ordenService.save(orden);
	}

	@Override
	public List<Orden> findAll() {
		// TODO Auto-generated method stub
		return ordenService.findByUsuario(usuario);
	}

	@Override
	public List<Orden> findByUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Orden> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public String generarNumeroOrden() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
