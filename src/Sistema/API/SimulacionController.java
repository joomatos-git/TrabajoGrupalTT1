package Sistema.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulacion")
public class SimulacionController {

    // Spring inyecta automáticamente SimulacionServiceImpl aquí
    // El Controller solo conoce la interfaz, no la implementación
    @Autowired
    private ISimulacionService simulacionService;

    // POST /simulacion/iniciar
    // El cliente manda un JSON con la configuración y recibe el token
    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarSimulacion(@RequestBody ConfiguracionDTO configuracion) {
        String token = simulacionService.iniciarSimulacion(configuracion);
        return ResponseEntity.ok(token);
    }

    // GET /simulacion/estado?token=abc123&instante=2
    // El cliente manda el token y el instante y recibe el estado del tablero
    @GetMapping("/estado")
    public ResponseEntity<EstadoTableroDTO> getEstado(
            @RequestParam String token,
            @RequestParam int instante) {

        EstadoTableroDTO estado = simulacionService.getEstado(token, instante);

        // Si el token no existe, devolvemos 404
        if (estado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(estado);
    }
}