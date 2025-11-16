package co.edu.uniquindio.poo.command;

import co.edu.uniquindio.poo.model.Envio;
import co.edu.uniquindio.poo.state.*;
import lombok.AllArgsConstructor;

/**
 * Comando para actualizar el estado de un envío.
 * Implementa el patrón Command para permitir deshacer cambios de estado.
 * Actualiza tanto el enum de estado como el objeto de estado (State pattern).
 */
@AllArgsConstructor
public class ActualizarEstadoCommand implements Command {
    private Envio envio;
    private Envio.EstadoEnvio nuevoEstado;
    private Envio.EstadoEnvio estadoAnterior;
    private EstadoEnvio estadoAnteriorObject;
    
    /**
     * Constructor que captura automáticamente el estado actual del envío.
     * 
     * @param envio Envío cuyo estado se va a cambiar
     * @param nuevoEstado Nuevo estado destino del envío
     */
    public ActualizarEstadoCommand(Envio envio, Envio.EstadoEnvio nuevoEstado) {
        this.envio = envio;
        this.nuevoEstado = nuevoEstado;
        this.estadoAnterior = envio.getEstado();
        this.estadoAnteriorObject = envio.getEstadoActual();
    }
    
    /**
     * Ejecuta la actualización del estado del envío.
     * Cambia tanto el enum de estado como el objeto de estado.
     * Notifica a todos los observadores del cambio.
     */
    @Override
    public void ejecutar() {
        // Cambiar el enum de estado
        envio.setEstado(nuevoEstado);
        
        // Cambiar el objeto de estado (State pattern)
        EstadoEnvio nuevoEstadoObject = crearEstadoObject(nuevoEstado);
        envio.setEstadoActual(nuevoEstadoObject);
        
        System.out.println("[COMMAND] Estado del envío " + envio.getIdEnvio() + " actualizado a " + nuevoEstado);
        
        // Notificar a todos los observadores (incluyendo AdminDashboardObserver)
        envio.notificarObservadores("Estado del envío cambió a: " + nuevoEstado, envio);
    }
    
    /**
     * Revierte el cambio de estado restaurando el estado anterior.
     * Notifica a los observadores sobre la reversión.
     */
    @Override
    public void deshacer() {
        envio.setEstado(estadoAnterior);
        envio.setEstadoActual(estadoAnteriorObject);
        System.out.println("[COMMAND] Estado restaurado a " + estadoAnterior);
        envio.notificarObservadores("Estado del envío restaurado a: " + estadoAnterior, envio);
    }
    
    /**
     * Obtiene una descripción de la operación.
     * 
     * @return Descripción del cambio de estado
     */
    @Override
    public String getDescripcion() {
        return "Actualizar estado del envío " + envio.getIdEnvio() + " a " + nuevoEstado;
    }
    
    /**
     * Factory method privado para crear la instancia correcta del objeto de estado.
     * Mapea el enum de estado a su implementación concreta.
     * 
     * @param estado Enum del estado deseado
     * @return Objeto que implementa EstadoEnvio
     */
    private EstadoEnvio crearEstadoObject(Envio.EstadoEnvio estado) {
        switch (estado) {
            case SOLICITADO:
                return new EstadoSolicitado();
            case ASIGNADO:
                return new EstadoAsignado();
            case EN_RUTA:
                return new EstadoEnRuta();
            case ENTREGADO:
                return new EstadoEntregado();
            case INCIDENCIA:
                return new EstadoIncidencia();
            default:
                return new EstadoSolicitado();
        }
    }
}
