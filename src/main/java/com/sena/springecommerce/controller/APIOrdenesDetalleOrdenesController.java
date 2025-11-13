package com.sena.springecommerce.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.springecommerce.model.*;
import com.sena.springecommerce.service.*;

@RestController
@RequestMapping("/api/ordenes")
public class APIOrdenesDetalleOrdenesController {

	@Autowired
	private IOrdenService ordenService;

	@Autowired
	private IDetalleOrdenService detalleService;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IProductoService productoService;

	// ✅ Crear una nueva orden con detalles
	@PostMapping
	public ResponseEntity<Orden> crearOrden(@RequestBody Orden ordenRequest) {

	    // Validar usuario
	    Optional<Usuario> usuarioOpt = usuarioService.findById(ordenRequest.getUsuario().getId());
	    if (!usuarioOpt.isPresent()) {
	        return ResponseEntity.badRequest().build();
	    }

	    ordenRequest.setUsuario(usuarioOpt.get());
	    ordenRequest.setNumero(ordenService.generarNumeroOrden());
	    ordenRequest.setFechacreacion(new Date());

	    double totalOrden = 0.0;

	    // Procesar detalles antes de guardar la orden
	    if (ordenRequest.getDetalle() != null) {
	        for (DetalleOrden det : ordenRequest.getDetalle()) {

	            Optional<Producto> productoOpt = productoService.get(det.getProductos().getId());
	            if (!productoOpt.isPresent()) continue;

	            Producto producto = productoOpt.get();

	            if (producto.getCantidad() < det.getCantidad()) {
	                return ResponseEntity.badRequest().body(null);
	            }

	            producto.setCantidad(producto.getCantidad() - det.getCantidad());
	            productoService.update(producto);

	            det.setProductos(producto);

	            if (det.getTotal() == null) {
	                det.setTotal(det.getCantidad() * det.getPrecio());
	            }

	            totalOrden += det.getTotal();

	            det.setOrden(ordenRequest); // asignación correcta
	        }
	    }

	    ordenRequest.setTotal(totalOrden);

	    // Guardar la orden junto con los detalles
	    Orden nuevaOrden = ordenService.save(ordenRequest);

	    return ResponseEntity.ok(nuevaOrden);
	}


	// ✅ Obtener todas las órdenes
	@GetMapping("/list")
	public ResponseEntity<List<Orden>> listarOrdenes() {
		List<Orden> ordenes = ordenService.findAll();
		return ResponseEntity.ok(ordenes);
	}

	// ✅ Obtener una orden por ID
	@GetMapping("/orden/{id}")
	public ResponseEntity<Orden> obtenerPorId(@PathVariable Integer id) {
		Optional<Orden> ordenOpt = ordenService.findById(id);
		return ordenOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
