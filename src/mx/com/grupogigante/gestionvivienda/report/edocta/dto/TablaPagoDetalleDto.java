package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

public class TablaPagoDetalleDto {
	private String fechDoc;
	private String netwr;
	private String moneda;
	private String concepto;
	private String referencia;
	private String signo;
	private String clearDate;
	private String clrDocNo;
	public String getFechDoc() {
		return fechDoc;
	}
	public void setFechDoc(String fechDoc) {
		this.fechDoc = fechDoc;
	}
	public String getNetwr() {
		return netwr;
	}
	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getSigno() {
		return signo;
	}
	public void setSigno(String signo) {
		this.signo = signo;
	}
	public String getClearDate() {
		return clearDate;
	}
	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}
	public String getClrDocNo() {
		return clrDocNo;
	}
	public void setClrDocNo(String clrDocNo) {
		this.clrDocNo = clrDocNo;
	}
	public TablaPagoDetalleDto() {
		super();
	}

	
}
