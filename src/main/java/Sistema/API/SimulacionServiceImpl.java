package Sistema.API;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoMitosis;
import main.ModeloDominio.BichitoMovil;
import main.ModeloDominio.BichitoQuieto;
import main.ModeloDominio.Grid;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SimulacionServiceImpl implements ISimulacionService {

    // Ahora guardamos el GridLogic completo, no solo la config
    private Map<String, GridLogic> simulaciones = new HashMap<>();

    @Override
    public String iniciarSimulacion(ConfiguracionDTO configuracion) {
        String token = UUID.randomUUID().toString();
        Random r = new Random();
        int seed = r.nextInt();
        System.out.println(seed);
        GridLogic grid = new GridLogic(seed);


        // Avanzamos 50 turnos (o los que quieras)
        for (int i = 0; i < 50; i++) {
            grid.step();
        }

        simulaciones.put(token, grid);
        return token;
    }

    @Override
    public EstadoTableroDTO getEstado(String token, int instante) {
        if (!simulaciones.containsKey(token)) {
            return null;
        }

        GridLogic grid = simulaciones.get(token);
        List<List<BichitoInterface>> historia = grid.getBichitosTiempo();

        // Comprobamos que el instante pedido existe
        if (instante < 0 || instante >= historia.size()) {
            return null;
        }

        int filas = grid.getGrid().getMatrix().length;
        int columnas = grid.getGrid().getMatrix()[0].length;
        String[][] tablero = new String[filas][columnas]; // null = vacío por defecto

        // Rellenamos el tablero con los bichitos del instante pedido
        for (BichitoInterface b : historia.get(instante)) {
            int x = b.getPosicion().x;
            int y = b.getPosicion().y;

            if (b instanceof BichitoQuieto) {
                tablero[x][y] = "QUIETO";
            } else if (b instanceof BichitoMovil) {
                tablero[x][y] = "MOVIL";
            } else if (b instanceof BichitoMitosis) {
                tablero[x][y] = "MITOSIS";
            }
        }

        boolean terminada = historia.size() >= 51;
        return new EstadoTableroDTO(instante, tablero, terminada);
    }
}