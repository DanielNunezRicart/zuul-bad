import java.util.Stack;
import java.util.ArrayList;

/**
 * Clase Player
 * 
 * Clase que almacena los datos referentes al jugador.
 * 
 * @author Daniel Núñez Ricart 
 * @version 28/04/2020
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> recorrido; 
    private ArrayList<Item> inventario;
    private int pesoActual;

    /**
     * Constructor de la clase Player
     */
    public Player()
    {
        recorrido = new Stack<>();
        inventario = new ArrayList<>();
        pesoActual = 0;
    }

    /**
     * @return  La habitación en la que el jugador se encuentra actualmente.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Método que cambia la habitación en la que se encuentra el jugador.
     * 
     * @param newRoom   La nueva habitación en la que va a estar el jugador.
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
     * Nos devuelve a la habitación anterior. Excepto que acabemos de empezar el juego
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
     * sala en la que está.
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
     * Método que nos permite ejecutar el comando "take". Coge un objeto de la
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
            pesoActual += item.getPesoItem();
        } else if (item != null &&!item.getAdquirible()) {
            System.out.println("No puedes coger ese objeto, de momento...");
        } else if (item == null) {
            System.out.println("No ves ese objeto por ninguna parte.");
        }
    }

    /**
     * Ejecuta el comando items. Muestra por pantalla la información de los objetos que 
     * ha recogido el jugador. Si no ha recogido ninguno, muestra entonces que no se 
     * tiene ningún objeto.
     */
    public void items() {
        if (inventario.isEmpty()) {
            System.out.println("No tienes objetos en el inventario");
        } else {
            for (Item item : inventario) {
                System.out.println(item.getItemInfo());
            }
            System.out.println("Peso total: " + pesoActual);
        }
    }

    /**
     * Ejecuta el comando "drop". Deja un objeto que haya cogido el jugador
     * en la sala actual.
     * 
     * @param command   El comando que se ha introducido
     */
    public void drop(Command command) {
        Item item = null;

        if (!inventario.isEmpty()) {
            for (int i = 0; i < inventario.size() && item == null; i++) {
                item = inventario.get(i);
                if (item.getId().equals(command.getSecondWord())) {
                    currentRoom.addItem(item);
                    inventario.remove(item);
                    pesoActual -= item.getPesoItem();
                    System.out.println(item.getId() + " se ha dejado en la sala.");
                }
            }
            if (item == null) {
                System.out.println("¡No tienes ese objeto!");
            }
        } else {
            System.out.println("¡No tienes objetos!");
        }
    }
}
