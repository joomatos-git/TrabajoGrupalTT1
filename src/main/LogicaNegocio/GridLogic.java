package main.LogicaNegocio;

import main.ModeloDominio.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GridLogic {
    Grid grid;
    List<List<BichitoInterface>> bichitosTiempo = new ArrayList<List<BichitoInterface>>();

    public GridLogic(){

    }

    public void initialize(){
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
        bichitosTiempo.add(new ArrayList<>());
        Random r = new Random(seed);
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

    //para avanzar "turnos"
    public void step(){}

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
}
