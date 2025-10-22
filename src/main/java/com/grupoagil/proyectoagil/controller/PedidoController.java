package com.grupoagil.proyectoagil.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Pedido;
import com.grupoagil.proyectoagil.service.PedidoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /** Crear pedido para una mesa (requiere idMesa, idUsuario; estadoInicial opcional) */
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody Map<String, Object> body) {
        try {
            Long idMesa = Long.valueOf(body.get("idMesa").toString());
            String idUsuario = body.get("idUsuario").toString();
            String estadoInicial = body.get("estado") == null ? null : body.get("estado").toString();

            Pedido creado = pedidoService.crearPedido(idMesa, idUsuario, estadoInicial);
            return ResponseEntity.ok(creado);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    /** ¿La mesa tiene pedido abierto? */
    @GetMapping("/mesa/{idMesa}/tiene-abierto")
    public Map<String, Object> mesaTieneAbierto(@PathVariable Long idMesa) {
        boolean tiene = pedidoService.mesaTienePedidoAbierto(idMesa);
        return Map.of("idMesa", idMesa, "tieneAbierto", tiene);
    }

    /** Obtener el pedido abierto de una mesa (si existe) */
    @GetMapping("/mesa/{idMesa}/abierto")
    public ResponseEntity<?> pedidoAbiertoDeMesa(@PathVariable Long idMesa) {
        return pedidoService.obtenerPedidoAbiertoPorMesa(idMesa)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(Map.of("mensaje", "La mesa no tiene pedido abierto.")));
    }

    /** Listar pedidos de una mesa */
    @GetMapping("/mesa/{idMesa}")
    public List<Pedido> listarPorMesa(@PathVariable Long idMesa) {
        return pedidoService.listarPorMesa(idMesa);
    }

    /** Cambiar estado de un pedido (Ordenado, En Preparación, Atendido, Pagado, Cancelado) */
    @PatchMapping("/{idPedido}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idPedido, @RequestBody Map<String, String> body) {
        try {
            String nuevoEstado = body.get("estado");
            Pedido actualizado = pedidoService.cambiarEstado(idPedido, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    /** Obtener un pedido por ID */
    @GetMapping("/{idPedido}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idPedido) {
        return pedidoService.obtenerPorId(idPedido)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(Map.of("error", "Pedido no encontrado")));
    }
}
