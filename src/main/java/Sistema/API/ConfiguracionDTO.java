package Sistema.API;

public class ConfiguracionDTO {

        private int[] cantidadesIniciales;
        private String[] nombreEntidades;

        public ConfiguracionDTO() {}

        public int[] getCantidadesIniciales() { return cantidadesIniciales; }
        public String[] getNombreEntidades() { return nombreEntidades; }

        public void setCantidadesIniciales(int[] cantidadesIniciales) {
                this.cantidadesIniciales = cantidadesIniciales;
        }
        public void setNombreEntidades(String[] nombreEntidades) {
                this.nombreEntidades = nombreEntidades;
        }
}