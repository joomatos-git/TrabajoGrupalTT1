package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.*;
import main.ModeloDominio.BichitoMitosis;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
    void testHijoSoloPuedeEstarEnAdyacente() {
        GridLogic grid = new GridLogic(42, 5, 5);
        grid.getBichitosTiempo().clear();

        List<BichitoInterface> instante0 = new ArrayList<>();
        Posicion posMadre = new Posicion(2, 2);
        instante0.add(new BichitoMitosis(posMadre));

        grid.getBichitosTiempo().add(instante0);

        grid.step();

        List<BichitoInterface> instante1 = grid.getBichitosTiempo().get(1);

        List<BichitoMitosis> mitosis = instante1.stream()
                .filter(b -> b instanceof BichitoMitosis)
                .map(b -> (BichitoMitosis) b)
                .toList();

        boolean madrePresente = mitosis.stream()
                .anyMatch(b -> b.getPosicion().equals(posMadre));

        Assertions.assertTrue(madrePresente,
                "La célula madre debe permanecer en su posición");

        mitosis.stream()
                .filter(b -> !b.getPosicion().equals(posMadre))
                .forEach(hijo -> {
                    Posicion posHijo = hijo.getPosicion();

                    int distancia = Math.abs(posHijo.x - posMadre.x)
                            + Math.abs(posHijo.y - posMadre.y);

                    Assertions.assertEquals(1, distancia,
                            "El hijo debe aparecer en una celda adyacente");
                });
    }

    @Test
    void testMitosisNoSeReproduceTodosLosTurnos() {
        GridLogic grid = new GridLogic(42, 6, 6);
        grid.getBichitosTiempo().clear();

        List<BichitoInterface> instante0 = new ArrayList<>();
        instante0.add(new BichitoMitosis(new Posicion(3, 3)));

        grid.getBichitosTiempo().add(instante0);

        int turnosSinReproduccion = 0;


        for (int t = 0; t < 30; t++) {

            int antes = (int) grid.getBichitosTiempo()
                    .get(grid.getBichitosTiempo().size() - 1)
                    .stream()
                    .filter(b -> b instanceof BichitoMitosis)
                    .count();

            grid.step();

            int despues = (int) grid.getBichitosTiempo()
                    .get(grid.getBichitosTiempo().size() - 1)
                    .stream()
                    .filter(b -> b instanceof BichitoMitosis)
                    .count();

            if (despues == antes) {
                turnosSinReproduccion++;
            }
        }

        Assertions.assertTrue(turnosSinReproduccion > 0,
                "Debe haber turnos sin reproducción (probabilidad ~25%)");
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
