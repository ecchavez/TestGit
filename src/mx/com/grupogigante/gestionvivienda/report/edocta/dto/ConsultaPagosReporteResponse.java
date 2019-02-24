package mx.com.grupogigante.gestionvivienda.report.edocta.dto;

import java.util.List;

public class ConsultaPagosReporteResponse {
	
	private List<ConsultaPagosReporteLayout>  layout;
	//private List<EstadoCuentaReporteFieldDto> fieldList;
	private String                            mensaje;
	private String                            descripcion;

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

	public List<ConsultaPagosReporteLayout> getLayout() {
		return layout;
	}

	public void setLayout(List<ConsultaPagosReporteLayout> layout) {
		this.layout = layout;
	}
	
}
