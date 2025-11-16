package co.edu.uniquindio.poo.adapter;

// Adapter para el servicio de SMS
public class SMSAdapter implements ServicioNotificacion {
    private ServicioSMSExterno servicioSMS;
    
    public SMSAdapter() {
        this.servicioSMS = new ServicioSMSExterno();
    }
    
    @Override
    public void enviarNotificacion(String destinatario, String mensaje) {
        if (servicioSMS.validarNumero(destinatario)) {
            servicioSMS.mandarMensajeTexto(destinatario, mensaje);
        } else {
            System.out.println("Número de teléfono inválido: " + destinatario);
        }
    }
    
    @Override
    public String getTipo() {
        return "SMS";
    }
}
