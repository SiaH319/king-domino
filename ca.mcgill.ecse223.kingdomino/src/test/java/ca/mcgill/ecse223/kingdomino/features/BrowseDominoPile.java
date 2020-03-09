package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameController;
import ca.mcgill.ecse223.kingdomino.controller.InitializationController;
import ca.mcgill.ecse223.kingdomino.controller.addDefaultController;
import ca.mcgill.ecse223.kingdomino.controller.getDominoController;
import ca.mcgill.ecse223.kingdomino.controller.InitializationController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BrowseDominoPile {
	/***
	 * Feature: Browse Domino Pile
	 * @author Sia Ham
	 * As a player,
	 * I wish to browse the set of all dominoes in increasing order of
	 * numbers prior to playing the game so that I can adjust my strategy,
	 * view an individual domino or filter the dominoes by terrain type
	 */

	Domino currentDomino;
	List<Domino> currentDominoes;
	/*
	 * Background
	 */
	@Given("the program is started and ready for browsing dominoes")
	public void the_program_is_started_and_ready_for_browsing_dominoes() throws InvalidInputException {
		// Intialize empty game
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		game.hasPlayers();
		game.hasAllDominos();
		game.hasAllDrafts();
		game.getAllDominos();
		// Populate game
		addDefaultController.addDefaultUsersAndPlayers(game);
		addDefaultController.createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}

	/*
	 * Scenario: Browse all the dominoes
	 */
	@When("I initiate the browsing of all dominoes")
	public void i_initiate_the_browsing_of_all_dominoes() throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.getAllDominos();

	}

	@Then("all the dominoes are listed in increasing order of identifiers")
	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() throws InvalidInputException {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		addDefaultController.addAllDominoesInOrder(game);
	}


	/*
	 * Scenario Outline: Select and observe an individual domino
	 */
	@When("I provide a domino ID {int}")
	public void i_provide_a_domino_ID(Integer id) {
		currentDomino = getDominoController.getdominoByID(id);
	}


	@Then("the listed domino has {string} left terrain")
	public void the_listed_domino_has_left_terrain(String string) {
		String excepeted = getDominoController.getTerrainTypeString(currentDomino.getLeftTile());
		assertEquals(excepeted,string);
	}


	@Then("the listed domino has {string} right terrain")
	public void the_listed_domino_has_right_terrain(String string) {
		String excepeted = getDominoController.getTerrainTypeString(currentDomino.getRightTile());
		assertEquals(excepeted,string);
	}


	@Then("the listed domino has {int} crowns")
	public void the_listed_domino_has_crowns(Integer int1) {
		Integer excepeted = getDominoController.getDominoTotalCrown(currentDomino);
		assertEquals(excepeted,int1);
	}


	/*
	 *  Scenario Outline: Filter domino by terrain type
	 */
	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
			currentDominoes = GameController.getAllDominobyTerrainType(string, game);
	}
	
	
	@Then("list of dominoes with IDs {string} should be shown")
	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
		String[] ids = string.split(",");
		int k = 0;//ids.length;
		for (Domino domino: currentDominoes) { 
			string = ids[k];
			assertEquals(""+domino.getId(),string);
			k++;
		}
	}
}