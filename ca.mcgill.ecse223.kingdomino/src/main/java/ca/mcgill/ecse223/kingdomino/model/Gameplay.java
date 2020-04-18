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
        initializeGame(numOfPlayers,userNames);setGameOptions(mkActivated, harmonyActivated);shuffleDominoPile(); createNextDraft(); orderNextDraft();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
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

  public boolean saveGame()
  {
    boolean wasEventProcessed = false;
    
    Gamestatus aGamestatus = gamestatus;
    switch (aGamestatus)
    {
      case InGame:
        exitGamestatus();
        // line 50 "../../../../../Gameplay.ump"
        save();
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
        // line 54 "../../../../../Gameplay.ump"
        
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
        // line 16 "../../../../../Gameplay.ump"
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
        // line 19 "../../../../../Gameplay.ump"
          
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 20 "../../../../../Gameplay.ump"
          
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
        // line 31 "../../../../../Gameplay.ump"
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
        // line 32 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();
          setGamestatusInGame(GamestatusInGame.SelectingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (impossibleToPlaceDomino()&&isCurrentTurnTheLastInGame()&&isCurrentPlayerTheLastInTurn())
        {
          exitGamestatus();
        // line 33 "../../../../../Gameplay.ump"
          discardDomino(); calculateCurrentPlayerScore();
          setGamestatus(Gamestatus.EndofGame);
          wasEventProcessed = true;
          break;
        }
        if (impossibleToPlaceDomino()&&isCurrentTurnTheLastInGame()&&!(isCurrentPlayerTheLastInTurn()))
        {
          exitGamestatusInGame();
        // line 34 "../../../../../Gameplay.ump"
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
        // line 45 "../../../../../Gameplay.ump"
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
        // line 48 "../../../../../Gameplay.ump"
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
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino); }
        break;
      case InGame:
        if (gamestatusInGame == GamestatusInGame.Null) { setGamestatusInGame(GamestatusInGame.PreplacingDomino); }
        break;
      case EndofGame:
        // line 53 "../../../../../Gameplay.ump"
        calculatePlayerRanking();resolveTieBreak();
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
        // line 21 "../../../../../Gameplay.ump"
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
        // line 15 "../../../../../Gameplay.ump"
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

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 63 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
        case "SelectingFirstDomino":
            gamestatus = Gamestatus.Initializing;
            gamestatusInitializing = GamestatusInitializing.SelectingFirstDomino;
            break;
        case "ProceedingToNextPlayerOrNextTurn":
            gamestatus = Gamestatus.Initializing;
            gamestatusInitializing = GamestatusInitializing.ProceedingToNextPlayerOrNextTurn;
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
  // line 95 "../../../../../Gameplay.ump"
   public boolean areAllDominoesInCurrentDraftSelected(){
    return GameplayController.areAllDominoesInCurrentDraftSelected();
  }

  // line 99 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    return GameplayController.isCurrentPlayerTheLastInTurn();
  }

  // line 103 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    return GameplayController.isCurrentTurnTheLastInGame();
  }

  // line 107 "../../../../../Gameplay.ump"
   public boolean isCorrectlyPreplaced(){
    return GameplayController.isCorrectlyPreplaced();
  }

  // line 112 "../../../../../Gameplay.ump"
   public boolean isLoadedGameValid(String filename){
    return GameplayController.isLoadedGameValid(filename);
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
   public String createUser(String name){
    return GameplayController.acceptCreateUserCallFromSM(name);
  }

  // line 130 "../../../../../Gameplay.ump"
   public void resolveTieBreak(){
    
  }

  // line 133 "../../../../../Gameplay.ump"
   public void calculatePlayerRanking(){
    
  }

  // line 136 "../../../../../Gameplay.ump"
   public void switchDraft(){
    
  }

  // line 138 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    GameplayController.acceptCallFromSM("shuffleDominoPile");
  }

  // line 143 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    GameplayController.acceptCallFromSM("generateInitialPlayerOrder");
  }

  // line 147 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    GameplayController.acceptCallFromSM("createNextDraft");
  }

  // line 152 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
    GameplayController.acceptCallFromSM("orderNextDraft");
  }

  // line 156 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
    GameplayController.acceptCallFromSM("revealNextDraft");
  }

  // line 160 "../../../../../Gameplay.ump"
   public void initializeGame(int numOfPlayers, String [] userNames){
    GameplayController.acceptInitializeGameCallFromSM(numOfPlayers,userNames);
  }

  // line 165 "../../../../../Gameplay.ump"
   public void setGameOptions(boolean mkActivated, boolean harmonyActivated){
    GameplayController.acceptSetBonusOptionFromSM(mkActivated, harmonyActivated);
  }

  // line 169 "../../../../../Gameplay.ump"
   public void currentPlayerSelectDomino(int id){
    // TODO: implement this
  }

  // line 173 "../../../../../Gameplay.ump"
   public void moveCurrentDomino(String dir){
    GameplayController.acceptMoveDominoCallFromSM(dir);
  }

  // line 177 "../../../../../Gameplay.ump"
   public void rotateCurrentDomino(int dir){
    // TODO: implement this
  }

  // line 181 "../../../../../Gameplay.ump"
   public void placeDomino(){
    GameplayController.acceptCallFromSM("placeDomino");
  }

  // line 185 "../../../../../Gameplay.ump"
   public void discardDomino(){
    GameplayController.acceptDiscardDominoFromSM();
  }

  // line 189 "../../../../../Gameplay.ump"
   public void calculateCurrentPlayerScore(){
    GameplayController.acceptCallFromSM("calculateCurrentPlayerScore");
  }

  // line 193 "../../../../../Gameplay.ump"
   public void switchCurrentPlayer(){
    GameplayController.acceptCallFromSM("switchCurrentPlayer");
  }

  // line 197 "../../../../../Gameplay.ump"
   public void save(){
    // TODO: implement this
  }

  // line 201 "../../../../../Gameplay.ump"
   public void load(){
    // TODO: implement this
  }

}