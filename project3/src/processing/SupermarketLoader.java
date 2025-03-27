package processing;

//Imports for CSV read/write
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;  
import java.util.List;
import java.util.ArrayList;
//Imports for Date usage
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import module.Lote;
import module.Product;


public class SupermarketLoader {
	
	/**
	 * Reads the file and creates a map that helps us creating the inventory
	 * @param dataFile
	 * @return List containing all the data
	 * @throws IOException
	 * @throws ParseException 
	 */
	private static List<Lote> cargarLotes(String dataFile) throws IOException, ParseException {		
		List<Lote> loadedList = new ArrayList<>();
		BufferedReader csvReader = new BufferedReader(new FileReader(dataFile));
        // declare a variable
        int k=0;
        String row;
		while ((row = csvReader.readLine()) != null) {
            if(k == 0){
                k++;
                continue;
            }
            String[] rows = row.split(";");
            List<String> categories = new ArrayList<>();
            String name = rows[0];
            String marca = rows[1];
            String peso = rows[2];
            String tipo = rows[3];
            int barCode = Integer.parseInt(rows[4]);
            Date dateArrival =  new SimpleDateFormat("dd/MM/yyyy").parse(rows[10]);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(rows[5]);
            boolean isPacked = false;
            if (rows[6] == "1") {
            	isPacked = true;
            }

            String careType = rows[7];
            int precio = Integer.parseInt(rows[8]);
            int cantidad = Integer.parseInt(rows[9]);
            categories.add(tipo);
            Lote newLote = new Lote(name,marca,careType,peso,categories,isPacked,barCode,date,dateArrival,cantidad,precio);
            loadedList.add(newLote);
            if (loadedList.isEmpty() == false) {
            	for( Lote theLote : loadedList) {
            		if (theLote.getCode() == barCode) {
            			if (theLote.getCategory().contains(tipo) == false) {
            				theLote.getCategory().add(tipo);	
            			}
            		}
            	}
            }
		}
        

		csvReader.close();
		return loadedList;

	}
	
	
	//loads inventory over other inventory already available
	public static void cargarIf(String dataFile, Inventory inventory) throws IOException, ParseException {
		BufferedReader csvReader = new BufferedReader(new FileReader(dataFile));
        List<Lote> lotes = inventory.getLotes();
        List<Product> products = inventory.getProducts();
		// declare a variable
        int k=0;
        String row;
		while ((row = csvReader.readLine()) != null) {
            if(k == 0){
                k++;
                continue;
            }
            String[] rows = row.split(";");
            List<String> categories = new ArrayList<>();
            String name = rows[0];
            String marca = rows[1];
            String peso = rows[2];
            String tipo = rows[3];
            int barCode = Integer.parseInt(rows[4]);
            Date dateArrival =  new SimpleDateFormat("dd/MM/yyyy").parse(rows[10]);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(rows[5]);
            boolean isPacked = false;
            if (rows[6] == "1") {
            	isPacked = true;
            }
            
            String careType = rows[7];
            int precio = Integer.parseInt(rows[8]);
            int cantidad = Integer.parseInt(rows[9]);
            int precioCliente = (int) (precio + (precio*0.25));
            categories.add(tipo);
            Lote newLote = new Lote(name,marca,careType,peso,categories,isPacked,barCode,date,dateArrival,cantidad,precio);
            Product newProduct = new Product(name,marca,tipo,peso,categories,isPacked,careType,barCode,date,precioCliente);
            lotes.add(newLote);
            products.add(newProduct);
            if (lotes.isEmpty() == false) {
            	for( Lote theLote : lotes) {
            		if (theLote.getCode() == barCode) {
            			if (theLote.getCategory().contains(tipo) == false) {
            				theLote.getCategory().add(tipo);
            			}
            		}
            	}
            }
            if (products.isEmpty() == false) {
            	for(Product theProduct : products) {
            		if (theProduct.getCode() == barCode) {
            			theProduct.setPrecioCliente(precioCliente);
            			if (theProduct.getCategory().contains(tipo) == false) {
            				theProduct.getCategory().add(tipo);	
            			}
            		}
            	}
            }
		}
		csvReader.close();
		inventory.setLotes(lotes);
		inventory.setProducts(products);
	}
	
	private static List<Product> cargarProducto(String dataFile, int code) throws IOException, ParseException {		
		List<Product> loadedList = new ArrayList<>();
		BufferedReader csvReader = new BufferedReader(new FileReader(dataFile));
        // declare a variable
        int k=0;
        String row;
		while ((row = csvReader.readLine()) != null) {
            if(k == 0){
                k++;
                continue;
            }
            String[] rows = row.split(";");
            List<String> categories = new ArrayList<>();
            String name = rows[0];
            String marca = rows[1];
            String peso = rows[2];
            String tipo = rows[3];
            int barCode = Integer.parseInt(rows[4]);
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(rows[5]);
            boolean isPacked = false;
            if (rows[6] == "1") {
            	isPacked = true;
            }

            String careType = rows[7];
            int precio = Integer.parseInt(rows[8]);
            int precioCliente = (int) (precio + (precio*0.25));
            categories.add(tipo);
            Product newProduct = new Product(name,marca,tipo,peso,categories,isPacked,careType,barCode,date,precioCliente);
            if (code == 1)
            	newProduct.setImage2(rows[11]);
            loadedList.add(newProduct);
            if (loadedList.isEmpty() == false) {
            	for(Product theProduct : loadedList) {
            		if (theProduct.getCode() == barCode) {
            			theProduct.setPrecioCliente(precioCliente);
            			if (theProduct.getCategory().contains(tipo) == false) {
            				theProduct.getCategory().add(tipo);	
            			}
            		}
            	}
            }
		}
        

		csvReader.close();
		return loadedList;
	}
	
	/**
	 * Creates the inventory from the dataFile.
	 * @param dataFile
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static Inventory loadSuperMarket(String dataFile, int code) throws IOException, ParseException {	
		List<Lote> fullInventory = cargarLotes(dataFile);
		List<Product> inventoryProduct = cargarProducto(dataFile, code);
		Inventory inventory = new Inventory(fullInventory,inventoryProduct); 
		return inventory;
	}
}





