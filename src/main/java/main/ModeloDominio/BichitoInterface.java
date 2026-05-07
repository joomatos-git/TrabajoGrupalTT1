package main.ModeloDominio;

/**
 * Interfaz que define el modelo común para todas las criaturas del sistema.
 */
public interface BichitoInterface {
    
    /** @return La posición actual de la criatura en la cuadrícula. */
    Posicion getPosicion();
    
    /** @param p Nueva posición que ocupará la criatura. */
    void setPosicion(Posicion p);
}

