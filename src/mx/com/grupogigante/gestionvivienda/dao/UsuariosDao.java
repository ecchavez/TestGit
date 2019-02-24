package mx.com.grupogigante.gestionvivienda.dao;

import java.util.ArrayList;

import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpdUsuariDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

public interface UsuariosDao {
	
	public abstract ResponseValidaLoginDto validaLogin(UsuarioDto usuarios)throws ViviendaException;	
	public abstract ResponseGetUsuariosDto getUsuarios(CriteriosUsuariosDto usuarios)throws ViviendaException;
	public abstract ResponseAddUsuarioDto addUsuario(CriteriosUsuariosDto usuarios)throws ViviendaException;	
	public abstract ResponseUpdUsuariDto updUsuario(CriteriosUsuariosDto usuarios)throws ViviendaException;	
	public abstract ResponseDelUsuarioDto delUsuario(CriteriosUsuariosDto usuarios)throws ViviendaException;
	public abstract ArrayList<PermisosDto> getCatalogoPermisos (String usuario, String unidad) throws ViviendaException;

}
