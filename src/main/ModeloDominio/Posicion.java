package main.ModeloDominio;

public class Posicion {
    public int x;
    public int y;

    public Posicion(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Posicion other)) return false;
        return this.x == other.x && this.y == other.y;
    }
}
