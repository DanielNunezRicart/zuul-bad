import java.util.Stack;
import java.util.ArrayList;

/**
 * Clase Player
 * 
 * Clase que almacena los datos referentes al jugador.
 * 
 * @author Daniel N��ez Ricart 
 * @version 28/04/2020
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> recorrido; 
    private ArrayList<Item> inventario;

    /**
     * Constructor de la clase Player
     */
    public Player()
    {
        recorrido = new Stack<>();
        inventario = new ArrayList<>();
    }

    /**
     * @return  La habitaci�n en la que el jugador se encuentra actualmente.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * M�todo que cambia la habitaci�n en la que se encuentra el jugador.
     * 
     * @param newRoom   La nueva habitaci�n en la que va a estar el jugador.
     */
    public void setCurrentRoom(Room newRoom) {
        currentRoom = newRoom;
    }

    /**
     * Permite utilizar el comando eat.
     */
    public void eat() {
        System.out.println("Acabas de comer y ya no tienes hambre");   
    }

    /**
     * Nos devuelve a la habitaci�n anterior. Excepto que acabemos de empezar el juego
     * o hayamos rehecho nuestros pasos hasta el principio.
     */
    public void back() {
        if (!recorrido.empty()) {
            currentRoom = recorrido.pop();
            look();
        } else {
            System.out.println("No puedes hacer eso");
        }
    }

    /**
     * Permite al jugador utilizar el comando look, que
     * nos muestra por pantalla la LongDescription de la 
     * sala en la que est�.
     */
    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            recorrido.add(currentRoom);
            currentRoom = nextRoom;
            look();
        }
    }

    /**
     * M�todo que nos permite ejecutar el comando "take". Coge un objeto de la
     * sala y se lo guarda, siempre y cuando no sobrepasemos el peso maximo.
     * 
     * @param command   El comando que se ha introducido
     */
    public void take(Command command) {
        Item item = currentRoom.getItem(command.getSecondWord());

        if (item != null && item.getAdquirible()) {
            inventario.add(item);
            currentRoom.removeItem(item);
            System.out.println("Has cogido " + item.getId());
        } else if (!item.getAdquirible()) {
            System.out.println("No puedes coger ese objeto, de momento...");
        } else {
            System.out.println("No ves ese objeto por ninguna parte.");
        }
    }
}
