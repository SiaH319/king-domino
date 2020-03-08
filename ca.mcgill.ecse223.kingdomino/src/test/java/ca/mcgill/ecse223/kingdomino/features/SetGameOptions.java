package ca.mcgill.ecse223.kingdomino.features;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InitializationController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.controller.InitializationController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SetGameOptions {
	/**
	 * Feature: Set Game Options
	 * @author Sia Ham  
	 * As a player, 
	 * I want to configure the designated options of the Kingdomino game  
	 * including the number of players (2, 3 or 4) 
	 * and the bonus scoring options. (F1)
	 */

	/*
	 * Background
	 */
	@Given("the game is initialized for set game options")
	public void the_game_is_initialized_for_set_game_options() {
		try {
			InitializationController.initializeGame();
		}
		catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Scenario Outline: Configuring game
	 */
	@When("set game options is initiated")
	public void set_game_options_is_initiated() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.hasSelectedBonusOptions();
		game.hasAllDominos();
		game.hasAllDrafts();
		game.hasPlayers();
		//game.setNumberOfPlayers(game.getNumberOfPlayers());
		
	}


	@When("the number of players is set to {int}")
	public void the_number_of_players_is_set_to(Integer numplayer) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.setNumberOfPlayers(numplayer);
		
		
		Integer actual = game.getNumberOfPlayers();
		assertEquals(numplayer,actual);
	}

	// When Harmony "<isUsingHarmony>" selected as bonus option
	@When("Harmony {string} selected as bonus option")
	public void harmony_selected_as_bonus_option(String isUsingHarmony) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.addSelectedBonusOption(new BonusOption(isUsingHarmony,kingdomino));
		
	}


	@When("Middle Kingdom {string} selected as bonus option")
	public void middle_Kingdom_selected_as_bonus_option(String isUsingMiddleKingdom) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.addSelectedBonusOption(new BonusOption(isUsingMiddleKingdom,kingdomino));
	}

	@Then("the number of players shall be {int}")
	public void the_number_of_players_shall_be(Integer numplayer) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		Integer actual = game.getNumberOfPlayers();
		assertEquals(numplayer,actual);
	}

	@Then("Harmony {string} an active bonus")
	public void harmony_an_active_bonus(String isUsingHarmony) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		List<BonusOption> actual = game.getSelectedBonusOptions();
		for (BonusOption bonus:actual) {
			if(bonus.getOptionName() == isUsingHarmony) {
				assertEquals(isUsingHarmony,actual);
			}
		}
	}

	@Then("Middle Kingdom {string} an active bonus")
	public void middle_Kingdom_an_active_bonus(String isUsingMiddleKingdom) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		List<BonusOption> actual = game.getSelectedBonusOptions();
		for (BonusOption bonus:actual) {
			if(bonus.getOptionName() == isUsingMiddleKingdom) {
				assertEquals(isUsingMiddleKingdom,actual);
			}
		}
	}
	}


