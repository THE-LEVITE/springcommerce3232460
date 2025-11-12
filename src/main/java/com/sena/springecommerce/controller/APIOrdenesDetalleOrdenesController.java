package com.sena.springecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sena.springecommerce.model.*;
import com.sena.springecommerce.service.*;

@RestController
@RequestMapping("/apiordenes")
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
    @PostMapping("/create")
    public ResponseEntity<Orden> crearOrden(@RequestBody Orden ordenRequest) {
        // Validar usuario
        Optional<Usuario> usuarioOpt = usuarioService.findById(ordenRequest.getUsuario().getId());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        ordenRequest.setUsuario(usuarioOpt.get());
        ordenRequest.setNumero(ordenService.generarNumeroOrden());

        // Guardar orden principal
        Orden nuevaOrden = ordenService.save(ordenRequest);

        // Guardar los detalles asociados
        if (ordenRequest.getDetalle() != null) {
            for (DetalleOrden det : ordenRequest.getDetalle()) {
                Optional<Producto> productoOpt = productoService.get(det.getProductos().getId());
                if (!productoOpt.isPresent()) continue;

                det.setProductos(productoOpt.get());
                det.setOrden(nuevaOrden);
                detalleService.save(det);
            }
        }

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
        return ordenOpt.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Actualizar una orden existente
    @PutMapping("/update/{id}")
    public ResponseEntity<Orden> actualizarOrden(@PathVariable Integer id, @RequestBody Orden ordenRequest) {
        Optional<Orden> ordenOpt = ordenService.findById(id);
        if (!ordenOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Orden orden = ordenOpt.get();
        orden.setTotal(ordenRequest.getTotal());
        orden.setFechacreacion(ordenRequest.getFechacreacion());
        ordenService.save(orden);
        return ResponseEntity.ok(orden);
    }

    // ✅ Eliminar una orden (y sus detalles)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Integer id) {
        Optional<Orden> ordenOpt = ordenService.findById(id);
        if (!ordenOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Orden orden = ordenOpt.get();

        // Eliminar detalles primero (por seguridad referencial)
        if (orden.getDetalle() != null) {
            for (DetalleOrden det : orden.getDetalle()) {
                detalleService.save(det);
            }
        }

        ordenService.findById(id).ifPresent(o -> ordenService.findAll().remove(o));
        return ResponseEntity.noContent().build();
    }
}
