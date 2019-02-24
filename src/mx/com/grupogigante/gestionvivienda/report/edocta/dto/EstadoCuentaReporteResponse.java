package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

import java.util.List;

public class EstadoCuentaReporteResponse {
	private List<EstadoCuentaReporteLayout> layout;
	
	
	private EstadoCuentaReporteParamDto       parameter;
	private List<EstadoCuentaReporteFieldDto> fieldList;
	private String                            mensaje;
	private String                            descripcion;
	
//	private List<TablaEstadoCuentaHeaderDto>        estadoCuentaHeader;
//	private List<TablaCobroDetalleDto>              estadoCuentaCobroDetalle;
//	private List<TablaPagoDetalleDto>               estadoCuentaPagoDetalle;
//	private List<TablaClienteDto>                   estadoCuentaCliente;
//	private List<TablaDireccionClienteDto>          estadoCuentaDireccionCliente;
//	private List<TablaDireccionSociedadDto>         estadoCuentaDireccionSociedad;
	public EstadoCuentaReporteResponse() {
	}

	public List<EstadoCuentaReporteLayout> getLayout() {
		return layout;
	}

	public void setLayout(List<EstadoCuentaReporteLayout> layout) {
		this.layout = layout;
	}

	public EstadoCuentaReporteParamDto getParameter() {
		return parameter;
	}

	public void setParameter(EstadoCuentaReporteParamDto parameter) {
		this.parameter = parameter;
	}

	public List<EstadoCuentaReporteFieldDto> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<EstadoCuentaReporteFieldDto> fieldList) {
		this.fieldList = fieldList;
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
	
}
