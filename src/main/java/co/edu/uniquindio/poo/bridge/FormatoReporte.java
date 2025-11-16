package co.edu.uniquindio.poo.bridge;

/**
 * Interfaz Implementador del patrón Bridge para generación de reportes.
 * Define los métodos que deben cumplir todos los formatos de salida
 * (PDF, CSV, etc.) para generar reportes.
 * 
 * Este es el lado de "implementación" del patrón Bridge, permitiendo
 * separar la abstracción (tipos de reportes) de las implementaciones concretas (formatos).
 */
public interface FormatoReporte {
    /**
     * Genera el encabezado del reporte con el título.
     * 
     * @param titulo Título principal del reporte
     */
    void generarEncabezado(String titulo);
    
    /**
     * Genera el cuerpo principal del reporte.
     * 
     * @param contenido Contenido formateado del reporte
     */
    void generarContenido(String contenido);
    
    /**
     * Genera el pie de página o información final del reporte.
     * 
     * @param piePagina Texto a mostrar al final del reporte
     */
    void generarPiePagina(String piePagina);
    
    /**
     * Guarda el reporte generado en un archivo.
     * El formato del archivo depende de la implementación.
     * 
     * @param nombreArchivo Nombre del archivo a guardar (sin extensión)
     */
    void guardarArchivo(String nombreArchivo);
    
    /**
     * Obtiene la extensión de archivo correspondiente al formato.
     * Ejemplos: "pdf", "csv", "txt".
     * 
     * @return Extensión del archivo
     */
    String getExtension();
}
