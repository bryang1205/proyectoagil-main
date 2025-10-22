package com.grupoagil.proyectoagil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupoagil.proyectoagil.model.Mesa;
import com.grupoagil.proyectoagil.model.Pedido;
import com.grupoagil.proyectoagil.model.Usuario;
import com.grupoagil.proyectoagil.repository.MesaRepository;
import com.grupoagil.proyectoagil.repository.PedidoRepository;
import com.grupoagil.proyectoagil.repository.UsuarioRepository;

@Service
public class PedidoService {

    public static final List<String> ESTADOS_ABIERTOS = List.of("Ordenado", "En Preparación", "Atendido");

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         MesaRepository mesaRepository,
                         UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** ¿La mesa tiene un pedido abierto? */
    public boolean mesaTienePedidoAbierto(Long idMesa) {
        return pedidoRepository.existsByMesa_IdMesaAndEstadoIn(idMesa, ESTADOS_ABIERTOS);
    }

    /** Obtener (si existe) el pedido abierto de la mesa */
    public Optional<Pedido> obtenerPedidoAbiertoPorMesa(Long idMesa) {
        return pedidoRepository.findFirstByMesa_IdMesaAndEstadoInOrderByFechaDesc(idMesa, ESTADOS_ABIERTOS);
    }

    /** Listar pedidos por mesa (más recientes primero) */
    public List<Pedido> listarPorMesa(Long idMesa) {
        return pedidoRepository.findByMesa_IdMesaOrderByFechaDesc(idMesa);
    }

    /** Crear pedido para una mesa (no permite crear si ya hay uno abierto) */
    @Transactional
    public Pedido crearPedido(Long idMesa, String idUsuario, String estadoInicial) {
        if (estadoInicial == null || estadoInicial.isBlank()) {
            estadoInicial = "Ordenado";
        }
        // Validar estado inicial permitido (puede ser abierto o ya cerrado si así lo decides)
        // Aquí forzamos que el inicio sea uno de los abiertos:
        if (!ESTADOS_ABIERTOS.contains(estadoInicial)) {
            throw new IllegalArgumentException("Estado inicial inválido. Use: " + ESTADOS_ABIERTOS);
        }

        // Validar mesa
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada: " + idMesa));

        // Validar usuario (quien registra)
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + idUsuario));

        // Regla: una mesa no puede tener más de un pedido abierto
        if (mesaTienePedidoAbierto(idMesa)) {
            throw new IllegalStateException("La mesa " + idMesa + " ya tiene un pedido abierto.");
        }

        Pedido p = new Pedido();
        p.setMesa(mesa);
        p.setUsuario(usuario);
        p.setFecha(LocalDateTime.now());
        p.setEstado(estadoInicial);

        return pedidoRepository.save(p);
    }

    /** Cambiar estado del pedido (maneja cierre al pasar a Pagado/Cancelado) */
    @Transactional
    public Pedido cambiarEstado(Long idPedido, String nuevoEstado) {
        Pedido p = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + idPedido));

        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("Nuevo estado no puede ser vacío.");
        }

        // Validaciones ligeras de transición (puedes endurecerlas si quisieras)
        // Cerramos cuando pasa a Pagado o Cancelado.
        p.setEstado(nuevoEstado.trim());

        return pedidoRepository.save(p);
    }

    /** Obtener un pedido por ID */
    public Optional<Pedido> obtenerPorId(Long idPedido) {
        return pedidoRepository.findById(idPedido);
    }
}
