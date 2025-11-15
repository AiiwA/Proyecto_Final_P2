package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;

/**
 * Modelo de Incidencia en un envío.
 * Registra problemas o incidentes ocurridos durante el proceso de envío.
 * Permite rastrear issues desde su reporte hasta su resolución.
 */
public class Incidencia {
    private final String idIncidencia;
    private String descripcion;
    private LocalDateTime fecha;
    private EstadoIncidencia estado;
    private Envio envio;
    
    /**
     * Estados posibles de una incidencia durante su ciclo de vida.
     */
    public enum EstadoIncidencia {
        REPORTADA,   // La incidencia acaba de ser reportada
        EN_REVISION, // Se está investigando el problema
        RESUELTA,    // El problema ha sido solucionado
        CANCELADA    // La incidencia fue cancelada
    }
    
    /**
     * Constructor privado que inicializa la incidencia con los valores del builder.
     * Solo accesible a través del patrón Builder.
     * 
     * @param builder Objeto Builder con los valores configurados
     */
    private Incidencia(Builder builder) {
        this.idIncidencia = builder.idIncidencia;
        this.descripcion = builder.descripcion;
        this.fecha = builder.fecha;
        this.estado = EstadoIncidencia.REPORTADA;
        this.envio = builder.envio;
    }
    
    // Builder Pattern
    public static class Builder {
        private String idIncidencia;
        private String descripcion;
        private LocalDateTime fecha;
        private Envio envio;
        
        /**
         * Constructor del Builder con el ID de la incidencia.
         * 
         * @param idIncidencia Identificador único de la incidencia
         */
        public Builder(String idIncidencia) {
            this.idIncidencia = idIncidencia;
        }
        
        /**
         * Establece la descripción detallada del problema.
         * 
         * @param descripcion Descripción del incidente
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }
        
        /**
         * Establece la fecha y hora en que ocurrió la incidencia.
         * Si no se establece, se usa la fecha actual.
         * 
         * @param fecha Fecha de la incidencia
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conFecha(LocalDateTime fecha) {
            this.fecha = fecha;
            return this;
        }
        
        /**
         * Asocia la incidencia a un envío específico.
         * 
         * @param envio Envío afectado por la incidencia
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conEnvio(Envio envio) {
            this.envio = envio;
            return this;
        }
        
        /**
         * Construye y retorna la incidencia con los valores establecidos.
         * 
         * @return Nueva Incidencia completamente inicializada
         */
        public Incidencia build() {
            return new Incidencia(this);
        }
    }
    
    /**
     * Obtiene el identificador único de esta incidencia.
     * 
     * @return ID de la incidencia
     */
    public String getIdIncidencia() {
        return idIncidencia;
    }
    
    /**
     * Obtiene la descripción del problema o incidente.
     * 
     * @return Descripción de la incidencia
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Obtiene la fecha y hora en que fue reportada la incidencia.
     * 
     * @return Fecha de la incidencia
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    /**
     * Obtiene el estado actual de la incidencia.
     * 
     * @return Estado de la incidencia
     */
    public EstadoIncidencia getEstado() {
        return estado;
    }
    
    /**
     * Actualiza el estado de la incidencia.
     * Permite cambiar el estado a medida que se investiga y resuelve.
     * 
     * @param estado Nuevo estado de la incidencia
     */
    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }
    
    /**
     * Obtiene el envío afectado por esta incidencia.
     * 
     * @return Envío asociado a la incidencia
     */
    public Envio getEnvio() {
        return envio;
    }
}
