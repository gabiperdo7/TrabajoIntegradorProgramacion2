/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Joaco
 */

import service.CategoriaService;
import java.util.Scanner;
import service.ProductoService;
import enums.Rol;
import service.UsuarioService;        
import service.PedidoService;
import enums.Estado;
import enums.FormaPago;


public class Menu {

    private Scanner teclado;
    private CategoriaService categoriaService;
    private ProductoService productoService;
    private UsuarioService usuarioService;
    private PedidoService pedidoService;
    
public Menu() {
    teclado = new Scanner(System.in);

    categoriaService = new CategoriaService();
    productoService = new ProductoService();
    usuarioService = new UsuarioService();
    pedidoService = new PedidoService();
}

    public void iniciar() {

        int opcion;

        do {

            System.out.println("\n=== FOOD STORE ===");
            System.out.println("1. Categorias");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {

                case 1 ->
                    menuCategorias();
                case 2 ->
                    menuProductos();
                case 3 ->
                    menuUsuarios();
                case 0 ->
                    System.out.println("Hasta luego");
                case 4 ->
                    menuPedidos();
                default ->  
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);
    }

private void menuPedidos() {

    int opcion;

    do {

        System.out.println("\n=== PEDIDOS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Actualizar estado");
        System.out.println("4. Actualizar forma de pago");
        System.out.println("5. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        opcion = Integer.parseInt(teclado.nextLine());

        switch (opcion) {

            case 1 ->
                listarPedidos();

            case 2 ->
                crearPedido();

            case 3 ->
                actualizarEstadoPedido();

            case 4 ->
                actualizarFormaPagoPedido();

            case 5 ->
                eliminarPedido();

            case 0 ->
                System.out.println("Volviendo...");

            default ->
                System.out.println("Opcion invalida");
        }

    } while (opcion != 0);
}    


    
private void menuUsuarios() {

    int opcion;

    do {

        System.out.println("\n=== USUARIOS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        opcion = Integer.parseInt(teclado.nextLine());

        switch (opcion) {

            case 1 ->
                listarUsuarios();

            case 2 ->
                crearUsuario();

            case 3 ->
                editarUsuario();

            case 4 ->
                eliminarUsuario();

            case 0 ->
                System.out.println("Volviendo...");

            default ->
                System.out.println("Opcion invalida");
        }

    } while (opcion != 0);
}

private void crearPedido() {

    try {

        listarUsuarios();

        System.out.print("Id usuario: ");
        Long idUsuario =
                Long.parseLong(teclado.nextLine());

        var usuario =
                usuarioService.buscarPorId(idUsuario);

        System.out.println("Forma de pago");
        System.out.println("1. TARJETA");
        System.out.println("2. TRANSFERENCIA");
        System.out.println("3. EFECTIVO");

        int opcionPago =
                Integer.parseInt(teclado.nextLine());

        FormaPago formaPago;

        switch (opcionPago) {

            case 1 -> formaPago = FormaPago.TARJETA;
            case 2 -> formaPago = FormaPago.TRANSFERENCIA;
            default -> formaPago = FormaPago.EFECTIVO;
        }

        var pedido =
                pedidoService.crear(
                        usuario,
                        formaPago
                );

        String seguir;

        do {

            listarProductos();

            System.out.print("Id producto: ");
            Long idProducto =
                    Long.parseLong(
                            teclado.nextLine()
                    );

            var producto =
                    productoService.buscarPorId(
                            idProducto
                    );

            System.out.print("Cantidad: ");
            Integer cantidad =
                    Integer.parseInt(
                            teclado.nextLine()
                    );

            pedidoService.agregarDetalle(
                    pedido,
                    producto,
                    cantidad
            );

            System.out.print(
                    "Agregar otro producto? (S/N): "
            );

            seguir = teclado.nextLine();

        } while (seguir.equalsIgnoreCase("S"));

        pedido.calcularTotal();

        System.out.println(
                "Pedido creado correctamente"
        );

        System.out.println(
                "Total: $" +
                pedido.getTotal()
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void actualizarEstadoPedido() {

    try {

        listarPedidos();

        System.out.print("Id pedido: ");
        Long id =
                Long.parseLong(
                        teclado.nextLine()
                );

        System.out.println("1. PENDIENTE");
        System.out.println("2. CONFIRMADO");
        System.out.println("3. TERMINADO");
        System.out.println("4. CANCELADO");

        int opcion =
                Integer.parseInt(
                        teclado.nextLine()
                );

        Estado estado;

        switch (opcion) {

            case 1 -> estado = Estado.PENDIENTE;
            case 2 -> estado = Estado.CONFIRMADO;
            case 3 -> estado = Estado.TERMINADO;
            default -> estado = Estado.CANCELADO;
        }

        pedidoService.actualizarEstado(
                id,
                estado
        );

        System.out.println(
                "Estado actualizado"
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void actualizarFormaPagoPedido() {

    try {

        listarPedidos();

        System.out.print("Id pedido: ");
        Long id =
                Long.parseLong(
                        teclado.nextLine()
                );

        System.out.println("1. TARJETA");
        System.out.println("2. TRANSFERENCIA");
        System.out.println("3. EFECTIVO");

        int opcion =
                Integer.parseInt(
                        teclado.nextLine()
                );

        FormaPago formaPago;

        switch (opcion) {

            case 1 -> formaPago = FormaPago.TARJETA;
            case 2 -> formaPago = FormaPago.TRANSFERENCIA;
            default -> formaPago = FormaPago.EFECTIVO;
        }

        pedidoService.actualizarFormaPago(
                id,
                formaPago
        );

        System.out.println(
                "Forma de pago actualizada"
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void listarPedidos() {

    if (pedidoService.listar().isEmpty()) {

        System.out.println("No hay pedidos cargados");
        return;
    }

    for (var pedido : pedidoService.listar()) {

        System.out.println(pedido);

        if (pedido.getDetalles() != null) {

            for (var detalle : pedido.getDetalles()) {
                System.out.println("   " + detalle);
            }
        }
    }
}

private void eliminarPedido() {

    try {

        listarPedidos();

        System.out.print("Id pedido: ");
        Long id =
                Long.parseLong(
                        teclado.nextLine()
                );

        System.out.print(
                "Confirma eliminacion? (S/N): "
        );

        String confirmacion =
                teclado.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            pedidoService.eliminar(id);

            System.out.println(
                    "Pedido eliminado"
            );
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void listarUsuarios() {

    if (usuarioService.listar().isEmpty()) {

        System.out.println("No hay usuarios cargados");
        return;
    }

    for (var usuario : usuarioService.listar()) {
        System.out.println(usuario);
    }
}

private void crearUsuario() {

    try {

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();

        System.out.print("Mail: ");
        String mail = teclado.nextLine();

        System.out.print("Celular: ");
        String celular = teclado.nextLine();

        System.out.print("Contrasenia: ");
        String contrasenia = teclado.nextLine();

        System.out.println("1. ADMIN");
        System.out.println("2. USUARIO");
        System.out.print("Rol: ");

        int opcionRol =
                Integer.parseInt(teclado.nextLine());

        Rol rol = opcionRol == 1
                ? Rol.ADMIN
                : Rol.USUARIO;

        var usuario =
                usuarioService.crear(
                        nombre,
                        apellido,
                        mail,
                        celular,
                        contrasenia,
                        rol
                );

        System.out.println(
                "Usuario creado con id "
                + usuario.getId()
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void editarUsuario() {

    try {

        listarUsuarios();

        System.out.print("Id usuario: ");
        Long id =
                Long.parseLong(teclado.nextLine());

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Apellido: ");
        String apellido = teclado.nextLine();

        System.out.print("Mail: ");
        String mail = teclado.nextLine();

        System.out.print("Celular: ");
        String celular = teclado.nextLine();

        System.out.print("Contrasenia: ");
        String contrasenia = teclado.nextLine();

        System.out.println("1. ADMIN");
        System.out.println("2. USUARIO");
        System.out.print("Rol: ");

        int opcionRol =
                Integer.parseInt(teclado.nextLine());

        Rol rol = opcionRol == 1
                ? Rol.ADMIN
                : Rol.USUARIO;

        usuarioService.editar(
                id,
                nombre,
                apellido,
                mail,
                celular,
                contrasenia,
                rol
        );

        System.out.println(
                "Usuario actualizado"
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void eliminarUsuario() {

    try {

        listarUsuarios();

        System.out.print("Id usuario: ");
        Long id =
                Long.parseLong(teclado.nextLine());

        System.out.print(
                "Confirma eliminacion? (S/N): "
        );

        String confirmacion =
                teclado.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            usuarioService.eliminar(id);

            System.out.println(
                    "Usuario eliminado"
            );
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void menuCategorias() {

    int opcion;

    do {

        System.out.println("\n=== CATEGORIAS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        opcion = Integer.parseInt(teclado.nextLine());

        switch (opcion) {

            case 1 ->
                listarCategorias();

            case 2 ->
                crearCategoria();

            case 3 ->
                editarCategoria();

            case 4 ->
                eliminarCategoria();

            case 0 ->
                System.out.println("Volviendo...");

            default ->
                System.out.println("Opcion invalida");
        }

    } while (opcion != 0);
}

private void menuProductos() {

    int opcion;

    do {

        System.out.println("\n=== PRODUCTOS ===");
        System.out.println("1. Listar");
        System.out.println("2. Crear");
        System.out.println("3. Editar");
        System.out.println("4. Eliminar");
        System.out.println("0. Volver");
        System.out.print("Seleccione: ");

        opcion = Integer.parseInt(teclado.nextLine());

        switch (opcion) {

            case 1 ->
                listarProductos();

            case 2 ->
                crearProducto();

            case 3 ->
                editarProducto();

            case 4 ->
                eliminarProducto();

            case 0 ->
                System.out.println("Volviendo...");

            default ->
                System.out.println("Opcion invalida");
        }

    } while (opcion != 0);
}

private void listarProductos() {

    if (productoService.listar().isEmpty()) {

        System.out.println("No hay productos cargados");
        return;
    }

    for (var producto : productoService.listar()) {
        System.out.println(producto);
    }
}

private void crearProducto() {

    try {

        listarCategorias();

        System.out.print("Id categoria: ");
        Long idCategoria =
                Long.parseLong(teclado.nextLine());

        var categoria =
                categoriaService.buscarPorId(idCategoria);

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Precio: ");
        Double precio =
                Double.parseDouble(teclado.nextLine());

        System.out.print("Descripcion: ");
        String descripcion = teclado.nextLine();

        System.out.print("Stock: ");
        Integer stock =
                Integer.parseInt(teclado.nextLine());

        System.out.print("Imagen: ");
        String imagen = teclado.nextLine();

        System.out.print("Disponible (true/false): ");
        boolean disponible =
                Boolean.parseBoolean(teclado.nextLine());

        var producto =
                productoService.crear(
                        nombre,
                        precio,
                        descripcion,
                        stock,
                        imagen,
                        disponible,
                        categoria
                );

        System.out.println(
                "Producto creado con id "
                + producto.getId()
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void editarProducto() {

    try {

        listarProductos();

        System.out.print("Id producto: ");
        Long id =
                Long.parseLong(teclado.nextLine());

        listarCategorias();

        System.out.print("Id categoria: ");
        Long idCategoria =
                Long.parseLong(teclado.nextLine());

        var categoria =
                categoriaService.buscarPorId(idCategoria);

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Precio: ");
        Double precio =
                Double.parseDouble(teclado.nextLine());

        System.out.print("Descripcion: ");
        String descripcion = teclado.nextLine();

        System.out.print("Stock: ");
        Integer stock =
                Integer.parseInt(teclado.nextLine());

        System.out.print("Imagen: ");
        String imagen = teclado.nextLine();

        System.out.print("Disponible (true/false): ");
        boolean disponible =
                Boolean.parseBoolean(teclado.nextLine());

        productoService.editar(
                id,
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        System.out.println(
                "Producto actualizado"
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void eliminarProducto() {

    try {

        listarProductos();

        System.out.print("Id producto: ");
        Long id =
                Long.parseLong(teclado.nextLine());

        System.out.print(
                "Confirma eliminacion? (S/N): "
        );

        String confirmacion =
                teclado.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            productoService.eliminar(id);

            System.out.println(
                    "Producto eliminado"
            );
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void listarCategorias() {

    if (categoriaService.listar().isEmpty()) {

        System.out.println("No hay categorias cargadas");
        return;
    }

    for (var categoria : categoriaService.listar()) {
        System.out.println(categoria);
    }
}

private void crearCategoria() {

    try {

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Descripcion: ");
        String descripcion = teclado.nextLine();

        var categoria =
                categoriaService.crear(
                        nombre,
                        descripcion
                );

        System.out.println(
                "Categoria creada con id "
                + categoria.getId()
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void editarCategoria() {

    try {

        listarCategorias();

        System.out.print("Id: ");
        Long id = Long.parseLong(
                teclado.nextLine()
        );

        System.out.print("Nuevo nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Nueva descripcion: ");
        String descripcion = teclado.nextLine();

        categoriaService.editar(
                id,
                nombre,
                descripcion
        );

        System.out.println(
                "Categoria modificada correctamente"
        );

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

private void eliminarCategoria() {

    try {

        listarCategorias();

        System.out.print("Id: ");
        Long id = Long.parseLong(
                teclado.nextLine()
        );

        System.out.print(
                "Confirma eliminacion? (S/N): "
        );

        String confirmacion =
                teclado.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {

            categoriaService.eliminar(id);

            System.out.println(
                    "Categoria eliminada"
            );
        }

    } catch (Exception e) {

        System.out.println(e.getMessage());

    }
}

}
