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
    void testMovilSoloVaAAdyacentes() {
        GridLogic grid = new GridLogic(42, 5, 5);

        //Poner una posición inicial que conozcamos
        grid.getBichitosTiempo().clear();
        List<BichitoInterface> instante0 = new ArrayList<>();

        Posicion posInicial = new Posicion(2, 2);
        instante0.add(new BichitoMovil(posInicial));

        grid.getBichitosTiempo().add(instante0);
        grid.step();

        List<BichitoInterface> instante1 = grid.getBichitosTiempo().get(1);

        //Solo tiene que hbaer un móvil en el turno siguiente
        List<BichitoMovil> moviles = instante1.stream()
                .filter(b -> b instanceof BichitoMovil)
                .map(b -> (BichitoMovil) b)
                .toList();

        Assertions.assertEquals(1, moviles.size(),
                "Debe seguir habiendo exactamente 1 móvil");

        // Comprobar que se movió a una celda adyacente
        Posicion posNueva = moviles.get(0).getPosicion();

        int distancia = Math.abs(posNueva.x - posInicial.x)
                + Math.abs(posNueva.y - posInicial.y);

        Assertions.assertEquals(1, distancia,
                "El móvil debe moverse exactamente a una celda adyacente (distancia Manhattan = 1)");
    }
    @Test
    void testMovilNoCruzaDiagonal() {
        GridLogic grid = new GridLogic(7, 7);
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

            // Para cada móvil en el turno siguiente,
            // comprobar que puede venir de alguna posición válida del turno anterior
            for (BichitoMovil bDespues : despues) {
                Posicion p1 = bDespues.getPosicion();

                boolean movimientoValido = antes.stream().anyMatch(bAntes -> {
                    Posicion p0 = bAntes.getPosicion();
                    int dist = Math.abs(p1.x - p0.x) + Math.abs(p1.y - p0.y);
                    return dist <= 1; // mismo sitio o adyacente
                });

                Assertions.assertTrue(movimientoValido,
                        "Movimiento inválido en turno " + t +
                                " hacia posición (" + p1.x + "," + p1.y + ")");
            }
        }
    }


    @Test
    void testComprobarDistintos() {
    }

}
