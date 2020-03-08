package ca.mcgill.ecse223.kingdomino.features;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.controller.NewGameStartController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StartANewGame {
	/**
	 * Feature: Start a New Game
	 * @author: Sia Ham
	 *  As a Kingdomino player, 
	 *  I want to start a new game of Kingdomino against some opponents
	 *  with my castle placed on my territory with the current settings of the game. 
	 *  The initial order of player should be randomly determined.
	 */

	/*
	 * Background
	 */
	@Given("the program is started and ready for starting a new game")
	public void the_program_is_started_and_ready_for_starting_a_new_game() {
		Game game = new Game(48, KingdominoApplication.getKingdomino()); // program startes
		game.hasPlayers();
		game.hasAllDominos();
		game.hasAllDrafts();
		game.hasCurrentDraft();
		game.hasNextDraft();
		game.hasNextPlayer();
	}

	/*
	 * Scenario: Start a new game
	 */
	@Given("there are four selected players")
	public void there_are_four_selected_players() {
		Game game = new Game(48, KingdominoApplication.getKingdomino());
		game.setNumberOfPlayers(4);
		NewGameStartController.addDefaultUsersAndPlayers(game);
	}

	@Given("bonus options Harmony and MiddleKingdom are selected")
	public void bonus_options_Harmony_and_MiddleKingdom_are_selected() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game currentGame = new Game(48, KingdominoApplication.getKingdomino());
		currentGame.hasSelectedBonusOptions();
		currentGame.addSelectedBonusOption(new BonusOption("Harmony", kingdomino));
		currentGame.addSelectedBonusOption(new BonusOption("MiddleKingdom", kingdomino));
	}

	@When("starting a new game is initiated")
	public void starting_a_new_game_is_initiated() {
		try {
			NewGameStartController.initializeGame();
		}
		catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}

	@When("reveal first draft is initiated")
	public void reveal_first_draft_is_initiated() {
		try {
			NewGameStartController.initializeDraft();
		}
		catch (InvalidInputException e) {
			e.printStackTrace();
		}
	}

	@Then("all kingdoms shall be initialized with a single castle")
	public void all_kingdoms_shall_be_initialized_with_a_single_castle() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		for (int i = 0; i< game.getNumberOfPlayers(); i ++){
			Player player = new Player(game);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0,0,kingdom,player);
		}
	}

	//Then all castle are placed at 0:0 in their respective kingdoms
	@Then("all castle are placed at {int}:{int} in their respective kingdoms")
	public void all_castle_are_placed_at_in_their_respective_kingdoms(Integer int1, Integer int2) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<Player> playerList = kingdomino.getCurrentGame().getPlayers();
		for (Player player: playerList) {
			Kingdom kingdom = player.getKingdom();
			new Castle(int1,int2,kingdom,player);}
	}


	@Then("the first draft of dominoes is revealed")
	public void the_first_draft_of_dominoes_is_revealed() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.getCurrentDraft();

	}

	@Then("all the dominoes form the first draft are facing up")
	public void all_the_dominoes_form_the_first_draft_are_facing_up() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		game.getAllDraft(0).setDraftStatus(DraftStatus.FaceUp);
	}

	@Then("all the players have no properties")
	public void all_the_players_have_no_properties() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		List<Player> playerList = game.getPlayers();
		for (Player player: playerList) {
			Kingdom kingdom = player.getKingdom();
			Property property = new Property(kingdom);
			property.setSize(0);
		}
	}

	@Then("all player scores are initialized to zero")
	public void all_player_scores_are_initialized_to_zero() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<Player> playerList = kingdomino.getCurrentGame().getPlayers();
		for (Player player: playerList) {
			player.setPropertyScore(0);
		}
	}
}
