package mx.com.grupogigante.gestionvivienda.domain.dto.simulador;

public class CriteriosCotizadorImprimeDto {	
	String tipo;
	String desc1;
	String precio;
	String equipo;
	String numero;
	String desc2;
	String fecha;
	String costo;
	String opc1;
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDesc1() {
		return desc1;
	}
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDesc2() {
		return desc2;
	}
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCosto() {
		return costo;
	}
	public void setCosto(String costo) {
		this.costo = costo;
	}
	public String getOpc1() {
		return opc1;
	}
	public void setOpc1(String opc1) {
		this.opc1 = opc1;
	}
	@Override
	public String toString() {
		return "CriteriosCotizadorImprimeDto [tipo=" + tipo + ", desc1="
				+ desc1 + ", precio=" + precio + ", equipo=" + equipo
				+ ", numero=" + numero + ", desc2=" + desc2 + ", fecha="
				+ fecha + ", costo=" + costo + ", opc1=" + opc1 + "]";
	}
	
}
