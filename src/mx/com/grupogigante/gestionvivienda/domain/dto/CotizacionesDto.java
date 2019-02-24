/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: XX/06/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import mx.com.grupogigante.gestionvivienda.domain.dto.simulador.CotizadorSubequiposDto;

/**
 * Clase que encapsula los fields de la Informacion de las Cotizaciones 
 * Fecha de creación: 19/09/2012               
 */
public class CotizacionesDto {
	
private String idCotizacion;
private String idPedido;
private String inicioVig;
private String finVig;
private String pedido;
private String dptoCasa;
private String dpto;
private String idSapCliente;
private String idCarCliente;
private String idCliente;
private String spaso;
private String spasox;
private String npaso;
private String npasox;
private String mje;

//datos para la consulta de la cartera del cliente
private String nombre;
private String sNombre;
private String aApat;
private String aMat;
private String fchNac;
private String mail;
private String telfn;
private String tlfmo;
private String telof;
private String extnc;
private String mail2;
private int via_con;
private String via_conx;
private String vendedor;
private ArrayList<CotizadorSubequiposDto> cotizacionSubequipos = new ArrayList<CotizadorSubequiposDto>();
private String idCotizacionZ;
private String stContra;

/**
 * @return the idCotizacion
 */
public String getIdCotizacion() {
	return idCotizacion;
}
/**
 * @param idCotizacion the idCotizacion to set
 */
public void setIdCotizacion(String idCotizacion) {
	this.idCotizacion = idCotizacion;
}
/**
 * @return the idPedido
 */
public String getIdPedido() {
	return idPedido;
}
/**
 * @param idPedido the idPedido to set
 */
public void setIdPedido(String idPedido) {
	this.idPedido = idPedido;
}
/**
 * @return the inicioVig
 */
public String getInicioVig() {
	return inicioVig;
}
/**
 * @param inicioVig the inicioVig to set
 */
public void setInicioVig(String inicioVig) {
	this.inicioVig = inicioVig;
}
/**
 * @return the finVig
 */
public String getFinVig() {
	return finVig;
}
/**
 * @param finVig the finVig to set
 */
public void setFinVig(String finVig) {
	this.finVig = finVig;
}
/**
 * @return the pedido
 */
public String getPedido() {
	return pedido;
}
/**
 * @param pedido the pedido to set
 */
public void setPedido(String pedido) {
	this.pedido = pedido;
}
/**
 * @return the dptoCasa
 */
public String getDptoCasa() {
	return dptoCasa;
}
/**
 * @param dptoCasa the dptoCasa to set
 */
public void setDptoCasa(String dptoCasa) {
	this.dptoCasa = dptoCasa;
}
/**
 * @return the idSapCliente
 */
public String getIdSapCliente() {
	return idSapCliente;
}
/**
 * @param idSapCliente the idSapCliente to set
 */
public void setIdSapCliente(String idSapCliente) {
	this.idSapCliente = idSapCliente;
}
/**
 * @return the idCarCliente
 */
public String getIdCarCliente() {
	return idCarCliente;
}
/**
 * @param idCarCliente the idCarCliente to set
 */
public void setIdCarCliente(String idCarCliente) {
	this.idCarCliente = idCarCliente;
}
/**
 * @return the dpto
 */
public String getDpto() {
	return dpto;
}
/**
 * @param dpto the dpto to set
 */
public void setDpto(String dpto) {
	this.dpto = dpto;
}
/**
 * @return the nombre
 */
public String getNombre() {
	return nombre;
}
/**
 * @param nombre the nombre to set
 */
public void setNombre(String nombre) {
	this.nombre = nombre;
}
/**
 * @return the aApat
 */
public String getAApat() {
	return aApat;
}
/**
 * @param apat the aApat to set
 */
public void setAApat(String apat) {
	aApat = apat;
}
/**
 * @return the aMat
 */
public String getAMat() {
	return aMat;
}
/**
 * @param mat the aMat to set
 */
public void setAMat(String mat) {
	aMat = mat;
}
/**
 * @return the fchNac
 */
public String getFchNac() {
	return fchNac;
}
/**
 * @param fchNac the fchNac to set
 */
public void setFchNac(String fchNac) {
	this.fchNac = fchNac;
}
/**
 * @return the spaso
 */
public String getSpaso() {
	return spaso;
}
/**
 * @param spaso the spaso to set
 */
public void setSpaso(String spaso) {
	this.spaso = spaso;
}
/**
 * @return the spasox
 */
public String getSpasox() {
	return spasox;
}
/**
 * @param spasox the spasox to set
 */
public void setSpasox(String spasox) {
	this.spasox = spasox;
}
/**
 * @return the npaso
 */
public String getNpaso() {
	return npaso;
}
/**
 * @param npaso the npaso to set
 */
public void setNpaso(String npaso) {
	this.npaso = npaso;
}
/**
 * @return the npasox
 */
public String getNpasox() {
	return npasox;
}
/**
 * @param npasox the npasox to set
 */
public void setNpasox(String npasox) {
	this.npasox = npasox;
}
/**
 * @return the mje
 */
public String getMje() {
	return mje;
}
/**
 * @param mje the mje to set
 */
public void setMje(String mje) {
	this.mje = mje;
}
/**
 * @return the idCliente
 */
public String getIdCliente() {
	return idCliente;
}
/**
 * @param idCliente the idCliente to set
 */
public void setIdCliente(String idCliente) {
	this.idCliente = idCliente;
}
/**
 * @return the sNombre
 */
public String getSNombre() {
	return sNombre;
}
/**
 * @param nombre the sNombre to set
 */
public void setSNombre(String nombre) {
	sNombre = nombre;
}
/**
 * @return the mail
 */
public String getMail() {
	return mail;
}
/**
 * @param mail the mail to set
 */
public void setMail(String mail) {
	this.mail = mail;
}
/**
 * @return the telfn
 */
public String getTelfn() {
	return telfn;
}
/**
 * @param telfn the telfn to set
 */
public void setTelfn(String telfn) {
	this.telfn = telfn;
}
/**
 * @return the tlfmo
 */
public String getTlfmo() {
	return tlfmo;
}
/**
 * @param tlfmo the tlfmo to set
 */
public void setTlfmo(String tlfmo) {
	this.tlfmo = tlfmo;
}
/**
 * @return the telof
 */
public String getTelof() {
	return telof;
}
/**
 * @param telof the telof to set
 */
public void setTelof(String telof) {
	this.telof = telof;
}
/**
 * @return the extnc
 */
public String getExtnc() {
	return extnc;
}
/**
 * @param extnc the extnc to set
 */
public void setExtnc(String extnc) {
	this.extnc = extnc;
}
/**
 * @return the mail2
 */
public String getMail2() {
	return mail2;
}
/**
 * @param mail2 the mail2 to set
 */
public void setMail2(String mail2) {
	this.mail2 = mail2;
}
/**
 * @return the via_conx
 */
public String getVia_conx() {
	return via_conx;
}
/**
 * @param via_conx the via_conx to set
 */
public void setVia_conx(String via_conx) {
	this.via_conx = via_conx;
}
/**
 * @return the vendedor
 */
public String getVendedor() {
	return vendedor;
}
/**
 * @param vendedor the vendedor to set
 */
public void setVendedor(String vendedor) {
	this.vendedor = vendedor;
}
/**
 * @return the via_con
 */
public int getVia_con() {
	return via_con;
}
/**
 * @param via_con the via_con to set
 */
public void setVia_con(int via_con) {
	this.via_con = via_con;
}
public String getsNombre() {
	return sNombre;
}
public void setsNombre(String sNombre) {
	this.sNombre = sNombre;
}
public String getaApat() {
	return aApat;
}
public void setaApat(String aApat) {
	this.aApat = aApat;
}
public String getaMat() {
	return aMat;
}
public void setaMat(String aMat) {
	this.aMat = aMat;
}
public ArrayList<CotizadorSubequiposDto> getCotizacionSubequipos() {
	return cotizacionSubequipos;
}
public void setCotizacionSubequipos(
		ArrayList<CotizadorSubequiposDto> cotizacionSubequipos) {
	this.cotizacionSubequipos = cotizacionSubequipos;
}
public String getIdCotizacionZ() {
	return idCotizacionZ;
}
public void setIdCotizacionZ(String idCotizacionZ) {
	this.idCotizacionZ = idCotizacionZ;
}
public String getStContra() {
	return stContra;
}
public void setStContra(String stContra) {
	this.stContra = stContra;
}
	
}