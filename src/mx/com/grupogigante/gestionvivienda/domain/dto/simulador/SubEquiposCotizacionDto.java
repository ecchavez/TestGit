/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 02/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

/**
 * @author osvaldo
 *
 */
public class SubEquiposCotizacionDto {
	String id_cotiz_z;
	int posnr_z;
	String id_equnr;
	String id_equnrx;
	String tipo;
	String netwr;
	String moneda;
	
	public String getId_cotiz_z() {
		return id_cotiz_z;
	}
	public void setId_cotiz_z(String id_cotiz_z) {
		this.id_cotiz_z = id_cotiz_z;
	}
	public int getPosnr_z() {
		return posnr_z;
	}
	public void setPosnr_z(int posnr_z) {
		this.posnr_z = posnr_z;
	}
	public String getId_equnr() {
		return id_equnr;
	}
	public void setId_equnr(String id_equnr) {
		this.id_equnr = id_equnr;
	}
	public String getId_equnrx() {
		return id_equnrx;
	}
	public void setId_equnrx(String id_equnrx) {
		this.id_equnrx = id_equnrx;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNetwr() {
		return netwr;
	}
	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
}
