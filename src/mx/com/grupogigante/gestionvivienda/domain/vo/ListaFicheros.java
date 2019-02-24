package mx.com.grupogigante.gestionvivienda.domain.vo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class ListaFicheros {
	
	private List<MultipartFile> archivos;
	private ArrayList<String> zona;
	private ArrayList<String> vicio;
	private ArrayList<String> descripcion;
	private String accion;
	private String idArea;
	private String idFase;
	private String idEquipo;
	private String cliente;
	private String correo;
	private String observaciones;
	private String asunto;
	private String prioridad;
	private String usuario;
	private String id_ut_sup;
	private String adjunto;
	private String ccp;
	
	private ArrayList<String> categoria;
	private ArrayList<String> subcategoria;
	private ArrayList<String> textoabierto;

	public String getCcp() {
		return ccp;
	}
	public void setCcp(String ccp) {
		this.ccp = ccp;
	}
	public List<MultipartFile> getArchivos() {
		return archivos;
	}
	public void setArchivos(List<MultipartFile> archivos) {
		this.archivos = archivos;
	}
	public ArrayList<String> getZona() {
		return zona;
	}
	public void setZona(ArrayList<String> zona) {
		this.zona = zona;
	}
	public ArrayList<String> getVicio() {
		return vicio;
	}
	public void setVicio(ArrayList<String> vicio) {
		this.vicio = vicio;
	}
	public String getIdFase() {
		return idFase;
	}
	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}
	public String getIdEquipo() {
		return idEquipo;
	}
	public void setIdEquipo(String idEquipo) {
		this.idEquipo = idEquipo;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
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
	public String getIdArea() {
		return idArea;
	}
	public void setIdArea(String idArea) {
		this.idArea = idArea;
	}
	public ArrayList<String> getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(ArrayList<String> descripcion) {
		this.descripcion = descripcion;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}
	/*public ArrayList<String> getCategoria() {
		return categoria;
	}
	public void setCategoria(ArrayList<String> categoria) {
		this.categoria = categoria;
	}
	public ArrayList<String> getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(ArrayList<String> subcategoria) {
		this.subcategoria = subcategoria;
	}
	public ArrayList<String> getTextoabierto() {
		return textoabierto;
	}
	public void setTextoabierto(ArrayList<String> textoabierto) {
		this.textoabierto = textoabierto;
	}*/
	public ArrayList<String> getCategoria() {
		return categoria;
	}
	public void setCategoria(ArrayList<String> categoria) {
		this.categoria = categoria;
	}
	public ArrayList<String> getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(ArrayList<String> subcategoria) {
		this.subcategoria = subcategoria;
	}
	public ArrayList<String> getTextoabierto() {
		return textoabierto;
	}
	public void setTextoabierto(ArrayList<String> textoabierto) {
		this.textoabierto = textoabierto;
	}

}
