package test.LogicaNegocio;

import main.ModeloDominio.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGrid {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(10, 10);
    }

    @Test
    void testGrid_tieneFilas10() {
        assertEquals(10, grid.getMatrix().length,
                "El grid debe tener 10 filas");
    }

    @Test
    void testGrid_tieneColumnas10() {
        assertEquals(10, grid.getMatrix()[0].length,
                "El grid debe tener 10 columnas");
    }

    @Test
    void testGrid_inicializadaConCeros() {
        int[][] m = grid.getMatrix();
        for (int[] fila : m) {
            for (int val : fila) {
                assertEquals(0, val, "La matriz debe estar inicializada a 0");
            }
        }
    }

    @Test
    void testGrid_setMatrixCambiaMatriz() {
        int[][] nueva = new int[5][5];
        nueva[2][2] = 99;
        grid.setMatrix(nueva);
        assertEquals(99, grid.getMatrix()[2][2]);
    }

    @Test
    void testGrid_setMatrixNull_matricesNull() {
        grid.setMatrix(null);
        assertNull(grid.getMatrix());
    }
}