package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.controller.EnvioController;
import co.edu.uniquindio.poo.controller.UsuarioController;
import co.edu.uniquindio.poo.model.Envio;
import co.edu.uniquindio.poo.model.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class UserDashboardViewController {
    
    @FXML private Label welcomeLabel;
    @FXML private TableView<Envio> enviosTable;
    @FXML private TableColumn<Envio, String> colId;
    @FXML private TableColumn<Envio, String> colOrigen;
    @FXML private TableColumn<Envio, String> colDestino;
    @FXML private TableColumn<Envio, String> colEstado;
    @FXML private TableColumn<Envio, Double> colCosto;
    @FXML private ComboBox<String> filtroEstadoCombo;
    @FXML private Label totalEnviosLabel;
    @FXML private Label enviosActivosLabel;
    @FXML private Label enviosEntregadosLabel;
    
    private Usuario usuarioActual;
    private UsuarioController usuarioController;
    private EnvioController envioController;
    
    public void initialize() {
        usuarioActual = SessionManager.getCurrentUser();
        usuarioController = new UsuarioController();
        envioController = new EnvioController();
        
        if (usuarioActual != null) {
            welcomeLabel.setText("Bienvenido, " + usuarioActual.getNombreCompleto());
            configurarTabla();
            configurarFiltros();
            cargarEnvios();
            actualizarEstadisticas();
        }
    }
    
    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colOrigen.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getOrigen().getCiudad()
            )
        );
        colDestino.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDestino().getCiudad()
            )
        );
        colEstado.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEstado().toString()
            )
        );
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colCosto.setCellFactory(column -> new javafx.scene.control.TableCell<Envio, Double>() {
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
    }
    
    private void configurarFiltros() {
        filtroEstadoCombo.getItems().addAll("TODOS", "SOLICITADO", "ASIGNADO", "EN_RUTA", "ENTREGADO", "INCIDENCIA");
        filtroEstadoCombo.setValue("TODOS");
    }
    
    private void cargarEnvios() {
        List<Envio> envios;
        String filtro = filtroEstadoCombo.getValue();
        
        if (filtro.equals("TODOS")) {
            envios = usuarioController.obtenerEnviosUsuario(usuarioActual.getIdUsuario());
        } else {
            Envio.EstadoEnvio estado = Envio.EstadoEnvio.valueOf(filtro);
            envios = usuarioController.filtrarEnviosPorEstado(usuarioActual.getIdUsuario(), estado);
        }
        
        enviosTable.setItems(FXCollections.observableArrayList(envios));
    }
    
    private void actualizarEstadisticas() {
        List<Envio> todosEnvios = usuarioController.obtenerEnviosUsuario(usuarioActual.getIdUsuario());
        totalEnviosLabel.setText(String.valueOf(todosEnvios.size()));
        
        long activos = todosEnvios.stream()
                .filter(e -> e.getEstado() != Envio.EstadoEnvio.ENTREGADO)
                .count();
        enviosActivosLabel.setText(String.valueOf(activos));
        
        long entregados = todosEnvios.stream()
                .filter(e -> e.getEstado() == Envio.EstadoEnvio.ENTREGADO)
                .count();
        enviosEntregadosLabel.setText(String.valueOf(entregados));
    }
    
    @FXML
    void handleFiltrar(ActionEvent event) {
        cargarEnvios();
    }
    
    @FXML
    void handleNuevoEnvio(ActionEvent event) {
        NavigationController.navigateToNuevoEnvio();
    }
    
    @FXML
    void handlePerfil(ActionEvent event) {
        mostrarAlerta("Perfil", "Usuario: " + usuarioActual.getNombreCompleto() + 
                     "\nCorreo: " + usuarioActual.getCorreoElectronico() +
                     "\nTeléfono: " + usuarioActual.getTelefono());
    }
    
    @FXML
    void handleLogout(ActionEvent event) {
        SessionManager.logout();
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
