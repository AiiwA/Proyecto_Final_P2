package co.edu.uniquindio.poo.adapter;

// Simula un servicio externo de SMS
public class ServicioSMSExterno {
    
    public void mandarMensajeTexto(String numeroTelefono, String contenido) {
        System.out.println("Enviando SMS a: " + numeroTelefono);
        System.out.println("Mensaje: " + contenido);
        System.out.println("SMS enviado exitosamente!");
    }
    
    public boolean validarNumero(String numero) {
        return numero != null && numero.length() >= 10;
    }
}
