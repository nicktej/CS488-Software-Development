package machikoro;
//
//import javax.smartcardio.Card;

public class Player {


    private int coins;

    private Deck deck;
    private Deck gameDeck;

    public Player() {
        setCoins(3);
        deck = new Deck(true);
        gameDeck = new Deck(false);
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int newBalance) {
        if (newBalance < 0) {
            coins = 0;
        } else {
            coins = newBalance;
        }
    }
    public int getLandmarks(){
        int n = 0;
        for(Card c : deck.cards){
            if(c.getType().equals("Landmarks")){
                n = n + c.getQuantity();
            }
        }

        return n;
    }

    public boolean construct(String cardName) {
        Card card = deck.getCard(cardName);
        Card supplyCard = gameDeck.getCard(cardName);
        if (card.getCost() <= coins) {
            card.setQuantity(card.getQuantity() + 1);
            supplyCard.setQuantity(supplyCard.getQuantity() - 1);
            coins = coins - card.getCost();
            return true;
        }
        return false;
    }

    public void addCoins(int i) {
        coins += i;
    }

    public Deck getDeck() {
        return deck;
    }


    public boolean hasCard(String cardName) {
        if (deck.getCard(cardName).getQuantity() > 0) {
            return true;
        }
        else {
            return false;
        }
    }
}