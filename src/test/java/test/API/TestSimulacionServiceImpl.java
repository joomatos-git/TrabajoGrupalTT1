package test.API;

import Sistema.API.ConfiguracionDTO;
import Sistema.API.EstadoTableroDTO;
import Sistema.API.SimulacionServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestSimulacionServiceImpl {

    private SimulacionServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new SimulacionServiceImpl();
    }

    @AfterEach
    void tearDown() {
        service = null;
    }

    @Test
    void testIniciar_devuelveTokenNoNulo() {
        String token = service.iniciarSimulacion(configBasica());
        assertNotNull(token, "El token no debe ser null");
    }

    @Test
    void testIniciar_devuelveTokenNoVacio() {
        String token = service.iniciarSimulacion(configBasica());
        assertFalse(token.isBlank(), "El token no debe estar vacío");
    }

    @Test
    void testIniciar_cadaLlamadaGeneraTokenDistinto() {
        String token1 = service.iniciarSimulacion(configBasica());
        String token2 = service.iniciarSimulacion(configBasica());
        assertNotEquals(token1, token2, "Cada simulación debe tener un token único");
    }

    @Test
    void testGetEstado_tokenValido_devuelveEstadoNoNulo() {
        String token = service.iniciarSimulacion(configBasica());
        EstadoTableroDTO estado = service.getEstado(token, 0);
        assertNotNull(estado, "getEstado con token válido no debe devolver null");
    }

    @Test
    void testGetEstado_tokenInvalido_devuelveNull() {
        EstadoTableroDTO estado = service.getEstado("token-inexistente", 0);
        assertNull(estado, "getEstado con token inválido debe devolver null");
    }

    @Test
    void testGetEstado_tableroTieneDimensionesCorrectas() {
        ConfiguracionDTO config = new ConfiguracionDTO();
        config.setFilas(5);
        config.setColumnas(8);

        String token = service.iniciarSimulacion(config);
        EstadoTableroDTO estado = service.getEstado(token, 0);

        assertEquals(5, estado.getTablero().length, "Número de filas incorrecto");
        assertEquals(8, estado.getTablero()[0].length, "Número de columnas incorrecto");
    }

    @Test
    void testGetEstado_devuelveInstanteCorrecto() {
        String token = service.iniciarSimulacion(configBasica());
        int instante = 3;
        EstadoTableroDTO estado = service.getEstado(token, instante);
        assertEquals(instante, estado.getInstante());
    }

    @Test
    void testGetEstado_simulacionNoTerminadaInicialmente() {
        String token = service.iniciarSimulacion(configBasica());
        EstadoTableroDTO estado = service.getEstado(token, 0);
        assertFalse(estado.isSimulacionTerminada(),
                "La simulación no debería estar terminada en el instante 0");
    }

    @Test
    void testGetEstado_variosTokensIndependientes() {
        String token1 = service.iniciarSimulacion(config(3, 3));
        String token2 = service.iniciarSimulacion(config(7, 7));

        EstadoTableroDTO e1 = service.getEstado(token1, 0);
        EstadoTableroDTO e2 = service.getEstado(token2, 0);

        assertEquals(3, e1.getTablero().length);
        assertEquals(7, e2.getTablero().length);
    }

    private ConfiguracionDTO configBasica() {
        return config(10, 10);
    }

    private ConfiguracionDTO config(int filas, int cols) {
        ConfiguracionDTO c = new ConfiguracionDTO();
        c.setFilas(filas);
        c.setColumnas(cols);
        return c;
    }
}
