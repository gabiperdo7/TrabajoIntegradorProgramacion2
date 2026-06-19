/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Joaco
 */
import entities.Usuario;
import enums.Rol;
import exception.DatoInvalidoException;
import exception.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<Usuario> usuarios;
    private Long siguienteId;

    public UsuarioService() {
        usuarios = new ArrayList<>();
        siguienteId = 1L;
    }

    public Usuario crear(
            String nombre,
            String apellido,
            String mail,
            String celular,
            String contrasenia,
            Rol rol
    ) throws DatoInvalidoException {

        validar(nombre, apellido, mail);

        Usuario usuario = new Usuario(
                siguienteId++,
                nombre,
                apellido,
                mail,
                celular,
                contrasenia,
                rol
        );

        usuarios.add(usuario);

        return usuario;
    }

    public List<Usuario> listar() {

        List<Usuario> activos = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()) {
                activos.add(usuario);
            }
        }

        return activos;
    }

    public Usuario buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()
                    && usuario.getId().equals(id)) {

                return usuario;
            }
        }

        throw new EntidadNoEncontradaException(
                "Usuario no encontrado"
        );
    }

    public void editar(
            Long id,
            String nombre,
            String apellido,
            String mail,
            String celular,
            String contrasenia,
            Rol rol
    ) throws EntidadNoEncontradaException,
            DatoInvalidoException {

        Usuario usuario = buscarPorId(id);

        validarEdicion(nombre, apellido, mail, id);

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol);
    }

    public void eliminar(Long id)
            throws EntidadNoEncontradaException {

        Usuario usuario = buscarPorId(id);

        usuario.setEliminado(true);
    }

    private void validar(
            String nombre,
            String apellido,
            String mail
    ) throws DatoInvalidoException {

        if (nombre == null || nombre.isBlank()) {
            throw new DatoInvalidoException(
                    "El nombre no puede estar vacio"
            );
        }

        if (apellido == null || apellido.isBlank()) {
            throw new DatoInvalidoException(
                    "El apellido no puede estar vacio"
            );
        }

        if (mail == null || mail.isBlank()) {
            throw new DatoInvalidoException(
                    "El mail no puede estar vacio"
            );
        }

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()
                    && usuario.getMail().equalsIgnoreCase(mail)) {

                throw new DatoInvalidoException(
                        "El mail ya existe"
                );
            }
        }
    }

    private void validarEdicion(
            String nombre,
            String apellido,
            String mail,
            Long id
    ) throws DatoInvalidoException {

        if (nombre == null || nombre.isBlank()) {
            throw new DatoInvalidoException(
                    "El nombre no puede estar vacio"
            );
        }

        if (apellido == null || apellido.isBlank()) {
            throw new DatoInvalidoException(
                    "El apellido no puede estar vacio"
            );
        }

        if (mail == null || mail.isBlank()) {
            throw new DatoInvalidoException(
                    "El mail no puede estar vacio"
            );
        }

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()
                    && !usuario.getId().equals(id)
                    && usuario.getMail().equalsIgnoreCase(mail)) {

                throw new DatoInvalidoException(
                        "El mail ya existe"
                );
            }
        }
    }
}