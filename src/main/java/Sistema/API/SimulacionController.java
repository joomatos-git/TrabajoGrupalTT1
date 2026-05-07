package Sistema.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que expone los puntos de entrada para la comunicación
 * entre el cliente y el simulador de criaturas.
 */
@RestController
@RequestMapping("/")
public class SimulacionController {

    @Autowired
    private ISimulacionService simulacionService;

    
    /**
     * Constructor que inyecta la dependencia del servicio de simulación.
     * @param simulacionService Implementación del servicio de lógica de negocio.
     */
    public SimulacionController(ISimulacionService simulacionService) {
        this.simulacionService = simulacionService;
    }
    
    /**
     * Recibe una solicitud de simulación y devuelve un identificador único.
     * @param nombreUsuario Nombre del solicitante (opcional).
     * @param configuracion Objeto JSON con las cantidades de cada tipo de criatura.
     * @return ResponseEntity con el token numérico de la simulación.
     */
    @PostMapping("Solicitud/Solicitar")
    public ResponseEntity<Integer> iniciarSimulacion(
            @RequestParam(required = false) String nombreUsuario,
            @RequestBody ConfiguracionDTO configuracion) {
        int token = simulacionService.iniciarSimulacion(configuracion);
        System.out.println(token);
        return ResponseEntity.ok(token);
    }

    /**
     * Devuelve los resultados acumulados de una simulación específica.
     * @param tok Identificador numérico de la simulación.
     * @return DTO con el string de datos formateado para la capa de presentación.
     */
    @PostMapping(value = "Resultados")
    public ResponseEntity<EstadoTableroDTO> getEstado(
            @RequestParam int tok) {
        System.out.println("ha entrao");
        EstadoTableroDTO estado = simulacionService.getEstado(tok);
        if (estado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(estado);
    }
}