package co.edu.uniquindio.poo.state;

import co.edu.uniquindio.poo.model.Envio;

/**
 * Estado de envío con incidencia o problema reportado.
 * 
 * Representa un envío que ha experimentado un problema que requiere investigación
 * y resolución. Puede ocurrir en casi cualquier etapa del ciclo de vida.
 * 
 * Desde este estado se permite:
 * - Reasignar a otro repartidor (transiciona a ASIGNADO para reintentar)
 * 
 * No se permite:
 * - Retroceder a SOLICITADO
 * - Reportar nuevamente una incidencia (ya está en estado INCIDENCIA)
 * - Iniciar ruta directamente (debe resolver primero)
 * - Entregar (debe resolver la incidencia)
 */
public class EstadoIncidencia implements EstadoEnvio {
    
    /**
     * Previene retroceso a estado anterior.
     */
    @Override
    public void procesar(Envio envio) {
        throw new IllegalStateException("No se puede retroceder a estado anterior. Resuelva la incidencia primero");
    }
    
    /**
     * Permite reasignación después de resolver la incidencia.
     * Transiciona de INCIDENCIA a ASIGNADO para reintentar entrega.
     */
    @Override
    public void asignar(Envio envio) {
        System.out.println("Reasignando envío después de resolver incidencia...");
        envio.setEstadoActual(new EstadoAsignado());
        envio.setEstado(Envio.EstadoEnvio.ASIGNADO);
    }
    
    /**
     * No permite iniciar ruta sin resolver incidencia primero.
     */
    @Override
    public void iniciarRuta(Envio envio) {
        throw new IllegalStateException("Debe resolver la incidencia primero");
    }
    
    /**
     * No permite entrega sin resolver incidencia.
     */
    @Override
    public void entregar(Envio envio) {
        throw new IllegalStateException("Debe resolver la incidencia primero");
    }
    
    /**
     * No permite reportar nueva incidencia estando ya en ese estado.
     */
    @Override
    public void reportarIncidencia(Envio envio) {
        System.out.println("Ya existe una incidencia reportada");
    }
    
    /**
     * Permite cancelación de envíos con incidencias.
     */
    @Override
    public void cancelar(Envio envio) {
        System.out.println("Cancelando envío con incidencia...");
    }
    
    @Override
    public String getNombre() {
        return "INCIDENCIA";
    }
}
