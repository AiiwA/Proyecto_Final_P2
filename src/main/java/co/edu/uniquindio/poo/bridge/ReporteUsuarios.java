package co.edu.uniquindio.poo.bridge;

import co.edu.uniquindio.poo.model.Usuario;
import java.util.List;

// Abstracción refinada - Reporte de Usuarios
public class ReporteUsuarios extends Reporte {
    private List<Usuario> usuarios;
    
    public ReporteUsuarios(FormatoReporte formato, List<Usuario> usuarios) {
        super(formato);
        this.usuarios = usuarios;
    }
    
    @Override
    public void generar(String nombreArchivo) {
        StringBuilder contenido = new StringBuilder();
        
        // Generar contenido del reporte
        if (formato.getExtension().equals("csv")) {
            contenido.append("ID,Nombre,Correo,Teléfono,Direcciones,Métodos de Pago\n");
            for (Usuario usuario : usuarios) {
                contenido.append(usuario.getIdUsuario()).append(",")
                        .append(usuario.getNombreCompleto()).append(",")
                        .append(usuario.getCorreoElectronico()).append(",")
                        .append(usuario.getTelefono()).append(",")
                        .append(usuario.getDireccionesFrecuentes().size()).append(",")
                        .append(usuario.getMetodosPago().size()).append("\n");
            }
        } else {
            contenido.append("\nTotal de usuarios: ").append(usuarios.size()).append("\n\n");
            for (Usuario usuario : usuarios) {
                contenido.append("ID: ").append(usuario.getIdUsuario()).append("\n");
                contenido.append("  Nombre: ").append(usuario.getNombreCompleto()).append("\n");
                contenido.append("  Correo: ").append(usuario.getCorreoElectronico()).append("\n");
                contenido.append("  Teléfono: ").append(usuario.getTelefono()).append("\n");
                contenido.append("  Direcciones: ").append(usuario.getDireccionesFrecuentes().size()).append("\n");
                contenido.append("  Métodos de Pago: ").append(usuario.getMetodosPago().size()).append("\n\n");
            }
        }
        
        generarEstructura("REPORTE DE USUARIOS", contenido.toString(), 
                         "CityDrop © 2025");
        formato.guardarArchivo(nombreArchivo);
    }
}
