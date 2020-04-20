import java.util.HashMap;

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
        Room sala = null;

        if (salida.equals("north")) {
            sala = salidas.get("north");
        }
        if (salida.equals("east")) {
            sala = salidas.get("east");
        }
        if (salida.equals("south")) {
            sala = salidas.get("south");
        }
        if (salida.equals("west")) {
            sala = salidas.get("west");
        }
        if (salida.equals("southEast")) {
            sala = salidas.get("southEast");
        }

        return sala;
    }

    /**
     * Devuelve la informaci�n de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripci�n de las salidas existentes.
     */
    public String getExitString() {
        String texto = "Exits: ";

        texto += (salidas.containsKey("north")) ? "north " : "";
        texto += (salidas.containsKey("east")) ? "east " : "";
        texto += (salidas.containsKey("southEast")) ? "southEast " : "";
        texto += (salidas.containsKey("south")) ? "south " : "";
        texto += (salidas.containsKey("west")) ? "west " : "";

        return texto;
    }

    /**
     * Define una salida para esta sala
     * 
     * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
     * @param sala La sala que se encuentra en la direccion indicada
     */
    public void setExit(String direccion, Room sala) {
        if (direccion.equals("north")) {
            salidas.put("north", sala);
        }
        if (direccion.equals("east")) {
            salidas.put("east", sala);
        }
        if (direccion.equals("south")) {
            salidas.put("south", sala);
        }
        if (direccion.equals("west")) {
            salidas.put("west", sala);
        }
        if (direccion.equals("southEast")) {
            salidas.put("southEast", sala);
        }
    }
}
