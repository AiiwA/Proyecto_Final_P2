package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.controller.UsuarioController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterViewController {
    
    @FXML private TextField nombreField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label mensajeLabel;
    
    private UsuarioController usuarioController;
    
    public void initialize() {
        usuarioController = new UsuarioController();
    }
    
    @FXML
    void handleRegister(ActionEvent event) {
        String nombre = nombreField.getText().trim();
        String correo = correoField.getText().trim();
        String telefono = telefonoField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // Validaciones
        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty()) {
            mostrarMensaje("Por favor complete todos los campos", "error");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            mostrarMensaje("Las contraseñas no coinciden", "error");
            return;
        }
        
        if (password.length() < 6) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres", "error");
            return;
        }
        
        if (!correo.contains("@")) {
            mostrarMensaje("Por favor ingrese un correo válido", "error");
            return;
        }
        
        try {
            usuarioController.registrarUsuario(nombre, correo, telefono, password);
            mostrarMensaje("¡Registro exitoso! Redirigiendo al login...", "success");
            
            // Esperar 2 segundos antes de navegar
            javafx.application.Platform.runLater(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                NavigationController.navigateToLogin();
            });
            
        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage(), "error");
        } catch (Exception e) {
            mostrarMensaje("Error al registrar usuario: " + e.getMessage(), "error");
        }
    }
    
    @FXML
    void handleBack(ActionEvent event) {
        NavigationController.navigateToLogin();
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
