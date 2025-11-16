package co.edu.uniquindio.poo.adapter;

import co.edu.uniquindio.poo.model.Direccion;

/**
 * Interfaz del patrón Adapter para servicios de cálculo de distancia.
 * Define el contrato que espera la aplicación para calcular distancias
 * y tiempos estimados entre dos direcciones geográficas.
 */
public interface ServicioDistancia {
    /**
     * Calcula la distancia en kilómetros entre dos direcciones.
     * Utiliza las coordenadas geográficas de las direcciones.
     * 
     * @param origen Dirección de partida con coordenadas
     * @param destino Dirección de llegada con coordenadas
     * @return Distancia en kilómetros
     */
    double calcularDistancia(Direccion origen, Direccion destino);
    
    /**
     * Calcula el tiempo estimado en minutos para recorrer entre dos direcciones.
     * Considerando velocidad promedio de tráfico urbano (30 km/h).
     * 
     * @param origen Dirección de partida
     * @param destino Dirección de llegada
     * @return Tiempo estimado en minutos
     */
    int calcularTiempoEstimado(Direccion origen, Direccion destino);
}
