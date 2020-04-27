package ca.mcgill.ecse223.kingdomino.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TOUser;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

/**
 * @author Mohamad, Cecilia Jiang
 */

public class CreateNewGamePage extends JFrame {
    private static final long serialVersionUID = -4426310869335015542L;
    //step 1 define the UI elements
    private JLabel errorMessage;
    //Choose Number UI Elements
    private JLabel playerNumberLabel;
    private JComboBox playerNumberBox;

    //Bonus Option UI elements
    private JLabel bonusOptions;
    private JCheckBox middleKingdom;
    private JCheckBox harmony;

    //Select Players from Users UI Elements
    private JLabel selectingPlayers;
    private JPanel playerColor;
    private JComboBox playerSelection;
    private JButton joinButton;
    private ArrayList<String> selectedUserNames = new ArrayList<>();
    Color[] color;
    //Done button
    private JButton doneButton;


    //Data elements
	private HashMap<Integer,Integer> playerNumList = new HashMap<>();
	private HashMap<Integer,String> userNamesList = new HashMap<>();

	public CreateNewGamePage() {
		initCompenents();
	}

	public void CreateNewGamePage() {
        initCompenents();
    }

    private void initCompenents() {
        //Global Setting
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 1000);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);

        //Initialize UI Elements
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.RED);

        playerNumberLabel = new JLabel();
        playerNumberLabel.setText("Number of players:");
        playerNumberLabel.setFont(font);
        bonusOptions = new JLabel();
        bonusOptions.setText("Bonus Options:");
        bonusOptions.setFont(font);

        playerNumberBox = new JComboBox<Integer>();
        playerNumberBox.addItem(2);
        playerNumberBox.addItem(3);
        playerNumberBox.addItem(4);
		playerNumList.put(0,2);
		playerNumList.put(1,3);
		playerNumList.put(2,4);
		playerNumberBox.setSelectedIndex(-1);
		playerNumberBox.setFont(font);
        color = new Color[]{Color.BLUE, Color.GREEN, Color.pink, Color.yellow};

        middleKingdom = new JCheckBox("Middle Kingdom");
        middleKingdom.setFont(font);
        harmony = new JCheckBox("Harmony");
        harmony.setFont(font);
        joinButton = new JButton("Join!");
        joinButton.setFont(font);
        doneButton = new JButton("Done");
		doneButton.setFont(font);

        selectingPlayers = new JLabel("Select a user as player");
        selectingPlayers.setFont(font);
        playerColor = new JPanel();
        playerColor.setFont(font);
        playerColor.setSize(10, 10);
        playerColor.setBackground(color[0]);
        playerSelection = new JComboBox<>(new String[0]);
        playerSelection.setFont(font);
        int i = 0;
        for (TOUser item : KingdominoController.getAllTOUsers()) {
            String userName = item.getName();
            playerSelection.addItem(userName);
            userNamesList.put(i,userName);
            i++;
        }
        playerSelection.addItem("A passing-by person");
        playerSelection.setSelectedIndex(-1);
		userNamesList.put(i,"A passing-by person");


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
        layout.setVerticalGroup(layout.createSequentialGroup().
				addGroup(layout.createParallelGroup().
						addComponent(playerNumberLabel).
						addComponent(playerNumberBox)).
				addGroup(layout.createParallelGroup()
				.addComponent(bonusOptions).addComponent(middleKingdom).addComponent(harmony)).
				addGroup(layout.createParallelGroup().
						addComponent(selectingPlayers).
						addComponent(playerColor,20,20,20).
						addComponent(playerSelection).
						addComponent(joinButton))
				.addComponent(doneButton));

        layout.setHorizontalGroup(layout.createSequentialGroup().
				addGroup(layout.createParallelGroup().
						addComponent(playerNumberLabel).
						addComponent(bonusOptions).
						addComponent(selectingPlayers)
				).addComponent(playerColor,20,20,20).
				addGroup(layout.createParallelGroup().
						addComponent(playerNumberBox).
						addGroup(layout.createSequentialGroup().addComponent(middleKingdom).addComponent(harmony)).
						addComponent(playerSelection)).
				addComponent(joinButton).
				addComponent(doneButton));
    }

    private void doneButtonActionPerformed(ActionEvent evt) {
		boolean mkActivated = middleKingdom.isSelected();
		boolean harmonyActivated = harmony.isSelected();
		int selectedPlayerNumIndex = this.playerNumberBox.getSelectedIndex();
		int selectedPlayerNumberInGame = this.playerNumList.get(selectedPlayerNumIndex);
		String[] userNameArr = new String[selectedPlayerNumberInGame];
		int i = 0;
		for(String str: this.selectedUserNames){
			userNameArr[i] = str;
			i++;
		}
		GameplayController.triggerStartNewGameInSM(selectedPlayerNumberInGame,mkActivated,harmonyActivated,(userNameArr));

		System.out.println("The current game has "+ KingdominoApplication.getKingdomino().getCurrentGame().getAllDrafts().size()+" draft generated");
		System.out.println("The current game has "+KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos().size()+"dominos in total");
		System.out.println("The first domino in current draft is: "
                +KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(0).getId());
        System.out.println("The second domino in current draft is: "
                +KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(1).getId());
        System.out.println("The third domino in current draft is: "
                +KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(2).getId());

        this.setVisible(false);
        new DraftReadyPage().setVisible(true);

    }

    private void joinButtonActionPerformed(ActionEvent evt) {
        // call the controller
       int userNameSelected = this.playerSelection.getSelectedIndex();
       String selectedUserName = userNamesList.get(userNameSelected);
       if(selectedUserName.equals("A passing-by person")){
		   this.selectedUserNames.add(selectedUserName);
	   }else if(this.selectedUserNames.contains(selectedUserName)){
			this.errorMessage.setText( "Already Selected This User!");
	   }else{
		   this.selectedUserNames.add(selectedUserName);
	   }

        // update visuals
        refreshData();
    }

    private void refreshData() {
		int selectedPlayerNumIndex = this.playerNumberBox.getSelectedIndex();
		int selectedPlayerNumberInGame = this.playerNumList.get(selectedPlayerNumIndex);
		if(this.selectedUserNames.size() == selectedPlayerNumberInGame){
			joinButton.setEnabled(false);
		}
		if(joinButton.isEnabled()){
			playerColor.setBackground(color[this.selectedUserNames.size()]);
		}
		this.playerSelection.setSelectedIndex(-1);


    }


}
