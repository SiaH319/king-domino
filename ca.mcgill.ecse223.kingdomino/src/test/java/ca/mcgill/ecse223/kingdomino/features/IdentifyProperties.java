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

public class IdentifyProperties {
	
//	@Given("the game is initialized for identify properties")
//	public void the_game_is_initialized_for_identify_properties() {
//	    // Write code here that turns the phrase above into concrete actions
//			// Intialize empty game
//			Kingdomino kingdomino = new Kingdomino();
//			Game game = new Game(48, kingdomino);
//			game.setNumberOfPlayers(4);
//			kingdomino.setCurrentGame(game);
//			//Populate game do i need to populate game?
//			//addDefaultUsersAndPlayers(game);
//			//createAllDominoes(game);
//			game.setNextPlayer(game.getPlayer(0));
//			KingdominoApplication.setKingdomino(kingdomino);
//			
//		  
//	   // throw new cucumber.api.PendingException();
//	}
//	
//	@Given ("the player's kingdom has the following dominoes")
//	public void the_player_s_kingdom_has_the_following_dominoes(io.cucumber.datatable.DataTable dataTable) {
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
//	
//}
//		
//
//	@When("the properties of the player are identified")
//	public void the_properties_of_the_player_are_identified() {
//	// Write code here that turns the phrase above into concrete actions
//	Game game = KingdominoApplication.getKingdomino().getCurrentGame();	
//	Kingdom kingdom = game.getPlayer(0).getKingdom(); //getting the players kingdom
//	int numOfProp = kingdom.numberOfProperties();
//	
//	
//		
//	    throw new cucumber.api.PendingException();
//	}
//
//	@Then("the player shall have the following properties:")
//	public void the_player_shall_have_the_following_properties(io.cucumber.datatable.DataTable dataTable) {
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
//
////[private class
//		private Domino getdominoByID(int id) {
//			Game game = KingdominoApplication.getKingdomino().getCurrentGame();
//			for (Domino domino : game.getAllDominos()) {
//				if (domino.getId() == id) {
//					return domino;
//				}
//			}
//			throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
//		}
//		
//		//private
//		
//		private DirectionKind getDirection(String dir) {
//			switch (dir) {
//			case "up":
//				return DirectionKind.Up;
//			case "down":
//				return DirectionKind.Down;
//			case "left":
//				return DirectionKind.Left;
//			case "right":
//				return DirectionKind.Right;
//			default:
//				throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
//			}
//		}
		
		
}