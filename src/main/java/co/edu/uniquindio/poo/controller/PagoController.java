package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.strategy.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class PagoController {
    private SistemaGestion sistema;
    
    public PagoController() {
        this.sistema = SistemaGestion.obtenerInstancia();
    }
    
    public Pago procesarPagoTarjeta(Envio envio, String numeroTarjeta, String titular) {
        EstrategiaPago estrategia = new PagoTarjeta(numeroTarjeta, titular);
        return procesarPago(envio, estrategia);
    }
    
    public Pago procesarPagoNequi(Envio envio, String telefono) {
        EstrategiaPago estrategia = new PagoNequi(telefono);
        return procesarPago(envio, estrategia);
    }
    
    public Pago procesarPagoPayPal(Envio envio, String email, String password) {
        EstrategiaPago estrategia = new PagoPayPal(email, password);
        return procesarPago(envio, estrategia);
    }
    
    public Pago procesarPagoEfectivo(Envio envio) {
        EstrategiaPago estrategia = new PagoEfectivo();
        return procesarPago(envio, estrategia);
    }
    
    private Pago procesarPago(Envio envio, EstrategiaPago estrategia) {
        double monto = envio.getCosto();
        
        boolean exitoso = estrategia.procesarPago(monto);
        
        // Crear MetodoPago con la estrategia
        MetodoPago metodoPago = new MetodoPago.Builder(java.util.UUID.randomUUID().toString())
                .conEstrategia(estrategia)
                .build();
        
        Pago pago = new Pago.Builder(java.util.UUID.randomUUID().toString())
                .conMonto(monto)
                .conFecha(LocalDateTime.now())
                .conMetodoPago(metodoPago)
                .conEnvio(envio)
                .build();
        
        if (exitoso) {
            pago.setEstado(Pago.EstadoPago.APROBADO);
            sistema.registrarPago(pago);
        } else {
            pago.setEstado(Pago.EstadoPago.RECHAZADO);
        }
        
        return pago;
    }
    
    public Optional<Pago> buscarPagoPorId(String id) {
        return sistema.getPagos().stream()
                .filter(p -> p.getIdPago().equals(id))
                .findFirst();
    }
    
    public Optional<Pago> buscarPagoPorEnvio(String idEnvio) {
        return sistema.getPagos().stream()
                .filter(p -> p.getEnvio().getIdEnvio().equals(idEnvio))
                .findFirst();
    }
    
    public String generarComprobante(String idPago) {
        Optional<Pago> pago = buscarPagoPorId(idPago);
        if (pago.isPresent()) {
            Pago p = pago.get();
            StringBuilder comprobante = new StringBuilder();
            comprobante.append("═══════════════════════════════════════\n");
            comprobante.append("         COMPROBANTE DE PAGO\n");
            comprobante.append("═══════════════════════════════════════\n");
            comprobante.append("ID Pago: ").append(p.getIdPago()).append("\n");
            comprobante.append("ID Envío: ").append(p.getEnvio().getIdEnvio()).append("\n");
            comprobante.append("Monto: $").append(String.format("%.2f", p.getMonto())).append("\n");
            comprobante.append("Método: ").append(p.getMetodoPago().getTipo()).append("\n");
            comprobante.append("Estado: ").append(p.getEstado()).append("\n");
            comprobante.append("Fecha: ").append(p.getFecha()).append("\n");
            comprobante.append("═══════════════════════════════════════\n");
            return comprobante.toString();
        }
        return "Pago no encontrado";
    }
}
