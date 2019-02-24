/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012             
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.catalogos;

/**
 * @author WSNADM
 *
 */
public class CriteriosGetCatalogosDto {

	private String i_id_ut_sup;
	private String i_usuario;
	private String id_pais;
	private char i_paises;
	private char i_edo_civil;
	private char i_sexo;
	private char i_tratamientos;
	private char i_regiones;
	private String i_catalogo;
	
	/**
	 * @return the id_pais
	 */
	public String getId_pais() {
		return id_pais;
	}
	/**
	 * @param id_pais the id_pais to set
	 */
	public void setId_pais(String id_pais) {
		this.id_pais = id_pais;
	}
	/**
	 * @return the i_id_ut_sup
	 */
	public String getI_id_ut_sup() {
		return i_id_ut_sup;
	}
	/**
	 * @param i_id_ut_sup the i_id_ut_sup to set
	 */
	public void setI_id_ut_sup(String i_id_ut_sup) {
		this.i_id_ut_sup = i_id_ut_sup;
	}
	/**
	 * @return the i_usuario
	 */
	public String getI_usuario() {
		return i_usuario;
	}
	/**
	 * @param i_usuario the i_usuario to set
	 */
	public void setI_usuario(String i_usuario) {
		this.i_usuario = i_usuario;
	}
	/**
	 * @return the i_paises
	 */
	public char getI_paises() {
		return i_paises;
	}
	/**
	 * @param i_paises the i_paises to set
	 */
	public void setI_paises(char i_paises) {
		this.i_paises = i_paises;
	}
	/**
	 * @return the i_edo_civil
	 */
	public char getI_edo_civil() {
		return i_edo_civil;
	}
	/**
	 * @param i_edo_civil the i_edo_civil to set
	 */
	public void setI_edo_civil(char i_edo_civil) {
		this.i_edo_civil = i_edo_civil;
	}
	/**
	 * @return the i_sexo
	 */
	public char getI_sexo() {
		return i_sexo;
	}
	/**
	 * @param i_sexo the i_sexo to set
	 */
	public void setI_sexo(char i_sexo) {
		this.i_sexo = i_sexo;
	}
	/**
	 * @return the i_tratamientos
	 */
	public char getI_tratamientos() {
		return i_tratamientos;
	}
	/**
	 * @param i_tratamientos the i_tratamientos to set
	 */
	public void setI_tratamientos(char i_tratamientos) {
		this.i_tratamientos = i_tratamientos;
	}
	/**
	 * @return the i_regiones
	 */
	public char getI_regiones() {
		return i_regiones;
	}
	/**
	 * @param i_regiones the i_regiones to set
	 */
	public void setI_regiones(char i_regiones) {
		this.i_regiones = i_regiones;
	}
	public String getI_catalogo() {
		return i_catalogo;
	}
	public void setI_catalogo(String i_catalogo) {
		this.i_catalogo = i_catalogo;
	}

}
