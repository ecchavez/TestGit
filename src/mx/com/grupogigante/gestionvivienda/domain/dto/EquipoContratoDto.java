/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/08/2012              
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

/**
 * @author WSNADM
 *
 */
public class EquipoContratoDto {

	private String fase;
	private String equnr;
	private String id_equnrx;
	private String piso;
	private double sup_total;
	private double price;
	private double engm;
	private double anticipo;
	private double gastos_adm;
	private double difem;
	private int nest;
	private int nbod;
	private String asign_E;
	private String asign_B;
	private double engp=0;
	private double m2es;
	private String fecha_entrega;
	private double m2ja;
	private double m2bo;
	private String apoderado1;
	private String apoderado2;
	private String tipo;
	private String m2pr;
	
	
	public String getM2pr() {
		return m2pr;
	}
	public void setM2pr(String m2pr) {
		this.m2pr = m2pr;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getId_equnrx() {
		return id_equnrx;
	}
	public void setId_equnrx(String id_equnrx) {
		this.id_equnrx = id_equnrx;
	}
	public String getAsign_B() {
		return asign_B;
	}
	public void setAsign_B(String asign_B) {
		this.asign_B = asign_B;
	}
	public double getEngp() {
		return engp;
	}
	public void setEngp(double engp) {
		this.engp = engp;
	}
	/**
	 * @return the fase
	 */
	public String getFase() {
		return fase;
	}
	/**
	 * @param fase the fase to set
	 */
	public void setFase(String fase) {
		this.fase = fase;
	}
	/**
	 * @return the equnr
	 */
	public String getEqunr() {
		return equnr;
	}
	/**
	 * @param equnr the equnr to set
	 */
	public void setEqunr(String equnr) {
		this.equnr = equnr;
	}
	/**
	 * @return the sup_total
	 */
	public double getSup_total() {
		return sup_total;
	}
	/**
	 * @param sup_total the sup_total to set
	 */
	public void setSup_total(double sup_total) {
		this.sup_total = sup_total;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * @return the engm
	 */
	public double getEngm() {
		return engm;
	}
	/**
	 * @param engm the engm to set
	 */
	public void setEngm(double engm) {
		this.engm = engm;
	}
	/**
	 * @return the anticipo
	 */
	public double getAnticipo() {
		return anticipo;
	}
	/**
	 * @param anticipo the anticipo to set
	 */
	public void setAnticipo(double anticipo) {
		this.anticipo = anticipo;
	}
	/**
	 * @return the gastos_adm
	 */
	public double getGastos_adm() {
		return gastos_adm;
	}
	/**
	 * @param gastos_adm the gastos_adm to set
	 */
	public void setGastos_adm(double gastos_adm) {
		this.gastos_adm = gastos_adm;
	}
	/**
	 * @return the difem
	 */
	public double getDifem() {
		return difem;
	}
	/**
	 * @param difem the difem to set
	 */
	public void setDifem(double difem) {
		this.difem = difem;
	}
	/**
	 * @return the nest
	 */
	public int getNest() {
		return nest;
	}
	/**
	 * @param nest the nest to set
	 */
	public void setNest(int nest) {
		this.nest = nest;
	}

	/**
	 * @return the fecha_entrega
	 */
	public String getFecha_entrega() {
		return fecha_entrega;
	}
	/**
	 * @param fecha_entrega the fecha_entrega to set
	 */
	public void setFecha_entrega(String fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}
	/**
	 * @return the piso
	 */
	public String getPiso() {
		return piso;
	}
	/**
	 * @param piso the piso to set
	 */
	public void setPiso(String piso) {
		this.piso = piso;
	}
	/**
	 * @return the nbod
	 */
	public int getNbod() {
		return nbod;
	}
	/**
	 * @param nbod the nbod to set
	 */
	public void setNbod(int nbod) {
		this.nbod = nbod;
	}
	/**
	 * @return the asign_E
	 */
	public String getAsign_E() {
		return asign_E;
	}
	/**
	 * @param asign_E the asign_E to set
	 */
	public void setAsign_E(String asign_E) {
		this.asign_E = asign_E;
	}
	/**
	 * @return the m2es
	 */
	public double getM2es() {
		return m2es;
	}
	/**
	 * @param m2es the m2es to set
	 */
	public void setM2es(double m2es) {
		this.m2es = m2es;
	}
	/**
	 * @return the m2ja
	 */
	public double getM2ja() {
		return m2ja;
	}
	/**
	 * @param m2ja the m2ja to set
	 */
	public void setM2ja(double m2ja) {
		this.m2ja = m2ja;
	}
	/**
	 * @return the m2bo
	 */
	public double getM2bo() {
		return m2bo;
	}
	/**
	 * @param m2bo the m2bo to set
	 */
	public void setM2bo(double m2bo) {
		this.m2bo = m2bo;
	}
	/**
	 * @return the apoderado1
	 */
	public String getApoderado1() {
		return apoderado1;
	}
	/**
	 * @param apoderado1 the apoderado1 to set
	 */
	public void setApoderado1(String apoderado1) {
		this.apoderado1 = apoderado1;
	}
	/**
	 * @return the apoderado2
	 */
	public String getApoderado2() {
		return apoderado2;
	}
	/**
	 * @param apoderado2 the apoderado2 to set
	 */
	public void setApoderado2(String apoderado2) {
		this.apoderado2 = apoderado2;
	}

}
