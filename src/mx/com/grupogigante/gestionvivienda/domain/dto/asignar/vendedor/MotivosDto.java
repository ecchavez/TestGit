package mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor;

public class MotivosDto {
	private String idMotivoRea;
	private String idMotivoReax;
	public String getIdMotivoRea() {
		return idMotivoRea;
	}
	public void setIdMotivoRea(String idMotivoRea) {
		this.idMotivoRea = idMotivoRea;
	}
	public String getIdMotivoReax() {
		return idMotivoReax;
	}
	public void setIdMotivoReax(String idMotivoReax) {
		this.idMotivoReax = idMotivoReax;
	}
	public MotivosDto() {
		super();
	}
	@Override
	public String toString() {
		return "MotivosDto [idMotivoRea=" + idMotivoRea + ", idMotivoReax="
				+ idMotivoReax + "]";
	}
}
