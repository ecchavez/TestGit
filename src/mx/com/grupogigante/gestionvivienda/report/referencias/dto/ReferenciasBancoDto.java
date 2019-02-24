package mx.com.grupogigante.gestionvivienda.report.referencias.dto;

public class ReferenciasBancoDto {
	private String bankl;
	private String banka;
	public String getBankl() {
		return bankl;
	}
	public void setBankl(String bankl) {
		this.bankl = bankl;
	}
	public String getBanka() {
		return banka;
	}
	public void setBanka(String banka) {
		this.banka = banka;
	}
	public ReferenciasBancoDto() {
		super();
	}
	public ReferenciasBancoDto(String bankl, String banka) {
		super();
		this.bankl = bankl;
		this.banka = banka;
	}
	
}
