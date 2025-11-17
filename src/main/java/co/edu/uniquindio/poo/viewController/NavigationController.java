package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationController {
    private static NavigationController instance;
    private static Stage primaryStage;
    
    private NavigationController() {}
    
    public static NavigationController getInstance() {
        if (instance == null) {
            instance = new NavigationController();
        }
        return instance;
    }
    
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }
    
    public static void navigateTo(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/edu/uniquindio/demop2pf/" + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + fxmlFile);
        }
    }
    
    public static void navigateToLogin() {
        navigateTo("login-view.fxml", "Sistema de Gestión de Envíos - Login");
    }
    
    public static void navigateToRegister() {
        navigateTo("register-view.fxml", "Registro de Usuario");
    }
    
    public static void navigateToUserDashboard() {
        navigateTo("user-dashboard.fxml", "Dashboard - Usuario");
    }
    
    public static void navigateToAdminDashboard() {
        navigateTo("admin-dashboard.fxml", "Dashboard - Administrador");
    }
    
    public static void navigateToNuevoEnvio() {
        navigateTo("nuevo-envio.fxml", "Crear Nuevo Envío");
    }
}