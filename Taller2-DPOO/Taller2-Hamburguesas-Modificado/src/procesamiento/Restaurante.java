package procesamiento;

import java.util.ArrayList;

import modelo.Ingrediente;
import modelo.Pedido;
import modelo.ProductoMenu;
import modelo.Combo;
import modelo.Bebida;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Restaurante {
	
	private ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	private ArrayList<ProductoMenu> productos = new ArrayList<>();
	private ArrayList<Combo> combos = new ArrayList<>();
	private ArrayList<Bebida> bebidas = new ArrayList<>();
	private Pedido actual = null;
	private File archivoIngredientes;
	private File archivoProductosMenu;
	private File archivoCombos;
	private File archivoBebidas;
	private int facturasRealizadas = 0;
	private ArrayList<Pedido> historiaPedidos = new ArrayList<>();
	
	public Restaurante() throws IOException, FileNotFoundException {
		
		this.archivoIngredientes = new File("./data/ingredientes.txt");
		this.archivoProductosMenu = new File("./data/menu.txt");
		this.archivoCombos = new File("./data/combos.txt");
		this.archivoBebidas = new File("./data/bebidas.txt");
		cargarinformacionRestaurante(archivoIngredientes, archivoProductosMenu, archivoCombos, archivoBebidas);
		
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		
		facturasRealizadas += 1;
		actual = new Pedido(nombreCliente, direccionCliente, facturasRealizadas);
		
	}
	
	public void cerrarYGuardarPedido() throws IOException {
		
		for (int i = 0; i < historiaPedidos.size(); i++) {
			
			Pedido pedidoARevisar = historiaPedidos.get(i);
			if (pedidoARevisar.equals(actual)) System.out.println("\n¡Esta factura ya se había creado antes!");
			
		}
		File nuevaFactura = new File("./facturas/" + Integer.toString(actual.getIdPedido()) + ".txt");
		boolean created = nuevaFactura.createNewFile();
		boolean writeable = nuevaFactura.canWrite();
		if (created && writeable) {
			
			historiaPedidos.add(actual);
			actual.guardarFactura(nuevaFactura); 
			actual = null;
			System.out.println("\nSe ha guardado exitosamente la factura con id " + nuevaFactura.getName());
			
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
	
	public ArrayList<Bebida> getBebidas() {
		
		return bebidas;
		
	}
	
	public File getFacturaByID(int id) {
		
		try {
			File factura = new File("./facturas/" + Integer.toString(id) + ".txt");
			
			return factura;
		} catch (Exception FileNotFoundException) {
			System.out.println("\n¡El archivo no existe!");
			return null;
		}
		
	}
	
	public void cargarinformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas) throws FileNotFoundException, IOException {
		
		cargarIngredientes(archivoIngredientes.getPath());
		cargarMenu(archivoMenu.getPath());
		cargarCombos(archivoCombos.getPath(), productos);
		cargarBebidas(archivoBebidas.getPath());
		
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
	
	private void cargarBebidas(String archivoBebidas) throws FileNotFoundException, IOException {
		
		ArrayList<Bebida> bebidas = LoaderRestaurante.leerInfoArchivoBebidas(archivoBebidas);
		this.bebidas = bebidas;
		
	}
	
}
