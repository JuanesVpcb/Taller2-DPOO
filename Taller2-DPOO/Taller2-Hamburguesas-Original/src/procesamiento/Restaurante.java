package procesamiento;

import java.util.ArrayList;

import modelo.Ingrediente;
import modelo.Pedido;
import modelo.ProductoMenu;
import modelo.Combo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Restaurante {
	
	private ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	private ArrayList<ProductoMenu> productos = new ArrayList<>();
	private ArrayList<Combo> combos = new ArrayList<>();
	private Pedido actual = null;
	private int facturasRealizadas = 0;
	
	public Restaurante() throws IOException, FileNotFoundException {
		
		cargarinformacionRestaurante(new File("./data/ingredientes.txt"), 
									 new File("./data/menu.txt"),
									 new File("./data/combos.txt"));
		
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		
		facturasRealizadas += 1;
		actual = new Pedido(nombreCliente, direccionCliente, facturasRealizadas);
		
	}
	
	public void cerrarYGuardarPedido() throws IOException {
		
		
		File nuevaFactura = new File("./facturas/" + Integer.toString(actual.getIdPedido()) + ".txt");
		boolean created = nuevaFactura.createNewFile();
		boolean writeable = nuevaFactura.canWrite();
		if (created && writeable) {
			actual.guardarFactura(nuevaFactura); 
			actual = null;
			System.out.println("Se ha guardado exitosamente la factura con id " + nuevaFactura.getName());
		}
		
	}
	
	public Pedido getPedidoEnCurso() {
		
		return actual;
		
	}
	
	public ArrayList<ProductoMenu> getMenuBase() {
		
		return productos;
		
	}
	
	public ArrayList<Ingrediente> getIngredientes() {
		
		return ingredientes;
		
	}
	
	public ArrayList<Combo> getCombos() {
		
		return combos;
		
	}
	
	public File getFacturaByID(int id) {
		
		try {
			File factura = new File("./facturas/" + Integer.toString(id) + ".txt");
			if (factura.exists()) return factura;
			else return null;
		} catch (Exception FileNotFoundException) {
			return null;
		}
		
	}
	
	public void cargarinformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) throws FileNotFoundException, IOException {
		
		cargarIngredientes(archivoIngredientes.getPath());
		cargarMenu(archivoMenu.getPath());
		cargarCombos(archivoCombos.getPath(), productos);
		
	}
	
	private void cargarIngredientes(String archivoIngredientes) throws FileNotFoundException, IOException {
		
		ArrayList<Ingrediente> ingredientes = LoaderRestaurante.leerInfoArchivoIngredientes(archivoIngredientes);
		this.ingredientes = ingredientes;
		
	}
	
	private void cargarMenu(String archivoMenu) throws FileNotFoundException, IOException {
		
		ArrayList<ProductoMenu> productosMenu = LoaderRestaurante.leerInfoArchivoProductosMenu(archivoMenu);
		this.productos = productosMenu;
		
	}
	
	private void cargarCombos(String archivoCombos, ArrayList<ProductoMenu> productosMenu) throws FileNotFoundException, IOException {
		
		ArrayList<Combo> combos = LoaderRestaurante.leerInfoArchivoCombos(archivoCombos, productosMenu);
		this.combos = combos;
		
	}
	
}
