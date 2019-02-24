/**
 *  Clase creada por Osvaldo Rodriguez Martinez (SACTI consultores) con fecha de 24/10/2012
 */
package mx.com.grupogigante.gestionvivienda.domain.dto;


/**
 * @author WSNADM
 *
 */
public class ResponseGetCompPago {
		private PagoHdrDto pagoHdr = new PagoHdrDto();
		private SociedadDto sociedad=new SociedadDto();
		private PagoDetDto pagoDet = new PagoDetDto();
		private String mensaje;
		private String descripcion;
	
		
		/**
		 * @return the dlmcp
		 */
		public String getDlmcp() {
			return dlmcp;
		}
		/**
		 * @param dlmcp the dlmcp to set
		 */
		public void setDlmcp(String dlmcp) {
			this.dlmcp = dlmcp;
		}
		/**
		 * @return the estdotx
		 */
		public String getEstdotx() {
			return estdotx;
		}
		/**
		 * @param estdotx the estdotx to set
		 */
		public void setEstdotx(String estdotx) {
			this.estdotx = estdotx;
		}
		/**
		 * @return the paistx
		 */
		public String getPaistx() {
			return paistx;
		}
		/**
		 * @param paistx the paistx to set
		 */
		public void setPaistx(String paistx) {
			this.paistx = paistx;
		}
		/**
		 * @return the telfn
		 */
		public String getTelfn() {
			return telfn;
		}
		/**
		 * @param telfn the telfn to set
		 */
		public void setTelfn(String telfn) {
			this.telfn = telfn;
		}
		/**
		 * @return the vblen
		 */
		public String getVblen() {
			return vblen;
		}
		/**
		 * @param vblen the vblen to set
		 */
		public void setVblen(String vblen) {
			this.vblen = vblen;
		}
		/**
		 * @return the kunnr
		 */
		public String getKunnr() {
			return kunnr;
		}
		/**
		 * @param kunnr the kunnr to set
		 */
		public void setKunnr(String kunnr) {
			this.kunnr = kunnr;
		}
		/**
		 * @return the kunnrtx
		 */
		public String getKunnrtx() {
			return kunnrtx;
		}
		/**
		 * @param kunnrtx the kunnrtx to set
		 */
		public void setKunnrtx(String kunnrtx) {
			this.kunnrtx = kunnrtx;
		}
		/**
		 * @return the id_equnr
		 */
		public String getId_equnr() {
			return id_equnr;
		}
		/**
		 * @param id_equnr the id_equnr to set
		 */
		public void setId_equnr(String id_equnr) {
			this.id_equnr = id_equnr;
		}
		/**
		 * @return the fregi
		 */
		public String getFregi() {
			return fregi;
		}
		/**
		 * @param fregi the fregi to set
		 */
		public void setFregi(String fregi) {
			this.fregi = fregi;
		}
		/**
		 * @return the netwr_x_pagar
		 */
		public String getNetwr_x_pagar() {
			return netwr_x_pagar;
		}
		/**
		 * @param netwr_x_pagar the netwr_x_pagar to set
		 */
		public void setNetwr_x_pagar(String netwr_x_pagar) {
			this.netwr_x_pagar = netwr_x_pagar;
		}
		/**
		 * @return the netwr_pag
		 */
		public String getNetwr_pag() {
			return netwr_pag;
		}
		/**
		 * @param netwr_pag the netwr_pag to set
		 */
		public void setNetwr_pag(String netwr_pag) {
			this.netwr_pag = netwr_pag;
		}
		private String dlmcp;
		private String estdotx;
		private String paistx;
		private String telfn;
		//datso cliente
		private String vblen;
		private String kunnr;
		private String kunnrtx;
		private String id_equnr;
		private String fregi;
		private String netwr_x_pagar;
		private String netwr_pag;
		/**
		 * @return the mensaje
		 */
		public String getMensaje() {
			return mensaje;
		}
		/**
		 * @param mensaje the mensaje to set
		 */
		public void setMensaje(String mensaje) {
			this.mensaje = mensaje;
		}
		/**
		 * @return the descripcion
		 */
		public String getDescripcion() {
			return descripcion;
		}
		/**
		 * @param descripcion the descripcion to set
		 */
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		/**
		 * @return the pagoHdr
		 */
		public PagoHdrDto getPagoHdr() {
			return pagoHdr;
		}
		/**
		 * @param pagoHdr the pagoHdr to set
		 */
		public void setPagoHdr(PagoHdrDto pagoHdr) {
			this.pagoHdr = pagoHdr;
		}
		/**
		 * @return the sociedad
		 */
		public SociedadDto getSociedad() {
			return sociedad;
		}
		/**
		 * @param sociedad the sociedad to set
		 */
		public void setSociedad(SociedadDto sociedad) {
			this.sociedad = sociedad;
		}
		public PagoDetDto getPagoDet() {
			return pagoDet;
		}
		public void setPagoDet(PagoDetDto pagoDet) {
			this.pagoDet = pagoDet;
		}

	
	
	
}
