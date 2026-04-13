package main.LogicaNegocio;

import main.ModeloDominio.BichitoMitosis;
import main.ModeloDominio.BichitoMovil;
import main.ModeloDominio.BichitoQuieto;
import main.ModeloDominio.Grid;

import java.util.Random;

public class GridLogic {
    Grid grid;

    public GridLogic(){

    }

    public void initialize(){
        Random r = new Random();
        grid = new Grid();
        Object[][] gridMatrix = grid.getMatrix();
        for(Object o: gridMatrix){
            if(r.nextInt(6)==0){
                switch(r.nextInt(3)){
                    case 0:
                        o = new BichitoMitosis();
                        break;
                    case 1:
                        o = new BichitoMovil();
                        break;
                    case 2:
                        o = new BichitoQuieto();
                        break;

                }


            }
        }
    }

    public void initialize(int seed){
        Random r = new Random(seed);

    }


    public Grid getGrid(){
        return grid;
    }

    public void setGrid(Grid g){
        grid=g;
    }

    public void teardown(){

    }
}
