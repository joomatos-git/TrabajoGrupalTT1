package Sistema.API;

/**
 * Objeto de Transferencia de Datos utilizado para recibir los parámetros 
 * de inicio de una simulación desde el frontend.
 */
public class ConfiguracionDTO {

        /** Array con las cantidades iniciales de cada tipo de criatura. 
         * Se espera el formato: [numQuietos, numMoviles, numMitosis]. */
        private int[] cantidadesIniciales;
        
        /** Array con los nombres que identifican a cada tipo de entidad. */
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