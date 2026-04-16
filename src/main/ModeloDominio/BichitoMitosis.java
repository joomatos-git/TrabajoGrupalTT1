package main.ModeloDominio;

public class BichitoMitosis implements BichitoInterface {
    private Posicion posicion;
    public BichitoMitosis(Posicion p){
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
