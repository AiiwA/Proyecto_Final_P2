package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.factory.EnvioFactory;
import co.edu.uniquindio.poo.adapter.MapasAdapter;
import co.edu.uniquindio.poo.adapter.ServicioDistancia;
import co.edu.uniquindio.poo.command.*;
import co.edu.uniquindio.poo.state.EstadoSolicitado;
import co.edu.uniquindio.poo.observer.NotificadorUsuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador de Envíos
 * 
 * Gestiona la lógica de negocio relacionada con envíos. Proporciona funcionalidades para:
 * - Crear envíos (estándar, express, con validación)
 * - Calcular costos basados en peso, volumen y distancia
 * - Cambiar estado de envíos
 * - Reportar incidencias
 * - Administrar operaciones con patrón Command (deshacer/rehacer)
 * 
 * Utiliza patrones de diseño como Factory, Adapter, Command y State para mantener
 * una arquitectura flexible y mantenible.
 */
public class EnvioController {
    private SistemaGestion sistema;
    private ServicioDistancia servicioDistancia;
    private GestorComandos gestorComandos;
    
    /**
     * Constructor que inicializa el controlador con las dependencias necesarias.
     * Obtiene instancia única de SistemaGestion, inicializa adaptador de mapas
     * y gestor de comandos para operaciones reversibles.
     */
    public EnvioController() {
        this.sistema = SistemaGestion.obtenerInstancia();
        this.servicioDistancia = new MapasAdapter();
        this.gestorComandos = new GestorComandos();
    }
    
    /**
     * Crea un nuevo envío en el sistema con validación de usuario.
     * 
     * @param idUsuario Identificador único del usuario propietario del envío
     * @param origen Dirección de origen del envío
     * @param destino Dirección de destino del envío
     * @param tipo Tipo de envío (ESTANDAR, EXPRESS, FRAGIL)
     * @param peso Peso del paquete en kilogramos
     * @param volumen Volumen del paquete en centímetros cúbicos
     * @param descripcion Descripción del contenido del paquete
     * @return Objeto Envio creado y registrado en el sistema
     * @throws RuntimeException Si el usuario no existe en el sistema
     */
    public Envio crearEnvio(String idUsuario, Direccion origen, Direccion destino,
                           Envio.TipoEnvio tipo, double peso, double volumen, String descripcion) {
        // Buscar usuario
        Usuario usuario = sistema.getUsuarios().stream()
                .filter(u -> u.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
        
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        // Crear envío usando builder
        Envio envio = Envio.builder()
                .idEnvio(java.util.UUID.randomUUID().toString())
                .usuario(usuario)
                .origen(origen)
                .destino(destino)
                .tipoEnvio(tipo)
                .peso(peso)
                .volumen(volumen)
                .descripcion(descripcion)
                .fechaEntregaEstimada(java.time.LocalDateTime.now().plusDays(tipo == Envio.TipoEnvio.EXPRESS ? 1 : 3))
                .build();
        
        // Inicializar estado y observadores
        envio.setEstadoActual(new EstadoSolicitado());
        envio.agregarObservador(new NotificadorUsuario(usuario.getIdUsuario(), usuario.getNombreCompleto()));
        
        sistema.registrarEnvio(envio);
        return envio;
    }
    
    /**
     * Crea un envío de tipo estándar con tarifa base.
     * Calcula automáticamente el costo según peso, volumen y distancia.
     * 
     * @param usuario Usuario propietario del envío
     * @param origen Punto de partida
     * @param destino Punto de llegada
     * @param peso Peso en kilogramos
     * @param largo Largo en centímetros
     * @param ancho Ancho en centímetros
     * @param alto Alto en centímetros
     * @return Envio creado con estado SOLICITADO
     */
    public Envio crearEnvioEstandar(Usuario usuario, Direccion origen, Direccion destino,
                                     double peso, double largo, double ancho, double alto) {
        Envio envio = EnvioFactory.crearEnvioEstandar(usuario, origen, destino, peso, largo, ancho, alto);
        
        // Calcular costo basado en distancia, peso y volumen
        double distancia = servicioDistancia.calcularDistancia(origen, destino);
        double volumen = envio.getVolumen();
        double costo = calcularCosto(peso, volumen, distancia, 1.0);
        envio.setCosto(costo);
        
        // Inicializar estado y observadores
        envio.setEstadoActual(new EstadoSolicitado());
        envio.agregarObservador(new NotificadorUsuario(usuario.getIdUsuario(), usuario.getNombreCompleto()));
        
        sistema.registrarEnvio(envio);
        return envio;
    }
    
    /**
     * Crea un envío de tipo express con tarifa 50% superior.
     * Garantiza entrega en 24 horas con costo incrementado.
     * 
     * @param usuario Usuario propietario del envío
     * @param origen Punto de partida
     * @param destino Punto de llegada
     * @param peso Peso en kilogramos
     * @param largo Largo en centímetros
     * @param ancho Ancho en centímetros
     * @param alto Alto en centímetros
     * @return Envio creado con tipo EXPRESS
     */
    public Envio crearEnvioExpress(Usuario usuario, Direccion origen, Direccion destino,
                                    double peso, double largo, double ancho, double alto) {
        Envio envio = EnvioFactory.crearEnvioExpress(usuario, origen, destino, peso, largo, ancho, alto);
        
        double distancia = servicioDistancia.calcularDistancia(origen, destino);
        double volumen = envio.getVolumen();
        double costo = calcularCosto(peso, volumen, distancia, 1.5); // 50% más caro
        envio.setCosto(costo);
        
        envio.setEstadoActual(new EstadoSolicitado());
        envio.agregarObservador(new NotificadorUsuario(usuario.getIdUsuario(), usuario.getNombreCompleto()));
        
        sistema.registrarEnvio(envio);
        return envio;
    }
    
    /**
     * Calcula el costo total de un envío basado en múltiples factores.
     * 
     * Tarificación:
     * - Tarifa base: $3,500 (mínimo)
     * - Costo por peso: $150 por kilogramo
     * - Costo por volumen: $50 por decímetro cúbico (litro)
     * - Costo por distancia: $50 por kilómetro
     * - Factor de prioridad:
     *   - ESTANDAR: 1.0x
     *   - EXPRESS: 1.5x (50% más caro)
     *   - FRAGIL: 1.3x (30% más caro)
     * 
     * @param peso Peso del paquete en kilogramos
     * @param volumen Volumen en centímetros cúbicos
     * @param distancia Distancia en kilómetros
     * @param tipo Tipo de envío que determina el factor de prioridad
     * @return Costo total calculado en pesos colombianos
     */
    public double calcularCostoEnvio(double peso, double volumen, double distancia, Envio.TipoEnvio tipo) {
        // Tarifas realistas para un sistema de envíos colombiano
        double tarifaBase = 3500.0; // Tarifa base mínima
        double costoPeso = peso * 150.0; // $150 por kg
        
        // Convertir volumen de cm³ a dm³ (litros) para cargo por volumen
        double volumenDM3 = volumen / 1000.0; // 1 dm³ = 1000 cm³
        double costoVolumen = volumenDM3 * 50.0; // $50 por dm³
        
        double costoDistancia = distancia * 50.0; // $50 por km
        
        double factorPrioridad = 1.0;
        switch (tipo) {
            case EXPRESS:
                factorPrioridad = 1.5; // 50% más caro
                break;
            case FRAGIL:
                factorPrioridad = 1.3; // 30% más caro
                break;
            default:
                factorPrioridad = 1.0;
        }
        
        double costoTotal = tarifaBase + costoPeso + costoVolumen + costoDistancia;
        return costoTotal * factorPrioridad;
    }
    
    /**
     * Calcula el costo con un factor de prioridad personalizado.
     * Método interno utilizado por crearEnvioEstandar y crearEnvioExpress.
     * 
     * @param peso Peso en kilogramos
     * @param volumen Volumen en centímetros cúbicos
     * @param distancia Distancia en kilómetros
     * @param factorPrioridad Factor multiplicador (1.0 = tarifa base, 1.5 = 50% más)
     * @return Costo total calculado
     */
    private double calcularCosto(double peso, double volumen, double distancia, double factorPrioridad) {
        // Tarifas realistas para un sistema de envíos colombiano
        double tarifaBase = 3500.0; // Tarifa base mínima
        double costoPeso = peso * 150.0; // $150 por kg
        
        // Convertir volumen de cm³ a dm³ (litros) para cargo por volumen
        double volumenDM3 = volumen / 1000.0; // 1 dm³ = 1000 cm³
        double costoVolumen = volumenDM3 * 50.0; // $50 por dm³
        
        double costoDistancia = distancia * 50.0; // $50 por km
        
        double costoTotal = tarifaBase + costoPeso + costoVolumen + costoDistancia;
        return costoTotal * factorPrioridad;
    }
    
    /**
     * Cancela un envío encontrado por su identificador.
     * Utiliza el patrón Command para permitir deshacer la cancelación.
     * Solo puede cancelarse un envío en estado SOLICITADO.
     * 
     * @param idEnvio Identificador único del envío a cancelar
     */
    public void cancelarEnvio(String idEnvio) {
        buscarEnvioPorId(idEnvio).ifPresent(envio -> {
            Command comando = new CancelarEnvioCommand(envio);
            gestorComandos.ejecutarComando(comando);
            envio.cancelarEnvio();
        });
    }
    
    /**
     * Actualiza el estado de un envío.
     * Utiliza el patrón State para validar transiciones permitidas.
     * 
     * Transiciones válidas:
     * - SOLICITADO -> ASIGNADO -> EN_RUTA -> ENTREGADO
     * - Cualquier estado -> INCIDENCIA (y de INCIDENCIA -> ASIGNADO)
     * 
     * @param idEnvio Identificador del envío
     * @param nuevoEstado Nuevo estado a aplicar
     * @throws IllegalStateException Si la transición no es válida
     */
    public void actualizarEstado(String idEnvio, Envio.EstadoEnvio nuevoEstado) {
        buscarEnvioPorId(idEnvio).ifPresent(envio -> {
            Command comando = new ActualizarEstadoCommand(envio, nuevoEstado);
            gestorComandos.ejecutarComando(comando);
        });
    }
    
    /**
     * Inicia la entrega de un envío.
     * Transiciona el envío del estado ASIGNADO a EN_RUTA.
     * 
     * @param idEnvio Identificador del envío a iniciar
     */
    public void iniciarEntrega(String idEnvio) {
        buscarEnvioPorId(idEnvio).ifPresent(Envio::iniciarEntrega);
    }
    
    /**
     * Marca un envío como entregado.
     * Transiciona el envío de EN_RUTA a ENTREGADO.
     * Notifica al usuario y registra la finalización del envío.
     * 
     * @param idEnvio Identificador del envío a marcar como entregado
     */
    public void marcarComoEntregado(String idEnvio) {
        buscarEnvioPorId(idEnvio).ifPresent(Envio::marcarEntregado);
    }
    
    /**
     * Reporta una incidencia en un envío.
     * Crea un registro de incidencia asociado al envío y lo pasa a estado INCIDENCIA.
     * Las incidencias pueden incluir daños, retrasos, o errores en la dirección.
     * 
     * @param idEnvio Identificador del envío con incidencia
     * @param descripcion Descripción detallada del problema
     */
    public void reportarIncidencia(String idEnvio, String descripcion) {
        buscarEnvioPorId(idEnvio).ifPresent(envio -> {
            envio.reportarProblema();
            Incidencia incidencia = new Incidencia.Builder(java.util.UUID.randomUUID().toString())
                    .conDescripcion(descripcion)
                    .conEnvio(envio)
                    .build();
            sistema.registrarIncidencia(incidencia);
        });
    }
    
    /**
     * Busca un envío por su identificador único.
     * 
     * @param id Identificador del envío
     * @return Optional conteniendo el envío si existe, Optional vacío en caso contrario
     */
    public Optional<Envio> buscarEnvioPorId(String id) {
        return sistema.getEnvios().stream()
                .filter(e -> e.getIdEnvio().equals(id))
                .findFirst();
    }
    
    /**
     * Obtiene la lista completa de todos los envíos registrados en el sistema.
     * 
     * @return Lista de todos los envíos
     */
    public List<Envio> obtenerTodosEnvios() {
        return sistema.getEnvios();
    }
    
    /**
     * Filtra envíos por estado específico.
     * 
     * @param estado Estado a filtrar (SOLICITADO, ASIGNADO, EN_RUTA, ENTREGADO, INCIDENCIA)
     * @return Lista de envíos que coinciden con el estado especificado
     */
    public List<Envio> filtrarPorEstado(Envio.EstadoEnvio estado) {
        return sistema.getEnvios().stream()
                .filter(e -> e.getEstado() == estado)
                .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todos los envíos que aún no han sido asignados a un repartidor.
     * Estos son envíos en estado SOLICITADO.
     * 
     * @return Lista de envíos pendientes de asignación
     */
    public List<Envio> obtenerEnviosPendientesAsignacion() {
        return sistema.getEnvios().stream()
                .filter(e -> e.getEstado() == Envio.EstadoEnvio.SOLICITADO)
                .collect(Collectors.toList());
    }
    
    /**
     * Deshace la última operación realizada en un envío.
     * Utiliza el patrón Command para revertir cambios.
     * Solo funciona con operaciones que implementan Command (cancelación, cambio de estado).
     */
    public void deshacerUltimaOperacion() {
        gestorComandos.deshacerUltimoComando();
    }
    
    /**
     * Rehace la última operación que fue deshecha.
     * Permite avanzar nuevamente en el historial de operaciones.
     */
    public void rehacerUltimaOperacion() {
        gestorComandos.rehacerUltimoComando();
    }
    
    /**
     * Valida los datos de un nuevo envío antes de su creación.
     * Realiza validaciones exhaustivas en todos los campos obligatorios y opcionales.
     * 
     * Validaciones incluyen:
     * - Descripción: Obligatoria, máximo 200 caracteres
     * - Peso: Mayor a 0 kg, máximo 1000 kg
     * - Volumen: Mayor a 0, máximo 1,000,000 cm³
     * - Valor declarado: No negativo, máximo $100,000,000
     * - Direcciones: Ciudades obligatorias, coordenadas válidas, diferentes entre sí
     * - Destinatario: Nombre (3+ caracteres), teléfono (7-10 dígitos), email válido si existe
     * 
     * @param descripcion Descripción del paquete
     * @param peso Peso en kilogramos
     * @param volumen Volumen en centímetros cúbicos
     * @param valorDeclarado Valor asegurado en pesos
     * @param origen Dirección de origen
     * @param destino Dirección de destino
     * @param nombreDestinatario Nombre completo del destinatario
     * @param telefonoDestinatario Teléfono del destinatario
     * @param emailDestinatario Email del destinatario (opcional)
     * @throws IllegalArgumentException Con descripción de todos los errores encontrados
     */
    public void validarDatosEnvio(String descripcion, double peso, double volumen, double valorDeclarado,
                                   Direccion origen, Direccion destino, 
                                   String nombreDestinatario, String telefonoDestinatario, String emailDestinatario) {
        StringBuilder errores = new StringBuilder();
        
        // Validar descripción
        if (descripcion == null || descripcion.trim().isEmpty()) {
            errores.append("- La descripción del paquete es obligatoria\n");
        } else if (descripcion.length() > 200) {
            errores.append("- La descripción no puede exceder 200 caracteres\n");
        }
        
        // Validar peso
        if (peso <= 0) {
            errores.append("- El peso debe ser mayor a 0 kg\n");
        } else if (peso > 1000) {
            errores.append("- El peso máximo permitido es 1000 kg\n");
        }
        
        // Validar volumen
        if (volumen <= 0) {
            errores.append("- El volumen debe ser mayor a 0 cm³\n");
        } else if (volumen > 1000000) { // 1 metro cúbico
            errores.append("- El volumen máximo permitido es 1,000,000 cm³ (1 m³)\n");
        }
        
        // Validar valor declarado
        if (valorDeclarado < 0) {
            errores.append("- El valor declarado no puede ser negativo\n");
        } else if (valorDeclarado > 100000000) { // 100 millones
            errores.append("- El valor declarado máximo es $100,000,000\n");
        }
        
        // Validar direcciones
        if (origen == null) {
            errores.append("- La dirección de origen es obligatoria\n");
        } else {
            if (origen.getCiudad() == null || origen.getCiudad().trim().isEmpty()) {
                errores.append("- La ciudad de origen es obligatoria\n");
            }
            if (origen.getLatitud() == 0 && origen.getLongitud() == 0) {
                errores.append("- Las coordenadas de origen son obligatorias\n");
            }
        }
        
        if (destino == null) {
            errores.append("- La dirección de destino es obligatoria\n");
        } else {
            if (destino.getCiudad() == null || destino.getCiudad().trim().isEmpty()) {
                errores.append("- La ciudad de destino es obligatoria\n");
            }
            if (destino.getLatitud() == 0 && destino.getLongitud() == 0) {
                errores.append("- Las coordenadas de destino son obligatorias\n");
            }
        }
        
        // Validar que origen y destino no sean el mismo
        if (origen != null && destino != null) {
            if (Math.abs(origen.getLatitud() - destino.getLatitud()) < 0.0001 && 
                Math.abs(origen.getLongitud() - destino.getLongitud()) < 0.0001) {
                errores.append("- El origen y destino no pueden ser el mismo lugar\n");
            }
        }
        
        // Validar destinatario
        if (nombreDestinatario == null || nombreDestinatario.trim().isEmpty()) {
            errores.append("- El nombre del destinatario es obligatorio\n");
        } else if (nombreDestinatario.length() < 3) {
            errores.append("- El nombre del destinatario debe tener al menos 3 caracteres\n");
        } else if (!nombreDestinatario.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            errores.append("- El nombre del destinatario solo puede contener letras y espacios\n");
        }
        
        if (telefonoDestinatario == null || telefonoDestinatario.trim().isEmpty()) {
            errores.append("- El teléfono del destinatario es obligatorio\n");
        } else if (!telefonoDestinatario.matches("^[0-9]{7,10}$")) {
            errores.append("- El teléfono debe tener entre 7 y 10 dígitos\n");
        }
        
        if (emailDestinatario != null && !emailDestinatario.trim().isEmpty()) {
            if (!emailDestinatario.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                errores.append("- El formato del email no es válido\n");
            }
        }
        
        if (errores.length() > 0) {
            throw new IllegalArgumentException(errores.toString());
        }
    }
    
    /**
     * Elimina un envío del sistema.
     * Solo se pueden eliminar envíos que no estén en estado EN_RUTA o ENTREGADO.
     * 
     * @param idEnvio Identificador único del envío a eliminar
     * @return true si el envío fue eliminado, false si no se encontró
     * @throws IllegalStateException Si el envío no puede ser eliminado por su estado
     */
    public boolean eliminarEnvio(String idEnvio) {
        Optional<Envio> envioOpt = sistema.getEnvios().stream()
                .filter(e -> e.getIdEnvio().equals(idEnvio))
                .findFirst();
        
        if (envioOpt.isPresent()) {
            Envio envio = envioOpt.get();
            
            // Verificar que el envío no esté en un estado que impida su eliminación
            if (envio.getEstado() == Envio.EstadoEnvio.EN_RUTA) {
                throw new IllegalStateException("No se puede eliminar un envío que está en ruta");
            }
            
            if (envio.getEstado() == Envio.EstadoEnvio.ENTREGADO) {
                throw new IllegalStateException("No se puede eliminar un envío que ya fue entregado");
            }
            
            sistema.getEnvios().remove(envio);
            return true;
        }
        return false;
    }
}
