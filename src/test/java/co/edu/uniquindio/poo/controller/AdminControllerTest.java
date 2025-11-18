package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitarios para AdminController
 * Prueba las funcionalidades principales de administración
 */
class AdminControllerTest {
    
    private AdminController adminController;
    private SistemaGestion sistema;
    
    @BeforeEach
    void setUp() {
        // Reiniciar sistema antes de cada prueba
        sistema = SistemaGestion.obtenerInstancia();
        adminController = new AdminController();
    }
    
    @Test
    @DisplayName("Debe registrar un administrador correctamente")
    void testRegistrarAdministrador() {
        Administrador admin = adminController.registrarAdministrador(
            "Admin Test", 
            "admin.nuevo.test@test.com", 
            "password123"
        );
        
        assertNotNull(admin);
        assertEquals("Admin Test", admin.getNombre());
        assertEquals("admin.nuevo.test@test.com", admin.getCorreo());
        assertNotNull(admin.getIdAdmin());
        assertFalse(admin.getIdAdmin().isEmpty());
    }
    
    @Test
    @DisplayName("Debe obtener métricas del sistema")
    void testObtenerMetricas() {
        var metricas = adminController.obtenerMetricas();
        
        assertNotNull(metricas);
        assertTrue(metricas.containsKey("totalUsuarios"));
        assertTrue(metricas.containsKey("totalEnvios"));
        assertTrue(metricas.containsKey("enviosActivos"));
        assertTrue(metricas.containsKey("ingresoTotal"));
    }
    
    @Test
    @DisplayName("Debe obtener todos los usuarios")
    void testObtenerTodosUsuarios() {
        List<Usuario> usuarios = adminController.obtenerTodosUsuarios();
        
        assertNotNull(usuarios);
        assertFalse(usuarios.isEmpty());
    }
    
    @Test
    @DisplayName("Debe obtener todos los envíos")
    void testObtenerTodosEnvios() {
        List<Envio> envios = adminController.obtenerTodosEnvios();
        
        assertNotNull(envios);
        assertFalse(envios.isEmpty());
    }
    
    @Test
    @DisplayName("Debe registrar un repartidor correctamente")
    void testRegistrarRepartidor() {
        Repartidor repartidor = adminController.registrarRepartidor(
            "Carlos Test",
            "123456789",
            "3001234567",
            "Armenia Centro",
            Repartidor.EstadoRepartidor.ACTIVO
        );
        
        assertNotNull(repartidor);
        assertEquals("Carlos Test", repartidor.getNombre());
        assertEquals("123456789", repartidor.getDocumento());
        assertEquals(Repartidor.EstadoRepartidor.ACTIVO, repartidor.getEstado());
        assertTrue(repartidor.getIdRepartidor().startsWith("REP"));
    }
    
    @Test
    @DisplayName("Debe obtener todos los repartidores")
    void testObtenerTodosRepartidores() {
        List<Repartidor> repartidores = adminController.obtenerTodosRepartidores();
        
        assertNotNull(repartidores);
        assertFalse(repartidores.isEmpty());
    }
    
    @Test
    @DisplayName("Debe eliminar un repartidor sin envíos asignados")
    void testEliminarRepartidor() {
        // Crear repartidor de prueba
        Repartidor repartidor = adminController.registrarRepartidor(
            "Test Eliminar",
            "999999999",
            "3009999999",
            "Zona Test",
            Repartidor.EstadoRepartidor.INACTIVO
        );
        
        String idRepartidor = repartidor.getIdRepartidor();
        
        // Eliminar
        assertDoesNotThrow(() -> adminController.eliminarRepartidor(idRepartidor));
        
        // Verificar que no existe
        List<Repartidor> repartidores = adminController.obtenerTodosRepartidores();
        assertFalse(repartidores.stream().anyMatch(r -> r.getIdRepartidor().equals(idRepartidor)));
    }
    
    @Test
    @DisplayName("Debe asignar repartidor a envío correctamente")
    void testAsignarRepartidorAEnvio() {
        // Obtener un envío y un repartidor existentes
        List<Envio> envios = adminController.obtenerTodosEnvios();
        List<Repartidor> repartidores = adminController.obtenerTodosRepartidores();
        
        if (!envios.isEmpty() && !repartidores.isEmpty()) {
            Envio envio = envios.get(0);
            Repartidor repartidor = repartidores.stream()
                    .filter(r -> r.getEstado() == Repartidor.EstadoRepartidor.ACTIVO)
                    .findFirst()
                    .orElse(null);
            
            if (repartidor != null) {
                assertDoesNotThrow(() -> 
                    adminController.asignarRepartidorAEnvio(envio.getIdEnvio(), repartidor.getIdRepartidor())
                );
            }
        }
    }
}
