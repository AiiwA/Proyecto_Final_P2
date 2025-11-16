package co.edu.uniquindio.poo.factory;

import co.edu.uniquindio.poo.model.*;
import java.util.UUID;

/**
 * Factory del patrón Factory Method para crear instancias de entidades del modelo.
 * Centraliza la creación de Usuario, Administrador, Dirección e Incidencia.
 * Asegura que todas las entidades se creen con identificadores únicos y consistencia.
 */
public class EntidadFactory {
    
    /**
     * Crea una nueva instancia de Usuario.
     * Genera automáticamente un ID único para el usuario.
     * 
     * @param nombreCompleto Nombre completo del usuario
     * @param correo Dirección de email del usuario
     * @param telefono Número de contacto del usuario
     * @return Nuevo usuario creado y listo para usar
     */
    public static Usuario crearUsuario(String nombreCompleto, String correo, String telefono) {
        return Usuario.builder()
                .idUsuario(UUID.randomUUID().toString())
                .nombreCompleto(nombreCompleto)
                .correoElectronico(correo)
                .telefono(telefono)
                .build();
    }
    
    /**
     * Crea una nueva instancia de Administrador.
     * Genera automáticamente un ID único para el administrador.
     * 
     * @param nombre Nombre del administrador
     * @param correo Dirección de email del administrador
     * @return Nuevo administrador con permisos de gestión del sistema
     */
    public static Administrador crearAdministrador(String nombre, String correo) {
        return Administrador.builder()
                .idAdmin(UUID.randomUUID().toString())
                .nombre(nombre)
                .correo(correo)
                .build();
    }
    
    /**
     * Crea una nueva instancia de Dirección con coordenadas geográficas.
     * Genera automáticamente un ID único para la dirección.
     * 
     * @param alias Nombre descriptivo de la dirección (ej: "Casa", "Oficina")
     * @param calle Calle y número de la dirección
     * @param ciudad Ciudad de la dirección
     * @param lat Latitud geográfica
     * @param lng Longitud geográfica
     * @return Nueva dirección georeferenciada
     */
    public static Direccion crearDireccion(String alias, String calle, String ciudad, double lat, double lng) {
        return new Direccion.Builder(UUID.randomUUID().toString())
                .conAlias(alias)
                .conCalle(calle)
                .conCiudad(ciudad)
                .conCoordenadas(lat, lng)
                .build();
    }
    
    /**
     * Crea una nueva instancia de Incidencia.
     * Registra un problema o incidente en un envío específico.
     * Genera automáticamente un ID único para la incidencia.
     * 
     * @param descripcion Descripción detallada del incidente
     * @param envio Envío afectado por la incidencia
     * @return Nueva incidencia asociada al envío
     */
    public static Incidencia crearIncidencia(String descripcion, Envio envio) {
        return new Incidencia.Builder(UUID.randomUUID().toString())
                .conDescripcion(descripcion)
                .conEnvio(envio)
                .build();
    }
}
