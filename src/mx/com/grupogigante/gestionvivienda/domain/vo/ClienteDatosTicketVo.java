package mx.com.grupogigante.gestionvivienda.domain.vo;

public class ClienteDatosTicketVo {
	
private String depto;
private String nombre;
private String correo;
private String fechaInicioGarantia;
private String fechaFinGarantia;
private String mensaje;
private String descripcion;
private String telefono;

public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getCorreo() {
	return correo;
}
public void setCorreo(String correo) {
	this.correo = correo;
}
public String getDepto() {
	return depto;
}
public void setDepto(String depto) {
	this.depto = depto;
}
@Override
public String toString() {
	return "ClienteDatosTicket [depto=" + depto + ", nombre=" + nombre
			+ ", correo=" + correo + "]";
}
public String getMensaje() {
	return mensaje;
}
public void setMensaje(String mensaje) {
	this.mensaje = mensaje;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public String getFechaInicioGarantia() {
	return fechaInicioGarantia;
}
public void setFechaInicioGarantia(String fechaInicioGarantia) {
	this.fechaInicioGarantia = fechaInicioGarantia;
}
public String getFechaFinGarantia() {
	return fechaFinGarantia;
}
public void setFechaFinGarantia(String fechaFinGarantia) {
	this.fechaFinGarantia = fechaFinGarantia;
}
public String getTelefono() {
	return telefono;
}
public void setTelefono(String telefono) {
	this.telefono = telefono;
}

}
