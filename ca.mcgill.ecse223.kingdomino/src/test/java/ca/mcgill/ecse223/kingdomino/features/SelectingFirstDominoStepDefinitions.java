package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

/**
 * @author Violet Wei
 * Feature: Selecting First Domino
 */

public class SelectingFirstDominoStepDefinitions {

	/* Background */
	@Given("the game has been initialized for selecting first domino")
	public void the_game_has_been_initialized_for_selecting_first_domino() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		KingdominoApplication.getStateMachine();
		GameplayController.triggerStartNewGameInSM(4);
		
	}

	/* Scenario Outline: Select first domino of the game */
	@Given("the initial order of players is {string}")
	public void the_initial_order_of_players_is_playerorder(String playerOrder) {

	}

	@Given("the current draft has the dominoes with ID 1,2,3,4")
	public void the_current_draft_has_the_dominoes_with_ID() {

	}

	@Given("player's first domino selection of the game is {string}")
	public void players_first_domino_selection_of_the_game_is_currentselection(String currentSelection) {

	}

	@Given("the {string} player is selecting his/her domino with ID {int}")
	public void the_player_is_selecting_hisher_domino_with_ID(String currentplayer, int chosendominoid) {

	}

	@When("the {string} player completes his/her domino selection")
	public void the_player_completes_his_her_domino_selection(String currentplayer) {

	}

	@Then("the {string} player shall be {string} his/her domino")
	public void the_nextplayer_shall_be_action_his_her_domino(String nextplayer, String action) {

	}


	// We use the annotation @And to signal precondition check instead of
	// initialization (which is done in @Given methods)
	@And("the validation of domino selection returns {string}")
	public void the_validation_of_domino_selection_returns(String expectedValidationResultString) {
		boolean expectedValidationResult = true;
		if ("success".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = true;
		} else if ("error".equalsIgnoreCase(expectedValidationResultString.trim())) {
			expectedValidationResult = false;
		} else {
			throw new IllegalArgumentException(
					"Unknown validation result string \"" + expectedValidationResultString + "\"");
		}
		boolean actualValidationResult = false;

		// TODO call here the guard function from the statemachine and store the result
		// actualValidationResult = gameplay.isSelectionValid();

		// Check the precondition prescribed by the scenario
		assertEquals(expectedValidationResult, actualValidationResult);
	}

	/* Scenario Outline: Complete first turn of domino selection */

	@Then("a new draft shall be available, face down")
    public void a_new_draft_shall_be_available_face_down() {

	}
}
