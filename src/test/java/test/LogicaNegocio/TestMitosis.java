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
        grid1 = new GridLogic(new int[]{0, 0, 3});
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
        List<BichitoMitosis> listaEspecifica = new ArrayList<>();
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
        assertNotEquals(countPre, countPost,
                "El número de BichitoMitosis debe haber cambiado tras 40 turnos");
    }

    @Test
    void testHijoSoloPuedeEstarEnAdyacente() {
        GridLogic grid = new GridLogic(new int[]{0, 0, 0});
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
        GridLogic grid = new GridLogic(new int[]{0, 0, 0});
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
                "Debe haber turnos sin reproducción (probabilidad ~75%)");
    }

    @Test
    void testComprobarDistintos() {
        GridLogic grid2 = new GridLogic(new int[]{0, 0, 1});
        for (int n = 1; n < 50; n++) {
            grid2.step();
        }

        long count1 = grid1.getBichitosTiempo().get(10).stream()
                .filter(b -> b instanceof BichitoMitosis).count();
        long count2 = grid2.getBichitosTiempo().get(10).stream()
                .filter(b -> b instanceof BichitoMitosis).count();

        assertNotEquals(count1, count2,
                "Con diferente número inicial de mitosis, la expansión en el turno 10 debe ser distinta");
    }
}