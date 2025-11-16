package co.edu.uniquindio.poo.strategy;

public class PagoPayPal implements EstrategiaPago {
    private String correoElectronico;
    private String tokenSesion;
    
    public PagoPayPal(String correoElectronico, String tokenSesion) {
        this.correoElectronico = correoElectronico;
        this.tokenSesion = tokenSesion;
    }
    
    @Override
    public boolean procesarPago(double monto) {
        // Simulación de procesamiento de pago con PayPal
        return correoElectronico != null && 
               correoElectronico.matches("^[A-Za-z0-9+_.-]+@(.+)$") && // Valida formato de email
               tokenSesion != null && !tokenSesion.isEmpty();
    }
    
    @Override
    public String getTipo() {
        return "PAYPAL";
    }
    
    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public String getTokenSesion() {
        return tokenSesion;
    }
}