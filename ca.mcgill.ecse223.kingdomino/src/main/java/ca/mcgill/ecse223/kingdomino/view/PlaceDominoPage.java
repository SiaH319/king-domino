package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TOPlayer;

import javax.swing.*;
import java.awt.*;

public class PlaceDominoPage extends JFrame {
    //UI Elements
    //Current Player Info
    private JLabel curPlayerColorLabel;
    private JLabel curPlayerColor;
    //Grid in Java 2d
    private GridVisualizer gridVisualizer;

    //Movement Buttons Visualizer in JAVA 2d
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton moveLeftButton;
    private JButton moveRightButton;

    //Rotate Buttons Visualizer in Java 2d
    private JButton rotateClockwise;
    private JButton rotateAntiClockwise;

    //Place Button
    private JButton placeButton;
    //Discard Button
    private JButton discardButton;

    public PlaceDominoPage(){
        init();
        refresh();
    }

    public void init(){
        //Set Global Seetings
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 900);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);

        //Initialize UI Elements
        curPlayerColor = new JLabel();
        TOPlayer curPlayer = KingdominoController.getTOPlyerFromCurrentPlayer();
        curPlayerColor.setText(curPlayer.getColor());
        curPlayerColor.setSize(200,100);
        curPlayerColor.setFont(font);
        curPlayerColorLabel = new JLabel();
        curPlayerColorLabel.setText("Current Player's color");
        curPlayerColorLabel.setSize(200,100);
        curPlayerColorLabel.setFont(font);
        placeButton = new JButton("Place");
        discardButton = new JButton("Discard");

        String color = curPlayer.getColor();
        gridVisualizer = new GridVisualizer(GameController.getGrid(color));
        //Add Action Listeners

        //Set Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);

        layout.setVerticalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                                addGroup(layout.createSequentialGroup().
                                        addComponent(curPlayerColorLabel).
                                        addComponent(curPlayerColor)).
                                addComponent(gridVisualizer)));

        layout.setHorizontalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup().
                                addComponent(curPlayerColorLabel).
                                addComponent(curPlayerColor)).
                       addComponent(gridVisualizer));
    }

    public void refresh(){
        gridVisualizer.repaint();
    }
}
