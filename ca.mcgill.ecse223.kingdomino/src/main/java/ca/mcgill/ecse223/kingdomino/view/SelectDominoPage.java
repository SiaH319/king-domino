package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TODomino;                   

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectDominoPage extends JFrame {
    //UI Elements
    //Player Info
    private JLabel curPlayerColorLabel;
    private JPanel curPlayerColor;
    //Available Dominos For Selection
    //Domino Visualization
    private List<DominoVisualizer> dominoVisualizers;
    //Nav Bar
    //Save Button
    //Browse Domino Button
    //Turn Number

    //Buttons
    private JButton makeSelection;
    //Data Elements
    private HashMap<Integer,Integer> dominoIDList;
    private HashMap<Integer,Integer> dominoPlayerSelectionList;

    public SelectDominoPage(){
        init();
    }

    private void init(){
        //Global Setting
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 900);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);

        curPlayerColor = new JPanel();
        curPlayerColorLabel = new JLabel();
        curPlayerColorLabel.setText("Current Player's color");

        dominoVisualizers = new ArrayList<>();

        int playerNum =KingdominoApplication.getKingdomino().getCurrentGame().getNumberOfPlayers();
        int yStart = 50;
        for(int i = 0 ; i < ((playerNum%2 == 0)?4:3);i++){
            TODomino domino =KingdominoController.getAllTODominoInCurrentDraft().get(i);
            System.out.println("In Select Domino Page first domino's id: "+domino.getId());
            DominoVisualizer dominoVisualizer = new DominoVisualizer(domino,700,yStart);
            dominoVisualizers.add(dominoVisualizer);
            yStart+=0;
        }
        makeSelection = new JButton();
        makeSelection.setText("Select");

        // layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup groupDomino = layout.createSequentialGroup();
        for(DominoVisualizer dominoVisualizer: this.dominoVisualizers){
            groupDomino.addComponent(dominoVisualizer);
        }

        GroupLayout.ParallelGroup groupDomino2 = layout.createParallelGroup();
        for(DominoVisualizer dominoVisualizer: this.dominoVisualizers){
            groupDomino2.addComponent(dominoVisualizer);
        }
        layout.setHorizontalGroup(layout.createSequentialGroup().
                addGroup(groupDomino2).
                addComponent(makeSelection));
        layout.setVerticalGroup(groupDomino.addComponent(makeSelection));
    }
}
