package modelo;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class Pedido {
	
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> productos = new ArrayList<>(); 
	
	public Pedido(String nombreCliente, String direccionCliente, int id) {
		
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		File file = new File("./facturas/" + Integer.toString(id) + ".txt");
		while (file.exists()) {

			id += 1;
			file = new File("./facturas/" + Integer.toString(id) + ".txt");

		}
		this.idPedido = id;
		
	}
	
	public int getIdPedido() {
		
		return idPedido;
		
	}
	
	public void agregarProducto(Producto nuevoItem) {
		
		productos.add(nuevoItem);
		
	}
	
	private int getPrecioNetoPedido() {
		
		int total = 0;
		for (int i = 0; i < productos.size(); i++) total += productos.get(i).getPrecio();
		return total;
		
	}
	
	private int getPrecioIVAPedido() {
		
		double ivaDecimal = getPrecioNetoPedido() * 0.19;
		return (int)ivaDecimal;
		
	}
	
	private int getPrecioTotalPedido() {
		
		return getPrecioNetoPedido() + getPrecioIVAPedido();
		
	}
	
	private String generarTextoFactura() {
		
		String cadena = "Pedido n." + Integer.toString(idPedido) + "\n";
		
		cadena += "Cliente: " + nombreCliente + "\n";
		cadena += "Dirección: " + direccionCliente + "\n";
		
		cadena += "==================================================\n";
		for (int i = 0; i < productos.size(); i++) {
			
			cadena += productos.get(i).generarTextoFactura();
			
		}
		
		cadena += "==================================================\n";
		cadena += "Subtotal (Precio Neto): $" + Integer.toString(getPrecioNetoPedido()) + "\n";
		cadena += "Impuesto (IVA): \t\t$" + Integer.toString(getPrecioIVAPedido()) + "\n";
		cadena += "TOTAL: \t\t\t\t\t$" + Integer.toString(getPrecioTotalPedido());
		
		return cadena;
		
	}
	
	public void guardarFactura(File archivo) throws IOException {
		
		String str = generarTextoFactura();
		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
	    writer.write(str);
		writer.close();
		
	}
	
}