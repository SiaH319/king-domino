package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.GamestatusInitializing;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
/**
 * Gherkin Scenario for initializing game
 *
 * @author Mohamad, Cecilia Jiang
 *         Created Apr 13, 2020.
 */
public class InitializingGameStepDefinitions {
	@Given("the game has not been started")
	public void the_game_has_not_been_started() {
		KingdominoApplication.getKingdomino().setCurrentGame(null);
		String[] names = {"User1","User2","User3","User4"};
		GameplayController.initStatemachine();
		GameplayController.setStateMachineState("SettingUp");
		GameplayController.triggerStartNewGameInSM(4,false,false,names);
	}
	
	@When("start of the game is initiated")
	public void start_of_the_game_is_initiated() {
		GameplayController.triggerEventsInSM("draftReady");
	}
	@Then("the pile shall be shuffled")
	public void the_pile_shall_be_shuffled() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Domino> pile = game.getAllDominos();
		for (int i = 0; i < pile.size(); i++) {
			System.out.print(pile.get(i).getId() + ", ");
		}
		
	}
	@Then("the first draft shall be on the table")
	public void the_first_draft_shall_be_on_the_table() {
		System.out.println("------------Dominoes in the draft that is on the table-----------");

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertNotEquals(null, game.getNextDraft());
		assertEquals(4, game.getNextDraft().getIdSortedDominos().size());
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			System.out.println(game.getNextDraft().getIdSortedDominos().get(i).getId());
		}
	}
	@Then("the first draft shall be revealed")
	public void the_first_draft_shall_be_revealed() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertEquals(DraftStatus.FaceUp, game.getAllDraft(0).getDraftStatus());
		System.out.println("------------The draft is faced up-----------");

	}

	@Then("the initial order of players shall be determined")
	public void the_initial_order_of_players_shall_be_determined() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Gameplay gp = KingdominoApplication.getStateMachine();
		System.out.print("initial Player list: ");
		for (int i = 0; i < game.getNumberOfPlayers(); i++) {
			System.out.print(game.getPlayers().get(i).getColor().toString() + ", ");
		}
	}
	@Then("the first player shall be selecting his\\/her first domino of the game")
	public void the_first_player_shall_be_selecting_his_her_first_domino_of_the_game() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();

		assertEquals(game.getPlayer(0),game.getNextPlayer());
		assertEquals(Gamestatus.Initializing,KingdominoApplication.getStateMachine().getGamestatus());
		assertEquals(GamestatusInitializing.SelectingFirstDomino,KingdominoApplication.getStateMachine().getGamestatusInitializing());
	}
	   
	@Then("the second draft shall be on the table, face down")
	public void the_second_draft_shall_be_on_the_table_face_down() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertNotNull(game.getNextDraft());
		assertEquals(DraftStatus.FaceDown, game.getNextDraft().getDraftStatus());
		assertEquals(0, game.getNextDraft().getSelections().size());
		assertEquals(4, game.getNextDraft().getIdSortedDominos().size());
		
	}
	

	
	

}
