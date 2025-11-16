package co.edu.uniquindio.poo.decorator;

import co.edu.uniquindio.poo.model.Envio;

public interface ServicioEnvio {
    double calcularCosto(Envio envio);
    String getDescripcion();
}