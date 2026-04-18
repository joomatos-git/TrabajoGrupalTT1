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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class TestMovil {
    GridLogic grid1;

    @BeforeEach
    void setUp() {
        grid1 = new GridLogic();
        grid1.initialize();
        for (int n = 1; n < 50; n++) {
            grid1.step();
        }
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
    }

    @Test
    void testComprobarInmoviles() {
        List<BichitoMovil> listaEspecifica = new ArrayList<BichitoMovil>();
        List<List<BichitoInterface>> listaGeneral = grid1.getBichitosTiempo();
        List<Posicion> posicionesPre = new ArrayList<Posicion>();
        List<Posicion> posicionesPost = new ArrayList<Posicion>();
        for (BichitoInterface b : listaGeneral.get(0)) {
            if (b instanceof BichitoMovil bm) {
                listaEspecifica.add(bm);
                posicionesPre.add(bm.getPosicion());
            }
        }
        for (int n = 1; n < 50; n++) {
            for (BichitoInterface b : listaGeneral.get(n)) {
                if (b instanceof BichitoMovil bm) {
                    listaEspecifica.add(bm);
                    posicionesPost.add(bm.getPosicion());
                }
            }
            int countSamePos = 0;
            for (int i = 0; i < posicionesPre.size(); i++) {
                if (posicionesPre.get(i).equals(posicionesPost.get(i))) {
                    countSamePos++;
                }
                // asi me aseguro que por lo menos alguno se ha movido. No puedo comprobar que "todos" se hayan movido porque igual alguno no se mueve esta "ronda". Tecnicamente puede ocurrir que no se mueva ninguno en una ronda pero ya sería mala folla.
                Assertions.assertNotEquals(countSamePos, posicionesPre.size());
            }
            posicionesPre = posicionesPost;
        }
    }


    @Test
    void testComprobarDistintos() {
    }

}
