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

        /**
         * Constructor sin parámetros necesario para la correcta inicialización y serialización JSON.
         */
        public ConfiguracionDTO() {}

        /**
         * Obtiene las cantidades iniciales requeridas para cada tipo de entidad.
         * @return Array de enteros con las cantidades.
         */
        public int[] getCantidadesIniciales() { return cantidadesIniciales; }
        
        /**
         * Obtiene los identificadores o nombres de las entidades a simular.
         * @return Array de Strings con los nombres de las entidades.
         */
        public String[] getNombreEntidades() { return nombreEntidades; }

        /**
         * Establece las cantidades iniciales requeridas para cada tipo de entidad.
         * @param cantidadesIniciales Array de enteros con las cantidades.
         */
        public void setCantidadesIniciales(int[] cantidadesIniciales) {
                this.cantidadesIniciales = cantidadesIniciales;
        }
        
        /**
         * Establece los identificadores o nombres de las entidades a simular.
         * @param nombreEntidades Array de Strings con los nombres de las entidades.
         */
        public void setNombreEntidades(String[] nombreEntidades) {
                this.nombreEntidades = nombreEntidades;
        }
}