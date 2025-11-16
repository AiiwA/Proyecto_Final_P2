package co.edu.uniquindio.poo.bridge;

/**
 * Clase abstracta Abstracción del patrón Bridge para reportes.
 * Define la interfaz general que deben cumplir todos los tipos de reportes.
 * Delega la implementación del formato a objetos que implementan FormatoReporte.
 * 
 * Este es el lado de "abstracción" del patrón Bridge, permitiendo cambiar
 * dinámicamente el formato de salida sin modificar el tipo de reporte.
 */
public abstract class Reporte {
    protected FormatoReporte formato;
    
    /**
     * Constructor que establece el formato en el que se generará el reporte.
     * 
     * @param formato Implementación de FormatoReporte (PDF, CSV, etc.)
     */
    public Reporte(FormatoReporte formato) {
        this.formato = formato;
    }
    
    /**
     * Método abstracto que debe implementar cada tipo de reporte.
     * Define cómo se genera el reporte específico.
     * 
     * @param nombreArchivo Nombre base para guardar el archivo
     */
    public abstract void generar(String nombreArchivo);
    
    /**
     * Método auxiliar para generar la estructura estándar de un reporte.
     * Invoca los métodos del formato para encabezado, contenido y pie de página.
     * 
     * @param titulo Título del reporte
     * @param contenido Cuerpo principal del reporte
     * @param piePagina Información del pie de página
     */
    protected void generarEstructura(String titulo, String contenido, String piePagina) {
        formato.generarEncabezado(titulo);
        formato.generarContenido(contenido);
        formato.generarPiePagina(piePagina);
    }
}
