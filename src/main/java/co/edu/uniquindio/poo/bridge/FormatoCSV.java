package co.edu.uniquindio.poo.bridge;

import java.io.FileWriter;
import java.io.IOException;

// Implementación concreta para formato CSV
public class FormatoCSV implements FormatoReporte {
    private StringBuilder contenidoCSV;
    
    public FormatoCSV() {
        this.contenidoCSV = new StringBuilder();
    }
    
    @Override
    public void generarEncabezado(String titulo) {
        contenidoCSV.append("# ").append(titulo).append("\n");
        contenidoCSV.append("Generado el: ").append(java.time.LocalDateTime.now()).append("\n\n");
    }
    
    @Override
    public void generarContenido(String contenido) {
        contenidoCSV.append(contenido);
    }
    
    @Override
    public void generarPiePagina(String piePagina) {
        contenidoCSV.append("\n# ").append(piePagina).append("\n");
    }
    
    @Override
    public void guardarArchivo(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo + ".csv")) {
            writer.write(contenidoCSV.toString());
            System.out.println("Reporte CSV generado: " + nombreArchivo + ".csv");
        } catch (IOException e) {
            System.err.println("Error al guardar archivo CSV: " + e.getMessage());
        }
    }
    
    @Override
    public String getExtension() {
        return "csv";
    }
}
