package test.API;

import Sistema.API.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestSimulacionController {

    private SimulacionController controller;
    private SimulacionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new SimulacionServiceImpl();
        controller = new SimulacionController(service);
    }

    @Test
    void testIniciar_returns200() {
        ConfiguracionDTO config = config(10, 10, 2, 2, 2);
        var response = controller.iniciarSimulacion(config);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testIniciar_devuelveTokenNoVacio() {
        ConfiguracionDTO config = config(10, 10, 1, 1, 1);
        var response = controller.iniciarSimulacion(config);
        String token = response.getBody();
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void testGetEstado_tokenValido_returns200() {
        String token = controller.iniciarSimulacion(config(10, 10, 0, 0, 0)).getBody();
        var response = controller.getEstado(token, 0);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetEstado_tokenInvalido_returns404() {
        var response = controller.getEstado("token-que-no-existe", 0);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testGetEstado_respuestaContieneInstante() {
        String token = controller.iniciarSimulacion(config(5, 5, 0, 0, 0)).getBody();
        var response = controller.getEstado(token, 2);
        assertEquals(2, response.getBody().getInstante());
    }

    @Test
    void testGetEstado_respuestaContieneTablero() {
        String token = controller.iniciarSimulacion(config(4, 4, 0, 0, 0)).getBody();
        var response = controller.getEstado(token, 0);
        String[][] tablero = response.getBody().getTablero();
        assertNotNull(tablero);
        assertEquals(4, tablero.length);
    }

    @Test
    void testGetEstado_faltaToken_returns404() {
        var response = controller.getEstado(null, 0);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testGetEstado_tokenVacio_returns404() {
        var response = controller.getEstado("", 0);
        assertEquals(404, response.getStatusCode().value());
    }


    private ConfiguracionDTO config(int filas, int cols, int quietos, int moviles, int mitosis) {
        ConfiguracionDTO c = new ConfiguracionDTO();
        c.setFilas(filas);
        c.setColumnas(cols);
        c.setNumQuietos(quietos);
        c.setNumMoviles(moviles);
        c.setNumMitosis(mitosis);
        return c;
    }
}