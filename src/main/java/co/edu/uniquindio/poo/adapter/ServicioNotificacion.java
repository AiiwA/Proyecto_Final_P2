package co.edu.uniquindio.poo.adapter;

// Interfaz que espera nuestra aplicación
public interface ServicioNotificacion {
    void enviarNotificacion(String destinatario, String mensaje);
    String getTipo();
}
