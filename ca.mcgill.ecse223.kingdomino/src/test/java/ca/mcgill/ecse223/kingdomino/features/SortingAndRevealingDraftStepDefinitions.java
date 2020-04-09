package ca.mcgill.ecse223.kingdomino.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.DraftController;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
/**
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad.
 *         Created Apr 8, 2020.
 */
public class SortingAndRevealingDraftStepDefinitions {
	
	@Given("there is a next draft, face down")
	public void there_is_a_next_draft_face_down() {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		KingdominoApplication.getStateMachine();
        Game game = new Game(48, kingdomino);
        game.setNumberOfPlayers(4);
        kingdomino.setCurrentGame(game);
        // Populate game
        addDefaultUsersAndPlayers(game);
        createAllDominoes(game);
        game.setNextPlayer(game.getPlayer(0));
        KingdominoApplication.setKingdomino(kingdomino);
		if(game.getCurrentDraft()==null) {
			Draft newCurrentDraft = new Draft(DraftStatus.FaceUp,game);
			game.setCurrentDraft(newCurrentDraft);
			newCurrentDraft.addIdSortedDomino(getdominoByID(4));
			newCurrentDraft.addIdSortedDomino(getdominoByID(1));
			newCurrentDraft.addIdSortedDomino(getdominoByID(2));
			newCurrentDraft.addIdSortedDomino(getdominoByID(3));
		}
		if(game.getNextDraft()==null) {
			Draft newNextDraft= new Draft(DraftStatus.FaceDown, game);
			game.setNextDraft(newNextDraft);
			newNextDraft.addIdSortedDomino(getdominoByID(8));
			newNextDraft.addIdSortedDomino(getdominoByID(7));
			newNextDraft.addIdSortedDomino(getdominoByID(6));
			newNextDraft.addIdSortedDomino(getdominoByID(5));

		}
		else {
			game.getNextDraft().setDraftStatus(DraftStatus.FaceDown);
		}
		
	}
	
	@And("all dominoes in current draft are selected")
	public void all_dominoes_in_current_draft_are_selected() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int playerIndex=0;
		for(Domino d :game.getCurrentDraft().getIdSortedDominos()) {
			if(d.hasDominoSelection()==false) {
				DominoSelection newDominoSelection = new DominoSelection(game.getPlayer(playerIndex), d, game.getCurrentDraft());
				playerIndex++;
				game.getCurrentDraft().addSelection(newDominoSelection);
				d.setDominoSelection(newDominoSelection);
			}
		}
		boolean expectedResult =true;
		boolean actualResult=GameplayController.areAlmostAllDominoesInCurrentDraftSelected();
		assertEquals(expectedResult,actualResult);
		
		KingdominoApplication.getStateMachine().setGamestatus("OrderingNextDraft");
	}
	@When("next draft is sorted")
	public void next_draft_is_sorted() {
		System.out.println("triggering the order event");
		GameplayController.triggerEventsInSM("order");
	}
	
	@When("next draft is revealed")
	public void next_draft_is_revealed() {
		GameplayController.triggerEventsInSM("reveal");
	}
	
	@Then("the next draft shall be sorted")
	public void the_next_draft_shall_be_sorted() {
		boolean expectedSortedResult=true;
		boolean actualSortedResult=false;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft=game.getNextDraft();
		
		for(int i=1;i<nextDraft.getIdSortedDominos().size();i++) {
			actualSortedResult=(nextDraft.getIdSortedDomino(i).getId()>nextDraft.getIdSortedDomino(i-1).getId());
			assertEquals(expectedSortedResult,actualSortedResult);
		}
		
	}
	@Then("the next draft shall be facing up")
	public void the_next_draft_shall_be_facing_up() {
		boolean expectedFaceUp=true;
		boolean actualFaceUp=false;
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(game.getNextDraft().getDraftStatus().equals(DraftStatus.FaceUp)) {
			actualFaceUp=true;
		}
		assertEquals(expectedFaceUp,actualFaceUp);
	}
	
	@Then("it shall be the player's turn with the lowest domino ID selection")
	public void it_shall_be_the_player_s_turn_with_the_lowest_domino_ID_selection() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean expectedLowest=true;
		boolean actualLowest=true;
		Player nextPlayer=game.getNextPlayer();
		if(nextPlayer==null) {
			System.out.println("There is no nxt player");
		}
		System.out.println("Current Player has selected domino"+ nextPlayer.getDominoSelection().getDomino().getId());
		for(Player p: game.getPlayers()) {
			if(p.getDominoSelection()==null) {
				System.out.println("Player does not have a domino selection");
			}
			actualLowest=(p.getDominoSelection().getDomino().getId()>=nextPlayer.getDominoSelection().getDomino().getId());
			if(actualLowest==false) {
				break;
			}
		}
		assertEquals(expectedLowest,actualLowest);
	}
	

	
	///////////////////////////////////////
	/// -----Private Helper Methods---- ///
	///////////////////////////////////////

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

	private Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
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

	private DirectionKind getDirection(String dir) {
		switch (dir) {
		case "up":
			return DirectionKind.Up;
		case "down":
			return DirectionKind.Down;
		case "left":
			return DirectionKind.Left;
		case "right":
			return DirectionKind.Right;
		default:
			throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
		}
	}

	private DominoStatus getDominoStatus(String status) {
		switch (status) {
		case "inPile":
			return DominoStatus.InPile;
		case "excluded":
			return DominoStatus.Excluded;
		case "inCurrentDraft":
			return DominoStatus.InCurrentDraft;
		case "inNextDraft":
			return DominoStatus.InNextDraft;
		case "erroneouslyPreplaced":
			return DominoStatus.ErroneouslyPreplaced;
		case "correctlyPreplaced":
			return DominoStatus.CorrectlyPreplaced;
		case "placedInKingdom":
			return DominoStatus.PlacedInKingdom;
		case "discarded":
			return DominoStatus.Discarded;
		default:
			throw new java.lang.IllegalArgumentException("Invalid domino status: " + status);
		}
	}
	
	
	
	
	
}
