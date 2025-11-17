package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.utils.DataInitializer;
import co.edu.uniquindio.poo.viewController.NavigationController;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Inicializar datos de prueba
        DataInitializer.inicializarDatos();
        
        // Configurar el stage principal en el NavigationController
        NavigationController.setPrimaryStage(stage);
        
        // Configurar propiedades de la ventana
        stage.setTitle("CityDrop");
        stage.setWidth(900);
        stage.setHeight(650);
        stage.setResizable(false);
        
        // Navegar a la pantalla de login
        NavigationController.navigateToLogin();
    }

    public static void main(String[] args) {
        launch();
    }
}