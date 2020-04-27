import java.util.Stack;
import java.util.ArrayList;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> recorrido;
    private ArrayList<Item> mochila;
    private int pesoActual;
    private int pesoMax;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        recorrido = new Stack<>();
        mochila = new ArrayList<>();
        pesoActual = 0;
        pesoMax = 5;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room patio, salaDelTrono, granSalon, granComedor, pasillo, aseo, mazmorra, aposentosCapGuardiaReal, pasilloSecreto;

        // Crea las salas
        patio = new Room("patio principal del castillo");
        salaDelTrono = new Room("la sala del trono");
        granSalon = new Room("el gran salón");
        granComedor = new Room("el gran comedor");
        pasillo = new Room("un largo pasillo");
        aseo = new Room("los baños");
        mazmorra = new Room("una mazmorra oculta");
        aposentosCapGuardiaReal = new Room("los aposentos privados del Capitán de la Guardia Real, al noroeste de la sala del trono");
        pasilloSecreto = new Room("un pasillo secreto al noreste de la mazmorra");

        // Creamos los objetos
        Item cuchillo = new Item("cuchillo", "Un simlple cuchillo del banquete, está afilado", 1);
        Item behelit = new Item("behelit", "Un amuleto muy raro que te provoca escalofríos", 1);
        behelit.setAdquirible(false);
        Item cartaInculpatoria = new Item("cartaInculpatoria", "Carta inculpatoria encontrada en los aposentos del Capitán de la Guardia Real", 1);
        Item vendasEnsangrentadas = new Item("vendasEnsangrentadas", "Vendas empapadas en sangre encontradas en los baños", 2);
        Item anillo = new Item("anillo", "Anillo de oro con un rubí incrustado", 1);
        Item ganzua = new Item("ganzua","Una herramienta para abrir puertas", 1);

        // Y ahora los añadimos a sus salas correspondientes
        patio.addItem(ganzua);
        granComedor.addItem(cuchillo);
        aseo.addItem(vendasEnsangrentadas);
        aseo.addItem(anillo);
        mazmorra.addItem(behelit);
        aposentosCapGuardiaReal.addItem(cartaInculpatoria);

        // Creamos el mapa (virtualmente hablando)
        // Salidas del patio
        patio.setExit("north", salaDelTrono);
        // Salidas de la sala del trono
        salaDelTrono.setExit("east", granSalon);
        salaDelTrono.setExit("south", patio);
        salaDelTrono.setExit("northWest", aposentosCapGuardiaReal);
        // Salidas del gran salon
        granSalon.setExit("east", pasillo);
        granSalon.setExit("south", granComedor);
        granSalon.setExit("west", salaDelTrono);
        // Salidas del gran comedor
        granComedor.setExit("north", granSalon);
        // Salidas del pasillo
        pasillo.setExit("north", aseo);
        pasillo.setExit("southEast", mazmorra);
        pasillo.setExit("west", granSalon);
        // Salidas del aseo
        aseo.setExit("south", pasillo);
        // Salidas de la mazmorra
        mazmorra.setExit("northWest", pasillo);
        mazmorra.setExit("northEast", pasilloSecreto);
        // Salidas de los aposentos del capitan de la guardia real
        aposentosCapGuardiaReal.setExit("southEast", salaDelTrono);
        // Salidas del pasillo secreto
        pasilloSecreto.setExit("southWest", mazmorra);

        currentRoom = patio;  // Comienza el juego en el patio del castillo
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("back")) {
            back();
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("items")) {
            items();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Imprime por pantalla la descripción de la sala actual y sus 
     * salidas.
     */
    private void printLocationInfo() {
        System.out.print(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Permite al jugador utilizar el comando look, que
     * nos muestra por pantalla la LongDescription de la 
     * sala en la que está.
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Permite utilizar el comando eat.
     */
    private void eat() {
        System.out.println("Acabas de comer y ya no tienes hambre");   
    }

    /**
     * Nos devuelve a la habitación anterior. Excepto que acabemos de empezar el juego
     * o hayamos rehecho nuestros pasos hasta el principio.
     */
    private void back() {
        if (!recorrido.empty()) {
            currentRoom = recorrido.pop();
            printLocationInfo();
        } else {
            System.out.println("No puedes hacer eso");
        }
    }
    
    /**
     * Método que nos permite ejecutar el comando "take". Coge un objeto de la
     * sala y se lo guarda, siempre y cuando no sobrepasemos el peso maximo.
     * 
     * @param command   El comando que se ha introducido
     */
    private void take(Command command) {
        ArrayList<Item> objetosSala = currentRoom.getListaItems();
        boolean nombreCorrecto = false;
        
        for (int i = 0; i < objetosSala.size(); i++) {
            Item item = objetosSala.get(i);
            if (item.getItemId().equals(command.getSecondWord())) {
                if ((pesoActual + item.getPesoItem()) <= pesoMax && item.getAdquirible()) {
                    mochila.add(item);
                    pesoActual += item.getPesoItem();
                    currentRoom.removeItem(item);
                    nombreCorrecto = true;
                    System.out.println("Has cogido " + item.getItemId());
                } else if (!item.getAdquirible()) {
                    System.out.println("No puedes coger ese objeto, de momento...");
                    nombreCorrecto = true;
                } else {
                    System.out.println("No puedes coger este objeto, llevas demasiado peso");
                    nombreCorrecto = true;
                }
            }
        }
        
        //Esta parte controla si se ha metido mal el nombre del objeto
        if (nombreCorrecto == false) {
            System.out.println("No ves ese objeto por ninguna parte.");
        }
    }
    
    /**
     * Ejecuta el comando "drop". Deja un objeto que haya cogido el jugador/a
     * en la sala actual.
     * 
     * @param command   El comando que se ha introducido
     */
    private void drop(Command command) {
        boolean nombreCorrecto = false;
        
        for (int i = 0; i < mochila.size(); i++) {
            Item item = mochila.get(i);
            if (item.getItemId().equals(command.getSecondWord())) {
                currentRoom.addItem(item);
                mochila.remove(item);
                nombreCorrecto = true;
                System.out.println(item.getItemId() + " se ha dejado en la sala.");
            }
        }
        
        if (nombreCorrecto == false) {
            System.out.println("¡No tienes ese objeto!");
        }
    }
    
    /**
     * Ejecuta el comando items. Muestra por pantalla la información de los objetos que 
     * ha recogido el jugador/a. Si no ha recogido ninguno, muestra entonces que no se 
     * tiene ningún objeto.
     */
    private void items() {
        if (mochila.isEmpty()) {
            System.out.println("No tienes objetos");
        } else {
            for (Item item : mochila) {
                System.out.println(item.getItemInfo());
            }
        }
    }
}
