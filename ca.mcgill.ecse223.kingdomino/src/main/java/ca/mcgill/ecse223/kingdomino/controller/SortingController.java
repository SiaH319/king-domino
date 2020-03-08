package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;


import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

public class SortingController {
	public static void bubbleSortById(List<Domino> dominos) { 
		int n = dominos.size(); 
		for (int i = 0; i < n-1; i++) 
			for (int j = 0; j < n-i-1; j++) 
				if (dominos[j].getId() > dominos[j+1].getId()) 
				{ 
					// swap arr[j+1] and arr[i] 
							int temp = dominos[j]; 
							dominos[j] = dominos[j+1]; 
							dominos[j+1] = temp; 


				}
	}
}
