package main.ModeloDominio;

import java.util.Arrays;

public class Grid {
    private int[][] matrix;
    public Grid(int row, int col) {
        this.matrix = new int[row][col];
    }
    public int[][] getMatrix(){
        return matrix;
    }

    public void setMatrix(int[][] m){
        matrix=m;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid other)) return false;
        return Arrays.deepEquals(this.matrix, other.matrix);
    }

}
