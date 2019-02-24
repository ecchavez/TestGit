package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

public class UsuarioDto {
	private String usuario_cm;
	private String id_ut_sup_cm;
	private String usuario;
	private String id_ut_sup;
	private String nombre1;
	private String nombre2;
	private String app_pat;
	private String app_mat;
	private String pass;
	private String permisos;
	private String estatus;
	private String accion;
	private String permisosStr;
	private String jsonPermisosStr;	
	private String act_usr;
	private String telefono;
	private String extension;
	private String correo;
	private String pathrel;
	
	private List<PermisoUsuariosDto> permisosUserList = new ArrayList<PermisoUsuariosDto>();
	private Object objPermisosUserList = new Object();	
	private ArrayList<Object> objPermisosUserListArray = new ArrayList<Object>();	
	private ArrayList<PermisosDto> catalogoPermisos = new ArrayList<PermisosDto>();
	
	public String getPathrel() {
		return pathrel;
	}
	public void setPathrel(String pathrel) {
		this.pathrel = pathrel;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public ArrayList<PermisosDto> getCatalogoPermisos() {
		return catalogoPermisos;
	}
	public void setCatalogoPermisos(ArrayList<PermisosDto> catalogoPermisos) {
		this.catalogoPermisos = catalogoPermisos;
	}
	public String getJsonPermisosStr() {
		return jsonPermisosStr;
	}
	public void setJsonPermisosStr(String jsonPermisosStr) {
		this.jsonPermisosStr = jsonPermisosStr;
	}
	public List<Object> getObjPermisosUserListArray() {
		return objPermisosUserListArray;
	}
	public void setObjPermisosUserListArray(ArrayList<Object> objPermisosUserListArray) {
		this.objPermisosUserListArray = objPermisosUserListArray;
	}
	public String getUsuario_cm() {
		return usuario_cm;
	}
	public void setUsuario_cm(String usuario_cm) {
		this.usuario_cm = usuario_cm;
	}
	public String getId_ut_sup_cm() {
		return id_ut_sup_cm;
	}
	public void setId_ut_sup_cm(String id_ut_sup_cm) {
		this.id_ut_sup_cm = id_ut_sup_cm;
	}	
	public String getAct_usr() {
		return act_usr;
	}
	public void setAct_usr(String act_usr) {
		this.act_usr = act_usr;
	}
	public String getPermisosStr() {
		return permisosStr;
	}
	public void setPermisosStr(String permisosStr) {
		this.permisosStr = permisosStr;
	}	
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}	
	public Object getObjPermisosUserList() {
		return objPermisosUserList;
	}
	public void setObjPermisosUserList(Object objPermisosUserList) {
		this.objPermisosUserList = objPermisosUserList;
	}
	public List<PermisoUsuariosDto> getPermisosUserList() {
		return permisosUserList;
	}
	public void setPermisosUserList(List<PermisoUsuariosDto> permisosUserList) {
		this.permisosUserList = permisosUserList;
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
	public String getNombre1() {
		return nombre1;
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return nombre2;
	}
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	public String getApp_pat() {
		return app_pat;
	}
	public void setApp_pat(String app_pat) {
		this.app_pat = app_pat;
	}
	public String getApp_mat() {
		return app_mat;
	}
	public void setApp_mat(String app_mat) {
		this.app_mat = app_mat;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getPermisos() {
		return permisos;
	}
	public void setPermisos(String permisos) {
		this.permisos = permisos;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}
