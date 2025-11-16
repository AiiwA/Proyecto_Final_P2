package co.edu.uniquindio.poo.observer;

import lombok.AllArgsConstructor;

/**
 * Implementación concreta de Observer para notificar usuarios.
 * Recibe eventos del sistema y los comunica al usuario correspondiente.
 * Actualmente imprime en consola, pero puede extenderse para enviar
 * emails, SMS, o notificaciones push.
 */
@AllArgsConstructor
public class NotificadorUsuario implements Observer {
    private String idUsuario;
    private String nombreUsuario;
    
    /**
     * Notifica al usuario sobre un evento ocurrido en el sistema.
     * Actualmente imprime en consola, pero puede adaptarse para:
     * - Enviar notificaciones por email
     * - Enviar mensajes SMS
     * - Generar notificaciones en aplicación
     * 
     * @param mensaje Tipo y descripción del evento
     * @param datos Información adicional del evento (ej: detalles del envío)
     */
    @Override
    public void actualizar(String mensaje, Object datos) {
        System.out.println("Notificación para " + nombreUsuario + ": " + mensaje);
        // Aquí se puede integrar con un sistema de notificaciones real
    }
}
