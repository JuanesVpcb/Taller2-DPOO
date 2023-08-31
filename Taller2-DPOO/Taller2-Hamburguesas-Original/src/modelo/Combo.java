package modelo;

import java.util.ArrayList;

public class Combo implements Producto {

	private double descuento;
	private String nombreCombo;
	private ArrayList<ProductoMenu> items = new ArrayList<>();
	
	public Combo(String nombre, double descuento) {
		
		this.nombreCombo = nombre;
		this.descuento = descuento;
		
	}
	
	public void agregarItemACombo(String itemCombo, ArrayList<ProductoMenu> productos) {
		
		ProductoMenu item = null;
		
		for (int i = 0; i < productos.size(); i++) {
			
			if (productos.get(i).getNombre().equals(itemCombo)) {
				
				item = productos.get(i);
				break;
				
			}
			
		}
		
		if (item != null) items.add(item);
		
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
	
	public String generarTextoFactura() {
		
		String tabulates;
		if (nombreCombo.length() < 12) tabulates = "\t\t\t\t";
		else if (nombreCombo.length() < 24) tabulates = "\t\t\t";
		else tabulates = "\t\t";
		String cadena = "* " + nombreCombo + tabulates + "$" + Integer.toString(getPrecio()) + "\n";
		return cadena;
		
	}
	
}
