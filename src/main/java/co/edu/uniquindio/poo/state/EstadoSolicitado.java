package co.edu.uniquindio.poo.state;

import co.edu.uniquindio.poo.model.Envio;

/**
 * Estado inicial de un envío.
 * 
 * Representa un envío que acaba de ser creado en el sistema y está esperando
 * ser asignado a un repartidor. Desde este estado solo se permite:
 * - Transicionar a ASIGNADO (asignación de repartidor)
 * - Transicionar a INCIDENCIA (si surge un problema)
 * - Cancelación
 * 
 * No se permite:
 * - Iniciar ruta sin asignar primero
 * - Entregar directamente sin pasar por ASIGNADO y EN_RUTA
 */
public class EstadoSolicitado implements EstadoEnvio {
    
    @Override
    public void procesar(Envio envio) {
        System.out.println("Procesando envío solicitado...");
    }
    
    /**
     * Asigna el envío a un repartidor.
     * Transiciona de SOLICITADO a ASIGNADO.
     */
    @Override
    public void asignar(Envio envio) {
        System.out.println("Preparando envío para ruta...");
        envio.setEstadoActual(new EstadoAsignado());
        envio.setEstado(Envio.EstadoEnvio.ASIGNADO);
    }
    
    /**
     * No permite iniciar ruta sin asignar primero.
     */
    @Override
    public void iniciarRuta(Envio envio) {
        throw new IllegalStateException("No se puede iniciar ruta sin asignar primero");
    }
    
    /**
     * No permite entrega directa sin pasos intermedios.
     */
    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("No se puede entregar. Debe seguir: asignar -> iniciar ruta -> entregar");
    }
    
    /**
     * Permite reportar incidencia incluso en estado inicial.
     */
    @Override
    public void reportarIncidencia(Envio envio) {
        envio.setEstadoActual(new EstadoIncidencia());
        envio.setEstado(Envio.EstadoEnvio.INCIDENCIA);
    }
    
    /**
     * Permite cancelación de envíos solicitados.
     */
    @Override
    public void cancelar(Envio envio) {
        System.out.println("Envío cancelado exitosamente");
    }
    
    @Override
    public String getNombre() {
        return "SOLICITADO";
    }
}
