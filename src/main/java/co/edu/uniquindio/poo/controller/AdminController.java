package co.edu.uniquindio.poo.controller;

import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.factory.EntidadFactory;
import co.edu.uniquindio.poo.bridge.*;
import co.edu.uniquindio.poo.adapter.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class AdminController {
    private SistemaGestion sistema;
    private ServicioNotificacion notificadorEmail;
    private ServicioNotificacion notificadorSMS;
    
    public AdminController() {
        this.sistema = SistemaGestion.obtenerInstancia();
        this.notificadorEmail = new EmailAdapter();
        this.notificadorSMS = new SMSAdapter();
    }
    
    public Administrador registrarAdministrador(String nombre, String correo, String password) {
        // Validar que no exista el correo
        if (buscarAdminPorCorreo(correo).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        
        Administrador admin = EntidadFactory.crearAdministrador(nombre, correo);
        admin.setPassword(password);
        
        // Asignar todos los permisos por defecto
        for (Administrador.Permiso permiso : Administrador.Permiso.values()) {
            admin.agregarPermiso(permiso);
        }
        
        sistema.registrarAdministrador(admin);
        return admin;
    }
    
    public Optional<Administrador> autenticar(String correo, String password) {
        return sistema.getAdministradores().stream()
                .filter(a -> a.getCorreo().equals(correo) && 
                           a.getPassword().equals(password))
                .findFirst();
    }
    
    public Optional<Administrador> buscarAdminPorCorreo(String correo) {
        return sistema.getAdministradores().stream()
                .filter(a -> a.getCorreo().equals(correo))
                .findFirst();
    }
    
    // Gestión de usuarios
    public List<Usuario> listarUsuarios() {
        return sistema.getUsuarios();
    }
    
    public void eliminarUsuario(String idUsuario) {
        UsuarioController usuarioController = new UsuarioController();
        usuarioController.eliminarUsuario(idUsuario);
    }
    
    // Gestión de envíos
    public List<Envio> listarEnvios() {
        return sistema.getEnvios();
    }
    
    // Gestión de incidencias
    public List<Incidencia> listarIncidencias() {
        return sistema.getIncidencias();
    }
    
    public List<Incidencia> listarIncidenciasPendientes() {
        return sistema.getIncidencias().stream()
                .filter(i -> i.getEstado() == Incidencia.EstadoIncidencia.REPORTADA ||
                           i.getEstado() == Incidencia.EstadoIncidencia.EN_REVISION)
                .collect(Collectors.toList());
    }
    
    public void resolverIncidencia(String idIncidencia) {
        sistema.getIncidencias().stream()
                .filter(i -> i.getIdIncidencia().equals(idIncidencia))
                .findFirst()
                .ifPresent(incidencia -> {
                    incidencia.setEstado(Incidencia.EstadoIncidencia.RESUELTA);
                });
    }
    
    // Métricas y estadísticas
    public Map<String, Object> obtenerMetricas() {
        Map<String, Object> metricas = new HashMap<>();
        
        metricas.put("totalUsuarios", sistema.getUsuarios().size());
        metricas.put("totalEnvios", sistema.getEnvios().size());
        
        long enviosActivos = sistema.getEnvios().stream()
                .filter(e -> e.getEstado() != Envio.EstadoEnvio.ENTREGADO)
                .count();
        metricas.put("enviosActivos", enviosActivos);
        
        long enviosEntregados = sistema.getEnvios().stream()
                .filter(e -> e.getEstado() == Envio.EstadoEnvio.ENTREGADO)
                .count();
        metricas.put("enviosEntregados", enviosEntregados);
        
        long incidenciasPendientes = sistema.getIncidencias().stream()
                .filter(i -> i.getEstado() == Incidencia.EstadoIncidencia.REPORTADA ||
                           i.getEstado() == Incidencia.EstadoIncidencia.EN_REVISION)
                .count();
        metricas.put("incidenciasPendientes", incidenciasPendientes);
        
        double ingresoTotal = sistema.getEnvios().stream()
                .mapToDouble(Envio::getCosto)
                .sum();
        metricas.put("ingresoTotal", ingresoTotal);
        
        return metricas;
    }
    
    // Generación de reportes usando el patrón Bridge
    public void generarReporteEnviosCSV(String nombreArchivo) {
        FormatoReporte formato = new FormatoCSV();
        Reporte reporte = new ReporteEnvios(formato, sistema.getEnvios());
        reporte.generar(nombreArchivo);
    }
    
    public void generarReporteEnviosPDF(String nombreArchivo) {
        FormatoReporte formato = new FormatoPDF();
        Reporte reporte = new ReporteEnvios(formato, sistema.getEnvios());
        reporte.generar(nombreArchivo);
    }
    
    public void generarReporteUsuariosCSV(String nombreArchivo) {
        FormatoReporte formato = new FormatoCSV();
        Reporte reporte = new ReporteUsuarios(formato, sistema.getUsuarios());
        reporte.generar(nombreArchivo);
    }
    
    public void generarReporteUsuariosPDF(String nombreArchivo) {
        FormatoReporte formato = new FormatoPDF();
        Reporte reporte = new ReporteUsuarios(formato, sistema.getUsuarios());
        reporte.generar(nombreArchivo);
    }
    
    // Notificaciones masivas usando Adapter
    public void enviarNotificacionEmail(String destinatario, String mensaje) {
        notificadorEmail.enviarNotificacion(destinatario, mensaje);
    }
    
    public void enviarNotificacionSMS(String destinatario, String mensaje) {
        notificadorSMS.enviarNotificacion(destinatario, mensaje);
    }
    
    public void notificarATodosLosUsuarios(String mensaje) {
        for (Usuario usuario : sistema.getUsuarios()) {
            notificadorEmail.enviarNotificacion(usuario.getCorreoElectronico(), mensaje);
        }
    }
    
    // Nuevos métodos para el dashboard
    public List<Usuario> obtenerTodosUsuarios() {
        return sistema.getUsuarios();
    }
    
    public List<Envio> obtenerTodosEnvios() {
        return sistema.getEnvios();
    }
    
    public List<Envio> obtenerEnviosDeUsuario(String idUsuario) {
        return sistema.getEnvios().stream()
                .filter(e -> e.getUsuario().getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }
    
    public int notificarTodosUsuarios(String mensaje) {
        List<Usuario> usuarios = sistema.getUsuarios();
        for (Usuario usuario : usuarios) {
            notificadorEmail.enviarNotificacion(usuario.getCorreoElectronico(), mensaje);
        }
        return usuarios.size();
    }
    
    // Métodos de eliminación
    public boolean eliminarEnvio(String idEnvio) {
        EnvioController envioController = new EnvioController();
        return envioController.eliminarEnvio(idEnvio);
    }
}
