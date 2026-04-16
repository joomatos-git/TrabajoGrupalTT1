package main.ModeloDominio;

public class Grid {
    private final int rows = 10;
    private final int cols = 10;
    private int[][] matrix;


    public int[][] getMatrix(){
        return matrix;
    }

    public void setMatrix(int[][] m){
        matrix=m;
    }

}
