/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;

// line 3 "../../../../../Gameplay.ump"
public class Gameplay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay State Machines
  public enum Gamestatus { SettingUp, Initializing, InGame, EndofGame }
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino, ProceedingToNextPlayerOrNextTurn }
  public enum GamestatusInGame { Null, PreplacingDomino, SelectingNextDomino, ProceedingToNextPlayerOrNextTurn, OrderingNextDraft, RevealingNextDraft }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusInGame gamestatusInGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusInGame(GamestatusInGame.Null);
    setGamestatus(Gamestatus.SettingUp);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getGamestatusFullName()
  {
    String answer = gamestatus.toString();
    if (gamestatusInitializing != GamestatusInitializing.Null) { answer += "." + gamestatusInitializing.toString(); }
    if (gamestatusInGame != GamestatusInGame.Null) { answer += "." + gamestatusInGame.toString(); }
    return answer;
  }

  public Gamestatus getGamestatus()
  {
    return gamestatus;
  }

  public GamestatusInitializing getGamestatusInitializing()
  {
    return gamestatusInitializing;
  }

  public GamestatusInGame getGamestatusInGame()
  {
    return gamestatusInGame;
  }

  public boolean startNewGame(int numOfPlayers)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case SettingUp:
        // line 8 "../../../../../Gameplay.ump"
        initializeGame(numOfPlayers);setGameOptions();
        setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean loadGame()
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case SettingUp:
        if (isLoadedGameValid())
        {
        // line 9 "../../../../../Gameplay.ump"
          load();
          setGamestatus(Gamestatus.InGame);
          wasEventProcessed = true;
          break;
        }
        if (!(isLoadedGameValid()))
        {
        // line 10 "../../../../../Gameplay.ump"
          
          setGamestatus(Gamestatus.SettingUp);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean saveGame()
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case InGame:
        exitGamestatus();
        // line 51 "../../../../../Gameplay.ump"
        save();
        setGamestatus(Gamestatus.SettingUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean draftReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    switch (aGamestatusInitializing)
    {
      case CreatingFirstDraft:
        exitGamestatusInitializing();
        // line 15 "../../../../../Gameplay.ump"
        revealNextDraft(); generateInitialPlayerOrder();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean makeSelection(int id)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        exitGamestatusInitializing();
        // line 18 "../../../../../Gameplay.ump"
        currentPlayerSelectDomino(id);
        setGamestatusInitializing(GamestatusInitializing.ProceedingToNextPlayerOrNextTurn);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusInGame)
    {
      case SelectingNextDomino:
        exitGamestatusInGame();
        // line 38 "../../../../../Gameplay.ump"
        currentPlayerSelectDomino(id);
        setGamestatusInGame(GamestatusInGame.ProceedingToNextPlayerOrNextTurn);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean proceed()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInitializing)
    {
      case ProceedingToNextPlayerOrNextTurn:
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInitializing();
        // line 21 "../../../../../Gameplay.ump"
          
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 22 "../../../../../Gameplay.ump"
          
          setGamestatusInGame(GamestatusInGame.OrderingNextDraft);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusInGame)
    {
      case ProceedingToNextPlayerOrNextTurn:
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInGame();
        // line 41 "../../../../../Gameplay.ump"
          
          setGamestatusInGame(GamestatusInGame.PreplacingDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatusInGame();
        // line 42 "../../../../../Gameplay.ump"
          
          setGamestatusInGame(GamestatusInGame.OrderingNextDraft);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDomino(String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case PreplacingDomino:
        exitGamestatusInGame();
        // line 28 "../../../../../Gameplay.ump"
        moveCurrentDomino(dir);
        setGamestatusInGame(GamestatusInGame.PreplacingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean rotateDomino(int dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case PreplacingDomino:
        exitGamestatusInGame();
        // line 29 "../../../../../Gameplay.ump"
        rotateCurrentDomino(dir);
        setGamestatusInGame(GamestatusInGame.PreplacingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean place()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case PreplacingDomino:
        if (isCorrectlyPreplaced()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusInGame();
        // line 30 "../../../../../Gameplay.ump"
          placeDomino(); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCorrectlyPreplaced()&&isCurrentPlayerTheLastInTurn()&&isCurrentTurnTheLastInGame())
        {
          exitGamestatus();
        // line 32 "../../../../../Gameplay.ump"
          placeDomino();calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        if (isCorrectlyPreplaced()&&!(isCurrentPlayerTheLastInTurn())&&isCurrentTurnTheLastInGame())
        {
          exitGamestatusInGame();
        // line 33 "../../../../../Gameplay.ump"
          placeDomino();calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.ProceedingToNextPlayerOrNextTurn);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean discard()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case PreplacingDomino:
        if (impossibleTopPlaceDomino()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusInGame();
        // line 34 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (impossibleTopPlaceDomino()&&isCurrentTurnTheLastInGame())
        {
          exitGamestatus();
        // line 35 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean order()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case OrderingNextDraft:
        exitGamestatusInGame();
        // line 46 "../../../../../Gameplay.ump"
        orderNextDraft();
        setGamestatusInGame(GamestatusInGame.RevealingNextDraft);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean reveal()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case RevealingNextDraft:
        exitGamestatusInGame();
        // line 49 "../../../../../Gameplay.ump"
        revealNextDraft();
        setGamestatusInGame(GamestatusInGame.PreplacingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitGamestatus()
  {
    switch(gamestatus)
    {
      case Initializing:
        exitGamestatusInitializing();
        break;
      case InGame:
        exitGamestatusInGame();
        break;
    }
  }

  private void setGamestatus(Gamestatus aGamestatus)
  {
    gamestatus = aGamestatus;

    // entry actions and do activities
    switch(gamestatus)
    {
      case Initializing:
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft); }
        break;
      case InGame:
        if (gamestatusInGame == GamestatusInGame.Null) { setGamestatusInGame(GamestatusInGame.PreplacingDomino); }
        break;
      case EndofGame:
        // line 54 "../../../../../Gameplay.ump"
        calculateRanking();resolveTieBreak();
        break;
    }
  }

  private void exitGamestatusInitializing()
  {
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case SelectingFirstDomino:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case ProceedingToNextPlayerOrNextTurn:
        // line 23 "../../../../../Gameplay.ump"
        switchCurrentPlayer();
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
    }
  }

  private void setGamestatusInitializing(GamestatusInitializing aGamestatusInitializing)
  {
    gamestatusInitializing = aGamestatusInitializing;
    if (gamestatus != Gamestatus.Initializing && aGamestatusInitializing != GamestatusInitializing.Null) { setGamestatus(Gamestatus.Initializing); }

    // entry actions and do activities
    switch(gamestatusInitializing)
    {
      case CreatingFirstDraft:
        // line 14 "../../../../../Gameplay.ump"
        shuffleDominoPile(); createNextDraft(); orderNextDraft();
        break;
    }
  }

  private void exitGamestatusInGame()
  {
    switch(gamestatusInGame)
    {
      case PreplacingDomino:
        setGamestatusInGame(GamestatusInGame.Null);
        break;
      case SelectingNextDomino:
        setGamestatusInGame(GamestatusInGame.Null);
        break;
      case ProceedingToNextPlayerOrNextTurn:
        // line 43 "../../../../../Gameplay.ump"
        switchCurrentPlayer();
        setGamestatusInGame(GamestatusInGame.Null);
        break;
      case OrderingNextDraft:
        setGamestatusInGame(GamestatusInGame.Null);
        break;
      case RevealingNextDraft:
        setGamestatusInGame(GamestatusInGame.Null);
        break;
    }
  }

  private void setGamestatusInGame(GamestatusInGame aGamestatusInGame)
  {
    gamestatusInGame = aGamestatusInGame;
    if (gamestatus != Gamestatus.InGame && aGamestatusInGame != GamestatusInGame.Null) { setGamestatus(Gamestatus.InGame); }
  }

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 63 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
       	case "CreatingFirstDraft":
       	    gamestatus = Gamestatus.Initializing;
       	    gamestatusInitializing = GamestatusInitializing.CreatingFirstDraft;
       	// TODO add further cases here to set desired state
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
       	}
  }


  /**
   * Guards
   */
  // line 78 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    // TODO: implement this
        return false;
  }

  // line 83 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    // TODO: implement this
        return false;
  }

  // line 88 "../../../../../Gameplay.ump"
   public boolean isCorrectlyPreplaced(){
    // TODO: implement this
        return false;
  }

  // line 93 "../../../../../Gameplay.ump"
   public boolean isLoadedGameValid(){
    // TODO: implement this
        return false;
  }

  // line 98 "../../../../../Gameplay.ump"
   public boolean impossibleTopPlaceDomino(){
    // TODO: implement this
        return false;
  }


  /**
   * You may need to add more guards here
   * Actions
   */
  // line 108 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    GameplayController.acceptCallFromSM("shuffleDominoPile");
  }

  // line 112 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
	   GameplayController.acceptCallFromSM("generateInitialPlayerOrder");
  }

  // line 116 "../../../../../Gameplay.ump"
   public void createNextDraft(){
	   GameplayController.acceptCallFromSM("createNextDraft");
  }

  // line 120 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
	   GameplayController.acceptCallFromSM("orderNextDraft");
  }

  // line 124 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
	   GameplayController.acceptCallFromSM("revealNextDraft");
  }

  // line 128 "../../../../../Gameplay.ump"
   public void initializeGame(int numOfPlayers){
	   GameplayController.acceptInitializeGameCallFromSM(numOfPlayers);
  }

  // line 132 "../../../../../Gameplay.ump"
   public void setGameOptions(){
    // TODO: implement this
  }

  // line 136 "../../../../../Gameplay.ump"
   public void currentPlayerSelectDomino(int id){
	   GameplayController.acceptSelectDominoCallFromSM(id);
  }

  // line 140 "../../../../../Gameplay.ump"
   public void moveCurrentDomino(String dir){
    GameplayController.acceptMoveDominoCallFromSM(dir);
  }

  // line 144 "../../../../../Gameplay.ump"
   public void rotateCurrentDomino(int dir){
	   GameplayController.acceptRotateCurrentDomino(dir);
  }

  // line 148 "../../../../../Gameplay.ump"
   public void placeDomino(){
	   GameplayController.acceptCallFromSM("placeDomino");
  }

  // line 152 "../../../../../Gameplay.ump"
   public void discardDomino(){
	   GameplayController.acceptCallFromSM("discardDomino");
  }

  // line 156 "../../../../../Gameplay.ump"
   public void calculateCurrentPlayerScore(){
	   GameplayController.acceptCallFromSM("calculateCurrentPlayerScore");
  }

  // line 160 "../../../../../Gameplay.ump"
   public void calculateRanking(){
	   GameplayController.acceptCallFromSM("calculateRanking");
  }

  // line 164 "../../../../../Gameplay.ump"
   public void resolveTieBreak(){
	   GameplayController.acceptCallFromSM("resolveTieBreak");
  }

  // line 168 "../../../../../Gameplay.ump"
   public void switchCurrentPlayer(){
	   GameplayController.acceptCallFromSM("switchCurrentPlayer");
  }

  // line 172 "../../../../../Gameplay.ump"
   public void save(){
	   GameplayController.acceptCallFromSM("save");
  }

  // line 176 "../../../../../Gameplay.ump"
   public void load(){
	   GameplayController.acceptCallFromSM("load");
  }

}