package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.factory.EntidadFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioController {
    private SistemaGestion sistema;
    
    public UsuarioController() {
        this.sistema = SistemaGestion.obtenerInstancia();
    }
    
    public Usuario registrarUsuario(String nombre, String correo, String telefono, String password) {
        // Validar que no exista el correo
        if (buscarUsuarioPorCorreo(correo).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        
        Usuario usuario = EntidadFactory.crearUsuario(nombre, correo, telefono);
        usuario.setPassword(password);
        sistema.registrarUsuario(usuario);
        return usuario;
    }
    
    public Optional<Usuario> autenticar(String correo, String password) {
        return sistema.getUsuarios().stream()
                .filter(u -> u.getCorreoElectronico().equals(correo) && 
                           u.getPassword().equals(password))
                .findFirst();
    }
    
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return sistema.getUsuarios().stream()
                .filter(u -> u.getIdUsuario().equals(id))
                .findFirst();
    }
    
    public Optional<Usuario> buscarUsuarioPorCorreo(String correo) {
        return sistema.getUsuarios().stream()
                .filter(u -> u.getCorreoElectronico().equals(correo))
                .findFirst();
    }
    
    public void actualizarPerfil(String idUsuario, String nombre, String telefono) {
        buscarUsuarioPorId(idUsuario).ifPresent(usuario -> {
            usuario.setNombreCompleto(nombre);
            usuario.setTelefono(telefono);
        });
    }
    
    public void agregarDireccion(String idUsuario, Direccion direccion) {
        buscarUsuarioPorId(idUsuario).ifPresent(usuario -> {
            usuario.agregarDireccionFrecuente(direccion);
        });
    }
    
    public void agregarMetodoPago(String idUsuario, MetodoPago metodoPago) {
        buscarUsuarioPorId(idUsuario).ifPresent(usuario -> {
            usuario.agregarMetodoPago(metodoPago);
        });
    }
    
    public List<Envio> obtenerEnviosUsuario(String idUsuario) {
        return sistema.getEnvios().stream()
                .filter(e -> e.getUsuario().getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }
    
    public List<Envio> filtrarEnviosPorEstado(String idUsuario, Envio.EstadoEnvio estado) {
        return obtenerEnviosUsuario(idUsuario).stream()
                .filter(e -> e.getEstado() == estado)
                .collect(Collectors.toList());
    }
    
    public List<Usuario> obtenerTodosUsuarios() {
        return sistema.getUsuarios();
    }
    
    public boolean eliminarUsuario(String idUsuario) {
        Optional<Usuario> usuario = buscarUsuarioPorId(idUsuario);
        if (usuario.isPresent()) {
            // Verificar que no tenga envíos activos
            long enviosActivos = obtenerEnviosUsuario(idUsuario).stream()
                    .filter(e -> e.getEstado() != Envio.EstadoEnvio.ENTREGADO)
                    .count();
            
            if (enviosActivos > 0) {
                throw new IllegalStateException("No se puede eliminar un usuario con envíos activos");
            }
            
            sistema.getUsuarios().remove(usuario.get());
            return true;
        }
        return false;
    }
}
