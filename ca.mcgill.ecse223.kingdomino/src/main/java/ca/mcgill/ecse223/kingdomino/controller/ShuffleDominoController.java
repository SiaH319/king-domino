package ca.mcgill.ecse223.kingdomino.controller;
import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
	import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

	import ca.mcgill.ecse223.kingdomino.controller.CalculateBonusController;
	import ca.mcgill.ecse223.kingdomino.controller.CalculationController;
	import ca.mcgill.ecse223.kingdomino.controller.DisjointSet;
	import ca.mcgill.ecse223.kingdomino.controller.GameController;
	import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
	import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
	import ca.mcgill.ecse223.kingdomino.controller.Square;
	import ca.mcgill.ecse223.kingdomino.controller.VerificationController;
	import ca.mcgill.ecse223.kingdomino.model.BonusOption;
	import ca.mcgill.ecse223.kingdomino.model.Castle;
	import ca.mcgill.ecse223.kingdomino.model.Domino;
	import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
	import ca.mcgill.ecse223.kingdomino.model.Game;
	import ca.mcgill.ecse223.kingdomino.model.Kingdom;
	import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
	import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
	import ca.mcgill.ecse223.kingdomino.model.Player;
	import ca.mcgill.ecse223.kingdomino.model.TerrainType;
	import ca.mcgill.ecse223.kingdomino.model.User;
	import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
	import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
	import ca.mcgill.ecse223.kingdomino.model.Property;
	
public class ShuffleDominoController {
	
public static void shuffleDomino (List<Domino> domino, Game game){  

	//Draft draft1 = new Draft(DraftStatus.FaceDown, game);

	//game.setCurrentDraft(draft1);
	
	
	List <Domino> shuffledDominos = new ArrayList<Domino>();
	if (shuffledDominos.addAll(game.getAllDominos()))
	{

		Collections.shuffle(shuffledDominos);

	}
	for( int i=0; i<48; i++){
	game.getAllDominos().remove(i);
	}
	
	for(int i=0; i<48; i++) {
	Domino deepDominoPointer =  shuffledDominos.get(i);
	Domino deepDomino = new Domino (deepDominoPointer.getId(), deepDominoPointer.getLeftTile(), deepDominoPointer.getRightTile(), deepDominoPointer.getRightCrown(), deepDominoPointer.getGame());
	game.addAllDomino(deepDomino);
	}
	
	
	
	//Now that we have an array of Dominos, we need to setPrevious and setNext for the domino linked list.
	//No need to discard excess domino now. Discard themm AFTER shuffling all 48 dominos.
	Domino dominoToSet = game.getAllDomino(0);
	dominoToSet.setNextDomino(game.getAllDomino(1));
	
	game.setTopDominoInPile(dominoToSet);
	
	for(int i=1; i < game.getAllDominos().size()-1; i++) {
		
		dominoToSet.setPrevDomino(game.getAllDomino(i-1));
		dominoToSet.setNextDomino(game.getAllDomino(i+1));
		dominoToSet = dominoToSet.getNextDomino();
	}
	
	//set all the domino status to pile. We are later going to modify their status.
	
	for (int a = 0; a < game.getAllDominos().size(); a++) {                                           

		game.getAllDomino(a).setStatus(DominoStatus.InPile);

	}  
	
	//adding domino to game
	
	
		
		
	//}
	
	//We now need to set status of the dominos (in pile.. next draft..current draft etc)
	//The number of domino used in game will depend on the amount of player
	
	/*bonus
	if(game.getNumberOfPlayers() == 2) {
		
	Draft draft1 = new Draft(DraftStatus.FaceDown, game);
	game.setCurrentDraft(draft1);

	List <Domino> domino2 = new ArrayList<Domino>();
	
	
	
	for (int k = 0; k < 24; k++) {

			domino2.add(game.getAllDominos().get(k));
	
	}
	
	for (int z =0; z<3; z++) {
		
		draft1.addIdSortedDomino(domino2.get(z));
		domino2.get(z).setStatus(DominoStatus.InCurrentDraft); 
	}


				
			}
	
	
	if(game.getNumberOfPlayers() == 3) {
		
		Draft draft1 = new Draft(DraftStatus.FaceDown, game);
		game.setCurrentDraft(draft1);

		List <Domino> domino3 = new ArrayList<Domino>();
		
			
		
		for (int l = 0; l < 36; l++) {

				domino3.add(game.getAllDominos().get(l));
		
		}
		
		for (int w =0; w<4; w++) {
			
			draft1.addIdSortedDomino(domino3.get(w));
			domino3.get(w).setStatus(DominoStatus.InCurrentDraft); 
		}
		
	
					
				}
	*/
	
	{
		
		Draft draft1 = new Draft(DraftStatus.FaceDown, game);
		game.setCurrentDraft(draft1);

		
		for (int y =0; y<4; y++) {
			
			draft1.addIdSortedDomino(game.getAllDomino(y));
			game.getAllDomino(y).setStatus(DominoStatus.InCurrentDraft); 
		}
		
	}
	}
	


private static Domino getdominoByID(int id) {
    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    for (Domino domino : game.getAllDominos()) {
        if (domino.getId() == id) {
            return domino;
        }
    }
    throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
    }

public static void fixedArrangement (String string,Game game) {
string = string.replaceAll("\\s", "");
List<Integer> list = Stream.of(string.split(",")).map(Integer::parseInt).collect(Collectors.toList());
int numPlayer = game.getNumberOfPlayers();
	
//List<Domino> domino = game.getAllDominos();


{
	
Draft draft1 = new Draft(DraftStatus.FaceDown, game);
	game.setCurrentDraft(draft1);
		
for(int c = 0; c < 4; c++) {
game.addAllDomino((getdominoByID(list.get(c).intValue())));
game.getAllDomino(c).setStatus(DominoStatus.InCurrentDraft);
		}	
			
	
for(int c = 4; c < list.size(); c++) {
game.addAllDomino((getdominoByID(list.get(c).intValue())));
game.getAllDomino(c).setStatus(DominoStatus.InPile);
}	
	
}
/*
if(numPlayer == 3) {
	for(int c = 3; c < list.size(); c++) {
		domino.add(getdominoByID(list.get(c).intValue()));
		domino.get(c).setStatus(DominoStatus.InPile);

		}	
	Draft draft1 = new Draft(DraftStatus.FaceDown, game);
	game.setCurrentDraft(draft1);
	
	for(int f = 0; f<3; f++) {
		domino.get(3).setStatus(DominoStatus.InCurrentDraft);
		draft1.addIdSortedDomino(domino.get(3));

}
}
if(numPlayer == 2) {
	for(int c = 2; c < list.size(); c++) {
		domino.add(getdominoByID(list.get(c).intValue()));
		domino.get(c).setStatus(DominoStatus.InPile);

		}	
	Draft draft1 = new Draft(DraftStatus.FaceDown, game);
	game.setCurrentDraft(draft1);
	
	for(int f = 0; f<2; f++) {
		domino.get(f).setStatus(DominoStatus.InCurrentDraft);
		draft1.addIdSortedDomino(domino.get(f));
		
	}
}
*/


}
}
