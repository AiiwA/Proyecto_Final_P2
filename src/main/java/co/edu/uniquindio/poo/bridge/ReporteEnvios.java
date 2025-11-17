package co.edu.uniquindio.poo.bridge;

import co.edu.uniquindio.poo.model.Envio;
import java.util.List;

/**
 * Implementación concreta del patrón Bridge: Reporte de Envíos.
 * Genera reportes con información de envíos en el formato especificado
 * (PDF, CSV, etc.) mediante el objeto FormatoReporte.
 * 
 * Permite cambiar dinámicamente el formato sin modificar esta clase,
 * demostrando la flexibilidad del patrón Bridge.
 */
public class ReporteEnvios extends Reporte {
    private List<Envio> envios;
    
    /**
     * Constructor que inicializa el reporte con los envíos y formato.
     * 
     * @param formato Implementación de FormatoReporte (PDF, CSV, etc.)
     * @param envios Lista de envíos a incluir en el reporte
     */
    public ReporteEnvios(FormatoReporte formato, List<Envio> envios) {
        super(formato);
        this.envios = envios;
    }
    
    /**
     * Genera el reporte de envíos en el formato especificado.
     * Formatea los datos de envíos según el tipo de formato (CSV genera tabla delimitada,
     * PDF genera documento formateado).
     * 
     * @param nombreArchivo Nombre base del archivo a guardar
     */
    @Override
    public void generar(String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        
        // Generar contenido del reporte
        if (formato.getExtension().equals("csv")) {
            contenido.append("ID,Usuario,Origen,Destino,Estado,Costo,Fecha Creación\n");
            for (Envio envio : envios) {
                contenido.append(envio.getIdEnvio()).append(",")
                        .append(envio.getUsuario().getNombreCompleto()).append(",")
                        .append(envio.getOrigen().getCiudad()).append(",")
                        .append(envio.getDestino().getCiudad()).append(",")
                        .append(envio.getEstado()).append(",")
                        .append(envio.getCosto()).append(",")
                        .append(envio.getFechaCreacion()).append("\n");
            }
        } else {
            contenido.append("\nTotal de envíos: ").append(envios.size()).append("\n\n");
            for (Envio envio : envios) {
                contenido.append("ID: ").append(envio.getIdEnvio()).append("\n");
                contenido.append("  Usuario: ").append(envio.getUsuario().getNombreCompleto()).append("\n");
                contenido.append("  Origen: ").append(envio.getOrigen().getCiudad()).append("\n");
                contenido.append("  Destino: ").append(envio.getDestino().getCiudad()).append("\n");
                contenido.append("  Estado: ").append(envio.getEstado()).append("\n");
                contenido.append("  Costo: $").append(String.format("%.2f", envio.getCosto())).append("\n");
                contenido.append("  Fecha: ").append(envio.getFechaCreacion()).append("\n\n");
            }
        }
        
        generarEstructura("REPORTE DE ENVÍOS", contenido.toString(), 
                         "CityDrop © 2025");
        formato.guardarArchivo(nombreArchivo);
    }
}
