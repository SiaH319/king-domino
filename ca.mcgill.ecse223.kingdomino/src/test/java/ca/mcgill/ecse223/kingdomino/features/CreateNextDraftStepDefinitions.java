package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * As a player, I want the Kingdomino app to automatically provide 
  the next four dominos once the previous round is finished	
 *
 * @author Mohamad.
 *         Created Feb 25, 2020.
 */

public class CreateNextDraftStepDefinitions {
	Game PreviousGame;
	

	
	
	
	/**
	 * 
	 * @author Mohamad Dimassi.
	 * Initialise the game
	 *
	 */
	
	@Given("the game is initialized to create next draft")
	public void the_game_is_initialized_to_reveal_next_draft() {
				// Intialize empty game
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
	

	@Given("there has been {int} drafts created")
	public void there_has_been_drafts_created(int number) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Integer numDraft=number;
		for(int i=0;i<numDraft;i++) { // add {int} drafts to the list of Drafts
			 Draft D =new Draft(DraftStatus.FaceUp,game);
			 game.addAllDraft(D);	
		}
		
		
	}
	
	@Given("there is a current draft")
	public void there_is_a_current_draft() {
		boolean setCurrentDraft=false;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		setCurrentDraft=game.setCurrentDraft(game.getAllDraft(1));
	
		
	}
	@Given("there is a next draft")
	public void there_is_a_next_draft() {
		boolean setNextDraft=false;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		setNextDraft=game.setNextDraft(game.getAllDraft(game.getAllDrafts().size()-1)); // last draft created is the newest, so must be the next draft
		
	}
	@Given("this is a {int} player game")
	public void this_is_a_player_game(Integer numOfPlayer) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setNumberOfPlayers(numOfPlayer); // set the number of players in the game
	}

	
	@Given("the top {int} dominoes in my pile have the IDs {string}")
	public void the_top_5_dominoes_in_my_pile_have_the_IDs(Integer NumOfDominoes,String listOfIDs) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> myList =getListOfIDs(listOfIDs);
		game.setTopDominoInPile(getdominoByID(myList.get(0)));// set top domino as the first integer in the string
		Domino TopDomino = game.getTopDominoInPile();
		for(int i=1;i<myList.size();i++) {
			TopDomino.setNextDomino(getdominoByID(myList.get(i))); //update the linked list accordingly  
			TopDomino=TopDomino.getNextDomino();
		}
	}
	
	@When("create next draft is initiated")
	public void create_next_draft_is_initiated() {
		DraftController.createNewDraftIsInitiated();
	}
	
	
	@Then("the pile is empty")
	public void the_pile_is_empty() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean hasTop =game.hasTopDominoInPile();
		assertEquals(false,hasTop);
		
	}
	
	@Then("there is no next draft")
	public void there_is_no_next_draft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean HasNextDraft =game.hasNextDraft();
		assertEquals(false,HasNextDraft);
	}

	@Then("the former next draft is now the current draft")
	public void the_former_next_draft_is_now_current_draft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft current = game.getCurrentDraft();
		if(game.getNextDraft()==null) {
			Draft LastCreated =game.getAllDraft(game.getAllDrafts().size()-1);
			assertEquals(LastCreated,current);
		}
		else {
			Draft LastCreated =game.getAllDraft(game.getAllDrafts().size()-2);
			assertEquals(LastCreated,current);
		}

	}

	@Then("the top domino of the pile is ID {int}")
	public void the_top_domino_of_the_pile_is_ID(int topID) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino actualTop=game.getTopDominoInPile();
		assertEquals(getdominoByID(topID),actualTop);
	}
	@Then("the dominoes in the next draft are face down")
	public void the_dominoes_in_the_next_draft_are_face_down() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		DraftStatus actualStatus=game.getNextDraft().getDraftStatus();
		DraftStatus expectedStatus = DraftStatus.FaceDown;
		assertEquals(expectedStatus,actualStatus);
	}
	@Then("the next draft now has the dominoes {string}")
	public void the_next_draft_now_has_the_dominoes(String listOfIDs) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> expectedList = getListOfIDs(listOfIDs);
		ArrayList<Domino> expectedListD =new ArrayList<Domino>();
		for(int i=0;i<expectedList.size();i++) {
			expectedListD.add(getdominoByID(expectedList.get(i)));
		}
		List<Domino> DominoesInDraft =game.getNextDraft().getIdSortedDominos();
		for(int i=0;i<game.getNextDraft().maximumNumberOfIdSortedDominos();i++) {
			assertEquals(expectedListD.get(i).getId(),DominoesInDraft.get(i).getId());//make sure the correct dominoes are in the draft
		}

		
	}
	@Then("a new draft is created from dominoes {string}")
	public void a_new_draft_is_created_from_dominoes(String listOfIDs) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> actualList = getListOfIDs(listOfIDs);
		for(int i=0;i<game.getNextDraft().maximumNumberOfIdSortedDominos();i++) {
			Domino current =getdominoByID(actualList.get(i));
			assertEquals(DominoStatus.InNextDraft,current.getStatus());
		}

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

	
	
	
	
	
	


