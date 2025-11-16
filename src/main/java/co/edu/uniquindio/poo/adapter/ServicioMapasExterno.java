package co.edu.uniquindio.poo.adapter;

/**
 * Servicio externo simulado de cálculo de distancias.
 * Representa una librería o API externa (como Google Maps) que proporciona
 * cálculos geográficos. Está aislada del resto de la aplicación mediante
 * el patrón Adapter.
 */
public class ServicioMapasExterno {
    
    /**
     * Calcula la distancia en kilómetros entre dos coordenadas geográficas.
     * Utiliza la fórmula de Haversine que considera la curvatura de la Tierra.
     * 
     * @param lat1 Latitud del punto de origen
     * @param lon1 Longitud del punto de origen
     * @param lat2 Latitud del punto de destino
     * @param lon2 Longitud del punto de destino
     * @return Distancia en kilómetros
     */
    public double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        // Fórmula de Haversine simplificada para la simulación
        double radioTierra = 6371; // km
        
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return radioTierra * c;
    }
    
    /**
     * Obtiene el tiempo estimado de viaje en minutos basado en la distancia.
     * Asume velocidad promedio de 30 km/h en entorno urbano.
     * 
     * @param distanciaKm Distancia en kilómetros a recorrer
     * @return Tiempo estimado en minutos
     */
    public int obtenerTiempoEstimadoMinutos(double distanciaKm) {
        // Asume velocidad promedio de 30 km/h en ciudad
        return (int) (distanciaKm / 30.0 * 60);
    }
}
