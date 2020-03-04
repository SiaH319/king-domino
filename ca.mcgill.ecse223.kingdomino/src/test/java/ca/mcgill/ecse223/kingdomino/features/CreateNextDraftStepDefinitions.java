package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.CreateNextDraftController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
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
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad.
 *         Created Feb 25, 2020.
 */
//@RunWith(Cucumber.class)
//@CucumberOptions(
//		plugin = "pretty", 
//		features = "src/test/resources",
//		glue = "CreateNextDraft.feature")
public class CreateNextDraftStepDefinitions {
	
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

	
	
	
	/**
	 * 
	 * @author Mohamad Dimassi.
	 * Initialise the game
	 *
	 */
	
	@Given("the game is initialized to reveal next draft")
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
	

	@Given("there has been {string} drafts created")
	public void there_has_been_drafts_created(String myString) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Integer numDraft= Integer.parseInt(myString);
		for(int i=0;i<numDraft;i++) {
			 Draft D =new Draft(DraftStatus.FaceUp,game);
			boolean added=game.addAllDraft(D);
			assertEquals(true,added);
		}
	}
	
	@Given("there is a current draft")
	public void there_is_a_current_draft() {
		boolean setCurrentDraft=false;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		setCurrentDraft=game.setCurrentDraft(game.getAllDraft(game.getAllDrafts().size()-2));
		if(setCurrentDraft) {
			System.out.println("succesfully set current draft");
		}
		else {
			System.out.println("failed to set current draft");

		}
		
	}
	@Given("there is a next draft")
	public void there_is_a_next_draft() {
		boolean setNextDraft=false;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		setNextDraft=game.setNextDraft(game.getAllDraft(game.getAllDrafts().size()-1));
		if(setNextDraft) {
			System.out.println("succesfully set next draft");
		}
		else {
			System.out.println("failed to set next draft");

		}
		
	}
	

	
	@Given("the top {int} dominoes in my pile have the IDs {string}")
	public void the_top_5_dominoes_in_my_pile_have_the_IDs(Integer NumOfDominoes,String listOfIDs) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> myList =getListOfIDs(listOfIDs);
		game.setTopDominoInPile(getdominoByID(myList.get(0)));
		Domino TopDomino = game.getTopDominoInPile();
		for(int i=1;i<myList.size();i++) {
			TopDomino.setNextDomino(getdominoByID(myList.get(i)));
			TopDomino=TopDomino.getNextDomino();
		}
	}
	
	@When("create next draft is initiated")
	public void create_next_draft_is_initiated() {
		CreateNextDraftController.createNewDraftIsInitiated();
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	@Then("The pile is empty")
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
		assertEquals(game.getCurrentDraft(),game.getCurrentDraft());
	}
	@Then("the top domino of the pile is ID {string}")
	public void the_top_domino_of_the_pile_is_ID(String topID) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino actualTop=game.getTopDominoInPile();
		assertEquals(getdominoByID(Integer.parseInt(topID)),actualTop);
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
		assertEquals(expectedListD,DominoesInDraft);

		
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
	}

	
	
	
	
	
	


