package co.edu.uniquindio.poo.strategy;

public interface EstrategiaPago {
    boolean procesarPago(double monto);
    String getTipo();
}