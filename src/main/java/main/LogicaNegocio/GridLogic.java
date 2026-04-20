package main.LogicaNegocio;

import main.ModeloDominio.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GridLogic {
    Grid grid;
    List<List<BichitoInterface>> bichitosTiempo;

    public GridLogic(){
        initialize();
    }

    public GridLogic(int seed){
        initialize(seed);
    }

    private void initialize(){
        bichitosTiempo = new ArrayList<List<BichitoInterface>>();
        bichitosTiempo.add(new ArrayList<>());
        Random r = new Random();
        grid = new Grid();
        for (int row = 0; row < grid.getMatrix().length; row++) {
            for (int col = 0; col < grid.getMatrix()[row].length; col++) {

                if (r.nextInt(6) == 0) {
                    switch (r.nextInt(3)) {
                        case 0:
                            bichitosTiempo.get(0).add(
                                    new BichitoMitosis(new Posicion(row, col))
                            );
                            break;
                        case 1:
                            bichitosTiempo.get(0).add(
                                    new BichitoMovil(new Posicion(row, col))
                            );
                            break;
                        case 2:
                            bichitosTiempo.get(0).add(
                                    new BichitoQuieto(new Posicion(row, col))
                            );
                            break;
                    }
                }
            }
        }

    }


    public void initialize(int seed){
        bichitosTiempo = new ArrayList<List<BichitoInterface>>();
        bichitosTiempo.add(new ArrayList<>());
        Random r = new Random(seed);
        grid = new Grid();
        for (int row = 0; row < grid.getMatrix().length; row++) {
            for (int col = 0; col < grid.getMatrix()[row].length; col++) {

                if (r.nextInt(6) < 2) {
                    switch (r.nextInt(6)) {
                        case (0):
                        case (1):
                        case (2):
                            bichitosTiempo.get(0).add(
                                    new BichitoQuieto(new Posicion(row, col))
                            );
                            break;
                        case 3:
                        case 4:
                            bichitosTiempo.get(0).add(
                                    new BichitoMovil(new Posicion(row, col))
                            );
                            break;
                        case 5:
                            bichitosTiempo.get(0).add(
                                    new BichitoMitosis(new Posicion(row, col))
                            );
                            break;
                    }
                }
            }
        }
    }




    //para avanzar "turnos"
    public void step(){
        // Si no hay estado inicial, no podemos avanzar
        if (bichitosTiempo.isEmpty()) return;

        // Recuperamos los bichitos actuales
        List<BichitoInterface> genActual = bichitosTiempo.get(bichitosTiempo.size() - 1);
        List<BichitoInterface> genNueva = new ArrayList<>();
        
        int filas = grid.getMatrix().length;
        int columnas = grid.getMatrix()[0].length;

        // Matriz auxiliar para registrar qué casillas del nuevo turno ya están cogidas
        boolean[][] casillasOcupadas = new boolean[filas][columnas];
        Random r = new Random();

        //ORDENAR POR PRIORIDAD 
        List<BichitoInterface> bichosOrdenados = new ArrayList<>(genActual);
        bichosOrdenados.sort((b1, b2) -> {
            Posicion p1 = b1.getPosicion();
            Posicion p2 = b2.getPosicion();
            // Fila superior tiene prioridad (menor x)
            if (p1.x != p2.x) {
                return Integer.compare(p1.x, p2.x);
            }
            // En caso de empate, columna más a la derecha (mayor y)
            return Integer.compare(p2.y, p1.y);
        });

        //Reservar sitio para los que NO se mueven
        for (BichitoInterface bicho : bichosOrdenados) {
            Posicion pos = bicho.getPosicion();
            
            if (bicho instanceof BichitoQuieto) {
                genNueva.add(new BichitoQuieto(new Posicion(pos.x, pos.y)));
                casillasOcupadas[pos.x][pos.y] = true;
            } else if (bicho instanceof BichitoMitosis) {
                // El bicho original de mitosis se queda en su sitio
                genNueva.add(new BichitoMitosis(new Posicion(pos.x, pos.y)));
                casillasOcupadas[pos.x][pos.y] = true;
            }
        }

        //Mover a los Móviles y generar hijos de Mitosis
        for (BichitoInterface bicho : bichosOrdenados) {
            Posicion pos = bicho.getPosicion();

            if (bicho instanceof BichitoMovil) {
                List<Posicion> libres = obtenerAdyacentesLibres(pos, casillasOcupadas, filas, columnas);
                if (!libres.isEmpty()) {
                    // Se mueve a una adyacente libre aleatoria
                    Posicion nuevaPos = libres.get(r.nextInt(libres.size()));
                    genNueva.add(new BichitoMovil(nuevaPos));
                    casillasOcupadas[nuevaPos.x][nuevaPos.y] = true;
                } else {
                    // Si está acorralado, se queda en su sitio (si nadie se lo ha quitado)
                    if (!casillasOcupadas[pos.x][pos.y]) {
                        genNueva.add(new BichitoMovil(new Posicion(pos.x, pos.y)));
                        casillasOcupadas[pos.x][pos.y] = true;
                    }
                }
            } else if (bicho instanceof BichitoMitosis) {
                // Comportamiento epidémico: Generar copia en casilla libre
                List<Posicion> libres = obtenerAdyacentesLibres(pos, casillasOcupadas, filas, columnas);
                if (!libres.isEmpty()) {
                    Posicion nuevaPos = libres.get(r.nextInt(libres.size()));
                    genNueva.add(new BichitoMitosis(nuevaPos));
                    casillasOcupadas[nuevaPos.x][nuevaPos.y] = true;
                }
            }
        }

        //GUARDAMOS EL NUEVO INSTANTE EN LA HISTORIA
        bichitosTiempo.add(genNueva);
    }

    //esto lo dejo asi temporalmente pero la idea es que devuelva el set completo. Cada instante de tiempo con sus bichitos y sus posiciones.
    public Grid getGrid(){
        return grid;
    }

    //probablemente no se use
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
    
    /**
     * Método auxiliar para buscar las casillas adyacentes (Arriba, Abajo, Izquierda, Derecha)
     * que estén dentro del tablero y que no estén ocupadas.
     */
    private List<Posicion> obtenerAdyacentesLibres(Posicion p, boolean[][] ocupadas, int maxFilas, int maxCols) {
        List<Posicion> libres = new ArrayList<>();
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; 
        
        for (int[] dir : direcciones) {
            int nx = p.x + dir[0];
            int ny = p.y + dir[1];
            
            // Comprobar límites del tablero y si la casilla está vacía
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

}
