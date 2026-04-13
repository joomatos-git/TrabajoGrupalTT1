package Sistema.API;


public class ConfiguracionDTO {
        public int filas;
        public int columnas;
        public int numQuietos;
        public int numMoviles;
        public int numMitosis;

        // tenemos que tener getters y setters Jackson funcione
        public int getFilas() { return filas; }
        public int getColumnas() { return columnas; }
        public int getNumQuietos() { return numQuietos; }
        public int getNumMoviles() { return numMoviles; }
        public int getNumMitosis() { return numMitosis; }
}
