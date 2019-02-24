package mx.com.grupogigante.gestionvivienda.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseGetUnidadesTecnicasDto {
	private List<UbicacionTecnicaDto> ubicacionesList = new ArrayList<UbicacionTecnicaDto>();
	
	public List<UbicacionTecnicaDto> getUbicacionesList() {
		return ubicacionesList;
	}
	public void setUbicacionesList(List<UbicacionTecnicaDto> ubicacionesList) {
		this.ubicacionesList = ubicacionesList;
	}
}
