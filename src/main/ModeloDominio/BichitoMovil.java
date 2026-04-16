package main.ModeloDominio;

public class BichitoMovil implements BichitoInterface {
    private Posicion posicion;
    public BichitoMovil(Posicion p){
        posicion=p;
    }

    @Override
    public Posicion getPosicion() {
        return null;
    }

    @Override
    public void setPosicion(Posicion p) {

    }
}
