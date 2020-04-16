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
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;
    private Room southEastExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
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
            sala = northExit;
        }
        if (salida.equals("east")) {
            sala = eastExit;
        }
        if (salida.equals("south")) {
            sala = southExit;
        }
        if (salida.equals("west")) {
            sala = westExit;
        }
        if (salida.equals("southEast")) {
            sala = southEastExit;
        }

        return sala;
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        String texto = "Exits: ";

        texto += (northExit != null) ? "north " : "";
        texto += (eastExit != null) ? "east " : "";
        texto += (southEastExit != null) ? "southEast " : "";
        texto += (southExit != null) ? "south " : "";
        texto += (westExit != null) ? "west " : "";

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
            northExit = sala;
        }
        if (direccion.equals("east")) {
            eastExit = sala;
        }
        if (direccion.equals("south")) {
            southExit = sala;
        }
        if (direccion.equals("west")) {
            westExit = sala;
        }
        if (direccion.equals("southEast")) {
            southEastExit = sala;
        }
    }
}
