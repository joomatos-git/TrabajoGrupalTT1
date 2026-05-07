package main.ModeloDominio;

/**
 * Representa una criatura con capacidad de clonación (comportamiento epidémico).
 * A lo largo de la simulación, la criatura original mantiene su posición, pero 
 * intenta generar una copia perfecta de sí misma en una celda adyacente vacía.
 */
public class BichitoMitosis implements BichitoInterface {
    
    private Posicion posicion;
    
    /**
     * Constructor que inicializa la posición de la criatura.
     * @param p La posición inicial en la cuadrícula.
     */
    public BichitoMitosis(Posicion p){
        posicion=p;
    }
    
    /** {@inheritDoc} */
    @Override
    public Posicion getPosicion() {
        return posicion;
    }

    /** {@inheritDoc} */
    @Override
    public void setPosicion(Posicion p) {
        this.posicion = p;
    }
}
