package machikoro;

import java.util.ArrayList;

public class Deck {

    ArrayList<Card> cards;

    /**
     * Constructs a Deck for a Player or the Game supply.
     * @param player    true if constructing a Deck for a Player.
     */
    public Deck(boolean player) {
        cards = new ArrayList<Card>();
        cards.add(new WheatField(player));
        cards.add(new Ranch());
        cards.add(new Forest());
        cards.add(new Mine());
        cards.add(new AppleOrchard());

        cards.add(new Bakery(player));
        cards.add(new ConvenienceStore());
        cards.add(new CheeseFactory());
        cards.add(new FurnitureFactory());
        cards.add(new FruitAndVegetableMarket());

        cards.add(new Stadium());
        cards.add(new TVStation());
        cards.add(new BusinessCenter());

        cards.add(new TrainStation());
        cards.add(new ShoppingMall());
        cards.add(new AmusementPark());
        cards.add(new RadioTower());

        cards.add(new Cafe());
        cards.add(new FamilyRestaurant());

        if (player) {
            for (Card c : cards) {
                c.setQuantity(0);
            }

            getCard("Wheat Field").setQuantity(1);
            getCard("Bakery").setQuantity(1);
        }
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getQuantity() > 0) {
                s += cards.get(i).toString() + " ";
//                if (i != cards.size() - 1) {
//                    s += ", ";
//                } else {
//                    s += ".";
//                }
            }
        }
        return s;
    }

    /**
     * Returns a card with the given String for a title.
     * Throws exception if passed invalid String.
     * @param name name of card to return
     * @return Card
     */
    public Card getCard(String name){
        for(Card c : cards){
            if(c.getTitle().toLowerCase().equals(name.toLowerCase())){
                return c;
            }
        }
        throw new IllegalArgumentException("Card not found");
    }

    /**
     * Increases the quantity of the named card by one.
     * @param name Name of cad to build
     */
    public void build(String name){
        Card c = getCard(name);
        c.setQuantity(c.getQuantity()+1);
    }

    /**
     * @param icon Icon next to the card name
     * @return number of cards with the given icon in this Deck
     */
    public int getQuantityIcon(String icon) {
        int count = 0;
        for (Card c : cards) {
            if (c.getIcon().equals(icon)) {
                count += c.getQuantity();
            }
        }
        return count;
    }


    public class WheatField extends Card {

        public WheatField(boolean player) {
            super("Wheat Field", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "Wheat", 1, 6);
            if(player){
                setQuantity(1);
            }
            coins = 1;
            activation.add(1);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(1);
            }
        }

    }

    public class Ranch extends Card {

        public Ranch() {
            super("Ranch", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "Ranch", 1, 6);
            coins = 1;
            activation.add(2);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins);
            }

        }

    }

    public class Forest extends Card {

        public Forest() {
            super("Forest", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "Gear", 3, 6);
            coins = 1;
            activation.add(5);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins);
            }

        }

    }

    public class Mine extends Card {

        public Mine() {
            super("Mine", "Primary Industry", "You get 5 coin from the bank, on anyone's turn.", "Gear", 6, 6);
            coins = 5;
            activation.add(9);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins);
            }

        }

    }

    public class AppleOrchard extends Card {

        public AppleOrchard() {
            super("Apple Orchard", "Primary Industry", "You get 3 coin from the bank, on anyone's turn.", "Wheat", 3, 6);
            coins = 3;
            activation.add(10);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins);
            }

        }

    }

    public class Bakery extends Card {

        public Bakery(boolean player) {
            super("Bakery", "Secondary Industry", "Get 1 coin from the bank, on your turn only.", "Bread", 1, 6);
            if(player){
                setQuantity(6);
            }
            coins = 1;
            activation.add(2);
            activation.add(3);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins);
            }

        }

    }

    public class ConvenienceStore extends Card {

        public ConvenienceStore() {
            super("Convenience Store", "Secondary Industry", "Get 3 coin from the bank, on your turn only.", "Bread", 2, 6);
            coins = 3;
            activation.add(4);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins);
            }

        }

    }

    public class CheeseFactory extends Card {

        public CheeseFactory() {
            super("Cheese Factory", "Secondary Industry", "Get 3 coin from the bank, for each Ranch establishment that you own, on your turn only.", "Factory", 5, 6);
            coins = 3;
            activation.add(4);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins * p.getDeck().getQuantityIcon("Ranch")); //
            }

        }

    }

    public class FurnitureFactory extends Card {

        public FurnitureFactory() {
            super("Furniture Factory", "Secondary Industry", "Get 3 coin from the bank, for each Gear establishment that you own, on your turn only.", "Factory", 3, 6);
            coins = 3;
            activation.add(8);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins * p.getDeck().getQuantityIcon("Gear")); //
            }

        }

    }

    public class FruitAndVegetableMarket extends Card {

        public FruitAndVegetableMarket() {
            super("Fruit and Vegetable Market", "Secondary Industry", "Get 2 coin from the bank, for each Wheat establishment that you own, on your turn only.", "Fruit", 2, 6);
            coins = 2;
            activation.add(11);
            activation.add(12);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                p.addCoins(coins * p.getDeck().getQuantityIcon("Wheat"));
            }
        }
    }

    public class Cafe extends Card {

        public Cafe() {
            super("Cafe", "Restaurants", "Get 1 coin from the player who rolled the dice.", "Mug", 2, 6);
            coins = 1;
            activation.add(3);
        }

        public void cardAction(Game g, Player p, int die) {
            if (activation.contains(die)) {
                if (g.getCurrentPlayer().getCoins() > 0) {
                    p.addCoins(coins);
                    g.getCurrentPlayer().addCoins(-coins);
                }
            }
        }

    }

    public class TrainStation extends Card {

        public TrainStation() {
            super("Train Station", "Landmarks", "Train station effect", "Train", 4, 4);
            coins = 0;
        }

        public void cardAction(Game g, Player p, int die) {

        }
        //add landmarks's effect here
    }

    public class ShoppingMall extends Card {

        public ShoppingMall() {
            super("Shopping Mall", "Landmarks", "shopping mall effect", "Train", 10, 4);
            coins = 0;
        }

        //add landmarks's effect here
    }

    public class AmusementPark extends Card {

        public AmusementPark() {
            super("Amusement Park", "Landmarks", "amusement park effect", "Train", 16, 4);
            coins = 0;
        }

        //add landmarks's effect here
    }

    public class RadioTower extends Card {

        public RadioTower() {
            super("Radio Tower", "Landmarks", "radio tower effect", "Train", 22, 4);
            coins = 0;
        }

        //add landmarks's effect here
    }


    public class FamilyRestaurant extends Card {

        public FamilyRestaurant() {
            super("Family Restaurant", "Restaurants", "Get 2 coin from the player who rolled the dice.", "Mug", 3, 6);
            coins = 2;
            activation.add(9);
            activation.add(10);
        }

        public void cardAction(Game g, Player p, int die) {
//            if (activation.contains(die)) {
//                if (g.getCurrentPlayer().getCoins() > 1) {
//                    p.addCoins(coins);
//                    g.getCurrentPlayer().addCoins(-coins);
//                } else if (g.getCurrentPlayer().getCoins == 1) {
//                    p.addCoins(1);
//                    g.getCurrentPlayer().addCoins(-1);
//                }
//            }
        }

    }

    public class Stadium extends Card {

        public Stadium() {
            super("Stadium", "Major", "Get 2 coin from the player who rolled the dice.", "Mug", 6, 4);
            coins = 2;
            activation.add(9);
            activation.add(10);
        }

        public void cardAction(Game g, Player p, int die) {
//            if (activation.contains(die)) {
//                if (g.getCurrentPlayer().getCoins() > 1) {
//                    p.addCoins(coins);
//                    g.getCurrentPlayer().addCoins(-coins);
//                } else if (g.getCurrentPlayer().getCoins == 1) {
//                    p.addCoins(1);
//                    g.getCurrentPlayer().addCoins(-1);
//                }
//            }
        }

    }

    public class TVStation extends Card {

        public TVStation() {
            super("TV Station", "Major", "Get 2 coin from the player who rolled the dice.", "Mug", 7, 4);
            coins = 2;
            activation.add(9);
            activation.add(10);
        }

        public void cardAction(Game g, Player p, int die) {
//            if (activation.contains(die)) {
//                if (g.getCurrentPlayer().getCoins() > 1) {
//                    p.addCoins(coins);
//                    g.getCurrentPlayer().addCoins(-coins);
//                } else if (g.getCurrentPlayer().getCoins == 1) {
//                    p.addCoins(1);
//                    g.getCurrentPlayer().addCoins(-1);
//                }
//            }
        }

    }

    public class BusinessCenter extends Card {

        public BusinessCenter() {
            super("Business Center", "Major", "Get 2 coin from the player who rolled the dice.", "Mug", 8, 4);
            coins = 2;
            activation.add(9);
            activation.add(10);
        }

        public void cardAction(Game g, Player p, int die) {
//            if (activation.contains(die)) {
//                if (g.getCurrentPlayer().getCoins() > 1) {
//                    p.addCoins(coins);
//                    g.getCurrentPlayer().addCoins(-coins);
//                } else if (g.getCurrentPlayer().getCoins == 1) {
//                    p.addCoins(1);
//                    g.getCurrentPlayer().addCoins(-1);
//                }
//            }
        }

    }

}
