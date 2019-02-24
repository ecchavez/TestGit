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

public class CotizacionDetDto implements JRDataSource {
	
	public CotizacionDetDto()
    {
    }
	
	private String id_cotiz_z;
	private int posnr_z;
	private String conce;
	private String vbeln_cot;	
	private String vbeln_ped;
	
	private String flim;
	private double total;
	private float sim1;
	private float sim2;
	private String concex;
	private int conse;
	private String mesMax;
	private String diaMax;
	private String anoMax;
	private String totalDesc;
	
/**
	 * @return the conse
	 */
	public int getConse() {
		return conse;
	}
	/**
	 * @param conse the conse to set
	 */
	public void setConse(int conse) {
		this.conse = conse;
	}
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


	public CotizacionDetDto(String id_cotiz_z,int posnr_z,String conce,String vbeln_cot,String vbeln_ped,String flim,double total,float sim1,float sim2,String concex,int conse,String mesMax,String diaMax,String anoMax, String totalDesc)
	    {
		this.id_cotiz_z=id_cotiz_z;
		this.posnr_z=posnr_z;
		this.conce=conce;
		this.vbeln_cot=vbeln_cot;	
		this.vbeln_ped=vbeln_ped;
		this.flim=flim;
		this.total=total;
		this.sim1=sim1;
		this.sim2=sim2;
		this.concex=concex;
		this.conse=conse;
		this.mesMax=mesMax;
		this.diaMax=diaMax;
		this.anoMax=anoMax;
		this.totalDesc=totalDesc;
   }
	  
	  private List<CotizacionDetDto> listaCtaPagos = new ArrayList<CotizacionDetDto>();
	    private int indiceCtaPagoActual = -1;
	    
	    public Object getFieldValue(JRField jrField) throws JRException
	    { 
	        Object valor = null;  
	         if("id_cotiz_z".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getId_cotiz_z();
	        } 
	        else if("posnr_z".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getPosnr_z(); 
	        } 
	        else if("conce".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getConce(); 
	        }
	        else if("vbeln_cot".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getVbeln_cot(); 
	        }  else if("vbeln_ped".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getVbeln_ped(); 
	        } 
	        else if("flim".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getFlim(); 
	        } 
	        else if("total".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getTotal(); 
	        } 
	        else if("sim1".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getSim1(); 
	        }  else if("conse".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getConse(); 
	        } 
	        else if("sim2".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getSim2(); 
	        }  else if("concex".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getConcex(); 
	        }  else if("conse".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getConse(); 
	        } 
	        else if("mesMax".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getMesMax(); 
	        } 
	        else if("diaMax".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getDiaMax(); 
	        } 
	        else if("anoMax".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getAnoMax(); 
	        }
	        else if("totalDesc".equals(jrField.getName())) 
	        { 
	            valor = listaCtaPagos.get(indiceCtaPagoActual).getTotalDesc(); 
	        }
	       
	        return valor; 
	    }

	    public boolean next() throws JRException
	    {
	        return ++indiceCtaPagoActual < listaCtaPagos.size();
	    }	
	

	    public void addCtaPagos(CotizacionDetDto cotizacion)
	    {
	        this.listaCtaPagos.add(cotizacion);
	    }
		/**
		 * @return the id_cotiz_z
		 */
		public String getId_cotiz_z() {
			return id_cotiz_z;
		}
		/**
		 * @param id_cotiz_z the id_cotiz_z to set
		 */
		public void setId_cotiz_z(String id_cotiz_z) {
			this.id_cotiz_z = id_cotiz_z;
		}
		/**
		 * @return the posnr_z
		 */
		public int getPosnr_z() {
			return posnr_z;
		}
		/**
		 * @param posnr_z the posnr_z to set
		 */
		public void setPosnr_z(int posnr_z) {
			this.posnr_z = posnr_z;
		}
		/**
		 * @return the conce
		 */
		public String getConce() {
			return conce;
		}
		/**
		 * @param conce the conce to set
		 */
		public void setConce(String conce) {
			this.conce = conce;
		}
		/**
		 * @return the vbeln_cot
		 */
		public String getVbeln_cot() {
			return vbeln_cot;
		}
		/**
		 * @param vbeln_cot the vbeln_cot to set
		 */
		public void setVbeln_cot(String vbeln_cot) {
			this.vbeln_cot = vbeln_cot;
		}
		/**
		 * @return the vbeln_ped
		 */
		public String getVbeln_ped() {
			return vbeln_ped;
		}
		/**
		 * @param vbeln_ped the vbeln_ped to set
		 */
		public void setVbeln_ped(String vbeln_ped) {
			this.vbeln_ped = vbeln_ped;
		}
		/**
		 * @return the sim1
		 */
		public float getSim1() {
			return sim1;
		}
		/**
		 * @param sim1 the sim1 to set
		 */
		public void setSim1(float sim1) {
			this.sim1 = sim1;
		}
		/**
		 * @return the sim2
		 */
		public float getSim2() {
			return sim2;
		}
		/**
		 * @param sim2 the sim2 to set
		 */
		public void setSim2(float sim2) {
			this.sim2 = sim2;
		}
		/**
		 * @return the listaCtaPagos
		 */
		public List<CotizacionDetDto> getListaCtaPagos() {
			return listaCtaPagos;
		}
		/**
		 * @param listaCtaPagos the listaCtaPagos to set
		 */
		public void setListaCtaPagos(List<CotizacionDetDto> listaCtaPagos) {
			this.listaCtaPagos = listaCtaPagos;
		}
		/**
		 * @return the indiceCtaPagoActual
		 */
		public int getIndiceCtaPagoActual() {
			return indiceCtaPagoActual;
		}
		/**
		 * @param indiceCtaPagoActual the indiceCtaPagoActual to set
		 */
		public void setIndiceCtaPagoActual(int indiceCtaPagoActual) {
			this.indiceCtaPagoActual = indiceCtaPagoActual;
		}
		/**
		 * @return the mesMax
		 */
		public String getMesMax() {
			return mesMax;
		}
		/**
		 * @param mesMax the mesMax to set
		 */
		public void setMesMax(String mesMax) {
			this.mesMax = mesMax;
		}
		/**
		 * @return the diaMax
		 */
		public String getDiaMax() {
			return diaMax;
		}
		/**
		 * @param diaMax the diaMax to set
		 */
		public void setDiaMax(String diaMax) {
			this.diaMax = diaMax;
		}
		/**
		 * @return the anoMax
		 */
		public String getAnoMax() {
			return anoMax;
		}
		/**
		 * @param anoMax the anoMax to set
		 */
		public void setAnoMax(String anoMax) {
			this.anoMax = anoMax;
		}
		/**
		 * @return the totalDesc
		 */
		public String getTotalDesc() {
			return totalDesc;
		}
		/**
		 * @param totalDesc the totalDesc to set
		 */
		public void setTotalDesc(String totalDesc) {
			this.totalDesc = totalDesc;
		}
		
		public double getTotal() {
			return total;
		}
		
		public void setTotal(double total) {
			this.total = total;
		}
	
}
