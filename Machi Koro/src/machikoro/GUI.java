package machikoro;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private Game game;
    private Player player;
    private Deck deck;
    private JLabel rollOutput;
    private JPanel player1Panel = new JPanel();
    private JPanel player2Panel = new JPanel();
    private JPanel player3Panel = new JPanel();
    private JPanel player4Panel = new JPanel();
    private JLabel p1 = new JLabel();
    private JLabel p2 = new JLabel();
    private JLabel p3 = new JLabel();
    private JLabel p4 = new JLabel();
    JLabel p1Coins = new JLabel();
    JLabel p2Coins = new JLabel();
    JLabel p3Coins = new JLabel();
    JLabel p4Coins = new JLabel();

    /**
     * Constructor starts the game, makes new GridBagLayout, makes JLabels for players and coins, and makes buttons for roll functions.
     */
    public GUI() throws HeadlessException {
        game = new Game(4);
        deck = new Deck(false);

        int playerNumber = 0;
        player = game.getPlayer(playerNumber);
//        game.getPlayer(0).setCoins(100); use this to test construction

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        setSize(1200, 800);

        ToolTipManager.sharedInstance().setInitialDelay(0);


        // Initialize supply area panel
        JPanel supplyArea = new JPanel();
        supplyArea.setBackground(Color.gray);
//        supplyArea.setPreferredSize(new Dimension(600, 400));
        supplyArea.setLayout(new GridLayout(5, 5));

        // Add borders
        player1Panel.setBorder(new TitledBorder(new LineBorder(Color.black, 3),
                "Player 1"));
        player2Panel.setBorder(new TitledBorder(new LineBorder(Color.black, 3),
                "Player 2"));
        player3Panel.setBorder(new TitledBorder(new LineBorder(Color.black, 3),
                "Player 3"));
        player4Panel.setBorder(new TitledBorder(new LineBorder(Color.black, 3),
                "Player 4"));

        // Initialize player coins values
        p1Coins.setText("Coins:  " + game.getPlayer(0).getCoins());
        p2Coins.setText("Coins:  " + game.getPlayer(1).getCoins());
        p3Coins.setText("Coins:  " + game.getPlayer(2).getCoins());
        p4Coins.setText("Coins:  " + game.getPlayer(3).getCoins());

        // Initialize landmark buttons
        JButton trainStation = new JButton("Train Station");
        JButton shoppingMall = new JButton("Shopping Mall");
        JButton amusementPark = new JButton("Amusement Park");
        JButton radioTower = new JButton("Radio Tower");

        // Supply cards
        JButton wheatField = new JButton("Wheat Field");
        JButton ranch = new JButton("Ranch");
        JButton forest = new JButton("Forest");
        JButton mine = new JButton("Mine");
        JButton appleOrchard = new JButton("Apple Orchard");
        JButton bakery = new JButton("Bakery");
        JButton convenienceStore = new JButton("Convenience Store");
        JButton cheeseFactory = new JButton("Cheese Factory");
        JButton furnitureFactory = new JButton("Furniture Factory");
        JButton fruitAndVegetableMarket = new JButton("Fruit and Vegetable Market");
        JButton cafe = new JButton("Cafe");
        JButton familyRestaurant = new JButton("Family Restaurant");
        JButton stadium = new JButton("Stadium");
        JButton tvStation = new JButton("TV Station");
        JButton businessCenter = new JButton("Business Center");

        // Give landmark buttons tooltips
        trainStation.setToolTipText(deck.getCard("Train Station").toStringGUI());
        shoppingMall.setToolTipText(deck.getCard("Shopping Mall").toStringGUI());
        amusementPark.setToolTipText(deck.getCard("Amusement Park").toStringGUI());
        radioTower.setToolTipText(deck.getCard("Radio Tower").toStringGUI());

        // Give supply buttons tooltips
        wheatField.setToolTipText(deck.getCard("Wheat Field").toStringGUI());
        ranch.setToolTipText(deck.getCard("Ranch").toStringGUI());
        forest.setToolTipText(deck.getCard("Forest").toStringGUI());
        mine.setToolTipText(deck.getCard("Mine").toStringGUI());
        appleOrchard.setToolTipText(deck.getCard("Apple Orchard").toStringGUI());
        bakery.setToolTipText(deck.getCard("Bakery").toStringGUI());
        convenienceStore.setToolTipText(deck.getCard("Convenience Store").toStringGUI());
        cheeseFactory.setToolTipText(deck.getCard("Cheese Factory").toStringGUI());
        furnitureFactory.setToolTipText(deck.getCard("Furniture Factory").toStringGUI());
        fruitAndVegetableMarket.setToolTipText(deck.getCard("Fruit and Vegetable Market").toStringGUI());
        cafe.setToolTipText(deck.getCard("Cafe").toStringGUI());
        familyRestaurant.setToolTipText(deck.getCard("Family Restaurant").toStringGUI());
        stadium.setToolTipText(deck.getCard("Stadium").toStringGUI());
        tvStation.setToolTipText(deck.getCard("TV Station").toStringGUI());
        businessCenter.setToolTipText(deck.getCard("Business Center").toStringGUI());

        // Initialize the roll buttons
        JButton roll1 = new JButton("Roll 1");
        JButton roll2 = new JButton("Roll 2");
        rollOutput = new JLabel("");

        rollOutput.setEnabled(false);

        // Initialize action listeners
        RollOneListener buttonRoll1 = new RollOneListener();
        RollTwoListener buttonRoll2 = new RollTwoListener();
        ConstructionListener buttonConstruct = new ConstructionListener();

        // Add action listeners
        roll1.addActionListener(buttonRoll1);
        roll2.addActionListener(buttonRoll2);
        trainStation.addActionListener(buttonConstruct);
        shoppingMall.addActionListener(buttonConstruct);
        amusementPark.addActionListener(buttonConstruct);
        radioTower.addActionListener(buttonConstruct);
        wheatField.addActionListener(buttonConstruct);
        ranch.addActionListener(buttonConstruct);
        forest.addActionListener(buttonConstruct);
        mine.addActionListener(buttonConstruct);
        appleOrchard.addActionListener(buttonConstruct);
        bakery.addActionListener(buttonConstruct);
        convenienceStore.addActionListener(buttonConstruct);
        cheeseFactory.addActionListener(buttonConstruct);
        furnitureFactory.addActionListener(buttonConstruct);
        fruitAndVegetableMarket.addActionListener(buttonConstruct);
        cafe.addActionListener(buttonConstruct);
        familyRestaurant.addActionListener(buttonConstruct);
        stadium.addActionListener(buttonConstruct);
        tvStation.addActionListener(buttonConstruct);
        businessCenter.addActionListener(buttonConstruct);

        // Add supply area to the GUI
        add(supplyArea, new GBC(0, 0, 6, 4).setFill(GBC.BOTH).setWeight(100, 100));
        add(player1Panel, new GBC(6, 0, 5, 1).setFill(GBC.BOTH).setWeight(100, 100));
        add(player2Panel, new GBC(6, 1, 5, 1).setFill(GBC.BOTH).setWeight(100, 100));
        add(player3Panel, new GBC(6, 2, 5, 1).setFill(GBC.BOTH).setWeight(100, 100));
        add(player4Panel, new GBC(6, 3, 5, 1).setFill(GBC.BOTH).setWeight(100, 100));
        player1Panel.add(p1);
        player2Panel.add(p2);
        player3Panel.add(p3);
        player4Panel.add(p4);

        // Add buttons to supply area
        supplyArea.add(trainStation);
        supplyArea.add(shoppingMall);
        supplyArea.add(amusementPark);
        supplyArea.add(radioTower);
        supplyArea.add(wheatField);
        supplyArea.add(ranch);
        supplyArea.add(forest);
        supplyArea.add(mine);
        supplyArea.add(appleOrchard);
        supplyArea.add(bakery);
        supplyArea.add(convenienceStore);
        supplyArea.add(cheeseFactory);
        supplyArea.add(furnitureFactory);
        supplyArea.add(fruitAndVegetableMarket);
        supplyArea.add(cafe);
        supplyArea.add(familyRestaurant);
        supplyArea.add(stadium);
        supplyArea.add(tvStation);
        supplyArea.add(businessCenter);

        // Add coins specific to players to the GUI
        player1Panel.add(p1Coins);
        player2Panel.add(p2Coins);
        player3Panel.add(p3Coins);
        player4Panel.add(p4Coins);

        // Add starting cards to players
        player1Panel.add(new JLabel("Bakery"));
        player2Panel.add(new JLabel("Bakery"));
        player3Panel.add(new JLabel("Bakery"));
        player4Panel.add(new JLabel("Bakery"));
        player1Panel.add(new JLabel("Wheat Field"));
        player2Panel.add(new JLabel("Wheat Field"));
        player3Panel.add(new JLabel("Wheat Field"));
        player4Panel.add(new JLabel("Wheat Field"));

        // Add roll buttons to the GUI
        add(roll1, new GBC(0, 11, 1, 1).setFill(GBC.CENTER).setWeight(100, 100));
        add(roll2, new GBC(1, 11, 1, 1).setFill(GBC.CENTER).setWeight(100, 100));
        add(rollOutput, new GBC(2, 11, 1, 1).setFill(GBC.CENTER).setWeight(100, 100));

    }

    /**
     * Action listener for roll single die
     */
    private class RollOneListener implements ActionListener {

        private int lastRoll;

        public void actionPerformed(ActionEvent event) {
            // stuff
            lastRoll = game.rollSingleDie();
            System.out.println(rollOutput.getText());
            rollOutput.setText("" + lastRoll);
            return;
        }
    }

    /**
     * Action listener for roll two die
     */
    private class RollTwoListener implements ActionListener {

        private int roll1;
        private int roll2;

        public void actionPerformed(ActionEvent event) {
            roll1 = game.rollSingleDie();
            roll2 = game.rollSingleDie();
            if (roll1 == roll2) {
                rollOutput.setText("" + (roll1 + roll2) + " Doubles!");
                return;
            }
            rollOutput.setText("" + (roll1 + roll2));
            return;
        }
    }

    /**
     * Action listener for construction
     */
    private class ConstructionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            boolean pass = player.construct(button.getText());

            if (pass) {
                JLabel test = new JLabel(button.getText());
                player1Panel.add(test);
                rollOutput.setText("Purchased " + button.getText());
                int quantity;
                quantity = deck.getCard(button.getText()).getQuantity() - 1;
                deck.getCard(button.getText()).setQuantity(quantity);
                button.setToolTipText(deck.getCard(button.getText()).toStringGUI());
            }

            else {
                rollOutput.setText("Not enough coins");
            }
            p1Coins.setText("Coins:  " + String.valueOf(game.getPlayer(0).getCoins()));
            revalidate();
            return;


        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new GUI();
            frame.setTitle("Machi Koro GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}