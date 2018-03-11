package machikoro;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private ArrayList<Player> players;
    private Deck supply;
    private int numPlayers;
    private Player currentPlayer;

    public Game() {
        supply = new Deck(false);
        players = new ArrayList<>();
    }

    public Game(int n) {
        supply = new Deck(false);
        players = new ArrayList<>();
        initializeGame(n);
    }

    /**
     * Allow the user to specify the number of players in the game
     */
    public void initializeGame(int n) {
        numPlayers = n;
        while (n > 0) {
            players.add(new Player());
            n--;
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer(int playerNumber) {
        return players.get(playerNumber);
    }

    public Deck getSupply() {
        return supply;
    }

    public int rollSingleDie() {
        Random roll = new Random();
        return roll.nextInt(6) + 1;
    }

    public int rollDoubleDie() {
        return rollSingleDie() + rollSingleDie();
    }


    // TODO: Write this
    public boolean noWinners() {
        return true;
    }

}