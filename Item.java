
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
    private String descripcion;
    private int peso;

    /**
     * Constructor de la clase Item.
     * Como no es necesario que haya objetos en todas las habitaciones,
     * se le pasará null como parámetro si no queremos que haya ninguno.
     * 
     * @param descripcionItem La descripción del objeto.
     * @param pesoItem  El peso del objeto
     */
    public Item(String descripcionItem, int pesoItem)
    {
        descripcion = descripcionItem;
        peso = pesoItem;
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
        String itemInfo = "Objeto: " + descripcion + " (peso " + peso + ")";
        return itemInfo;
    }
}
