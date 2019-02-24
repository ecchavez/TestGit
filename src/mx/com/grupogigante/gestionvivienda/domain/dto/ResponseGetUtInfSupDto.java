/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.report.referencias.dto.ReferenciasBancoDto;
/**
 * Clase que encapsula los fields para la respuesta de las listas de consulta para Inf UT
 * Fecha de creación: XX/06/2012               
 */
public class ResponseGetUtInfSupDto {

	private List<UbicacionTecnicaDto> ubicacionesList = new ArrayList<UbicacionTecnicaDto>();
	private Object objUbicacionesList = new Object();
	private List<UbicacionTecnicaDto> pisosList = new ArrayList<UbicacionTecnicaDto>();
	private Object objPisosList = new Object();
	private List<EquipoDto> equiposList = new ArrayList<EquipoDto>();
	private Object objEquiposList = new Object();
	private List<SubequipoDto> subequiposList = new ArrayList<SubequipoDto>();
	private Object objSubequiposList = new Object();
	private List<EqCaracteristicasDto> EqCaracteristicasList = new ArrayList<EqCaracteristicasDto>();
	private Object objEqCaracteristicasList = new Object();
	private List<UTCaracteristicasDto> UtCaracterisiticasList = new ArrayList<UTCaracteristicasDto>();
	private Object objUtCaracterisiticasList = new Object();
	private List<EqPriceDto> EqPriceList = new ArrayList<EqPriceDto>();
	private Object objEqPriceList = new Object();
	public ArrayList<CoordenadasDto> listCoordenadas = new ArrayList<CoordenadasDto>();
	private Object objCoordenadasList = new Object();
	private ArrayList<CoordenadasDto> listFiltradoCoordenadas = new ArrayList<CoordenadasDto>();
	private Object objFiltradoCoordenadasList = new Object();	
	private String mensaje;
	private String descripcion;
	private ArrayList<UbicacionTecnicaDto> ubicacionesDetalleList = new ArrayList<UbicacionTecnicaDto>();
	private int maxep_piso=0;
	private ArrayList<CarpetasDigitDto> carpetasDigitalizacion = new ArrayList<CarpetasDigitDto>();
	private ArrayList<CarpetasTicketDto> carpetasTicket = new ArrayList<CarpetasTicketDto>();
	private ArrayList<CarpetasDigitEstatusDto> estatusDigitalizacion = new ArrayList<CarpetasDigitEstatusDto>();
	private List<EstatusFiltroReporteDto> estatus;
	private List<ReferenciasBancoDto> bancos;
	
	
	public ArrayList<CarpetasTicketDto> getCarpetasTicket() {
		return carpetasTicket;
	}
	public void setCarpetasTicket(ArrayList<CarpetasTicketDto> carpetasTicket) {
		this.carpetasTicket = carpetasTicket;
	}
	public ArrayList<CoordenadasDto> getListCoordenadas() {
		return listCoordenadas;
	}
	public void setListCoordenadas(ArrayList<CoordenadasDto> listCoordenadas) {
		this.listCoordenadas = listCoordenadas;
	}
	public ArrayList<CarpetasDigitEstatusDto> getEstatusDigitalizacion() {
		return estatusDigitalizacion;
	}
	public void setEstatusDigitalizacion(
			ArrayList<CarpetasDigitEstatusDto> estatusDigitalizacion) {
		this.estatusDigitalizacion = estatusDigitalizacion;
	}
	/**
	 * @return the carpetasDigitalizacion
	 */
	public ArrayList<CarpetasDigitDto> getCarpetasDigitalizacion() {
		return carpetasDigitalizacion;
	}
	/**
	 * @param carpetasDigitalizacion the carpetasDigitalizacion to set
	 */
	public void setCarpetasDigitalizacion(
			ArrayList<CarpetasDigitDto> carpetasDigitalizacion) {
		this.carpetasDigitalizacion = carpetasDigitalizacion;
	}
	public List<UbicacionTecnicaDto> getPisosList() {
		return pisosList;
	}
	public void setPisosList(List<UbicacionTecnicaDto> pisosList) {
		this.pisosList = pisosList;
	}
	public Object getObjPisosList() {
		return objPisosList;
	}
	public void setObjPisosList(Object objPisosList) {
		this.objPisosList = objPisosList;
	}
	public ArrayList<CoordenadasDto> getListFiltradoCoordenadas() {
		return listFiltradoCoordenadas;
	}
	public void setListFiltradoCoordenadas(
			ArrayList<CoordenadasDto> listFiltradoCoordenadas) {
		this.listFiltradoCoordenadas = listFiltradoCoordenadas;
	}
	public Object getObjFiltradoCoordenadasList() {
		return objFiltradoCoordenadasList;
	}
	public void setObjFiltradoCoordenadasList(Object objFiltradoCoordenadasList) {
		this.objFiltradoCoordenadasList = objFiltradoCoordenadasList;
	}
	public ArrayList<CoordenadasDto> getCoordenadasList() {
		return listCoordenadas;
	}
	public void setCoordenadasList(ArrayList<CoordenadasDto> coordenadasList) {
		listCoordenadas = coordenadasList;
	}
	public Object getObjCoordenadasList() {
		return objCoordenadasList;
	}
	public void setObjCoordenadasList(Object objCoordenadasList) {
		this.objCoordenadasList = objCoordenadasList;
	}
	public int getMaxep_piso() {
		return maxep_piso;
	}
	public void setMaxep_piso(int maxep_piso) {
		this.maxep_piso = maxep_piso;
	}
	public ArrayList<UbicacionTecnicaDto> getUbicacionesDetalleList() {
		return ubicacionesDetalleList;
	}
	public void setUbicacionesDetalleList(
			ArrayList<UbicacionTecnicaDto> ubicacionesDetalleList) {
		this.ubicacionesDetalleList = ubicacionesDetalleList;
	}
	/**
	 * @return the ubicacionesList
	 */
	public List<UbicacionTecnicaDto> getUbicacionesList() {
		return ubicacionesList;
	}
	/**
	 * @param ubicacionesList the ubicacionesList to set
	 */
	public void setUbicacionesList(List<UbicacionTecnicaDto> ubicacionesList) {
		this.ubicacionesList = ubicacionesList;
	}
	/**
	 * @return the objUbicacionesList
	 */
	public Object getObjUbicacionesList() {
		return objUbicacionesList;
	}
	/**
	 * @param objUbicacionesList the objUbicacionesList to set
	 */
	public void setObjUbicacionesList(Object objUbicacionesList) {
		this.objUbicacionesList = objUbicacionesList;
	}
	/**
	 * @return the equiposList
	 */
	public List<EquipoDto> getEquiposList() {
		return equiposList;
	}
	/**
	 * @param equiposList the equiposList to set
	 */
	public void setEquiposList(List<EquipoDto> equiposList) {
		this.equiposList = equiposList;
	}
	/**
	 * @return the objEquiposList
	 */
	public Object getObjEquiposList() {
		return objEquiposList;
	}
	/**
	 * @param objEquiposList the objEquiposList to set
	 */
	public void setObjEquiposList(Object objEquiposList) {
		this.objEquiposList = objEquiposList;
	}
	/**
	 * @return the subequiposList
	 */
	public List<SubequipoDto> getSubequiposList() {
		return subequiposList;
	}
	/**
	 * @param subequiposList the subequiposList to set
	 */
	public void setSubequiposList(List<SubequipoDto> subequiposList) {
		this.subequiposList = subequiposList;
	}
	/**
	 * @return the objSubequiposList
	 */
	public Object getObjSubequiposList() {
		return objSubequiposList;
	}
	/**
	 * @param objSubequiposList the objSubequiposList to set
	 */
	public void setObjSubequiposList(Object objSubequiposList) {
		this.objSubequiposList = objSubequiposList;
	}
	/**
	 * @return the eqCaracteristicasList
	 */
	public List<EqCaracteristicasDto> getEqCaracteristicasList() {
		return EqCaracteristicasList;
	}
	/**
	 * @param eqCaracteristicasList the eqCaracteristicasList to set
	 */
	public void setEqCaracteristicasList(
			List<EqCaracteristicasDto> eqCaracteristicasList) {
		EqCaracteristicasList = eqCaracteristicasList;
	}
	/**
	 * @return the objEqCaracteristicasList
	 */
	public Object getObjEqCaracteristicasList() {
		return objEqCaracteristicasList;
	}
	/**
	 * @param objEqCaracteristicasList the objEqCaracteristicasList to set
	 */
	public void setObjEqCaracteristicasList(Object objEqCaracteristicasList) {
		this.objEqCaracteristicasList = objEqCaracteristicasList;
	}
	/**
	 * @return the utCaracterisiticasList
	 */
	public List<UTCaracteristicasDto> getUtCaracterisiticasList() {
		return UtCaracterisiticasList;
	}
	/**
	 * @param utCaracterisiticasList the utCaracterisiticasList to set
	 */
	public void setUtCaracterisiticasList(
			List<UTCaracteristicasDto> utCaracterisiticasList) {
		UtCaracterisiticasList = utCaracterisiticasList;
	}
	/**
	 * @return the objUtCaracterisiticasList
	 */
	public Object getObjUtCaracterisiticasList() {
		return objUtCaracterisiticasList;
	}
	/**
	 * @param objUtCaracterisiticasList the objUtCaracterisiticasList to set
	 */
	public void setObjUtCaracterisiticasList(Object objUtCaracterisiticasList) {
		this.objUtCaracterisiticasList = objUtCaracterisiticasList;
	}
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
	 * @return the eqPriceList
	 */
	public List<EqPriceDto> getEqPriceList() {
		return EqPriceList;
	}
	/**
	 * @param eqPriceList the eqPriceList to set
	 */
	public void setEqPriceList(List<EqPriceDto> eqPriceList) {
		EqPriceList = eqPriceList;
	}
	/**
	 * @return the objEqPriceList
	 */
	public Object getObjEqPriceList() {
		return objEqPriceList;
	}
	/**
	 * @param objEqPriceList the objEqPriceList to set
	 */
	public void setObjEqPriceList(Object objEqPriceList) {
		this.objEqPriceList = objEqPriceList;
	}
	public List<EstatusFiltroReporteDto> getEstatus() {
		return estatus;
	}
	public void setEstatus(List<EstatusFiltroReporteDto> estatus) {
		this.estatus = estatus;
	}
	public List<ReferenciasBancoDto> getBancos() {
		return bancos;
	}
	public void setBancos(List<ReferenciasBancoDto> bancos) {
		this.bancos = bancos;
	}

	
	}
