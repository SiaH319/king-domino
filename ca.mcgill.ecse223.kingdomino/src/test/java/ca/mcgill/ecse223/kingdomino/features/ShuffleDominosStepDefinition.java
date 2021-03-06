package ca.mcgill.ecse223.kingdomino.features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.ShuffleDominoesController;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad.
 *         Created Mar 9, 2020.
 */
public class ShuffleDominosStepDefinition {
	int numberOfPlayers;
	Game PreviousGame;
	@Given("the game is initialized for shuffle dominoes")
	public void the_game_is_initialized_for_shuffle_dominoes() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		PreviousGame =game;
		game.setNumberOfPlayers(4);
		kingdomino.setCurrentGame(game);
		// Populate game
		addDefaultUsersAndPlayersFour(game);
		createAllDominoes(game);
		game.setNextPlayer(game.getPlayer(0));
		KingdominoApplication.setKingdomino(kingdomino);
	}
	
		
	@Given("there are {int} players playing")
	public void there_are_players_playing(Integer numPlayers) {
		
		if((int)numPlayers==3) { 
			Game game = new Game(12, PreviousGame.getKingdomino());
			game.setNumberOfPlayers(3);
			String[] usernames = {"Kser1","Kser2","Kser3"};
			addUsersAndPlayers(usernames,game);
			createAllDominoes(game);
			for(int i = 0 ; i<12;i++) {
				int size = game.getAllDominos().size();
				int index = (int)(Math.random() * (size));
				Domino domino = game.getAllDomino(index);
				while(domino.getStatus()== Domino.DominoStatus.Excluded) {
					index = (int)(Math.random() * (size));
					domino = game.getAllDomino(index);
				}
				domino.setStatus(Domino.DominoStatus.Excluded);

			}
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.getKingdomino().setCurrentGame(game);
			KingdominoApplication.setKingdomino(game.getKingdomino());
		}
		else if((int)numPlayers==2) {
			Game game = new Game(6, PreviousGame.getKingdomino());
			game.setNumberOfPlayers(2);
			String[] usernames = {"Aser1","Aser2"};
			addUsersAndPlayers(usernames,game);
			createAllDominoes(game);
			for(int i = 0 ; i<24;i++) {
				int size = game.getAllDominos().size();
				int index = (int)(Math.random() * (size));
				Domino domino = game.getAllDomino(index);
				while(domino.getStatus()== Domino.DominoStatus.Excluded) {
					index = (int)(Math.random() * (size));
					domino = game.getAllDomino(index);
				}
				domino.setStatus(Domino.DominoStatus.Excluded);

			}
			game.setNextPlayer(game.getPlayer(0));
			KingdominoApplication.getKingdomino().setCurrentGame(game);
			KingdominoApplication.setKingdomino(game.getKingdomino());
			
			
		}
		
	}
	
	@When("the shuffling of dominoes is initiated")
	public void the_shuffling_of_dominoes_is_initiated() throws Exception {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ShuffleDominoesController.shuffleDomino(game.getAllDominos(),game);
		
	}
	@When("I initiate to arrange the domino in the fixed order {string}")
	public void I_initiate_to_arrange_the_domino_in_the_fixed_order(String orderedList) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ShuffleDominoesController.fixedArrangement(orderedList,game);
	}
	
	@Then("the first draft shall exist")
	public void the_first_draft_shall_exist() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft firstDraft = game.getAllDraft(0);
		assertNotNull(firstDraft);
	}
	
	@Then("the first draft should have {int} dominoes on the board face down")
	public void the_first_draft_should_have_dominoes_on_the_board_face_down(Integer expectedDominoesInDraft) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
	
		int actualDominoesInDraft =game.getAllDraft(0).getIdSortedDominos().size();
	
		assertEquals((int)expectedDominoesInDraft,actualDominoesInDraft);
	}
	
	@Then("there should be {int} dominoes left in the draw pile")
	public void there_should_be_dominoes_left_in_the_draw_pile(Integer ExpectedSizeOfPile) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int num = 0;
		for(Domino domino: game.getAllDominos()){
			if(domino.getStatus() == Domino.DominoStatus.InPile){
				num++;
			}
		}
	
		assertEquals((int)ExpectedSizeOfPile,num);
	}
	
	@Then("the draw pile should consist of everything in {string} except the first {int} dominoes with their order preserved")
	public void the_draw_pile_should_consist_of_everything_in_except_the_first_dominoes_with_their_order_preserved(String orderedList,Integer numOfDominoes) {
		ArrayList<Integer> expectedList =getListOfIDs(orderedList);
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		List<Draft> drafts = game.getAllDrafts();
		int index = 4;
		if(game.getNumberOfPlayers()%2 == 1)
			index = 3;
		for(Draft draft: drafts){
			if(draft.getDraftStatus() == Draft.DraftStatus.Sorted) continue;

			if(game.getNumberOfPlayers()%2 == 1){
				for(int i = 0; i< 3; i++){
					assertEquals((int)draft.getIdSortedDomino(i).getId(),(int)(expectedList.get(index + i)));

				}
				index+=3;
			}else{
				for(int i = 0; i< 4; i++){
					assertEquals((int)draft.getIdSortedDomino(i).getId(),(int)(expectedList.get(index + i)));

				}
				index+=4;
			}
		}
		
		
		
	}
	
	
	
//	private void removeAllUser(Kingdomino kingdomino,Game game) {
//		int i= game.getNumberOfPlayers();
//		for(User u : kingdomino.getUsers()) {
//			i++;
//			u.set
//		}
//	}
	
	private void addDefaultUsersAndPlayersFour(Game game) {
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
	private void addDefaultUsersAndPlayersThree(Game game) {
		String[] userNames = { "User5", "User6", "User7"};
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
		}
	}
	private void addDefaultUsersAndPlayersTwo(Game game) {
		String[] userNames = { "User8", "User9"};
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

	private static void addUsersAndPlayers(String[] userNames, Game game) {
		for (int i = 0; i < userNames.length; i++) {
			User user = game.getKingdomino().addUser(userNames[i]);
			Player player = new Player(game);
			player.setUser(user);
			player.setColor(PlayerColor.values()[i]);
			Kingdom kingdom = new Kingdom(player);
			new Castle(0, 0, kingdom, player);
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
		String [] ids = aListOfIDs.split(", ");
		for(int i=0; i<ids.length;i++) {
			myList.add(Integer.parseInt(ids[i]));
		}

		return myList;
		
	}
}
