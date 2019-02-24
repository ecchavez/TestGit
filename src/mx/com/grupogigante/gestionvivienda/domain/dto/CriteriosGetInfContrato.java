/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class CriteriosGetInfContrato {
	private String id_ut_sup;
	private String usuario;
	private int accion;
	private String idPedido;
	private String fregi;
	
	/**
	 * @return the id_ut_sup
	 */
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	/**
	 * @param id_ut_sup the id_ut_sup to set
	 */
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the accion
	 */
	public int getAccion() {
		return accion;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(int accion) {
		this.accion = accion;
	}
	/**
	 * @return the idPedido
	 */
	public String getIdPedido() {
		return idPedido;
	}
	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}
	/**
	 * @return the fregi
	 */
	public String getFregi() {
		return fregi;
	}
	/**
	 * @param fregi the fregi to set
	 */
	public void setFregi(String fregi) {
		this.fregi = fregi;
	}
}
