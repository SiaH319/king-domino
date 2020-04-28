package ca.mcgill.ecse223.kingdomino.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.InitializationController.InvalidInputException;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Gameplay;
import ca.mcgill.ecse223.kingdomino.model.Gameplay.Gamestatus;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.User;

public class GameplayController {
	public static Gameplay statemachine;
	public static Kingdomino kingdomino;
	private static ArrayList<Player> Orders = new ArrayList<Player>();
	private static String error;

/////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////Helper Methods/////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void initStatemachine() {
		statemachine = KingdominoApplication.getStateMachine();
		kingdomino = KingdominoApplication.getKingdomino();
	}

	public static void setStateMachineState(String stateName) {
		statemachine.setGamestatus(stateName);
	}
	
	

/////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////Trigger Relevant Events In SM//////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * A wrppaer method that triggers actions without parameters in state machine
	 * @param methodName, method name in string
	 */
	public static void triggerEventsInSM(String methodName) {
		initStatemachine();
		switch (methodName) {
		case "proceed":
			statemachine.proceed();
			break;
		case "place":
			break;
		case "order":
			statemachine.order();
			break;
		case "draftReady":
			statemachine.draftReady();
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid trigger event: " + methodName);
		}


	}

	/**
	 * Trigger discard action in statemachine
	 * @author Cecilia Jiang
	 */
	public static void triggerDiscardDominoInSM() {
		statemachine.discard();
	}
	
	/**
	 * Trigger place action in statemachine
	 * @author Violet
	 */
	public static void triggerPlaceDominoInSM() {
		statemachine.place();
	}
	
	public static void triggerSaveInSM(String filename) {
		statemachine.save(filename);
	}
	
	/**
	 * Trigger createnewuser action in statemachine
	 * @author Cecilia Jiang
	 */
	public static boolean triggerCreateNewUser(String name) {
		return statemachine.createNewUser(name);
	}

	/**
	 * Tigger startANewGame action in statemachine
	 * @autor Cecilia Jiang
	 * @param numOfPlayers,     number of players for the new game
	 * @param mkActivated,      true if middleKingdom bonus is activated
	 * @param harmonyActivated, true if harmony bonus is activated
	 */
	public static void triggerStartNewGameInSM(int numOfPlayers, boolean mkActivated, boolean harmonyActivated,
			String[] userNames) {
		statemachine.startNewGame(numOfPlayers, mkActivated, harmonyActivated, userNames);
	}

	/**
	 * Given an id of a domino, the current player will try to select it.
	 * @author Mohamad
	 * @param id
	 */
	public static void triggerMakeSelectionInSM(int id) {
		statemachine.makeSelection(id);
	}

	/**
	 * Trigger loadGame action in state machine
	 * @author Cecilia Jiang
	 * @param filename, fileName for load file
	 */
	public static void triggerLoadGameInSM(String filename) {
		statemachine.loadGame(filename);
	}

	/**
	 * Trigger moveDomino action in state machine
	 * @author Cecilia Jiang
	 * @param dir, direction of movement
	 */
	public static void triggerMoveDominoInSM(String dir) {
		statemachine.moveCurrentDomino(dir);
	}

	/**
	 * Trigger RotateDomino action in state machine
	 * @author Cecilia Jiang
	 * @param dir, 1 clockwise or 0 counterwise
	 */
	public static void triggerRotateDominoInSM(int dir) {
		statemachine.rotateCurrentDomino(dir);
	}

	// Guards
	/**
	 * 
	 * Checks is the current player is last in turn If there is a next draft, see
	 * how many selection have been made
	 * 
	 * If there is no next draft, compare selected domino Id to the ones in the
	 * current draft
	 * 
	 * @author Mohamad
	 * @return true if curren tplayer is last, false otherwise
	 */
	public static boolean isCurrentPlayerTheLastInTurn() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();


		int rank = game.getNextPlayer().getCurrentRanking();
		if(game.getNumberOfPlayers() %2 == 0)
			return rank == 3;
		return rank == 2;
	}

	/**
	 * Guard In StateMachine. 
	 * Checks if the current turn is the last turn in game.
	 * @author Mohamad
	 * @return true if next draft doesn't exist
	 */
	public static boolean isCurrentTurnTheLastInGame() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		return (game.getNextDraft() == null);

	}

	
	/**
	 * Guard In StateMachine. Checks if the domino status of the current player's
	 * domino selection's domino is CorrectlyPreplaced
	 * @author Mohamad
	 * @return true if it's CorrectlyPreplaced, false otherwise
	 */
	public static boolean isCorrectlyPreplaced() {
		
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPl = game.getNextPlayer();
		Domino currentlyChosedDomino = currentPl.getDominoSelection().getDomino();
		DominoStatus curDominoStatus = currentlyChosedDomino.getStatus();
		if(curDominoStatus!=DominoStatus.CorrectlyPreplaced) GameplayController.setError("Not correctly preplaced");
		return curDominoStatus == DominoStatus.CorrectlyPreplaced;
	}

	public static boolean isSelectionValid(int id){
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Domino domino = getdominoByID(id);
		return !domino.hasDominoSelection();

    }
	
	/**
	 * Guard In StateMachine. 
	 * Checks if all dominoes in current draft have
	 * selections
	 * @author Mohamad
	 * @return true if they all have, false otherwise.
	 */
	public static boolean areAllDominoesInCurrentDraftSelected() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		int expectedNumberOfDominoesSelected = game.getCurrentDraft().getIdSortedDominos().size();
		int actualNumberOfDominoesSelected = 0;
		for (Domino d : game.getCurrentDraft().getIdSortedDominos()) {
			if (d.hasDominoSelection()) {
				actualNumberOfDominoesSelected++;
			}
		}

		return ((expectedNumberOfDominoesSelected - actualNumberOfDominoesSelected) == 0);
	}

	
	/**
	 * Guard In StateMachine.
	 * Check if loading a string filename is successful
	 * @author Cecilia Jiang
	 * @param filename, input filename
	 * @return true if succeeded
	 */
	public static boolean isLoadedGameValid(String filename) {
		try {
			return SaveLoadGameController.loadGame(filename);
		} catch (Exception e) {
			return false;
		}

	}

	
	/**
	 * Guard method in sate machine. Check if it's impossible to place the currently
	 * selected domino
	 * @author Mohamad
	 * @return true if can't place the domino anywhere, false if it can.
	 */
	public static boolean impossibleToPlaceDomino() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPl = game.getPlayer(0);
		Kingdom kingdom = currentPl.getKingdom();
		Castle castle = getCastle(kingdom);
		Square[] grid = GameController.getGrid( getStringFromPlayerColor(currentPl));
		DominoInKingdom dominoInKingdom = (DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size() - 1);
		ArrayList<DominoInKingdom.DirectionKind> directions = new ArrayList<DominoInKingdom.DirectionKind>();
		directions.add(DominoInKingdom.DirectionKind.Down);
		directions.add(DominoInKingdom.DirectionKind.Left);
		directions.add(DominoInKingdom.DirectionKind.Up);
		directions.add(DominoInKingdom.DirectionKind.Right);
		for (int x = -4; x < 5; x++) {
			for (int y = -4; y < 5; y++) {
				for (DominoInKingdom.DirectionKind dir : directions) {
					dominoInKingdom.setDirection(dir);
					dominoInKingdom.setX(x);
					dominoInKingdom.setY(y);

					if ((VerificationController.verifyGridSize(currentPl.getKingdom().getTerritories()))
							&& (VerificationController.verifyNoOverlapping(castle, grid, dominoInKingdom))
							&& ((VerificationController.verifyCastleAdjacency(castle, dominoInKingdom))
									|| (VerificationController.verifyNeighborAdjacency(castle, grid,
											dominoInKingdom)))) {
						System.out.println("Found a place where we can place the domino with x= " + x + " y=" + y
								+ "direction =" + dir);
						System.out.println(
								"A this x,y there is :" + grid[Square.convertPositionToInt(x, y)].getTerrain());
						return false; // as we have found a place where we can place the domino
					}
				}
			}
		}
		// couldn't place the domino anywhere.
		dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
		return true;
	}

	// Accept Action Calls
	public static void acceptCallFromSM(String methodName) {
		switch (methodName) {
		case "shuffleDominoPile":
			Game game = kingdomino.getCurrentGame();
			List<Domino> dominoes = game.getAllDominos();
			try {
				ShuffleDominoesController.shuffleDomino(dominoes,game);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "createNextDraft":
			DraftController.createNewDraftIsInitiated();
			break;
		case "generateInitialPlayerOrder":
			generateInitialPlayerOrder();
			break;
		case "orderNextDraft":
			DraftController.orderNewDraftInitiated();
			break;
		case "revealNextDraft":
			DraftController.revealDominoesInitiated();
			break;
		case "placeDomino":
			DominoController.placeDomino(null, 0);
			break;
		case "calculateCurrentPlayerScore":
			CalculationController.calculateCurrentPlayerScore();
			break;
		case "calculateRanking":
			calculateRanking();
			break;
		case "switchCurrentPlayer":
			switchCurrentPlayerInitiated();
			break;
		default:
			throw new java.lang.IllegalArgumentException("Invalid trigger event: " + methodName);
		}

	}

	/**
	 * Accept calculateRanking call from sm
	 */
	public static void calculateRanking(){
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean noTie = true;
		for(Player p1 : game.getPlayers()) {
			for(Player p2:game.getPlayers()) {
				if(p1.getColor()!=p2.getColor()) {
					if((p2.getBonusScore()+p2.getPropertyScore())==(p1.getBonusScore()+p1.getPropertyScore())) {// if we catch any pair of different
						//players with the same score then there is a tie
						noTie=false;
					}
				}

			}
		}
		ArrayList<Integer> scoreList = new ArrayList<>();
		for(Player player: game.getPlayers()) {
			scoreList.add(player.getTotalScore());
		}
		CalculationController.calculateRanking(noTie, scoreList);
	}
	
	/**
	 * Accept saveGame call from SM
	 * @author Cecilia Jiang
	 * @param filename, filename as string
	 * @return true if succeeded, false otherwise
	 */
	public static boolean acceptSaveGameCallFromSM(String filename) {
		try {
			return SaveLoadGameController.saveGame(filename);
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Accept create user call from state machine
	 * @author Cecilia Jiang
	 * @param name, user's name
	 * @return String, errorMessage if exception encountered, "" otherwise
	 */
	public static String acceptCreateUserCallFromSM(String name){
		try {
			InitializationController.initializeUser(name);
			return "";
		} catch (InvalidInputException e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Accept initializeGame call from state machine
	 * Given the number of players, initialise the game accordingly.
	 * @author Cecilia Jiang, Mohammad Dimassi
	 * @param numOfPlayer, number of player for the new game
	 */
	public static void acceptInitializeGameCallFromSM(int numOfPlayer, String[] userNames) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game;
		if (numOfPlayer == 4) {
			game = new Game(12, kingdomino);
			game.setNumberOfPlayers(4);
			game.getKingdomino().setCurrentGame(game);
			kingdomino.setCurrentGame(game);
			// Populate game
			addUsersAndPlayers(userNames,game);
			createAllDominoes(game);
			for(int k = 0; k < numOfPlayer; k++) {
				String playerName = (game.getPlayer(k).getColor().toString());
				GameController.setGrid(playerName, new Square[81]);
				GameController.setSet(playerName, new DisjointSet(81));
				Square[] grid = GameController.getGrid(playerName);
				for (int i = 4; i >= -4; i--)
					for (int j = -4; j <= 4; j++)
						grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
			}
		}

		if (numOfPlayer == 3) {
			game = new Game(12, kingdomino);
			game.setNumberOfPlayers(3);
			game.getKingdomino().setCurrentGame(game);
			addUsersAndPlayers(userNames,game);
			createAllDominoes(game);
			for(int k = 0; k < numOfPlayer; k++) {
				String playerName = (game.getPlayer(k).getColor().toString());
				GameController.setGrid(playerName, new Square[81]);
				GameController.setSet(playerName, new DisjointSet(81));
				Square[] grid = GameController.getGrid(playerName);
				for (int i = 4; i >= -4; i--)
					for (int j = -4; j <= 4; j++)
						grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
			}
			for(int i = 0 ; i<12;i++) {
				int size = game.getAllDominos().size();
				int index = (int)(Math.random() * (size));
				Domino domino = game.getAllDomino(index);
				while(domino.getStatus()== DominoStatus.Excluded) {
					index = (int)(Math.random() * (size));
					domino = game.getAllDomino(index);
				}
				domino.setStatus(DominoStatus.Excluded);
				
			}

		}
		if (numOfPlayer == 2) {
			game = new Game(6, kingdomino);
			game.setNumberOfPlayers(2);
			game.getKingdomino().setCurrentGame(game);
			addUsersAndPlayers(userNames,game);
			createAllDominoes(game);
			for(int k = 0 ;k < 2; k++) {
				String playerName = (game.getPlayer(2*k).getColor().toString());
				GameController.setGrid(playerName, new Square[81]);
				GameController.setSet(playerName, new DisjointSet(81));
				Square[] grid = GameController.getGrid(playerName);
				for (int i = 4; i >= -4; i--)
					for (int j = -4; j <= 4; j++)
						grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
			}
			
			for(int i = 0 ; i < 24; i++) {
				int size = game.getAllDominos().size();
				int index = (int)(Math.random() * (size));
				Domino domino = game.getAllDomino(index);
				while(domino.getStatus()==DominoStatus.Excluded) {
					index = (int)(Math.random() * (size));
					domino = game.getAllDomino(index);
				}
				domino.setStatus(DominoStatus.Excluded);
				
			}
		}
	}
	
	/**
	 * Accept set bonus option call from state machine
	 * @author Cecilia Jiang
	 * @param mkActivated, middle kingdom activated or not,
	 * @param harmonyActivated, harmony activated or not
	 */
	public static void acceptSetBonusOptionFromSM(boolean mkActivated, boolean harmonyActivated) {
		GameController.setBonusOptionForCurrentGame(mkActivated,harmonyActivated);
	}

	/**
	 * Accept place domino call from state machine
	 * @author Cecilia Jiang
	 */
	public static String acceptPlaceDominoFromSM() {
		Player currentPlayer = kingdomino.getCurrentGame().getNextPlayer();
		Domino domino = currentPlayer.getDominoSelection().getDomino();
		try{
			DominoController.placeDomino(currentPlayer, domino.getId());
			return "";
		}catch(Exception e){
			System.out.println("Message"+e.getMessage());
			return e.getMessage();
		}
	}

	public static void acceptDiscardDominoFromSM() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player player = game.getNextPlayer();
		List<KingdomTerritory> list = player.getKingdom().getTerritories();
		DominoInKingdom dominoInKingdom = (DominoInKingdom) list.get(list.size() - 1);
		DominoController.attemptDiscardSelectedDomino(dominoInKingdom);
	}

	/**
	 * Method that switches the current player to find what players'turn it is.
	 * @author Mohamad, Cecilia Jiang
	 */
	private static void switchCurrentPlayerInitiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		boolean zeroCondition = game.getAllDrafts().size() == 2 && game.getNextDraft().getDraftStatus() == Draft.DraftStatus.Sorted;
//		for(Domino domino: game.getCurrentDraft().getIdSortedDominos()){
//			zeroCondition = zeroCondition || (domino.getDominoSelection()==null);
//		}
		if(zeroCondition) {
			int curPlayerRanking = game.getNextPlayer().getCurrentRanking();
			if(!isCurrentPlayerTheLastInTurn()) {
				System.out.println("eNTERED ZERO CONDITION IN SWITCH PLAYER");
				for(Player player: game.getPlayers()) {
					if(player.getDominoSelection()==null&&player.getCurrentRanking()-1 == curPlayerRanking) {
						game.setNextPlayer(player);
						return;
					}
				}

			}
			return;
		}

		boolean firstCondition = true;
		for(Domino domino: game.getNextDraft().getIdSortedDominos()){
			firstCondition = firstCondition &&(domino.getDominoSelection()==null);
		}
		if(firstCondition){
			System.out.println("eNTERED FIRST CONDITION IN SWITCH PLAYER");
			for(Player player: game.getPlayers()){
				if(player.getCurrentRanking() == 0){
					game.setNextPlayer(player);
					break;
				}
			}
			return;
		}else{
			Player p = game.getNextPlayer();
			int mod = (game.getNumberOfPlayers()%2==0)? 4 : 3;
			System.out.println("eNTERED SECOND CONDITION IN SWITCH PLAYER");
			int rank = (p.getCurrentRanking()+1) % mod;
			for(Player player: game.getPlayers()){
				if(player.getCurrentRanking() == rank)
					game.setNextPlayer(player);
			}
		}


	}

	/**
	 * Given an order of player set it
	 * @author Mohamad
	 */
	public static void setOrderOfPlayer(Player[] order) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (int i = 0; i < game.getPlayers().size(); i++) {
			game.addOrMovePlayerAt(order[i], i);
		}
	}

	public static boolean specificPlayerChosesDomino(Player player, Draft draft, int dominoId) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		return DominoController.chooseNextDomino(game, dominoId);
	}

	/**
	 * Randomly generate the order of players at the first turn of the game
	 * @author Cecilia Jiang
	 */
	private static void generateInitialPlayerOrder() {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game currentGame = kd.getCurrentGame();
		int playerNumber = currentGame.getNumberOfPlayers();
		List<Integer> ranks = new ArrayList<>();
		if(playerNumber % 2 ==0) {
			ranks.add(0);
			ranks.add(1);
			ranks.add(2);
			ranks.add(3);
		}else {
			ranks.add(0);
			ranks.add(1);
			ranks.add(2);
		}
		Collections.shuffle(ranks);
		List<Player> players = currentGame.getPlayers();
		for(Player player: players) {
			player.setCurrentRanking(ranks.remove(0));
		}
		
		int i = 0;
		int indexMin = 0;
		for(Player player: players) {
			if(player.getCurrentRanking() == 0) {
				indexMin = i;
			}
			i++;
		}
		currentGame.setNextPlayer(currentGame.getPlayer(indexMin));
	}

	/**
	 * Accept selectDominoCall from state machine
	 * @author Cecilia Jiang
	 * @param dominoId, chosen domino id
	 */
	public static void acceptSelectDominoCallFromSM(int dominoId) {
		Kingdomino kingdomino = KingdominoApplication.getKingdomino();
		Game game = kingdomino.getCurrentGame();
		DominoController.chooseNextDomino(game, dominoId);
	}
	

	/**
	 * Accept moveDominoCall from state machine
	 * @author Cecilia Jiang
	 * @param dir, direction in String
	 */
	public static void acceptMoveDominoCallFromSM(String dir) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		Player p = game.getNextPlayer();
		Domino domino = p.getDominoSelection().getDomino();
		Kingdom kingdom;
		if(game.getNumberOfPlayers() == 2 && (p.getColor() == PlayerColor.Green|| p.getColor() == PlayerColor.Pink)){
			System.out.println("Player: "+p.getColor().toString()+"Index: "+ game.indexOfPlayer(p)+"Accepted move domino action");
			kingdom = game.getPlayer(game.indexOfPlayer(p)-1).getKingdom();
			if(kingdom == null){
				System.out.println("Kingdom is null");
			}
		}else{
			kingdom = p.getKingdom();
		}
		DominoInKingdom dik = KingdomController.getDominoInKingdomByDominoId(domino.getId(), kingdom);
        if(dik == null){
            dik = new DominoInKingdom(0,0,kingdom,domino);
        }
		DominoController.moveCurrentDomino(p, p.getDominoSelection().getDomino().getId(), dir);
	}

	
	/**
	 * Accept rotateCurrentDominoCall from state machine
	 * @author Cecilia Jiang
	 * @param dir,rotationDir 1 for clockwise, -1 for anticlockwise
	 */
	public static void acceptRotateCurrentDomino(int dir) {
		Kingdomino kd = KingdominoApplication.getKingdomino();
		Game game = kd.getCurrentGame();
		Player p = game.getNextPlayer();
		Castle castle = KingdomController.getCastle(p.getKingdom());
		Square[] grid = GameController.getGrid(getStringFromPlayerColor(p));
		Domino domino = p.getDominoSelection().getDomino();
		DominoInKingdom dik = KingdomController.getDominoInKingdomByDominoId(domino.getId(), p.getKingdom());
		if(dik == null){
			return;
		}
		DominoController.rotateExistingDomino(castle, grid, p.getKingdom().getTerritories(), dik, dir);
	}

	
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////
	private static Castle getCastle(Kingdom kingdom) {
		for (KingdomTerritory territory : kingdom.getTerritories()) {
			if (territory instanceof Castle)
				return (Castle) territory;
		}
		return null;
	}

	private static void addUsersAndPlayers(String[] userNames, Game game) {
		if(userNames.length == 3 || userNames.length == 4) {
			for (int i = 0; i < userNames.length; i++) {
				List<User> users = game.getKingdomino().getUsers();
				User curUser = null;
				for(User user: users) {
					if(user.getName().equals(userNames[i])) {
						curUser = user;
						break;
					}
				}

					Player player = new Player(game);
					player.setColor(PlayerColor.values()[i]);
					Kingdom kingdom = new Kingdom(player);
				new Castle(0, 0, kingdom, player);
				String player0Name =  getStringFromPlayerColor(player);
				GameController.setGrid(player0Name, new Square[81]);
				GameController.setSet(player0Name, new DisjointSet(81));
				Square[] grid = GameController.getGrid(player0Name);
				for (int i2 = 4; i2 >= -4; i2--)
					for (int j2 = -4; j2 <= 4; j2++)
						grid[Square.convertPositionToInt(i2, j2)] = new Square(i2, j2);
				if(curUser != null) {
					player.setUser(curUser);
				}
			}
		} else {
			for (int i = 0; i < 2; i++) {
				List<User> users = game.getKingdomino().getUsers();
				User curUser = null;
				for(User user: users) {
					if(user.getName().equals(userNames[i])) {
						curUser = user;
						break;
					}
				}

					Player player1 = new Player(game);
					player1.setColor(PlayerColor.values()[2*i]);
					Kingdom kingdom = new Kingdom(player1);		//Create kingdom for blue and yellow
					new  Castle(0, 0, kingdom, player1);
					Player player2 = new Player(game);
					player2.setColor(PlayerColor.values()[2*i+1]);


					String player0Name =  getStringFromPlayerColor(player1);
					String player1Name = getStringFromPlayerColor(player2);

					GameController.setSet(player0Name, new DisjointSet(81));
					Square[] grid = new Square[81];
					for (int i2 = 4; i2>= -4; i2--)
						for (int j2 = -4; j2 <= 4; j2++)
							grid[Square.convertPositionToInt(i2, j2)] = new Square(i2, j2);
					GameController.setGrid(player0Name,grid);
					GameController.setGrid(player1Name,grid);
					GameController.setSet(player1Name,GameController.getSet(player0Name));

				if(curUser != null) {
					player1.setUser(curUser);
					player2.setUser(curUser);
				}
				
			}
		}
		
	}


	private static void createAllDominoes(Game game) {
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

	private static TerrainType getTerrainType(String terrain) {
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

	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

	private ArrayList<Integer> getListOfIDs(String aListOfIDs) {
		boolean beforeIsDigit = false;
		ArrayList<Integer> myList = new ArrayList<Integer>();
		String[] ids = aListOfIDs.split(", ");
		for (int i = 0; i < ids.length; i++) {
			myList.add(Integer.parseInt(ids[i]));
		}

		return myList;

	}

	private static String getStringFromPlayerColor(Player p){
		String result = "";
		switch(p.getColor()){
			case Blue:
				result = "Blue";
				break;
			case Green:
				result = "Green";
				break;
			case Pink:
				result = "Pink";
				break;
			case Yellow:
				result = "Yellow";
				break;
		}
		return result;
	}
	public static String getError() {
		return error;
	}

	public static void setError(String error) {
		GameplayController.error = error;
	}
}
