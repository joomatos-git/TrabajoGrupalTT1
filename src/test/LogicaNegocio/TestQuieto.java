package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoQuieto;
import main.ModeloDominio.Grid;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class TestQuieto {

    GridLogic grid1;

    @BeforeEach
    void setUp() {
        grid1 = new GridLogic();
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
    }

    @Test
    void testComprobarInmoviles() {
        List<BichitoQuieto> listaEspecifica = new ArrayList<BichitoQuieto>();
        List<BichitoInterface> listaGeneral = grid1.getBichitos();
        List<int[]> posiciones = new ArrayList<int[]>();
        for(BichitoInterface b: listaGeneral){
            if(b instanceof BichitoQuieto bq){
                listaEspecifica.add(bq);
                posiciones.add(bq.getPos());
            }
        }
        for(int n = 0; n<50; n++){
            grid1.avanzarTurno();
        }
        listaEspecifica = new ArrayList<BichitoQuieto>();
        listaGeneral = grid1.getBichitos();
        List<int[]> posicionesAfter = new ArrayList<int[]>();
        for(BichitoInterface b: listaGeneral){
            if(b instanceof BichitoQuieto bq){
                listaEspecifica.add(bq);
                posicionesAfter.add(bq.getPos());
            }
        }

        for (int i = 0; i < posiciones.size(); i++) {
            assertTrue(Arrays.equals(posiciones.get(i), posicionesAfter.get(i)),
                    "La posición en el índice " + i + " ha cambiado");
        }    }



    @Test
    void testComprobarDistintos() {
    }




}