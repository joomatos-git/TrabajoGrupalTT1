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
import static org.junit.jupiter.api.Assertions.assertNotEquals;



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
        // Creamos una segunda simulación con una semilla distinta a la implícita de grid1
        GridLogic grid2 = new GridLogic(1234);
        for (int n = 1; n < 50; n++) {
            grid2.step();
        }

        // Recuperamos la última generación de ambas simulaciones
        List<BichitoInterface> ultimaGen1 = grid1.getBichitosTiempo().get(49);
        List<BichitoInterface> ultimaGen2 = grid2.getBichitosTiempo().get(49);

        // Extraemos solo las posiciones de los móviles
        List<Posicion> posMoviles1 = new ArrayList<>();
        for (BichitoInterface b : ultimaGen1) {
            if (b instanceof BichitoMovil) posMoviles1.add(b.getPosicion());
        }

        List<Posicion> posMoviles2 = new ArrayList<>();
        for (BichitoInterface b : ultimaGen2) {
            if (b instanceof BichitoMovil) posMoviles2.add(b.getPosicion());
        }

        // Si la aleatoriedad funciona, las posiciones finales de los móviles deben ser distintas
        assertNotEquals(posMoviles1, posMoviles2, "Dos simulaciones distintas no deberían tener los móviles en las mismas posiciones");
    }
}
