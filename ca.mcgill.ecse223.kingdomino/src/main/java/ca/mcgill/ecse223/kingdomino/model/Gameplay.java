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
  public enum GamestatusInitializing { Null, SelectingFirstDomino, ProceedingToNextPlayerOrNextTurn }
  public enum GamestatusInGame { Null, PreplacingDomino, SelectingNextDomino, ProceedingToNextPlayerOrNextTurn, OrderingNextDraft, RevealingNextDraft }
  public enum GamestatusEndofGame { Null, CalculatingScore, ShowingRankings }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusInGame gamestatusInGame;
  private GamestatusEndofGame gamestatusEndofGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusInGame(GamestatusInGame.Null);
    setGamestatusEndofGame(GamestatusEndofGame.Null);
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
    if (gamestatusEndofGame != GamestatusEndofGame.Null) { answer += "." + gamestatusEndofGame.toString(); }
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

  public GamestatusEndofGame getGamestatusEndofGame()
  {
    return gamestatusEndofGame;
  }

  public boolean startNewGame(int numOfPlayers)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case SettingUp:
        // line 8 "../../../../../Gameplay.ump"
        initializeGame(numOfPlayers);setGameOptions();shuffleDominoPile(); createNextDraft(); orderNextDraft();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
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

  public boolean makeSelection(int id)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        exitGamestatusInitializing();
        // line 15 "../../../../../Gameplay.ump"
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
        // line 37 "../../../../../Gameplay.ump"
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
        // line 18 "../../../../../Gameplay.ump"
          
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 19 "../../../../../Gameplay.ump"
          
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
          switchCurrentPlayer();
          setGamestatusInGame(GamestatusInGame.PreplacingDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatusInGame();
        // line 42 "../../../../../Gameplay.ump"
          switchDraft();
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
        // line 25 "../../../../../Gameplay.ump"
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
        // line 26 "../../../../../Gameplay.ump"
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
        // line 27 "../../../../../Gameplay.ump"
          placeDomino(); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCorrectlyPreplaced()&&isCurrentPlayerTheLastInTurn()&&isCurrentTurnTheLastInGame())
        {
          exitGamestatus();
        // line 29 "../../../../../Gameplay.ump"
          placeDomino();calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        if (isCorrectlyPreplaced()&&!(isCurrentPlayerTheLastInTurn())&&isCurrentTurnTheLastInGame())
        {
          exitGamestatusInGame();
        // line 30 "../../../../../Gameplay.ump"
          placeDomino();calculateCurrentPlayerScore();switchCurrentPlayer();
          setGamestatusInGame(GamestatusInGame.PreplacingDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean discard(DominoInKingdom dominoInKingdom)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case PreplacingDomino:
        if (impossibleToPlaceDomino()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusInGame();
        // line 31 "../../../../../Gameplay.ump"
          discardDomino(dominoInKingdom); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (impossibleToPlaceDomino()&&isCurrentTurnTheLastInGame()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 32 "../../../../../Gameplay.ump"
          discardDomino(dominoInKingdom); calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        if (impossibleToPlaceDomino()&&isCurrentTurnTheLastInGame()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInGame();
        // line 33 "../../../../../Gameplay.ump"
          discardDomino(dominoInKingdom); calculateCurrentPlayerScore();switchCurrentPlayer();
          setGamestatusInGame(GamestatusInGame.PreplacingDomino);
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
        if (areAllDominoesInCurrentDraftSelected())
        {
          exitGamestatusInGame();
        // line 46 "../../../../../Gameplay.ump"
          orderNextDraft();
          setGamestatusInGame(GamestatusInGame.RevealingNextDraft);
          wasEventProcessed = true;
          break;
        }
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
        revealNextDraft();switchCurrentPlayer();
        setGamestatusInGame(GamestatusInGame.PreplacingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean showRankings()
  {
    boolean wasEventProcessed = false;
    
    GamestatusEndofGame aGamestatusEndofGame = gamestatusEndofGame;
    switch (aGamestatusEndofGame)
    {
      case CalculatingScore:
        exitGamestatusEndofGame();
        // line 56 "../../../../../Gameplay.ump"
        
        setGamestatusEndofGame(GamestatusEndofGame.ShowingRankings);
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
      case EndofGame:
        exitGamestatusEndofGame();
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
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino); }
        break;
      case InGame:
        if (gamestatusInGame == GamestatusInGame.Null) { setGamestatusInGame(GamestatusInGame.PreplacingDomino); }
        break;
      case EndofGame:
        if (gamestatusEndofGame == GamestatusEndofGame.Null) { setGamestatusEndofGame(GamestatusEndofGame.CalculatingScore); }
        break;
    }
  }

  private void exitGamestatusInitializing()
  {
    switch(gamestatusInitializing)
    {
      case SelectingFirstDomino:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case ProceedingToNextPlayerOrNextTurn:
        // line 20 "../../../../../Gameplay.ump"
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
      case SelectingFirstDomino:
        // line 14 "../../../../../Gameplay.ump"
        revealNextDraft(); generateInitialPlayerOrder();switchDraft();createNextDraft();
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

  private void exitGamestatusEndofGame()
  {
    switch(gamestatusEndofGame)
    {
      case CalculatingScore:
        setGamestatusEndofGame(GamestatusEndofGame.Null);
        break;
      case ShowingRankings:
        setGamestatusEndofGame(GamestatusEndofGame.Null);
        break;
    }
  }

  private void setGamestatusEndofGame(GamestatusEndofGame aGamestatusEndofGame)
  {
    gamestatusEndofGame = aGamestatusEndofGame;
    if (gamestatus != Gamestatus.EndofGame && aGamestatusEndofGame != GamestatusEndofGame.Null) { setGamestatus(Gamestatus.EndofGame); }

    // entry actions and do activities
    switch(gamestatusEndofGame)
    {
      case CalculatingScore:
        // line 55 "../../../../../Gameplay.ump"
        calculateScore();
        break;
      case ShowingRankings:
        // line 59 "../../../../../Gameplay.ump"
        showRanking();
        break;
    }
  }

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 69 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
       	case "OrderingNextDraft":
       		gamestatus=Gamestatus.InGame;
       		gamestatusInGame=GamestatusInGame.OrderingNextDraft;
       		break;
       	case "PreplacingDomino":
       		gamestatus=Gamestatus.InGame;
       		gamestatusInGame=GamestatusInGame.PreplacingDomino;
       		break;
       	case "SettingUp":
       		gamestatus=Gamestatus.SettingUp;
       		break;
       		
       	// TODO add further cases here to set desired state
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
       	}
  }


  /**
   * Guards
   */
  // line 93 "../../../../../Gameplay.ump"
   public boolean areAllDominoesInCurrentDraftSelected(){
    return GameplayController.areAllDominoesInCurrentDraftSelected();
  }

  // line 98 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    return GameplayController.isCurrentPlayerTheLastInTurn();
  }

  // line 102 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    return GameplayController.isCurrentTurnTheLastInGame();
  }

  // line 106 "../../../../../Gameplay.ump"
   public boolean isCorrectlyPreplaced(){
    return GameplayController.isCorrectlyPreplaced();
  }

  // line 111 "../../../../../Gameplay.ump"
   public boolean isLoadedGameValid(){
    return GameplayController.isLoadedGameValid();
  }

  // line 116 "../../../../../Gameplay.ump"
   public boolean impossibleToPlaceDomino(){
    return GameplayController.impossibleToPlaceDomino();
  }


  /**
   * You may need to add more guards here
   * Actions
   */
  // line 125 "../../../../../Gameplay.ump"
   public void showRanking(){
    
  }

  // line 128 "../../../../../Gameplay.ump"
   public void calculateScore(){
    
  }

  // line 131 "../../../../../Gameplay.ump"
   public void switchDraft(){
    
  }

  // line 133 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    GameplayController.acceptCallFromSM("shuffleDominoPile");
  }

  // line 138 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    GameplayController.acceptCallFromSM("generateInitialPlayerOrder");
  }

  // line 142 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    GameplayController.acceptCallFromSM("createNextDraft");
  }

  // line 147 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
    GameplayController.acceptCallFromSM("orderNextDraft");
  }

  // line 151 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
    GameplayController.acceptCallFromSM("revealNextDraft");
  }

  // line 155 "../../../../../Gameplay.ump"
   public void initializeGame(int numOfPlayers){
    GameplayController.acceptInitializeGameCallFromSM(numOfPlayers);
  }

  // line 160 "../../../../../Gameplay.ump"
   public void setGameOptions(){
    // TODO: implement this
  }

  // line 164 "../../../../../Gameplay.ump"
   public void currentPlayerSelectDomino(int id){
    // TODO: implement this
  }

  // line 168 "../../../../../Gameplay.ump"
   public void moveCurrentDomino(String dir){
    GameplayController.acceptMoveDominoCallFromSM(dir);
  }

  // line 172 "../../../../../Gameplay.ump"
   public void rotateCurrentDomino(int dir){
    // TODO: implement this
  }

  // line 176 "../../../../../Gameplay.ump"
   public void placeDomino(){
    GameplayController.acceptCallFromSM("placeDomino");
  }

  // line 180 "../../../../../Gameplay.ump"
   public void discardDomino(DominoInKingdom dominoInKingdom){
    GameplayController.acceptDiscardDominoFromSM(dominoInKingdom);
  }

  // line 185 "../../../../../Gameplay.ump"
   public void calculateCurrentPlayerScore(){
    GameplayController.acceptCallFromSM("calculateCurrentPlayerScore");
  }

  // line 190 "../../../../../Gameplay.ump"
   public void calculateRanking(){
    // TODO: implement this
  }

  // line 194 "../../../../../Gameplay.ump"
   public void resolveTieBreak(){
    // TODO: implement this
  }

  // line 198 "../../../../../Gameplay.ump"
   public void switchCurrentPlayer(){
    GameplayController.acceptCallFromSM("switchCurrentPlayer");
  }

  // line 202 "../../../../../Gameplay.ump"
   public void save(){
    // TODO: implement this
  }

  // line 206 "../../../../../Gameplay.ump"
   public void load(){
    // TODO: implement this
  }

}