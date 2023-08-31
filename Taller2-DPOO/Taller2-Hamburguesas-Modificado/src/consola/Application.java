package consola;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

import procesamiento.Restaurante;
import modelo.ProductoMenu;
import modelo.Ingrediente;
import modelo.Combo;
import modelo.Pedido;
import modelo.ProductoAjustado;
import modelo.Bebida;

public class Application {
	
	private Restaurante elCorral;
	private ArrayList<ProductoMenu> productos = new ArrayList<>();
	private ArrayList<Combo> combos = new ArrayList<>();
	private ArrayList<Ingrediente> ingredientes = new ArrayList<>();
	private ArrayList<Bebida> bebidas = new ArrayList<>();
	
	private void correrAplicacion() throws IOException, FileNotFoundException {
		
		elCorral = new Restaurante();
		productos = elCorral.getMenuBase();
		combos = elCorral.getCombos();
		bebidas = elCorral.getBebidas();
		ingredientes = elCorral.getIngredientes();
		
		System.out.println("Bienvenidx a El Corral.\n");
		boolean continuar = true;
		while (continuar) {
			
			opciones();
			int opcion = Integer.parseInt(input("\nSeleccione una opción para continuar"));
			if (opcion != 6) ejecutarOpcion(opcion);
			else continuar = false;
			
		}
		
	}
	
	private void opciones() {
		
		System.out.println("\n1. Mostrar el menú.");
		System.out.println("2. Iniciar un nuevo pedido.");
		System.out.println("3. Agregar elemento a un pedido.");
		System.out.println("4. Cerrar un pedido y guardar su factura.");
		System.out.println("5. Consultar un pedido por número identificador.");
		System.out.println("6. Salir de la aplicación.");
		
	}
	
	private void mostrarMenu() {
		
		productosMenu();
		combosMenu();
		ingredientesMenu();
		bebidasMenu();
		
	}
	
	private void productosMenu() {
		
		System.out.println("\n===================== Productos Base =====================");
		for (int i = 0; i < productos.size(); i++) {
			
			ProductoMenu producto = productos.get(i);
			String nomProducto = producto.getNombre();
			String id = Integer.toString(i + 1) + ". ";
			String linea = String.format("%-4s%-30s%4d kcal\t$%-6d", id, nomProducto, producto.getCalorias(), producto.getPrecio());
			System.out.println(linea);
			
		}
		
	}
	
	private void combosMenu() {
		
		System.out.println("\n========================= Combos =========================");
		for (int i = 0; i < combos.size(); i++) {
			
			Combo combito = combos.get(i);
			String id = Integer.toString(i + 1) + ". ";
			String nomCombito = combito.getNombre();
			String linea = String.format("%-4s%-30s%4d kcal\t$%-6d", id, nomCombito, combito.getCalorias(), combito.getPrecio());
			System.out.println(linea);
			ArrayList<ProductoMenu> items = combito.getItems();
			for (int x = 0; x < items.size(); x++) {
				
				ProductoMenu item = items.get(x);
				System.out.println("\t+" + item.getNombre());
				
			}
			
		}
		
	}
	
	private void ingredientesMenu() {
		
		System.out.println("\n====================== Ingredientes ======================");
		for (int i = 0; i < ingredientes.size(); i++) {
			
			Ingrediente ingrediente = ingredientes.get(i);
			String nomIngrediente = ingrediente.getNombre();
			String id = Integer.toString(i + 1) + ". ";
			String linea = String.format("%-4s%-30s%4d kcal\t$%-6d", id, nomIngrediente, ingrediente.getCalorias(), ingrediente.getCostoAdicional());
			System.out.println(linea);

		}
		
	}
	
	private void bebidasMenu() {
		
		System.out.println("\n========================= Bebidas ========================");
		for (int i = 0; i < bebidas.size(); i++) {
			
			Bebida bebida = bebidas.get(i);
			String nomBebida = bebida.getNombre();
			String id = Integer.toString(i + 1) + ". ";
			String linea = String.format("%-4s%-30s%4d kcal\t$%-6d", id, nomBebida, bebida.getCalorias(), bebida.getPrecio());
			System.out.println(linea);
			
		}
		
	}
	
	public void ejecutarOpcion(int opcionSeleccionada) throws IOException, FileNotFoundException {
		
		switch (opcionSeleccionada) {
			
			case 1:
				mostrarMenu();
				break;
				
			case 2:
				if (elCorral.getPedidoEnCurso() != null) {
					System.out.println("\n¡Ya hay un pedido activo!");
					break;
				}
				
				String nombre = input("Ingrese su nombre");
				String direccion = input("Ingrese su dirección");
				elCorral.iniciarPedido(nombre, direccion);
				break;
				
			case 3:
				if (elCorral.getPedidoEnCurso() == null) {
					System.out.println("\n¡No hay un pedido activo!");
					break;
				}
				
				Pedido actual = elCorral.getPedidoEnCurso();
				System.out.println("\n1. Productos individuales");
				System.out.println("2. Combos");
				System.out.println("3. Bebidas");
				System.out.println("4. Cancelar");
				int opcion = Integer.parseInt(input("\nSeleccione una opción para continuar"));
				switch (opcion) {
					case 1:
						productosMenu();
						int seleccionProducto = Integer.parseInt(input("\nSeleccione una opción para continuar")) - 1;
						ProductoMenu productoSeleccionado = productos.get(seleccionProducto);
						System.out.println("\n1. Añadir");
						System.out.println("2. Quitar");
						System.out.println("3. Ninguna");
						int ajuste = Integer.parseInt(input("\n¿Desea añadir o quitar algún ingrediente?"));
						
						boolean seguir = true;
						if (ajuste == 1 || ajuste == 2) {
							
							ProductoAjustado productoModificado = new ProductoAjustado(productoSeleccionado);
							
							while (seguir) {
								ingredientesMenu();
								int ajusteIngrediente = Integer.parseInt(input("\nSeleccione una opción para continuar")) - 1;
								
								if (ajusteIngrediente < ingredientes.size()) {
									Ingrediente ingredienteModificacion = ingredientes.get(ajusteIngrediente);
									
									if (ajuste == 1) productoModificado.agregar(ingredienteModificacion);
									else if (ajuste == 2) productoModificado.eliminar(ingredienteModificacion);
									
								} else System.out.println("\n¡Opción inexistente!");
								
								System.out.println("\n¿Seguir?");
								System.out.println("\n1. Sí, añadir");
								System.out.println("2. Si, quitar");
								System.out.println("3. No (Termina el producto)");
								
								String entrada = input("\nSeleccione una opción para continuar");
								if (entrada.equals("1")) {
									ajuste = 1;
								} else if (entrada.equals("2")) {
									ajuste = 2;
								} else if (entrada.equals("3")) {
									seguir = false;
									actual.agregarProducto(productoModificado);
								}
							}
							
						} else if (ajuste == 3) {
							
							actual.agregarProducto(productoSeleccionado);
							
						} else System.out.println("\n¡Opción inexistente!");
						break;
						
					case 2:
						combosMenu();
						int seleccionCombo = Integer.parseInt(input("\nSeleccione una opción para continuar")) - 1;
						actual.agregarProducto(combos.get(seleccionCombo));
						break;
						
					case 3:
						bebidasMenu();
						int seleccionBebida = Integer.parseInt(input("\nSeleccione una opción para continuar")) - 1;
						actual.agregarProducto(bebidas.get(seleccionBebida));
						break;
						
					case 4:
						break;
						
					default:
						System.out.println("\n¡Opción inexistente!");
						break;
				}
				break;
				
			case 4:
				if (elCorral.getPedidoEnCurso() == null) {
					System.out.println("\n¡No hay un pedido activo!");
					break;
				}
				elCorral.cerrarYGuardarPedido();
				break;
				
			case 5:
				int id = Integer.parseInt(input("\nIngrese el id de la factura"));
				File factura = elCorral.getFacturaByID(id);
				System.out.println("\n");
				if (factura == null) System.out.println("\n¡No existe el archivo!");
				else {
					Scanner scanner = new Scanner(factura);
		            while(scanner.hasNext()) {
		                System.out.println(scanner.nextLine());
		            }
		            scanner.close();
				}
				break;
				
			default:
				System.out.println("\n¡Opción inexistente!");
				break;
				
		}
		
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		
		Application app = new Application();
		app.correrAplicacion();
		
	}

}
