package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
/**
 * 
 * @author Mohamad
 *
 */

public class CreateNewGamePage extends JFrame{
	private static final long serialVersionUID = -4426310869335015542L;
	//step 1 define the UI elements
	private JLabel errorMessage;
	private JLabel playerNumberLabel;
	private JComboBox playerNumberBox;
	private JLabel bonusOptions;
	private JLabel selectingPlayers;
	private JComboBox playerSelection;
	private JCheckBox middleKingdom;
	private JCheckBox harmony;
	private JButton joinButton;
	private JButton doneButton;
	private String error = null;
	
		
	
	private void initCompenents() {
		errorMessage=new JLabel();
		errorMessage.setForeground(Color.RED);
		
		playerNumberLabel=new JLabel();
		playerNumberLabel.setText("Number of players:");
		bonusOptions=new JLabel();
		bonusOptions.setText("Bonus Options:");
		Player.PlayerColor[] color= {PlayerColor.Blue,PlayerColor.Green,PlayerColor.Pink,PlayerColor.Yellow};
		playerSelection=new JComboBox<Player.PlayerColor>(color);
		playerNumberBox=new JComboBox<Integer>();
		playerNumberBox.addItem(2);
		playerNumberBox.addItem(3);
		playerNumberBox.addItem(4);
		harmony=new JCheckBox("Harmony");
		middleKingdom = new JCheckBox("Middle Kingdom");
		joinButton = new JButton("Join!");
		doneButton= new JButton("Done");
		
		
		
		
		
		
		setTitle("Creating New Game");
		
		joinButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				joinButtonActionPerformed(evt);
			}
		});
		doneButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				doneButtonActionPerformed(evt);
			}
		});
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		

		
	}
	private void doneButtonActionPerformed(ActionEvent evt) {
//		error = null;
//		
//		// call the controller
//		try {
//			//call controller gameplay
//		} catch (InvalidInputException e) {
//			error = e.getMessage();
//		}
//		
//		// update visuals
//		refreshData();
		
	}
	private void joinButtonActionPerformed(ActionEvent evt) {
//		// clear error message
//				//error = null;
//				
//				// call the controller
//				try {
//					//call controller gameplay
//				} catch (InvalidInputException e) {
//					//error = e.getMessage();
//				}
//				
//				// update visuals
//				refreshData();
		
	}
	private void refreshData() {
		// TODO Auto-generated method stub
		
	}

	
}
