package ca.mcgill.ecse223.kingdomino.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.controller.DominoController;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TODomino;
import ca.mcgill.ecse223.kingdomino.controller.TOPlayer;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

/**
 * @author Violet 
 *
 * This class contains the layout of the Kingdomino board grid
 * & Place Domino button, 
 * & Move Domino button,
 * & Rotate Domino button, 
 * & Dicard Domino button
 * & Choose Next Domino button
 */
public class GameGridGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	//Domino Visualization
        private List<DominoVisualizer> dominoVisualizers;
        private DominoQueueVisualizer dominoQueueVisualizer;
	
	//Data Elements
        private HashMap<Integer,Integer> dominoIDList;
        private HashMap<Integer,Integer> dominoPlayerSelectionList;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGridGUI window = new GameGridGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
        
        private JButton btnNewButton;
        private JButton btnNewButton_1;
        private JButton btnNewButton_2;
        private JButton btnNewButton_3;
        
        private JPanel leftPanel;
        private JPanel rightPanel;
        private JPanel upPanel;

	/**
	 * Create the application.
	 */
	public GameGridGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(40, 50));
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(40, 50));
        upPanel = new JPanel();
        upPanel.setPreferredSize(new Dimension(40, 10));
		
		//Global Setting
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 900);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);

	    
	    btnNewButton = new JButton("Place Domino");
	    btnNewButton.setBackground(Color.ORANGE);
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
			// DominoController.placeDomino(Player player, int id)
	    		// Add controller - Place domino
		        // Feature 13
			placeDominoActionPerformed(e);
	    	}
	    });
		
	    
	    btnNewButton_1 = new JButton("Move");
	    btnNewButton_1.setBackground(Color.PINK);
	    btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
			// Feature 11: Move Current Domino
	    		// Add controller - Move domino
			// DominoController.initialMoveDominoToKingdom(Player player, int dominoId)
			moveDominoActionPerformed(e);
	    	}
	    });
	    
		
	    
	    btnNewButton_2 = new JButton("Rotate");
	    btnNewButton_2.setBackground(Color.CYAN);
	    btnNewButton_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
			// Feature 12
	    		// Add controller - Rotate domino
			// DominoController.rotateExistingDomino(Castle castle, Square[] grid, List<KingdomTerritory> territories,
                        //                    DominoInKingdom dominoInKingdom, int rotationDir)
			rotateDominoActionPerformed(e);
	    	}
	    });
	    
		
	    
	    btnNewButton_3 = new JButton("Discard");
	    btnNewButton_3.setBackground(Color.MAGENTA);
	    btnNewButton_3.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
			// Feature 18 : Discard Domino
	    		// Add controller - Discard
			// DominoController.attemptDiscardSelectedDomino(DominoInKingdom dominoInKingdom)
			discardDominoActionPerformed(e);
	    	}
	    });
	    
		
	    
	    JButton btnNewButton_4 = new JButton("Next");
	    btnNewButton_4.setBackground(Color.GRAY);
	    btnNewButton_4.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
			// Feature 10: Choose Next Domino
	    		// Add controller - Next Domino
			// DominoController.chooseNextDomino(Game game,int dominoId)
	    	}
	    });
	    
	    
	   
	  //SetLayOut
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(upPanel)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(leftPanel)
                                .addGroup(layout.createSequentialGroup().
                                        addComponent(btnNewButton).
                                        addComponent(btnNewButton_1).
                                        addComponent(btnNewButton_2).
                                        addComponent(btnNewButton_3))
                                .addComponent(rightPanel))


        );
        
        layout.setHorizontalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup()
                                .addComponent(upPanel)
                                .addComponent(leftPanel)).
                        addGroup(layout.createParallelGroup().
                                addComponent(btnNewButton).
                                addComponent(btnNewButton_1).
                                addComponent(btnNewButton_2).
                                addComponent(btnNewButton_3))
                .addComponent(rightPanel)

        );
	 
	    
	}
	
	public void placeDominoActionPerformed(ActionEvent e){
		GameplayController.triggerPlaceDominoInSM();
		this.setVisible(false);
        new PlaceDominoPage().setVisible(true);
	}
	
	public void discardDominoActionPerformed(ActionEvent e){
		GameplayController.triggerDiscardDominoInSM();
		this.setVisible(false);
        new DiscardDominoPage().setVisible(true);
	}
	
	public void moveDominoActionPerformed(ActionEvent e){
		int id = dominoQueueVisualizer.getCurDominoId();
		String dir = "";
		GameplayController.triggerMoveDominoInSM(dir);
		this.setVisible(false);
        new MoveDominoPage().setVisible(true);
	}
	
	public void rotateDominoActionPerformed(ActionEvent e){
		int id = dominoQueueVisualizer.getCurDominoId();
		int dir = 1;
		GameplayController.triggerRotateDominoInSM(dir);
		this.setVisible(false);
        new RotateDominoPage().setVisible(true);
	}
	
	private static Domino getdominoByID(int id) {
            Game game = KingdominoApplication.getKingdomino().getCurrentGame();
            for (Domino domino : game.getAllDominos()) {
                if (domino.getId() == id) {
                    return domino;
                }
            }
            throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
        }

}
