package main.LogicaNegocio;

import main.ModeloDominio.*;

import java.util.*;
public class GridLogic {
    Grid grid;
    List<List<BichitoInterface>> bichitosTiempo;
    int roundsStuck=0;

    public GridLogic(int[] cantidadesIniciales) {
        grid = new Grid(10, 10);
        initialize(cantidadesIniciales[0], cantidadesIniciales[1], cantidadesIniciales[2]);
    }
    public void initialize(int numQuietos, int numMoviles, int numMitosis) {
        bichitosTiempo = new ArrayList<>();
        bichitosTiempo.add(new ArrayList<>());
        Random r = new Random();

        List<Posicion> posiciones = new ArrayList<>();
        for (int row = 0; row < grid.getMatrix().length; row++) {
            for (int col = 0; col < grid.getMatrix()[row].length; col++) {
                posiciones.add(new Posicion(row, col));
            }
        }
        Collections.shuffle(posiciones, r);

        int idx = 0;
        for (int i = 0; i < numQuietos && idx < posiciones.size(); i++, idx++) {
            bichitosTiempo.get(0).add(new BichitoQuieto(posiciones.get(idx)));
        }
        for (int i = 0; i < numMoviles && idx < posiciones.size(); i++, idx++) {
            bichitosTiempo.get(0).add(new BichitoMovil(posiciones.get(idx)));
        }
        for (int i = 0; i < numMitosis && idx < posiciones.size(); i++, idx++) {
            bichitosTiempo.get(0).add(new BichitoMitosis(posiciones.get(idx)));
        }
    }
    public void step(){
        boolean stuck=true;

        if (bichitosTiempo.isEmpty()) return;

        List<BichitoInterface> genActual = bichitosTiempo.get(bichitosTiempo.size() - 1);
        List<BichitoInterface> genNueva = new ArrayList<>();

        int filas = grid.getMatrix().length;
        int columnas = grid.getMatrix()[0].length;

        boolean[][] casillasOcupadas = new boolean[filas][columnas];
        Random r = new Random();

        List<BichitoInterface> bichosOrdenados = new ArrayList<>(genActual);
        bichosOrdenados.sort((b1, b2) -> {
            Posicion p1 = b1.getPosicion();
            Posicion p2 = b2.getPosicion();
            if (p1.x != p2.x) {
                return Integer.compare(p1.x, p2.x);
            }
             return Integer.compare(p2.y, p1.y);
        });

        for (BichitoInterface bicho : bichosOrdenados) {
            Posicion pos = bicho.getPosicion();
            if (bicho instanceof BichitoQuieto) {
                genNueva.add(new BichitoQuieto(new Posicion(pos.x, pos.y)));
                casillasOcupadas[pos.x][pos.y] = true;
            }
        }

        for (BichitoInterface bicho : bichosOrdenados) {
            Posicion pos = bicho.getPosicion();
            if (bicho instanceof BichitoMitosis) {
                genNueva.add(new BichitoMitosis(new Posicion(pos.x, pos.y)));
                casillasOcupadas[pos.x][pos.y] = true;
            }
        }

        for (BichitoInterface bicho : bichosOrdenados) {
            Posicion pos = bicho.getPosicion();
            if (bicho instanceof BichitoMovil) {
                List<Posicion> libres = obtenerAdyacentesLibres(pos, casillasOcupadas, filas, columnas);
                if (!libres.isEmpty() && r.nextInt(2) == 0) {
                    Posicion nuevaPos = libres.get(r.nextInt(libres.size()));
                    genNueva.add(new BichitoMovil(nuevaPos));
                    casillasOcupadas[nuevaPos.x][nuevaPos.y] = true;
                    stuck = false;
                } else {
                    final Posicion posFinal = pos;
                    genNueva.removeIf(b -> b instanceof BichitoMitosis
                            && b.getPosicion().equals(posFinal));
                    genNueva.add(new BichitoMovil(new Posicion(pos.x, pos.y)));
                    casillasOcupadas[pos.x][pos.y] = true;
                }
            }
        }

        for (BichitoInterface bicho : bichosOrdenados) {
            Posicion pos = bicho.getPosicion();
            if (bicho instanceof BichitoMitosis) {
                List<Posicion> libres = obtenerAdyacentesLibres(pos, casillasOcupadas, filas, columnas);
                if (!libres.isEmpty() && r.nextInt(4) == 0) {
                    Posicion nuevaPos = libres.get(r.nextInt(libres.size()));
                    genNueva.add(new BichitoMitosis(nuevaPos));
                    casillasOcupadas[nuevaPos.x][nuevaPos.y] = true;
                    stuck = false;
                }
            }
        }

        if(stuck){
            roundsStuck++;
        }else{
            roundsStuck=0;
        }

        bichitosTiempo.add(genNueva);
    }

    public Grid getGrid(){
        return grid;
    }

    public void setGrid(Grid g){
        grid=g;
    }

    public List<List<BichitoInterface>> getBichitosTiempo(){
        return bichitosTiempo;
    }

    public void teardown(){
        bichitosTiempo.clear();
        grid.setMatrix(null);
    }

    private List<Posicion> obtenerAdyacentesLibres(Posicion p, boolean[][] ocupadas, int maxFilas, int maxCols) {
        List<Posicion> libres = new ArrayList<>();
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : direcciones) {
            int nx = p.x + dir[0];
            int ny = p.y + dir[1];

            if (nx >= 0 && nx < maxFilas && ny >= 0 && ny < maxCols && !ocupadas[nx][ny]) {
                libres.add(new Posicion(nx, ny));
            }
        }
        return libres;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridLogic other)) return false;
        return Objects.equals(this.bichitosTiempo, other.bichitosTiempo);
    }

    public boolean isStuck(){
        return roundsStuck>=10;
    }

}