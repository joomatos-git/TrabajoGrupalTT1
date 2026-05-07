package main.ModeloDominio;

/**
 * Representa una criatura de tipo estático dentro de la simulación.
 * Su comportamiento se caracteriza por permanecer siempre en su celda inicial,
 * sin desplazarse ni multiplicarse durante el transcurso de los instantes.
 */
public class BichitoQuieto implements BichitoInterface {
    
    private Posicion posicion;
    
    /**
     * Constructor que inicializa la posición de la criatura.
     * @param p La posición inicial en la cuadrícula.
     */
    public BichitoQuieto(Posicion p){
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
