package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoQuieto;
import main.ModeloDominio.Posicion;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Comprobar funcionalidad del bichoquieto
public class TestQuieto {

    GridLogic grid1;

    @BeforeEach
    void setUp() {
        grid1 = new GridLogic(42);
        for (int n = 0; n < 10; n++) {
            grid1.step();
        }
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
    }


    @Test
    void testQuietos_noSeMovenEnNingunaRonda() {
        List<List<BichitoInterface>> historial = grid1.getBichitosTiempo();

        List<Posicion> posicionesIniciales = new ArrayList<>();
        for (BichitoInterface b : historial.get(0)) {
            if (b instanceof BichitoQuieto) {
                posicionesIniciales.add(b.getPosicion());
            }
        }

        assumeTrue(!posicionesIniciales.isEmpty(),
                "Con esta semilla no hay BichitoQuieto en la generación inicial");

        for (int n = 1; n < historial.size(); n++) {
            List<Posicion> posicionesEnN = new ArrayList<>();
            for (BichitoInterface b : historial.get(n)) {
                if (b instanceof BichitoQuieto) {
                    posicionesEnN.add(b.getPosicion());
                }
            }
            assertEquals(posicionesIniciales.size(), posicionesEnN.size(),
                    "El número de BichitoQuieto no debe cambiar en el instante " + n);
            for (int i = 0; i < posicionesIniciales.size(); i++) {
                assertEquals(posicionesIniciales.get(i), posicionesEnN.get(i),
                        "BichitoQuieto " + i + " se movió en el instante " + n);
            }
        }
    }


    @Test
    void testQuietos_cantidadConstanteEnElTiempo() {
        List<List<BichitoInterface>> historial = grid1.getBichitosTiempo();

        long cuentaInicial = historial.get(0).stream()
                .filter(b -> b instanceof BichitoQuieto).count();

        for (int n = 1; n < historial.size(); n++) {
            long cuentaN = historial.get(n).stream()
                    .filter(b -> b instanceof BichitoQuieto).count();
            assertEquals(cuentaInicial, cuentaN,
                    "La cantidad de BichitoQuieto cambió en el instante " + n);
        }
    }


    private void assumeTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assumptions.assumeTrue(condition, message);
    }
}