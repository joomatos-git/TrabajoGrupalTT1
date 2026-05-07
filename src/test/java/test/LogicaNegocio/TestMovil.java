package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoMovil;
import main.ModeloDominio.Posicion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests de validación de comportamiento específico.
 * Comprueba que las reglas de movimiento, restricciones de adyacencia
 * y/o clonación de esta criatura se cumplen a lo largo del tiempo.
 */
public class TestMovil {

    GridLogic grid1;

    @BeforeEach
    void setUp() {
        grid1 = new GridLogic(new int[]{0, 5, 0});
        for (int n = 1; n < 50; n++) {
            grid1.step();
        }
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
    }

    @Test
    void testComprobarMoviles() {
        int turnosConMovimiento = 0;

        List<BichitoMovil> listaEspecifica = new ArrayList<>();
        List<List<BichitoInterface>> listaGeneral = grid1.getBichitosTiempo();
        List<Posicion> posicionesPre = new ArrayList<>();
        for (BichitoInterface b : listaGeneral.get(0)) {
            if (b instanceof BichitoMovil bm) {
                listaEspecifica.add(bm);
                posicionesPre.add(bm.getPosicion());
            }
        }
        for (int n = 1; n < 50; n++) {
            List<Posicion> posicionesPost = new ArrayList<>();
            for (BichitoInterface b : listaGeneral.get(n)) {
                if (b instanceof BichitoMovil bm) {
                    listaEspecifica.add(bm);
                    posicionesPost.add(bm.getPosicion());
                }
            }

            int countSamePos = 0;
            int size = Math.min(posicionesPre.size(), posicionesPost.size());
            for (int i = 0; i < size; i++) {
                if (posicionesPre.get(i).equals(posicionesPost.get(i))) {
                    countSamePos++;
                }
            }

            if (size > 0 && countSamePos < size) {
                turnosConMovimiento++;
            }

            posicionesPre = posicionesPost;
        }

        Assertions.assertTrue(turnosConMovimiento > 0,
                "Ningún BichitoMovil se movió en ningún turno");
    }

    @Test
    void testMovilSoloVaAAdyacentes() {
        GridLogic grid = new GridLogic(new int[]{0, 0, 0});
        grid.getBichitosTiempo().clear();

        List<BichitoInterface> instante0 = new ArrayList<>();
        Posicion posInicial = new Posicion(2, 2);
        instante0.add(new BichitoMovil(posInicial));
        grid.getBichitosTiempo().add(instante0);

        grid.step();

        List<BichitoInterface> instante1 = grid.getBichitosTiempo().get(1);

        List<BichitoMovil> moviles = instante1.stream()
                .filter(b -> b instanceof BichitoMovil)
                .map(b -> (BichitoMovil) b)
                .toList();

        Assertions.assertEquals(1, moviles.size(),
                "Debe seguir habiendo exactamente 1 móvil");

        Posicion posNueva = moviles.get(0).getPosicion();
        int distancia = Math.abs(posNueva.x - posInicial.x)
                + Math.abs(posNueva.y - posInicial.y);

        Assertions.assertTrue(distancia <= 1,
                "El móvil no puede moverse más de una celda de distancia Manhattan");
    }

    @Test
    void testMovilNoCruzaDiagonal() {
        GridLogic grid = new GridLogic(new int[]{0, 4, 0});
        List<List<BichitoInterface>> historia = grid.getBichitosTiempo();
        for (int t = 0; t < 20; t++) {
            grid.step();
        }

        for (int t = 0; t < historia.size() - 1; t++) {
            List<BichitoMovil> antes = historia.get(t).stream()
                    .filter(b -> b instanceof BichitoMovil)
                    .map(b -> (BichitoMovil) b)
                    .toList();

            List<BichitoMovil> despues = historia.get(t + 1).stream()
                    .filter(b -> b instanceof BichitoMovil)
                    .map(b -> (BichitoMovil) b)
                    .toList();

            for (BichitoMovil bDespues : despues) {
                Posicion p1 = bDespues.getPosicion();

                boolean movimientoValido = antes.stream().anyMatch(bAntes -> {
                    Posicion p0 = bAntes.getPosicion();
                    int dist = Math.abs(p1.x - p0.x) + Math.abs(p1.y - p0.y);
                    return dist <= 1;
                });

                Assertions.assertTrue(movimientoValido,
                        "Movimiento inválido en turno " + t +
                                " hacia posición (" + p1.x + "," + p1.y + ")");
            }
        }
    }

    @Test
    void testComprobarDistintos() {
        GridLogic grid2 = new GridLogic(new int[]{0, 8, 0});
        for (int n = 1; n < 50; n++) {
            grid2.step();
        }

        List<BichitoInterface> ultimaGen1 = grid1.getBichitosTiempo().get(49);
        List<BichitoInterface> ultimaGen2 = grid2.getBichitosTiempo().get(49);

        List<Posicion> posMoviles1 = new ArrayList<>();
        for (BichitoInterface b : ultimaGen1) {
            if (b instanceof BichitoMovil) posMoviles1.add(b.getPosicion());
        }

        List<Posicion> posMoviles2 = new ArrayList<>();
        for (BichitoInterface b : ultimaGen2) {
            if (b instanceof BichitoMovil) posMoviles2.add(b.getPosicion());
        }

        assertNotEquals(posMoviles1, posMoviles2,
                "Dos simulaciones con diferente número de móviles no deberían tener las mismas posiciones finales");
    }
}