package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

import java.util.List;

public class EstadoCuentaReporteLayout {
	private String idCliente;
	private String nombre;
	private String segundo;
	private String appaterno;
	private String apmaterno;
	private String desarrollo;
	private String fase;
	private String casadepto;
	private String pedido;
	private String referencia;
	private String montoCouta;
	private String msgCouta;
	
	private String casa;
	private String unidaTecnicaDesc;
	
	private String calle;
	private String noext;
	private String noint;
	private String coln;
	private String cdpst;
	private String dlmcp;
	private String estdo;
	private String pais;
	private String telfn;
	private String telof;
	private String extnc;
	private String tlfmo;
	private String mail1;
	private String mail2;
	private String paixtx;
	private String referenciaDv;
	
	private String idEquipoCompleto;
	
	private List<TablaCobroDetalleDto> cobro;
	private List<TablaPagoDetalleDto>  pago;
	
	private TablaDireccionSociedadDto direccionEmpresa;
	
	private String cuenta;
	private String clabe;
	private String difGastosAdmin;
	private String msgGastosAdmin;
	
	
	public EstadoCuentaReporteLayout() {
		super();
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSegundo() {
		return segundo;
	}

	public void setSegundo(String segundo) {
		this.segundo = segundo;
	}

	public String getAppaterno() {
		return appaterno;
	}

	public void setAppaterno(String appaterno) {
		this.appaterno = appaterno;
	}

	public String getApmaterno() {
		return apmaterno;
	}

	public void setApmaterno(String apmaterno) {
		this.apmaterno = apmaterno;
	}

	public String getDesarrollo() {
		return desarrollo;
	}

	public void setDesarrollo(String desarrollo) {
		this.desarrollo = desarrollo;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getCasadepto() {
		return casadepto;
	}

	public void setCasadepto(String casadepto) {
		this.casadepto = casadepto;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public List<TablaCobroDetalleDto> getCobro() {
		return cobro;
	}

	public void setCobro(List<TablaCobroDetalleDto> cobro) {
		this.cobro = cobro;
	}

	public List<TablaPagoDetalleDto> getPago() {
		return pago;
	}

	public void setPago(List<TablaPagoDetalleDto> pago) {
		this.pago = pago;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getDifGastosAdmin() {
		return difGastosAdmin;
	}

	public void setDifGastosAdmin(String difGastosAdmin) {
		this.difGastosAdmin = difGastosAdmin;
	}

	public String getMsgGastosAdmin() {
		return msgGastosAdmin;
	}

	public void setMsgGastosAdmin(String msgGastosAdmin) {
		this.msgGastosAdmin = msgGastosAdmin;
	}

	public String getNoext() {
		return noext;
	}

	public void setNoext(String noext) {
		this.noext = noext;
	}

	public String getNoint() {
		return noint;
	}

	public void setNoint(String noint) {
		this.noint = noint;
	}

	public String getColn() {
		return coln;
	}

	public void setColn(String coln) {
		this.coln = coln;
	}

	public String getCdpst() {
		return cdpst;
	}

	public void setCdpst(String cdpst) {
		this.cdpst = cdpst;
	}

	public String getDlmcp() {
		return dlmcp;
	}

	public void setDlmcp(String dlmcp) {
		this.dlmcp = dlmcp;
	}

	public String getEstdo() {
		return estdo;
	}

	public void setEstdo(String estdo) {
		this.estdo = estdo;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelfn() {
		return telfn;
	}

	public void setTelfn(String telfn) {
		this.telfn = telfn;
	}

	public String getTelof() {
		return telof;
	}

	public void setTelof(String telof) {
		this.telof = telof;
	}

	public String getExtnc() {
		return extnc;
	}

	public void setExtnc(String extnc) {
		this.extnc = extnc;
	}

	public String getTlfmo() {
		return tlfmo;
	}

	public void setTlfmo(String tlfmo) {
		this.tlfmo = tlfmo;
	}

	public String getMail1() {
		return mail1;
	}

	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}

	public String getMail2() {
		return mail2;
	}

	public void setMail2(String mail2) {
		this.mail2 = mail2;
	}

	public String getPaixtx() {
		return paixtx;
	}

	public void setPaixtx(String paixtx) {
		this.paixtx = paixtx;
	}

	public TablaDireccionSociedadDto getDireccionEmpresa() {
		return direccionEmpresa;
	}

	public void setDireccionEmpresa(TablaDireccionSociedadDto direccionEmpresa) {
		this.direccionEmpresa = direccionEmpresa;
	}

	public String getIdEquipoCompleto() {
		return idEquipoCompleto;
	}

	public void setIdEquipoCompleto(String idEquipoCompleto) {
		this.idEquipoCompleto = idEquipoCompleto;
	}

	public String getCasa() {
		return casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	public String getUnidaTecnicaDesc() {
		return unidaTecnicaDesc;
	}

	public void setUnidaTecnicaDesc(String unidaTecnicaDesc) {
		this.unidaTecnicaDesc = unidaTecnicaDesc;
	}

	public String getMontoCouta() {
		return montoCouta;
	}

	public void setMontoCouta(String montoCouta) {
		this.montoCouta = montoCouta;
	}

	public String getMsgCouta() {
		return msgCouta;
	}

	public void setMsgCouta(String msgCouta) {
		this.msgCouta = msgCouta;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	
	public String getReferenciaDv() {
		return referenciaDv;
	}

	public void setReferenciaDv(String referenciaDv) {
		this.referenciaDv = referenciaDv;
	}
	
}
