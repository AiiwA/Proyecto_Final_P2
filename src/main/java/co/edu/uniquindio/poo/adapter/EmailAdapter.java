package co.edu.uniquindio.poo.adapter;


// Adapter para el servicio de Email
public class EmailAdapter implements ServicioNotificacion {
    private ServicioEmailExterno servicioEmail;
    
    public EmailAdapter() {
        this.servicioEmail = new ServicioEmailExterno();
    }
    
    @Override
    public void enviarNotificacion(String destinatario, String mensaje) {
        if (servicioEmail.verificarEmail(destinatario)) {
            servicioEmail.enviarEmail(destinatario, "Notificación de Envío", mensaje);
        } else {
            System.out.println("Email inválido: " + destinatario);
        }
    }
    
    @Override
    public String getTipo() {
        return "EMAIL";
    }
}
