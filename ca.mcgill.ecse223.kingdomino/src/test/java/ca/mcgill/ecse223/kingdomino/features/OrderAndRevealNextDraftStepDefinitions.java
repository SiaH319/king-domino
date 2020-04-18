package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DraftController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
/**
 * As a player, I want the Kingdomino app to automatically order 
  and reveal the next draft of dominos in increasing order 
  with respect to their numbers so that I know which are the more valuable dominos.
 *
 * @author Mohamad.
 *         Created Mar 8, 2020.
 */
public class OrderAndRevealNextDraftStepDefinitions {
	
	
	
	@Given("the game is initialized for order next draft of dominoes")
	public void the_game_is_initiated_for_order_next_draft_of_dominoes(){
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	@Given("the next draft is {string}")
	public void the_next_draft_is(String listOfIDs) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> myList =getListOfIDs(listOfIDs);
		Draft nextDraft = new Draft(DraftStatus.FaceDown,game);
		game.setNextDraft(nextDraft);
		for(int i=0;i<myList.size();i++) {
			Domino domino = getdominoByID(myList.get(i));
			domino.setStatus(DominoStatus.InNextDraft);
			nextDraft.addIdSortedDomino(getdominoByID(myList.get(i)));
		}
	}
	
	@Given("the dominoes in next draft are facing down")
	public void the_dominoes_in_the_next_draft_are_facing_down(){
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft=game.getNextDraft();
		nextDraft.setDraftStatus(DraftStatus.FaceDown);
	}
	
	@When("the ordering of the dominoes in the next draft is initiated")
	public void the_ordering_of_the_dominoes_in_the_next_draft_is_initiated() {
		DraftController.orderNewDraftInitiated();
	}
	@Then("the status of the next draft is sorted")
	public void the_status_of_the_next_draft_is_sorted() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertEquals(DraftStatus.Sorted,game.getNextDraft().getDraftStatus());
	}
	@Then("the order of dominoes in the draft will be {string}")
	public void the_order_of_dominoes_in_the_draft_will_be(String listOfIDs) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> expected =getListOfIDs(listOfIDs);
		ArrayList<Integer> actual = new ArrayList<Integer>();
		for(Domino d:game.getNextDraft().getIdSortedDominos()) {
			actual.add(d.getId());
		}
		assertEquals(expected.size(),actual.size());
		for(int i=0;i<expected.size();i++) {
			assertEquals((int)expected.get(i),(int)actual.get(i));
		}
	}
	
	@Given("the game is initialized for reveal next draft of dominoes")
	public void the_game_is_initialized_for_reveal_next_draft_of_dominoes() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayers(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	@Given("the dominoes in next draft are sorted")
	public void the_dominoes_in_next_draft_are_sorted() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft=game.getNextDraft();
		nextDraft.setDraftStatus(DraftStatus.Sorted);
	}
	@When("the revealing of the dominoes in the next draft is initiated")
	public void the_revealing_of_the_dominoes_in_the_next_draft_is_initiated() {
		DraftController.revealDominoesInitiated();
	}
		
	@Then("the status of the next draft is face up")
	public void the_status_of_the_next_draft_is_face_up() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		assertEquals(DraftStatus.FaceUp,game.getNextDraft().getDraftStatus());
	}
		
		
	
	
	//Helper Methods
	private void addDefaultUsersAndPlayers(Game game) {
		String[] userNames = { "User1", "User2", "User3", "User4" };
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	
	private void createAllDominoes(Game game) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
			String line = "";
			String delimiters = "[:\\+()]";
			while ((line = br.readLine()) != null) {
				String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
				int dominoId = Integer.decode(dominoString[0]);
				TerrainType leftTerrain = getTerrainType(dominoString[1]);
				TerrainType rightTerrain = getTerrainType(dominoString[2]);
				int numCrown = 0;
				if (dominoString.length > 3) {
					numCrown = Integer.decode(dominoString[3]);
				}
				new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new java.lang.IllegalArgumentException(
					"Error occured while trying to read alldominoes.dat: " + e.getMessage());
		}
	}
	
	
	private TerrainType getTerrainType(String terrain) {
		switch (terrain) {
		case "W":
			return TerrainType.WheatField;
		case "F":
			return TerrainType.Forest;
		case "M":
			return TerrainType.Mountain;
		case "G":
			return TerrainType.Grass;
		case "S":
			return TerrainType.Swamp;
		case "L":
			return TerrainType.Lake;
		default:
			throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
		}
	}
	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	
	
	private ArrayList<Integer> getListOfIDs(String aListOfIDs){
		boolean beforeIsDigit =false;
		ArrayList<Integer> myList = new ArrayList<Integer>();
		String [] ids = aListOfIDs.split(",");
		for(int i=0; i<ids.length;i++) {
			myList.add(Integer.parseInt(ids[i]));
		}

		return myList;
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
}
