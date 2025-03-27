package module;

import java.util.Date;
import java.util.List;

public class Promocion {
	private String name;
	private String type;
	private List<Product> products;
	private Date initialDate;
	private Date finalDate;
	private int code;
	private Product producto;
	private double descuento;
	private int initial;
	private int finalP;
	private int bonus;
	private int precio;
	
	//Constructor for bonus points
	public Promocion(String name, String type, List<Product> products, Date initialDate, Date finalDate) {
		this.name = name;
		this.type = type;
		this.products = products;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
	}
	
	//Constructor for combos
	public Promocion(String name, String type, List<Product> products, Date initialDate, Date finalDate, int code, int precio) {
		this.name = name;
		this.type = type;
		this.products = products;
		this.code = code;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.precio = precio;
	}
	
	//Constructor for gifts
	public Promocion(String name, String type, Product producto, Date initialDate, Date finalDate, int initial, int finalp) {
		this.name = name;
		this.type = type;
		this.producto = producto;
		this.descuento = Math.abs((descuento/100)-1);
		this.initial = initial;
		this.finalP = finalp;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
	}
	
	//Constructor for plain promotions
	public Promocion(String name, String type, Product producto, Date initialDate, Date finalDate, int descuento, String x) {
		if (x.equals("n")) {
			this.name = name;
			this.type = type;
			this.producto = producto;
			this.descuento = 1-((double)descuento)/100;
			this.initialDate = initialDate;
			this.finalDate = finalDate;
		}
		else if(x.equals("m")) {
			this.name = name;
			this.type = type;
			this.producto = producto;
			this.bonus = descuento;
			this.initialDate = initialDate;
			this.finalDate = finalDate;
		}
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}

	public String getType() {  
		return type;
	}

	public List<Product> getProducts() {
		return products;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public int getCode() {
		return code;
	}

	public Product getProducto() {
		return producto;
	}

	public double getDescuento() {
		return descuento;
	}

	public int getInitial() {
		return initial;
	}

	public int getFinalP() {
		return finalP;
	}

	public int getBonus() {
		return bonus;
	}

	public int getPrecio() {
		return precio;
	}

	
}


