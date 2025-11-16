package co.edu.uniquindio.poo.observer;

/**
 * Interfaz Observer del patrón Observer.
 * Define el contrato que deben cumplir todas las clases que deseen ser notificadas
 * sobre cambios en los sujetos observados.
 */
public interface Observer {
    /**
     * Recibe una notificación de cambio en el sujeto observado.
     * Este método es invocado cuando ocurre un evento relevante en el sujeto.
     * 
     * @param mensaje Descripción del evento que ocurrió
     * @param datos Objeto con datos adicionales relacionados al evento
     */
    void actualizar(String mensaje, Object datos);
}
