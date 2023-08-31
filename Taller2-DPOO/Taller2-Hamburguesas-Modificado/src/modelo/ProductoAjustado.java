package modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {

	ProductoMenu base;
	ArrayList<Ingrediente> adiciones = new ArrayList<>();
	ArrayList<Ingrediente> eliminados = new ArrayList<>();
	int calorias;
	
	public ProductoAjustado(ProductoMenu base) {
		
		this.base = base;
		this.calorias = base.getCalorias();
		
	}
	
	public void agregar(Ingrediente adicion) {
		
		adiciones.add(adicion);
		
	}
	
	public void eliminar(Ingrediente eliminado) {
		
		eliminados.add(eliminado);
		
	}

	public int getPrecio() {
		
		int precio = 0;
		precio += base.getPrecio();
		for (int i = 0; i < adiciones.size(); i++) {
			
			precio += adiciones.get(i).getCostoAdicional();
			
		}
		
		return precio;
	
	}

	public String getNombre() {
		
		String cadena = base.getNombre();
		
		if (adiciones.size() != 0) cadena += " Adición(es) de ";
		
		for (int i = 0; i < adiciones.size(); i++) {
			
			cadena += adiciones.get(i).getNombre();
			if (i + 1 < adiciones.size()) cadena+= ", ";
			else cadena += ".";
			
		}
		
		if (eliminados.size() != 0) cadena += " Sin ";
		
		for (int i = 0; i < eliminados.size(); i++) {
			
			cadena += eliminados.get(i).getNombre();
			if (i + 1 < eliminados.size()) cadena+= ", ";
			else cadena += ".";
			
		}
		
		return cadena;
		
	}
	
	public int getCalorias() {
		
		int caloriasTotales = calorias;
		
		for (int i = 0; i < adiciones.size(); i++) caloriasTotales += adiciones.get(i).getCalorias();
		for (int i = 0; i < eliminados.size(); i++) caloriasTotales -= eliminados.get(i).getCalorias();
		
		return caloriasTotales;
		
	}

	public String generarTextoFactura() {
		
		String cadena = String.format("* %-30s%4d kcal\t$%-6d\n", base.getNombre(), calorias, base.getPrecio());
		
		for (int i = 0; i < adiciones.size(); i++) {
			
			Ingrediente adicion = adiciones.get(i);
			String adicionNom = adicion.getNombre();
			cadena += String.format("\t+ %-22s%4d kcal\t$%-6d\n", adicionNom, adicion.getCalorias(), adicion.getCostoAdicional());
			
		}
		
		for (int i = 0; i < eliminados.size(); i++) {
			
			Ingrediente eliminado = eliminados.get(i);
			String eliminadoNom = eliminado.getNombre();
			cadena += String.format("\t+ %-22s%4d kcal\t$%-6d\n", eliminadoNom, eliminado.getCalorias(), eliminado.getCostoAdicional());
			
		}
				
		return cadena;
		
	}
	
	
	
}
