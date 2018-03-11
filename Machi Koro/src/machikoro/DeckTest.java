package machikoro;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeckTest {

    Deck playerDeck;
    Deck supplyDeck;

    @Before
    public void setUp() {
        playerDeck= new Deck(true);
        supplyDeck = new Deck(false);
    }

    @Test
    public void testStartingCards() {
        for (Card c : playerDeck.cards) {
            if (c.getQuantity() > 0) {
                Assert.assertTrue(c.getTitle().equals("Wheat Field") || c.getTitle().equals("Bakery"));
            }
        }
    }

    @Test
    public void testSupplyCard(){
        for(Card c : supplyDeck.cards){
            if (c.getType().equals("Landmarks") || c.getType().equals("Major")) {
                Assert.assertEquals(c.getQuantity(), 4);
            }
            else {
                Assert.assertEquals(c.getQuantity(), 6);
            }
        }
    }

    @Test
    public void testToStringWorks() {
        Assert.assertEquals("Wheat Field (1) Bakery (1) ", playerDeck.toString());
    }

    @Test
    public void testBuild(){
        playerDeck.build("Wheat Field");
        Assert.assertEquals(playerDeck.getCard("Wheat Field").getQuantity(), 2);
    }

}

