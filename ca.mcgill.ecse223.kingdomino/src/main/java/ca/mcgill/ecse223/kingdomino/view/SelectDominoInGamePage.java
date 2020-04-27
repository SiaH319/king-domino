package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TODomino;
import ca.mcgill.ecse223.kingdomino.controller.TOPlayer;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.view.DominoQueueVisualizer;
import ca.mcgill.ecse223.kingdomino.view.DominoVisualizer;
import ca.mcgill.ecse223.kingdomino.view.PlaceDominoPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectDominoInGamePage extends JFrame {
    //UI Elements
    //Player Info
    private JLabel curPlayerColorLabel;
    private JLabel curPlayerColor;
    private JLabel rightSpace;
    //Available Dominos For Selection
    //Domino Visualization
    private List<DominoVisualizer> dominoVisualizers;
    private DominoQueueVisualizer dominoQueueVisualizer;

    //Buttons
    private JButton makeSelection;
    //Data Elements
    private HashMap<Integer,Integer> dominoIDList;
    private HashMap<Integer,Integer> dominoPlayerSelectionList;

    private JLabel errorMessage;
    public SelectDominoInGamePage(){
        init();
    }

    private void init(){
        //Global Setting
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 900);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);

        curPlayerColor = new JLabel();
        TOPlayer curPlayer = KingdominoController.getTOPlyerFromCurrentPlayer();
        curPlayerColor.setText(curPlayer.getColor());
        curPlayerColor.setSize(200,100);
        curPlayerColor.setFont(font);
        curPlayerColorLabel = new JLabel();
        curPlayerColorLabel.setText("Current Player's color");
        curPlayerColorLabel.setSize(200,100);
        curPlayerColorLabel.setFont(font);

        rightSpace = new JLabel();
        rightSpace.setText("                     ");
        dominoVisualizers = new ArrayList<>();

        dominoQueueVisualizer = new DominoQueueVisualizer(KingdominoController.getAllTODominoInNextDraft());
        dominoQueueVisualizer.setSize(1200,800);
        makeSelection = new JButton();
        makeSelection.setText("Select");

        errorMessage = new JLabel();
        //Action Listeners
        makeSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeSelectionActionPerformed(evt);
            }
        });

        // layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(false);
        layout.setAutoCreateContainerGaps(false);
        JSeparator horizontalLineBottom = new JSeparator();

        layout.setVerticalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).
                                addGroup(layout.createSequentialGroup().
                                        addComponent(curPlayerColorLabel).
                                        addComponent(curPlayerColor)).
                                addComponent(dominoQueueVisualizer)).
                        addComponent(makeSelection));

        layout.setHorizontalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup().
                                addComponent(curPlayerColorLabel).
                                addComponent(curPlayerColor)).
                        addComponent(dominoQueueVisualizer).
                        addComponent(makeSelection));
    }

    public void refresh(){
        TOPlayer curPlayer = KingdominoController.getTOPlyerFromCurrentPlayer();
        curPlayerColor.setText(curPlayer.getColor());
        dominoQueueVisualizer.repaint();
    }

    public void makeSelectionActionPerformed(ActionEvent evt){
        int id = dominoQueueVisualizer.getCurDominoId();
//        System.out.println("id in make selection"+id);
//        System.out.println("Current player's rank:"+KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getCurrentRanking());
        if(id == -1) {
            errorMessage.setText("No Domino Selected");
            return;
        }else{
            GameplayController.triggerMakeSelectionInSM(id);
            GameplayController.triggerEventsInSM("proceed");
        }

        refresh();
        if(GameplayController.statemachine.getGamestatusInGame() == Gameplay.GamestatusInGame.PreplacingDomino){
            this.setVisible(false);
        }else{
            GameplayController.triggerEventsInSM("order");
            this.setVisible(false);
        }
        new PlaceDominoPage().setVisible(true);
    }

}
