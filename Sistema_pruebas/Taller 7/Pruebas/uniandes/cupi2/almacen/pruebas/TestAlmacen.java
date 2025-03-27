package uniandes.cupi2.almacen.pruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.cupi2.almacen.mundo.*;

class TestAlmacen {

	private Almacen almacenVacio;
	private Almacen almacenLleno; 
	
	@BeforeEach
	public void setUp() throws Exception {
		almacenVacio = new Almacen(new File("./Taller 7 - Pruebas/data/datosVacio2.txt"));
		almacenLleno = new Almacen(new File("./Taller 7 - Pruebas/data/datos.txt"));
	} 
	
	/**
	@Test
	public void fakeSetUp() {
		try{
			@SuppressWarnings("unused")
			Almacen almacen = new Almacen(new File("./Taller 7 - Pruebas/data/datosVacio2.tx"));
			fail("Se debío haber generado una excepción de almacen ya que no se pudo crear correctamente");
		}
		catch(AlmacenException e) {
			return;
		}
	}
	**/
	
	@Test
	public void test_numCategorias(){
		List<NodoAlmacen> nodosVacio = almacenVacio.darCategoriaRaiz().darPreorden();
		assertEquals(1, nodosVacio.size(), "El numero de categorias debe ser 1");
		
		List<NodoAlmacen> nodosLleno = almacenLleno.darCategoriaRaiz().darPreorden();
		assertEquals(21, nodosLleno.size(), "El numero de categorias debe ser 21");
	}
	
	@Test
	public void test_darCategoriaRaiz(){
		Categoria raiz = almacenVacio.darCategoriaRaiz();
		assertEquals("Cupi2", raiz.darNombre(), "El nombre de la categoria raiz debería ser Cupi2 y se presentó: " + raiz.darNombre());
	}
	
	/**
	 * Se revisa el caso de que no se borre la categoria raiz, 
	 * en caso de que lo haga se terminaría la prueba con un fail
	 */
	@Test
	public void test_eliminarNodoCasoRaiz(){
		Categoria raiz = almacenVacio.darCategoriaRaiz();
		try {
			almacenVacio.eliminarNodo(raiz.darIdentificador());
			fail("No se deberia poder eliminar la categoria raiz");
		}
		catch(AlmacenException e) {
			//Se termina ya que sabemos que funcionó el metodo de forma correcta
			return;
		}
	}
	
	/**
	 * Se eliminan todos los nodos y se revisa si este metodo funciona correctamente
	 */
	@Test
	public void test_eliminarNodoCasoNoRaiz(){
		List<NodoAlmacen> nodos = almacenLleno.darCategoriaRaiz().darPreorden();
		Categoria raiz = almacenLleno.darCategoriaRaiz();
		int i = 1;
		while(i<nodos.size()) {
			NodoAlmacen nodo = nodos.get(i);
			String identi = nodo.darIdentificador();
			Categoria padre = raiz.buscarPadre(identi);
			try {
				Categoria padreNodo = almacenLleno.eliminarNodo(nodo.darIdentificador());
				if (padreNodo != padre)
					fail("La categoria padre deberia ser a la misma retornada");
				
			}
			catch(AlmacenException e) {
				fail("No debería haber una excepcion ya que se esta eliminando un nodo que existe");
			}
			i++;
		}
		
	}
	
	@Test
	public void test_agregarNodoExistente(){
		Almacen almacen = almacenLleno;
		try {
			almacen.agregarNodo("1", "x", "11", "Comida");
			fail("Deberia lanzar una excepcion ya que el identificador ya existe");
		}
		catch(AlmacenException e) {
			return;
		}
	}
	
	@Test
	public void test_agregarNodoNoExistente() {
		Almacen almacen = almacenVacio;
		try {
			almacen.agregarNodo("1", "x", "11", "Comida");
		}
		catch(AlmacenException e) {
			fail("No deberia lanzar una excepcion ya que el identificador no existe");
		}
	}
	
	@Test
	public void test_buscarNodo() {
		NodoAlmacen nodo = almacenLleno.buscarNodo("1");
		if (nodo == null)
			fail("No deberia ser null ya que el nodo existe");
	}
	
	@Test
	public void test_venderProducto() {
		Almacen almacen = almacenLleno;
		Categoria raiz = almacen.darCategoriaRaiz();
		List<Producto> products = raiz.darProductos();
		for(Producto i : products) {
			String codigo = i.darCodigo();
			int cantidad = i.darCantidadUnidadesVendidas();
			almacen.venderProducto(codigo, 2);
			if (cantidad == i.darCantidadUnidadesVendidas())			
				fail("Las unidades iniciales no deberian ser iguales a las vencidas ya que se vendieron algunos productos");
		}
	}
	
	@Test
	public void test_agregarProductoExistente() {
		Almacen almacen = almacenLleno;
		try {
			almacen.agregarProducto("1111", "31759941", "Perrito", "x", 20);
			fail("Deberia lanzar una excepcion ya que existe un producto con ese codigo");
		}
		catch(AlmacenException e) {
			return;
		}
	}
	
	@Test
	public void test_agregarProductoNoExistente() {
		Almacen almacen = almacenLleno;
		try {
			almacen.agregarProducto("1111", "2482", "Perrito", "x", 20);	
		}
		catch(AlmacenException e) {
			fail("No deberia lanzar una excepcion ya que no existe un producto con ese codigo");
		}
	}
	
	public void test_eliminarProducto() {
		Almacen almacen = almacenLleno;
		Categoria raiz = almacen.darCategoriaRaiz();
		List<Producto> products = raiz.darProductos();
		for (Producto i : products) {
			String codigo = i.darCodigo();
			almacen.eliminarProducto(codigo);	
		}
		List<Producto> products2 = raiz.darProductos();
		if (products2.isEmpty() == false) {
			fail("La lista de productos deberia estar vacia ya que se eliminaron todos");
		}
		
	}
	
}
