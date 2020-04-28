import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> salidas;
    private ArrayList<Item> listaItems;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<String, Room>();
        listaItems = new ArrayList<>();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Devuelve la sala vecina a la actual que esta ubicada en la direccion indicada como parametro.
     *
     * @param salida Un String indicando la direccion por la que saldriamos de la sala actual
     * @return La sala ubicada en la direccion especificada o null si no hay ninguna salida en esa direccion
     */
    public Room getExit(String salida) {
        return salidas.get(salida);
    }

    /**
     * Devuelve la informaci�n de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripci�n de las salidas existentes.
     */
    public String getExitString() {
        String textoExits = "Exits: ";
        
        for (String salida : salidas.keySet()) {
            textoExits += salida + " ";
        }

        return textoExits;
    }

    /**
     * Define una salida para esta sala
     * 
     * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
     * @param sala La sala que se encuentra en la direccion indicada
     */
    public void setExit(String direccion, Room sala) {
        salidas.put(direccion, sala);
    }

    /**
     * Devuelve un texto con la descripcion completa de la habitacion, que 
     * incluye la descripcion corta de la sala y las salidas de la misma. Por ejemplo:
     *     You are in the lab
     *     Exits: north west southwest
     * @return Una descripcion completa de la habitacion incluyendo sus salidas
     */
    public String getLongDescription() {
        String descripcionCompleta = "Est�s en " + description + "\n";
        
        if (listaItems.size() > 0) {
            for (Item item : listaItems) {
                descripcionCompleta += item.getItemInfo() + "\n";
            }
        }
        
        descripcionCompleta += getExitString();
        return descripcionCompleta;
    }
    
    /**
     * A�ade un objeto nuevo a la sala.
     * 
     * @param objeto    El objeto que queremos a�adir
     */
    public void addItem(Item objeto) {
        listaItems.add(objeto);
    }
    
    /**
     * M�todo que devuelve un objeto pasado por par�metro (si est� en la sala),
     * o null si no lo est�.
     * 
     * @param objeto    El id del objeto que queremos devolver.
     * @return  El objeto buscado o null si no existe en esta sala.
     */
    public Item getItem(String objeto) {
        Item item = null;
        
        for (int i = 0; i < listaItems.size() && item == null; i++) {
            if (listaItems.get(i).getId().equals(objeto)) {
                item = listaItems.get(i);
            }
        }
        
        return item;
    }
    
    /**
     * Elimina un item de la sala.
     * 
     * @param objeto    El objeto que se pretende eliminar.
     */
    public void removeItem(Item objeto) {
        listaItems.remove(objeto);
    }
}
