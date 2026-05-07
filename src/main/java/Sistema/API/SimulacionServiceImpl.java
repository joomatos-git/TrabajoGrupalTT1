package Sistema.API;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoMitosis;
import main.ModeloDominio.BichitoMovil;
import main.ModeloDominio.BichitoQuieto;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementación del servicio de simulación. Encargada de mantener el estado
 * de las sesiones activas en memoria y procesar el historial de datos.
 */
@Service
public class SimulacionServiceImpl implements ISimulacionService {

    // Almacén en memoria de las simulaciones activas, indexadas por su token.
    private Map<Integer, GridLogic> simulaciones = new HashMap<>();

    /**
     * Constructor por defecto que inicializa el servicio de simulación.
     */
    public SimulacionServiceImpl() {}
    
    /**
     * Crea e inicia una nueva instancia de simulación, calculando hasta 50 instantes.
     * @param configuracion Configuración inicial enviada por el cliente.
     * @return Token entero positivo generado para la sesión.
     */
    @Override
    public int iniciarSimulacion(ConfiguracionDTO configuracion) {
        int token = Math.abs(UUID.randomUUID().hashCode());
        Random r = new Random();
        GridLogic grid = new GridLogic(configuracion.getCantidadesIniciales());
        for (int i = 0; i < 50; i++) {
            grid.step();
            if (grid.isStuck()) {
                break;
            }
        }

        simulaciones.put(token, grid);
        return token;
    }

    /**
     * Transforma el historial de BichitoInterface a un string multilínea formateado.
     * El formato devuelto es: instante, y, x, color_hexadecimal.
     * @param token Identificador de la simulación.
     * @return Objeto EstadoTableroDTO con los datos, o null si el token no existe.
     */
    @Override
    public EstadoTableroDTO getEstado(int token) {
        if (!simulaciones.containsKey(token)) {
            return null;
        }

        GridLogic grid = simulaciones.get(token);
        List<List<BichitoInterface>> historia = grid.getBichitosTiempo();

        String tablero="";

        tablero = tablero+(String.valueOf(grid.getGrid().getMatrix().length))+'\n';

        for(List<BichitoInterface> instante: historia){
            for(BichitoInterface bicho : instante){
                String bichoActual = (historia.indexOf(instante)) + "," + bicho.getPosicion().y + "," + bicho.getPosicion().x + ",";
                if(bicho instanceof BichitoMitosis){
                    bichoActual=bichoActual+"#00FF00";
                }else if(bicho instanceof BichitoMovil){
                    bichoActual=bichoActual+"#EA63FF";
                }else if(bicho instanceof BichitoQuieto){
                    bichoActual=bichoActual+"#FF0000";
                }
                tablero = tablero + (bichoActual)+'\n';
            }
        }

        return new EstadoTableroDTO(tablero);
    }
}