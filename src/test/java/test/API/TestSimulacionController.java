package test.API;

import Sistema.API.RestApplication;
import Sistema.API.SimulacionController;
import Sistema.API.SimulacionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests de integración para SimulacionController usando MockMvc.
 * Levanta el contexto Spring completo (SpringBootTest) y simula peticiones HTTP.
 *
 * DEPENDENCIAS NECESARIAS en pom.xml (añadir si no están):
 *
 *   <dependency>
 *       <groupId>org.springframework.boot</groupId>
 *       <artifactId>spring-boot-starter-test</artifactId>
 *       <scope>test</scope>
 *   </dependency>
 */
@SpringBootTest(classes = RestApplication.class)
@AutoConfigureMockMvc
public class TestSimulacionController {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ─── POST /simulacion/iniciar ─────────────────────────────────────────────

    @Test
    void testIniciar_returns200() throws Exception {
        String body = """
                {
                  "filas": 10,
                  "columnas": 10,
                  "numQuietos": 2,
                  "numMoviles": 2,
                  "numMitosis": 2
                }
                """;

        mockMvc.perform(post("/simulacion/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    void testIniciar_devuelveTokenNoVacio() throws Exception {
        String body = """
                {"filas":10,"columnas":10,"numQuietos":1,"numMoviles":1,"numMitosis":1}
                """;

        mockMvc.perform(post("/simulacion/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(not(emptyOrNullString())));
    }

    // ─── GET /simulacion/estado ───────────────────────────────────────────────

    @Test
    void testGetEstado_tokenValido_returns200() throws Exception {
        // Primero obtenemos un token
        String body = """
                {"filas":10,"columnas":10,"numQuietos":0,"numMoviles":0,"numMitosis":0}
                """;
        MvcResult result = mockMvc.perform(post("/simulacion/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();

        String token = result.getResponse().getContentAsString().replace("\"", "");

        // Ahora pedimos el estado
        mockMvc.perform(get("/simulacion/estado")
                        .param("token", token)
                        .param("instante", "0"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEstado_tokenInvalido_returns404() throws Exception {
        mockMvc.perform(get("/simulacion/estado")
                        .param("token", "token-que-no-existe")
                        .param("instante", "0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetEstado_respuestaContieneInstante() throws Exception {
        String body = """
                {"filas":5,"columnas":5,"numQuietos":0,"numMoviles":0,"numMitosis":0}
                """;
        MvcResult result = mockMvc.perform(post("/simulacion/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();

        String token = result.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(get("/simulacion/estado")
                        .param("token", token)
                        .param("instante", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instante").value(2));
    }

    @Test
    void testGetEstado_respuestaContieneTablero() throws Exception {
        String body = """
                {"filas":4,"columnas":4,"numQuietos":0,"numMoviles":0,"numMitosis":0}
                """;
        MvcResult result = mockMvc.perform(post("/simulacion/iniciar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andReturn();

        String token = result.getResponse().getContentAsString().replace("\"", "");

        mockMvc.perform(get("/simulacion/estado")
                        .param("token", token)
                        .param("instante", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tablero").isArray())
                .andExpect(jsonPath("$.tablero.length()").value(4));
    }

    @Test
    void testGetEstado_faltaParametroToken_returns400() throws Exception {
        mockMvc.perform(get("/simulacion/estado")
                        .param("instante", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetEstado_faltaParametroInstante_returns400() throws Exception {
        mockMvc.perform(get("/simulacion/estado")
                        .param("token", "cualquier-cosa"))
                .andExpect(status().isBadRequest());
    }
}