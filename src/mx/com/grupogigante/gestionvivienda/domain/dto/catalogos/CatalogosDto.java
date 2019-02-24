/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.catalogos;

/**
 * Clase que encapsula los fields de Paises
 * Fecha de creación: 19/07/2012               
 */
public class CatalogosDto {
	
	private String id_cat;
	private String id_val;
	private String valor;
	private String val_02;
	
	public String getId_cat() {
		return id_cat;
	}
	public void setId_cat(String id_cat) {
		this.id_cat = id_cat;
	}
	public String getId_val() {
		return id_val;
	}
	public void setId_val(String id_val) {
		this.id_val = id_val;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getVal_02() {
		return val_02;
	}
	public void setVal_02(String val_02) {
		this.val_02 = val_02;
	}	
	
	
}
