package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;

public class CalculatePropertyScoreController {

	/***
	 * author: Yuta Youness Bellali Controller method to calculate the score for a
	 * property!
	 * 
	 * @param properties
	 * @param player
	 */

	public static void calculatePropertyScore(List<Property> properties, Player player) {
		int score = 0;
		int Totalscore = 0;

		for (int i = 0; i < properties.size(); i++) {
			Property p = properties.get(i);
			score = p.getSize() * p.getCrowns();

			Totalscore += score;
		}

		player.setPropertyScore(Totalscore);

	}

}
