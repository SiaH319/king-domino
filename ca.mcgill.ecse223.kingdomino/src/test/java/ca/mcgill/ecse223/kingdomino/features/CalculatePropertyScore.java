package ca.mcgill.ecse223.kingdomino.features;

import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatePropertyScore {
//	@Given("the game is initialized for calculate property attributes")
//	public void the_game_is_initialized_for_calculate_property_attributes() {
//	    // Write code here that turns the phrase above into concrete actions
//		Kingdomino kingdomino = new Kingdomino();
//		Game game = new Game(48, kingdomino);
//		game.setNumberOfPlayers(4);
//		kingdomino.setCurrentGame(game);
//		
//		//addDefaultUsersAndPlayers(game);
//		//createAllDominoes(game);
//		game.setNextPlayer(game.getPlayer(0));
//		KingdominoApplication.setKingdomino(kingdomino);
//		
//	    throw new cucumber.api.PendingException();
//	}
//	@Given ("the player's kingdom has the following dominoes")
//	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
//	
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		List<Map<String, String>> valueMaps = dataTable.asMaps();
//		for (Map<String, String> map : valueMaps) {
//			// Get values from cucumber table
//			Integer id = Integer.decode(map.get("id"));
//			DirectionKind dir = getDirection(map.get("dominodir"));
//			Integer posx = Integer.decode(map.get("posx"));
//			Integer posy = Integer.decode(map.get("posy"));
//
//			// Add the domino to a player's kingdom
//			Domino dominoToPlace = getdominoByID(id);
//			Kingdom kingdom = game.getPlayer(0).getKingdom();
//			DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
//			domInKingdom.setDirection(dir);
//			dominoToPlace.setStatus(DominoStatus.PlacedInKingdom);	
//		}
//	}
//
//	@When("calculate property attributes is initiated")
//	public void calculate_property_attributes_is_initiated() {
//	    // Write code here that turns the phrase above into concrete actions
//		
//	    throw new cucumber.api.PendingException();
//	}
//
//	@Then("the player shall have a total of {int} properties")
//	public void the_player_shall_have_a_total_of_properties(Integer int1) {
//		
//	    // Write code here that turns the phrase above into concrete actions
//	    throw new cucumber.api.PendingException();
//	}
//
//	@Then("the player shall have properties with the following attributes:")
//	public void the_player_shall_have_properties_with_the_following_attributes(io.cucumber.datatable.DataTable dataTable) {
//	    // Write code here that turns the phrase above into concrete actions
//	    // For automatic transformation, change DataTable to one of
//	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
//	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
//	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
//	    //
//	    // For other transformations you can register a DataTableType.
//	    throw new cucumber.api.PendingException();
//	}
//
//	
//	//private helper classes
//	private Domino getdominoByID(int id) {
//		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//		for (Domino domino : game.getAllDominos()) {
//			if (domino.getId() == id) {
//				return domino;
//			}
//		}
//		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
//	}
//	
//	//private
//	
//	private DirectionKind getDirection(String dir) {
//		switch (dir) {
//		case "up":
//			return DirectionKind.Up;
//		case "down":
//			return DirectionKind.Down;
//		case "left":
//			return DirectionKind.Left;
//		case "right":
//			return DirectionKind.Right;
//		default:
//			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
//		}
//	}
	
}
