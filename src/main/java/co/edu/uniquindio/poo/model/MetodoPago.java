package co.edu.uniquindio.demop2pf.model;

import co.edu.uniquindio.demop2pf.strategy.EstrategiaPago;

/**
 * Modelo de Método de Pago.
 * Representa una forma de pago del usuario asociada a una estrategia específica.
 * Utiliza el patrón Strategy para manejar diferentes tipos de pagos.
 */
public class MetodoPago {
    private final String idMetodoPago;
    private final EstrategiaPago estrategia;
    
    /**
     * Constructor privado que inicializa el método de pago con su estrategia.
     * Solo accesible a través del patrón Builder.
     * 
     * @param builder Objeto Builder con los valores configurados
     */
    private MetodoPago(Builder builder) {
        this.idMetodoPago = builder.idMetodoPago;
        this.estrategia = builder.estrategia;
    }
    
    /**
     * Procesa un pago utilizando la estrategia configurada.
     * Delega la lógica de procesamiento a la implementación específica de pago.
     * 
     * @param monto Cantidad de dinero a procesar
     * @return true si el pago fue exitoso, false en caso contrario
     */
    public boolean procesarPago(double monto) {
        return estrategia.procesarPago(monto);
    }
    
    /**
     * Obtiene el tipo de método de pago (ej: "Tarjeta de Crédito", "PayPal").
     * 
     * @return Descripción del tipo de pago
     */
    public String getTipo() {
        return estrategia.getTipo();
    }
    
    // Builder Pattern
    public static class Builder {
        private String idMetodoPago;
        private EstrategiaPago estrategia;
        
        /**
         * Constructor del Builder con el ID del método de pago.
         * 
         * @param idMetodoPago Identificador único del método de pago
         */
        public Builder(String idMetodoPago) {
            this.idMetodoPago = idMetodoPago;
        }
        
        /**
         * Establece la estrategia de pago a utilizar.
         * Define cómo se procesará el pago (tarjeta, transferencia, etc.).
         * 
         * @param estrategia Implementación de EstrategiaPago
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conEstrategia(EstrategiaPago estrategia) {
            this.estrategia = estrategia;
            return this;
        }
        
        /**
         * Construye y retorna el MetodoPago con los valores establecidos.
         * Valida que la estrategia esté configurada antes de crear la instancia.
         * 
         * @return Nuevo MetodoPago completamente inicializado
         * @throws IllegalStateException Si la estrategia no está configurada
         */
        public MetodoPago build() {
            if (estrategia == null) {
                throw new IllegalStateException("La estrategia de pago no puede ser nula");
            }
            return new MetodoPago(this);
        }
    }
    
    /**
     * Obtiene el identificador único del método de pago.
     * 
     * @return ID del método de pago
     */
    public String getIdMetodoPago() {
        return idMetodoPago;
    }
    
    /**
     * Obtiene la estrategia de pago asociada a este método.
     * 
     * @return Objeto que implementa EstrategiaPago
     */
    public EstrategiaPago getEstrategia() {
        return estrategia;
    }
}