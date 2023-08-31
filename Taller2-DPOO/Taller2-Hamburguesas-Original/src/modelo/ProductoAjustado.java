package modelo;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {

	private ProductoMenu base;
	private ArrayList<Ingrediente> adiciones = new ArrayList<>();
	private ArrayList<Ingrediente> eliminados = new ArrayList<>();
	
	public ProductoAjustado(ProductoMenu base) {
		
		this.base = base;
		
	}
	
	public void agregar(Ingrediente adicion) {
		
		adiciones.add(adicion);
		
	}
	
	public void eliminar(Ingrediente eliminado) {
		
		eliminados.add(eliminado);
		
	}

	public ProductoMenu getBase() {
		
		return base;
		
	}
	
	public ArrayList<Ingrediente> getAdiciones() {
		
		return adiciones;
		
	}
	
	public ArrayList<Ingrediente> getEliminados() {
		
		return eliminados;
		
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

	public String generarTextoFactura() {
		
		String tabulates;
		if (base.getNombre().length() < 12) tabulates = "\t\t\t\t";
		else if (base.getNombre().length() < 24) tabulates = "\t\t\t";
		else tabulates = "\t\t";
		String cadena = "* " + base.getNombre() + tabulates + "$" + Integer.toString(base.getPrecio()) + "\n";
		
		for (int i = 0; i < adiciones.size(); i++) {
			
			Ingrediente adicion = adiciones.get(i);
			String adicionNom = adicion.getNombre();
			String tabulates1;
			if (adicionNom.length() < 12) tabulates1 = "\t\t\t";
			else if (adicionNom.length() < 24) tabulates1 = "\t\t";
			else tabulates1 = "\t";
			cadena += "\t+ " + adicionNom + tabulates1 + "$" + Integer.toString(adicion.getCostoAdicional()) + "\n";
			
		}
		
		for (int i = 0; i < eliminados.size(); i++) {
			
			Ingrediente eliminado = eliminados.get(i);
			cadena += "\t- " + eliminado.getNombre() + "\n";
			
		}
				
		return cadena;
		
	}
	
	
	
}
