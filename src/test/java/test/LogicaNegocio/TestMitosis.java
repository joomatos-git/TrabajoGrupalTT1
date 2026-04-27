package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.*;
import main.ModeloDominio.BichitoMitosis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestMitosis {

    GridLogic grid1;

    @BeforeEach
    void setUp() {
        grid1 = new GridLogic();
        for (int n = 1; n < 50; n++) {
            grid1.step();
        }
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
    }

    @Test
    void testComprobarMultiplicarse() {
        List<BichitoMitosis> listaEspecifica = new ArrayList<BichitoMitosis>();
        List<List<BichitoInterface>> listaGeneral = grid1.getBichitosTiempo();

        for (BichitoInterface b : listaGeneral.get(0)) {
            if (b instanceof BichitoMitosis bm) {
                listaEspecifica.add(bm);
            }
        }
        int countPre = listaEspecifica.size();
        for (BichitoInterface b : listaGeneral.get(40)) {
            if (b instanceof BichitoMitosis bm) {
                listaEspecifica.add(bm);
            }
        }
        int countPost = listaEspecifica.size();
        assertNotEquals(countPre, countPost);
    }


    @Test
    void testComprobarDistintos() {
        // Ejecutamos otra simulación paralela con semilla diferente
        GridLogic grid2 = new GridLogic(1234);
        for (int n = 1; n < 50; n++) {
            grid2.step();
        }

        List<BichitoInterface> ultimaGen1 = grid1.getBichitosTiempo().get(49);
        List<BichitoInterface> ultimaGen2 = grid2.getBichitosTiempo().get(49);

        // Extraemos posiciones para comparar de forma más estricta
        List<Posicion> pos1 = ultimaGen1.stream().filter(b -> b instanceof BichitoMitosis).map(BichitoInterface::getPosicion).toList();
        List<Posicion> pos2 = ultimaGen2.stream().filter(b -> b instanceof BichitoMitosis).map(BichitoInterface::getPosicion).toList();

        // Al ser aleatorio, el patrón de expansión final debería diferir
        assertNotEquals(pos1, pos2,"El patrón de expansión de Mitosis debería ser diferente con semillas distintas");
    }
}
