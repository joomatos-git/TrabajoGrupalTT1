package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.BichitoInterface;
import main.ModeloDominio.BichitoQuieto;
import main.ModeloDominio.Grid;
import main.ModeloDominio.Posicion;
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
        grid1.initialize();
        for(int n=1; n<50; n++){
            grid1.step();
        }
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
    }

    @Test
    void testComprobarInmoviles() {
        List<BichitoQuieto> listaEspecifica = new ArrayList<BichitoQuieto>();
        List<List<BichitoInterface>> listaGeneral = grid1.getBichitosTiempo();
        List<Posicion> posicionesPre = new ArrayList<Posicion>();
        List<Posicion> posicionesPost = new ArrayList<Posicion>();
        for(BichitoInterface b: listaGeneral.get(0)){
            if(b instanceof BichitoQuieto bq){
                listaEspecifica.add(bq);
                posicionesPre.add(bq.getPosicion());
            }
        }
        for(int n = 1; n<50; n++){
            for(BichitoInterface b: listaGeneral.get(n)){
                if(b instanceof BichitoQuieto bq){
                    listaEspecifica.add(bq);
                    posicionesPost.add(bq.getPosicion());
                }
            }
            for (int i = 0; i < posicionesPre.size(); i++) {
                assertEquals(posicionesPost.get(i), posicionesPre.get(i));
            }
            posicionesPre = posicionesPost;
        }
    }



    @Test
    void testComprobarDistintos() {
    }




}