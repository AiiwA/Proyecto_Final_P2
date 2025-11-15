package co.edu.uniquindio.demop2pf.model;

/**
 * Modelo de Dirección con coordenadas geográficas.
 * Representa una ubicación física con información de calle, ciudad y coordenadas GPS.
 * Utiliza patrón Builder para construcción flexible.
 */
public class Direccion {
    private final String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private double latitud;
    private double longitud;
    
    /**
     * Constructor privado que inicializa todos los campos de la dirección.
     * Solo accesible a través del patrón Builder.
     * 
     * @param builder Objeto Builder con los valores configurados
     */
    private Direccion(Builder builder) {
        this.idDireccion = builder.idDireccion;
        this.alias = builder.alias;
        this.calle = builder.calle;
        this.ciudad = builder.ciudad;
        this.latitud = builder.latitud;
        this.longitud = builder.longitud;
    }
    
    // Builder Pattern
    public static class Builder {
        private String idDireccion;
        private String alias;
        private String calle;
        private String ciudad;
        private double latitud;
        private double longitud;
        
        /**
         * Constructor del Builder con el ID de la dirección.
         * 
         * @param idDireccion Identificador único de la dirección
         */
        public Builder(String idDireccion) {
            this.idDireccion = idDireccion;
        }
        
        /**
         * Establece un alias descriptivo para la dirección (ej: "Casa", "Oficina").
         * 
         * @param alias Nombre de referencia de la dirección
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conAlias(String alias) {
            this.alias = alias;
            return this;
        }
        
        /**
         * Establece la calle y número de la dirección.
         * 
         * @param calle Nombre de la calle y número (ej: "Calle 10 #15-20")
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conCalle(String calle) {
            this.calle = calle;
            return this;
        }
        
        /**
         * Establece la ciudad de la dirección.
         * 
         * @param ciudad Nombre de la ciudad (ej: "Armenia", "Bogotá")
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conCiudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }
        
        /**
         * Establece las coordenadas geográficas (latitud y longitud).
         * Utilizadas para cálculos de distancia y visualización en mapa.
         * 
         * @param latitud Coordenada de latitud (rango: -90 a 90)
         * @param longitud Coordenada de longitud (rango: -180 a 180)
         * @return Referencia al Builder para encadenamiento
         */
        public Builder conCoordenadas(double latitud, double longitud) {
            this.latitud = latitud;
            this.longitud = longitud;
            return this;
        }
        
        /**
         * Construye y retorna la instancia de Dirección con los valores establecidos.
         * 
         * @return Nuevo objeto Dirección completamente inicializado
         */
        public Direccion build() {
            return new Direccion(this);
        }
    }
    
    /**
     * Obtiene el identificador único de esta dirección.
     * 
     * @return ID de la dirección
     */
    public String getIdDireccion() {
        return idDireccion;
    }
    
    /**
     * Obtiene el alias descriptivo de la dirección (ej: "Casa").
     * 
     * @return Alias de la dirección
     */
    public String getAlias() {
        return alias;
    }
    
    /**
     * Obtiene la calle y número de la dirección.
     * 
     * @return Calle y número
     */
    public String getCalle() {
        return calle;
    }
    
    /**
     * Obtiene la ciudad de la dirección.
     * 
     * @return Nombre de la ciudad
     */
    public String getCiudad() {
        return ciudad;
    }
    
    /**
     * Obtiene la latitud geográfica de la dirección.
     * 
     * @return Coordenada de latitud
     */
    public double getLatitud() {
        return latitud;
    }
    
    /**
     * Obtiene la longitud geográfica de la dirección.
     * 
     * @return Coordenada de longitud
     */
    public double getLongitud() {
        return longitud;
    }
}