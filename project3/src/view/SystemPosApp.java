package view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import processing.Inventory;
import processing.SupermarketLoader;
import processing.PosSystem;

public class SystemPosApp {

		/**
		 * Para usar el sistema pos debemos cargar el inventario para poder tener el uso de este
		 */
		private Inventory inventory;
		private PosSystem system;

		/**
		 * Ejecuta la aplicación: le muestra el menú al usuario y la pide que ingrese
		 * una opción, y ejecuta la opción seleccionada por el usuario. Este proceso se
		 * repite hasta que el usuario seleccione la opción de abandonar la aplicación.
		 * @throws ParseException 
		 */
		public void ejecutarAplicacion() throws ParseException, IOException {
			System.out.println("Sistema de compras supermercado DPOO");
			boolean continuar = true;
			while (continuar) {
				try {
					mostrarMenu();
					int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opcion"));
					if (opcion_seleccionada == 0) {
						ejecutarInicializarSuperMarket();
					}
					else if (opcion_seleccionada == 1 && inventory != null && system != null)
						ejecutarRegistrarCliente();
					else if (opcion_seleccionada == 2 && inventory != null && system != null)
						ejecutarInformacionCliente();
					else if (opcion_seleccionada == 3 && inventory != null && system != null)
						ejecutarCedulaPuntos();
					else if (opcion_seleccionada == 4 && inventory != null && system != null)
						ejecutarAddCart();
					else if (opcion_seleccionada == 5 && inventory != null && system != null)
						ejecutarCheckShoppingCart();
					else if (opcion_seleccionada == 6 && inventory != null && system != null)
						terminarCompra();
					else if (opcion_seleccionada == 7 && inventory != null && system != null) {
						System.out.println("Saliendo de la aplicación ...");
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



		private void mostrarMenu() {
			System.out.println("0. Cargar el archivo de lotes");
			System.out.println("1. Registrar cliente");
			System.out.println("2. Consultar informacion cliente dada la cedula");
			System.out.println("3. Consultar puntos dada la cedula");
			System.out.println("4. A�adir un producto a la compra");
			System.out.println("5. Mostrar carrito de compras");
			System.out.println("6. Terminar compra");
			
			System.out.println("7. Salir de la aplicacion");
		}

		
		/**
		 * Registra un cliente
		 * @throws IOException 
		 */
		private void ejecutarRegistrarCliente() throws IOException {
			String name = input("Digite el nombre del cliente");
			int cedula = Integer.parseInt(input("Digite la cedula del cliente"));
			int edad = Integer.parseInt(input("Digite la edad del cliente"));
			System.out.println("\n" + "Ahora digita el estado civil del cliente usando uno de estos numeros" +"\n"
					+ "\n" + "1. Soltero" +"\n"
					+ "\n" + "2. Casado" +"\n"
					+ "\n" + "3. Divorciado" +"\n"
					+ "\n" + "4. Viudo" +"\n"
					+ "\n" + "5. Union Libre" +"\n"	);
			String estadoCivil = input("Digite el numero que corresponde al estado civil");
			if (estadoCivil == "1") {
				estadoCivil = "Soltero";
			}
			else if (estadoCivil == "2") {
				estadoCivil = "Casado";
			}
			else if (estadoCivil == "3") {
				estadoCivil = "Divorciado";
			}
			else if (estadoCivil == "4") {
				estadoCivil = "Viudo";
			}
			else if (estadoCivil == "5") {
				estadoCivil = "Union Libre";
			}
			
			System.out.println("\n" + "Ahora digita la situacion laboral usando uno de estos numeros" +"\n"
					+ "\n" + "1. Estudiante" +"\n"
					+ "\n" + "2. Independiente" +"\n"
					+ "\n" + "3. Empleado" +"\n"
					+ "\n" + "4. Desempleado" +"\n");
			
			String situacionLaboral = input("Digite el numero que corresponde a la situacion laboral");
			if (situacionLaboral == "1") {
				situacionLaboral = "Estudiante";
			}
			else if (situacionLaboral == "2") {
				situacionLaboral = "Independiente";
			}
			else if (situacionLaboral == "3") {
				situacionLaboral = "Empleado";
			}
			else if (situacionLaboral == "4") {
				situacionLaboral = "Desempleado";
			}
			system.registerClient(name, cedula, edad, estadoCivil, situacionLaboral);
		}
		
		/**
		 * Da la informacion de un cliente dada su cedula
		 */
		private void ejecutarInformacionCliente() {
			int cedula = Integer.parseInt(input("Digite la cedula del cliente a consultar"));
			String ph = system.clientInformation(cedula);
			if (ph == "") {
				System.out.println("Digite una cedula valida");
				ejecutarInformacionCliente();
			}
			else {
				System.out.println(ph);
			}
		}

		/**
		 * Este metodo da la informacion de los puntos de un cliente
		 */
		private void ejecutarCedulaPuntos() {
			int cedula = Integer.parseInt(input("Digite la cedula del cliente a consultar"));
			int ph = system.clientPoints(cedula);
			if (ph == 0) {
				System.out.println("Digite una cedula valida");
				ejecutarCedulaPuntos();
			}
			else {
				System.out.println(ph);
			}
		}
		/**
		 * Agregar item a compra
		 */
		private void ejecutarAddCart() {
			int o = Integer.parseInt(input("Digite 1 si el producto es empacado y 2 si no lo es"));
			if (o==1) {
				int code = Integer.parseInt(input("Digite el codigo de barras del item a revisar"));
				String s = system.addItemsToShoppingCart(code);
				if (s == "0") {
				System.out.println("Hubo un error intente de nuevo, no hay unidades disponibles");
				ejecutarAddCart();
				}
				else if (s=="1")
					System.out.println("Se ha agregado correctamente");
				else if (s=="2") {
					System.out.println("No se encontro el codigo de barrass");
					ejecutarAddCart();
				}
			}
			else if (o==2) {
				int code = Integer.parseInt(input("Digite el codigo de barras del item a revisar"));
				float size = Float.parseFloat(input("Digite el peso del producto"));
				String s = system.addItemsToShoppingCartPack(code,size);
				if (s == "0") {
				System.out.println("Hubo un error intente de nuevo, no hay unidades disponibles");
				ejecutarAddCart();
				}
				else if (s=="1")
					System.out.println("Se ha agregado correctamente");
				else if (s=="2") {
					System.out.println("No se encontro el codigo de barrass");
					ejecutarAddCart();
				}
			}
		}
		
		private void ejecutarCheckShoppingCart() {
			String s = system.checkShoppingCart();
			System.out.println(s);
		}
		
		private void terminarCompra() throws IOException {
			int option = Integer.parseInt(input("Digite 1 si el cliente acumula puntos y 2 si no"));
			String s = system.checkShoppingCart();
			int p = system.precioShoppingCart();
			if (option==1) {
				int ced = Integer.parseInt(input("Digite la cedula para registrar los puntos"));
				System.out.println("La factura es" +"\n");
				System.out.println("Puntos iniciales: "+ "\n");
				System.out.println(system.clientPoints(ced));
				system.registerPointsWithId(ced);
				System.out.println(s);
				System.out.println("El precio total es: " + String.valueOf(p));
				System.out.println("Puntos Finales: "+ "\n");
				System.out.println(system.clientPoints(ced));
				system.resetShoppingCart();
			}
			else if (option==2) {
				System.out.println("La factura es" +"\n");
				System.out.println(s);
				System.out.println("El precio total es: " + String.valueOf(p));
				system.resetShoppingCart();
			}
			
		}
		
		/**
		 * Este metodo inicializa el supermercado
		 * @throws ParseException 
		 */
		private void ejecutarInicializarSuperMarket() throws ParseException {
			String file = input("Digite el archivo del inventario:");
			try {
				inventory = SupermarketLoader.loadSuperMarket(file, 0);
				inventory.updateInventory2();
				system = new PosSystem(inventory);
				System.out.println("Se pudo inicializar el sistema de pedidos correctamente!");
			} catch (FileNotFoundException e) {
				System.out.println("No se pudo inicializar el sistema de pedidos correctamente 1.");
			} catch (IOException e) {
				System.out.println("No se pudo inicializar el sistema de pedidos correctamente 2.");
				System.out.println(e.getMessage());
			}
		}
		
		
		/**
		 * Este método sirve para imprimir un mensaje en la consola pidiéndole
		 * información al usuario y luego leer lo que escriba el usuario.
		 *
		 * @param mensaje El mensaje que se le mostrará al usuario
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
		SystemPosApp consola = new SystemPosApp();
		consola.ejecutarAplicacion();
	}
}
