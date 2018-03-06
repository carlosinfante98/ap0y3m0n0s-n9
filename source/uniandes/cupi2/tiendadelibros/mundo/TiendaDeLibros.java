/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n3_tiendaDeLibros
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.tiendadelibros.mundo;

import java.util.ArrayList;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

/**
 * Clase que representa la tienda de libros.
 */
public class TiendaDeLibros
{
	// -----------------------------------------------------------------
	// Constantes
	// -----------------------------------------------------------------
	/**
	 * Categorias que puede tener un libro
	 */
	public static final String[] CATEGORIAS = {"Cuento Infantil"," Romance"," Novela Histórica","Terror"," Biografía" , "Ciencia Ficción"};

	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * cabeza de la lista de libros 
	 */
	private Libro primerLibro;

	/**
	 * Cantidad actual en la caja de la tienda de libros.
	 */
	private double caja;


	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	/**
	 * Crea una nueva tienda de libros. <br>
	 * <b>post:</b> El primer libro del catálogo fue inicializado en null. <br>
	 * La caja fue inicializada en 1000000.
	 */
	public TiendaDeLibros( )
	{
		primerLibro=null;
		caja = 1000000;
	}

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Retorna el catálogo de libros.
	 * @return El catálogo de libros.
	 */
	public ArrayList<Libro> darCatalogo( )
	{
		ArrayList<Libro> rta=new ArrayList<>();
		Libro actual=primerLibro;
		while(actual!=null)
		{
			rta.add(actual);
			actual=actual.darSiguiente();
		}
		return rta;
	}


	/**
	 * Retorna el primer libro de la lista.
	 * @return El primer libro de la lista.
	 */
	public Libro darPrimerLibro( )
	{
		return primerLibro;
	}

	/**
	 * Retorna el valor actual en la caja.
	 * @return El valor actual en caja.
	 */
	public double darCaja( )
	{
		return caja;
	}

	/**
	 * Modifica el valor actual de la caja.
	 * @param pCaja El nuevo valor de la caja.
	 */
	public void cambiarCaja( double pCaja )
	{
		caja = pCaja;
	}

	/**
	 * Busca un libro por el título dado por parámetro. 
	 * @param pTitulo El titulo del libro que se quiere buscar. pTitulo != null && pTitulo != "".
	 * @return Si existe un libro con ese título, lo retorna. En caso contrario, retorna null.
	 */
	public Libro buscarLibroPorTitulo( String pTitulo )
	{
		Libro buscado =primerLibro;
		while( buscado!=null )
		{
			if(buscado.darTitulo().equalsIgnoreCase(pTitulo))
			{
				return buscado;
			}
			buscado=buscado.darSiguiente();
		}
		return null;
	}

	/**
	 * Busca un libro por el código ISBN dado por parámetro.
	 * @param pIsbn El código ISBN del libro que se quiere buscar. pIsbn != null && pIsbn != "".
	 * @return Si existe un libro con ese ISBN, lo retorna. En caso contrario, retorna null.
	 */
	public Libro buscarLibroPorISBN( String pIsbn )
	{
		Libro buscado =primerLibro;
		while( buscado!=null )
		{
			if(buscado.darIsbn().equalsIgnoreCase(pIsbn))
			{
				return buscado;
			}
			buscado=buscado.darSiguiente();
		}
		return null;
	}

	/**
	 * Registra un libro en la tienda de libros. <br>
	 * <b>post: </b> El libro fue creado y agregado al catálogo alfabéticamente.
	 * @param pTitulo El título del libro que se quiere agregar. pTitulo != null && pTitulo != "".
	 * @param pIsbn El código ISBN del libro que se quiere agregar. pIsbn != null && pIsbn != "".
	 * @param pPrecioVenta El precio de venta del libro que se quiere agregar. pPrecioVenta > 0.
	 * @param pPrecioCompra El precio de compra del libro que se quiere agregar. pPrecioCompra > 0.
	 * @param pCategoria Categoría a la que pertenece el libro. pCategoria != null && pCategoria != "". pCategoria pertenece a CATEGORIAS.
	 * @param pRutaImagen La ruta de la imagen del libro. pRutaImagen != null && pRutaImagen != "".
	 * @return El nuevo libro registrado en caso de que si se haya podido realizar la operación, null en caso de que el libro ya exista.
	 */
	public Libro registrarLibro( String pTitulo, String pIsbn, double pPrecioVenta, double pPrecioCompra, String pCategoria, String pRutaImagen )
	{
		// Comprueba si el libro con ese ISBN no ha sido creado
		Libro buscado = buscarLibroPorISBN( pIsbn );
		Libro nuevo = null;
		if( buscado == null )
		{
			nuevo = new Libro( pTitulo, pIsbn, pPrecioVenta, pPrecioCompra, pCategoria, pRutaImagen );

			if(primerLibro==null)
			{
				primerLibro=nuevo;
			}
			else {
				Libro actual=primerLibro;
				while(actual!=null)
				{
					if(actual.darTitulo().compareTo(pTitulo)<0)
					{
						Libro temp=actual.darSiguiente();
						actual.cambiarSiguiente(nuevo);
						nuevo.cambiarSiguiente(temp);
						nuevo.cambiarAnterior(actual);
						if(temp!=null)
						{
							temp.cambiarAnterior(nuevo);
						}
						break;
					}
					actual=actual.darSiguiente();
				}
			}

		}
		return nuevo;
	}

	/**
	 * Elimina un libro con el ISBN dado por parámetro. Si la cantidad actual de ejemplares es mayor a cero, no se eliminará el libro. <br>
	 * <b>post: </b> El libro fue eliminado del catálogo.
	 * @param pIsbn El ISBN del libro que se quiere eliminar. pIsbn != null && pIsbn != "".
	 * @return Retorna true si se pudo eliminar, false si el libro no existe o si la cantidad actual de ejemplares es mayor a cero.
	 */
	public boolean eliminarLibro( String pIsbn )
	{
		
		Libro buscado = buscarLibroPorISBN( pIsbn );
		if(buscado==null)
		{
			return false;
		}
		if(buscado.darCantidadActual()>0)
		{
			return false;
		}
		if( primerLibro.equals(buscado))
		{
			primerLibro=primerLibro.darSiguiente();
		}
		else if(buscado.darSiguiente()==null)
		{
			buscado.darAnterior().cambiarSiguiente(null);
		}
		else
		{
			buscado.darSiguiente().cambiarAnterior(buscado.darAnterior());
			buscado.darAnterior().cambiarSiguiente(buscado.darSiguiente());
		}
		return true;
	}

	/**
	 * Abastece un libro con la cantidad de ejemplares dada por parámetro. <br>
	 * <b>post: </b> Se abasteció el libro con el ISBN dado y se disminuyó la cantidad en caja con el precio final del abastecimiento.
	 * @param pIsbn El Código ISBN del libro que se quiere abastecer. pIsbn!= null && pISBN != "".
	 * @param pFecha La fecha en la que se realizó la transacción. pFecha != "" && pFecha != null.
	 * @param pCantidad La cantidad de ejemplares que se van a abastecer. pCantidad >= 0.
	 * @return Retorna true si se pudo abastecer el libro, false en caso contrario.
	 */
	public boolean abastecer( String pIsbn, int pCantidad, Date pFecha )
	{
		Libro buscado = buscarLibroPorISBN( pIsbn );
		boolean seAbastecio = false;
		if( buscado != null && caja >= pCantidad * buscado.darPrecioCompra( ) )
		{
			buscado.abastecer( pCantidad, pFecha );
			// Disminuye la caja con el valor total de los ejemplares abastecidos
			caja -= pCantidad * buscado.darPrecioCompra( );
			seAbastecio = true;
		}
		return seAbastecio;
	}

	/**
	 * Vende la cantidad de ejemplares del libro con el ISBN dado por parámetro. <br>
	 * <b>post: </b> Se vendió el libro con el ISBN dado y se aumentó la cantidad en caja con el precio final de la venta.
	 * @param pIsbn El Código ISBN del libro que se quiere vender. pIsbn != null && pIsbn != "".
	 * @param pCantidad La cantidad de ejemplares que se van a vender.
	 * @param pFecha La fecha en la que se realizó la transacción. pFecha != "" && pFecha != null.
	 * @return Retorna true en caso de que se pueda vender la cantidad de ejemplares dada por parámetro. False en caso contrario.
	 */
	public boolean vender( String pIsbn, int pCantidad, Date pFecha )
	{
		boolean vendido = false;
		Libro buscado = buscarLibroPorISBN( pIsbn );
		if( buscado != null )
		{
			vendido = buscado.vender( pCantidad, pFecha );
			// Aumenta la caja con el valor total de los ejemplares vendidos
			caja += pCantidad * buscado.darPrecioVenta( );
		}
		return vendido;
	}

	/**
	 * Busca el libro más costoso, es decir el libro con el mayor precio de venta en el catálogo.
	 * @return El libro más costoso. En caso de que el catalogo esté vacío, retorna null
	 */
	public Libro darLibroMasCostoso( )
	{
		// Guarda el libro más costoso y su precio
		Libro masCostoso = null;
		double precioMasCostoso = 0.0;
		Libro actual=primerLibro;

		while(actual!=null) {
			// Verifica si el libro actual tiene un precio mayor al que está guardado
			if( actual.darPrecioVenta( ) > precioMasCostoso )
			{
				masCostoso = actual;
				precioMasCostoso = actual.darPrecioVenta( );
			}
			actual=actual.darSiguiente();
		}


		return masCostoso;
	}

	/**
	 * Busca el libro más económico en el catálogo de libros. El libros más económico es el libro con el menor precio de venta.
	 * @return El libro menos costoso. En caso de que el catálogo esté vacío, retorna null.
	 */
	public Libro darLibroMasEconomico( )
	{
		Libro masEconomico = null;
		double precioMasEconomico = Double.MAX_VALUE;
		Libro actual=primerLibro;

		while(actual!=null) {
			// Verifica si el libro actual tiene un precio mayor al que está guardado
			if( actual.darPrecioVenta( ) < precioMasEconomico )
			{
				masEconomico = actual;
				precioMasEconomico = actual.darPrecioVenta( );
			}
			actual=actual.darSiguiente();
		}


		return masEconomico;
	}

	/**
	 * Busca el libro más vendido, es decir el libro con más transacciones de tipo VENTA.
	 * @return El libro más vendido. En caso de que el catálogo esté vacío, retorna null.
	 */
	public Libro darLibroMasVendido( )
	{
		Libro masVendido = null;
		int ventas = 0;
		// Recorre el catalogo de libros
		Libro libroActual=primerLibro;

		while(libroActual!=null) {
			int ventasActual = 0;
			// Recorre las transacciones del libro actual
			for( Transaccion transaccionActual : libroActual.darTransacciones( ) )
			{
				if( transaccionActual.darTipo( ).equals( Transaccion.Tipo.VENTA ) )
				{
					// Cuenta los libros vendidos en la transacción actual.
					ventasActual += transaccionActual.darCantidad( );
				}
			}
			// Verifica que las ventas actuales sean mayores a las ventas guardadas
			if( ventasActual > ventas )
			{
				masVendido = libroActual;
				ventas = ventasActual;
			}
			libroActual=libroActual.darSiguiente();
		}

		return masVendido;
	}

	/**
	 * Calcula la cantidad de transacciones de abastecimiento del libro con el ISBN dado por parámetro.
	 * @param pIsbn El código ISBN del libro que se quiere buscar. pIsbn != null && pIsbn != "".
	 * @return La cantidad de transacciones de abastecimiento. En caso de que no encuentre el libro o no tenga transacciones, retorna cero.
	 */
	public int darCantidadTransaccionesAbastecimiento( String pIsbn )
	{
		// Busca el libro con el ISBN dado por parámetro
		Libro buscado = buscarLibroPorISBN( pIsbn );
		int cantidadTransacciones = 0;
		// Verifica que si exista el libro
		if( buscado != null )
		{
			// Guarda las transacciones del libro buscado

			Transaccion actual=buscado.darPrimeraTransaccion();

			while(actual!=null) {
				// Verifica y cuenta las transacciones de tipo ABASTECIMIENTO
				if( actual.darTipo( ).equals( Transaccion.Tipo.ABASTECIMIENTO ) )
				{
					cantidadTransacciones++;
				}
				actual=actual.darSiguiente();
			}
		}
		return cantidadTransacciones;
	}

	// -----------------------------------------------------------------
	// Puntos de Extensión
	// -----------------------------------------------------------------

	/**
	 * Método para la extensión 1.
	 * @return Respuesta 1.
	 */
	public String metodo1( )
	{
		return "Respuesta 1";
	}

	/**
	 * Método para la extensión 2.
	 * @return Respuesta 2.
	 */
	public String metodo2( )
	{
		return "Respuesta 2";
	}

}
