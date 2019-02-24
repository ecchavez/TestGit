package mx.com.grupogigante.gestionvivienda.exceptions;

public class DaoException extends Exception {
	private String mensesageError;
	private int    errorCode;
	public String getMensesageError() {
		return mensesageError;
	}
	public void setMensesageError(String mensesageError) {
		this.mensesageError = mensesageError;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public DaoException(String mensesageError, int errorCode) {
		super();
		this.mensesageError = mensesageError;
		this.errorCode = errorCode;
	}

}
