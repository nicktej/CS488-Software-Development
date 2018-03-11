package machikoro;

import org.junit.*;


public class LandmarksTest {

    private Game game;
    private Player player;
    private Deck deck;
    private Card trainStation;

    @Before
    public void setup() {
        game = new Game(4);
        player = game.getPlayer(1);
        deck = new Deck(false);
        trainStation = deck.getCard("Train Station");
    }
    @Test
    public void playerStartWithNoLandmarks() {
        Assert.assertTrue(player.getLandmarks() == 0);
        Assert.assertFalse(player.hasCard("Train Station"));
    }

    @Test
    public void landmarksAreConstructed(){
        player.setCoins(4);
        player.construct("Train Station");
        Assert.assertTrue(player.getLandmarks() == 1);
        Assert.assertTrue(player.getCoins() == 0);
        Assert.assertTrue(player.hasCard("Train Station"));
    }

}
