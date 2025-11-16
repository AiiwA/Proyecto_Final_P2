package co.edu.uniquindio.poo.adapter;

import co.edu.uniquindio.poo.model.Direccion;

/**
 * Adapter del patrón Adapter para servicios de mapas externos.
 * Adapta la interfaz de ServicioMapasExterno (tercero) a nuestra interfaz 
 * ServicioDistancia esperada por la aplicación.
 * Permite aislar cambios en las librerías externas de nuestro código.
 */
public class MapasAdapter implements ServicioDistancia {
    private ServicioMapasExterno servicioMapas;
    
    /**
     * Constructor que inicializa el servicio de mapas externo.
     */
    public MapasAdapter() {
        this.servicioMapas = new ServicioMapasExterno();
    }
    
    /**
     * Calcula la distancia entre dos direcciones adaptando la llamada al servicio externo.
     * Convierte los objetos Direccion a coordenadas que entiende el servicio externo.
     * 
     * @param origen Dirección de origen con coordenadas geográficas
     * @param destino Dirección de destino con coordenadas geográficas
     * @return Distancia en kilómetros calculada por Haversine
     */
    @Override
    public double calcularDistancia(Direccion origen, Direccion destino) {
        return servicioMapas.calcularDistanciaKm(
            origen.getLatitud(), 
            origen.getLongitud(),
            destino.getLatitud(),
            destino.getLongitud()
        );
    }
    
    /**
     * Calcula el tiempo estimado de viaje entre dos direcciones.
     * Primero obtiene la distancia y luego la convierte a tiempo
     * asumiendo velocidad promedio de ciudad.
     * 
     * @param origen Dirección de partida
     * @param destino Dirección de llegada
     * @return Tiempo estimado en minutos
     */
    @Override
    public int calcularTiempoEstimado(Direccion origen, Direccion destino) {
        double distancia = calcularDistancia(origen, destino);
        return servicioMapas.obtenerTiempoEstimadoMinutos(distancia);
    }
}
