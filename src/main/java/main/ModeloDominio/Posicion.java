package main.ModeloDominio;

/**
 * Representa una coordenada bidimensional (x, y) en la cuadrícula.
 */
public class Posicion {
    /** Fila de la cuadrícula. */
    public int x;
    /** Columna de la cuadrícula. */
    public int y;

    /**
     * Constructor que inicializa una nueva posición con las coordenadas especificadas.
     * @param x Coordenada en el eje X (fila).
     * @param y Coordenada en el eje Y (columna).
     */
    public Posicion(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Compara dos posiciones basándose en sus coordenadas.
     * @param obj Objeto a comparar.
     * @return true si x e y coinciden en ambos objetos.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Posicion other)) return false;
        return this.x == other.x && this.y == other.y;
    }
}
