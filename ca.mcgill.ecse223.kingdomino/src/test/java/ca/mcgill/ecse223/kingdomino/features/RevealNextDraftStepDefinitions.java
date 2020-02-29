package ca.mcgill.ecse223.kingdomino.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
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
public class RevealNextDraftStepDefinitions {
	
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
	
	@Given("the top 5 dominoes in my pile have IDs id9, id10, id11, id12, id13")
	public void the_top_5_dominoes_in_my_pile_have_IDs_id9_id10_id11_id12_id13(){

		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino topDomino=game.getTopDominoInPile();
		topDomino =getdominoByID(9);
		Domino second =topDomino.getNextDomino();
		second=getdominoByID(10);
		Domino third =second.getNextDomino();
		third=getdominoByID(11);
		Domino fourth =third.getNextDomino();
		fourth=getdominoByID(12);
		Domino fifth =fourth.getNextDomino();
		fifth=getdominoByID(13);
		
	}
	
	@Given("there has been {int} drafts")
	public void there_has_been_drafts(Integer numDrafts) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		for(int i=0;i<numDrafts;i++) {
			 Draft D =new Draft(DraftStatus.FaceUp,game);
			boolean added=game.addAllDraft(D);
			assertEquals(true,added);
		}
	}
	
	@Given("there is a current draft")
	public void there_is_a_current_draft() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft currentD = new Draft(DraftStatus.FaceUp,game);
		game.setCurrentDraft(currentD);
		
	}
	
	@Given("there is an existing next draft with IDs id4, id5, id6, id7")
	public void there_is_an_existing_next_draft_with_IDs_id4_id5_id6_id7() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft=new Draft(DraftStatus.FaceDown,game);
		game.setNextDraft(nextDraft);
		
		Domino d1= getdominoByID(4);
		boolean added1=nextDraft.addIdSortedDomino(d1);
		assertEquals(true,added1);
		d1.setStatus(DominoStatus.InNextDraft);
		
		Domino d2= getdominoByID(5);
		boolean added2=nextDraft.addIdSortedDomino(d2);
		assertEquals(true,added2);
		d2.setStatus(DominoStatus.InNextDraft);
		
		Domino d3= getdominoByID(6);
		boolean added3=nextDraft.addIdSortedDomino(d3);
		assertEquals(true,added3);
		d3.setStatus(DominoStatus.InNextDraft);
		
		Domino d4= getdominoByID(7);
		boolean added4=nextDraft.addIdSortedDomino(d4);
		assertEquals(true,added4);
		d4.setStatus(DominoStatus.InNextDraft);
		

	}
	
	
	
	
	
	
	
	

}
