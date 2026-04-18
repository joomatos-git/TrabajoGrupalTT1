package Sistema.API;

public class ConfiguracionDTO {
        private int filas;
        private int columnas;
        private int numQuietos;
        private int numMoviles;
        private int numMitosis;

        // Necesario para Jackson
        public ConfiguracionDTO() {}

        public int getFilas() { return filas; }
        public int getColumnas() { return columnas; }
        public int getNumQuietos() { return numQuietos; }
        public int getNumMoviles() { return numMoviles; }
        public int getNumMitosis() { return numMitosis; }

        public void setFilas(int filas) { this.filas = filas; }
        public void setColumnas(int columnas) { this.columnas = columnas; }
        public void setNumQuietos(int numQuietos) { this.numQuietos = numQuietos; }
        public void setNumMoviles(int numMoviles) { this.numMoviles = numMoviles; }
        public void setNumMitosis(int numMitosis) { this.numMitosis = numMitosis; }
}
