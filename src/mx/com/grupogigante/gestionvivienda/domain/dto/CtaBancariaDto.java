/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class CtaBancariaDto {

	private String banco;
	private String tipo_cuenta;
	private String clabe;
	private String referencia;
	private String ciudad;
	private String cta;
	
	/**
	 * @return the banco
	 */
	public String getBanco() {
		return banco;
	}
	/**
	 * @param banco the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}
	/**
	 * @return the tipo_cuenta
	 */
	public String getTipo_cuenta() {
		return tipo_cuenta;
	}
	/**
	 * @param tipo_cuenta the tipo_cuenta to set
	 */
	public void setTipo_cuenta(String tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}
	/**
	 * @return the clabe
	 */
	public String getClabe() {
		return clabe;
	}
	/**
	 * @param clabe the clabe to set
	 */
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the cta
	 */
	public String getCta() {
		return cta;
	}
	/**
	 * @param cta the cta to set
	 */
	public void setCta(String cta) {
		this.cta = cta;
	}
	@Override
	public String toString() {
		return "CtaBancariaDto [banco=" + banco + ", tipo_cuenta="
				+ tipo_cuenta + ", clabe=" + clabe + ", referencia="
				+ referencia + ", ciudad=" + ciudad + ", cta=" + cta + "]";
	}
	
	
}
