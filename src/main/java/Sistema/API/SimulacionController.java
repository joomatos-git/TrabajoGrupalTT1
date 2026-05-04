package Sistema.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class SimulacionController {

    @Autowired
    private ISimulacionService simulacionService;

    // Constructor añadido para tests
    public SimulacionController(ISimulacionService simulacionService) {
        this.simulacionService = simulacionService;
    }

    @PostMapping("Solicitud/Solicitar")
    public ResponseEntity<Integer> iniciarSimulacion(
            @RequestParam(required = false) String nombreUsuario,
            @RequestBody ConfiguracionDTO configuracion) {
        int token = simulacionService.iniciarSimulacion(configuracion);
        System.out.println(token);
        return ResponseEntity.ok(token);
    }

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