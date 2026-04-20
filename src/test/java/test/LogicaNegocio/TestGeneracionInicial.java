package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestGeneracionInicial {

    GridLogic grid1;
    GridLogic grid2;

    @BeforeEach
    void setUp() {
        grid1 = new GridLogic();
        grid2 = new GridLogic();
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
        grid2 = null;
    }


    @Test
    void testMismaSemilla_mismaCantidadDeBichitos() {
        int seed = 84;
        grid1.initialize(seed);
        grid2.initialize(seed);

        List<BichitoInterface> lista1 = grid1.getBichitosTiempo().get(0);
        List<BichitoInterface> lista2 = grid2.getBichitosTiempo().get(0);

        assertEquals(lista1.size(), lista2.size(),
                "Con la misma semilla deben generarse el mismo número de bichitos");
    }


    @Test
    void testMismaSemilla_mismasPosiciones() {
        int seed = 84;
        grid1.initialize(seed);
        grid2.initialize(seed);

        List<BichitoInterface> lista1 = grid1.getBichitosTiempo().get(0);
        List<BichitoInterface> lista2 = grid2.getBichitosTiempo().get(0);

        for (int i = 0; i < lista1.size(); i++) {
            assertEquals(lista1.get(i).getPosicion(), lista2.get(i).getPosicion(),
                    "Con la misma semilla, la posición " + i + " debe coincidir");
        }
    }


    @Test
    void testMismaSemilla_mismosTipos() {
        int seed = 84;
        grid1.initialize(seed);
        grid2.initialize(seed);

        List<BichitoInterface> lista1 = grid1.getBichitosTiempo().get(0);
        List<BichitoInterface> lista2 = grid2.getBichitosTiempo().get(0);

        for (int i = 0; i < lista1.size(); i++) {
            assertEquals(lista1.get(i).getClass(), lista2.get(i).getClass(),
                    "Con la misma semilla, el tipo del bichito " + i + " debe coincidir");
        }
    }


    @Test
    void testSemillasDistintas_cantidadesDistintas() {
        grid1.initialize(1);
        grid2.initialize(999999);

        int size1 = grid1.getBichitosTiempo().get(0).size();
        int size2 = grid2.getBichitosTiempo().get(0).size();

        assertNotEquals(size1, size2,
                "Con semillas distintas debe generarse un tablero diferente");
    }


    @Test
    void testInicializar_generaExactamenteUnaGeneracion() {
        grid1.initialize(42);
        assertEquals(1, grid1.getBichitosTiempo().size(),
                "Tras initialize debe existir exactamente 1 instante de tiempo");
    }


    @Test
    void testInicializar_todosBichitosEnRangos() {
        grid1.initialize(42);
        for (BichitoInterface b : grid1.getBichitosTiempo().get(0)) {
            int x = b.getPosicion().x;
            int y = b.getPosicion().y;
            assertTrue(x >= 0 && x < 10, "X fuera de rango: " + x);
            assertTrue(y >= 0 && y < 10, "Y fuera de rango: " + y);
        }
    }


    @Test
    void testInicializar_sinColisionesIniciales() {
        grid1.initialize(42);
        List<BichitoInterface> lista = grid1.getBichitosTiempo().get(0);
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                assertNotEquals(lista.get(i).getPosicion(), lista.get(j).getPosicion(),
                        "No puede haber dos bichitos en la misma posición inicial");
            }
        }
    }

    /**
     * teardown() debe limpiar el estado completamente.
     */
    @Test
    void testTeardown_limpiaLista() {
        grid1.initialize(42);
        grid1.teardown();
        assertEquals(0, grid1.getBichitosTiempo().size(),
                "Tras teardown, bichitosTiempo debe estar vacío");
    }
}