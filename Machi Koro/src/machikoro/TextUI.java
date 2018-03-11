package machikoro;

import java.util.Scanner;

public class TextUI {

    public static int numPlayers;

    private Game game;

    int currentRoll;

    /** Constructor makes a new instance of Game */
    public TextUI() {
        game = new Game();
    }

    /** Constructor makes a new instance of Game with specific number of players */
    public TextUI(int n) {
        game = new Game(n);
    }

    /**  Allow the user to specify the number of players in the game */
    public void initializeGame() {
        System.out.println("Enter the number of players:");
        Scanner scanner = new Scanner(System.in);
        numPlayers = Integer.parseInt(System.console().readLine());
        game.initializeGame(numPlayers);
    }

    /**  Allow the tester to specify the number of players in the game */
    public void initializeGame(int n) {
        numPlayers = n;
        game.initializeGame(numPlayers);
    }

    /** Our Game Loop: Print GameState, Roll Dice, Actions, Purchasing, etc.. */
    public void runGame() {

        // 0 -> player 1, player 1 starts the game
        int activePlayer = 0;

        Scanner scanner = new Scanner(System.in);
        String command = "";
        int numRoll = 0;

        boolean doubles = false;

        while(game.noWinners()) {

            doubles = false;

            // Assure that active player is always valid 0 -> num players
            activePlayer = activePlayer % numPlayers;

            // Print GameState
            System.out.println("The current game state is: ");
            gameState();
            System.out.println();

            // Prompt player to roll dice
            if (game.getPlayer(activePlayer).hasCard("Train Station")){
                System.out.println("Player " + (activePlayer + 1) + ", how many dice would you like to roll?");
                numRoll = scanner.nextInt();
                if (numRoll == 1) {
                    currentRoll = game.rollSingleDie();
                }
                else if (numRoll == 2) {
                    int roll1 = game.rollSingleDie();
                    int roll2 = game.rollSingleDie();
                    if (roll1 == roll2) {
                        doubles = true;
                        System.out.println("Doubles!");
                    }
                    currentRoll = roll1 + roll2;
                }
                else {
                    System.out.println("You failed to enter a correct number.");
                    System.exit(0);
                }
            }
            else { // Don't own a train station
                currentRoll = game.rollSingleDie();
            }
            System.out.println("You rolled " + currentRoll + "\n");


            //TODO: Determine actions that happen based on the roll, do them

            // Ask Current Player if they would like to buy a card
            System.out.println("\nWould you like to buy any cards? (y/n)");
            command = scanner.next().toLowerCase();
            if (command.equals("y")) {
                System.out.println("What would you like to buy?");
                command = scanner.next();
                if(command.equals(game.getSupply().getCard(command).title)){
                    game.getPlayer(activePlayer).construct(command);
                    game.getSupply().getCard(command).setQuantity(game.getSupply().getCard(command).getQuantity() - 1);
                }
            }


            // Go to next player if not doubles
            if(!doubles) {
                activePlayer++;
            }
        }

    }


    /** Gets all players from game and prints their toString method. */
    private void gameState() {
        for (int i=0; i < numPlayers; i++) {
            System.out.println("Player " + (i+1) + " has " + game.getPlayer(i).getCoins() + " coins and " + game.getPlayer(i).getDeck().toString());
        }
        System.out.println("The cards remaining in the supply are: \n" + game.getSupply().toString());
    }

    public static void main(String[] args) {
        TextUI textGame = new TextUI();
        textGame.initializeGame(3);
        textGame.runGame();
    }

}