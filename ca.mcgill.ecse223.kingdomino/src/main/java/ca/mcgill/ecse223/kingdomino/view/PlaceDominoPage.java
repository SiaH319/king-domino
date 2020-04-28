package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlaceDominoPage extends JFrame {
    //UI Elements
    //Current Player Info
    private JLabel curPlayerColorLabel;
    private JLabel curPlayerColor;
    private DominoVisualizer dominoVisualizer;
    //Grid in Java 2d
    private GridVisualizer gridVisualizer;
    private JScrollPane scroller;
    //Movement Buttons Visualizer in JAVA 2d
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton moveLeftButton;
    private JButton moveRightButton;

    //Rotate Buttons Visualizer in Java 2d
    private JButton rotateClockwise;
    private JButton rotateAntiClockwise;

    //Place Button
    private JLabel errorMessage;
    private JButton placeButton;
    //Discard Button
    private JButton discardButton;

    //Next Button
    private JButton nextButton;
    public PlaceDominoPage(){
        init();
        refresh();
    }

    public void init(){
        //Set Global Seetings
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 1000);
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
        placeButton.setFont(font);
        discardButton = new JButton("Discard");
        discardButton.setFont(font);
        moveUpButton = new JButton("Move Up");
        moveDownButton = new JButton("Move Down");
        moveLeftButton = new JButton("Move Left");
        moveRightButton = new JButton("Move Right");
        rotateClockwise = new JButton("Rotate Clockwise");
        rotateAntiClockwise = new JButton("Rotate Anticlockwise");
        errorMessage = new JLabel("Error Message: ");
        nextButton = new JButton("NEXT");
        nextButton.setEnabled(false);
        TODomino toDomino = KingdominoController.getDominoSelectedByCurrentPlayer();
        dominoVisualizer = new DominoVisualizer(toDomino,225,400);
        dominoVisualizer.setSize(300,200);

        String color = curPlayer.getColor();
        gridVisualizer = new GridVisualizer(GameController.getGrid(color));
        scroller = new JScrollPane(gridVisualizer);

        //Add Action Listeners
        moveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpActionPerformed(evt);
            }
        });

        moveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownActionPerformed(evt);
            }
        });

        moveLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveLeftActionPerformed(evt);
            }
        });

        moveRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRightActionPerformed(evt);
            }
        });

        placeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeDominoActionPerformed(evt);
            }
        });

        discardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discardDominoActionPerformed(evt);
            }
        });

        rotateClockwise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateCloclwiseActionPerformed(evt);
            }
        });

        rotateAntiClockwise.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateAntiCloclwiseActionPerformed(evt);
            }
        });

        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonClickedActionPerformed(evt);
            }
        });

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
                                        addComponent(curPlayerColor).
                                        addComponent(dominoVisualizer)).
                                addComponent(gridVisualizer).
                                addGroup(layout.createSequentialGroup().
                                        addComponent(moveUpButton).
                                        addComponent(moveDownButton).
                                        addComponent(moveLeftButton).
                                        addComponent(moveRightButton).
                                        addComponent(rotateClockwise).
                                        addComponent(rotateAntiClockwise))).
                        addComponent(errorMessage).
                        addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                                addComponent(placeButton).
                                addComponent(discardButton).addComponent(nextButton)));

        layout.setHorizontalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                                addComponent(curPlayerColorLabel).
                                addComponent(curPlayerColor).
                                addComponent(dominoVisualizer, GroupLayout.Alignment.LEADING)).
                       addGroup((layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                               addComponent(gridVisualizer)).
                               addComponent(errorMessage).
                               addGroup(layout.createSequentialGroup().
                                       addComponent(placeButton).
                                       addComponent(discardButton))).
                        addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                                addComponent(moveUpButton).
                                addComponent(moveDownButton).
                                addComponent(moveLeftButton).
                                addComponent(moveRightButton).
                                addComponent(rotateClockwise).
                                addComponent(rotateAntiClockwise).addComponent(nextButton)));
    }

    public void refresh(){
        gridVisualizer.repaint();
    }

    public void placeDominoActionPerformed(ActionEvent e){
        GameplayController.triggerPlaceDominoInSM();
        String error = "Error Message: "+GameplayController.getError();
        this.errorMessage.setText(error);
        if(!error.equals("Error Message: ")) return;
        TOPlayer curPlayer = KingdominoController.getTOPlyerFromCurrentPlayer();
        String color = curPlayer.getColor();
        gridVisualizer.setGrid(GameController.getGrid(color));
        placeButton.setEnabled(false);
        discardButton.setEnabled(false);
        moveUpButton.setEnabled(false);
        moveDownButton.setEnabled(false);
        moveLeftButton.setEnabled(false);
        moveRightButton.setEnabled(false);
        rotateAntiClockwise.setEnabled(false);
        rotateClockwise.setEnabled(false);
        nextButton.setEnabled(true);
        refresh();
    }

    public void discardDominoActionPerformed(ActionEvent e){
        GameplayController.triggerDiscardDominoInSM();
        placeButton.setEnabled(false);
        discardButton.setEnabled(false);
        moveUpButton.setEnabled(false);
        moveDownButton.setEnabled(false);
        moveLeftButton.setEnabled(false);
        moveRightButton.setEnabled(false);
        rotateAntiClockwise.setEnabled(false);
        rotateClockwise.setEnabled(false);
        nextButton.setEnabled(true);
        refresh();
    }

    public void moveUpActionPerformed(ActionEvent e){
        gridVisualizer.moveUpCursor();
        GameplayController.triggerMoveDominoInSM("Up");
        refresh();
        //System.out.println("TERRITORIES SIZE" + KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getKingdom().getTerritories().size());
    }


    public void moveDownActionPerformed(ActionEvent e){
        gridVisualizer.moveDownCursor();
        GameplayController.triggerMoveDominoInSM("Down");
        refresh();

    }

    public void moveRightActionPerformed(ActionEvent e){
        gridVisualizer.moveRightCursor();
        GameplayController .triggerMoveDominoInSM("Right");
        refresh();
    }

    public void moveLeftActionPerformed(ActionEvent e){
        gridVisualizer.moveLeftCursor();
        GameplayController.triggerMoveDominoInSM("Left");
        refresh();
    }

    public void rotateCloclwiseActionPerformed(ActionEvent e){
        int dir = 1;
        GameplayController.triggerRotateDominoInSM(dir);

    }

    public void rotateAntiCloclwiseActionPerformed(ActionEvent e){
        int dir = -1;
        GameplayController.triggerRotateDominoInSM(dir);

    }
    public void nextButtonClickedActionPerformed(ActionEvent e){
        if(GameplayController.statemachine.getGamestatusInGame()== Gameplay.GamestatusInGame.SelectingNextDomino){
            this.setVisible(false);
            new SelectDominoInGamePage().setVisible(true);
        }else if(GameplayController.statemachine.getGamestatusInGame()== Gameplay.GamestatusInGame.PreplacingDomino){
            this.setVisible(false);
            new PlaceDominoPage().setVisible(true);
        }else if(GameplayController.statemachine.getGamestatus()== Gameplay.Gamestatus.EndofGame){
            this.setVisible(false);
            new EndOfGamePage().setVisible(true);
        }

    }
}
