/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Joaco
 */
import entities.Categoria;
import entities.Producto;
import exception.DatoInvalidoException;
import exception.EntidadNoEncontradaException;
import exception.StockInvalidoException;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private List<Producto> productos;
    private Long siguienteId;

    public ProductoService() {
        productos = new ArrayList<>();
        siguienteId = 1L;
    }

    public Producto crear(
            String nombre,
            Double precio,
            String descripcion,
            Integer stock,
            String imagen,
            boolean disponible,
            Categoria categoria
    ) throws DatoInvalidoException,
            StockInvalidoException {

        validar(nombre, precio, stock);

        Producto producto = new Producto(
                siguienteId++,
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        productos.add(producto);

        return producto;
    }

    public List<Producto> listar() {

        List<Producto> activos = new ArrayList<>();

        for (Producto producto : productos) {

            if (!producto.isEliminado()) {
                activos.add(producto);
            }
        }

        return activos;
    }

    public Producto buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        for (Producto producto : productos) {

            if (!producto.isEliminado()
                    && producto.getId().equals(id)) {

                return producto;
            }
        }

        throw new EntidadNoEncontradaException(
                "Producto no encontrado"
        );
    }

    public void editar(
            Long id,
            String nombre,
            Double precio,
            String descripcion,
            Integer stock,
            String imagen,
            boolean disponible,
            Categoria categoria
    ) throws EntidadNoEncontradaException,
            DatoInvalidoException,
            StockInvalidoException {

        validar(nombre, precio, stock);

        Producto producto = buscarPorId(id);

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setDisponible(disponible);
        producto.setCategoria(categoria);
    }

    public void eliminar(Long id)
            throws EntidadNoEncontradaException {

        Producto producto = buscarPorId(id);

        producto.setEliminado(true);
    }

    private void validar(
            String nombre,
            Double precio,
            Integer stock
    ) throws DatoInvalidoException,
            StockInvalidoException {

        if (nombre == null || nombre.isBlank()) {

            throw new DatoInvalidoException(
                    "El nombre no puede estar vacio"
            );
        }

        if (precio < 0) {

            throw new StockInvalidoException(
                    "El precio no puede ser negativo"
            );
        }

        if (stock < 0) {

            throw new StockInvalidoException(
                    "El stock no puede ser negativo"
            );
        }
    }
}