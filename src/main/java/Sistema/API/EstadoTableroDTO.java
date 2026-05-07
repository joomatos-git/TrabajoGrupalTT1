package Sistema.API;

/**
 * Objeto de Transferencia de Datos utilizado para enviar los resultados 
 * y la traza completa de la simulación al frontend.
 */
public class EstadoTableroDTO {

    // En qué instante de la simulación estamos. 
    private int instante;

    /** 
    * La cuadrícula: cada celda tiene un String que puede ser
    * "QUIETO", "MOVIL", "MITOSIS" o null si está vacía
    * Podrías devolver los objetos Bichito, pero para la API es más limpio devolver solo lo que el cliente necesita ver. 
    * Un String como "QUIETO" es suficiente para que la capa de presentación sepa qué color pintar. No hace falta mandar todo el objeto Java.
    */
    private String data;


    /**
     * Constructor sin paráremtros necesario para que la librería Jackson pueda deserializar el objeto.
     */
    public EstadoTableroDTO() {}

    /**
     * Constructor que inicializa el objeto con la traza de la simulación.
     * @param data String multilínea con los datos de la simulación.
     */
    public EstadoTableroDTO(String data) {
        this.data = data;

    }
    
    /**
     * Obtiene el instante actual (usado para consultas individuales).
     * @return El número del instante.
     */
    public int getInstante() { return instante; }
    
    /**
     * Obtiene los datos del tablero formateados.
     * @return Cadena de texto con la información de todos los turnos.
     */
    public String getData() { return data; }
}