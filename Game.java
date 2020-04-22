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

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room patio, salaDelTrono, granSalon, granComedor, pasillo, aseo, mazmorra, aposentosCapGuardiaReal, pasilloSecreto;

        // Crea los objetos
        Item cuchillo = new Item("Un cuchillo de cocina", 1);
        Item amuletoExtraño = new Item("Un amuleto muy raro que te provoca escalofríos", 1);
        Item cartaInculpatoria = new Item("Carta inculpatoria encontrada en los aposentos del Capitán de la Guardia Real", 1);
        
        // Crea las salas
        patio = new Room("patio principal del castillo", null);
        salaDelTrono = new Room("la sala del trono", null);
        granSalon = new Room("el gran salón", null);
        granComedor = new Room("el gran comedor", cuchillo);
        pasillo = new Room("un largo pasillo", null);
        aseo = new Room("los baños", null);
        mazmorra = new Room("una mazmorra oculta", amuletoExtraño);
        aposentosCapGuardiaReal = new Room("los aposentos privados del Capitán de la Guardia Real, al noroeste de la sala del trono", cartaInculpatoria);
        pasilloSecreto = new Room("un pasillo secreto al noreste de la mazmorra", null);

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
}
