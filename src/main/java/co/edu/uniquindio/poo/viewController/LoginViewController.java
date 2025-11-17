package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.controller.UsuarioController;
import co.edu.uniquindio.poo.controller.AdminController;
import co.edu.uniquindio.poo.model.Usuario;
import co.edu.uniquindio.poo.model.Administrador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class LoginViewController {
    
    @FXML private ComboBox<String> tipoUsuarioCombo;
    @FXML private TextField usuarioField;
    @FXML private PasswordField passwordField;
    @FXML private Label mensajeLabel;
    
    private UsuarioController usuarioController;
    private AdminController adminController;
    
    public void initialize() {
        usuarioController = new UsuarioController();
        adminController = new AdminController();
        
        // Configurar ComboBox
        tipoUsuarioCombo.getItems().addAll("Usuario", "Administrador");
        tipoUsuarioCombo.setValue("Usuario");
    }
    
    @FXML
    void handleLogin(ActionEvent event) {
        String tipoUsuario = tipoUsuarioCombo.getValue();
        String usuario = usuarioField.getText().trim();
        String password = passwordField.getText();
        
        System.out.println("[DEBUG] Login intent - Type: " + tipoUsuario + ", User: " + usuario);
        
        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Por favor complete todos los campos", "error");
            return;
        }
        
        try {
            switch (tipoUsuario) {
                case "Usuario":
                    Optional<Usuario> user = usuarioController.autenticar(usuario, password);
                    if (user.isPresent()) {
                        System.out.println("[DEBUG] User authenticated: " + user.get().getIdUsuario());
                        SessionManager.setCurrentUser(user.get());
                        mostrarMensaje("Login exitoso!", "success");
                        NavigationController.navigateToUserDashboard();
                    } else {
                        System.out.println("[DEBUG] User authentication failed");
                        mostrarMensaje("Credenciales inválidas", "error");
                    }
                    break;
                    
                case "Administrador":
                    System.out.println("[DEBUG] Attempting admin authentication...");
                    Optional<Administrador> admin = adminController.autenticar(usuario, password);
                    if (admin.isPresent()) {
                        System.out.println("[DEBUG] Admin authenticated: " + admin.get().getIdAdmin());
                        SessionManager.setCurrentAdmin(admin.get());
                        System.out.println("[DEBUG] Admin set in SessionManager");
                        mostrarMensaje("Login exitoso!", "success");
                        NavigationController.navigateToAdminDashboard();
                    } else {
                        System.out.println("[DEBUG] Admin authentication failed");
                        mostrarMensaje("Credenciales inválidas", "error");
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("[DEBUG] Login exception: " + e.getMessage());
            e.printStackTrace();
            mostrarMensaje("Error al intentar ingresar: " + e.getMessage(), "error");
        }
    }
    
    @FXML
    void handleRegister(ActionEvent event) {
        NavigationController.navigateToRegister();
    }
    
    private void mostrarMensaje(String mensaje, String tipo) {
        mensajeLabel.setText(mensaje);
        if (tipo.equals("error")) {
            mensajeLabel.setStyle("-fx-text-fill: red;");
        } else {
            mensajeLabel.setStyle("-fx-text-fill: green;");
        }
    }
}
