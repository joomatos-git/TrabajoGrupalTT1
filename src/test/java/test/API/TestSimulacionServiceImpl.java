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
        EstadoTableroDTO estado = service.getEstado(token);
        assertNotNull(estado, "getEstado con token válido no debe devolver null");
    }

    @Test
    void testGetEstado_tokenInvalido_devuelveNull() {
        EstadoTableroDTO estado = service.getEstado("token-inexistente");
        assertNull(estado, "getEstado con token inválido debe devolver null");
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
