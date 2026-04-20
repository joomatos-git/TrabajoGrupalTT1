package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoMovil;
import main.ModeloDominio.BichitoQuieto;
import main.ModeloDominio.Posicion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;



public class TestMovil {
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
    void testComprobarMoviles() {
        int turnosConMovimiento = 0;

        List<BichitoMovil> listaEspecifica = new ArrayList<BichitoMovil>();
        List<List<BichitoInterface>> listaGeneral = grid1.getBichitosTiempo();
        List<Posicion> posicionesPre = new ArrayList<Posicion>();
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

            // Contar cuántos se movieron en este turno
            int countSamePos = 0;
            int size = Math.min(posicionesPre.size(), posicionesPost.size());
            for (int i = 0; i < size; i++) {
                if (posicionesPre.get(i).equals(posicionesPost.get(i))) {
                    countSamePos++;
                }
            }

            // Si hay bichitos y no todos están acorralados, al menos uno debería moverse
            if (size > 0 && countSamePos < size) {
                turnosConMovimiento++;
            }

            posicionesPre = posicionesPost;
        }

        // A lo largo de 49 turnos, al menos en algunos debería haber movimiento
        Assertions.assertTrue(turnosConMovimiento > 0,
                "Ningún BichitoMovil se movió en ningún turno");
    }


    @Test
    void testComprobarDistintos() {
    }

}
