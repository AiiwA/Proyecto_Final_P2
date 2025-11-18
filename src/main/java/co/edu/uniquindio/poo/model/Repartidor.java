package co.edu.uniquindio.poo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;

/**
 * Modelo de Repartidor
 * 
 * Representa un repartidor en el sistema que puede ser asignado a envíos.
 */
@Getter
@Setter
@Builder
public class Repartidor {
    @NonNull
    private final String idRepartidor;
    
    @NonNull
    private String nombre;
    
    @NonNull
    private String documento;
    
    @NonNull
    private String telefono;
    
    @NonNull
    @Builder.Default
    private EstadoRepartidor estado = EstadoRepartidor.ACTIVO;
    
    @NonNull
    private String zonaCobertura;
    
    private int enviosAsignados;
    
    /**
     * Estados posibles de un repartidor
     */
    public enum EstadoRepartidor {
        ACTIVO,
        INACTIVO,
        EN_RUTA
    }
    
    /**
     * Constructor completo usando el patrón Builder de Lombok
     */
    private Repartidor(String idRepartidor, String nombre, String documento, String telefono, 
                       EstadoRepartidor estado, String zonaCobertura, int enviosAsignados) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.estado = estado;
        this.zonaCobertura = zonaCobertura;
        this.enviosAsignados = enviosAsignados;
    }
    
    /**
     * Incrementa el contador de envíos asignados
     */
    public void incrementarEnviosAsignados() {
        this.enviosAsignados++;
    }
    
    /**
     * Decrementa el contador de envíos asignados
     */
    public void decrementarEnviosAsignados() {
        if (this.enviosAsignados > 0) {
            this.enviosAsignados--;
        }
    }
    
    /**
     * Verifica si el repartidor está disponible para asignar envíos
     */
    public boolean isDisponible() {
        return estado == EstadoRepartidor.ACTIVO;
    }
    
    @Override
    public String toString() {
        return nombre + " - " + zonaCobertura + " (" + estado + ")";
    }
}
