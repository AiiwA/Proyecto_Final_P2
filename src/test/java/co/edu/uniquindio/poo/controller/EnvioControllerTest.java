package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.factory.EntidadFactory;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitarios para EnvioController
 * Prueba las funcionalidades principales de gestión de envíos
 */
class EnvioControllerTest {
    
    private EnvioController envioController;
    private UsuarioController usuarioController;
    private Usuario usuarioTest;
    private Direccion origen;
    private Direccion destino;
    
    @BeforeEach
    void setUp() {
        envioController = new EnvioController();
        usuarioController = new UsuarioController();
        
        // Obtener usuario existente
        List<Usuario> usuarios = usuarioController.obtenerTodosUsuarios();
        if (!usuarios.isEmpty()) {
            usuarioTest = usuarios.get(0);
        }
        
        // Crear direcciones de prueba
        origen = EntidadFactory.crearDireccion(
            "Origen Test",
            "Calle 10",
            "Armenia",
            4.536389,
            -75.681111
        );
        
        destino = EntidadFactory.crearDireccion(
            "Destino Test",
            "Calle 20",
            "Armenia",
            4.540000,
            -75.675000
        );
    }
    
    @Test
    @DisplayName("Debe crear envío estándar correctamente")
    void testCrearEnvioEstandar() {
        if (usuarioTest != null) {
            Envio envio = envioController.crearEnvioEstandar(
                usuarioTest,
                origen,
                destino,
                2.5,  // peso
                30,   // largo
                20,   // ancho
                15    // alto
            );
            
            assertNotNull(envio);
            assertEquals(Envio.TipoEnvio.ESTANDAR, envio.getTipoEnvio());
            assertEquals(2.5, envio.getPeso());
            assertEquals(usuarioTest, envio.getUsuario());
            assertNotNull(envio.getIdEnvio());
            assertFalse(envio.getIdEnvio().isEmpty());
        }
    }
    
    @Test
    @DisplayName("Debe crear envío express correctamente")
    void testCrearEnvioExpress() {
        if (usuarioTest != null) {
            Envio envio = envioController.crearEnvioExpress(
                usuarioTest,
                origen,
                destino,
                1.0,  // peso
                25,   // largo
                15,   // ancho
                10    // alto
            );
            
            assertNotNull(envio);
            assertEquals(Envio.TipoEnvio.EXPRESS, envio.getTipoEnvio());
            assertEquals(1.0, envio.getPeso());
        }
    }
    
    @Test
    @DisplayName("Debe obtener todos los envíos")
    void testObtenerTodosEnvios() {
        List<Envio> envios = envioController.obtenerTodosEnvios();
        
        assertNotNull(envios);
        assertFalse(envios.isEmpty());
    }
    
    @Test
    @DisplayName("Debe buscar envío por ID existente")
    void testBuscarEnvioPorId() {
        List<Envio> envios = envioController.obtenerTodosEnvios();
        if (!envios.isEmpty()) {
            String idEnvio = envios.get(0).getIdEnvio();
            var envioOpt = envioController.buscarEnvioPorId(idEnvio);
            
            assertTrue(envioOpt.isPresent());
            assertEquals(idEnvio, envioOpt.get().getIdEnvio());
        }
    }
    
    @Test
    @DisplayName("Debe obtener todos los envíos del sistema")
    void testObtenerEnviosSistema() {
        List<Envio> envios = envioController.obtenerTodosEnvios();
        
        assertNotNull(envios);
        // Verificar que hay envíos cargados
        assertFalse(envios.isEmpty());
    }
    
    @Test
    @DisplayName("Debe crear envío con estado SOLICITADO")
    void testEstadoInicialEnvio() {
        if (usuarioTest != null) {
            Envio envio = envioController.crearEnvioEstandar(
                usuarioTest,
                origen,
                destino,
                3.0,
                35,
                25,
                20
            );
            
            assertNotNull(envio);
            assertEquals(Envio.EstadoEnvio.SOLICITADO, envio.getEstado());
        }
    }
    
    @Test
    @DisplayName("Debe calcular costo basado en peso y distancia")
    void testCalculoCosto() {
        if (usuarioTest != null) {
            Envio envio = envioController.crearEnvioEstandar(
                usuarioTest,
                origen,
                destino,
                5.0,  // 5 kg
                40,
                30,
                20
            );
            
            assertNotNull(envio);
            assertTrue(envio.getCosto() > 0);
            // El costo debe aumentar con el peso y la distancia
        }
    }
    
    @Test
    @DisplayName("Debe verificar tipos de envío")
    void testTiposEnvio() {
        if (usuarioTest != null) {
            // Crear envío estándar
            Envio envioEstandar = envioController.crearEnvioEstandar(
                usuarioTest, origen, destino, 2.0, 30, 20, 15
            );
            
            // Crear envío express
            Envio envioExpress = envioController.crearEnvioExpress(
                usuarioTest, origen, destino, 1.5, 25, 15, 10
            );
            
            assertNotEquals(envioEstandar.getTipoEnvio(), envioExpress.getTipoEnvio());
            assertEquals(Envio.TipoEnvio.ESTANDAR, envioEstandar.getTipoEnvio());
            assertEquals(Envio.TipoEnvio.EXPRESS, envioExpress.getTipoEnvio());
        }
    }
}
