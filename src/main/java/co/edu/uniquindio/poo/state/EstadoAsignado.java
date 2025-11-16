package co.edu.uniquindio.poo.state;

import co.edu.uniquindio.poo.model.Envio;

/**
 * Estado de envío asignado a un repartidor.
 * 
 * Representa un envío que ya ha sido asignado a un repartidor específico
 * y está esperando que inicie la ruta de entrega. Desde este estado solo se permite:
 * - Transicionar a EN_RUTA (inicio de entrega)
 * - Transicionar a INCIDENCIA (si surge un problema)
 * 
 * No se permite:
 * - Retroceder a SOLICITADO
 * - Entregar directamente sin iniciar ruta
 */
public class EstadoAsignado implements EstadoEnvio {
    
    /**
     * Previene retroceso a estado anterior.
     */
    @Override
    public void procesar(Envio envio) {
        throw new IllegalStateException("No se puede retroceder a estado anterior");
    }
    
    /**
     * No permite reasignación, el envío ya está asignado.
     */
    @Override
    public void asignar(Envio envio) {
        System.out.println("El envío ya ha sido preparado");
    }
    
    /**
     * Inicia la ruta de entrega.
     * Transiciona de ASIGNADO a EN_RUTA.
     */
    @Override
    public void iniciarRuta(Envio envio) {
        System.out.println("Iniciando ruta de entrega...");
        envio.setEstadoActual(new EstadoEnRuta());
        envio.setEstado(Envio.EstadoEnvio.EN_RUTA);
    }
    
    /**
     * No permite entrega sin iniciar ruta.
     */
    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("No se puede entregar. Debe iniciar ruta primero");
    }
    
    /**
     * Permite reportar incidencia.
     */
    @Override
    public void reportarIncidencia(Envio envio) {
        envio.setEstadoActual(new EstadoIncidencia());
        envio.setEstado(Envio.EstadoEnvio.INCIDENCIA);
    }
    
    /**
     * No permite cancelación de envíos ya asignados.
     */
    @Override
    public void cancelar(Envio envio) {
        throw new IllegalStateException("No se puede cancelar un envío ya asignado");
    }
    
    @Override
    public String getNombre() {
        return "ASIGNADO";
    }
}
