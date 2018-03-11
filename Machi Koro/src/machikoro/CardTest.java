package machikoro;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class CardTest {

    public Card wheatField;
    public Card bakery;

    @Before
    public void setUp(){
        wheatField = new Card("Wheat Field", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "Wheat", 1, 1);
        bakery = new Card("Bakery", "Secondary Industry", "Get 1 coin from the bank, on your turn only.", "Bread", 1, 6);

    }

    @Test
    public void testToString(){
        Assert.assertEquals(wheatField.toString(), "Wheat Field (1)");
        Assert.assertEquals(bakery.toString(), "Bakery (6)");
    }

}
