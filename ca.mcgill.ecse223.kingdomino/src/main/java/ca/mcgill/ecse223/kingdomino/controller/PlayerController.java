package ca.mcgill.ecse223.kingdomino.controller;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;

public class PlayerController {
	
	
	



	public static Player getPlayerByColor (String color, Game game){
		
		int i = 0;
		while(true) {
			
			if (game.getPlayer(i).getColor().toString().equalsIgnoreCase(color)) return game.getPlayer(i);
			
			i++;
			
		}




	}



	
	
	

}
