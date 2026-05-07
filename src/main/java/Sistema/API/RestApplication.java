package Sistema.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de configuración y arranque de la aplicación Spring Boot.
 * Levanta el servidor web integrado y expone la API REST del simulador.
 */
@SpringBootApplication
public class RestApplication {

    /**
     * Método principal que inicia la ejecución de la aplicación.
     * @param args Argumentos pasados por línea de comandos al iniciar el programa.
     */
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
