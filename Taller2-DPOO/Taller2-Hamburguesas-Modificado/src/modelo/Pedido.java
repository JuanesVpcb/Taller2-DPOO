package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class Pedido {
	
	public int numeroPedidos;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	protected ArrayList<Producto> productos = new ArrayList<>(); 
	
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
	
	private int getCaloriasTotales() {
		
		int calorias = 0;
		for (int i = 0; i < productos.size(); i++) calorias += productos.get(i).getCalorias();
		return calorias;
		
	}
	
	private String generarTextoFactura() {
		
		String cadena = "Pedido n." + Integer.toString(idPedido) + "\n";
		
		cadena += "Cliente: " + nombreCliente + "\n";
		cadena += "Dirección: " + direccionCliente + "\n";
		
		cadena += "======================================================\n";
		for (int i = 0; i < productos.size(); i++) {
			
			cadena += productos.get(i).generarTextoFactura();
			
		}
		
		cadena += "======================================================\n";
		cadena += String.format("%-30s%4d kcal\n", "Total calorias:", getCaloriasTotales());
		cadena += String.format("%-30s$%-6d\n", "Subtotal (Precio Neto):", getPrecioNetoPedido());
		cadena += String.format("%-30s$%-6d\n", "Impuesto (IVA):", getPrecioIVAPedido());
		cadena += String.format("%-30s$%-6d\n", "TOTAL:", getPrecioTotalPedido());
		
		return cadena;
		
	}
	
	public boolean equals(Pedido factura2) {
		
		if (this.productos.size() != factura2.productos.size()) return false;
		for (int i = 0; i < this.productos.size(); i++) {
			
			Producto esteProd = this.productos.get(i);
			boolean ajustado = false;
			boolean encontrado = false;
			if (esteProd.getClass().equals(ProductoAjustado.class)) ajustado = true;
			for (int j = 0; j < factura2.productos.size(); j++) {
				
				Producto eseProd = factura2.productos.get(j);
				if (eseProd.getClass().equals(ProductoAjustado.class) && ajustado) {
					
					ProductoAjustado este = (ProductoAjustado) esteProd;
					ProductoAjustado ese = (ProductoAjustado) eseProd;
					if (!este.base.equals(ese.base)) return false;
					if (este.adiciones.size() != ese.adiciones.size()) return false;
					boolean adicionEncontrada = false;
					for (int k = 0; k < este.adiciones.size(); k++) {
						
						Ingrediente ingredienteActual = este.adiciones.get(k);
						int freqEste = Collections.frequency(este.adiciones, ingredienteActual);
						int freqEse = Collections.frequency(ese.adiciones, ingredienteActual);
						if (ese.adiciones.contains(ingredienteActual) && freqEste == freqEse) adicionEncontrada = true;
						
					}
					if (!adicionEncontrada) return false;
					if (este.eliminados.size() != ese.eliminados.size()) return false;
					boolean eliminadoEncontrado = false;
					for (int k = 0; k < este.eliminados.size(); k++) {
						
						Ingrediente ingredienteActual = este.eliminados.get(k);
						int freqEste = Collections.frequency(este.adiciones, ingredienteActual);
						int freqEse = Collections.frequency(ese.adiciones, ingredienteActual);
						if (ese.eliminados.contains(ingredienteActual) && freqEste == freqEse) eliminadoEncontrado = true;
						
					}
					if (!eliminadoEncontrado) return false;
					if (adicionEncontrada && eliminadoEncontrado) encontrado = true;
					
				} else if (!eseProd.getClass().equals(ProductoAjustado.class) && !ajustado) {
					
					if (esteProd.equals(eseProd)) encontrado = true;
					
				}
				
			}
			if (!encontrado) return false;
			
		}
		
		return true;
		
	}
	
	public void guardarFactura(File archivo) throws IOException {
		
		String str = generarTextoFactura();
		BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
	    writer.write(str);
		writer.close();
		
	}
	
}