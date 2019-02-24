package mx.com.grupogigante.gestionvivienda.domain.vo;

public class DigitContadorDto {

	private String contador_equipo;
	private String contador_proc;
	private String contador_file;
	private int contador_consecutivo;
	private int contador_id;
	
	public int getContador_id() {
		return contador_id;
	}
	public void setContador_id(int contador_id) {
		this.contador_id = contador_id;
	}
	public String getContador_equipo() {
		return contador_equipo;
	}
	public void setContador_equipo(String contador_equipo) {
		this.contador_equipo = contador_equipo;
	}
	public String getContador_proc() {
		return contador_proc;
	}
	public void setContador_proc(String contador_proc) {
		this.contador_proc = contador_proc;
	}
	public String getContador_file() {
		return contador_file;
	}
	public void setContador_file(String contador_file) {
		this.contador_file = contador_file;
	}
	public int getContador_consecutivo() {
		return contador_consecutivo;
	}
	public void setContador_consecutivo(int contador_consecutivo) {
		this.contador_consecutivo = contador_consecutivo;
	}
}
