package co.edu.uniquindio.poo.command;

import co.edu.uniquindio.poo.model.Envio;
import lombok.AllArgsConstructor;

/**
 * Comando para cancelar un envío.
 * Implementa el patrón Command para permitir deshacer la cancelación.
 * Solo permite cancelar envíos en estado SOLICITADO.
 */
@AllArgsConstructor
public class CancelarEnvioCommand implements Command {
    private Envio envio;
    private Envio.EstadoEnvio estadoAnterior;
    
    /**
     * Constructor que captura el estado anterior del envío automáticamente.
     * 
     * @param envio Envío a cancelar
     */
    public CancelarEnvioCommand(Envio envio) {
        this.envio = envio;
        this.estadoAnterior = envio.getEstado();
    }
    
    /**
     * Ejecuta la cancelación del envío.
     * Valida que el envío esté en estado SOLICITADO antes de cancelar.
     * 
     * @throws IllegalStateException Si el envío no está en estado SOLICITADO
     */
    @Override
    public void ejecutar() {
        if (envio.getEstado() != Envio.EstadoEnvio.SOLICITADO) {
            throw new IllegalStateException("Solo se pueden cancelar envíos en estado SOLICITADO");
        }
        System.out.println("Envío " + envio.getIdEnvio() + " cancelado");
    }
    
    /**
     * Revierte la cancelación restaurando el estado anterior del envío.
     */
    @Override
    public void deshacer() {
        envio.setEstado(estadoAnterior);
        System.out.println("Cancelación deshecha");
    }
    
    /**
     * Obtiene una descripción de la operación.
     * 
     * @return Descripción: "Cancelar envío [id]"
     */
    @Override
    public String getDescripcion() {
        return "Cancelar envío " + envio.getIdEnvio();
    }
}
