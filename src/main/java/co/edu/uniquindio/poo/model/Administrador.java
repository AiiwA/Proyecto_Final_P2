package co.edu.uniquindio.poo.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Modelo de Administrador del sistema.
 * Representa a un usuario con permisos administrativos para gestionar
 * usuarios, envíos, incidencias, tarifas, métricas y reportes.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Administrador {
    private String idAdmin;
    private String nombre;
    private String correo;
    private String password;
    @Builder.Default
    private Set<Permiso> permisos = new HashSet<>();
    
    public enum Permiso {
        GESTIONAR_USUARIOS,    // Crear, actualizar, eliminar usuarios
        GESTIONAR_ENVIOS,      // Ver, modificar, cancelar envíos
        GESTIONAR_TARIFAS,     // Actualizar precios y costos de envío
        GESTIONAR_INCIDENCIAS, // Resolver problemas en envíos
        VER_METRICAS,          // Acceder a estadísticas del sistema
        GENERAR_REPORTES       // Crear reportes de envíos y usuarios
    }
    
    /**
     * Obtiene una copia defensiva de los permisos del administrador.
     * 
     * @return Conjunto de permisos del administrador
     */
    public Set<Permiso> getPermisos() {
        return new HashSet<>(permisos);
    }
    
    /**
     * Verifica si el administrador tiene un permiso específico.
     * 
     * @param permiso Permiso a verificar
     * @return true si el administrador tiene el permiso, false en caso contrario
     */
    public boolean tienePermiso(Permiso permiso) {
        return permisos.contains(permiso);
    }
    
    /**
     * Agrega un permiso al administrador.
     * Permite otorgar nuevas capacidades administrativas.
     * 
     * @param permiso Permiso a agregar
     */
    public void agregarPermiso(Permiso permiso) {
        permisos.add(permiso);
    }
    
    /**
     * Elimina un permiso del administrador.
     * Revoca capacidades administrativas específicas.
     * 
     * @param permiso Permiso a remover
     */
    public void removerPermiso(Permiso permiso) {
        permisos.remove(permiso);
    }
}
