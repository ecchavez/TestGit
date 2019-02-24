/**
 * @author Monse V.G. - Wayssen  S.C / Para Grupo Gigante "Gestion Vivienda"
 * Fecha de creación: 16/07/2012               
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Clase que encapsula los fields de Cotizaciones  Detalle 
 * 
 * Fecha de creación: 16/07/2012               
 */

public class TalonDto implements JRDataSource {
	
	public TalonDto()
    {
    }
	

	private String flim;
	private Double total;
	private String concex;
	
	//title
	private String vivienda;
	private String referencia;
	private String noCliente;
	private String nombreCliente;
	private String clabeba2;
	private String clabeba1;
	private String ctaba1;
	private String ctaba2;
	private String periodo;
	
	/**
	 * @return the concex
	 */
	public String getConcex() {
		return concex;
	}
	/**
	 * @param conse the concex to set
	 */
	public void setConcex(String concex) {
		this.concex = concex;
	}
	/**
	 * @return the flim
	 */
	public String getFlim() {
		return flim;
	}
	/**
	 * @param flim the flim to set
	 */
	public void setFlim(String flim) {
		this.flim = flim;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}



	  public TalonDto(String concex,String periodo,String flim,String vivienda,String noCliente,String nombreCliente,String referencia,String ctaba1,String ctaba2,String clabeba1,String clabeba2, Double total)
	    {
	        this.concex = concex;
	        this.periodo = periodo;
	        this.flim = flim;
	        this.vivienda = vivienda;
	        this.noCliente = noCliente;
	        this.nombreCliente = nombreCliente;
	        this.referencia = referencia;
	        this.ctaba1 = ctaba1;
	        this.ctaba2 = ctaba2;
	        this.clabeba1 = clabeba1;
	        this.clabeba2 = clabeba2;
	        this.total=total;
	    }
	  
	  private List<TalonDto> listaTalones = new ArrayList<TalonDto>();
	    private int indiceTalonActual = -1;
	    
	    public Object getFieldValue(JRField jrField) throws JRException
	    { 
	        Object valor = null;  

	        if("concex".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getConcex();
	        } 
	        else if("periodo".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getPeriodo(); 
	        } 
	        else if("flim".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getFlim(); 
	        } 
	        else if("vivienda".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getVivienda(); 
	        } 
	        else if("noCliente".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getNoCliente(); 
	        } 
	        else if("nombreCliente".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getNombreCliente(); 
	        } 
	        else if("referencia".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getReferencia(); 
	        } 
	        else if("ctaba1".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getCtaba1(); 
	        } 
	        else if("ctaba2".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getCtaba2(); 
	        } 
	        else if("clabeba1".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getClabeba1(); 
	        } 
	        else if("clabeba2".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getClabeba2(); 
	        } 
	        else if("total".equals(jrField.getName())) 
	        { 
	            valor = listaTalones.get(indiceTalonActual).getTotal(); 
	        }
	       
	        return valor; 
	    }

	    public boolean next() throws JRException
	    {
	        return ++indiceTalonActual < listaTalones.size();
	    }	
	

	    public void addTalones(TalonDto talon)
	    {
	        this.listaTalones.add(talon);
	    }
		/**
		 * @return the vivienda
		 */
		public String getVivienda() {
			return vivienda;
		}
		/**
		 * @param vivienda the vivienda to set
		 */
		public void setVivienda(String vivienda) {
			this.vivienda = vivienda;
		}
		/**
		 * @return the referencia
		 */
		public String getReferencia() {
			return referencia;
		}
		/**
		 * @param referencia the referencia to set
		 */
		public void setReferencia(String referencia) {
			this.referencia = referencia;
		}
		/**
		 * @return the noCliente
		 */
		public String getNoCliente() {
			return noCliente;
		}
		/**
		 * @param noCliente the noCliente to set
		 */
		public void setNoCliente(String noCliente) {
			this.noCliente = noCliente;
		}
		/**
		 * @return the nombreCliente
		 */
		public String getNombreCliente() {
			return nombreCliente;
		}
		/**
		 * @param nombreCliente the nombreCliente to set
		 */
		public void setNombreCliente(String nombreCliente) {
			this.nombreCliente = nombreCliente;
		}
		/**
		 * @return the clabeba2
		 */
		public String getClabeba2() {
			return clabeba2;
		}
		/**
		 * @param clabeba2 the clabeba2 to set
		 */
		public void setClabeba2(String clabeba2) {
			this.clabeba2 = clabeba2;
		}
		/**
		 * @return the clabeba1
		 */
		public String getClabeba1() {
			return clabeba1;
		}
		/**
		 * @param clabeba1 the clabeba1 to set
		 */
		public void setClabeba1(String clabeba1) {
			this.clabeba1 = clabeba1;
		}
		/**
		 * @return the ctaba1
		 */
		public String getCtaba1() {
			return ctaba1;
		}
		/**
		 * @param ctaba1 the ctaba1 to set
		 */
		public void setCtaba1(String ctaba1) {
			this.ctaba1 = ctaba1;
		}
		/**
		 * @return the ctaba2
		 */
		public String getCtaba2() {
			return ctaba2;
		}
		/**
		 * @param ctaba2 the ctaba2 to set
		 */
		public void setCtaba2(String ctaba2) {
			this.ctaba2 = ctaba2;
		}
		/**
		 * @return the periodo
		 */
		public String getPeriodo() {
			return periodo;
		}
		/**
		 * @param periodo the periodo to set
		 */
		public void setPeriodo(String periodo) {
			this.periodo = periodo;
		}
		/**
		 * @return the listaTalones
		 */
		public List<TalonDto> getListaTalones() {
			return listaTalones;
		}
		/**
		 * @param listaTalones the listaTalones to set
		 */
		public void setListaTalones(List<TalonDto> listaTalones) {
			this.listaTalones = listaTalones;
		}
		/**
		 * @return the indiceCtaPagoActual
		 */
		public int getIndiceTalonesActual() {
			return indiceTalonActual;
		}
		/**
		 * @param indiceCtaPagoActual the indiceCtaPagoActual to set
		 */
		public void setIndiceTalonesActual(int indiceTalonActual) {
			this.indiceTalonActual = indiceTalonActual;
		}
	
}
