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
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino }
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

  public boolean createNewUser(String name)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case SettingUp:
        // line 7 "../../../../../Gameplay.ump"
        createUser(name);
        setGamestatus(Gamestatus.SettingUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startNewGame(int numOfPlayers,boolean mkActivated,boolean harmonyActivated,String [] userNames)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case SettingUp:
        // line 9 "../../../../../Gameplay.ump"
        initializeGame(numOfPlayers,userNames);setGameOptions(mkActivated, harmonyActivated);
        setGamestatusInitializing(GamestatusInitializing.CreatingFirstDraft);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean loadGame(String filename)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case SettingUp:
        if (isLoadedGameValid(filename))
        {
        // line 10 "../../../../../Gameplay.ump"
          
          setGamestatus(Gamestatus.InGame);
          wasEventProcessed = true;
          break;
        }
        if (!(isLoadedGameValid(filename)))
        {
        // line 11 "../../../../../Gameplay.ump"
          
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

  public boolean saveGame(String filename)
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case InGame:
        exitGamestatus();
        // line 52 "../../../../../Gameplay.ump"
        save(filename);
        setGamestatus(Gamestatus.SettingUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean restart()
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case EndofGame:
        // line 56 "../../../../../Gameplay.ump"
        
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
        // line 16 "../../../../../Gameplay.ump"
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
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInitializing();
        // line 20 "../../../../../Gameplay.ump"
          currentPlayerSelectDomino(id);switchCurrentPlayer();
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 21 "../../../../../Gameplay.ump"
          switchCurrentPlayer();
          setGamestatusInGame(GamestatusInGame.PreplacingDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    switch (aGamestatusInGame)
    {
      case SelectingNextDomino:
        exitGamestatusInGame();
        // line 40 "../../../../../Gameplay.ump"
        currentPlayerSelectDomino(id);
        setGamestatusInGame(GamestatusInGame.ProceedingToNextPlayerOrNextTurn);
        wasEventProcessed = true;
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
        // line 26 "../../../../../Gameplay.ump"
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
        // line 27 "../../../../../Gameplay.ump"
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
        // line 28 "../../../../../Gameplay.ump"
          placeDomino(); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCorrectlyPreplaced()&&isCurrentPlayerTheLastInTurn()&&isCurrentTurnTheLastInGame())
        {
          exitGamestatus();
        // line 30 "../../../../../Gameplay.ump"
          placeDomino();calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        if (isCorrectlyPreplaced()&&!(isCurrentPlayerTheLastInTurn())&&isCurrentTurnTheLastInGame())
        {
          exitGamestatusInGame();
        // line 32 "../../../../../Gameplay.ump"
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

  public boolean discard()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case PreplacingDomino:
        if (impossibleToPlaceDomino()&&!(isCurrentTurnTheLastInGame()))
        {
          exitGamestatusInGame();
        // line 33 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (impossibleToPlaceDomino()&&isCurrentTurnTheLastInGame()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 34 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        if (impossibleToPlaceDomino()&&isCurrentTurnTheLastInGame()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInGame();
        // line 36 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();switchCurrentPlayer();
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

  public boolean proceed()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInGame aGamestatusInGame = gamestatusInGame;
    switch (aGamestatusInGame)
    {
      case ProceedingToNextPlayerOrNextTurn:
        if (!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInGame();
        // line 43 "../../../../../Gameplay.ump"
          switchCurrentPlayer();
          setGamestatusInGame(GamestatusInGame.PreplacingDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatusInGame();
        // line 44 "../../../../../Gameplay.ump"
          createNextDraft();
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
        // line 47 "../../../../../Gameplay.ump"
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
        // line 50 "../../../../../Gameplay.ump"
        revealNextDraft();switchCurrentPlayer();
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
        // line 55 "../../../../../Gameplay.ump"
        calculatePlayerRanking();
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
        // line 15 "../../../../../Gameplay.ump"
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
  // line 65 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
        case "SelectingFirstDomino":
            gamestatus = Gamestatus.Initializing;
            gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
            break;
       	case "OrderingNextDraft":
       		gamestatus = Gamestatus.InGame;
       		gamestatusInGame = GamestatusInGame.OrderingNextDraft;
       		break;
       	case "PreplacingDomino":
       		gamestatus=Gamestatus.InGame;
       		gamestatusInGame = GamestatusInGame.PreplacingDomino;
       		break;
       	case "SettingUp":
       		gamestatus = Gamestatus.SettingUp;
       		break;

       	// TODO add further cases here to set desired state
       	default:
       	    throw new RuntimeException("Invalid Game Status string was provided: " + status);
       	}
  }


  /**
   * Guards
   */
  // line 93 "../../../../../Gameplay.ump"
   public boolean areAllDominoesInCurrentDraftSelected(){
    return GameplayController.areAllDominoesInCurrentDraftSelected();
  }

  // line 97 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    return GameplayController.isCurrentPlayerTheLastInTurn();
  }

  // line 101 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    return GameplayController.isCurrentTurnTheLastInGame();
  }

  // line 105 "../../../../../Gameplay.ump"
   public boolean isCorrectlyPreplaced(){
    return GameplayController.isCorrectlyPreplaced();
  }

  // line 110 "../../../../../Gameplay.ump"
   public boolean isLoadedGameValid(String filename){
    return GameplayController.isLoadedGameValid(filename);
  }

  // line 114 "../../../../../Gameplay.ump"
   public boolean impossibleToPlaceDomino(){
    return GameplayController.impossibleToPlaceDomino();
  }


  /**
   * You may need to add more guards here
   * Actions
   */
  // line 123 "../../../../../Gameplay.ump"
   public String createUser(String name){
    return GameplayController.acceptCreateUserCallFromSM(name);
  }

  // line 127 "../../../../../Gameplay.ump"
   public void calculatePlayerRanking(){
    GameplayController.acceptCallFromSM("calculateRanking");
  }

  // line 131 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    GameplayController.acceptCallFromSM("shuffleDominoPile");
  }

  // line 136 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    GameplayController.acceptCallFromSM("generateInitialPlayerOrder");
  }

  // line 140 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    GameplayController.acceptCallFromSM("createNextDraft");
  }

  // line 145 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
    GameplayController.acceptCallFromSM("orderNextDraft");
  }

  // line 149 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
    GameplayController.acceptCallFromSM("revealNextDraft");
  }

  // line 153 "../../../../../Gameplay.ump"
   public void initializeGame(int numOfPlayers, String [] userNames){
    GameplayController.acceptInitializeGameCallFromSM(numOfPlayers,userNames);
  }

  // line 158 "../../../../../Gameplay.ump"
   public void setGameOptions(boolean mkActivated, boolean harmonyActivated){
    GameplayController.acceptSetBonusOptionFromSM(mkActivated, harmonyActivated);
  }

  // line 162 "../../../../../Gameplay.ump"
   public void currentPlayerSelectDomino(int id){
    GameplayController.acceptSelectDominoCallFromSM(id);
  }

  // line 166 "../../../../../Gameplay.ump"
   public void moveCurrentDomino(String dir){
    GameplayController.acceptMoveDominoCallFromSM(dir);
  }

  // line 170 "../../../../../Gameplay.ump"
   public void rotateCurrentDomino(int dir){
    GameplayController.acceptRotateCurrentDomino(dir);
  }

  // line 174 "../../../../../Gameplay.ump"
   public void placeDomino(){
    GameplayController.acceptPlaceDominoFromSM();
  }

  // line 178 "../../../../../Gameplay.ump"
   public void discardDomino(){
    GameplayController.acceptDiscardDominoFromSM();
  }

  // line 182 "../../../../../Gameplay.ump"
   public void calculateCurrentPlayerScore(){
    GameplayController.acceptCallFromSM("calculateCurrentPlayerScore");
  }

  // line 186 "../../../../../Gameplay.ump"
   public void switchCurrentPlayer(){
    GameplayController.acceptCallFromSM("switchCurrentPlayer");
  }

  // line 190 "../../../../../Gameplay.ump"
   public void save(String filename){
    GameplayController.acceptSaveGameCallFromSM(filename);
  }

}