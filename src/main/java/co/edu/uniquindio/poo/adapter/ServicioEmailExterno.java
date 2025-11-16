package co.edu.uniquindio.poo.adapter;

// Simula un servicio externo de Email
public class ServicioEmailExterno {
    
    public void enviarEmail(String emailDestino, String asunto, String cuerpo) {
        System.out.println("Enviando email a: " + emailDestino);
        System.out.println("Asunto: " + asunto);
        System.out.println("Cuerpo: " + cuerpo);
        System.out.println("Email enviado exitosamente!");
    }
    
    public boolean verificarEmail(String email) {
        return email != null && email.contains("@");
    }
}
