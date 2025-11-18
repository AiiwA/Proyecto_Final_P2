package co.edu.uniquindio.poo.factory;

import co.edu.uniquindio.poo.model.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Factory del patrón Factory Method para crear instancias de Envío.
 * Encapsula la lógica de creación de diferentes tipos de envíos
 * (estándar, express, frágil) con sus configuraciones específicas.
 * Centraliza la creación para facilitar cambios en la estructura de Envío.
 */
public class EnvioFactory {
    
    /**
     * Crea un envío de tipo estándar.
     * Configuración: Entrega en 3 días, tarifa base (1.0x multiplicador).
     * 
     * @param usuario Usuario propietario del envío
     * @param origen Dirección de partida
     * @param destino Dirección de llegada
     * @param peso Peso del paquete en kilogramos
     * @param largo Largo del paquete en centímetros
     * @param ancho Ancho del paquete en centímetros
     * @param alto Alto del paquete en centímetros
     * @return Nuevo envío en estado SOLICITADO
     */
    public static Envio crearEnvioEstandar(Usuario usuario, Direccion origen, Direccion destino,
                                           double peso, double largo, double ancho, double alto) {
        return Envio.builder()
                .idEnvio(UUID.randomUUID().toString())
                .origen(origen)
                .destino(destino)
                .peso(peso)
                .largo(largo)
                .ancho(ancho)
                .alto(alto)
                .usuario(usuario)
                .tipoEnvio(Envio.TipoEnvio.ESTANDAR)
                .estado(Envio.EstadoEnvio.SOLICITADO)
                .fechaEntregaEstimada(LocalDateTime.now().plusDays(3))
                .build();
    }
    
    /**
     * Crea un envío de tipo express.
     * Configuración: Entrega garantizada en 24 horas, tarifa 1.5x (50% más cara).
     * Ideal para envíos urgentes.
     * 
     * @param usuario Usuario propietario del envío
     * @param origen Dirección de partida
     * @param destino Dirección de llegada
     * @param peso Peso del paquete en kilogramos
     * @param largo Largo del paquete en centímetros
     * @param ancho Ancho del paquete en centímetros
     * @param alto Alto del paquete en centímetros
     * @return Nuevo envío express en estado SOLICITADO
     */
    public static Envio crearEnvioExpress(Usuario usuario, Direccion origen, Direccion destino,
                                          double peso, double largo, double ancho, double alto) {
        return Envio.builder()
                .idEnvio(UUID.randomUUID().toString())
                .origen(origen)
                .destino(destino)
                .peso(peso)
                .largo(largo)
                .ancho(ancho)
                .alto(alto)
                .usuario(usuario)
                .tipoEnvio(Envio.TipoEnvio.EXPRESS)
                .estado(Envio.EstadoEnvio.SOLICITADO)
                .fechaEntregaEstimada(LocalDateTime.now().plusHours(24))
                .build();
    }
    
    /**
     * Crea un envío de tipo frágil.
     * Configuración: Entrega en 2 días, tarifa 1.3x (30% más cara por manejo especial).
     * Incluye embalaje reforzado y manejo cuidadoso.
     * 
     * @param usuario Usuario propietario del envío
     * @param origen Dirección de partida
     * @param destino Dirección de llegada
     * @param peso Peso del paquete en kilogramos
     * @param largo Largo del paquete en centímetros
     * @param ancho Ancho del paquete en centímetros
     * @param alto Alto del paquete en centímetros
     * @return Nuevo envío frágil en estado SOLICITADO con costo aumentado
     */
    public static Envio crearEnvioFragil(Usuario usuario, Direccion origen, Direccion destino,
                                         double peso, double largo, double ancho, double alto) {
        Envio envio = Envio.builder()
                .idEnvio(UUID.randomUUID().toString())
                .origen(origen)
                .destino(destino)
                .peso(peso)
                .largo(largo)
                .ancho(ancho)
                .alto(alto)
                .usuario(usuario)
                .tipoEnvio(Envio.TipoEnvio.FRAGIL)
                .estado(Envio.EstadoEnvio.SOLICITADO)
                .fechaEntregaEstimada(LocalDateTime.now().plusDays(2))
                .build();
        
        // Agregar recargo por frágil (20%)
        double costoBase = envio.getCosto();
        envio.setCosto(costoBase * 1.2);
        
        return envio;
    }
}
