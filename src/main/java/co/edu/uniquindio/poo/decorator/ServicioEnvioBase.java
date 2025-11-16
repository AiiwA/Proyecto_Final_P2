package co.edu.uniquindio.poo.decorator;

import co.edu.uniquindio.poo.model.Envio;

public class ServicioEnvioBase implements ServicioEnvio {
    @Override
    public double calcularCosto(Envio envio) {
        return envio.getCosto();
    }

    @Override
    public String getDescripcion() {
        return "Servicio de envío básico";
    }
}