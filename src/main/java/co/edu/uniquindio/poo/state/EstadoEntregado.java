package co.edu.uniquindio.poo.state;

import co.edu.uniquindio.poo.model.Envio;

/**
 * Estado final de un envío entregado exitosamente.
 * 
 * Representa un envío que ha sido entregado al destinatario. Este es un estado
 * terminal que no permite ninguna transición o cambio posterior.
 * 
 * No se permite:
 * - Retroceder a estados anteriores
 * - Reasignar o modificar el envío
 * - Reportar incidencias (ya fue entregado)
 * - Cancelar (ya fue entregado)
 */
public class EstadoEntregado implements EstadoEnvio {
    
    /**
     * No permite ninguna modificación en envío entregado.
     */
    @Override
    public void procesar(Envio envio) {
        System.out.println("Envío ya entregado");
    }
    
    /**
     * No permite reasignación de envío entregado.
     */
    @Override
    public void asignar(Envio envio) {
        throw new IllegalStateException("No se puede asignar un envío ya entregado");
    }
    
    /**
     * No permite modificar ruta de envío entregado.
     */
    @Override
    public void iniciarRuta(Envio envio) {
        throw new IllegalStateException("No se puede iniciar ruta de un envío ya entregado");
    }
    
    /**
     * El envío ya fue entregado.
     */
    @Override
    public void entregar(Envio envio) {
        System.out.println("El envío ya fue entregado");
    }
    
    /**
     * No se puede reportar incidencia en envío ya entregado.
     */
    @Override
    public void reportarIncidencia(Envio envio) {
        throw new IllegalStateException("No se puede reportar incidencia en un envío entregado");
    }
    
    /**
     * No se puede cancelar un envío ya entregado.
     */
    @Override
    public void cancelar(Envio envio) {
        throw new IllegalStateException("No se puede cancelar un envío ya entregado");
    }
    
    @Override
    public String getNombre() {
        return "ENTREGADO";
    }
}
