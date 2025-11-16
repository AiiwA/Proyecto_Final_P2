package co.edu.uniquindio.poo.strategy;

public class PagoEfectivo implements EstrategiaPago {
    @Override
    public boolean procesarPago(double monto) {
        // Simulación de procesamiento de pago en efectivo
        return true; // Siempre se acepta el pago en efectivo en esta simulación
    }
    
    @Override
    public String getTipo() {
        return "EFECTIVO";
    }
}