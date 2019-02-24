package mx.com.grupogigante.gestionvivienda.domain.dto;

public class PermisosDto {
	String module;
	String authr;
	String id_permiso;
	String id_permisox;
	String e_usr_init;
	String special;
	
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getE_usr_init() {
		return e_usr_init;
	}
	public void setE_usr_init(String e_usr_init) {
		this.e_usr_init = e_usr_init;
	}		
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getAuthr() {
		return authr;
	}
	public void setAuthr(String authr) {
		this.authr = authr;
	}
	public String getId_permiso() {
		return id_permiso;
	}
	public void setId_permiso(String id_permiso) {
		this.id_permiso = id_permiso;
	}
	public String getId_permisox() {
		return id_permisox;
	}
	public void setId_permisox(String id_permisox) {
		this.id_permisox = id_permisox;
	}
	@Override
	public String toString() {
		return "PermisosDto [module=" + module + ", authr=" + authr
				+ ", id_permiso=" + id_permiso + ", id_permisox=" + id_permisox
				+ ", e_usr_init=" + e_usr_init + "]";
	}
	
	
}
