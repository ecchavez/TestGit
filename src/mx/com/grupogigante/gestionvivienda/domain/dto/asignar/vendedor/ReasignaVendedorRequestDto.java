package mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor;

public class ReasignaVendedorRequestDto {
	private String iusuario;
	private String iidUtSup;
	public String getIusuario() {
		return iusuario;
	}
	public void setIusuario(String iusuario) {
		this.iusuario = iusuario;
	}
	public String getIidUtSup() {
		return iidUtSup;
	}
	public void setIidUtSup(String iidUtSup) {
		this.iidUtSup = iidUtSup;
	}
	public ReasignaVendedorRequestDto() {
		super();
	}
	@Override
	public String toString() {
		return "ReasignaVendedorRequestDto [iusuario=" + iusuario
				+ ", iidUtSup=" + iidUtSup + "]";
	}


}
