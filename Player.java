import java.util.Stack;

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

    /**
     * Constructor de la clase Player
     */
    public Player()
    {
        recorrido = new Stack<>();
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
}
