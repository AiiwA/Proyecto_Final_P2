package co.edu.uniquindio.poo.state;

import co.edu.uniquindio.poo.model.Envio;

/**
 * Interfaz de patrón State para transiciones de estado de envíos
 * 
 * Define el contrato que deben cumplir todos los estados posibles de un envío.
 * Cada implementación concreta (EstadoSolicitado, EstadoAsignado, etc.) valida
 * qué transiciones son permitidas desde ese estado particular.
 * 
 * Beneficios de este patrón:
 * - Encapsula la lógica de transición de estado
 * - Previene transiciones inválidas lanzando excepciones
 * - Permite agregar nuevos estados sin modificar el modelo Envio
 * - Mejora la mantenibilidad y extensibilidad del código
 */
public interface EstadoEnvio {
    
    /**
     * Procesa el envío en su estado actual.
     * Realiza cualquier acción necesaria según el estado.
     * 
     * @param envio Envío a procesar
     */
    void procesar(Envio envio);
    
    /**
     * Asigna un repartidor al envío.
     * Válido solo en estado SOLICITADO, transiciona a ASIGNADO.
     * 
     * @param envio Envío a asignar
     * @throws IllegalStateException Si el estado no permite asignación
     */
    void asignar(Envio envio);
    
    /**
     * Inicia la ruta de entrega del envío.
     * Válido solo en estado ASIGNADO, transiciona a EN_RUTA.
     * 
     * @param envio Envío a iniciar ruta
     * @throws IllegalStateException Si el estado no permite iniciar ruta
     */
    void iniciarRuta(Envio envio);
    
    /**
     * Marca el envío como entregado.
     * Válido solo en estado EN_RUTA, transiciona a ENTREGADO.
     * 
     * @param envio Envío a entregar
     * @throws IllegalStateException Si el estado no permite entrega
     */
    void entregar(Envio envio);
    
    /**
     * Reporta una incidencia en el envío.
     * Transiciona a INCIDENCIA desde cualquier estado activo.
     * 
     * @param envio Envío con incidencia
     */
    void reportarIncidencia(Envio envio);
    
    /**
     * Cancela el envío.
     * Válido solo en estado SOLICITADO.
     * 
     * @param envio Envío a cancelar
     * @throws IllegalStateException Si el estado no permite cancelación
     */
    void cancelar(Envio envio);
    
    /**
     * Obtiene el nombre del estado actual.
     * 
     * @return Nombre del estado como String
     */
    String getNombre();
}
