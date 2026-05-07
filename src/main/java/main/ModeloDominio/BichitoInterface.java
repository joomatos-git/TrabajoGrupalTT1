package main.ModeloDominio;

/**
 * Interfaz que define el modelo común para todas las criaturas del sistema.
 */
public interface BichitoInterface {
    
    /**
     * Obtiene la posición actual de la criatura.
     * @return La posición actual de la criatura en la cuadrícula. 
     */
    Posicion getPosicion();
    
    /**
     * Establece una nueva posición en el tablero para la criatura.
     * @param p Nueva posición que ocupará la criatura. 
     */
    void setPosicion(Posicion p);
}

