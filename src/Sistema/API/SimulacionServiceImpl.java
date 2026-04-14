package Sistema.API;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service  // Le dice a Spring que esta clase es un servicio y la gestiona él
public class SimulacionServiceImpl implements ISimulacionService {

    // Aquí guardamos las simulaciones activas
    // La clave es el token, el valor es la configuración de esa simulación
    private Map<String, ConfiguracionDTO> simulaciones = new HashMap<>();

    @Override
    public String iniciarSimulacion(ConfiguracionDTO configuracion) {
        // Generamos un token único para esta simulación
        String token = UUID.randomUUID().toString();

        // Guardamos la configuración asociada a ese token
        simulaciones.put(token, configuracion);

        return token;
    }

    @Override
    public EstadoTableroDTO getEstado(String token, int instante) {
        // Comprobamos que el token existe
        if (!simulaciones.containsKey(token)) {
            return null; // El controller gestionará este caso devolviendo un error
        }

        ConfiguracionDTO config = simulaciones.get(token);

        // De momento devolvemos un tablero de prueba vacío con el tamaño correcto
        // Cuando GridLogic esté lista, aquí llamarás a la lógica real
        String[][] tablero = new String[config.getFilas()][config.getColumnas()];

        // Ponemos algunos bichitos de prueba para ver que funciona
        if (config.getFilas() > 0 && config.getColumnas() > 0) {
            tablero[0][0] = "QUIETO";
        }
        if (config.getFilas() > 1 && config.getColumnas() > 1) {
            tablero[1][1] = "MOVIL";
        }

        return new EstadoTableroDTO(instante, tablero, false);
    }
}
