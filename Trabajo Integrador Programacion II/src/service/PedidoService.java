/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Joaco
 */
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;
import exception.EntidadNoEncontradaException;
import exception.StockInsuficienteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private List<Pedido> pedidos;
    private Long siguienteId;

    public PedidoService() {
        pedidos = new ArrayList<>();
        siguienteId = 1L;
    }

    public Pedido crear(
            Usuario usuario,
            FormaPago formaPago
    ) {

        Pedido pedido = new Pedido(
                siguienteId++,
                LocalDate.now(),
                Estado.PENDIENTE,
                formaPago,
                usuario
        );

        pedidos.add(pedido);

        return pedido;
    }

    public List<Pedido> listar() {

        List<Pedido> activos = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (!pedido.isEliminado()) {
                activos.add(pedido);
            }
        }

        return activos;
    }

    public Pedido buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        for (Pedido pedido : pedidos) {

            if (!pedido.isEliminado()
                    && pedido.getId().equals(id)) {

                return pedido;
            }
        }

        throw new EntidadNoEncontradaException(
                "Pedido no encontrado"
        );
    }

    public void agregarDetalle(
            Pedido pedido,
            Producto producto,
            Integer cantidad
    ) throws StockInsuficienteException {

        if (cantidad <= 0) {

            throw new StockInsuficienteException(
                    "La cantidad debe ser mayor a cero"
            );
        }

        if (producto.getStock() < cantidad) {

            throw new StockInsuficienteException(
                    "Stock insuficiente"
            );
        }

        Double subtotal =
                producto.getPrecio() * cantidad;

        pedido.addDetallePedido(
                cantidad,
                subtotal,
                producto
        );

        producto.setStock(
                producto.getStock() - cantidad
        );
    }

    public void actualizarEstado(
            Long id,
            Estado estado
    ) throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setEstado(estado);
    }

    public void actualizarFormaPago(
            Long id,
            FormaPago formaPago
    ) throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setFormaPago(formaPago);
    }

    public void eliminar(Long id)
            throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setEliminado(true);
    }
}