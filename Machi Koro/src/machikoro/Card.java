package machikoro;

import java.util.ArrayList;

public class Card {

    protected String title;

    protected ArrayList<Integer> activation;

    protected String effect;

    protected String icon;

    protected int cost;

    private int quantity;

    private String type;

    protected int coins; // amount of coins gained when card activated

    public Card(String title, String type, String effect, String icon, int cost, int quantity) {
        this.title = title;
        this.effect = effect;
        this.icon = icon;
        this.cost = cost;
        this.quantity = quantity;
        this.type = type;
        activation = new ArrayList<Integer>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Integer> getActivation() {
        return activation;
    }

    public void setActivation(ArrayList<Integer> activation) {
        this.activation = activation;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }


    public String toString() {
        return title + " (" + quantity + ")";
    }

    public String toStringGUI() {
        return "Cost: (" + cost + ") / " + "Quantity: (" + quantity + ")";
    }
}

/*
WheatField = new Card("Wheat Field", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "1", "Wheat", 1, 6);
        Ranch = new Card("Ranch", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "2", "Ranch", 1, 6);
        Forest = new Card("Forest", "Primary Industry", "You get 1 coin from the bank, on anyone's turn.", "5", "Gear", 3, 6);
        Mine = new Card("Mine", "Primary Industry", "You get 5 coin from the bank, on anyone's turn.", "9", "Gear", 6, 6);
        Bakery = new Card("Apple Orchard", "Primary Industry", "You get 3 coin from the bank, on anyone's turn.", "10", "Wheat", 3, 6);
        AppleOrchard = new Card("Bakery", "Secondary Industry", "Get 1 coin from the bank, on your turn only.", "2-3", "Bread", 1, 6);
       ConvenienceStore = new Card("Convenience Store", "Secondary Industry", "Get 3 coin from the bank, on your turn only.", "4", "Bread", 2, 6);
        CheeseFactory = new Card("Cheese Factory", "Secondary Industry", "Get 3 coin from the bank, for each Ranch establishment that you own, on your turn only.", "7", "Factory", 5, 6);
        FurnitureFactory = new Card("Furniture Factory", "Secondary Industry", "Get 3 coin from the bank, for each Gear establishment that you own, on your turn only.", "8", "Factory", 3, 6);
        FruitAndVegetableMarket = new Card("Fruit and Vegetable Market", "Secondary Industry", "Get 2 coin from the bank, for each Wheat establishment that you own, on your turn only.", "11-12", "Fruit", 2, 6);
        Cafe = new Card("Cafe", "Restaurants", "Get 1 coin from the player who rolled the dice.", "3", "Mug", 2, 6);
        FamilyRestaurant = new Card("Family Restaurant", "Restaurants",  "Get 2 coin from the player who rolled the dice.", "9-10", "Mug", 3, 6);
        Stadium = new Card("Stadium", "Major Establishment",  "Get 2 coin from all players, on your turn only.", "6", "Major", 6, 4);
        TVStation = new Card("TV Station", "Major Establishment", "Take 5 coins from any one player, on your turn only.", "6", "Major", 7, 4);
        BusinessCenter = new Card("Business Center", "Major Establishment", "Trade one non major establishment with another player", "6", "Major", 8, 4);
        TrainStation = new Card("Train Station", "Landmarks", "You may roll 1 or 2 dice.", "", "Major", 4, 4);
        ShoppingMall = new Card("Shopping Mall", "Landmarks", "Everytime you collect for one of your buildings containing a 'mug' symbol or the 'bread' symbol, you gain extra coins.", "", "Major", 10, 4);
        AmusementPark = new Card("Amusement Park", "Landmarks", "If you roll doubles, take another turn after this one.", "", "Major", 16, 4);
        RadioTower = new Card("Radio Tower", "Landmarks", "Once every turn, you can choose to re-roll your dice.", "", "Major", 22, 4);
 */
