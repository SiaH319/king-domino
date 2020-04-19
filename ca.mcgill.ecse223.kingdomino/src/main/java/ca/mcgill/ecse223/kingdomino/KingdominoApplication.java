package ca.mcgill.ecse223.kingdomino;

import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;

public class KingdominoApplication {

	private static Kingdomino kingdomino;
	private static Gameplay statemachine;

	public static void main(String[] args) {
		System.out.println("Hello Kingdomino!");
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}
	
	public static Gameplay getStateMachine() {
		if (statemachine == null) {
			statemachine = new Gameplay();
		}
		return statemachine;
	}

	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}
	
	public static void setStateMachine(Gameplay gp) {
		statemachine = gp;
	}
}
