  package processing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;

import module.Lote;
import module.Product;

public class Inventory {
	
	private List<Lote> lotes;
	private List<Product> products;
	private List<Lote> vencidos;
	
	//Constructor
	
	public Inventory(List<Lote> lotes ,List<Product> products) {
		this.lotes = lotes;
		this.products = products;
	}
	
	public String consultInventory(int code) {
		String h = "";
		for(Lote theLote : lotes) {
			if (theLote.getCode()==code) {
				h += "Codigo de barras" + ": " + String.valueOf(theLote.getCode()) + "\n" + 
				"Nombre" + ": " + theLote.getName() + "\n" + 
				"Cantidad" + ": " + String.valueOf(theLote.getCantidad()) + "\n" + 
				"Fecha de vencimiento" + ": " + String.valueOf(theLote.getExpirationDate());
				return h;
			}
		}
		return null;
	}
	
	/**
	 * Suma las cantidades de un lote
	 * @param code
	 * @return suma cantidades lote
	 */
	public int sumarCantidades(int code) {
		int suma = 0;
		for(Lote theLote : lotes) {
			if (theLote.getCode() == code)
				suma += theLote.getCantidad();
		}
		return suma;
	}
	
	public String nombreByCode(int code) {
		String nom = "";
		for(Lote i : lotes) {
			if (i.getCode()==code)
				nom = i.getName();
		}
		return nom;
	}
	
	/**
	 * Guarda los datos para graficar
	 * @throws IOException
	 */
	public void saveDatos() throws IOException {
		FileWriter writer = new FileWriter("./data/Datos/datos.csv", false);
		for (Lote i : lotes) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String date = dateFormat.format(new Date());  
			writer.append(date);
			writer.append(";");
			writer.append(String.valueOf(i.getCode()));
			writer.append(";");
			writer.append(String.valueOf(sumarCantidades(i.getCode())));
			writer.append("\n");
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * Guarda los datos en un hash para usar despues
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public Map<Date, Integer> cargarDatos(int code, Date initial, Date fin) throws IOException, ParseException {
		BufferedReader csvReader = new BufferedReader(new FileReader("./data/Datos/datosEjemplo.csv"));
		String row;
		Map<Date, Integer> mapita = new HashMap<>();
		while ((row = csvReader.readLine()) != null) {
            String[] rows = row.split(";");
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(rows[0]);
            int codigo = Integer.parseInt(rows[1]);
            int cantidad = Integer.parseInt(rows[2]);
            if(codigo == code && date.after(initial) == true && date.before(fin) == true) {
            	mapita.put(date, cantidad);
            }
		}
		csvReader.close();
		return mapita;
	}
	
	/**
	 * Gets a product given a barcode
	 * @param BarCode
	 * @return
	 */
	public Product productBarCode(int BarCode) {
		for (Product theProduct : products) {
			if (theProduct.getCode() == BarCode) {
				return theProduct;
			}
		}
		return null;
	}
	
	/**
	 * Updates the inventory, eliminates products that are past the 
	 * expiry date.
	 */
	public void updateInventory2() {
		Date date=new java.util.Date();
		List<Product> newList = new ArrayList<>();
		List<Lote> newList2 = new ArrayList<>();
		for (Product theProduct : products) {
			Date expiryDate = theProduct.getExpirationDate();
			if(expiryDate.compareTo(date) > 0) {
				newList.add(theProduct);
				}
			}
		for (Lote theLote : lotes) {
			Date expiryDate = theLote.getExpirationDate();
			if(expiryDate.compareTo(date) > 0) {
				newList2.add(theLote);
				}
			}
		
		setProducts(newList);
		setLotes(newList2);
	}
	
	/**
	 * Gets a Default List to use in the graphic interface
	 */
	public DefaultListModel<String> getDemoList(){
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		int contador = 1;
		demoList.addElement("Nombre / Codigo Barras / Exp Date / Cantidad");
		for (Lote i : lotes) {
			String temp = "";
			temp += contador + " ";
			temp += i.getName() + " / ";
			temp += i.getCode() + " / ";
			temp += i.getExpirationDate() + " / ";
			temp += i.getCantidad();
			demoList.addElement(temp);
			
		}
		
		return demoList;
	}
	
	/**
	 * Changes product image
	 */
	public void changeImage(int code, String image) {
		for (Product i : products) {
			if (i.getCode()==code){
				i.setImage(image);
			}
		}
	}
	
	/**
	 * Gets product image given a product code
	 * @return 
	 */
	public String getImage(int code) {
		String image = "";
		for (Product i : products) {
			if (i.getCode()==code){
				image = i.getImage();
			}
		}
		return image;
	}
	
	/**
	 * Gets a Default List to use in the graphic interface
	 */
	public DefaultListModel<String> getDemoListCode(int code){
		DefaultListModel<String> demoList = new DefaultListModel<String>();
		int contador = 1;
		demoList.addElement("Nombre / Codigo Barras / Exp Date / Cantidad");
		for (Lote i : lotes) {
			if(i.getCode()==code) {
				String temp = "";
				temp += contador + " ";
				temp += i.getName() + " / ";
				temp += i.getCode() + " / ";
				temp += i.getExpirationDate() + " / ";
				temp += i.getCantidad();
				demoList.addElement(temp);
			}
		}
		return demoList;
	}
	
	/**
	 * Updates the inventory, eliminates products that are past the 
	 * expiry date.
	 */
	public List<String> updateInventory(int code) {
		String remaining = "Nombre / Codigo / Exp Date";
		String message = "";
		Date date=new java.util.Date();
		List<Product> newList = new ArrayList<>();
		List<Lote> newList2 = new ArrayList<>();
		List<Lote> vencidosList = new ArrayList<>();
		List<String> newList3 = new ArrayList<>();
		int precio1 = 0;
		int precio2 = 0;
		for (Product theProduct : products) {
			if (theProduct.getCode() == code) {
				Date expiryDate = theProduct.getExpirationDate();
				if(expiryDate.compareTo(date) > 0) {
					newList.add(theProduct);
				}
			}
			else {
				newList.add(theProduct);
			}
		}
		for (Lote theLote : lotes) {
			if (theLote.getCode() == code) {
				Date expiryDate = theLote.getExpirationDate();
				if(expiryDate.compareTo(date) > 0) {
					newList2.add(theLote);
					precio1 += theLote.precioProveedores();
				}
				else {
					remaining += "\n" + theLote.getName() + " / " + String.valueOf(theLote.getCode()) + " / " + String.valueOf(theLote.getExpirationDate()) + "\n";
					precio2 += theLote.precioProveedores();
					vencidosList.add(theLote);
				}
			}
			else {
				newList2.add(theLote);
			}
		}
	
		setProducts(newList);
		setLotes(newList2);
		setExpired(vencidosList);
		System.out.println(precio1 + ": " + precio2);
		
		if (precio1 == 0) {
			message = "100%";
		}
		
		else if (precio2 == 0) {
			message = "0%";
		}
		
		else {
			message = String.valueOf(precio1/precio2 + "%");
		}
		
		
		if (remaining == "Nombre / Codigo / Exp Date") {
			remaining = "No habian productos vencidos por eliminar";
		}
		
		newList3.add(remaining);
		newList3.add(message);
		newList3.add(String.valueOf(vencidosList.size()));
		return newList3;
	}
	
	public void saveInventoryProducts() throws IOException {
		FileWriter writer = new FileWriter("./data/datafileff.csv", false);
		writer.append("NOMBRE");
		writer.append(";");
		writer.append("MARCA");
		writer.append(";");
		writer.append("PESO");
		writer.append(";");
		writer.append("CATEGORIAS");
		writer.append(";");
		writer.append("CODIGO DE BARRAS");
		writer.append(";");
		writer.append("VENCIMIENTO");
		writer.append(";");
		writer.append("PAQUETE");
		writer.append(";");
		writer.append("TIPO DE CUIDADO");
		writer.append(";");
		writer.append("PRECIO PROVEEDOR");
		writer.append(";");
		writer.append("CANTIDAD");
		writer.append(";");
		writer.append("IMAGEN");
		writer.append("\n");
		
		for (Lote i : lotes) {
			Product theProduct = productBarCode(i.getCode());
			writer.append(theProduct.getName());
			writer.append(";");
			writer.append(theProduct.getBrand());
			writer.append(";");
			writer.append(theProduct.getSize());
			writer.append(";");
			writer.append(theProduct.getCategory().get(0));
			writer.append(";");
			writer.append(String.valueOf(theProduct.getCode()));
			writer.append(";");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String date = dateFormat.format(theProduct.getExpirationDate());  
			writer.append(date);
			writer.append(";");
			boolean pack = theProduct.isPacked();
			String s = "";
			if (pack == true) {
				s = "1";
			}
			else {
				s = "2";
			}
			writer.append(s);
			writer.append(";");
			writer.append(i.getType());
			writer.append(";");
			writer.append(String.valueOf(i.getPrecioProveedor()));
			writer.append(";");
			writer.append(String.valueOf(i.getCantidad()));
			writer.append(";");
			String date2 = dateFormat.format(i.getFechaLlegada());
			writer.append(date2);
			writer.append(";");
			writer.append(theProduct.getImage());
			writer.append("\n");
		}
		writer.flush();
		writer.close();
	}
	
	
	public List<Lote> getLotes() {
		return lotes;
	}

	public List<Product> getProducts() {
		return products;
	}
 
	public List<Lote> getVencidos() {
		return vencidos;
	}
	
	/**
	 * Sets the products list
	 * @param newProducts
	 */
	public void setProducts(List<Product> newProducts){
		this.products = newProducts;
	}
	
	/**
	 * Sets the expired list
	 * @param newExpired
	 */
	public void setExpired(List<Lote> newExpired) {
		this.vencidos = newExpired;
	}
	
	/**
	 * Sets the products list
	 * @param newProducts
	 */
	public void setLotes(List<Lote> newLotes){
		this.lotes = newLotes;
	}
}