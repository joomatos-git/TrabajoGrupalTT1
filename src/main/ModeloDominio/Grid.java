package main.ModeloDominio;

public class Grid {
    private final int rows = 10;
    private final int cols = 10;
    private int[][] matrix;

    public Grid(){
        matrix = new Object[rows][cols];
    }

    public Object[][] getMatrix(){
        return matrix;
    }

    public void setMatrix(Object[][] m){
        matrix=m;
    }

}
