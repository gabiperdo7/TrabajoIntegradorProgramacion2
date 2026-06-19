/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Joaco
 */
import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido() {
        detalles = new ArrayList<>();
    }

    public Pedido(
            Long id,
            LocalDate fecha,
            Estado estado,
            FormaPago formaPago,
            Usuario usuario
    ) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    @Override
    public void calcularTotal() {

        total = 0.0;

        for (DetallePedido detalle : detalles) {
            total += detalle.getSubtotal();
        }
    }

    public void addDetallePedido(
            int cantidad,
            Double subtotal,
            Producto producto
    ) {

        Long idDetalle = (long) (detalles.size() + 1);

        DetallePedido detalle =
                new DetallePedido(
                        idDetalle,
                        cantidad,
                        subtotal,
                        producto
                );

        detalles.add(detalle);

        calcularTotal();
    }

    public DetallePedido findDetallePedidoByProducto(
            Producto producto
    ) {

        for (DetallePedido detalle : detalles) {

            if (detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }

        return null;
    }

    public void deleteDetallePedidoByProducto(
            Producto producto
    ) {

        DetallePedido detalle =
                findDetallePedidoByProducto(producto);

        if (detalle != null) {

            detalles.remove(detalle);

            calcularTotal();
        }
    }

    @Override
    public String toString() {

        return "Pedido{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                ", usuario=" + usuario.getNombre() +
                '}';
    }
}