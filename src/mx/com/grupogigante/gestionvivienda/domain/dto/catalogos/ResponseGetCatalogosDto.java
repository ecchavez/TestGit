/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 19/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto.catalogos;

import java.util.ArrayList;
import java.util.List;


/**
 * Clase que encapsula los fields para la respuesta (listas de consulta de los catalogos)
 * Fecha de creación: XX/06/2012               
 */
public class ResponseGetCatalogosDto {
	
	private String mensaje;
	private String descripcion;
	private List<PaisesDto> paisesList = new ArrayList<PaisesDto>();
	private Object objPaisesList = new Object();
	private List<EdoCivilDto> edoCivilList = new ArrayList<EdoCivilDto>();
	private Object objEdoCivilList = new Object();
	private List<RegionesDto> regionesList = new ArrayList<RegionesDto>();
	private Object objRegionesList = new Object();
	private List<SexoDto> sexoList = new ArrayList<SexoDto>();
	private Object objSexoList = new Object();
	private List<TratamientoDto> tratamientoList = new ArrayList<TratamientoDto>();
	private Object objTratamientoList = new Object();
	private List<CatalogosDto> catalogosList = new ArrayList<CatalogosDto>();
	private Object objCatalogosList = new Object();
	
	private String edoCivilDesc;
	private String sexoDesc;
	private String tratamientoDesc;
	private String regionDesc;
	private String paisDesc;
	private String catalogoDesc;
/**
 * @return the mensaje
 */
public String getMensaje() {
	return mensaje;
}
/**
 * @param mensaje the mensaje to set
 */
public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
}
/**
 * @return the descripcion
 */
public String getDescripcion() {
	return descripcion;
}
/**
 * @param descripcion the descripcion to set
 */
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
/**
 * @return the paisesList
 */
public List<PaisesDto> getPaisesList() {
	return paisesList;
}
/**
 * @param paisesList the paisesList to set
 */
public void setPaisesList(List<PaisesDto> paisesList) {
	this.paisesList = paisesList;
}
/**
 * @return the objPaisesList
 */
public Object getObjPaisesList() {
	return objPaisesList;
}
/**
 * @param objPaisesList the objPaisesList to set
 */
public void setObjPaisesList(Object objPaisesList) {
	this.objPaisesList = objPaisesList;
}
/**
 * @return the edoCivilList
 */
public List<EdoCivilDto> getEdoCivilList() {
	return edoCivilList;
}
/**
 * @param edoCivilList the edoCivilList to set
 */
public void setEdoCivilList(List<EdoCivilDto> edoCivilList) {
	this.edoCivilList = edoCivilList;
}
/**
 * @return the objEdoCivilList
 */
public Object getObjEdoCivilList() {
	return objEdoCivilList;
}
/**
 * @param objEdoCivilList the objEdoCivilList to set
 */
public void setObjEdoCivilList(Object objEdoCivilList) {
	this.objEdoCivilList = objEdoCivilList;
}
/**
 * @return the regionesList
 */
public List<RegionesDto> getRegionesList() {
	return regionesList;
}
/**
 * @param regionesList the regionesList to set
 */
public void setRegionesList(List<RegionesDto> regionesList) {
	this.regionesList = regionesList;
}
/**
 * @return the objRegionesList
 */
public Object getObjRegionesList() {
	return objRegionesList;
}
/**
 * @param objRegionesList the objRegionesList to set
 */
public void setObjRegionesList(Object objRegionesList) {
	this.objRegionesList = objRegionesList;
}
/**
 * @return the sexoList
 */
public List<SexoDto> getSexoList() {
	return sexoList;
}
/**
 * @param sexoList the sexoList to set
 */
public void setSexoList(List<SexoDto> sexoList) {
	this.sexoList = sexoList;
}
/**
 * @return the objSexoList
 */
public Object getObjSexoList() {
	return objSexoList;
}
/**
 * @param objSexoList the objSexoList to set
 */
public void setObjSexoList(Object objSexoList) {
	this.objSexoList = objSexoList;
}
/**
 * @return the tratamientoList
 */
public List<TratamientoDto> getTratamientoList() {
	return tratamientoList;
}
/**
 * @param tratamientoList the tratamientoList to set
 */
public void setTratamientoList(List<TratamientoDto> tratamientoList) {
	this.tratamientoList = tratamientoList;
}
/**
 * @return the objTratamientoList
 */
public Object getObjTratamientoList() {
	return objTratamientoList;
}
/**
 * @param objTratamientoList the objTratamientoList to set
 */
public void setObjTratamientoList(Object objTratamientoList) {
	this.objTratamientoList = objTratamientoList;
}
/**
 * @return the edoCivilDesc
 */
public String getEdoCivilDesc() {
	return edoCivilDesc;
}
/**
 * @param edoCivilDesc the edoCivilDesc to set
 */
public void setEdoCivilDesc(String edoCivilDesc) {
	this.edoCivilDesc = edoCivilDesc;
}
/**
 * @return the sexoDesc
 */
public String getSexoDesc() {
	return sexoDesc;
}
/**
 * @param sexoDesc the sexoDesc to set
 */
public void setSexoDesc(String sexoDesc) {
	this.sexoDesc = sexoDesc;
}
/**
 * @return the tratamientoDesc
 */
public String getTratamientoDesc() {
	return tratamientoDesc;
}
/**
 * @param tratamientoDesc the tratamientoDesc to set
 */
public void setTratamientoDesc(String tratamientoDesc) {
	this.tratamientoDesc = tratamientoDesc;
}
/**
 * @return the regionDesc
 */
public String getRegionDesc() {
	return regionDesc;
}
/**
 * @param regionDesc the regionDesc to set
 */
public void setRegionDesc(String regionDesc) {
	this.regionDesc = regionDesc;
}
/**
 * @return the paisDesc
 */
public String getPaisDesc() {
	return paisDesc;
}
/**
 * @param paisDesc the paisDesc to set
 */
public void setPaisDesc(String paisDesc) {
	this.paisDesc = paisDesc;
}
public String getCatalogoDesc() {
	return catalogoDesc;
}
public void setCatalogoDesc(String catalogoDesc) {
	this.catalogoDesc = catalogoDesc;
}
public List<CatalogosDto> getCatalogosList() {
	return catalogosList;
}
public void setCatalogosList(List<CatalogosDto> catalogosList) {
	this.catalogosList = catalogosList;
}
public Object getObjCatalogosList() {
	return objCatalogosList;
}
public void setObjCatalogosList(Object objCatalogosList) {
	this.objCatalogosList = objCatalogosList;
}

}
