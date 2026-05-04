package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.Posicion;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGridLogicStep {

    private GridLogic gridLogic;

    @BeforeEach
    void setUp() {
        gridLogic = new GridLogic();
    }

    @AfterEach
    void tearDown() {
        gridLogic = null;
    }


    @Test
    void testStep_acumulaInstantesDetiempo() {
        gridLogic.initialize(1);
        assertEquals(1, gridLogic.getBichitosTiempo().size());

        gridLogic.step();
        assertEquals(2, gridLogic.getBichitosTiempo().size());

        gridLogic.step();
        assertEquals(3, gridLogic.getBichitosTiempo().size());
    }


    @Test
    void testStep_sinInitializeNoCrashea() {
        assertDoesNotThrow(() -> gridLogic.step());
    }


    @Test
    void testStep_sinColisionesEnNingunInstante() {
        gridLogic.initialize(7);
        for (int i = 0; i < 15; i++) {
            gridLogic.step();
        }

        for (int n = 0; n < gridLogic.getBichitosTiempo().size(); n++) {
            List<BichitoInterface> instante = gridLogic.getBichitosTiempo().get(n);
            for (int i = 0; i < instante.size(); i++) {
                for (int j = i + 1; j < instante.size(); j++) {
                    assertNotEquals(
                            instante.get(i).getPosicion(),
                            instante.get(j).getPosicion(),
                            "Colisión detectada en instante " + n
                    );
                }
            }
        }
    }


    @Test
    void testStep_todosBichitosEnRangoValido() {
        gridLogic.initialize(3);
        for (int i = 0; i < 20; i++) {
            gridLogic.step();
        }

        for (List<BichitoInterface> instante : gridLogic.getBichitosTiempo()) {
            for (BichitoInterface b : instante) {
                Posicion p = b.getPosicion();
                assertTrue(p.x >= 0 && p.x < 10,
                        "X fuera de tablero: " + p.x);
                assertTrue(p.y >= 0 && p.y < 10,
                        "Y fuera de tablero: " + p.y);
            }
        }
    }


    @Test
    void testStep_nuncaSuperaCapacidadTablero() {
        gridLogic.initialize(5);
        for (int i = 0; i < 30; i++) {
            gridLogic.step();
        }

        for (int n = 0; n < gridLogic.getBichitosTiempo().size(); n++) {
            int total = gridLogic.getBichitosTiempo().get(n).size();
            assertTrue(total <= 100,
                    "Instante " + n + " supera 100 bichitos: " + total);
        }
    }

    @Test
    void testTeardown_limpiaYPermiteReinicializar() {
        gridLogic.initialize(1);
        gridLogic.step();
        gridLogic.teardown();

        assertEquals(0, gridLogic.getBichitosTiempo().size());

        GridLogic newLogic = new GridLogic();
        newLogic.initialize(2);

        assertEquals(1, newLogic.getBichitosTiempo().size());
    }
}