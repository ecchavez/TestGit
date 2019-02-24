package mx.com.grupogigante.gestionvivienda.domain.dto;

public class PermisoUsuariosDto {
    String usuario;
    String id_permiso;
    String id_permisox;
    
	public String getId_permisox() {
		return id_permisox;
	}
	public void setId_permisox(String id_permisox) {
		this.id_permisox = id_permisox;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getId_permiso() {
		return id_permiso;
	}
	public void setId_permiso(String id_permiso) {
		this.id_permiso = id_permiso;
	}
}
