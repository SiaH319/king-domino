package ca.mcgill.ecse223.kingdomino;

import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.view.CreateNewGamePage;
import ca.mcgill.ecse223.kingdomino.view.CreateUserPage;
import ca.mcgill.ecse223.kingdomino.view.MainMenuPage;

public class KingdominoApplication {

	private static Kingdomino kingdomino;
	private static Gameplay statemachine;

	public static void main(String[] args) {
		System.out.println("Hello Kingdomino!");
		getKingdomino();
		getStateMachine();
		GameplayController.initStatemachine();
		GameplayController.setStateMachineState("SettingUp");
		GameplayController.triggerCreateNewUser("alice");
		GameplayController.triggerCreateNewUser("basolo");
		GameplayController.triggerCreateNewUser("calvin");
		GameplayController.triggerCreateNewUser("dante");
		System.out.println("After creating users:\n the user size is "+kingdomino.getUsers().size());
		String[] userNamesForTheNewGame = new String[]{"alice","basolo","calvin"};
		GameplayController.triggerStartNewGameInSM(3,false,true,userNamesForTheNewGame);
		System.out.print("After triggering start a new game: the current game");
		System.out.println("The current game's user size: "+kingdomino.getCurrentGame().numberOfPlayers());
		System.out.println("The current game's bonus option size: "+kingdomino.getCurrentGame().getSelectedBonusOptions().size());
		System.out.println("The current statemachine game status is "+statemachine.getGamestatus());
		GameplayController.setStateMachineState("SettingUp");
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				//new CreateUserPage().setVisible(true);
				new MainMenuPage().setVisible(true);
				//new CreateNewGamePage().setVisible(true);
			}
		});
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}
	
	public static Gameplay getStateMachine() {
		if (statemachine == null) {
			statemachine = new Gameplay();
		}
		return statemachine;
	}

	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}
	
	public static void setStateMachine(Gameplay gp) {
		statemachine = gp;
	}
}
