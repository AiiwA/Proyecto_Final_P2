package co.edu.uniquindio.poo.command;

import java.util.Stack;

/**
 * Gestor de Comandos - Implementación del patrón Command.
 * Mantiene el historial de comandos ejecutados y permite deshacer/rehacer operaciones.
 * Utiliza dos pilas: una para el historial de ejecuciones y otra para rehacer.
 */
public class GestorComandos {
    private Stack<Command> historial = new Stack<>();
    private Stack<Command> rehacer = new Stack<>();
    
    /**
     * Ejecuta un comando y lo registra en el historial.
     * Limpia la pila de rehacer al ejecutar un nuevo comando.
     * 
     * @param comando Comando a ejecutar
     */
    public void ejecutarComando(Command comando) {
        comando.ejecutar();
        historial.push(comando);
        rehacer.clear(); // Limpia el historial de rehacer
    }
    
    /**
     * Deshace el último comando ejecutado.
     * Restaura el estado anterior y lo mueve a la pila de rehacer.
     */
    public void deshacerUltimoComando() {
        if (!historial.isEmpty()) {
            Command comando = historial.pop();
            comando.deshacer();
            rehacer.push(comando);
        } else {
            System.out.println("No hay comandos para deshacer");
        }
    }
    
    /**
     * Rehace el último comando que fue deshecho.
     * Ejecuta nuevamente el comando y lo restaura en el historial.
     */
    public void rehacerUltimoComando() {
        if (!rehacer.isEmpty()) {
            Command comando = rehacer.pop();
            comando.ejecutar();
            historial.push(comando);
        } else {
            System.out.println("No hay comandos para rehacer");
        }
    }
    
    /**
     * Obtiene un resumen formateado de todos los comandos ejecutados.
     * Útil para mostrar el historial al usuario.
     * 
     * @return String con listado de comandos
     */
    public String obtenerHistorial() {
        StringBuilder sb = new StringBuilder("Historial de comandos:\n");
        for (Command cmd : historial) {
            sb.append("- ").append(cmd.getDescripcion()).append("\n");
        }
        return sb.toString();
    }
}
