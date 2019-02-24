package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class CriteriosClientesDto {
	String usuario;
	String id_ut_sup;
	String fecha_vis_b;
	String fecha_vis_e;
	String strListaVendedores;
	String accion;
	
	private ArrayList<UsuarioDto> listaVendedores = new ArrayList<UsuarioDto>();
	private Object objListaVendedores = new Object();
	
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getId_ut_sup() {
		return id_ut_sup;
	}
	public void setId_ut_sup(String id_ut_sup) {
		this.id_ut_sup = id_ut_sup;
	}
	public String getFecha_vis_b() {
		return fecha_vis_b;
	}
	public void setFecha_vis_b(String fecha_vis_b) {
		this.fecha_vis_b = fecha_vis_b;
	}
	public String getFecha_vis_e() {
		return fecha_vis_e;
	}
	public void setFecha_vis_e(String fecha_vis_e) {
		this.fecha_vis_e = fecha_vis_e;
	}
	public String getStrListaVendedores() {
		return strListaVendedores;
	}
	public void setStrListaVendedores(String strListaVendedores) {
		this.strListaVendedores = strListaVendedores;
	}
	public ArrayList<UsuarioDto> getListaVendedores() {
		return listaVendedores;
	}
	public void setListaVendedores(ArrayList<UsuarioDto> listaVendedores) {
		this.listaVendedores = listaVendedores;
	}
	public Object getObjListaVendedores() {
		return objListaVendedores;
	}
	public void setObjListaVendedores(Object objListaVendedores) {
		this.objListaVendedores = objListaVendedores;
	}
}
