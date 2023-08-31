package modelo;

public class ProductoMenu implements Producto {
	
	private String nombre;
	private int precioBase;
	
	public ProductoMenu(String nombre, int precioBase) {
		
		this.nombre = nombre;
		this.precioBase = precioBase;
		
	}
	
	public String getNombre() {
		
		return nombre;
		
	}
	
	public int getPrecio( ) {
		
		return precioBase;
		
	}
	
	public String generarTextoFactura() {
		
		String tabulates;
		if (nombre.length() < 12) tabulates = "\t\t\t\t";
		else if (nombre.length() < 24) tabulates = "\t\t\t";
		else tabulates = "\t\t";
		String cadena = "* " + nombre + tabulates + "$" + Integer.toString(getPrecio()) + "\n";
		return cadena;
		
	}
	
}
