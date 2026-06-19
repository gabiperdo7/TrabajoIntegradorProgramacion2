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
import exception.DatoInvalidoException;
import exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private List<Categoria> categorias;
    private Long siguienteId;

    public CategoriaService() {
        categorias = new ArrayList<>();
        siguienteId = 1L;
    }

    public Categoria crear(String nombre, String descripcion)
            throws DatoInvalidoException {

        validarNombre(nombre);

        Categoria categoria = new Categoria(
                siguienteId++,
                nombre,
                descripcion
        );

        categorias.add(categoria);

        return categoria;
    }

    public List<Categoria> listar() {

        List<Categoria> activas = new ArrayList<>();

        for (Categoria categoria : categorias) {
            if (!categoria.isEliminado()) {
                activas.add(categoria);
            }
        }

        return activas;
    }

    public Categoria buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        for (Categoria categoria : categorias) {

            if (!categoria.isEliminado()
                    && categoria.getId().equals(id)) {

                return categoria;
            }
        }

        throw new EntidadNoEncontradaException(
                "Categoria no encontrada"
        );
    }

    public void editar(
            Long id,
            String nombre,
            String descripcion
    ) throws EntidadNoEncontradaException,
            DatoInvalidoException {

        validarNombre(nombre);

        Categoria categoria = buscarPorId(id);

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
    }

    public void eliminar(Long id)
            throws EntidadNoEncontradaException {

        Categoria categoria = buscarPorId(id);

        categoria.setEliminado(true);
    }

    private void validarNombre(String nombre)
            throws DatoInvalidoException {

        if (nombre == null || nombre.isBlank()) {

            throw new DatoInvalidoException(
                    "El nombre no puede estar vacio"
            );
        }

        for (Categoria categoria : categorias) {

            if (!categoria.isEliminado()
                    && categoria.getNombre()
                            .equalsIgnoreCase(nombre)) {

                throw new DatoInvalidoException(
                        "Ya existe una categoria con ese nombre"
                );
            }
        }
    }
}
