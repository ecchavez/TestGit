package mx.com.grupogigante.gestionvivienda.exceptions;

public class ViviendaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String mensajeError;
	
	public ViviendaException(RuntimeException re) {
		super();		
	}
	
	public String getMensajeError() {
		return mensajeError;
	}

	public ViviendaException(String mensajeError) {
		super(mensajeError);
		this.mensajeError = mensajeError;
	}
	
	
}
