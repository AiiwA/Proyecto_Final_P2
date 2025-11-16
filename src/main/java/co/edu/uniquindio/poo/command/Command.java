package co.edu.uniquindio.poo.command;

/**
 * Interfaz Command del patrón Command.
 * Define el contrato que deben cumplir todos los comandos ejecutables.
 * Permite encapsular solicitudes como objetos, habilitando deshacer/rehacer
 * y historial de operaciones.
 */
public interface Command {
    /**
     * Ejecuta la operación encapsulada en el comando.
     * Implementar la lógica de negocio que debe ejecutarse.
     */
    void ejecutar();
    
    /**
     * Revierte la operación realizada por ejecutar().
     * Restaura el estado anterior a la ejecución del comando.
     */
    void deshacer();
    
    /**
     * Obtiene una descripción legible del comando.
     * Utilizado para mostrar el historial de operaciones al usuario.
     * 
     * @return Descripción del comando y sus parámetros
     */
    String getDescripcion();
}
