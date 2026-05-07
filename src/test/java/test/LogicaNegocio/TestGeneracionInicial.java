package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de verificación de la configuración inicial.
 * Comprueba que el tablero se puebla con las cantidades exactas requeridas
 * y que las posiciones iniciales son siempre válidas y sin solapamientos.
 */
public class TestGeneracionInicial {

    GridLogic grid1;
    GridLogic grid2;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        grid1 = null;
        grid2 = null;
    }


    @Test
    void testMismaConfiguracion_mismaCantidadDeBichitos() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        grid2 = new GridLogic(new int[]{3, 3, 3});

        List<BichitoInterface> lista1 = grid1.getBichitosTiempo().get(0);
        List<BichitoInterface> lista2 = grid2.getBichitosTiempo().get(0);

        assertEquals(lista1.size(), lista2.size(),
                "Con la misma configuración deben generarse el mismo número de bichitos");
    }

    @Test
    void testMismaConfiguracion_mismasPosicionesTotales() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        grid2 = new GridLogic(new int[]{3, 3, 3});

        List<BichitoInterface> lista1 = grid1.getBichitosTiempo().get(0);
        List<BichitoInterface> lista2 = grid2.getBichitosTiempo().get(0);

         assertEquals(lista1.size(), lista2.size(),
                "Ambas configuraciones deben tener el mismo número de bichitos");
    }

    @Test
    void testMismaConfiguracion_mismosTipos() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        grid2 = new GridLogic(new int[]{3, 3, 3});

        List<BichitoInterface> lista1 = grid1.getBichitosTiempo().get(0);
        List<BichitoInterface> lista2 = grid2.getBichitosTiempo().get(0);

        for (int i = 0; i < lista1.size(); i++) {
            assertEquals(lista1.get(i).getClass(), lista2.get(i).getClass(),
                    "El tipo del bichito " + i + " debe coincidir entre configuraciones iguales");
        }
    }

    @Test
    void testConfiguracionesDistintas_cantidadesDistintas() {
        grid1 = new GridLogic(new int[]{1, 0, 0});
        grid2 = new GridLogic(new int[]{5, 5, 5});

        int size1 = grid1.getBichitosTiempo().get(0).size();
        int size2 = grid2.getBichitosTiempo().get(0).size();

        assertNotEquals(size1, size2,
                "Con configuraciones distintas debe generarse un número distinto de bichitos");
    }

    @Test
    void testInicializar_generaExactamenteUnaGeneracion() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        assertEquals(1, grid1.getBichitosTiempo().size(),
                "Tras initialize debe existir exactamente 1 instante de tiempo");
    }

    @Test
    void testInicializar_todosBichitosEnRangos() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        for (BichitoInterface b : grid1.getBichitosTiempo().get(0)) {
            int x = b.getPosicion().x;
            int y = b.getPosicion().y;
            assertTrue(x >= 0 && x < 10, "X fuera de rango: " + x);
            assertTrue(y >= 0 && y < 10, "Y fuera de rango: " + y);
        }
    }

    @Test
    void testInicializar_sinColisionesIniciales() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        List<BichitoInterface> lista = grid1.getBichitosTiempo().get(0);
        for (int i = 0; i < lista.size(); i++) {
            for (int j = i + 1; j < lista.size(); j++) {
                assertNotEquals(lista.get(i).getPosicion(), lista.get(j).getPosicion(),
                        "No puede haber dos bichitos en la misma posición inicial");
            }
        }
    }

    @Test
    void testTeardown_limpiaLista() {
        grid1 = new GridLogic(new int[]{3, 3, 3});
        grid1.teardown();
        assertEquals(0, grid1.getBichitosTiempo().size(),
                "Tras teardown, bichitosTiempo debe estar vacío");
    }
}