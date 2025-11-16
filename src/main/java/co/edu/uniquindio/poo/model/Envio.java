package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;

import co.edu.uniquindio.poo.observer.Subject;
import co.edu.uniquindio.poo.state.EstadoEnvio;
import co.edu.uniquindio.poo.state.EstadoSolicitado;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Modelo de Envío
 * 
 * Representa un envío en el sistema de logística. Contiene toda la información
 * sobre el paquete a entregar incluyendo origen, destino, características físicas,
 * datos del destinatario y estado actual.
 * 
 * Implementa patrones de diseño:
 * - Observer: Para notificar cambios de estado a observadores suscritos
 * - State: Para validar transiciones de estado permitidas
 * 
 * Ciclo de vida de un envío:
 * SOLICITADO -> ASIGNADO -> EN_RUTA -> ENTREGADO (o INCIDENCIA)
 */
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
public class Envio extends Subject {
    
    /** Identificador único del envío en el sistema */
    @NonNull
    private String idEnvio;
    
    /** Dirección de origen del paquete */
    @NonNull
    private Direccion origen;
    
    /** Dirección de destino del paquete */
    @NonNull
    private Direccion destino;
    
    /** Peso del paquete en kilogramos */
    @Builder.Default
    private double peso = 0.0;
    
    /** Largo del paquete en centímetros */
    @Builder.Default
    private double largo = 0.0;
    
    /** Ancho del paquete en centímetros */
    @Builder.Default
    private double ancho = 0.0;
    
    /** Alto del paquete en centímetros */
    @Builder.Default
    private double alto = 0.0;
    
    /** Volumen total en centímetros cúbicos (calculado o ingresado manualmente) */
    @Builder.Default
    private double volumen = 0.0;
    
    /** Descripción del contenido del paquete */
    private String descripcion;
    
    /** Valor declarado para propósitos de aseguramiento en pesos */
    @Builder.Default
    private double valorDeclarado = 0.0;
    
    /** Distancia entre origen y destino en kilómetros */
    @Builder.Default
    private double distancia = 0.0;
    
    /** Nombre completo de la persona que recibirá el paquete */
    private String nombreDestinatario;
    
    /** Número de teléfono del destinatario para contacto en entrega */
    private String telefonoDestinatario;
    
    /** Correo electrónico del destinatario para notificaciones */
    private String emailDestinatario;
    
    /** Categoría de envío que determina tarifa y tiempo de entrega */
    @Builder.Default
    private TipoEnvio tipoEnvio = TipoEnvio.ESTANDAR;
    
    /** Estado actual del envío en su ciclo de vida */
    @Builder.Default
    private EstadoEnvio estado = EstadoEnvio.SOLICITADO;
    
    /** Usuario que realizó el envío */
    @NonNull
    private Usuario usuario;
    
    /** Costo total del envío en pesos */
    @Builder.Default
    private double costo = 0.0;
    
    /** Fecha y hora de creación del envío */
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    /** Fecha y hora estimada de entrega */
    @NonNull
    private LocalDateTime fechaEntregaEstimada;
    
    /** Objeto de estado que maneja las transiciones de estado (patrón State) */
    private co.edu.uniquindio.poo.state.EstadoEnvio estadoActual;
    
    /**
     * Tipos de envío disponibles con tarifas diferenciadas
     */
    public enum TipoEnvio {
        /** Envío estándar con entrega en 3-5 días */
        ESTANDAR,
        /** Envío rápido con entrega en 24 horas, tarifa 50% superior */
        EXPRESS,
        /** Envío para artículos frágiles con manejo especial, tarifa 30% superior */
        FRAGIL
    }
    
    /**
     * Estados posibles de un envío
     */
    public enum EstadoEnvio {
        /** Envío recién creado, pendiente de asignación a repartidor */
        SOLICITADO,
        /** Envío asignado a un repartidor, esperando iniciar ruta */
        ASIGNADO,
        /** Envío en ruta hacia el destino */
        EN_RUTA,
        /** Envío entregado exitosamente */
        ENTREGADO,
        /** Envío con problemas o inconvenientes reportados */
        INCIDENCIA
    }
    
    /**
     * Calcula el volumen total del paquete en centímetros cúbicos.
     * Si el volumen fue ingresado manualmente, lo retorna; si no, lo calcula
     * multiplicando las dimensiones largo x ancho x alto.
     * 
     * @return Volumen en centímetros cúbicos
     */
    public double getVolumen() {
        if (volumen > 0) {
            return volumen;
        }
        return largo * ancho * alto;
    }
    
    /**
     * Cambia el estado actual del envío y notifica a los observadores.
     * Implementa el patrón State para validar transiciones permitidas.
     * 
     * @param nuevoEstado Nuevo objeto de estado a aplicar
     */
    public void setEstadoActual(co.edu.uniquindio.poo.state.EstadoEnvio nuevoEstado) {
        this.estadoActual = nuevoEstado;
        notificarObservadores("Estado del envío cambiado a: " + nuevoEstado.getNombre(), this);
    }
    
    /**
     * Procesa el envío en su estado actual.
     * Delega la acción al objeto de estado correspondiente.
     */
    public void procesarEnvio() {
        if (estadoActual != null) {
            estadoActual.procesar(this);
        }
    }
    
    /**
     * Asigna un repartidor al envío.
     * Transiciona de SOLICITADO a ASIGNADO.
     */
    public void asignarRepartidor() {
        if (estadoActual != null) {
            estadoActual.asignar(this);
        }
    }
    
    /**
     * Inicia la entrega del envío.
     * Transiciona de ASIGNADO a EN_RUTA.
     */
    public void iniciarEntrega() {
        if (estadoActual != null) {
            estadoActual.iniciarRuta(this);
        }
    }
    
    /**
     * Marca el envío como entregado.
     * Transiciona de EN_RUTA a ENTREGADO.
     */
    public void marcarEntregado() {
        if (estadoActual != null) {
            estadoActual.entregar(this);
        }
    }
    
    /**
     * Reporta una incidencia en el envío.
     * Transiciona a INCIDENCIA para investigación.
     */
    public void reportarProblema() {
        if (estadoActual != null) {
            estadoActual.reportarIncidencia(this);
        }
    }
    
    /**
     * Cancela el envío.
     * Solo permitido en estado SOLICITADO.
     */
    public void cancelarEnvio() {
        if (estadoActual != null) {
            estadoActual.cancelar(this);
        }
    }
}
