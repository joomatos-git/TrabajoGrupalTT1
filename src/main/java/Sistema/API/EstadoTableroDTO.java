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


    // Constructor vacío necesario para que Jackson pueda crear el objeto.
    public EstadoTableroDTO() {}

    // Constructor cómodo para crear el objeto de una vez.
    public EstadoTableroDTO(String data) {
        this.data = data;

    }
    // Getters, Jackson los necesita para convertir a JSON.
    public int getInstante() { return instante; }
    public String getData() { return data; }
}