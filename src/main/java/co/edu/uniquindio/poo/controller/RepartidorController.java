package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.Repartidor;
import co.edu.uniquindio.poo.model.SistemaGestion;

import java.util.List;
import java.util.UUID;

/**
 * Controlador para gestión de repartidores
 */
public class RepartidorController {
    private final SistemaGestion sistema;
    
    public RepartidorController() {
        this.sistema = SistemaGestion.obtenerInstancia();
    }
    
    /**
     * Registra un nuevo repartidor en el sistema
     */
    public Repartidor registrarRepartidor(String nombre, String documento, String telefono, 
                                         String zonaCobertura, Repartidor.EstadoRepartidor estado) {
        String id = "REP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Repartidor repartidor = Repartidor.builder()
                .idRepartidor(id)
                .nombre(nombre)
                .documento(documento)
                .telefono(telefono)
                .zonaCobertura(zonaCobertura)
                .estado(estado != null ? estado : Repartidor.EstadoRepartidor.ACTIVO)
                .enviosAsignados(0)
                .build();
        
        sistema.registrarRepartidor(repartidor);
        System.out.println("✓ Repartidor registrado: " + nombre);
        return repartidor;
    }
    
    /**
     * Obtiene todos los repartidores registrados
     */
    public List<Repartidor> obtenerTodosRepartidores() {
        return sistema.getRepartidores();
    }
    
    /**
     * Obtiene solo los repartidores disponibles (estado ACTIVO)
     */
    public List<Repartidor> obtenerRepartidoresDisponibles() {
        return sistema.getRepartidores().stream()
                .filter(r -> r.getEstado() == Repartidor.EstadoRepartidor.ACTIVO)
                .toList();
    }
    
    /**
     * Busca un repartidor por su ID
     */
    public Repartidor buscarRepartidorPorId(String idRepartidor) {
        return sistema.buscarRepartidorPorId(idRepartidor);
    }
    
    /**
     * Actualiza la información de un repartidor
     */
    public void actualizarRepartidor(String idRepartidor, String nombre, String documento, 
                                     String telefono, String zonaCobertura, Repartidor.EstadoRepartidor estado) {
        Repartidor repartidor = sistema.buscarRepartidorPorId(idRepartidor);
        if (repartidor != null) {
            repartidor.setNombre(nombre);
            repartidor.setDocumento(documento);
            repartidor.setTelefono(telefono);
            repartidor.setZonaCobertura(zonaCobertura);
            repartidor.setEstado(estado);
            System.out.println("✓ Repartidor actualizado: " + nombre);
        }
    }
    
    /**
     * Elimina un repartidor del sistema
     */
    public void eliminarRepartidor(String idRepartidor) {
        Repartidor repartidor = sistema.buscarRepartidorPorId(idRepartidor);
        if (repartidor != null && repartidor.getEnviosAsignados() == 0) {
            sistema.eliminarRepartidor(idRepartidor);
            System.out.println("✓ Repartidor eliminado");
        } else if (repartidor != null) {
            System.out.println("✗ No se puede eliminar: El repartidor tiene envíos asignados");
        }
    }
    
    /**
     * Cambia el estado de un repartidor
     */
    public void cambiarEstado(String idRepartidor, Repartidor.EstadoRepartidor estado) {
        Repartidor repartidor = sistema.buscarRepartidorPorId(idRepartidor);
        if (repartidor != null) {
            repartidor.setEstado(estado);
            System.out.println("✓ Estado actualizado");
        }
    }
}
