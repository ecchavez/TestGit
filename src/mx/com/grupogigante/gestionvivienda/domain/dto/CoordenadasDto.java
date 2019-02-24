package mx.com.grupogigante.gestionvivienda.domain.dto;

public class CoordenadasDto {
	String uteq;
	String coord;
	String estatus;
	String i_id_ut_current;
	String i_id_ut;
	
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getI_id_ut_current() {
		return i_id_ut_current;
	}
	public void setI_id_ut_current(String i_id_ut_current) {
		this.i_id_ut_current = i_id_ut_current;
	}
	public String getI_id_ut() {
		return i_id_ut;
	}
	public void setI_id_ut(String i_id_ut) {
		this.i_id_ut = i_id_ut;
	}
	public String getUteq() {
		return uteq;
	}
	public void setUteq(String uteq) {
		this.uteq = uteq;
	}
	public String getCoord() {
		return coord;
	}
	public void setCoord(String coord) {
		this.coord = coord;
	}
	
}
