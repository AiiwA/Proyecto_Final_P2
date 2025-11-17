package co.edu.uniquindio.poo.utils;

import co.edu.uniquindio.poo.controller.*;
import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.factory.EntidadFactory;

public class DataInitializer {
    
    public static void inicializarDatos() {
        UsuarioController usuarioController = new UsuarioController();
        AdminController adminController = new AdminController();
        EnvioController envioController = new EnvioController();
        
        // Verificar si ya hay datos inicializados
        if (!usuarioController.obtenerTodosUsuarios().isEmpty()) {
            System.out.println("✓ Datos de prueba ya están inicializados");
            return;
        }
        
        // Crear usuarios de prueba
        Usuario usuario1 = usuarioController.registrarUsuario(
            "Juan Pérez", 
            "juan@email.com", 
            "3001234567", 
            "123456"
        );
        
        Usuario usuario2 = usuarioController.registrarUsuario(
            "María González", 
            "maria@email.com", 
            "3009876543", 
            "123456"
        );
        
        // Crear administrador
        adminController.registrarAdministrador(
            "Admin Sistema", 
            "admin@sistema.com", 
            "admin123"
        );
        
        // Crear direcciones
        Direccion dir1 = EntidadFactory.crearDireccion(
            "Casa", 
            "Calle 50 #20-30", 
            "Armenia", 
            4.533889, 
            -75.681111
        );
        
        Direccion dir2 = EntidadFactory.crearDireccion(
            "Oficina", 
            "Carrera 14 #10-15", 
            "Armenia", 
            4.540000, 
            -75.675000
        );
        
        Direccion dir3 = EntidadFactory.crearDireccion(
            "Casa María", 
            "Avenida Bolívar #25-40", 
            "Calarcá", 
            4.529167, 
            -75.644722
        );
        
        // Agregar direcciones a usuarios
        usuarioController.agregarDireccion(usuario1.getIdUsuario(), dir1);
        usuarioController.agregarDireccion(usuario1.getIdUsuario(), dir2);
        usuarioController.agregarDireccion(usuario2.getIdUsuario(), dir3);
        
        // Crear envíos de prueba
        Envio envio1 = envioController.crearEnvioEstandar(
            usuario1, dir1, dir2, 
            2.5, 30, 20, 15
        );
        
        envioController.crearEnvioExpress(
            usuario1, dir1, dir3, 
            1.0, 20, 15, 10
        );
        
        envioController.crearEnvioEstandar(
            usuario2, dir3, dir2, 
            3.0, 40, 30, 20
        );
        
        // Cambiar algunos estados
        envioController.iniciarEntrega(envio1.getIdEnvio());
        
        System.out.println("✓ Datos de prueba inicializados correctamente");
        System.out.println("  - Usuarios creados: 2");
        System.out.println("  - Admin: admin@sistema.com / admin123");
        System.out.println("  - Envíos creados: 3");
    }
}
