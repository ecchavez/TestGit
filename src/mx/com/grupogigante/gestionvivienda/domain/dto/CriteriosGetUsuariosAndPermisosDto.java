package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class CriteriosGetUsuariosAndPermisosDto {
	
	private String usuario;
	private String id_ut_sup;
	private String nombre1;
	private String nombre2;
	private String app_pat;
	private String app_mat;
	private String pass;
	private String permisos;
	private String estatus;
	private String accion;
	private String permisosStr;
	
	
	
	private List<PermisoUsuariosDto> permisosUserList = new ArrayList<PermisoUsuariosDto>();
	private Object objPermisosUserList = new Object();
}
