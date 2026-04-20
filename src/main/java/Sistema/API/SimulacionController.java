package Sistema.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulacion")
public class SimulacionController {

    @Autowired
    private ISimulacionService simulacionService;

    // Constructor añadido para tests
    public SimulacionController(ISimulacionService simulacionService) {
        this.simulacionService = simulacionService;
    }

    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarSimulacion(@RequestBody ConfiguracionDTO configuracion) {
        String token = simulacionService.iniciarSimulacion(configuracion);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/estado")
    public ResponseEntity<EstadoTableroDTO> getEstado(
            @RequestParam String token,
            @RequestParam int instante) {
        EstadoTableroDTO estado = simulacionService.getEstado(token, instante);
        if (estado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(estado);
    }
}