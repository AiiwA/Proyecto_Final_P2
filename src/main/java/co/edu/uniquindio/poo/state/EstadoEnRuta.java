package co.edu.uniquindio.poo.state;

import co.edu.uniquindio.poo.model.Envio;

/**
 * Estado de envío en ruta hacia el destino.
 * 
 * Representa un envío que actualmente está siendo trasladado por el repartidor
 * hacia su destino final. Desde este estado solo se permite:
 * - Transicionar a ENTREGADO (entrega completada)
 * - Transicionar a INCIDENCIA (si surge un problema durante la ruta)
 * 
 * No se permite:
 * - Retroceder a estados anteriores
 * - Reasignar a otro repartidor
 */
public class EstadoEnRuta implements EstadoEnvio {
    
    /**
     * Previene retroceso a estado anterior.
     */
    @Override
    public void procesar(Envio envio) {
        throw new IllegalStateException("No se puede retroceder a estado anterior");
    }
    
    /**
     * Previene reasignación de envío en ruta.
     */
    @Override
    public void asignar(Envio envio) {
        throw new IllegalStateException("No se puede retroceder a estado anterior");
    }
    
    /**
     * El envío ya está en ruta, no se reinicia.
     */
    @Override
    public void iniciarRuta(Envio envio) {
        System.out.println("El envío ya está en ruta");
    }
    
    /**
     * Entrega el envío al destinatario.
     * Transiciona de EN_RUTA a ENTREGADO.
     */
    @Override
    public void entregar(Envio envio) {
        System.out.println("Entregando paquete...");
        envio.setEstadoActual(new EstadoEntregado());
        envio.setEstado(Envio.EstadoEnvio.ENTREGADO);
    }
    
    /**
     * Permite reportar incidencia durante la ruta.
     */
    @Override
    public void reportarIncidencia(Envio envio) {
        envio.setEstadoActual(new EstadoIncidencia());
        envio.setEstado(Envio.EstadoEnvio.INCIDENCIA);
    }
    
    /**
     * No permite cancelación de envíos en ruta.
     */
    @Override
    public void cancelar(Envio envio) {
        throw new IllegalStateException("No se puede cancelar un envío en ruta");
    }
    
    @Override
    public String getNombre() {
        return "EN_RUTA";
    }
}
