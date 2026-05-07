package test.LogicaNegocio;

import main.ModeloDominio.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios estructurales de las entidades del modelo de dominio.
 * Verifica la correcta instanciación y el acceso a los atributos básicos de las criaturas.
 */
public class TestBichitos {

   //BichoQuieto
    @Test
    void testBichitoQuieto_getPosicionDevuelvePosicionInicial() {
        Posicion pos = new Posicion(2, 3);
        BichitoQuieto bq = new BichitoQuieto(pos);
        assertEquals(pos, bq.getPosicion());
    }

    @Test
    void testBichitoQuieto_setPosicionCambiaPosicion() {
        BichitoQuieto bq = new BichitoQuieto(new Posicion(0, 0));
        Posicion nueva = new Posicion(5, 5);
        bq.setPosicion(nueva);
        assertEquals(nueva, bq.getPosicion());
    }

    @Test
    void testBichitoQuieto_implementaInterfaz() {
        BichitoQuieto bq = new BichitoQuieto(new Posicion(0, 0));
        assertInstanceOf(BichitoInterface.class, bq);
    }

    //BichoMovil
    @Test
    void testBichitoMovil_getPosicionDevuelvePosicionInicial() {
        Posicion pos = new Posicion(4, 7);
        BichitoMovil bm = new BichitoMovil(pos);
        assertEquals(pos, bm.getPosicion());
    }

    @Test
    void testBichitoMovil_setPosicionCambiaPosicion() {
        BichitoMovil bm = new BichitoMovil(new Posicion(1, 1));
        Posicion nueva = new Posicion(9, 9);
        bm.setPosicion(nueva);
        assertEquals(nueva, bm.getPosicion());
    }

    @Test
    void testBichitoMovil_implementaInterfaz() {
        BichitoMovil bm = new BichitoMovil(new Posicion(0, 0));
        assertInstanceOf(BichitoInterface.class, bm);
    }

    //BichoMitosis
    @Test
    void testBichitoMitosis_getPosicionDevuelvePosicionInicial() {
        Posicion pos = new Posicion(0, 9);
        BichitoMitosis bmt = new BichitoMitosis(pos);
        assertEquals(pos, bmt.getPosicion());
    }

    @Test
    void testBichitoMitosis_setPosicionCambiaPosicion() {
        BichitoMitosis bmt = new BichitoMitosis(new Posicion(3, 3));
        Posicion nueva = new Posicion(6, 2);
        bmt.setPosicion(nueva);
        assertEquals(nueva, bmt.getPosicion());
    }

    @Test
    void testBichitoMitosis_implementaInterfaz() {
        BichitoMitosis bmt = new BichitoMitosis(new Posicion(0, 0));
        assertInstanceOf(BichitoInterface.class, bmt);
    }
}