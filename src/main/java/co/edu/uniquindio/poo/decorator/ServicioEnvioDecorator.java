package co.edu.uniquindio.poo.decorator;

import co.edu.uniquindio.poo.model.Envio;

public abstract class ServicioEnvioDecorator implements ServicioEnvio {
    protected ServicioEnvio servicioDecorado;
    
    public ServicioEnvioDecorator(ServicioEnvio servicio) {
        this.servicioDecorado = servicio;
    }
    
    @Override
    public double calcularCosto(Envio envio) {
        return servicioDecorado.calcularCosto(envio);
    }
    
    @Override
    public String getDescripcion() {
        return servicioDecorado.getDescripcion();
    }
}