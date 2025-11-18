package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitarios para UsuarioController
 * Prueba las funcionalidades principales de gestión de usuarios
 */
class UsuarioControllerTest {
    
    private UsuarioController usuarioController;
    
    @BeforeEach
    void setUp() {
        usuarioController = new UsuarioController();
    }
    
    @Test
    @DisplayName("Debe registrar un usuario correctamente")
    void testRegistrarUsuario() {
        Usuario usuario = usuarioController.registrarUsuario(
            "Juan Pérez Test",
            "juan.nuevo.test@email.com",
            "3001234567",
            "password123"
        );
        
        assertNotNull(usuario);
        assertEquals("Juan Pérez Test", usuario.getNombreCompleto());
        assertEquals("juan.nuevo.test@email.com", usuario.getCorreoElectronico());
        assertEquals("3001234567", usuario.getTelefono());
        assertNotNull(usuario.getIdUsuario());
        assertFalse(usuario.getIdUsuario().isEmpty());
    }
    
    @Test
    @DisplayName("Debe buscar usuario por correo")
    void testBuscarUsuarioPorCorreo() {
        // Usar usuario existente del sistema
        var usuarioOpt = usuarioController.buscarUsuarioPorCorreo("juan@email.com");
        
        assertTrue(usuarioOpt.isPresent());
        assertEquals("juan@email.com", usuarioOpt.get().getCorreoElectronico());
    }
    
    @Test
    @DisplayName("Debe fallar búsqueda con correo inexistente")
    void testBuscarUsuarioInexistente() {
        var usuarioOpt = usuarioController.buscarUsuarioPorCorreo("noexiste@email.com");
        
        assertTrue(usuarioOpt.isEmpty());
    }
    
    @Test
    @DisplayName("Debe obtener todos los usuarios")
    void testObtenerTodosUsuarios() {
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }
    
    @Test
    @DisplayName("Debe buscar usuario por ID existente")
    void testBuscarUsuarioPorId() {
        // Obtener un usuario existente
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        if (!usuarios.isEmpty()) {
            String idUsuario = usuarios.get(0).getIdUsuario();
            var usuarioOpt = usuarioController.buscarUsuarioPorId(idUsuario);
            
            assertTrue(usuarioOpt.isPresent());
            assertEquals(idUsuario, usuarioOpt.get().getIdUsuario());
        }
    }
    
    @Test
    @DisplayName("Debe agregar dirección a usuario")
    void testAgregarDireccion() {
        // Obtener un usuario existente
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        if (!usuarios.isEmpty()) {
            String idUsuario = usuarios.get(0).getIdUsuario();
            
            Direccion direccion = new Direccion.Builder("DIR-TEST-001")
                .conAlias("Casa Test")
                .conCalle("Calle 123")
                .conCiudad("Armenia")
                .conCoordenadas(4.5, -75.5)
                .build();
            
            assertDoesNotThrow(() -> usuarioController.agregarDireccion(idUsuario, direccion));
        }
    }
    
    @Test
    @DisplayName("Debe buscar usuario registrado")
    void testBuscarUsuarioRegistrado() {
        // Crear usuario de prueba
        Usuario usuario = usuarioController.registrarUsuario(
            "Test Busqueda",
            "busqueda@test.com",
            "3009999999",
            "pass123"
        );
        
        String idUsuario = usuario.getIdUsuario();
        
        // Buscar usuario
        var usuarioOpt = usuarioController.buscarUsuarioPorId(idUsuario);
        
        assertTrue(usuarioOpt.isPresent());
        assertEquals("Test Busqueda", usuarioOpt.get().getNombreCompleto());
        assertEquals("busqueda@test.com", usuarioOpt.get().getCorreoElectronico());
    }
}
