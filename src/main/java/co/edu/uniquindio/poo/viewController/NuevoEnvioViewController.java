package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.adapter.MapasAdapter;
import co.edu.uniquindio.poo.controller.EnvioController;
import co.edu.uniquindio.poo.controller.PagoController;
import co.edu.uniquindio.poo.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class NuevoEnvioViewController {
    
    // Información del paquete
    @FXML private TextField descripcionField;
    @FXML private TextField pesoField;
    @FXML private TextField volumenField;
    @FXML private TextField valorDeclaradoField;
    @FXML private ComboBox<String> tipoEnvioCombo;
    
    // Origen
    @FXML private TextField origenCalleField;
    @FXML private TextField origenCiudadField;
    @FXML private TextField origenCodigoPostalField;
    @FXML private TextField origenLatitudField;
    @FXML private TextField origenLongitudField;
    
    // Destino
    @FXML private TextField destinoCalleField;
    @FXML private TextField destinoCiudadField;
    @FXML private TextField destinoCodigoPostalField;
    @FXML private TextField destinoLatitudField;
    @FXML private TextField destinoLongitudField;
    
    // Destinatario
    @FXML private TextField destinatarioNombreField;
    @FXML private TextField destinatarioTelefonoField;
    @FXML private TextField destinatarioEmailField;
    
    // Servicios adicionales
    @FXML private CheckBox seguroCheckBox;
    @FXML private CheckBox expresCheckBox;
    
    // Resumen
    @FXML private Label distanciaLabel;
    @FXML private Label costoBaseLabel;
    @FXML private Label costosAdicionalesLabel;
    @FXML private Label costoTotalLabel;
    
    private Usuario usuarioActual;
    private EnvioController envioController;
    private PagoController pagoController;
    private MapasAdapter mapasAdapter;
    private double distanciaCalculada = 0.0;
    private double costoCalculado = 0.0;
    
    public void initialize() {
        usuarioActual = SessionManager.getCurrentUser();
        envioController = new EnvioController();
        pagoController = new PagoController();
        mapasAdapter = new MapasAdapter();
        
        configurarTiposEnvio();
        configurarValidaciones();
    }
    
    private void configurarTiposEnvio() {
        tipoEnvioCombo.getItems().addAll("ESTANDAR", "EXPRESS", "FRAGIL");
        tipoEnvioCombo.setValue("ESTANDAR");
    }
    
    private void configurarValidaciones() {
        // Validar solo números positivos en peso (máximo 1000 kg)
        pesoField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("\\d{0,4}(\\.\\d{0,2})?")) {
                pesoField.setText(oldVal);
            }
        });
        
        pesoField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !pesoField.getText().isEmpty()) {
                try {
                    double peso = Double.parseDouble(pesoField.getText());
                    if (peso <= 0) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Peso Inválido", "El peso debe ser mayor a 0 kg");
                        pesoField.clear();
                    } else if (peso > 1000) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Peso Excesivo", "El peso máximo permitido es 1000 kg");
                        pesoField.setText("1000");
                    }
                } catch (NumberFormatException e) {
                    pesoField.clear();
                }
            }
        });
        
        // Validar volumen positivo (máximo 100 m³)
        volumenField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("\\d{0,3}(\\.\\d{0,2})?")) {
                volumenField.setText(oldVal);
            }
        });
        
        volumenField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !volumenField.getText().isEmpty()) {
                try {
                    double volumen = Double.parseDouble(volumenField.getText());
                    if (volumen <= 0) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Volumen Inválido", "El volumen debe ser mayor a 0 m³");
                        volumenField.clear();
                    } else if (volumen > 100) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Volumen Excesivo", "El volumen máximo permitido es 100 m³");
                        volumenField.setText("100");
                    }
                } catch (NumberFormatException e) {
                    volumenField.clear();
                }
            }
        });
        
        // Validar valor declarado (máximo 100 millones)
        valorDeclaradoField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("\\d{0,9}(\\.\\d{0,2})?")) {
                valorDeclaradoField.setText(oldVal);
            }
        });
        
        valorDeclaradoField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !valorDeclaradoField.getText().isEmpty()) {
                try {
                    double valor = Double.parseDouble(valorDeclaradoField.getText());
                    if (valor < 0) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Valor Inválido", "El valor declarado no puede ser negativo");
                        valorDeclaradoField.clear();
                    } else if (valor > 100000000) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Valor Excesivo", "El valor máximo permitido es $100,000,000");
                        valorDeclaradoField.setText("100000000");
                    }
                } catch (NumberFormatException e) {
                    valorDeclaradoField.clear();
                }
            }
        });
        
        // Validar latitud (-90 a 90)
        origenLatitudField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("-?\\d{0,2}(\\.\\d{0,6})?")) {
                origenLatitudField.setText(oldVal);
            }
        });
        
        origenLatitudField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !origenLatitudField.getText().isEmpty()) {
                try {
                    double lat = Double.parseDouble(origenLatitudField.getText());
                    if (lat < -90 || lat > 90) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Latitud Inválida", "La latitud debe estar entre -90 y 90 grados");
                        origenLatitudField.clear();
                    }
                } catch (NumberFormatException e) {
                    origenLatitudField.clear();
                }
            }
        });
        
        // Validar longitud (-180 a 180)
        origenLongitudField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("-?\\d{0,3}(\\.\\d{0,6})?")) {
                origenLongitudField.setText(oldVal);
            }
        });
        
        origenLongitudField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !origenLongitudField.getText().isEmpty()) {
                try {
                    double lng = Double.parseDouble(origenLongitudField.getText());
                    if (lng < -180 || lng > 180) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Longitud Inválida", "La longitud debe estar entre -180 y 180 grados");
                        origenLongitudField.clear();
                    }
                } catch (NumberFormatException e) {
                    origenLongitudField.clear();
                }
            }
        });
        
        destinoLatitudField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("-?\\d{0,2}(\\.\\d{0,6})?")) {
                destinoLatitudField.setText(oldVal);
            }
        });
        
        destinoLatitudField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !destinoLatitudField.getText().isEmpty()) {
                try {
                    double lat = Double.parseDouble(destinoLatitudField.getText());
                    if (lat < -90 || lat > 90) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Latitud Inválida", "La latitud debe estar entre -90 y 90 grados");
                        destinoLatitudField.clear();
                    }
                } catch (NumberFormatException e) {
                    destinoLatitudField.clear();
                }
            }
        });
        
        destinoLongitudField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("-?\\d{0,3}(\\.\\d{0,6})?")) {
                destinoLongitudField.setText(oldVal);
            }
        });
        
        destinoLongitudField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !destinoLongitudField.getText().isEmpty()) {
                try {
                    double lng = Double.parseDouble(destinoLongitudField.getText());
                    if (lng < -180 || lng > 180) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Longitud Inválida", "La longitud debe estar entre -180 y 180 grados");
                        destinoLongitudField.clear();
                    }
                } catch (NumberFormatException e) {
                    destinoLongitudField.clear();
                }
            }
        });
        
        // Validar teléfono (solo números, 7-10 dígitos)
        destinatarioTelefonoField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("\\d{0,10}")) {
                destinatarioTelefonoField.setText(oldVal);
            }
        });
        
        destinatarioTelefonoField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !destinatarioTelefonoField.getText().isEmpty()) {
                String telefono = destinatarioTelefonoField.getText();
                if (telefono.length() < 7) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Teléfono Inválido", "El teléfono debe tener al menos 7 dígitos");
                    destinatarioTelefonoField.clear();
                }
            }
        });
        
        // Validar email
        destinatarioEmailField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !destinatarioEmailField.getText().isEmpty()) {
                String email = destinatarioEmailField.getText();
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Email Inválido", "Por favor ingrese un email válido (ej: usuario@ejemplo.com)");
                    destinatarioEmailField.clear();
                }
            }
        });
        
        // Validar nombre del destinatario (solo letras y espacios)
        destinatarioNombreField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{0,50}")) {
                destinatarioNombreField.setText(oldVal);
            }
        });
        
        destinatarioNombreField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !destinatarioNombreField.getText().isEmpty()) {
                String nombre = destinatarioNombreField.getText().trim();
                if (nombre.length() < 3) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Nombre Inválido", "El nombre debe tener al menos 3 caracteres");
                    destinatarioNombreField.clear();
                }
            }
        });
        
        // Validar descripción
        descripcionField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() > 200) {
                descripcionField.setText(oldVal);
            }
        });
        
        // Validar campos de dirección (no vacíos, longitud mínima)
        origenCalleField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !origenCalleField.getText().isEmpty()) {
                if (origenCalleField.getText().trim().length() < 5) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Dirección Inválida", "La dirección debe tener al menos 5 caracteres");
                    origenCalleField.clear();
                }
            }
        });
        
        destinoCalleField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused && !destinoCalleField.getText().isEmpty()) {
                if (destinoCalleField.getText().trim().length() < 5) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Dirección Inválida", "La dirección debe tener al menos 5 caracteres");
                    destinoCalleField.clear();
                }
            }
        });
    }
    
    @FXML
    void handleCalcularCosto(ActionEvent event) {
        try {
            if (!validarCamposBasicos()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", 
                    "Por favor complete todos los campos obligatorios para calcular el costo.");
                return;
            }
            
            // Crear direcciones temporales
            Direccion origen = construirDireccionOrigen();
            Direccion destino = construirDireccionDestino();
            
            // Obtener datos del paquete
            double peso = Double.parseDouble(pesoField.getText());
            double volumen = Double.parseDouble(volumenField.getText());
            double valorDeclarado = Double.parseDouble(valorDeclaradoField.getText());
            
            // Calcular distancia
            distanciaCalculada = mapasAdapter.calcularDistancia(origen, destino);
            distanciaLabel.setText(String.format("%.2f km", distanciaCalculada));
            
            // Calcular costo base
            Envio.TipoEnvio tipo = Envio.TipoEnvio.valueOf(tipoEnvioCombo.getValue());
            double costoBase = envioController.calcularCostoEnvio(peso, volumen, distanciaCalculada, tipo);
            costoBaseLabel.setText(String.format("$%.0f", costoBase));
            
            // Calcular servicios adicionales
            double costosAdicionales = 0.0;
            
            if (seguroCheckBox.isSelected()) {
                costosAdicionales += valorDeclarado * 0.10; // 10% del valor declarado
            }
            
            if (expresCheckBox.isSelected()) {
                costosAdicionales += costoBase * 0.50; // 50% del costo base
            }
            
            costosAdicionalesLabel.setText(String.format("$%.0f", costosAdicionales));
            
            // Calcular costo total
            costoCalculado = costoBase + costosAdicionales;
            
            costoTotalLabel.setText(String.format("$%.0f", costoCalculado));
            
            mostrarAlerta(Alert.AlertType.INFORMATION, "Costo Calculado", 
                String.format("El costo total del envío es: $%.0f\n\nDistancia: %.2f km\nCosto base: $%.0f\nServicios adicionales: $%.0f", 
                    costoCalculado, distanciaCalculada, costoBase, costosAdicionales));
            
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", 
                "Por favor ingrese valores numéricos válidos en los campos de peso, volumen y valor declarado.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al calcular el costo: " + e.getMessage());
        }
    }
    
    @FXML
    void handleCrearEnvio(ActionEvent event) {
        try {
            // Construir direcciones
            Direccion origen = construirDireccionOrigen();
            Direccion destino = construirDireccionDestino();
            
            Envio.TipoEnvio tipo = Envio.TipoEnvio.valueOf(tipoEnvioCombo.getValue());
            double peso = Double.parseDouble(pesoField.getText());
            double volumen = Double.parseDouble(volumenField.getText());
            double valorDeclarado = Double.parseDouble(valorDeclaradoField.getText());
            
            // Validar datos usando el controlador (lógica de negocio)
            envioController.validarDatosEnvio(
                descripcionField.getText(),
                peso,
                volumen,
                valorDeclarado,
                origen,
                destino,
                destinatarioNombreField.getText(),
                destinatarioTelefonoField.getText(),
                destinatarioEmailField.getText()
            );
            
            // Verificar que se haya calculado el costo
            if (costoCalculado == 0.0) {
                mostrarAlerta(Alert.AlertType.WARNING, "Calcular Costo", 
                    "Por favor calcule el costo del envío antes de continuar.");
                return;
            }
            
            // Crear el envío en el sistema
            Envio envioCreado = envioController.crearEnvio(
                    usuarioActual.getIdUsuario(), 
                    origen, 
                    destino, 
                    tipo, 
                    peso, 
                    volumen, 
                    descripcionField.getText()
            );
            
            if (envioCreado == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el envío.");
                return;
            }
            
            // Actualizar datos adicionales
            envioCreado.setValorDeclarado(valorDeclarado);
            envioCreado.setCosto(costoCalculado);
            envioCreado.setDistancia(distanciaCalculada);
            envioCreado.setNombreDestinatario(destinatarioNombreField.getText());
            envioCreado.setTelefonoDestinatario(destinatarioTelefonoField.getText());
            envioCreado.setEmailDestinatario(destinatarioEmailField.getText());
            
            // Mostrar diálogo de pago
            if (mostrarDialogoPago(envioCreado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Envío Creado", 
                    "El envío se ha creado exitosamente.\n\n" +
                    "ID: " + envioCreado.getIdEnvio() + "\n" +
                    "Costo: $" + String.format("%.0f", costoCalculado) + "\n" +
                    "Estado: " + envioCreado.getEstado() + "\n\n" +
                    "Recibirá notificaciones sobre el estado de su envío.");
                
                // Volver al dashboard
                handleVolver(event);
            }
            
        } catch (NumberFormatException e) {
            // Error al parsear números
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Formato", 
                "Por favor ingrese valores numéricos válidos.");
        } catch (IllegalArgumentException e) {
            // Errores de validación del controlador
            mostrarAlerta(Alert.AlertType.WARNING, "Datos Inválidos", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al crear el envío: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean mostrarDialogoPago(Envio envio) {
        // Crear diálogo personalizado de pago
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Procesar Pago");
        dialog.setHeaderText("Seleccione el método de pago para el envío");
        
        // Crear contenido del diálogo
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));
        
        Label infoLabel = new Label("Monto a pagar: $" + String.format("%.0f", envio.getCosto()));
        infoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        ComboBox<String> metodoPagoCombo = new ComboBox<>();
        metodoPagoCombo.getItems().addAll("TARJETA", "NEQUI", "PAYPAL", "EFECTIVO");
        metodoPagoCombo.setValue("TARJETA");
        metodoPagoCombo.setPrefWidth(300);
        
        TextField datosPagoField = new TextField();
        datosPagoField.setPromptText("Ingrese datos del método de pago");
        datosPagoField.setPrefWidth(300);
        
        TextField nombreField = new TextField();
        nombreField.setText(usuarioActual.getNombreCompleto());
        nombreField.setPrefWidth(300);
        
        grid.add(infoLabel, 0, 0, 2, 1);
        grid.add(new Label("Método de pago:"), 0, 1);
        grid.add(metodoPagoCombo, 1, 1);
        grid.add(new Label("Datos de pago:"), 0, 2);
        grid.add(datosPagoField, 1, 2);
        grid.add(new Label("Nombre:"), 0, 3);
        grid.add(nombreField, 1, 3);
        
        // Actualizar prompt text según el método seleccionado
        metodoPagoCombo.setOnAction(e -> {
            String metodo = metodoPagoCombo.getValue();
            switch (metodo) {
                case "TARJETA":
                    datosPagoField.setPromptText("Ej: 4532-1234-5678-9010");
                    break;
                case "NEQUI":
                    datosPagoField.setPromptText("Ej: 3001234567");
                    break;
                case "PAYPAL":
                    datosPagoField.setPromptText("Ej: email@paypal.com");
                    break;
                case "EFECTIVO":
                    datosPagoField.setPromptText("No requiere datos adicionales");
                    datosPagoField.setDisable(true);
                    break;
            }
        });
        
        dialog.getDialogPane().setContent(grid);
        
        ButtonType pagarButtonType = new ButtonType("Pagar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(pagarButtonType, ButtonType.CANCEL);
        
        Optional<ButtonType> result = dialog.showAndWait();
        
        if (result.isPresent() && result.get() == pagarButtonType) {
            try {
                String metodo = metodoPagoCombo.getValue();
                String datosPago = datosPagoField.getText();
                String nombre = nombreField.getText();
                
                if (datosPago.isEmpty() && !metodo.equals("EFECTIVO")) {
                    mostrarAlerta(Alert.AlertType.WARNING, "Datos Incompletos", 
                        "Por favor ingrese los datos del método de pago.");
                    return mostrarDialogoPago(envio); // Volver a mostrar el diálogo
                }
                
                // Procesar el pago según el método seleccionado
                Pago pago = null;
                switch (metodo) {
                    case "TARJETA":
                        pago = pagoController.procesarPagoTarjeta(envio, datosPago, nombre);
                        break;
                    case "NEQUI":
                        pago = pagoController.procesarPagoNequi(envio, datosPago);
                        break;
                    case "PAYPAL":
                        pago = pagoController.procesarPagoPayPal(envio, datosPago, nombre);
                        break;
                    case "EFECTIVO":
                        pago = pagoController.procesarPagoEfectivo(envio);
                        break;
                }
                
                if (pago != null && pago.getEstado() == Pago.EstadoPago.APROBADO) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Pago Exitoso", 
                        "El pago se ha procesado correctamente.\n\n" +
                        "ID Pago: " + pago.getIdPago() + "\n" +
                        "Método: " + pago.getMetodoPago().getTipo());
                    return true;
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Pago Fallido", 
                        "No se pudo procesar el pago. Intente nuevamente.");
                    return false;
                }
                
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error en Pago", 
                    "Error al procesar el pago: " + e.getMessage());
                return false;
            }
        }
        
        return false; // Usuario canceló
    }
    
    @FXML
    void handleVolver(ActionEvent event) {
        NavigationController.navigateToUserDashboard();
    }
    
    @FXML
    void handleCancelar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancelar Envío");
        alert.setHeaderText("¿Está seguro que desea cancelar?");
        alert.setContentText("Se perderán todos los datos ingresados.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            limpiarFormulario();
            handleVolver(event);
        }
    }
    
    @FXML
    void handleAbrirMapaOrigen(ActionEvent event) {
        abrirMapaSelector("origen");
    }
    
    @FXML
    void handleAbrirMapaDestino(ActionEvent event) {
        abrirMapaSelector("destino");
    }
    
    private void abrirMapaSelector(String tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/co/edu/uniquindio/demop2pf/mapa-selector.fxml"));
            javafx.scene.Parent root = loader.load();
            
            MapaSelectorViewController controller = loader.getController();
            
            Stage stage = new Stage();
            stage.setTitle("Selector de Coordenadas - " + tipo.substring(0, 1).toUpperCase() + tipo.substring(1));
            stage.setScene(new Scene(root, 1000, 700));
            stage.setResizable(true);
            
            controller.setStage(stage);
            
            // Callback cuando se confirman las coordenadas
            controller.setOnCoordinatesSelected(coords -> {
                System.out.println("[NUEVO ENVIO] Callback recibido con coordenadas: " + coords[0] + ", " + coords[1]);
                if (tipo.equals("origen")) {
                    String latStr = String.format(java.util.Locale.US, "%.6f", coords[0]);
                    String lngStr = String.format(java.util.Locale.US, "%.6f", coords[1]);
                    origenLatitudField.setText(latStr);
                    origenLongitudField.setText(lngStr);
                    System.out.println("✓ Coordenadas de origen establecidas: " + latStr + ", " + lngStr);
                } else if (tipo.equals("destino")) {
                    String latStr = String.format(java.util.Locale.US, "%.6f", coords[0]);
                    String lngStr = String.format(java.util.Locale.US, "%.6f", coords[1]);
                    destinoLatitudField.setText(latStr);
                    destinoLongitudField.setText(lngStr);
                    System.out.println("✓ Coordenadas de destino establecidas: " + latStr + ", " + lngStr);
                }
            });
            
            stage.show();
            
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", 
                    "No se pudo abrir la ventana del mapa: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Direccion construirDireccionOrigen() {
        String direccionCompleta = origenCalleField.getText() + ", " + 
                                  origenCiudadField.getText() + " - " + 
                                  origenCodigoPostalField.getText();
        return new Direccion.Builder(UUID.randomUUID().toString())
                .conCalle(direccionCompleta)
                .conCiudad(origenCiudadField.getText())
                .conCoordenadas(Double.parseDouble(origenLatitudField.getText()),
                               Double.parseDouble(origenLongitudField.getText()))
                .build();
    }
    
    private Direccion construirDireccionDestino() {
        String direccionCompleta = destinoCalleField.getText() + ", " + 
                                  destinoCiudadField.getText() + " - " + 
                                  destinoCodigoPostalField.getText();
        return new Direccion.Builder(UUID.randomUUID().toString())
                .conCalle(direccionCompleta)
                .conCiudad(destinoCiudadField.getText())
                .conCoordenadas(Double.parseDouble(destinoLatitudField.getText()),
                               Double.parseDouble(destinoLongitudField.getText()))
                .build();
    }
    
    private boolean validarCamposBasicos() {
        return !pesoField.getText().isEmpty() &&
               !volumenField.getText().isEmpty() &&
               !valorDeclaradoField.getText().isEmpty() &&
               !origenLatitudField.getText().isEmpty() &&
               !origenLongitudField.getText().isEmpty() &&
               !destinoLatitudField.getText().isEmpty() &&
               !destinoLongitudField.getText().isEmpty();
    }
    
    private boolean validarTodosLosCampos() {
        StringBuilder errores = new StringBuilder();
        
        if (descripcionField.getText().isEmpty()) {
            errores.append("- Descripción del paquete\n");
        }
        if (pesoField.getText().isEmpty()) {
            errores.append("- Peso del paquete\n");
        }
        if (volumenField.getText().isEmpty()) {
            errores.append("- Volumen del paquete\n");
        }
        if (valorDeclaradoField.getText().isEmpty()) {
            errores.append("- Valor declarado\n");
        }
        if (origenCalleField.getText().isEmpty() || origenCiudadField.getText().isEmpty()) {
            errores.append("- Dirección de origen completa\n");
        }
        if (destinoCalleField.getText().isEmpty() || destinoCiudadField.getText().isEmpty()) {
            errores.append("- Dirección de destino completa\n");
        }
        if (destinatarioNombreField.getText().isEmpty()) {
            errores.append("- Nombre del destinatario\n");
        }
        if (destinatarioTelefonoField.getText().isEmpty()) {
            errores.append("- Teléfono del destinatario\n");
        }
        
        if (errores.length() > 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", 
                "Por favor complete los siguientes campos:\n\n" + errores.toString());
            return false;
        }
        
        return true;
    }
    
    private void limpiarFormulario() {
        descripcionField.clear();
        pesoField.clear();
        volumenField.clear();
        valorDeclaradoField.clear();
        origenCalleField.clear();
        origenCiudadField.clear();
        origenCodigoPostalField.clear();
        origenLatitudField.clear();
        origenLongitudField.clear();
        destinoCalleField.clear();
        destinoCiudadField.clear();
        destinoCodigoPostalField.clear();
        destinoLatitudField.clear();
        destinoLongitudField.clear();
        destinatarioNombreField.clear();
        destinatarioTelefonoField.clear();
        destinatarioEmailField.clear();
        seguroCheckBox.setSelected(false);
        expresCheckBox.setSelected(false);
        distanciaLabel.setText("-- km");
        costoBaseLabel.setText("$0.00");
        costosAdicionalesLabel.setText("$0.00");
        costoTotalLabel.setText("$0.00");
        costoCalculado = 0.0;
        distanciaCalculada = 0.0;
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
