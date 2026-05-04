package test.API;

import Sistema.API.*;
import org.junit.jupiter.api.*;

import java.util.List;

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


        ConfiguracionDTO config = config(new int[]{5, 5, 5}, new String[]{"Quieto", "Movil", "Mitosis"});

        var response = controller.iniciarSimulacion("", config);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testIniciar_devuelveTokenNoVacio() {
        ConfiguracionDTO config = config(new int[]{5, 5, 5}, new String[]{"Quieto", "Movil", "Mitosis"});
        var response = controller.iniciarSimulacion("",config);
        int token = response.getBody();
        assertNotNull(token);
        assertFalse(token==0);
    }

    @Test
    void testGetEstado_tokenValido_returns200() {
        int token = controller.iniciarSimulacion("",config(new int[]{5, 5, 5}, new String[]{"Quieto", "Movil", "Mitosis"})).getBody();

        var response = controller.getEstado(token);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetEstado_tokenInvalido_returns404() {
        var response = controller.getEstado(12345);
        assertEquals(404, response.getStatusCode().value());
    }



    @Test
    void testGetEstado_respuestaContieneTablero() {
        int token = controller.iniciarSimulacion("",config(new int[]{5, 5, 5}, new String[]{"Quieto", "Movil", "Mitosis"})).getBody();
        var response = controller.getEstado(token);
        String tablero = response.getBody().getTablero();
        assertNotNull(tablero);
    }

    @Test
    void testGetEstado_faltaToken_returns404() {
        var response = controller.getEstado(0);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testGetEstado_tokenVacio_returns404() {
        var response = controller.getEstado(0);
        assertEquals(404, response.getStatusCode().value());
    }


    private ConfiguracionDTO config(int[] cantidades, String[] nombres) {
        ConfiguracionDTO c = new ConfiguracionDTO();
        c.setCantidadesIniciales(cantidades);
        c.setNombreEntidades(nombres);
        return c;
    }
}