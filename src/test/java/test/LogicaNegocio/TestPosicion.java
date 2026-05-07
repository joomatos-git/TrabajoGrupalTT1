package test.LogicaNegocio;

import main.ModeloDominio.Posicion;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase Posicion.
 * Verifica que la comparación de coordenadas (equals) funciona correctamente
 * para la resolución de conflictos en el tablero.
 */
public class TestPosicion {

    @Test
    void testPosicionMismasCoordenadas_sonIguales() {
        Posicion p1 = new Posicion(3, 5);
        Posicion p2 = new Posicion(3, 5);
        assertEquals(p1, p2, "Dos posiciones con las mismas coordenadas deben ser iguales");
    }

    @Test
    void testPosicionCoordenadaXDistinta_noSonIguales() {
        Posicion p1 = new Posicion(3, 5);
        Posicion p2 = new Posicion(4, 5);
        assertNotEquals(p1, p2, "Posiciones con X diferente no deben ser iguales");
    }

    @Test
    void testPosicionCoordenadaYDistinta_noSonIguales() {
        Posicion p1 = new Posicion(3, 5);
        Posicion p2 = new Posicion(3, 6);
        assertNotEquals(p1, p2, "Posiciones con Y diferente no deben ser iguales");
    }

    @Test
    void testPosicionMismoObjeto_esIgual() {
        Posicion p = new Posicion(1, 1);
        assertEquals(p, p, "Un objeto debe ser igual a sí mismo");
    }

    @Test
    void testPosicionContraNull_noSonIguales() {
        Posicion p = new Posicion(0, 0);
        assertNotEquals(p, null, "Una posición no debe ser igual a null");
    }

    @Test
    void testPosicionContraOtroTipo_noSonIguales() {
        Posicion p = new Posicion(1, 2);
        assertNotEquals(p, "1,2", "Una posición no debe ser igual a un String");
    }

    @Test
    void testCoordenadaXAccesible() {
        Posicion p = new Posicion(7, 2);
        assertEquals(7, p.x);
    }

    @Test
    void testCoordenadaYAccesible() {
        Posicion p = new Posicion(7, 2);
        assertEquals(2, p.y);
    }
}