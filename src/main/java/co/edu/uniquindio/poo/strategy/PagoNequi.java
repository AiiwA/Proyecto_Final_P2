package co.edu.uniquindio.poo.strategy;

public class PagoNequi implements EstrategiaPago {
    private String numeroCelular;
    
    public PagoNequi(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }
    
    @Override
    public boolean procesarPago(double monto) {
        // Simulación de procesamiento de pago con Nequi
        return numeroCelular != null && 
               numeroCelular.matches("^3\\d{9}$"); // Valida que sea un celular colombiano
    }
    
    @Override
    public String getTipo() {
        return "NEQUI";
    }
    
    public String getNumeroCelular() {
        return numeroCelular;
    }
}