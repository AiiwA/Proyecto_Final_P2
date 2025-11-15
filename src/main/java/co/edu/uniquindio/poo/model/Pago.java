package co.edu.uniquindio.poo.model;

import java.time.LocalDateTime;

/**
 * Modelo de Pago de un envío.
 * Registra la transacción financiera asociada a un envío específico.
 * Rastraea el estado del pago y el método utilizado.
 */
public class Pago {
    private final String idPago;
    private double monto;
    private LocalDateTime fecha;
    private MetodoPago metodoPago;
    private EstadoPago estado;
    private Envio envio;
    
    /**
     * Estados posibles de un pago.
     */
    public enum EstadoPago {
        PENDIENTE,  // El pago está en espera de procesamiento
        APROBADO,   // El pago fue procesado exitosamente
        RECHAZADO,  // El pago fue rechazado por el procesador
        CANCELADO   // El pago fue cancelado por el usuario
    }
    
    /**
     * Constructor privado que inicializa el pago con los valores del builder.
     * Solo accesible a través del patrón Builder.
     * 
     * @param builder Objeto Builder con los valores configurados
     */
    private Pago(Builder builder) {
        this.idPago = builder.idPago;
        this.monto = builder.monto;
        this.fecha = builder.fecha;
        this.metodoPago = builder.metodoPago;
        this.estado = EstadoPago.PENDIENTE;
        this.envio = builder.envio;
    }
    
    // Builder Pattern
    public static class Builder {
        private String idPago;
        private double monto;
        private LocalDateTime fecha;
        private MetodoPago metodoPago;
        private Envio envio;
        
        /**
         * Constructor del Builder con el ID del pago.
         * 
         * @param idPago Identificador único del pago
         */
        public Builder(String idPago) {
            this.idPago = idPago;
        }
        
        /**
         * Establece el monto del pago.
         * 
         * @param monto Cantidad de dinero a pagar
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conMonto(double monto) {
            this.monto = monto;
            return this;
        }
        
        /**
         * Establece la fecha y hora del pago.
         * 
         * @param fecha Fecha en que se realizó o realizará el pago
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conFecha(LocalDateTime fecha) {
            this.fecha = fecha;
            return this;
        }
        
        /**
         * Asocia el pago a un método de pago específico.
         * 
         * @param metodoPago Método de pago utilizado
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conMetodoPago(MetodoPago metodoPago) {
            this.metodoPago = metodoPago;
            return this;
        }
        
        /**
         * Asocia el pago a un envío específico.
         * 
         * @param envio Envío cuyo pago se registra
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conEnvio(Envio envio) {
            this.envio = envio;
            return this;
        }
        
        /**
         * Construye y retorna el pago con los valores establecidos.
         * 
         * @return Nuevo Pago completamente inicializado
         */
        public Pago build() {
            return new Pago(this);
        }
    }
    
    /**
     * Obtiene el identificador único de este pago.
     * 
     * @return ID del pago
     */
    public String getIdPago() {
        return idPago;
    }
    
    /**
     * Obtiene el monto del pago.
     * 
     * @return Cantidad de dinero del pago
     */
    public double getMonto() {
        return monto;
    }
    
    /**
     * Obtiene la fecha y hora en que se realizó o realizará el pago.
     * 
     * @return Fecha del pago
     */
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    /**
     * Obtiene el método de pago utilizado en esta transacción.
     * 
     * @return Método de pago
     */
    public MetodoPago getMetodoPago() {
        return metodoPago;
    }
    
    /**
     * Obtiene el estado actual del pago.
     * 
     * @return Estado del pago (PENDIENTE, APROBADO, RECHAZADO, CANCELADO)
     */
    public EstadoPago getEstado() {
        return estado;
    }
    
    /**
     * Actualiza el estado del pago.
     * Permite cambiar el estado a medida que se procesa la transacción.
     * 
     * @param estado Nuevo estado del pago
     */
    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }
    
    /**
     * Obtiene el envío asociado a este pago.
     * 
     * @return Envío cuyo costo se pagó
     */
    public Envio getEnvio() {
        return envio;
    }
}
