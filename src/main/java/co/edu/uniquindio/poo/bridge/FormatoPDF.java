package co.edu.uniquindio.poo.bridge;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

// Implementación concreta para formato PDF usando Apache PDFBox
public class FormatoPDF implements FormatoReporte {
    private PDDocument document;
    private PDPage currentPage;
    private PDPageContentStream contentStream;
    private float yPosition;
    private String titulo;
    private StringBuilder contenidoTexto;
    private String piePagina;
    private static final float MARGIN = 50;
    private static final float FONT_SIZE_TITLE = 18;
    private static final float FONT_SIZE_NORMAL = 12;
    private static final float LEADING = 14;
    
    public FormatoPDF() {
        this.document = new PDDocument();
        this.contenidoTexto = new StringBuilder();
    }
    
    @Override
    public void generarEncabezado(String titulo) {
        this.titulo = titulo;
    }
    
    @Override
    public void generarContenido(String contenido) {
        this.contenidoTexto.append(contenido);
    }
    
    @Override
    public void generarPiePagina(String piePagina) {
        this.piePagina = piePagina;
    }
    
    @Override
    public void guardarArchivo(String nombreArchivo) {
        try {
            currentPage = new PDPage(PDRectangle.A4);
            document.addPage(currentPage);
            contentStream = new PDPageContentStream(document, currentPage);
            yPosition = currentPage.getMediaBox().getHeight() - MARGIN;
            
            // Escribir título
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, FONT_SIZE_TITLE);
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText(titulo);
            contentStream.endText();
            yPosition -= 30;
            
            // Escribir fecha
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_NORMAL);
            String fecha = "Fecha: " + java.time.LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, yPosition);
            contentStream.showText(fecha);
            contentStream.endText();
            yPosition -= 30;
            
            // Escribir línea separadora
            contentStream.moveTo(MARGIN, yPosition);
            contentStream.lineTo(currentPage.getMediaBox().getWidth() - MARGIN, yPosition);
            contentStream.stroke();
            yPosition -= 20;
            
            // Escribir contenido
            contentStream.setFont(PDType1Font.HELVETICA, FONT_SIZE_NORMAL);
            String[] lineas = contenidoTexto.toString().split("\n");
            
            for (String linea : lineas) {
                // Verificar si necesitamos una nueva página
                if (yPosition < MARGIN + 50) {
                    contentStream.close();
                    currentPage = new PDPage(PDRectangle.A4);
                    document.addPage(currentPage);
                    contentStream = new PDPageContentStream(document, currentPage);
                    yPosition = currentPage.getMediaBox().getHeight() - MARGIN;
                }
                
                // Manejar líneas largas
                if (linea.length() > 80) {
                    String[] palabras = linea.split(" ");
                    StringBuilder lineaActual = new StringBuilder();
                    
                    for (String palabra : palabras) {
                        if (lineaActual.length() + palabra.length() > 80) {
                            contentStream.beginText();
                            contentStream.newLineAtOffset(MARGIN, yPosition);
                            contentStream.showText(lineaActual.toString());
                            contentStream.endText();
                            yPosition -= LEADING;
                            lineaActual = new StringBuilder(palabra + " ");
                        } else {
                            lineaActual.append(palabra).append(" ");
                        }
                    }
                    
                    if (lineaActual.length() > 0) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(MARGIN, yPosition);
                        contentStream.showText(lineaActual.toString());
                        contentStream.endText();
                        yPosition -= LEADING;
                    }
                } else {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(MARGIN, yPosition);
                    contentStream.showText(linea);
                    contentStream.endText();
                    yPosition -= LEADING;
                }
            }
            
            // Escribir pie de página
            if (piePagina != null && !piePagina.isEmpty()) {
                yPosition -= 20;
                contentStream.moveTo(MARGIN, yPosition);
                contentStream.lineTo(currentPage.getMediaBox().getWidth() - MARGIN, yPosition);
                contentStream.stroke();
                yPosition -= 20;
                
                contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, FONT_SIZE_NORMAL);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, yPosition);
                contentStream.showText(piePagina);
                contentStream.endText();
            }
            
            contentStream.close();
            document.save(nombreArchivo + ".pdf");
            document.close();
            System.out.println("Reporte PDF generado: " + nombreArchivo + ".pdf");
        } catch (IOException e) {
            System.err.println("Error al guardar archivo PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public String getExtension() {
        return "pdf";
    }
}
