/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 3 "../../../../../Gameplay.ump"
public class Gameplay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay State Machines
  public enum Gamestatus { Initializing }
  public enum GamestatusInitializing { Null, CreatingFirstDraft, SelectingFirstDomino }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatus(Gamestatus.Initializing);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getGamestatusFullName()
  {
    String answer = gamestatus.toString();
    if (gamestatusInitializing != GamestatusInitializing.Null) { answer += "." + gamestatusInitializing.toString(); }
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

  public boolean draftReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    switch (aGamestatusInitializing)
    {
      case CreatingFirstDraft:
        exitGamestatusInitializing();
        // line 10 "../../../../../Gameplay.ump"
        revealNextDraft(); generateInitialPlayerOrder();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
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
        // line 9 "../../../../../Gameplay.ump"
        shuffleDominoPile(); createNextDraft(); orderNextDraft();
        break;
    }
  }

  public void delete()
  {}


  /**
   * Setter for test setup
   */
  // line 23 "../../../../../Gameplay.ump"
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
  // line 38 "../../../../../Gameplay.ump"
   public boolean isCurrentPlayerTheLastInTurn(){
    // TODO: implement this
        return false;
  }

  // line 43 "../../../../../Gameplay.ump"
   public boolean isCurrentTurnTheLastInGame(){
    // TODO: implement this
        return false;
  }


  /**
   * You may need to add more guards here
   * Actions
   */
  // line 54 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    // TODO: implement this
  }

  // line 58 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    // TODO: implement this
  }

  // line 62 "../../../../../Gameplay.ump"
   public void createNextDraft(){
    // TODO: implement this
  }

  // line 66 "../../../../../Gameplay.ump"
   public void orderNextDraft(){
    // TODO: implement this
  }

  // line 70 "../../../../../Gameplay.ump"
   public void revealNextDraft(){
    // TODO: implement this
  }

}