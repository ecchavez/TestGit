package mx.com.grupogigante.gestionvivienda.report.exception;

public class ReporteException extends Exception {
	private int errorCode;

	public ReporteException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ReporteException() {
		super();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
