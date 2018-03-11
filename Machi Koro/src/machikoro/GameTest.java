package machikoro;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(4);
    }

    @Test
    public void coinsStartAtThree() {
        int coins = game.getPlayer(0).getCoins();
        Assert.assertTrue(coins == 3);
    }

    @Test
    public void coinsAreVisible() {
        game.getPlayer(0).setCoins(5);
        Assert.assertTrue(game.getPlayer(0).getCoins() == 5);
    }

    @Test
    public void coinsNeverNegative() {
        game.getPlayer(0).setCoins(-5);
        Assert.assertTrue(game.getPlayer(0).getCoins() == 0);
    }

    @Test
    public void rollSingleDieGivesNumberBetweenOneAndSix() {
        int roll = game.rollSingleDie();
        Assert.assertTrue(roll >= 1 && roll <= 6);
    }

    @Test
    public void rollDoubleDieGiveNumberBetweenTwoAndTwelve() {
        int roll = game.rollDoubleDie();
        Assert.assertTrue(roll >= 2 && roll <= 12);
    }
}

