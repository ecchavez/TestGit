package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

import java.math.BigDecimal;
import java.util.Date;

public class EstadoCuentaReporteFieldDto {
	//private String fechaCompromiso;
	private Date fechaCompromiso;
	//private String importeCompromiso;
	private BigDecimal importeCompromiso;
	private String mCompromiso;
	private String conceptoCompromiso;
	private String fechaPago;
	//private String importePago;
	private BigDecimal importePago;
	private String mPago;
	private String conceptoPago;
	
	/*
	public String getFechaCompromiso() {
		return fechaCompromiso;
	}
	public void setFechaCompromiso(String fechaCompromiso) {
		this.fechaCompromiso = fechaCompromiso;
	}*/
	
	/*
	public String getImporteCompromiso() {
		return importeCompromiso;
	}
	public void setImporteCompromiso(String importeCompromiso) {
		this.importeCompromiso = importeCompromiso;
	}*/
	public String getmCompromiso() {
		return mCompromiso;
	}
	public void setmCompromiso(String mCompromiso) {
		this.mCompromiso = mCompromiso;
	}
	public String getConceptoCompromiso() {
		return conceptoCompromiso;
	}
	public void setConceptoCompromiso(String conceptoCompromiso) {
		this.conceptoCompromiso = conceptoCompromiso;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	/*
	public String getImportePago() {
		return importePago;
	}
	public void setImportePago(String importePago) {
		this.importePago = importePago;
	}*/
	public String getmPago() {
		return mPago;
	}
	public void setmPago(String mPago) {
		this.mPago = mPago;
	}
	public String getConceptoPago() {
		return conceptoPago;
	}
	public void setConceptoPago(String conceptoPago) {
		this.conceptoPago = conceptoPago;
	}
	public EstadoCuentaReporteFieldDto() {
		super();
	}
	public BigDecimal getImporteCompromiso() {
		return importeCompromiso;
	}
	public void setImporteCompromiso(BigDecimal importeCompromiso) {
		this.importeCompromiso = importeCompromiso;
	}
	public Date getFechaCompromiso() {
		return fechaCompromiso;
	}
	public void setFechaCompromiso(Date fechaCompromiso) {
		this.fechaCompromiso = fechaCompromiso;
	}
	public BigDecimal getImportePago() {
		return importePago;
	}
	public void setImportePago(BigDecimal importePago) {
		this.importePago = importePago;
	}
	

}
