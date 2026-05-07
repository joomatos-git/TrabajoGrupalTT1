package Sistema.API;

public interface ISimulacionService {
    
    /**
    * Recibe la configuración (cantidades iniciales de cada tipo de criatura)
    * e inicializa una nueva simulación.
     * @param configuracion Objeto DTO con los parámetros iniciales enviados por el cliente.
     * @return Un número entero que actúa como identificador único (token) de la sesión creada.
    */
    int iniciarSimulacion(ConfiguracionDTO configuracion);

    /**
    * Recibe el token de sesión y devuelve el historial completo del tablero
    * con todos los instantes simulados.
     * @param token El identificador numérico de la simulación iniciada previamente.
     * @return Objeto EstadoTableroDTO con los datos de la simulación formateados en un string multilínea, o null si el token no es válido.
    */
    EstadoTableroDTO getEstado(int token);
}
