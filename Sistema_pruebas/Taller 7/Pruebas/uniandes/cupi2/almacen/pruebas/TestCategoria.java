package uniandes.cupi2.almacen.pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.cupi2.almacen.mundo.*;

class TestCategoria {

	private Categoria categoriaInit;
	private Categoria categoriaMore;
	
	@BeforeEach
	void setUp() throws Exception {
		categoriaInit = new Categoria( "202120", "Fritos" );
        
		BufferedReader in2 = new BufferedReader( new FileReader("./Taller 7 - Pruebas/data/datos.txt") );
        categoriaMore = new Categoria( in2.readLine( ), in2 );
	}

	@Test
	void test_listaNodosVacia() {
		List<NodoAlmacen> nodos = categoriaInit.darNodos();
		if (nodos.isEmpty() == false)
			fail("No deberia retornar una lista con nodos ya que se creo una categoria sin nodos");
	}

	@Test
	void test_listaNodos() {
		List<NodoAlmacen> nodos = categoriaMore.darNodos();
		if (nodos.isEmpty() == true)
			fail("Deberia retornar una lista no vacia conteniendo los nodos");
	}
	
	@Test
	void test_buscarPadreVacio() {
		Categoria padre = categoriaInit.buscarPadre("");
		if (padre != null)
			fail("La categoria padre deberia ser null ya que se toma una categoria sin nodos");
	}
	
	@Test
	void test_buscarPadreNoVacio() {
		List<NodoAlmacen> nodos = categoriaMore.darNodos();
		for (NodoAlmacen i : nodos) {
			Categoria padre = categoriaMore.buscarPadre(i.darIdentificador());
			if (padre == null)
				fail("La categoria padre no deberia ser null ya que se toma una categoria que sabemos que si tiene nodos");
		}
	}
	
	@Test
	void test_buscarNodo() {
		List<NodoAlmacen> nodos = categoriaMore.darNodos();
		for (NodoAlmacen i : nodos) {
			NodoAlmacen nodo = categoriaMore.buscarNodo(i.darIdentificador());
			if (nodo == null)
				fail("Nodo no deberia ser null ya que se estan buscando nodos existentes");
		}
	}
	
	@Test
	void test_agregarNodoExistente() {
		try {
			categoriaMore.agregarNodo("1", "x", "11", "x");
			fail("Deberia lanzar una excepcion debido a que se esta intentando agregar un nodo existente");
		}
		catch(AlmacenException e) {
			return;
		}
	}
	
	@Test
	void test_agregarNodoNoExistenteDifCategoria() {
		try {
			categoriaInit.agregarNodo("1", "x", "11", "x");
			
		}
		catch(AlmacenException e) {
			fail("No deberia lanzar una excepcion debido a que se esta intentando agregar un nodo no existente");
		}
	}
	
	@Test
	void test_agregarNodoNoExistenteCategoria() {
		try {
			categoriaMore.agregarNodo("1", "Categoria", "1133", "x");
		}
		catch(AlmacenException e) {
			fail("No deberia lanzar una excepcion debido a que se esta intentando agregar un nodo no existente");
		}
	}
	
	@Test
	void test_eliminarNodo() {
		Categoria categoria = categoriaMore;
		List<NodoAlmacen> nodos = categoria.darNodos();
		for (NodoAlmacen i : nodos) {
			NodoAlmacen cat = i;
			NodoAlmacen cat2 = categoria.eliminarNodo(i.darIdentificador());
			if (cat2 != cat)
				fail("El retorno deberia ser igual al nodo que se esta intentando eliminar");
			nodos = categoria.darNodos();
		}
		if (nodos.isEmpty() != false)
			fail("La lista deberia estar vacia ya que se eliminaron todos los nodos");
	}
		
	@Test
	void test_buscarProducto() {
		List<NodoAlmacen> nodos = categoriaMore.darNodos();
		for (NodoAlmacen i : nodos) {
			List<Producto> productos = i.darProductos();
			for (Producto m : productos) {
				Producto buscado = categoriaMore.buscarProducto(m.darCodigo());
				if (buscado != m) 
					fail("No deberian ser diferentes ya que este es el que se esta buscando y deberia ser retornado");
			}
		}
	}
	
	@Test
	void test_darMarcas() {
		List<Marca> marcas = categoriaMore.darMarcas();		
		if (marcas.isEmpty() == true)
			fail("La lista retornada no puede ser vacia ya que la categoria contiene nodos");
	}
	
	@Test
	void test_darPreorden() {
		List<NodoAlmacen> pre = categoriaMore.darPreorden();
		if (pre.isEmpty() == true)
			fail("La lista retornada no puede ser vacia ya que la categoria contiene nodos");
	}
	
	@Test
	void test_darPostOrden() {
		List<NodoAlmacen> pos = categoriaMore.darPosorden();
		if (pos.isEmpty() == true)
			fail("La lista retornada no puede ser vacia ya que la categoria contiene nodos");
	}
	
	@Test
	void test_darValorPrecioVenta() {
		double ventas = 0;
		List<NodoAlmacen> nodos = categoriaMore.darNodos();
		for (NodoAlmacen i : nodos) {
			ventas += i.darValorVentas();
		}
		double ventas2 = categoriaMore.darValorVentas();
		if (ventas != ventas2)
			fail("El valor de las ventas deberia ser igual ya que se realiza el mismo proceso solo que de forma diferente");
	}
}
	
	

