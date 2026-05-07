package main.LogicaNegocio;

import main.ModeloDominio.*;
import java.util.*;

/**
 * Motor principal de la simulación. Gestiona la cuadrícula, la inicialización
 * de criaturas y la evolución temporal del sistema.
 */
public class GridLogic {
    private Grid grid;
    private List<List<BichitoInterface>> bichitosTiempo;
    private int roundsStuck=0;

    /**
     * Inicializa la lógica con una cuadrícula de 10x10 y coloca las criaturas.
     * @param cantidadesIniciales Array con el número de bichitos [Quietos, Moviles, Mitosis].
     */
    public GridLogic(int[] cantidadesIniciales) {
        grid = new Grid(10, 10);
        initialize(cantidadesIniciales[0], cantidadesIniciales[1], cantidadesIniciales[2]);
    }
    
    /**
     * Inicializa el tablero y distribuye aleatoriamente las cantidades especificadas
     * de cada tipo de criatura asegurando que cada una ocupe una posición única.
     * Se crea una lista de todas las posiciones posibles, se baraja aleatoriamente 
     * y se asignan en orden.
     * @param numQuietos Número de criaturas iniciales de tipo Quieto.
     * @param numMoviles Número de criaturas iniciales de tipo Móvil.
     * @param numMitosis Número de criaturas iniciales de tipo Mitosis.
     */
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
    
    /**
     * Ejecuta el paso del tiempo (instante).
     * Aplica reglas de prioridad (fila superior y columna derecha) y procesa:
     * - Movimiento de criaturas (50% de probabilidad).
     * - Mitosis de criaturas (25% de probabilidad).
     * Detecta si el sistema se encuentra en un estado bloqueado (stuck).
     */

    public void step() {
        if (bichitosTiempo.isEmpty()) return;

        List<BichitoInterface> genActual = bichitosTiempo.get(bichitosTiempo.size() - 1);
        List<BichitoInterface> genNueva = new ArrayList<>();

        int filas = grid.getMatrix().length;
        int columnas = grid.getMatrix()[0].length;
        boolean[][] casillasOcupadas = new boolean[filas][columnas];
        Random r = new Random();
        boolean huboActividad = false;

        List<BichitoInterface> bichosOrdenados = new ArrayList<>(genActual);
        bichosOrdenados.sort((b1, b2) -> {
            Posicion p1 = b1.getPosicion();
            Posicion p2 = b2.getPosicion();
            if (p1.x != p2.x) return Integer.compare(p1.x, p2.x);
            return Integer.compare(p2.y, p1.y);
        });

        for (BichitoInterface bicho : bichosOrdenados) {
            if (bicho instanceof BichitoQuieto || bicho instanceof BichitoMitosis) {
                Posicion pos = bicho.getPosicion();
                if (bicho instanceof BichitoQuieto) {
                    genNueva.add(new BichitoQuieto(new Posicion(pos.x, pos.y)));
                } else {
                    genNueva.add(new BichitoMitosis(new Posicion(pos.x, pos.y)));
                }
                casillasOcupadas[pos.x][pos.y] = true;
            }
        }

        for (BichitoInterface bicho : bichosOrdenados) {
            if (bicho instanceof BichitoMovil) {
                Posicion pos = bicho.getPosicion();
                List<Posicion> libres = obtenerAdyacentesLibres(pos, casillasOcupadas, filas, columnas);

                if (!libres.isEmpty() && r.nextInt(2) == 0) {
                    Posicion nuevaPos = libres.get(r.nextInt(libres.size()));
                    genNueva.add(new BichitoMovil(nuevaPos));
                    casillasOcupadas[nuevaPos.x][nuevaPos.y] = true;
                    huboActividad = true;
                } else {
                    if (!casillasOcupadas[pos.x][pos.y]) {
                        genNueva.add(new BichitoMovil(new Posicion(pos.x, pos.y)));
                        casillasOcupadas[pos.x][pos.y] = true;
                    } else {
                        if (!libres.isEmpty()) {
                            Posicion emergencia = libres.get(0);
                            genNueva.add(new BichitoMovil(emergencia));
                            casillasOcupadas[emergencia.x][emergencia.y] = true;
                            huboActividad = true;
                        }
                    }
                }
            }
        }

        for (BichitoInterface bicho : bichosOrdenados) {
            if (bicho instanceof BichitoMitosis) {
                Posicion pos = bicho.getPosicion();
                List<Posicion> libres = obtenerAdyacentesLibres(pos, casillasOcupadas, filas, columnas);

                if (!libres.isEmpty() && r.nextInt(4) == 0) {
                    Posicion nuevaPos = libres.get(r.nextInt(libres.size()));
                    genNueva.add(new BichitoMitosis(nuevaPos));
                    casillasOcupadas[nuevaPos.x][nuevaPos.y] = true;
                    huboActividad = true;
                }
            }
        }

        if (!huboActividad) {
            roundsStuck++;
        } else {
            roundsStuck = 0;
        }

        bichitosTiempo.add(genNueva);
    }
    /**
     * Obtiene el objeto Grid actual asociado a la simulación.
     * @return El tablero (Grid) físico donde se desarrolla la simulación.
     */
    public Grid getGrid(){
        return grid;
    }

    /**
     * Reemplaza el objeto Grid actual por uno nuevo.
     * @param g El nuevo tablero (Grid) que se asignará a la lógica.
     */
    public void setGrid(Grid g){
        grid=g;
    }

    /**
     * Recupera el historial completo de la simulación.
     * @return Una lista de listas, donde cada lista interna representa 
     * el estado de todas las criaturas (BichitoInterface) en un instante de tiempo concreto.
     */
    public List<List<BichitoInterface>> getBichitosTiempo(){
        return bichitosTiempo;
    }

    /**
     * Limpia el estado interno de la simulación.
     * Vacía el historial de criaturas y elimina la matriz de datos del tablero,
     * liberando recursos y dejando la lógica lista para ser reinicializada o destruida.
     */
    public void teardown(){
        bichitosTiempo.clear();
        grid.setMatrix(null);
    }

    /**
     * Obtiene casillas adyacentes (N, S, E, O) que no estén ocupadas y que se 
     * encuentren dentro de los límites físicos del tablero.
     * @param p Posición de origen desde la que se buscan adyacentes.
     * @param ocupadas Matriz de booleanos que indica casillas ya reservadas en este turno.
     * @param maxFilas Número máximo de filas del tablero (límite inferior/superior).
     * @param maxCols Número máximo de columnas del tablero (límite lateral).
     * @return libres Lista de posiciones válidas y libres.
     */
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

    /**
     * Compara esta instancia de GridLogic con otro objeto para determinar si son iguales.
     * Se considera que dos lógicas de simulación son iguales si su historial de 
     * criaturas a lo largo del tiempo (bichitosTiempo) es idéntico.
     * @param o El objeto a comparar.
     * @return true si el objeto es de tipo GridLogic y sus historiales coinciden, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridLogic other)) return false;
        return Objects.equals(this.bichitosTiempo, other.bichitosTiempo);
    }

    /**
     * Indica si la simulación ha terminado por falta de actividad.
     * @return true si el sistema ha estado atascado durante 10 rondas consecutivas.
     */
    public boolean isStuck(){
        return roundsStuck>=10;
    }

}