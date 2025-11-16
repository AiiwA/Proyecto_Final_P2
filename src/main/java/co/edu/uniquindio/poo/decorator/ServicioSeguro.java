package co.edu.uniquindio.poo.decorator;

import co.edu.uniquindio.poo.model.Envio;

public class ServicioSeguro extends ServicioEnvioDecorator {
    public ServicioSeguro(ServicioEnvio servicio) {
        super(servicio);
    }
    
    @Override
    public double calcularCosto(Envio envio) {
        return super.calcularCosto(envio) + 5000; // Costo adicional del seguro
    }
    
    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Seguro";
    }
}