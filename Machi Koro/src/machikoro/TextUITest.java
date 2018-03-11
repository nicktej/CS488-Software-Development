package machikoro;

import static org.junit.Assert.*;
import org.junit.*;


public class TextUITest {
    TextUI tui = new TextUI();

    @Test
    public void initializeGameInitalizesGame(){
        tui.initializeGame(4);
        assertTrue(tui.numPlayers == 4);
    }


}
