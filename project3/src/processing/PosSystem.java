package processing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import module.Client;
import module.Product;
import module.Promocion;
import module.Lote;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class PosSystem {

	private Inventory inventory;
	private List<Client> clients;
	private List<Product> shoppingCart;
	private List<Promocion> promos;
	
	public PosSystem(Inventory inventory) {
		this.inventory = inventory;
		this.clients = new ArrayList<>();
		this.shoppingCart = new ArrayList<>();
		this.promos = new ArrayList<>();
	}
	
	private int repetionsInSP(int code) {
		int repetions = 0;
		for (Product i : shoppingCart) {
			if (i.getCode() == code) 
				repetions += 1;
		}
		return repetions;
	}

	private void removeFromSK(int code) {
		for( int j = 0; j < shoppingCart.size(); j++ ) {
			if (shoppingCart.get(j).getCode() == code)
				shoppingCart.remove(shoppingCart.get(j));
		}
	}
	
	/**
	 * Revisa si el carrito de compras tiene alguna promocion
	 * @return lista con las promociones
	 */
	public List<String> checkPromo() {
		int notCode = 0;
		List<String> promociones = new ArrayList<>();
		Date date = new Date();
		Product regalo = null;
		for(Promocion i : promos) {
			int contador = 0;
			for( int j = 0; j < shoppingCart.size(); j++ ) {
				if (i.getType().equals("R")){
					if (date.after(i.getInitialDate()) == true && date.before(i.getFinalDate())==true) {
						if(i.getProducto().getCode() == shoppingCart.get(j).getCode() && i.getProducto().getCode() != notCode) {
							int repetions = repetionsInSP(i.getProducto().getCode());
							if (repetions>=i.getInitial())
								regalo = new Product(i.getName(), shoppingCart.get(j).getCode(), 0);
								shoppingCart.add(regalo);
								shoppingCart.add(regalo);
								notCode = i.getProducto().getCode();
								promociones.add(i.getName());
						}
					}
				}
				
				if (i.getType().equals("C")){
					if (date.after(i.getInitialDate()) == true && date.before(i.getFinalDate())==true) {
						int codeNot2 = 0;
						for (Product mm : i.getProducts()) {
							if (mm.getCode() == shoppingCart.get(j).getCode() && mm.getCode() != codeNot2) 
								contador += 1;
								codeNot2 = mm.getCode();
							if (contador == 3) {
								removeFromSK(i.getProducts().get(0).getCode());
								removeFromSK(i.getProducts().get(1).getCode());
								removeFromSK(i.getProducts().get(2).getCode());
								Product theProduct = new Product(i.getName(), i.getCode(), i.getPrecio());
								shoppingCart.add(theProduct);
								promociones.add(i.getName());
								contador = 0;
							}
						}
					}
				}
				
				if (i.getType().equals("D")){
					if (date.after(i.getInitialDate()) == true && date.before(i.getFinalDate())==true) {
						if (shoppingCart.get(j).getCode() == i.getProducto().getCode()) {
							int precio = (int) (shoppingCart.get(j).getPrecioCliente()*i.getDescuento());
							shoppingCart.get(j).setPrecioCliente(precio);
							promociones.add(i.getName());
						}
					}
				}
				
				if (i.getType().equals("P")) {
					if (date.after(i.getInitialDate()) == true && date.before(i.getFinalDate())==true) {
						if (shoppingCart.get(j).getCode() == i.getProducto().getCode()) {
							float puntos = shoppingCart.get(j).getPuntos()*i.getBonus();
							shoppingCart.get(j).setPuntos(puntos);
							promociones.add(i.getName());
						}
					}
				}
			}
		}
		return promociones;
	}
	
	/**
	 * Cargar promociones
	 * @param folder carpeta con los archivos
	 * @throws IOException
	 * @throws ParseException
	 */
	public void findAllFilesInFolder(File folder) throws IOException, ParseException {
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				BufferedReader csvReader = new BufferedReader(new FileReader(file));
				String row;
				while ((row = csvReader.readLine()) != null) {
					String[] rows = row.split(";");
					String tipo = rows[0];
					Date dateI = new SimpleDateFormat("dd/MM/yyyy").parse(rows[1]);
					Date dateF = new SimpleDateFormat("dd/MM/yyyy").parse(rows[2]);
					if (tipo.equals("C")) {
						List<Product> productos = new ArrayList<>();
						Product p1 = inventory.productBarCode(Integer.parseInt(rows[3]));
						Product p2 = inventory.productBarCode(Integer.parseInt(rows[4]));
						Product p3 = inventory.productBarCode(Integer.parseInt(rows[5]));
						productos.add(p1);
						productos.add(p2);
						productos.add(p3);
						int code = Integer.parseInt(rows[6]);
						int precio = Integer.parseInt(rows[7]);
						String name = rows[8];
						Promocion combo = new Promocion(name, tipo, productos, dateI, dateF, code, precio);
						setCombos(combo);
					}
					if (tipo.equals("R")) {
						Product theProduct = inventory.productBarCode(Integer.parseInt(rows[3]));
						int initial = Integer.parseInt(rows[4]);
						int finalP = Integer.parseInt(rows[5]);
						String name = rows[6];
						Promocion combo = new Promocion(name, tipo, theProduct, dateI, dateF, initial, finalP);
						setCombos(combo);
					}
					if (tipo.equals("D")) {
						Product theProduct = inventory.productBarCode(Integer.parseInt(rows[3]));
						int discount = Integer.parseInt(rows[4]);
						String name = rows[5];
						Promocion combo = new Promocion(name, tipo, theProduct, dateI, dateF, discount, "n");
						setCombos(combo);
					}
					if (tipo.equals("P")) {
						Product theProduct = inventory.productBarCode(Integer.parseInt(rows[3]));
						int discount = Integer.parseInt(rows[4]);
						String name = rows[5];
						Promocion combo = new Promocion(name, tipo, theProduct, dateI, dateF, discount, "m");
						setCombos(combo);
					}
				}
				csvReader.close();
			} else {
				findAllFilesInFolder(file);
			}
		}
	}
	
	/**
	 * Añade items al carrito de compras
	 * @param barCode
	 */
	public String addItemsToShoppingCart(int barCode) {
		String result = "0";
		Product theProduct = inventory.productBarCode(barCode);
		if (theProduct != null) {
			shoppingCart.add(theProduct);
		}
		else {
			result = "2";
		}
		List<Lote> lotes = inventory.getLotes();
		for (Lote theLote : lotes) {
			if (theLote.getCode() == theProduct.getCode() && theLote.getExpirationDate().equals(theProduct.getExpirationDate()) && theLote.getCantidad() > 0) {
				theLote.setMinusCantidad(1);
				result = "1";
			}
		}
		return result;
	}
	
	/**
	 * Obtiene una lista de todas las categorias existentes
	 */
	public List<String> categories(){
		List<String> cat = new ArrayList<>();
		List<Product> products = inventory.getProducts();
		for(Product i : products) {
			List<String> cate;
			cate = i.getCategory();
			for (String m : cate) {
				if (cat.contains(m) == false) {
					cat.add(m);
				}
			}
		}
		
		return cat;
		
	}
	
	

	/**
	 * Añade items sin paquete al carrito de compras
	 * @param barCode
	 */
	public String addItemsToShoppingCartPack(int barCode, float peso) {
		String result = "0";
		float precioTemp = 0;
		Product theProduct = inventory.productBarCode(barCode);
		if (theProduct != null) {
			shoppingCart.add(theProduct);
			precioTemp = theProduct.priceBySize(peso);
			theProduct.setPrecioCliente((int) precioTemp);
		}
		else {
			result = "2";
		}
		List<Lote> lotes = inventory.getLotes();
		for (Lote theLote : lotes) {
			if (theLote.getCode() == theProduct.getCode() && theLote.getExpirationDate().equals(theProduct.getExpirationDate()) && theLote.getCantidad() > 0) {
				float x = theLote.priceBySize(peso);
				theLote.setMinusCantidad((int) x);
				result = "1";
			}
		}
		return result;
	}
	
	
	/**
	 * Da la informacion del cliente dada la cedula
	 */
	public String clientInformation(int cedula) {
		String ph = "";
		for (Client theClient : clients) {
			if (theClient.getCedula() == cedula) {
				ph += "\n" + "Nombre: " + theClient.getName() + "\n" + 
				"\n" + "Cedula: " + String.valueOf(theClient.getCedula()) + "\n" + 
				"\n" + "Edad: " + String.valueOf(theClient.getEdad()) + "\n" + 
				"\n" + "Estado civil: " + theClient.getEstadoCivil() + "\n" +
				"\n" + "Estado civil: " + theClient.getSituacionLaboral() + "\n";
			}
		}
		return ph;
	}
	
	/**
	 * Da la informacion del cliente y lo devuelve en un DefaultList 
	 */
	public DefaultListModel<String> defineList(int id){
		Client client = clientByID(id);
		DefaultListModel<String> list = new DefaultListModel<String>();;
		if (client != null) {
			String name = "Nombre: " + client.getName();
			String id1 = "Cedula: " + client.getCedula();
			String edad = "Edad: " + client.getEdad();
			String estado = "Estado civil: " + client.getEstadoCivil();
			String situacion = "Situacion laboral: " + client.getSituacionLaboral();
			String points = "Puntos: " + client.getPoints();
			list.addElement(name);
			list.addElement(id1);
			list.addElement(edad);
			list.addElement(estado);
			list.addElement(situacion);
			list.addElement(points);
		}
		else {
			String message = "No se ha encontrado ningun cliente con esta cedula";
			list.addElement(message);
		}
		return list;
	}
	
	/**
	 * Encuentra a cliente dada la ID
	 */
	public Client clientByID(int id) {
		Client client = null;
		for(Client i : clients) {
			if (i.getCedula() == id)
				client = i;
		}
		return client;
	}
	
	/**
	 * Puntos dada la cedula
	 * @param cedula
	 * @return puntos del cliente
	 */
	public int clientPoints(int cedula) {
		int puntos = 0;
		for (Client theClient : clients) {
			if (theClient.getCedula() == cedula) {
				puntos = (int) theClient.getPoints();
			}
		}
		return puntos;
	}

	/**
	 * Makes a DefaultListModel of labels to create a list in the interface
	 * @throws IOException 
	 */
	public JPanel labelList() throws IOException{
		JPanel demoList = new JPanel();
		List<Product> products = inventory.getProducts();
		int length = products.size();
		demoList.setLayout(new GridLayout(length, 0));
		BufferedImage img;
		
		for (Product i : products) {
			String pa1 = "";
			img = ImageIO.read(new File(i.getImage()));
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(img));
			if (i.isPacked() == true) {
				pa1 = "Empaquetado";
			}
			else {
				pa1 = "No Empaquetado";
			}
			label.setText(String.valueOf(i.getCode()) +  " / " + pa1);
            label.setVerticalTextPosition(SwingConstants.BOTTOM);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
			demoList.add(label);

		}
		return demoList;
	}
	
	/**
	 * Resetea el carrito de compras
	 */
	public void resetShoppingCart() {
		this.shoppingCart = new ArrayList<>();
		}
	
	
	/**
	 * Checkea los items que se encuentran en el carrito de compras
	 * @return String con la informacion de los elementos del carrito
	 */
	public String checkShoppingCart() {
		String shp = "\n" +  "Nombre" + ", " + "Codigo barras"+ ", " + "Precio cliente" + "\n";;
		if (shoppingCart.isEmpty() == false){
			for(Product theProduct : shoppingCart) {
				shp += "\n" +  theProduct.getName() + ", " + theProduct.getCode()+ ", " + theProduct.getPrecioCliente() + "\n";
			}
		}
		else {
			shp = "El carrito esta vacio";
		}
		return shp;
		
	}
	
	/**
	 * Muestra los elementos del carrito y su precio actual
	 */
	public DefaultListModel<String> showCart(int num, int resta) {
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		if (shoppingCart.isEmpty() == false){
			String g = "Nombre / Codigo de barras / Precio";
			demoList.addElement(g);
			for(Product theProduct : shoppingCart) {
				g = theProduct.getName() + " / " + theProduct.getCode()+ " / " + theProduct.getPrecioCliente();
				demoList.addElement(g);
			}
			int precio = precioShoppingCart()-resta;
			if (num == 1) 
				demoList.addElement("El precio actual del carrito es: " + precioShoppingCart());
			if (num == 2)
				demoList.addElement("Precio total de la factura: " + precioShoppingCart());
			if (num == 3)
				demoList.addElement("Precio total de la factura: " + precio);
		}
		else {
			String g = "El carrito esta vacio";
			demoList.addElement(g);
		}
		return demoList;
	}
	
	/**
	 * Finish purchase process
	 */
	public DefaultListModel<String> finishPurchase() {
		List<String> promociones = checkPromo();
		DefaultListModel<String> demoList = showCart(2,0);
		if (promociones.isEmpty() == false) {
			demoList.addElement("Promociones disponibles en la factura: ");
			for (String promocion : promociones) {
				demoList.addElement(promocion);
			}
		}
		demoList.addElement("El cliente no acumula puntos");
		resetShoppingCart();
		return demoList;	}
	
	/**
	 * Finish the process for clients with points
	 */
	public DefaultListModel<String> finishPurchaseWthId(int code, int num, int points) {
		List<String> promociones = checkPromo();
		DefaultListModel<String> demoList = showCart(2,0);
		if (num == 1) {
			demoList = showCart(3,points*15);
		}
		int pointsClient = clientPoints(code);
		demoList.addElement("Puntos iniciales del cliente: " + clientPoints(code));
		registerPointsWithId(code);
		pointsClient = Math.abs(pointsClient-clientPoints(code));
		demoList.addElement("Los puntos acumulados con esta compra fueron: " + pointsClient);
		int puntosFinal = clientPoints(code) - points;
		if (puntosFinal<0) {
			demoList.addElement("Puntos finales del cliente: " + clientPoints(code));
			demoList.addElement("Puntos usados en esta compra: " + 0);
		}
		else {
			demoList.addElement("Puntos finales del cliente: " + puntosFinal);
			demoList.addElement("Puntos usados en esta compra: " + points);
		}
		if (promociones.isEmpty() == false) {
			demoList.addElement("Promociones disponibles en la factura: ");
			for (String promocion : promociones) {
				
				demoList.addElement(promocion);
			}
		}
		resetShoppingCart();
		return demoList;
	}
	
	/**
	 * 
	 * Encuentra el precio total del carrito de compras
	 * @return precio total del carrito de compras
	 */
	public int precioShoppingCart() {
		int precioTotal = 0;
		for (Product theProduct : shoppingCart) {
			precioTotal += theProduct.getPrecioCliente();
		}
		return precioTotal;
	}
	
	/**
	 * Registra el cliente
	 * @param name
	 * @param cedula
	 * @param edad
	 * @param estadoCivil
	 * @param situacionLaboral
	 * @throws IOException
	 */
	public void registerClient(String name, int cedula, int edad, String estadoCivil, String situacionLaboral) throws IOException {
		Client client = new Client(name, cedula, edad, estadoCivil, situacionLaboral, 0);
		if (clients.contains(client) == false) {
			clients.add(client);
		}	
	}
	
	/**
	 * Obtiene listas por categoria
	 * @param category
	 * @return
	 */
	public DefaultListModel<String> listByCategory(String category){
		DefaultListModel<String> list = new DefaultListModel<>();
		list.addElement("Nombre / Categoria / Precio al publico / Codigo de barras");
		
		List<Product> products = inventory.getProducts();
		int contador = 1;
		for (Product i : products) {
			List<String> listCategory = i.getCategory();
			if (listCategory.contains(category)) {
				String element = "";
				element += contador + " " + i.getName() + " / " + i.getType() + " / " + i.getPrecioCliente() + " / " + i.getCode();
				list.addElement(element);
			}
		}
	
		return list;
	}
	
	/**
	 * Number of products in the cart
	 * @param code
	 * @return number of the same product in cart+
	 */
	public int numberInList(int code) {
		int numb = 0;
		for (Product i : shoppingCart) {
			if (i.getCode() == code)
				numb+=1;
		}	
		return numb;
		
	}
	
	
	/**
	 * Saves clients in the file
	 * @throws IOException
	 */
	public void saveClients() throws IOException {
		FileWriter clientWriter = new FileWriter("./data/clientData.csv", false);
		for (Client client : clients) {
		clientWriter.append(client.getName());
		clientWriter.append(";");
		clientWriter.append(String.valueOf(client.getCedula()));
		clientWriter.append(";");
		clientWriter.append(String.valueOf(client.getEdad()));
		clientWriter.append(";");
		clientWriter.append(client.getEstadoCivil());
		clientWriter.append(";");
		clientWriter.append(client.getSituacionLaboral());
		clientWriter.append(";");
		clientWriter.append(String.valueOf(client.getPoints()));
		clientWriter.append("\n");
		}
		clientWriter.flush();
        clientWriter.close();
	}
	
	/**
	 * Saves the inventory
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void saveInventoryP() throws IOException {
		inventory.saveInventoryProducts();
	}
	
	public void loadClients() throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader("./data/clientData.csv"));
		List<Client> c = new ArrayList<>();
        String row;
		try {
	        while ((row = reader.readLine()) != null) {
	            String[] rows = row.split(";");
	            String name = rows[0];
	            int cedula = Integer.parseInt(rows[1]);
	            int edad = Integer.parseInt(rows[2]);
	            String estado = rows[3];
	            String situacion = rows[4];
	            int points = (int) Float.parseFloat(rows[5]);
	            Client client = new Client(name, cedula, edad, estado, situacion, points);
	            c.add(client);
			}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			reader.close();
		}
		setClientList(c);
		reader.close();;
	}
	
	public void registerPointsWithId(int cedula){
		float puntos = 0;
		for (Product theProduct : shoppingCart) {
			puntos += theProduct.getPuntos();
		}
		
		for (Client theClient : clients) {
			if (theClient.getCedula() == cedula) {
				theClient.setPoints(puntos);
			}
		}
	}
	
	public void setClientList(List<Client> newClients) {
		this.clients = newClients;
	}


	public List<Promocion> getPromos() {
		return promos;
	}


	public void setCombos(Promocion combo) {
		this.promos.add(combo);
	}
	
}