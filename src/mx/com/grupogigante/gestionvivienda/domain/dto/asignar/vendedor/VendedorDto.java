package mx.com.grupogigante.gestionvivienda.domain.dto.asignar.vendedor;

public class VendedorDto {
	private String usuario;
	private String pass;
	private String nombre1;
	private String nombre2;
	private String appPat;
	private String appMat;
	private String idUtSu;
	private String idUtSux;
	private String telfn;
	private String extnc;
	private String mail1;
	
	public VendedorDto() {
		super();
	}
	
	public String getNombreCompleto() {
		return nombre1 + " " + nombre2 + " " + appPat + " " + appMat;
	}
	
	public void setNombrecompleto(String nombreCompleto) {
		//
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getNombre1() {
		return nombre1;
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return nombre2;
	}
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	public String getAppPat() {
		return appPat;
	}
	public void setAppPat(String appPat) {
		this.appPat = appPat;
	}
	public String getAppMat() {
		return appMat;
	}
	public void setAppMat(String appMat) {
		this.appMat = appMat;
	}
	public String getIdUtSu() {
		return idUtSu;
	}
	public void setIdUtSu(String idUtSu) {
		this.idUtSu = idUtSu;
	}
	public String getIdUtSux() {
		return idUtSux;
	}
	public void setIdUtSux(String idUtSux) {
		this.idUtSux = idUtSux;
	}
	public String getTelfn() {
		return telfn;
	}
	public void setTelfn(String telfn) {
		this.telfn = telfn;
	}
	public String getExtnc() {
		return extnc;
	}
	public void setExtnc(String extnc) {
		this.extnc = extnc;
	}
	public String getMail1() {
		return mail1;
	}
	public void setMail1(String mail1) {
		this.mail1 = mail1;
	}
	@Override
	public String toString() {
		return "VendedorDto [usuario=" + usuario + ", pass=" + pass
				+ ", nombre1=" + nombre1 + ", nombre2=" + nombre2 + ", appPat="
				+ appPat + ", appMat=" + appMat + ", idUtSu=" + idUtSu
				+ ", idUtSux=" + idUtSux + ", telfn=" + telfn + ", extnc="
				+ extnc + ", mail1=" + mail1 + "]";
	}
}
