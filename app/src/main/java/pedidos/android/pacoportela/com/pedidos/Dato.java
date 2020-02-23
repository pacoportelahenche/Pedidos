package pedidos.android.pacoportela.com.pedidos;

import java.util.Comparator;

/**
 * Creada por Francisco Portela Henche el 10/02/2020.
 * Esta clase crea un Dato que contendrá la información de uno de los productos de Danone.
 */
public class Dato implements Comparable{
    private String nombre;
    private String ctaPedida;
    private String ctaDevolucion;

    /**
     * Constructor sin parámetros. Crea un Dato vacio.
     */
    public Dato(){
        this.nombre = "";
        this.ctaPedida = "";
        this.ctaDevolucion = "";
    }

    /**
     * Constructor con parámetros. Creo un Dato con la información pasada en los parámetros.
     * @param nom el nombre del producto.
     * @param cped la cantidad a pedir del producto.
     * @param cdev la cantidad a devolver del producto.
     */
    public Dato (String nom, String cped, String cdev){
        this.nombre = nom;
        this.ctaPedida = cped;
        this.ctaDevolucion = cdev;
    }

    /**
     * Obtiene el nombre del producto.
     * @return el nombre.
     */
    public String getNombre(){
        return this.nombre;
    }

    /**
     * Pone el nombre a un producto.
     * @param nom el nombre a poner.
     */
    public void setNombre(String nom){ this.nombre = nom; }

    /**
     * Obtiene la cantidad a pedir.
     * @return la cantidad a pedir.
     */
    public String getCtaPedida(){
        return this.ctaPedida;
    }

    /**
     * Pone la cantidad a pedir.
     * @param cta la cantidad a pedir.
     */
    public void setCtaPedida(String cta){
        this.ctaPedida = cta;
    }

    /**
     * Obtiene la cantidad a devolver.
     * @return la cantidad a devolver.
     */
    public String getCtaDevolucion(){
        return this.ctaDevolucion;
    }

    /**
     * Pone la cantidad a devolver.
     * @param dev la cantidad a devolver.
     */
    public void setCtaDevolucion(String dev){
        this.ctaDevolucion = dev;
    }

    /**
     * Compara un Dato con otro Dato.
     * @param otro el otro Dato a comparar.
     * @return un int que indica cual es mayor.
     */
    @Override
    public int compareTo(Object otro) {
        Dato d = (Dato)otro;
        return nombre.substring(2).compareTo(d.getNombre().substring(2));
    }
}
