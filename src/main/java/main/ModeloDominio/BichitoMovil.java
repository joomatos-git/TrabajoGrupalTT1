package main.ModeloDominio;

/**
 * Representa una criatura de tipo móvil dentro de la simulación.
 * Su comportamiento consiste en intentar desplazarse a una celda adyacente libre
 * (Norte, Sur, Este u Oeste) en los instantes de tiempo en los que le corresponda actuar.
 */
public class BichitoMovil implements BichitoInterface {
    
    private Posicion posicion;
    
    /**
     * Constructor que inicializa la posición de la criatura.
     * @param p La posición inicial en la cuadrícula.
     */
    public BichitoMovil(Posicion p){
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
