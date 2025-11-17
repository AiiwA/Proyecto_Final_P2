package co.edu.uniquindio.poo.viewController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.util.function.Consumer;

public class MapaSelectorViewController {

    @FXML
    private WebView mapaWebView;
    
    @FXML
    private TextField latitudField;
    
    @FXML
    private TextField longitudField;
    
    @FXML
    private Button confirmarButton;
    
    @FXML
    private Button cancelarButton;

    private WebEngine webEngine;
    private double latitudSeleccionada = 0;
    private double longitudSeleccionada = 0;
    private Consumer<double[]> onCoordinatesSelected;
    private Stage stage;

    @FXML
    public void initialize() {
        webEngine = mapaWebView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        
        // Inyectar este controlador como objeto 'java' en el contexto de JavaScript
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                try {
                    // Inyectar el bridge para que JavaScript pueda llamar a Java
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    window.setMember("java", this);
                    System.out.println("[MAPA] Bridge Java-JavaScript establecido correctamente");
                } catch (Exception e) {
                    System.err.println("[MAPA] Error al establecer bridge: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        
        // Cargar el mapa HTML
        cargarMapa();
        
        confirmarButton.setOnAction(e -> handleConfirmar());
        cancelarButton.setOnAction(e -> handleCancelar());
    }

    private void cargarMapa() {
        String htmlContent = generarHTMLMapa();
        webEngine.loadContent(htmlContent);
    }

    /**
     * Método llamado desde JavaScript cuando se hace clic en el mapa
     * Esta función es accesible para el JavaScript embebido
     */
    public void actualizarCoordenadasDesdeJS(double latitud, double longitud) {
        System.out.println("[JS] Coordenadas recibidas desde JavaScript: Lat=" + latitud + ", Lng=" + longitud);
        // Ejecutar en el hilo de JavaFX UI
        javafx.application.Platform.runLater(() -> {
            actualizarCoordenadas(latitud, longitud);
        });
    }

    /**
     * Actualiza los campos de texto con las coordenadas obtenidas del mapa
     */
    private void actualizarCoordenadas(double latitud, double longitud) {
        System.out.println("[MAPA] Coordenadas detectadas: Lat=" + latitud + ", Lng=" + longitud);
        
        this.latitudSeleccionada = latitud;
        this.longitudSeleccionada = longitud;
        
        // Actualizar los campos de texto en el hilo de JavaFX
        // Usar Locale.US para garantizar punto decimal en lugar de coma
        String latText = String.format(java.util.Locale.US, "%.6f", latitud);
        String lngText = String.format(java.util.Locale.US, "%.6f", longitud);
        
        System.out.println("[MAPA] Texto a insertar - Lat: " + latText + ", Lng: " + lngText);
        System.out.println("[MAPA] latitudField es null? " + (latitudField == null));
        System.out.println("[MAPA] longitudField es null? " + (longitudField == null));
        
        latitudField.setText(latText);
        longitudField.setText(lngText);
        
        System.out.println("[MAPA] DESPUÉS de setText - Lat: " + latitudField.getText() + ", Lng: " + longitudField.getText());
    }

    private String generarHTMLMapa() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Selector de Coordenadas</title>
                    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/leaflet.min.css" />
                    <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/leaflet.min.js"></script>
                    <style>
                        body {
                            margin: 0;
                            padding: 0;
                            font-family: Arial, sans-serif;
                        }
                        #map {
                            width: 100%;
                            height: 100vh;
                            background-color: #f0f0f0;
                        }
                        .info-box {
                            background-color: white;
                            padding: 10px 15px;
                            border-radius: 5px;
                            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
                            font-size: 14px;
                            position: fixed;
                            top: 10px;
                            left: 10px;
                            z-index: 1000;
                            max-width: 300px;
                        }
                        .info-box h4 {
                            margin: 0 0 10px 0;
                            color: #4CAF50;
                        }
                        .info-box p {
                            margin: 5px 0;
                        }
                        .instruction {
                            color: #666;
                            font-style: italic;
                        }
                        .success {
                            color: #4CAF50;
                            font-weight: bold;
                        }
                    </style>
                </head>
                <body>
                    <div id="map"></div>
                    <div class="info-box">
                        <h4>📍 Selector de Coordenadas</h4>
                        <p class="instruction">Haz clic en el mapa para seleccionar una ubicación</p>
                        <p><strong>Latitud:</strong> <span id="latitud" class="success">--</span>°</p>
                        <p><strong>Longitud:</strong> <span id="longitud" class="success">--</span>°</p>
                        <p style="margin-top: 10px; color: #4CAF50; font-size: 12px;">
                            ✓ Las coordenadas se actualizan automáticamente
                        </p>
                    </div>

                    <script>
                        // Inicializar el mapa (ubicación: Colombia)
                        var map = L.map('map').setView([4.5709, -75.5140], 6);
                        
                        // Agregar layer de OpenStreetMap
                        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                            attribution: '© OpenStreetMap contributors',
                            maxZoom: 19
                        }).addTo(map);
                        
                        var marker = null;
                        
                        // Agregar listener de click al mapa
                        map.on('click', function(event) {
                            var lat = parseFloat(event.latlng.lat.toFixed(6));
                            var lng = parseFloat(event.latlng.lng.toFixed(6));
                            
                            console.log('Clic en mapa detectado: ' + lat + ', ' + lng);
                            
                            // Actualizar pantalla del mapa
                            document.getElementById('latitud').textContent = lat;
                            document.getElementById('longitud').textContent = lng;
                            
                            // Eliminar marcador anterior si existe
                            if (marker) {
                                map.removeLayer(marker);
                            }
                            
                            // Crear nuevo marcador
                            marker = L.marker([lat, lng]).addTo(map)
                                .bindPopup('Ubicación seleccionada<br/>Lat: ' + lat + '<br/>Lng: ' + lng)
                                .openPopup();
                            
                            // LLAMAR DIRECTAMENTE A JAVA
                            try {
                                if (typeof java !== 'undefined' && java.actualizarCoordenadasDesdeJS) {
                                    console.log('Llamando a Java con: ' + lat + ', ' + lng);
                                    java.actualizarCoordenadasDesdeJS(lat, lng);
                                } else {
                                    console.log('Java no está disponible o función no existe');
                                }
                            } catch (error) {
                                console.log('Error al llamar a Java: ' + error);
                            }
                        });
                        
                        // Permitir zoom con scroll
                        map.scrollWheelZoom.enable();
                    </script>
                </body>
                </html>
                """;
    }

    public void setOnCoordinatesSelected(Consumer<double[]> callback) {
        this.onCoordinatesSelected = callback;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public double getLatitudSeleccionada() {
        return latitudSeleccionada;
    }
    
    public double getLongitudSeleccionada() {
        return longitudSeleccionada;
    }

    @FXML
    private void handleConfirmar() {
        try {
            // Leer directamente de los campos de texto
            String latStr = latitudField.getText().trim();
            String lngStr = longitudField.getText().trim();
            
            if (latStr.isEmpty() || lngStr.isEmpty()) {
                mostrarAlerta("Por favor selecciona una ubicación en el mapa haciendo clic");
                return;
            }
            
            // Reemplazar comas por puntos si existen (por si acaso)
            latStr = latStr.replace(',', '.');
            lngStr = lngStr.replace(',', '.');
            
            // Convertir a double para validar que sean números
            latitudSeleccionada = Double.parseDouble(latStr);
            longitudSeleccionada = Double.parseDouble(lngStr);
            
            System.out.println("[MAPA] Confirmando coordenadas - Lat: " + latitudSeleccionada + ", Lng: " + longitudSeleccionada);
            System.out.println("[MAPA] Callback es null? " + (onCoordinatesSelected == null));
            
            // Ejecutar el callback con las coordenadas
            if (onCoordinatesSelected != null) {
                System.out.println("[MAPA] Ejecutando callback con coordenadas...");
                onCoordinatesSelected.accept(new double[]{latitudSeleccionada, longitudSeleccionada});
                System.out.println("[MAPA] Callback ejecutado");
            } else {
                System.err.println("[MAPA] ERROR: No hay callback configurado");
            }

            // Cerrar la ventana
            if (stage != null) {
                stage.close();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error: Las coordenadas deben ser números válidos. Valor recibido: " + latitudField.getText() + ", " + longitudField.getText());
            e.printStackTrace();
        } catch (Exception e) {
            mostrarAlerta("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelar() {
        if (stage != null) {
            stage.close();
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

