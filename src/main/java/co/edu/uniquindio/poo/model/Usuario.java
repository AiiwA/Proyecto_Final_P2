package co.edu.uniquindio.demop2pf.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;

/**
 * Modelo de Usuario en el sistema de logística.
 * Representa a un cliente que puede crear y rastrear envíos.
 * Mantiene direcciones frecuentes y métodos de pago para facilitar operaciones.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Usuario {
    private String idUsuario;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String password;
    @Builder.Default
    private List<Direccion> direccionesFrecuentes = new ArrayList<>();
    @Builder.Default
    private List<MetodoPago> metodosPago = new ArrayList<>();
    
    /**
     * Obtiene una copia defensiva de las direcciones frecuentes del usuario.
     * Retorna una copia para evitar modificaciones directas de la lista interna.
     * 
     * @return Lista de direcciones que el usuario utiliza con frecuencia
     */
    public List<Direccion> getDireccionesFrecuentes() {
        return new ArrayList<>(direccionesFrecuentes);
    }
    
    /**
     * Obtiene una copia defensiva de los métodos de pago del usuario.
     * Retorna una copia para mantener la integridad de la lista interna.
     * 
     * @return Lista de métodos de pago registrados por el usuario
     */
    public List<MetodoPago> getMetodosPago() {
        return new ArrayList<>(metodosPago);
    }
    
    /**
     * Agrega una nueva dirección a la lista de direcciones frecuentes.
     * Permite al usuario guardar direcciones para acceso rápido en futuros envíos.
     * 
     * @param direccion Dirección a agregar (ej: casa, oficina, proveedor)
     */
    public void agregarDireccionFrecuente(Direccion direccion) {
        direccionesFrecuentes.add(direccion);
    }
    
    /**
     * Agrega un nuevo método de pago a los métodos disponibles del usuario.
     * Permite al usuario registrar diferentes formas de pago para envíos.
     * 
     * @param metodoPago Método de pago a registrar (tarjeta, billetera digital, etc.)
     */
    public void agregarMetodoPago(MetodoPago metodoPago) {
        metodosPago.add(metodoPago);
    }
    
    /**
     * Elimina una dirección de las direcciones frecuentes del usuario.
     * Se utiliza cuando el usuario ya no desea usar esa dirección.
     * 
     * @param direccion Dirección a remover
     */
    public void removerDireccionFrecuente(Direccion direccion) {
        direccionesFrecuentes.remove(direccion);
    }
    
    /**
     * Elimina un método de pago de los métodos disponibles del usuario.
     * Se utiliza cuando el usuario cancela o reemplaza un método de pago.
     * 
     * @param metodoPago Método de pago a remover
     */
    public void removerMetodoPago(MetodoPago metodoPago) {
        metodosPago.remove(metodoPago);
    }
}