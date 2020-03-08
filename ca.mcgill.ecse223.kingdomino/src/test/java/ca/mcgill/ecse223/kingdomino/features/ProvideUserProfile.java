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


public class ProvideUserProfile {
	/**
	 *  Feature: Provide User Profile
	 *  @author: Sia Ham
	 *  As a player, 
	 *  I wish to use my unique user name in when a game starts. 
	 *  I also want the Kingdomino app to maintain my game statistics
	 *   (e.g. number of games played, won, etc.). 
	 *   I also wish wish to view all users.
	 */
	private boolean isValid;
	
	/*
	 * Background
	 */
	@Given("the program is started and ready for providing user profile")
	public void the_program_is_started_and_ready_for_providing_user_profile() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		kingdomino.hasUsers();
	}
	
	
	/*
	 *   Scenario Outline: Create the first user   
	 *   Examples: 
  			| name      |
      		| first user |
	 */
	@Given("there are no users exist")
	public void there_are_no_users_exist() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		if (!(kingdomino.numberOfUsers()==0)) {
			for (User user: kingdomino.getUsers()) {
				user.delete();
			}
		}
	}

	@When("I provide my username {string} and initiate creating a new user")
	public void i_provide_my_username_and_initiate_creating_a_new_user(String name) throws InvalidInputException {
		isValid = true;
		try {
			InitializationController.createUser(name);
		} 		
		catch (InvalidInputException e) {
			e.printStackTrace();
			isValid = false;
		}
		
	}


	@Then("the user {string} shall be in the list of users")
	public void the_user_shall_be_in_the_list_of_users(String name) {
		String actual = User.getWithName(name).getName();
		assertEquals(name, actual);	
	}

	
	/*
	 *   Scenario Outline: Create a new user
	 */
	@Given("the following users exist:")
	public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) throws InvalidInputException {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			String name = map.get("name");
			kingdomino.addUser(name);	
		}
	}
	

	@Then("the user creation shall {string}") // user creation shall fail or succeed
	public void the_user_creation_shall(String status) {
		Boolean expectedResult = (!status.equals("fail")); 
		assertEquals(expectedResult,isValid);
		}
	    

	/*
	 *    Scenario: List all users
	 */
	
	//@89: Given the following users exist:
	@When("I initiate the browsing of all users")
	public void i_initiate_the_browsing_of_all_users() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		kingdomino.getUsers();
	}

	@Then("the users in the list shall be in the following alphabetical order:")
	public void the_users_in_the_list_shall_be_in_the_following_alphabetical_order(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}
	
	/*
	 *   Scenario Outline: View game statistics for a user
	 */

	@Given("the following users exist with their game statistics:")
	public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			// Get values from cucumber table
			User user = kingdomino.addUser(map.get("name"));
			Integer played = Integer.decode(map.get("playedGames"));
			Integer won = Integer.decode(map.get("wonGame"));
	
			// users has their game statistics
			user.setWonGames(won);
			user.setPlayedGames(played);}
	}

	@When("I initiate querying the game statistics for a user {string}")
	public void i_initiate_querying_the_game_statistics_for_a_user(String string) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		for (User user: kingdomino.getUsers()) {
			user.getPlayedGames();
			user.getWonGames();
		}
	}

	@Then("the number of games played by and games won by the user shall be the following:")
	public void the_number_of_games_played_by_and_games_won_by_the_user_shall_be_the_following(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}


}
