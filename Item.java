
/**
 * Clase Item
 * 
 * Clase que almacena los datos referentes a objetos que podemos
 * encontrar en las distintas habitaciones del juego.
 * 
 * @author Daniel N��ez Ricart 
 * @version 22/04/2020
 */
public class Item
{
    private String id;
    private String descripcion;
    private int peso;
    private boolean adquirible;
    private boolean equipable;
    private boolean equiped;

    /**
     * Constructor de la clase Item.
     * Como no es necesario que haya objetos en todas las habitaciones,
     * se le pasar� null como par�metro si no queremos que haya ninguno.
     * 
     * @param descripcionItem La descripci�n del objeto.
     * @param pesoItem  El peso del objeto
     */
    public Item(String idItem, String descripcionItem, int pesoItem, boolean sePuedeEquipar)
    {
        id = idItem;
        descripcion = descripcionItem;
        peso = pesoItem;
        adquirible = true;
        equipable = sePuedeEquipar;
        equiped = false;
    }

    /**
     * @return  El id del objeto.
     */
    public String getId() {
        return id;
    }

    /**
     *  @return La descripci�n del objeto
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
     * @return  Devuelve un booleano que indica si puedes a�adir este objeto
     * a tu inventario.
     */
    public boolean getAdquirible() {
        return adquirible;
    }

    /**
     * @return  Devuelve un booleano que indica si es posible que el jugador se 
     * equipe este objeto.
     */
    public boolean getEquipable() {
        return equipable;
    }

    /**
     * @return  Devuelve un booleano que indica si el jugador se ha equipado ese objeto.
     */
    public boolean getEquiped() {
        return equiped;
    }
    
    /**
     * @return  La informaci�n b�sica del objeto en un String.
     */
    public String getItemInfo() {
        String itemInfo = id +": " + descripcion + " (peso " + peso + ")";
        return itemInfo;
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
     * Cambia el valor del atributo equiped (boolean).
     * 
     * @param valor Booleano que indica el nuevo valor del atributo equiped
     */
    public void setEquiped(boolean valor) {
        equiped = valor;
    }
}
