package co.edu.uniquindio.poo.model;


import java.util.ArrayList;
import java.util.List;

public class SistemaGestion {
    private static SistemaGestion instancia;
    private List<Usuario> usuarios;
    private List<Envio> envios;
    private List<Pago> pagos;
    private List<Incidencia> incidencias;
    private List<Administrador> administradores;
    private List<Repartidor> repartidores;
    
    private SistemaGestion() {
        this.usuarios = new ArrayList<>();
        this.envios = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.incidencias = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.repartidores = new ArrayList<>();
        inicializarDatosPrueba();
    }
    
    private void inicializarDatosPrueba() {
        // Crear usuarios de prueba
        Usuario usuario1 = Usuario.builder()
                .idUsuario("USR001")
                .nombreCompleto("María López")
                .correoElectronico("maria@email.com")
                .telefono("302-555-1234")
                .password("123456")
                .build();

        Usuario usuario2 = Usuario.builder()
                .idUsuario("USR002")
                .nombreCompleto("Juan Pérez")
                .correoElectronico("juan@email.com")
                .telefono("301-789-6543")
                .password("123456")
                .build();
        
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        
        // Crear administrador de prueba
        Administrador admin = Administrador.builder()
                .idAdmin("ADM001")
                .nombre("José Admin")
                .correo("admin@sistema.com")
                .password("admin123")
                .build();
        
        administradores.add(admin);
        
        // Crear repartidores de prueba
        crearRepartidoresPrueba();
        
        // Crear envíos de prueba
        crearEnviosPrueba(usuario1);
    }
    
    private void crearRepartidoresPrueba() {
        Repartidor rep1 = Repartidor.builder()
                .idRepartidor("REP-00000001")
                .nombre("Carlos Ramírez")
                .documento("1234567890")
                .telefono("3101234567")
                .zonaCobertura("Armenia Centro")
                .estado(Repartidor.EstadoRepartidor.ACTIVO)
                .enviosAsignados(0)
                .build();
        
        Repartidor rep2 = Repartidor.builder()
                .idRepartidor("REP-00000002")
                .nombre("Laura Martínez")
                .documento("0987654321")
                .telefono("3159876543")
                .zonaCobertura("Calarcá")
                .estado(Repartidor.EstadoRepartidor.ACTIVO)
                .enviosAsignados(0)
                .build();
        
        Repartidor rep3 = Repartidor.builder()
                .idRepartidor("REP-00000003")
                .nombre("Diego Silva")
                .documento("1122334455")
                .telefono("3201237890")
                .zonaCobertura("Armenia Norte")
                .estado(Repartidor.EstadoRepartidor.INACTIVO)
                .enviosAsignados(0)
                .build();
        
        repartidores.add(rep1);
        repartidores.add(rep2);
        repartidores.add(rep3);
    }
    
    private void crearEnviosPrueba(Usuario usuario) {
        // Crear direcciones
        Direccion origen1 = new Direccion.Builder("DIR001")
                .conAlias("Oficina Centro")
                .conCalle("Calle 10 #15-20")
                .conCiudad("Armenia")
                .conCoordenadas(4.536389, -75.681111)
                .build();
        
        Direccion destino1 = new Direccion.Builder("DIR002")
                .conAlias("Casa Norte")
                .conCalle("Carrera 20 #30-45")
                .conCiudad("Armenia")
                .conCoordenadas(4.540000, -75.675000)
                .build();
        
        Direccion origen2 = new Direccion.Builder("DIR003")
                .conAlias("Tienda Sur")
                .conCalle("Avenida Bolívar #25-30")
                .conCiudad("Armenia")
                .conCoordenadas(4.530000, -75.685000)
                .build();
        
        Direccion destino2 = new Direccion.Builder("DIR004")
                .conAlias("Oficina Centro 2")
                .conCalle("Calle 5 #10-15")
                .conCiudad("Armenia")
                .conCoordenadas(4.535000, -75.680000)
                .build();
        
        // Crear envío solicitado
        Envio envio1 = Envio.builder()
                .idEnvio("ENV001")
                .origen(origen1)
                .destino(destino1)
                .usuario(usuario)
                .peso(2.5)
                .largo(30.0)
                .ancho(20.0)
                .alto(15.0)
                .descripcion("Paquete documentos importantes")
                .nombreDestinatario("Juan Carlos")
                .telefonoDestinatario("315-123-4567")
                .emailDestinatario("juan@email.com")
                .tipoEnvio(Envio.TipoEnvio.ESTANDAR)
                .estado(Envio.EstadoEnvio.SOLICITADO)
                .costo(15000.0)
                .valorDeclarado(50000.0)
                .distancia(5.2)
                .fechaEntregaEstimada(java.time.LocalDateTime.now().plusDays(1))
                .build();
        
        // Crear envío en ruta
        Envio envio2 = Envio.builder()
                .idEnvio("ENV002")
                .origen(origen2)
                .destino(destino2)
                .usuario(usuario)
                .peso(1.0)
                .largo(25.0)
                .ancho(15.0)
                .alto(10.0)
                .descripcion("Productos electrónicos")
                .nombreDestinatario("María Elena")
                .telefonoDestinatario("320-987-6543")
                .emailDestinatario("maria.e@email.com")
                .tipoEnvio(Envio.TipoEnvio.EXPRESS)
                .estado(Envio.EstadoEnvio.EN_RUTA)
                .costo(25000.0)
                .valorDeclarado(200000.0)
                .distancia(3.8)
                .fechaEntregaEstimada(java.time.LocalDateTime.now().plusHours(4))
                .build();
        
        // Crear envío entregado
        Envio envio3 = Envio.builder()
                .idEnvio("ENV003")
                .origen(destino2)
                .destino(origen1)
                .usuario(usuario)
                .peso(0.5)
                .largo(20.0)
                .ancho(10.0)
                .alto(5.0)
                .descripcion("Correspondencia")
                .nombreDestinatario("Ana Torres")
                .telefonoDestinatario("300-555-7890")
                .tipoEnvio(Envio.TipoEnvio.ESTANDAR)
                .estado(Envio.EstadoEnvio.ENTREGADO)
                .costo(8000.0)
                .valorDeclarado(10000.0)
                .distancia(2.1)
                .fechaEntregaEstimada(java.time.LocalDateTime.now().minusHours(2))
                .build();
        
        envios.add(envio1);
        envios.add(envio2);
        envios.add(envio3);
    }
    
    public static SistemaGestion obtenerInstancia() {
        if (instancia == null) {
            instancia = new SistemaGestion();
        }
        return instancia;
    }
    
    // Métodos de gestión
    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    
    public void registrarEnvio(Envio envio) {
        envios.add(envio);
    }
    
    public void registrarPago(Pago pago) {
        pagos.add(pago);
    }
    
    public void registrarIncidencia(Incidencia incidencia) {
        incidencias.add(incidencia);
    }
    
    public void registrarAdministrador(Administrador admin) {
        administradores.add(admin);
    }
    
    public void registrarRepartidor(Repartidor repartidor) {
        repartidores.add(repartidor);
    }
    
    public void eliminarRepartidor(String idRepartidor) {
        repartidores.removeIf(r -> r.getIdRepartidor().equals(idRepartidor));
    }
    
    public Repartidor buscarRepartidorPorId(String idRepartidor) {
        return repartidores.stream()
                .filter(r -> r.getIdRepartidor().equals(idRepartidor))
                .findFirst()
                .orElse(null);
    }
    
    public Envio buscarEnvioPorId(String idEnvio) {
        return envios.stream()
                .filter(e -> e.getIdEnvio().equals(idEnvio))
                .findFirst()
                .orElse(null);
    }
    
    // Getters
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    public List<Envio> getEnvios() {
        return envios;
    }
    
    public List<Pago> getPagos() {
        return pagos;
    }
    
    public List<Incidencia> getIncidencias() {
        return incidencias;
    }
    
    public List<Administrador> getAdministradores() {
        return administradores;
    }
    
    public List<Repartidor> getRepartidores() {
        return repartidores;
    }
    
    public void actualizarEnvio(Envio envio) {
        for (int i = 0; i < envios.size(); i++) {
            if (envios.get(i).getIdEnvio().equals(envio.getIdEnvio())) {
                envios.set(i, envio);
                break;
            }
        }
    }
}
