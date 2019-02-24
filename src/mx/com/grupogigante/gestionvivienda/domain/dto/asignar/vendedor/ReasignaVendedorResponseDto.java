package mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor;

import java.util.List;

public class ReasignaVendedorResponseDto {
	private List<MotivosDto> listaMotivos;
	private List<VendedorDto> listaVendedor;
	private String eIdUsrCurr;
	private String mensaje;
	private String descripcion;
	private int    index;
	
	
	public ReasignaVendedorResponseDto() {
		super();
		this.index = -1;
	}
	public List<MotivosDto> getListaMotivos() {
		return listaMotivos;
	}
	public void setListaMotivos(List<MotivosDto> listaMotivos) {
		this.listaMotivos = listaMotivos;
	}
	public List<VendedorDto> getListaVendedor() {
		return listaVendedor;
	}
	public void setListaVendedor(List<VendedorDto> listaVendedor) {
		this.listaVendedor = listaVendedor;
	}
	
	public String geteIdUsrCurr() {
		return eIdUsrCurr;
	}
	public void seteIdUsrCurr(String eIdUsrCurr) {
		this.eIdUsrCurr = eIdUsrCurr;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "ReasignaVendedorResponseDto [listaMotivos=" + listaMotivos
				+ ", listaVendedor=" + listaVendedor + ", eIdUsrCurr="
				+ eIdUsrCurr + ", mensaje=" + mensaje + ", descripcion="
				+ descripcion + ", index=" + index + "]";
	}
	
	
}
