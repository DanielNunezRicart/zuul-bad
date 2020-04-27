
/**
 * Clase Item
 * 
 * Clase que almacena los datos referentes a objetos que podemos
 * encontrar en las distintas habitaciones del juego.
 * 
 * @author Daniel Núñez Ricart 
 * @version 22/04/2020
 */
public class Item
{
    private String id;
    private String descripcion;
    private int peso;
    private boolean adquirible;

    /**
     * Constructor de la clase Item.
     * Como no es necesario que haya objetos en todas las habitaciones,
     * se le pasará null como parámetro si no queremos que haya ninguno.
     * 
     * @param idLibro   El id del libro.
     * @param descripcionItem La descripción del objeto.
     * @param pesoItem  El peso del objeto.
     */
    public Item(String idLibro, String descripcionItem, int pesoItem)
    {
        id = idLibro;
        descripcion = descripcionItem;
        peso = pesoItem;
        adquirible = true;
    }

    /**
     *  @return La descripción del objeto
     */
    public String getDescripcionItem() {
        return descripcion;
    }
    
    /**
     * @return  El peso del objeto
     */
    public int getPesoItem() {
        return peso;
    }
    
    /**
     * @return  Toda la información referente al objeto
     */
    public String getItemInfo() {
        String itemInfo = id + ": " + descripcion + " (peso " + peso + ")";
        return itemInfo;
    }
    
    /**
     * @return  El id del objeto.
     */
    public String getItemId() {
        return id;
    }
    
    /**
     * Cambia el valor del atributo adquirible (boolean).
     * 
     * @param valor Booleano que indica el nuevo valor del atributo adquirible
     */
    public void setAdquirible(boolean valor) {
        adquirible = valor;
    }
    
    /**
     * @return  Devuelve un booleano que indica si puedes añadir este objeto
     * a tu inventario.
     */
    public boolean getAdquirible() {
        return adquirible;
    }
}
