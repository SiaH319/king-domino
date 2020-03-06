package ca.mcgill.ecse223.kingdomino.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProvideUserProfile {
	/*
	 * Feature: Provide User Profile
  As a player, I wish to use my unique user name in when a game starts. 
  I also want the Kingdomino app to maintain my game statistics
   (e.g. number of games played, won, etc.). I also wish wish to view all users.

  Background: 
    Given the program is started and ready for providing user profile

  Scenario Outline: Create the first user
    Given there are no users exist
    When I provide my username "<name>" and initiate creating a new user
    Then the user "<name>" shall be in the list of users

    Examples: 
      | name      |
      | firstuser |

  Scenario Outline: Create a new user
    Given the following users exist:
      | name  |
      | test1 |
      | test2 |
    When I provide my username "<name>" and initiate creating a new user
    Then the user creation shall "<status>"

    Examples: 
      | name  | status  |
      | test1 | fail    |
      | test2 | fail    |
      | Test1 | fail    |
      | test3 | succeed |
      |       | fail    |
      | te.() | fail    |

  Scenario: List all users
    Given the following users exist:
      | name  |
      | testa |
      | testc |
      | testb |
    When I initiate the browsing of all users
    Then the users in the list shall be in the following alphabetical order:
      | name  | placeinlist |
      | testa |           1 |
      | testc |           3 |
      | testb |           2 |

  Scenario Outline: View game statistics for a user
    Given the following users exist with their game statistics:
      | name  | playedGames | wonGames |
      | test1 |           0 |        0 |
      | test3 |           5 |        5 |
      | test2 |          10 |        6 |
    When I initiate querying the game statistics for a user "<name>"
    Then the number of games played by "<name>" shall be <playedGames> 
    Then the number of games won by "<name>" shall be <wonGames>
    
    Examples:
      | name  | playedGames | wonGames |
      | test1 |           0 |        0 |
      | test3 |           5 |        5 |
      | test2 |          10 |        6 |

	 */
	@Given("the program is started and ready for providing user profile")
	public void the_program_is_started_and_ready_for_providing_user_profile() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("there are no users exist")
	public void there_are_no_users_exist() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I provide my username {string} and initiate creating a new user")
	public void i_provide_my_username_and_initiate_creating_a_new_user(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("the user {string} shall be in the list of users")
	public void the_user_shall_be_in_the_list_of_users(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Given("the following users exist:")
	public void the_following_users_exist(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}

	@Then("the user creation shall {string}")
	public void the_user_creation_shall(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("I initiate the browsing of all users")
	public void i_initiate_the_browsing_of_all_users() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
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

	@Given("the following users exist with their game statistics:")
	public void the_following_users_exist_with_their_game_statistics(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}

	@When("I initiate querying the game statistics for a user {string}")
	public void i_initiate_querying_the_game_statistics_for_a_user(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
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
