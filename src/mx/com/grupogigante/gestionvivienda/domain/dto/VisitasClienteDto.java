/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 24/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.sql.Time;
import java.util.Date;

/**
 * Clase que encapsula los fields para la respuesta (listas de consulta )
 * Fecha de creación: 24/07/2012               
 */
public class VisitasClienteDto {
	    private String id_cliente;
	    private String id_vendedor;
	    private String fecha_visita;
	    
	    
		/**
		 * @return the id_cliente
		 */
		public String getId_cliente() {
			return id_cliente;
		}
		/**
		 * @param id_cliente the id_cliente to set
		 */
		public void setId_cliente(String id_cliente) {
			this.id_cliente = id_cliente;
		}
		/**
		 * @return the id_vendedor
		 */
		public String getId_vendedor() {
			return id_vendedor;
		}
		/**
		 * @param id_vendedor the id_vendedor to set
		 */
		public void setId_vendedor(String id_vendedor) {
			this.id_vendedor = id_vendedor;
		}
		/**
		 * @return the fecha_visita
		 */
		public String getFecha_visita() {
			return fecha_visita;
		}
		/**
		 * @param fecha_visita the fecha_visita to set
		 */
		public void setFecha_visita(String fecha_visita) {
			this.fecha_visita = fecha_visita;
		}
	
	    
	
}
