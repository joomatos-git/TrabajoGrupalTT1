package test.LogicaNegocio;

import main.LogicaNegocio.GridLogic;
import main.ModeloDominio.Grid;
import org.junit.Test;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class TestGeneracionInicial {

    GridLogic grid1;
    GridLogic grid2;
    @BeforeEach
    void setUp() {
        grid1 = new GridLogic();
        grid2 = new GridLogic();
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
        grid2 = null;
    }

    @Test
    public void testComprobarIguales() {
        int seed = 84;

        grid1.initialize(seed);
        grid2.initialize(seed);

        assertEquals(grid1.getGrid(), grid2.getGrid());

    }



    @Test
    public void testComprobarDistintos() {
        grid1.initialize();
        grid2.initialize();

        assertNotEquals(grid1.getGrid(), grid2.getGrid());
    }




}