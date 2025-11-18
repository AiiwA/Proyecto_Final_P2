package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.controller.AdminController;
import co.edu.uniquindio.poo.controller.EnvioController;
import co.edu.uniquindio.poo.model.*;
import co.edu.uniquindio.poo.observer.AdminDashboardObserver;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminDashboardViewController {
    
    // Labels de métricas
    @FXML private Label welcomeLabel;
    @FXML private Label totalUsuariosLabel;
    @FXML private Label totalEnviosLabel;
    @FXML private Label enviosActivosLabel;
    @FXML private Label ingresoTotalLabel;
    
    // Tabla de Usuarios
    @FXML private TextField filtroUsuarioField;
    @FXML private TableView<Usuario> usuariosTable;
    @FXML private TableColumn<Usuario, String> colUsuarioId;
    @FXML private TableColumn<Usuario, String> colUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colUsuarioEmail;
    @FXML private TableColumn<Usuario, String> colUsuarioTelefono;
    @FXML private TableColumn<Usuario, Integer> colUsuarioEnvios;
    
    // Tabla de Envíos
    @FXML private ComboBox<String> filtroEstadoEnvioCombo;
    @FXML private TableView<Envio> enviosTable;
    @FXML private TableColumn<Envio, String> colEnvioId;
    @FXML private TableColumn<Envio, String> colEnvioUsuario;
    @FXML private TableColumn<Envio, String> colEnvioOrigen;
    @FXML private TableColumn<Envio, String> colEnvioDestino;
    @FXML private TableColumn<Envio, String> colEnvioEstado;
    @FXML private TableColumn<Envio, Double> colEnvioCosto;
    
    // Tabla de Repartidores
    @FXML private TableView<Repartidor> repartidoresTable;
    @FXML private TableColumn<Repartidor, String> colRepartidorId;
    @FXML private TableColumn<Repartidor, String> colRepartidorNombre;
    @FXML private TableColumn<Repartidor, String> colRepartidorDocumento;
    @FXML private TableColumn<Repartidor, String> colRepartidorTelefono;
    @FXML private TableColumn<Repartidor, String> colRepartidorZonaCobertura;
    @FXML private TableColumn<Repartidor, String> colRepartidorEstado;
    @FXML private TableColumn<Repartidor, Integer> colRepartidorEnvios;
    
    // Campos de formulario de repartidor
    @FXML private TextField nombreRepartidorField;
    @FXML private TextField documentoRepartidorField;
    @FXML private TextField telefonoRepartidorField;
    @FXML private TextField zonaCoberturaRepartidorField;
    @FXML private ComboBox<String> estadoRepartidorCombo;
    
    // Asignación de repartidor a envío
    @FXML private ComboBox<String> envioAsignarCombo;
    @FXML private ComboBox<String> repartidorAsignarCombo;
    
    private Administrador adminActual;
    private AdminController adminController;
    private EnvioController envioController;
    
    public void initialize() {
        System.out.println("[DEBUG] AdminDashboardViewController.initialize() called");
        adminActual = SessionManager.getCurrentAdmin();
        System.out.println("[DEBUG] getCurrentAdmin() returned: " + adminActual);
        adminController = new AdminController();
        envioController = new EnvioController();
        
        if (adminActual != null) {
            System.out.println("[DEBUG] Admin found: " + adminActual.getNombre());
            welcomeLabel.setText("Bienvenido Admin: " + adminActual.getNombre());
            configurarTablas();
            cargarDatos();
        } else {
            System.out.println("[DEBUG] NO ADMIN FOUND IN SESSION!");
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró administrador en sesión");
            NavigationController.navigateToLogin();
        }
    }
    
    private void configurarTablas() {
        // Configurar tabla de usuarios
        colUsuarioId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colUsuarioEmail.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        colUsuarioTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colUsuarioEnvios.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            int numEnvios = adminController.obtenerEnviosDeUsuario(usuario.getIdUsuario()).size();
            return new javafx.beans.property.SimpleIntegerProperty(numEnvios).asObject();
        });
        
        // Configurar tabla de envíos
        colEnvioId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colEnvioUsuario.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario().getNombreCompleto())
        );
        colEnvioOrigen.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getOrigen().getCiudad())
        );
        colEnvioDestino.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDestino().getCiudad())
        );
        colEnvioEstado.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado().toString())
        );
        colEnvioCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colEnvioCosto.setCellFactory(column -> new javafx.scene.control.TableCell<Envio, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.0f", item));
                }
            }
        });
        
        // Configurar ComboBox de estados
        filtroEstadoEnvioCombo.getItems().addAll("TODOS", "SOLICITADO", "EN_RUTA", "ENTREGADO", "INCIDENCIA");
        filtroEstadoEnvioCombo.setValue("TODOS");
        
        // Configurar tabla de repartidores
        if (repartidoresTable != null) {
            colRepartidorId.setCellValueFactory(new PropertyValueFactory<>("idRepartidor"));
            colRepartidorNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colRepartidorDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
            colRepartidorTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            colRepartidorZonaCobertura.setCellValueFactory(new PropertyValueFactory<>("zonaCobertura"));
            colRepartidorEstado.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado().toString())
            );
            colRepartidorEnvios.setCellValueFactory(new PropertyValueFactory<>("enviosAsignados"));
            
            // Formatear columna de estado
            colRepartidorEstado.setCellFactory(column -> new javafx.scene.control.TableCell<Repartidor, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        switch (item) {
                            case "ACTIVO" -> setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                            case "EN_RUTA" -> setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                            case "INACTIVO" -> setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                            default -> setStyle("");
                        }
                    }
                }
            });
            
            // Configurar ComboBox de estados
            if (estadoRepartidorCombo != null) {
                estadoRepartidorCombo.setItems(FXCollections.observableArrayList("ACTIVO", "INACTIVO", "EN_RUTA"));
                estadoRepartidorCombo.setValue("ACTIVO");
            }
        }
    }
    
    private void cargarDatos() {
        cargarMetricas();
        cargarUsuarios();
        cargarEnvios();
        cargarRepartidores();
        actualizarCombosAsignacion();
    }
    
    private void cargarMetricas() {
        try {
            Map<String, Object> metricas = adminController.obtenerMetricas();
            
            totalUsuariosLabel.setText(metricas.get("totalUsuarios").toString());
            totalEnviosLabel.setText(metricas.get("totalEnvios").toString());
            enviosActivosLabel.setText(metricas.get("enviosActivos").toString());
            
            double ingreso = (Double) metricas.get("ingresoTotal");
            ingresoTotalLabel.setText(String.format("$%.0f", ingreso));
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al cargar métricas: " + e.getMessage());
        }
    }
    
    private void cargarUsuarios() {
        List<Usuario> usuarios = adminController.obtenerTodosUsuarios();
        usuariosTable.setItems(FXCollections.observableArrayList(usuarios));
    }
    
    private void cargarEnvios() {
        List<Envio> envios = adminController.obtenerTodosEnvios();
        javafx.collections.ObservableList<Envio> enviosObservable = FXCollections.observableArrayList(envios);
        enviosTable.setItems(enviosObservable);
        
        // Agregar el AdminDashboardObserver a cada envío para que se actualice automáticamente
        // Se usa la lista observable de la tabla para que los cambios se reflejen directamente
        AdminDashboardObserver observer = new AdminDashboardObserver(enviosObservable);
        for (Envio envio : envios) {
            envio.agregarObservador(observer);
        }
    }
    
    // ===== GESTIÓN DE USUARIOS =====
    
    @FXML
    void handleFiltrarUsuarios(ActionEvent event) {
        String filtro = filtroUsuarioField.getText().trim().toLowerCase();
        if (filtro.isEmpty()) {
            cargarUsuarios();
            return;
        }
        
        List<Usuario> usuarios = adminController.obtenerTodosUsuarios().stream()
                .filter(u -> u.getNombreCompleto().toLowerCase().contains(filtro) ||
                           u.getCorreoElectronico().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
        
        usuariosTable.setItems(FXCollections.observableArrayList(usuarios));
    }
    
    @FXML
    void handleMostrarTodosUsuarios(ActionEvent event) {
        filtroUsuarioField.clear();
        cargarUsuarios();
    }
    
    @FXML
    void handleVerDetallesUsuario(ActionEvent event) {
        Usuario seleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Por favor seleccione un usuario");
            return;
        }
        
        int numEnvios = adminController.obtenerEnviosDeUsuario(seleccionado.getIdUsuario()).size();
        
        String detalles = "=== DETALLES DEL USUARIO ===\n\n" +
                "ID: " + seleccionado.getIdUsuario() + "\n" +
                "Nombre Completo: " + seleccionado.getNombreCompleto() + "\n" +
                "Email: " + seleccionado.getCorreoElectronico() + "\n" +
                "Teléfono: " + seleccionado.getTelefono() + "\n" +
                "Total de Envíos: " + numEnvios;
        
        mostrarAlerta(Alert.AlertType.INFORMATION, "Detalles del Usuario", detalles);
    }
    
    @FXML
    void handleVerEnviosUsuario(ActionEvent event) {
        Usuario seleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Por favor seleccione un usuario");
            return;
        }
        
        List<Envio> enviosUsuario = adminController.obtenerEnviosDeUsuario(seleccionado.getIdUsuario());
        enviosTable.setItems(FXCollections.observableArrayList(enviosUsuario));
        
        mostrarAlerta(Alert.AlertType.INFORMATION, "Envíos del Usuario", 
                "Mostrando " + enviosUsuario.size() + " envíos del usuario: " + seleccionado.getNombreCompleto());
    }
    
    @FXML
    void handleEliminarUsuario(ActionEvent event) {
        Usuario seleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Por favor seleccione un usuario");
            return;
        }
        
        // Confirmar eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar este usuario?");
        confirmacion.setContentText("Usuario: " + seleccionado.getNombreCompleto() + "\n" +
                "Email: " + seleccionado.getCorreoElectronico() + "\n\n" +
                "ADVERTENCIA: Esta acción no se puede deshacer.");
        
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                adminController.eliminarUsuario(seleccionado.getIdUsuario());
                cargarUsuarios();
                cargarMetricas();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Usuario Eliminado", 
                        "El usuario ha sido eliminado exitosamente del sistema");
            } catch (IllegalStateException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "No se puede eliminar", e.getMessage());
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                        "Error al eliminar usuario: " + e.getMessage());
            }
        }
    }
    
    // ===== REPARTIDORES REMOVIDOS - No es requerimiento del sistema =====
    
    // ===== GESTIÓN DE ENVÍOS =====
    
    @FXML
    void handleFiltrarEnvios(ActionEvent event) {
        String estadoSeleccionado = filtroEstadoEnvioCombo.getValue();
        if (estadoSeleccionado.equals("TODOS")) {
            cargarEnvios();
            return;
        }
        
        Envio.EstadoEnvio estado = Envio.EstadoEnvio.valueOf(estadoSeleccionado);
        List<Envio> enviosFiltrados = adminController.obtenerTodosEnvios().stream()
                .filter(e -> e.getEstado() == estado)
                .collect(Collectors.toList());
        
        enviosTable.setItems(FXCollections.observableArrayList(enviosFiltrados));
    }
    
    @FXML
    void handleMostrarTodosEnvios(ActionEvent event) {
        filtroEstadoEnvioCombo.setValue("TODOS");
        cargarEnvios();
    }
    
    @FXML
    void handleVerDetallesEnvio(ActionEvent event) {
        Envio seleccionado = enviosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Por favor seleccione un envío");
            return;
        }
        
        String detalles = "=== DETALLES DEL ENVÍO ===\n\n" +
                "ID Envío: " + seleccionado.getIdEnvio() + "\n" +
                "Usuario: " + seleccionado.getUsuario().getNombreCompleto() + "\n" +
                "Origen: " + seleccionado.getOrigen().getCiudad() + " - " + seleccionado.getOrigen().getCalle() + "\n" +
                "Destino: " + seleccionado.getDestino().getCiudad() + " - " + seleccionado.getDestino().getCalle() + "\n" +
                "Estado: " + seleccionado.getEstado() + "\n" +
                "Tipo: " + seleccionado.getTipoEnvio() + "\n" +
                "Peso: " + seleccionado.getPeso() + " kg\n" +
                "Costo: $" + String.format("%.0f", seleccionado.getCosto()) + "\n" +
                "Fecha Creación: " + seleccionado.getFechaCreacion();
        
        if (seleccionado.getDescripcion() != null) {
            detalles += "\nDescripción: " + seleccionado.getDescripcion();
        }
        
        mostrarAlerta(Alert.AlertType.INFORMATION, "Detalles del Envío", detalles);
    }
    

    
    @FXML
    void handleEliminarEnvio(ActionEvent event) {
        Envio seleccionado = enviosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Por favor seleccione un envío");
            return;
        }
        
        // Confirmar eliminación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de eliminar este envío?");
        confirmacion.setContentText("ID Envío: " + seleccionado.getIdEnvio() + "\n" +
                "Usuario: " + seleccionado.getUsuario().getNombreCompleto() + "\n" +
                "Estado: " + seleccionado.getEstado() + "\n" +
                "Costo: $" + String.format("%.0f", seleccionado.getCosto()) + "\n\n" +
                "ADVERTENCIA: Esta acción no se puede deshacer.");
        
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                adminController.eliminarEnvio(seleccionado.getIdEnvio());
                cargarEnvios();
                cargarMetricas();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Envío Eliminado", 
                        "El envío ha sido eliminado exitosamente del sistema");
            } catch (IllegalStateException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "No se puede eliminar", e.getMessage());
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                        "Error al eliminar envío: " + e.getMessage());
            }
        }
    }
    
    @FXML
    void handleCambiarEstadoEnvio(ActionEvent event) {
        Envio seleccionado = enviosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Por favor seleccione un envío");
            return;
        }
        
        ChoiceDialog<Envio.EstadoEnvio> dialog = new ChoiceDialog<>(
                seleccionado.getEstado(),
                Envio.EstadoEnvio.values()
        );
        dialog.setTitle("Cambiar Estado");
        dialog.setHeaderText("Cambiar estado del envío");
        dialog.setContentText("Nuevo estado:");
        
        Optional<Envio.EstadoEnvio> resultado = dialog.showAndWait();
        resultado.ifPresent(nuevoEstado -> {
            envioController.actualizarEstado(seleccionado.getIdEnvio(), nuevoEstado);
            cargarEnvios();
            cargarMetricas();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Estado Actualizado", 
                    "Estado del envío actualizado a: " + nuevoEstado);
        });
    }
    
    // ===== REPORTES Y NOTIFICACIONES =====
    
    @FXML
    void handleGenerarReporteCSV(ActionEvent event) {
        try {
            String archivo = "reporte_envios_" + System.currentTimeMillis();
            adminController.generarReporteEnviosCSV(archivo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reporte Generado", 
                    "El reporte CSV ha sido generado exitosamente: " + archivo + ".csv");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al generar reporte: " + e.getMessage());
        }
    }
    
    @FXML
    void handleGenerarReportePDF(ActionEvent event) {
        try {
            String archivo = "reporte_envios_" + System.currentTimeMillis();
            adminController.generarReporteEnviosPDF(archivo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reporte Generado", 
                    "El reporte PDF ha sido generado exitosamente: " + archivo + ".pdf");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al generar reporte: " + e.getMessage());
        }
    }
    
    @FXML
    void handleGenerarReporteUsuariosCSV(ActionEvent event) {
        try {
            String archivo = "reporte_usuarios_" + System.currentTimeMillis();
            adminController.generarReporteUsuariosCSV(archivo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reporte Generado", 
                    "El reporte de usuarios CSV ha sido generado: " + archivo + ".csv");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al generar reporte: " + e.getMessage());
        }
    }
    
    @FXML
    void handleGenerarReporteUsuariosPDF(ActionEvent event) {
        try {
            String archivo = "reporte_usuarios_" + System.currentTimeMillis();
            adminController.generarReporteUsuariosPDF(archivo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Reporte Generado", 
                    "El reporte de usuarios PDF ha sido generado: " + archivo + ".pdf");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al generar reporte: " + e.getMessage());
        }
    }
    
    @FXML
    void handleNotificarUsuarios(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Notificar Usuarios");
        dialog.setHeaderText("Enviar notificación a todos los usuarios");
        dialog.setContentText("Mensaje:");
        
        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(mensaje -> {
            int notificados = adminController.notificarTodosUsuarios(mensaje);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Notificaciones Enviadas", 
                    "Se enviaron notificaciones a " + notificados + " usuarios");
        });
    }
    
    @FXML
    void handleEstadisticasDetalladas(ActionEvent event) {
        Map<String, Object> metricas = adminController.obtenerMetricas();
        
        String estadisticas = "=== ESTADÍSTICAS DETALLADAS DEL SISTEMA ===\n\n" +
                "Total de Usuarios: " + metricas.get("totalUsuarios") + "\n" +
                "Total de Envíos: " + metricas.get("totalEnvios") + "\n" +
                "Envíos Activos: " + metricas.get("enviosActivos") + "\n" +
                "Envíos Completados: " + metricas.get("enviosCompletados") + "\n" +
                "Ingreso Total: $" + String.format("%.0f", (Double) metricas.get("ingresoTotal")) + "\n\n" +
                "Estado de Envíos:\n" +
                "- Solicitados: " + contarEnviosPorEstado(Envio.EstadoEnvio.SOLICITADO) + "\n" +
                "- En Ruta: " + contarEnviosPorEstado(Envio.EstadoEnvio.EN_RUTA) + "\n" +
                "- Entregados: " + contarEnviosPorEstado(Envio.EstadoEnvio.ENTREGADO) + "\n" +
                "- Con Incidencia: " + contarEnviosPorEstado(Envio.EstadoEnvio.INCIDENCIA);
        
        mostrarAlerta(Alert.AlertType.INFORMATION, "Estadísticas Detalladas", estadisticas);
    }
    
    @FXML
    void handleExportarDatos(ActionEvent event) {
        try {
            adminController.generarReporteEnviosCSV("datos_completos_sistema");
            adminController.generarReporteUsuariosCSV("datos_usuarios_sistema");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Datos Exportados", 
                    "Todos los datos del sistema han sido exportados exitosamente");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al exportar datos: " + e.getMessage());
        }
    }
    
    // ===== UTILIDADES =====
    
    @FXML
    void handleActualizar(ActionEvent event) {
        cargarDatos();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Datos Actualizados", "Todos los datos han sido actualizados");
    }
    
    @FXML
    void handleLogout(ActionEvent event) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar Sesión");
        confirmacion.setHeaderText("¿Está seguro que desea cerrar sesión?");
        confirmacion.setContentText("Será redirigido a la pantalla de login");
        
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            SessionManager.logout();
        }
    }
    
    private int contarEnviosPorEstado(Envio.EstadoEnvio estado) {
        return (int) adminController.obtenerTodosEnvios().stream()
                .filter(e -> e.getEstado() == estado)
                .count();
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    // ===== GESTIÓN DE REPARTIDORES =====
    
    @FXML
    void handleAgregarRepartidor(ActionEvent event) {
        try {
            String nombre = nombreRepartidorField.getText().trim();
            String documento = documentoRepartidorField.getText().trim();
            String telefono = telefonoRepartidorField.getText().trim();
            String zonaCobertura = zonaCoberturaRepartidorField.getText().trim();
            String estadoStr = estadoRepartidorCombo.getValue();
            
            if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty() || zonaCobertura.isEmpty() || estadoStr == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Todos los campos son obligatorios");
                return;
            }
            
            Repartidor.EstadoRepartidor estado = Repartidor.EstadoRepartidor.valueOf(estadoStr);
            adminController.registrarRepartidor(nombre, documento, telefono, zonaCobertura, estado);
            limpiarFormularioRepartidor();
            cargarRepartidores();
            actualizarCombosAsignacion();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Repartidor agregado correctamente");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al agregar repartidor: " + e.getMessage());
        }
    }
    
    @FXML
    void handleActualizarRepartidor(ActionEvent event) {
        Repartidor seleccionado = repartidoresTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Seleccione un repartidor para actualizar");
            return;
        }
        
        try {
            String nombre = nombreRepartidorField.getText().trim();
            String documento = documentoRepartidorField.getText().trim();
            String telefono = telefonoRepartidorField.getText().trim();
            String zonaCobertura = zonaCoberturaRepartidorField.getText().trim();
            String estadoStr = estadoRepartidorCombo.getValue();
            
            if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty() || zonaCobertura.isEmpty() || estadoStr == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Todos los campos son obligatorios");
                return;
            }
            
            Repartidor.EstadoRepartidor estado = Repartidor.EstadoRepartidor.valueOf(estadoStr);
            adminController.actualizarRepartidor(seleccionado.getIdRepartidor(), nombre, documento, telefono, zonaCobertura, estado);
            limpiarFormularioRepartidor();
            cargarRepartidores();
            actualizarCombosAsignacion();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Repartidor actualizado correctamente");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al actualizar repartidor: " + e.getMessage());
        }
    }
    
    @FXML
    void handleEliminarRepartidor(ActionEvent event) {
        Repartidor seleccionado = repartidoresTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Seleccione un repartidor para eliminar");
            return;
        }
        
        if (seleccionado.getEnviosAsignados() > 0) {
            mostrarAlerta(Alert.AlertType.ERROR, "No se puede eliminar", 
                    "El repartidor tiene " + seleccionado.getEnviosAsignados() + " envío(s) asignado(s)");
            return;
        }
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Eliminar repartidor?");
        confirmacion.setContentText("¿Está seguro de eliminar a " + seleccionado.getNombre() + "?");
        
        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                adminController.eliminarRepartidor(seleccionado.getIdRepartidor());
                limpiarFormularioRepartidor();
                cargarRepartidores();
                actualizarCombosAsignacion();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Repartidor eliminado correctamente");
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al eliminar repartidor: " + e.getMessage());
            }
        }
    }
    
    @FXML
    void handleSeleccionarRepartidor() {
        Repartidor seleccionado = repartidoresTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            nombreRepartidorField.setText(seleccionado.getNombre());
            documentoRepartidorField.setText(seleccionado.getDocumento());
            telefonoRepartidorField.setText(seleccionado.getTelefono());
            zonaCoberturaRepartidorField.setText(seleccionado.getZonaCobertura());
            estadoRepartidorCombo.setValue(seleccionado.getEstado().toString());
        }
    }
    
    @FXML
    void handleVerEnviosRepartidor(ActionEvent event) {
        Repartidor seleccionado = repartidoresTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", 
                    "Por favor seleccione un repartidor de la tabla");
            return;
        }
        
        // Obtener todos los envíos asignados al repartidor
        List<Envio> enviosDelRepartidor = adminController.obtenerTodosEnvios().stream()
                .filter(e -> e.getRepartidor() != null && 
                            e.getRepartidor().getIdRepartidor().equals(seleccionado.getIdRepartidor()))
                .collect(Collectors.toList());
        
        if (enviosDelRepartidor.isEmpty()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sin Envíos", 
                    "El repartidor " + seleccionado.getNombre() + " no tiene envíos asignados");
            return;
        }
        
        // Construir lista de envíos
        StringBuilder contenido = new StringBuilder();
        contenido.append(String.format("Total de envíos asignados: %d\n", enviosDelRepartidor.size()));
        contenido.append("═══════════════════════════════════════════\n\n");
        
        for (int i = 0; i < enviosDelRepartidor.size(); i++) {
            Envio envio = enviosDelRepartidor.get(i);
            contenido.append(String.format("【 ENVÍO %d 】\n", i + 1));
            contenido.append(String.format("ID: %s\n", envio.getIdEnvio()));
            contenido.append(String.format("Estado: %s\n", envio.getEstado()));
            contenido.append(String.format("Tipo: %s\n", envio.getTipoEnvio()));
            contenido.append(String.format("Origen: %s\n", envio.getOrigen().getCiudad()));
            contenido.append(String.format("Destino: %s\n", envio.getDestino().getCiudad()));
            contenido.append(String.format("Peso: %.2f kg\n", envio.getPeso()));
            contenido.append(String.format("Costo: $%.0f\n", envio.getCosto()));
            contenido.append(String.format("Destinatario: %s\n", envio.getNombreDestinatario()));
            contenido.append(String.format("Teléfono: %s\n", envio.getTelefonoDestinatario()));
            if (i < enviosDelRepartidor.size() - 1) {
                contenido.append("\n───────────────────────────────────────────\n\n");
            }
        }
        
        // Crear TextArea con scroll
        TextArea textArea = new TextArea(contenido.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(550, 400);
        textArea.setStyle("-fx-font-family: 'Consolas', 'Courier New', monospace; -fx-font-size: 12px;");
        
        // Crear ventana emergente con el TextArea
        Alert ventanaEnvios = new Alert(Alert.AlertType.INFORMATION);
        ventanaEnvios.setTitle("Envíos del Repartidor");
        ventanaEnvios.setHeaderText("Envíos asignados a: " + seleccionado.getNombre());
        ventanaEnvios.getDialogPane().setContent(textArea);
        ventanaEnvios.getDialogPane().setPrefSize(600, 500);
        ventanaEnvios.setResizable(true);
        ventanaEnvios.showAndWait();
    }
    
    @FXML
    void handleAsignarRepartidor(ActionEvent event) {
        String idEnvio = envioAsignarCombo.getValue();
        String repartidorSeleccionado = repartidorAsignarCombo.getValue();
        
        if (idEnvio == null || repartidorSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección Incompleta", 
                    "Seleccione un envío y un repartidor");
            return;
        }
        
        try {
            // Extraer ID del repartidor (formato: "Nombre - ID")
            String idRepartidor = repartidorSeleccionado.split(" - ")[1];
            adminController.asignarRepartidorAEnvio(idEnvio, idRepartidor);
            cargarEnvios();
            cargarRepartidores();
            actualizarCombosAsignacion();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Repartidor asignado al envío correctamente");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al asignar repartidor: " + e.getMessage());
        }
    }
    
    private void cargarRepartidores() {
        if (repartidoresTable != null) {
            List<Repartidor> repartidores = adminController.obtenerTodosRepartidores();
            repartidoresTable.setItems(FXCollections.observableArrayList(repartidores));
        }
    }
    
    private void limpiarFormularioRepartidor() {
        if (nombreRepartidorField != null) nombreRepartidorField.clear();
        if (documentoRepartidorField != null) documentoRepartidorField.clear();
        if (telefonoRepartidorField != null) telefonoRepartidorField.clear();
        if (zonaCoberturaRepartidorField != null) zonaCoberturaRepartidorField.clear();
        if (estadoRepartidorCombo != null) estadoRepartidorCombo.setValue("ACTIVO");
    }
    
    private void actualizarCombosAsignacion() {
        if (envioAsignarCombo != null) {
            List<Envio> enviosSinRepartidor = adminController.obtenerTodosEnvios().stream()
                    .filter(e -> e.getRepartidor() == null || e.getEstado() == Envio.EstadoEnvio.SOLICITADO)
                    .toList();
            envioAsignarCombo.setItems(FXCollections.observableArrayList(
                    enviosSinRepartidor.stream().map(Envio::getIdEnvio).toList()
            ));
        }
        
        if (repartidorAsignarCombo != null) {
            List<Repartidor> repartidores = adminController.obtenerTodosRepartidores();
            repartidorAsignarCombo.setItems(FXCollections.observableArrayList(
                    repartidores.stream()
                            .map(r -> r.getNombre() + " - " + r.getIdRepartidor())
                            .toList()
            ));
        }
    }
}
