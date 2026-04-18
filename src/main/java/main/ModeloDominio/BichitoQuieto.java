package main.ModeloDominio;

public class BichitoQuieto implements BichitoInterface {
    private Posicion posicion;
    public BichitoQuieto(Posicion p){
        posicion=p;
    }

    @Override
    public Posicion getPosicion() {
        return posicion;
    }

    @Override
    public void setPosicion(Posicion p) {
        this.posicion = p;
    }
}
