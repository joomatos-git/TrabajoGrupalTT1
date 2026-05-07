package main.ModeloDominio;

import java.util.Arrays;

/**
 * Representa la estructura física del tablero como una matriz de enteros.
 */
public class Grid {
    
    private int[][] matrix;
    
    /**
     * Constructor que define las dimensiones del tablero.
     * @param row Número de filas.
     * @param col Número de columnas.
     */
    public Grid(int row, int col) {
        this.matrix = new int[row][col];
    }
    
    /**
     * Obtiene la matriz representativa del estado actual del tablero.
     * @return La matriz de datos del tablero. 
     */
    public int[][] getMatrix(){
        return matrix;
    }

    /**
     * Sustituye la matriz actual del tablero por una nueva.
     * @param m Nueva matriz bidimensional de enteros que representará el estado del tablero.
     */
    public void setMatrix(int[][] m){
        matrix=m;
    }

    /**
     * Compara este tablero con otro objeto para determinar si son iguales.
     * Dos tableros se consideran iguales si sus matrices internas tienen 
     * exactamente las mismas dimensiones y los mismos valores en cada celda (deep equals).
     * @param o Objeto a comparar con este tablero.
     * @return true si el objeto es de tipo Grid y su matriz de datos coincide, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid other)) return false;
        return Arrays.deepEquals(this.matrix, other.matrix);
    }

}
