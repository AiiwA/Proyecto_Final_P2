package co.edu.uniquindio.poo.strategy;

public class PagoTarjeta implements EstrategiaPago {
    private String numeroTarjeta;
    private String fechaExpiracion;
    
    public PagoTarjeta(String numeroTarjeta, String fechaExpiracion) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
    }
    
    @Override
    public boolean procesarPago(double monto) {
        // Simulación de procesamiento de pago con tarjeta
        return numeroTarjeta != null && !numeroTarjeta.isEmpty() && 
               fechaExpiracion != null && !fechaExpiracion.isEmpty();
    }
    
    @Override
    public String getTipo() {
        return "TARJETA";
    }
    
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }
    
    public String getFechaExpiracion() {
        return fechaExpiracion;
    }
}