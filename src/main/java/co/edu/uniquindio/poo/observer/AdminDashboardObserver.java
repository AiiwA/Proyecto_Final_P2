package co.edu.uniquindio.poo.observer;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import co.edu.uniquindio.poo.model.Envio;
import lombok.AllArgsConstructor;

/**
 * Observer que actualiza automáticamente la tabla de envíos en el AdminDashboard
 * cuando hay cambios en el estado de un envío.
 * 
 * Utiliza Platform.runLater para asegurar que la actualización ocurra en el hilo de JavaFX.
 */
@AllArgsConstructor
public class AdminDashboardObserver implements Observer {
    private ObservableList<Envio> enviosTable;
    
    @Override
    public void actualizar(String mensaje, Object datos) {
        if (datos instanceof Envio) {
            Envio envioActualizado = (Envio) datos;
            
            // Usar Platform.runLater para asegurar que se ejecuta en el hilo de JavaFX
            Platform.runLater(() -> {
                synchronized (enviosTable) {
                    // Buscar el envío en la tabla
                    int indice = -1;
                    for (int i = 0; i < enviosTable.size(); i++) {
                        if (enviosTable.get(i).getIdEnvio().equals(envioActualizado.getIdEnvio())) {
                            indice = i;
                            break;
                        }
                    }
                    
                    if (indice >= 0) {
                        // Actualizar el envío en la posición encontrada
                        // Esto mantiene el índice pero actualiza todos los campos
                        enviosTable.set(indice, envioActualizado);
                        System.out.println("[OBSERVER] Tabla actualizada para envío: " + envioActualizado.getIdEnvio() 
                                         + " - Estado: " + envioActualizado.getEstado());
                    } else {
                        // Si no existe, agregarlo (situación excepcional)
                        enviosTable.add(envioActualizado);
                        System.out.println("[OBSERVER] Nuevo envío agregado a la tabla: " + envioActualizado.getIdEnvio());
                    }
                }
            });
        }
    }
}
