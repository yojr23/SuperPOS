package module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lote implements LoteProduct{
	private String name;
	private String brand;
	private String type;
	private String size;
	private List<String> category;
	private boolean isPacked;
	private int code;
	private Date expirationDate;
	private Date fechaLlegada;
	private int cantidad;
	private int precioProveedor;
	
	public Lote(String name, String brand, String type, String size, List<String> category, boolean isPacked, int code, Date expirationDate, Date fechaLlegada,int cantidad, int precioProveedor) {
		this.name = name;
		this.brand = brand;
		this.size = size;
		this.type = type;
		this.category = new ArrayList<>();
		this.isPacked = isPacked;
		this.code = code;
		this.expirationDate = expirationDate;
		this.fechaLlegada = fechaLlegada;
		this.cantidad = cantidad;
		this.precioProveedor = precioProveedor;
		
	}
	
	/**
	 * Le resta a la cantidad del lote
	 */
	public void setMinusCantidad(int cantidad2) {
		this.cantidad = cantidad - cantidad2;
	}
	
	/**
	 * Le suma a la cantidad del lote
	 */
	public void setMoreCantidad(int cantidad2) {
		this.cantidad = cantidad + cantidad2;
	}
	
	/**
	 * Obtiene el precio de venta del proveedor
	 * @return
	 */
	public int getPrecioProveedor(){
		return precioProveedor;
	}
	
	/**
	 * Obtiene la fecha de llegada de un producto
	 * @return
	 */
	public Date getFechaLlegada() {
		return fechaLlegada;
	}
	
	/**
	 * Cambia la cantidad del producto
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	/**
	 * Halla el precio total pagado a los proveedores
	 * 
	 */
	public int precioProveedores() {
		return precioProveedor*cantidad;
	}
	
	/**
	 * Obtiene la cantidad del producto
	 * @return cantidad producto
	 */
	public int getCantidad() {
		return cantidad;
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
	/**
	 * Obtiene el valor por peso de un producto
	 * @param sizeP
	 * @return
	 */
	public float priceBySize(float sizeP) {
		String p = getSize();
		p = p.replaceAll("\\s","");
		int intValue = Integer.parseInt(p.replaceAll("[^0-9]", ""));
		float priceTemp = cantidad/intValue;
		return priceTemp;
	}

}
