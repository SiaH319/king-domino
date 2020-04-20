package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import ca.mcgill.ecse223.kingdomino.controller.QueryMethodController;
import ca.mcgill.ecse223.kingdomino.controller.TOPlayer;
import ca.mcgill.ecse223.kingdomino.controller.TOUser;
/**
 * 
 * @author Mohamad
 *
 */
	
public class EndOfGamePage extends JFrame {
	
	private static final long serialVersionUID = 3984796675573114484L;
	
	//define UI elements
	private JPanel panel;
	private JLabel winner;
	private JTable scores;
	private JButton restart;
	
	//define data elements
	private ArrayList<Integer> ranks = new ArrayList<Integer>();
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<Integer> propertyScores = new ArrayList<Integer>();
	private ArrayList<Integer> bonusScores = new ArrayList<Integer>();
	private ArrayList<Integer> totalScores = new ArrayList<Integer>();
	
	private DefaultTableModel tableModel; //new
	private JScrollPane scrollPane; //new
	private Object data[][] = { {"Rank", "User", "Property Score", "Bonus Score", "Total Score"},
								{1, "P1", 0, 0, 0}, 
								{2, "P2", 0, 0, 0}, 
								{3, "P3", 0, 0, 0}, 
								{4, "P4", 0, 0, 0} };
	String[] columnNames = {"Rank", "User", "Property Score", "Bonus Score", "Total Score"};
	
	private String winningUser = "";
	private int numPlayers;
	
	//@TODO add save game button?
	
	public EndOfGamePage() {
		initComponents();
		refreshData();
	}
	
	public void run() {
    	new EndOfGamePage().setVisible(true);
    }
	
	//initializing components
	private void initComponents() {		
		//window setup
		panel = new JPanel();
		this.add(panel);
		this.setSize(750, 250);

		//text to declare winner
		winner = new JLabel("The winner is ???");
		panel.add(winner);
		
		//score table
		scores = new JTable(data, columnNames);
		scrollPane = new JScrollPane(scores);
		panel.add(scrollPane);
		//panel.add(scores.getTableHeader(), BorderLayout.NORTH);
		//panel.add(scores, BorderLayout.CENTER);
		
		//button to restart application
		restart = new JButton("Restart KingDomino");
		restart.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				restartActionPerformed(evt);
			}
		});
		panel.add(restart);
		
		//setting the layout
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup()
				.addComponent(winner)
				.addComponent(scores)
				.addComponent(restart)
				);	
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addComponent(winner)
				.addComponent(scores)
				.addComponent(restart)
				);
		
	}
	
	//restarting game (going back to Kingdomino Menu)
	private void restartActionPerformed(ActionEvent evt) {
		panel.removeAll();;
		//new KingdominoMenu().setVisible(true); call the main menu
	}

	
	//refreshing data
	private void refreshData() {
		//getting winner 
		winningUser = QueryMethodController.getWinner();
		winner.setText("The winner is "+winningUser+"!");
		
		//refreshing data for score table
		List<TOPlayer> players = QueryMethodController.getPlayers();
		numPlayers = players.size();
		
		for(TOPlayer p : players) {
			int rank = p.getCurrentRanking();
			//using [rank-1] as row number orders table from rank 1-4
			data[rank - 1][0] = rank; //col 0: rank
			data[rank - 1][1] = "Name"; //col 1: user
			data[rank - 1][2] = p.getPropertyScore(); //col 2: property score
			data[rank - 1][3] = p.getBonusScore(); //col 3: bonus score
			data[rank - 1][4] = p.getBonusScore() +p.getPropertyScore(); //col 4: total score
		}
	}
}
