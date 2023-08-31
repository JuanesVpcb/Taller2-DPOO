package modelo;

import java.util.ArrayList;

public class Combo implements Producto {

	double descuento;
	String nombreCombo;
	ArrayList<ProductoMenu> items = new ArrayList<>();
	private int calorias;
	
	public Combo(String nombre, double descuento) {
		
		this.nombreCombo = nombre;
		this.descuento = descuento;
		this.calorias = 0;
		
	}
	
	public void agregarItemACombo(String itemCombo, ArrayList<ProductoMenu> productos) {
		
		ProductoMenu item = null;
		
		for (int i = 0; i < productos.size(); i++) {
			
			if (productos.get(i).getNombre().equals(itemCombo)) {
				
				item = productos.get(i);
				break;
				
			}
			
		}
		
		if (item != null) {
			
			items.add(item);
			calorias += item.getCalorias();
			
		}
		
	}
	
	public int getPrecio() {
		
		int precio = 0;
		
		for (int i = 0; i < items.size(); i++) {
			
			precio += items.get(i).getPrecio();
			
		}
		
		return (int)(precio * (1 - (descuento / 100)));
		
	}
	
	public ArrayList<ProductoMenu> getItems() {
		
		return items;
		
	}
	
	public String getNombre() {
		
		return nombreCombo;
		
	}
	
	public int getCalorias() {
		
		return calorias;
		
	}
	
	public String generarTextoFactura() {
		
		String cadena = String.format("* %-30s%4d kcal\t$%-6d\n", nombreCombo, calorias, getPrecio());
		return cadena;
		
	}
	
}
