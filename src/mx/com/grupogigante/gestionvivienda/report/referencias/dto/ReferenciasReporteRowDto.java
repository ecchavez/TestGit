package mx.com.grupogigante.gestionvivienda.report.referencias.dto;

public class ReferenciasReporteRowDto {
	private String mandt;
	private String idUtSup;
	private String idFase;
	private String idEqunr;
	private String idBanco;
	private String refPago;
	private String refPagonodig;
	private String banka;
	private String cta;
	private String clabe;
	
	public ReferenciasReporteRowDto() {
		super();
	}

	public String getMandt() {
		return mandt;
	}

	public void setMandt(String mandt) {
		this.mandt = mandt;
	}

	public String getIdUtSup() {
		return idUtSup;
	}

	public void setIdUtSup(String idUtSup) {
		this.idUtSup = idUtSup;
	}

	public String getIdFase() {
		return idFase;
	}

	public void setIdFase(String idFase) {
		this.idFase = idFase;
	}

	public String getIdEqunr() {
		return idEqunr;
	}

	public void setIdEqunr(String idEqunr) {
		this.idEqunr = idEqunr;
	}

	public String getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getRefPago() {
		return refPago;
	}

	public void setRefPago(String refPago) {
		this.refPago = refPago;
	}

	public String getRefPagonodig() {
		return refPagonodig;
	}

	public void setRefPagonodig(String refPagonodig) {
		this.refPagonodig = refPagonodig;
	}

	public String getBanka() {
		return banka;
	}

	public void setBanka(String banka) {
		this.banka = banka;
	}

	public String getCta() {
		return cta;
	}

	public void setCta(String cta) {
		this.cta = cta;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	
}