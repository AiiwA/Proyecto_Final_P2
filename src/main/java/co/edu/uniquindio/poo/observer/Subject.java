package co.edu.uniquindio.poo.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta Subject del patrón Observer.
 * Actúa como sujeto observable que mantiene una lista de observadores.
 * Cuando ocurren cambios en el sujeto, notifica automáticamente a todos
 * los observadores registrados.
 */
public abstract class Subject {
    private List<Observer> observadores = new ArrayList<>();
    
    /**
     * Agrega un nuevo observador a la lista de sujetos a notificar.
     * El observador recibirá notificaciones cuando ocurran cambios.
     * 
     * @param observador Instancia que implementa la interfaz Observer
     */
    public void agregarObservador(Observer observador) {
        observadores.add(observador);
    }
    
    /**
     * Elimina un observador de la lista de notificación.
     * El observador dejará de recibir notificaciones futuras.
     * 
     * @param observador Instancia a remover de la lista
     */
    public void removerObservador(Observer observador) {
        observadores.remove(observador);
    }
    
    /**
     * Notifica a todos los observadores registrados sobre un evento.
     * Invoca el método actualizar() en cada observador con los datos del evento.
     * 
     * @param mensaje Descripción del evento que ocurrió
     * @param datos Objeto con información adicional sobre el evento
     */
    public void notificarObservadores(String mensaje, Object datos) {
        for (Observer observador : observadores) {
            observador.actualizar(mensaje, datos);
        }
    }
}
