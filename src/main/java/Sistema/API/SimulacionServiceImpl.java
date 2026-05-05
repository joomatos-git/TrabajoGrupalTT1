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

    private Map<Integer, GridLogic> simulaciones = new HashMap<>();

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
                    bichoActual=bichoActual+"#00c853";
                }else if(bicho instanceof BichitoMovil){
                    bichoActual=bichoActual+"#d500f9";
                }else if(bicho instanceof BichitoQuieto){
                    bichoActual=bichoActual+"#f50057";
                }
                tablero = tablero + (bichoActual)+'\n';
            }
        }

        return new EstadoTableroDTO(tablero);
    }
}