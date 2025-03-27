package module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Product implements LoteProduct {
	
	private String name;
	private String brand;
	private String type;
	private String size;
	private List<String> category;
	private boolean isPacked;
	private int code;
	private Date expirationDate;
	private int precioCliente;
	private String image;
	private float puntos;
	
	public Product(String name, String brand, String type, String size, List<String> category, boolean isPacked, String careType, int code, Date expirationDate, int precio) {
		
		this.name = name;
		this.type = type;
		this.brand = brand;
		this.size = size;
		this.category = new ArrayList<>();
		this.isPacked = isPacked;
		this.code = code;
		this.expirationDate = expirationDate;
		this.precioCliente = precio;
		this.image = "./data/productImg/graysquare.png";
		this.puntos = precioCliente/1000;
	}
	
	public Product(String name, int code, int precio) {
		this.name = name;
		this.code = code;
		this.precioCliente = precio;
	}
	
	/**
	 * Cambia la categoria de un producto
	 */
	public void setCategory(List<String> category) {
		this.category = category;
	}
	
	/**
	 * Cambia el precio del producto
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Cambia el precio del producto
	 */
	public void setPrecioCliente(int precio) {
		this.precioCliente = precio;
	}
	
	/**
	 * Obtiene el precio para un cliente
	 * @return
	 */
	public int getPrecioCliente() {
		return precioCliente;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getSize() {
		return size;
	}

	@Override
	public List<String> getCategory() {
		return category;
	}

	@Override
	public boolean isPacked() {
		return isPacked;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public String getBrand() {
		return brand;
	}

	public List<Product> updateClientPrice(List<Product> listToUpdate, int price) {
		
		for (Product theProduct : listToUpdate ) {
			theProduct.setPrecioCliente(price);
		}
		
		return listToUpdate;
	}
	
	public String getListToString() {
		String m = "";
		int contador = 0;
		for (String i : category) {
			if (category.size() == 1) {
				return category.get(0);
			}
			else {
				if (contador != (category.size()-1)) {
					m += i + ".";
					contador += 1;
				}
				else
					m += i;
			}
		}
		return m;
	}
	
	/**
	 * Obtiene el valor por peso de un producto
	 * @param sizeP
	 * @return
	 */
	public float priceBySize(float sizeP) {
		String p = getSize();
		p = p.replaceAll("\\s","");
		int intValue = Integer.parseInt(p.replaceAll("[^0-9]", ""));
		float priceTemp = getPrecioCliente()/intValue;
		priceTemp = priceTemp * sizeP;
		return priceTemp;
	}

	/**
	 * Gets product image
	 * @return
	 */
	public String getImage() {
		return image;
	}
	/**
	 * Changes product image
	 * @param image
	 */
	public void setImage(String image) {
		this.image = "./data/productImg/" + image + ".png";
	}
	
	public void setImage2(String image) {
		this.image = image;
	}

	public float getPuntos() {
		return puntos;
	}
	
	public void setPuntos(float newPuntos) {
		this.puntos = newPuntos;
	}

}
	
	
