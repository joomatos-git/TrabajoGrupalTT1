package Sistema.API;

public interface ISimulacionService {

    // Recibe la configuración (filas, columnas, número de bichitos de cada tipo)
    // y devuelve un token
    String iniciarSimulacion(ConfiguracionDTO configuracion);

    // Recibe el token de sesión
    // y devuelve el estado del tablero en ese momento
    EstadoTableroDTO getEstado(String token);
}
