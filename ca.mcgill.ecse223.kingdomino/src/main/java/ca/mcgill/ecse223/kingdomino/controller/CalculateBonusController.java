package ca.mcgill.ecse223.kingdomino.controller;

import java.util.List;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class CalculateBonusController {
	/***
	 * author: Yuta Youness Bellali
	 * Controller class to calculate the bonus score!
	 * @param currentGame
	 * @param player
	 */

	public static void CalculateBonusScore(Game currentGame, Player player) {

		int bonusAmount = currentGame.getSelectedBonusOptions().size();

		/***
		 * code for: Harmony is selected A player will get 5 additional point if the
		 * player did not discard any dominos Simply check if a kingdom contains 13
		 * territories (all left tiles + castle)
		 */

		if (bonusAmount == 1 && currentGame.getSelectedBonusOption(0).getOptionName().equalsIgnoreCase("harmony")) {

			List<KingdomTerritory> allTerritories = player.getKingdom().getTerritories();

			if (allTerritories.size() == 13)
				player.setBonusScore(5);

		}

		/***
		 * Code for: Middle Kingdom The player will get an additional 10 points if its
		 * castle is placed in the middle of their territory
		 */

		if (bonusAmount == 1
				&& currentGame.getSelectedBonusOption(0).getOptionName().equalsIgnoreCase("middle kingdom")) {

			Kingdom playersKingdom = player.getKingdom();

			boolean middle = false;

			List<KingdomTerritory> territories = playersKingdom.getTerritories();

			middle = verifyMiddleCastle(territories);

			if (middle) {

				player.setBonusScore(10);

			}

		}

		/***
		 * Code for: If both Middle kingdom and harmony are selected
		 */
		if (bonusAmount == 2) {

			// if harmony is selected
			List<KingdomTerritory> allTerritories = player.getKingdom().getTerritories();
			;

			boolean middle = false;

			middle = verifyMiddleCastle(allTerritories);

			// calculate total Bonus Score
			if (middle && (allTerritories.size() != 13))
				player.setBonusScore(10);
			if (!middle && (allTerritories.size() == 13))
				player.setBonusScore(5);
			if (!middle && (allTerritories.size() != 13))
				player.setBonusScore(0);
			if (middle && (allTerritories.size() == 13))
				player.setBonusScore(15);
		}

	}

	/***
	 * Veryfying if castle is in the middle of Kingdom
	 * 
	 * @param territories
	 * @return true or false
	 */
	public static boolean verifyMiddleCastle(List<KingdomTerritory> territories) {
		boolean result = true;
		int x_max = -10;
		int x_min = 10;
		int y_max = -10;
		int y_min = 10;
		for (KingdomTerritory territory : territories) {

			int x_left = territory.getX();
			int y_left = territory.getY();
			x_max = Math.max(x_max, x_left);
			x_min = Math.min(x_min, x_left);
			y_max = Math.max(y_max, y_left);
			y_min = Math.min(y_min, y_left);

			if (territory instanceof DominoInKingdom) {
				int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left,
						((DominoInKingdom) territory).getDirection());
				int x_right = pos_right[0];
				int y_right = pos_right[1];
				result = result && (x_right <= 4 && x_right >= -4 && y_right <= 4 && y_right >= -4);

				x_max = Math.max(x_max, x_right);
				x_min = Math.min(x_min, x_right);
				y_max = Math.max(y_max, y_right);
				y_min = Math.min(y_min, y_right);
			}
		}

		return result && (y_max + y_min == 0) && (x_max + x_min == 0);
	}
}
