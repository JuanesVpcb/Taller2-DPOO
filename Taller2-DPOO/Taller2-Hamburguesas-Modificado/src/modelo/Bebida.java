package modelo;

public class Bebida implements Producto {

	private String nombre;
	private int precio;
	private int calorias;
	
	public Bebida (String nombre, int precio, int calorias) {
		
		this.nombre = nombre;
		this.precio = precio;
		this.calorias = calorias;
		
	}

	public int getPrecio() {

		return precio;
		
	}

	public String getNombre() {
		
		return nombre;
		
	}
	
	public int getCalorias() {
		
		return calorias;
		
	}

	public String generarTextoFactura() {
		
		String cadena = String.format("* %-30s%4d kcal\t$%-6d\n", nombre, calorias, precio);
		return cadena;
		
	}
	
}
