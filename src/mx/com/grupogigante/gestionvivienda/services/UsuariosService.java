package mx.com.grupogigante.gestionvivienda.services;

import java.util.ArrayList;

import mx.com.grupogigante.gestionvivienda.dao.UsuariosDao;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosClientesDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.CriteriosUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.PermisosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseAddUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseDelUsuarioDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseGetUsuariosDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseUpdUsuariDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.ResponseValidaLoginDto;
import mx.com.grupogigante.gestionvivienda.domain.dto.UsuarioDto;
import mx.com.grupogigante.gestionvivienda.exceptions.ViviendaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService implements IUsuariosService {
	
	@Autowired
	UsuariosDao usuariosDao;
	
	public ResponseValidaLoginDto validaLogin(UsuarioDto usuario) throws ViviendaException {
		return usuariosDao.validaLogin(usuario);
	}
	
	public ResponseGetUsuariosDto getUsuarios(CriteriosUsuariosDto usuario) throws ViviendaException {
		return usuariosDao.getUsuarios(usuario);
	}

	public ResponseAddUsuarioDto addUsuario(CriteriosUsuariosDto usuario) throws ViviendaException {
		return usuariosDao.addUsuario(usuario);
	}
	
	public ResponseUpdUsuariDto updUsuario(CriteriosUsuariosDto usuario) throws ViviendaException {
		return usuariosDao.updUsuario(usuario);
	}
	
	public ResponseDelUsuarioDto delUsuario(CriteriosUsuariosDto usuario) throws ViviendaException {
		return usuariosDao.delUsuario(usuario);
	}
	
	public ArrayList<PermisosDto> getCatalogoPermisos (String usuario, String unidad) throws ViviendaException {
		return usuariosDao.getCatalogoPermisos (usuario, unidad);
	}
	
}
