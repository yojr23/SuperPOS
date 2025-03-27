package module;

import java.util.Date;
import java.util.List;

public interface LoteProduct {
	/**
	 * Obtiene el nombre del producto
	 * @return nombre producto
	 */
	public String getName();

	/**
	 * Obtiene el tipo del producto
	 * @return tipo producto
	 */
	public String getType();
	
	/**
	 * Obtiene el tamaño del producto
	 * @return tamaño producto
	 */
	public String getSize();

	/**
	 * Obtiene la categoria del producto
	 * @return categoria producto
	 */
	public List<String> getCategory();

	/**
	 * Obtiene la informacion de si un producto esta empaquetado o no
	 * @return true si es empaquetado false si no.
	 */
	public boolean isPacked();


	/**
	 * Obtiene el codigo de barras del producto
	 * @return codigo de barras producto
	 */
	public int getCode();

	/**
	 * Obtiene la fecha de vencimiento del producto
	 * @return fecha de vencimiento producto
	 */
	public Date getExpirationDate();

	/**
	 * Obtiene la marca del producto
	 * @return marca producto
	 */
	public String getBrand();


}
