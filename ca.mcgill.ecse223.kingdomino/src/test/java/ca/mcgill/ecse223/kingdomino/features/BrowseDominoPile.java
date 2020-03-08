package ca.mcgill.ecse223.kingdomino.features;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.BroseDominoController;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BrowseDominoPile {
	/***
	 * Feature: Browse Domino Pile
	 * @author Sia Ham
	 * As a player, 
	 * I wish to browse the set of all dominoes in increasing order of 
	 *  numbers prior to playing the game so that I can adjust my strategy, 
	 *  view an individual domino or filter the dominoes by terrain type
	 */
	
	/*
	 * Background
	 */
	@Given("the program is started and ready for browsing dominoes")
	public void the_program_is_started_and_ready_for_browsing_dominoes() {
		Game game = new Game(48, KingdominoApplication.getKingdomino()); // program starts
		game.hasPlayers();
		game.hasAllDominos();
		game.hasAllDrafts();
		game.getAllDominos();
	}

	/*
	 * Scenario: Browse all the dominoes
	 */
	@When("I initiate the browsing of all dominoes")
	public void i_initiate_the_browsing_of_all_dominoes() {
		Game game = new Game(48, KingdominoApplication.getKingdomino()); // program starts
		BroseDominoController.addDefaultDominoes(game);
	}

	@Then("all the dominoes are listed in increasing order of identifiers")
	public void all_the_dominoes_are_listed_in_increasing_order_of_identifiers() {
		Game game = new Game(48, KingdominoApplication.getKingdomino());
		BroseDominoController.addDefaultDominoes(game);
	    List<Domino> dominos = game.getAllDominos();

	    

	    dominos.sort(Comparator.comparingDouble(Domino::getId())  );  
	}
	
	/*s
	 *   Scenario Outline: Select and observe an individual domino
    When I provide a domino ID <id>
    Then the listed domino has "<lefttile>" left terrain
    Then the listed domino has "<righttile>" right terrain
    Then the listed domino has <crowns> crowns

    Examples: 
      | id | lefttile | righttile | crowns |
      |  1 | wheat    | wheat     |      0 |
      | 28 | lake     | forest    |      1 |
      | 44 | grass    | swamp     |      2 |
      | 48 | wheat    | mountain  |      3 |
	 */
	@When("I provide a domino ID {int}")
	public void i_provide_a_domino_ID(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("the listed domino has {string} left terrain")
	public void the_listed_domino_has_left_terrain(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("the listed domino has {string} right terrain")
	public void the_listed_domino_has_right_terrain(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("the listed domino has {int} crowns")
	public void the_listed_domino_has_crowns(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	
	/*



	  Scenario Outline: Filter domino by terrain type
	    When I initiate the browsing of all dominoes of "<terrain>" terrain type
	    Then list of dominoes with IDs "<listofids>" should be shown

	    Examples: 
	      | terrain  | listofids                                                             |
	      | wheat    | 1,2,13,14,15,16,19,20,21,22,23,24,25,26,27,30,31,36,38,40,41,43,45,48 |
	      | mountain |                                                     23,40,45,46,47,48 |

		 */
		
	@When("I initiate the browsing of all dominoes of {string} terrain type")
	public void i_initiate_the_browsing_of_all_dominoes_of_terrain_type(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("list of dominoes with IDs {string} should be shown")
	public void list_of_dominoes_with_IDs_should_be_shown(String string) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	
	//private helper method 
	class SortbyIdComparator implements Comparator<Domino>  {
		public int compare(Domino domino1, Domino domino2) {
			{
				return domino1.getId()-domino2.getId();
			}
		}
	}
	

	/*
	public static void addDefaultDominoes(Game game) {
		BufferedReader reader;
		boolean hasCrown;
		String left, right, crown =null;
		TerrainType leftTile = null, rightTile =null;
		try {
			reader = new BufferedReader(new FileReader(
					"alldominoes.dat"));
			String line = reader.readLine();
			while (line != null) {
				hasCrown = line.contains("(");
				String[] s1= line.split(":");
				String id = s1[0];
				String[] s2 = s1[1].split("+");
				left = s2[0];
				if (!hasCrown) {
					right = s2[1];
					crown = "0";
				}
				else {
					String[] s3 = s2[1].split("(");
					right = s3[0];
					String[] s4 = s3[1].split(")");
					crown = s4[0];
				}
			
				if (left == "W") leftTile = TerrainType.WheatField;
				else if (left == "F") leftTile = TerrainType.Forest;
				else if (left == "L") leftTile = TerrainType.Lake;
				else if (left == "G") leftTile = TerrainType.Grass;
				else if (left == "S") leftTile = TerrainType.Swamp;
				else if (left == "M") leftTile = TerrainType.Mountain;
					
			
				if (right == "W") rightTile = TerrainType.WheatField;
				else if (right == "F") rightTile = TerrainType.Forest;
				else if (right == "L") rightTile = TerrainType.Lake;
				else if (right == "G") rightTile = TerrainType.Grass;
				else if (right == "S") rightTile = TerrainType.Swamp;
				else if (right == "M") rightTile = TerrainType.Mountain;
					
			//game.addAllDomino(new Domino(Integer.parseInt(id), leftTile, rightTile, Integer.parseInt(crown), game));
			game.hasAllDominos();
			game.addAllDomino(Integer.parseInt(id), leftTile, rightTile, Integer.parseInt(crown));
				// read next line
			line = reader.readLine();
		} 
			reader.close();
			}catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
}
