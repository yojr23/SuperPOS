package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import processing.Inventory;
import processing.SupermarketLoader;


public class InventoryApp {

	/**
	 * Se encarga de la logica
	 */
	private Inventory inventory;

	/**
	 * Ejecuta la aplicaci贸n: le muestra el men煤 al usuario y la pide que ingrese
	 * una opci贸n, y ejecuta la opci贸n seleccionada por el usuario. Este proceso se
	 * repite hasta que el usuario seleccione la opci贸n de abandonar la aplicaci贸n.
	 * @throws ParseException 
	 */
	public void ejecutarAplicacion() throws ParseException, IOException {
		System.out.println("Sistema de inventario supermercado DPOO");
		boolean continuar = true;
		while (continuar) {
			try {
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opcion"));
				if (opcion_seleccionada == 0) {
					ejecutarInicializarSuperMarket();
				}
				else if (opcion_seleccionada == 1 && inventory != null)
					ejecutarConsultarLoteInventario();
				else if (opcion_seleccionada == 2 && inventory != null)
					ejecutarUpdateInventario();
				else if (opcion_seleccionada == 3 && inventory != null) {
					System.out.println("Saliendo de la aplicaci贸n ...");
					continuar = false;
				}
				else {
					System.out.println("Por favor seleccione una opcion valida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los numeros de las opciones.");
				continuar = false;
			}
		}
	}



	public void mostrarMenu() {
		System.out.println("0. Cargar el archivo de lotes");
		System.out.println("1. Consultar lote del inventario usando codigo de barras");
		System.out.println("2. Eliminar lotes que se encuentran vencidos y encontrar su desempe駉 en cuanto a perdidas");
		System.out.println("3. Salir de la aplicacion");
	}

	/**
	 * Este metodo inicializa el supermercado
	 * @throws ParseException 
	 */
	private void ejecutarInicializarSuperMarket() throws ParseException {
		String file = input("Digite el archivo de los lotes a cargar:");
		try {
			inventory = SupermarketLoader.loadSuperMarket(file, 0);
			System.out.println("Se pudo inicializar el sistema de pedidos correctamente!");
		} catch (FileNotFoundException e) {
			System.out.println("No se pudo inicializar el sistema de pedidos correctamente 1.");
		} catch (IOException e) {
			System.out.println("No se pudo inicializar el sistema de pedidos correctamente 2.");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Este metodo consulta un lote por codigo de barras
	 */
	private void ejecutarConsultarLoteInventario() {
		int code = Integer.parseInt(input("Digite el codigo de barras del lote a consultar"));
		String consulta = inventory.consultInventory(code);
		System.out.println(consulta);
	}
	
	/**
	 * Este metodo elimina los lotes y productos vencidos
	 */
	private void ejecutarUpdateInventario() {
		int code = Integer.parseInt(input("Digite el codigo de barras de los lotes que desea actualizar"));
		List<String> update = inventory.updateInventory(code);
		System.out.println("\n" + "Se han eliminado los siguientes lotes" + "\n");
		System.out.println("\n" + update.get(0) + "\n");
		System.out.println("\n" + "El desempe駉 financiero de este producto respecto a perdidas" + "\n");
		System.out.println("\n" + update.get(1) + "\n");
	}
	
	/**
	 * Este m茅todo sirve para imprimir un mensaje en la consola pidi茅ndole
	 * informaci贸n al usuario y luego leer lo que escriba el usuario.
	 *
	 * @param mensaje El mensaje que se le mostrar谩 al usuario
	 * @return La cadena de caracteres que el usuario escriba como respuesta.
	 */
	public String input(String mensaje) {
		try {
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e) {
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		InventoryApp consola = new InventoryApp();
		consola.ejecutarAplicacion();
	}
	
}
