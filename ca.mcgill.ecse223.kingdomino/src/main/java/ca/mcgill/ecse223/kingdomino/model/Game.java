/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 28 "../../../../../kingdomino.ump"
public class Game
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { Green, Blue, Yellow, Pink }
  public enum DraftStatus { Current, Next, Unpicked }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private int numberOfPlayers;
  private int round;
  private int currentRound;

  //Game Associations
  private List<GameOption> gameOptions;
  private List<Draft> drafts;
  private Draft currentDraft;
  private Draft nextDraft;
  private List<Player> players;
  private List<King> kings;
  private List<Domino> dominos;
  private Application application;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(int aNumberOfPlayers, int aCurrentRound, Draft aCurrentDraft, Draft aNextDraft, Application aApplication)
  {
    numberOfPlayers = aNumberOfPlayers;
    resetRound();
    currentRound = aCurrentRound;
    gameOptions = new ArrayList<GameOption>();
    drafts = new ArrayList<Draft>();
    if (aCurrentDraft == null || aCurrentDraft.getCurrent() != null)
    {
      throw new RuntimeException("Unable to create Game due to aCurrentDraft");
    }
    currentDraft = aCurrentDraft;
    if (aNextDraft == null || aNextDraft.getNextRound() != null)
    {
      throw new RuntimeException("Unable to create Game due to aNextDraft");
    }
    nextDraft = aNextDraft;
    players = new ArrayList<Player>();
    kings = new ArrayList<King>();
    dominos = new ArrayList<Domino>();
    boolean didAddApplication = setApplication(aApplication);
    if (!didAddApplication)
    {
      throw new RuntimeException("Unable to create game due to application");
    }
  }

  public Game(int aNumberOfPlayers, int aCurrentRound, DraftStatus aDraftStatusForCurrentDraft, int aIdForCurrentDraft, Game aGameForCurrentDraft, Game aNextRoundForCurrentDraft, DraftStatus aDraftStatusForNextDraft, int aIdForNextDraft, Game aGameForNextDraft, Game aCurrentForNextDraft, Application aApplication)
  {
    numberOfPlayers = aNumberOfPlayers;
    resetRound();
    currentRound = aCurrentRound;
    gameOptions = new ArrayList<GameOption>();
    drafts = new ArrayList<Draft>();
    currentDraft = new Draft(aDraftStatusForCurrentDraft, aIdForCurrentDraft, aGameForCurrentDraft, this, aNextRoundForCurrentDraft);
    nextDraft = new Draft(aDraftStatusForNextDraft, aIdForNextDraft, aGameForNextDraft, aCurrentForNextDraft, this);
    players = new ArrayList<Player>();
    kings = new ArrayList<King>();
    dominos = new ArrayList<Domino>();
    boolean didAddApplication = setApplication(aApplication);
    if (!didAddApplication)
    {
      throw new RuntimeException("Unable to create game due to application");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetDefaulted */
  public boolean setRound(int aRound)
  {
    boolean wasSet = false;
    round = aRound;
    wasSet = true;
    return wasSet;
  }

  public boolean resetRound()
  {
    boolean wasReset = false;
    round = getDefaultRound();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentRound(int aCurrentRound)
  {
    boolean wasSet = false;
    currentRound = aCurrentRound;
    wasSet = true;
    return wasSet;
  }

  public int getNumberOfPlayers()
  {
    return numberOfPlayers;
  }

  public int getRound()
  {
    return round;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultRound()
  {
    return 0;
  }

  public int getCurrentRound()
  {
    return currentRound;
  }
  /* Code from template association_GetMany */
  public GameOption getGameOption(int index)
  {
    GameOption aGameOption = gameOptions.get(index);
    return aGameOption;
  }

  public List<GameOption> getGameOptions()
  {
    List<GameOption> newGameOptions = Collections.unmodifiableList(gameOptions);
    return newGameOptions;
  }

  public int numberOfGameOptions()
  {
    int number = gameOptions.size();
    return number;
  }

  public boolean hasGameOptions()
  {
    boolean has = gameOptions.size() > 0;
    return has;
  }

  public int indexOfGameOption(GameOption aGameOption)
  {
    int index = gameOptions.indexOf(aGameOption);
    return index;
  }
  /* Code from template association_GetMany */
  public Draft getDraft(int index)
  {
    Draft aDraft = drafts.get(index);
    return aDraft;
  }

  public List<Draft> getDrafts()
  {
    List<Draft> newDrafts = Collections.unmodifiableList(drafts);
    return newDrafts;
  }

  public int numberOfDrafts()
  {
    int number = drafts.size();
    return number;
  }

  public boolean hasDrafts()
  {
    boolean has = drafts.size() > 0;
    return has;
  }

  public int indexOfDraft(Draft aDraft)
  {
    int index = drafts.indexOf(aDraft);
    return index;
  }
  /* Code from template association_GetOne */
  public Draft getCurrentDraft()
  {
    return currentDraft;
  }
  /* Code from template association_GetOne */
  public Draft getNextDraft()
  {
    return nextDraft;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public King getKing(int index)
  {
    King aKing = kings.get(index);
    return aKing;
  }

  public List<King> getKings()
  {
    List<King> newKings = Collections.unmodifiableList(kings);
    return newKings;
  }

  public int numberOfKings()
  {
    int number = kings.size();
    return number;
  }

  public boolean hasKings()
  {
    boolean has = kings.size() > 0;
    return has;
  }

  public int indexOfKing(King aKing)
  {
    int index = kings.indexOf(aKing);
    return index;
  }
  /* Code from template association_GetMany */
  public Domino getDomino(int index)
  {
    Domino aDomino = dominos.get(index);
    return aDomino;
  }

  public List<Domino> getDominos()
  {
    List<Domino> newDominos = Collections.unmodifiableList(dominos);
    return newDominos;
  }

  public int numberOfDominos()
  {
    int number = dominos.size();
    return number;
  }

  public boolean hasDominos()
  {
    boolean has = dominos.size() > 0;
    return has;
  }

  public int indexOfDomino(Domino aDomino)
  {
    int index = dominos.indexOf(aDomino);
    return index;
  }
  /* Code from template association_GetOne */
  public Application getApplication()
  {
    return application;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameOptions()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGameOptions()
  {
    return 3;
  }
  /* Code from template association_AddOptionalNToOne */
  public GameOption addGameOption(OptionType aOptiontype, String aDesctiption, boolean aActivated)
  {
    if (numberOfGameOptions() >= maximumNumberOfGameOptions())
    {
      return null;
    }
    else
    {
      return new GameOption(aOptiontype, aDesctiption, aActivated, this);
    }
  }

  public boolean addGameOption(GameOption aGameOption)
  {
    boolean wasAdded = false;
    if (gameOptions.contains(aGameOption)) { return false; }
    if (numberOfGameOptions() >= maximumNumberOfGameOptions())
    {
      return wasAdded;
    }

    Game existingGame = aGameOption.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aGameOption.setGame(this);
    }
    else
    {
      gameOptions.add(aGameOption);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameOption(GameOption aGameOption)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameOption, as it must always have a game
    if (!this.equals(aGameOption.getGame()))
    {
      gameOptions.remove(aGameOption);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameOptionAt(GameOption aGameOption, int index)
  {  
    boolean wasAdded = false;
    if(addGameOption(aGameOption))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameOptions()) { index = numberOfGameOptions() - 1; }
      gameOptions.remove(aGameOption);
      gameOptions.add(index, aGameOption);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameOptionAt(GameOption aGameOption, int index)
  {
    boolean wasAdded = false;
    if(gameOptions.contains(aGameOption))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameOptions()) { index = numberOfGameOptions() - 1; }
      gameOptions.remove(aGameOption);
      gameOptions.add(index, aGameOption);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameOptionAt(aGameOption, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfDraftsValid()
  {
    boolean isValid = numberOfDrafts() >= minimumNumberOfDrafts() && numberOfDrafts() <= maximumNumberOfDrafts();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDrafts()
  {
    return 6;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDrafts()
  {
    return 12;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Draft addDraft(DraftStatus aDraftStatus, int aId, Game aCurrent, Game aNextRound)
  {
    if (numberOfDrafts() >= maximumNumberOfDrafts())
    {
      return null;
    }
    else
    {
      return new Draft(aDraftStatus, aId, this, aCurrent, aNextRound);
    }
  }

  public boolean addDraft(Draft aDraft)
  {
    boolean wasAdded = false;
    if (drafts.contains(aDraft)) { return false; }
    if (numberOfDrafts() >= maximumNumberOfDrafts())
    {
      return wasAdded;
    }

    Game existingGame = aDraft.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfDrafts() <= minimumNumberOfDrafts())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aDraft.setGame(this);
    }
    else
    {
      drafts.add(aDraft);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDraft(Draft aDraft)
  {
    boolean wasRemoved = false;
    //Unable to remove aDraft, as it must always have a game
    if (this.equals(aDraft.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (6)
    if (numberOfDrafts() <= minimumNumberOfDrafts())
    {
      return wasRemoved;
    }
    drafts.remove(aDraft);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDraftAt(Draft aDraft, int index)
  {  
    boolean wasAdded = false;
    if(addDraft(aDraft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDrafts()) { index = numberOfDrafts() - 1; }
      drafts.remove(aDraft);
      drafts.add(index, aDraft);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDraftAt(Draft aDraft, int index)
  {
    boolean wasAdded = false;
    if(drafts.contains(aDraft))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDrafts()) { index = numberOfDrafts() - 1; }
      drafts.remove(aDraft);
      drafts.add(index, aDraft);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDraftAt(aDraft, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(String aName, int aScore, int aRankInAllPlayers, Kingdom aKingdom, User aUser)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aName, aScore, aRankInAllPlayers, aKingdom, aUser, this);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Game existingGame = aPlayer.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aPlayer.setGame(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a game
    if (this.equals(aPlayer.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (2)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfKingsValid()
  {
    boolean isValid = numberOfKings() >= minimumNumberOfKings() && numberOfKings() <= maximumNumberOfKings();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfKings()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfKings()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public King addKing(Color aColor, int aCurrentPosition, int aUntillHisTurn, Player aPlayer)
  {
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return null;
    }
    else
    {
      return new King(aColor, aCurrentPosition, aUntillHisTurn, this, aPlayer);
    }
  }

  public boolean addKing(King aKing)
  {
    boolean wasAdded = false;
    if (kings.contains(aKing)) { return false; }
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return wasAdded;
    }

    Game existingGame = aKing.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfKings() <= minimumNumberOfKings())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aKing.setGame(this);
    }
    else
    {
      kings.add(aKing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeKing(King aKing)
  {
    boolean wasRemoved = false;
    //Unable to remove aKing, as it must always have a game
    if (this.equals(aKing.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (3)
    if (numberOfKings() <= minimumNumberOfKings())
    {
      return wasRemoved;
    }
    kings.remove(aKing);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addKingAt(King aKing, int index)
  {  
    boolean wasAdded = false;
    if(addKing(aKing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKings()) { index = numberOfKings() - 1; }
      kings.remove(aKing);
      kings.add(index, aKing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveKingAt(King aKing, int index)
  {
    boolean wasAdded = false;
    if(kings.contains(aKing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKings()) { index = numberOfKings() - 1; }
      kings.remove(aKing);
      kings.add(index, aKing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addKingAt(aKing, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfDominosValid()
  {
    boolean isValid = numberOfDominos() >= minimumNumberOfDominos() && numberOfDominos() <= maximumNumberOfDominos();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfDominos()
  {
    return 48;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominos()
  {
    return 48;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominos()
  {
    return 48;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Domino addDomino(String aLeftTileInfo, String aRightTileInfo, int aId, DominoRole aDominoRole, King aKing, Draft aDraft, Property aProperty)
  {
    if (numberOfDominos() >= maximumNumberOfDominos())
    {
      return null;
    }
    else
    {
      return new Domino(aLeftTileInfo, aRightTileInfo, aId, aDominoRole, this, aKing, aDraft, aProperty);
    }
  }

  public boolean addDomino(Domino aDomino)
  {
    boolean wasAdded = false;
    if (dominos.contains(aDomino)) { return false; }
    if (numberOfDominos() >= maximumNumberOfDominos())
    {
      return wasAdded;
    }

    Game existingGame = aDomino.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);

    if (isNewGame && existingGame.numberOfDominos() <= minimumNumberOfDominos())
    {
      return wasAdded;
    }

    if (isNewGame)
    {
      aDomino.setGame(this);
    }
    else
    {
      dominos.add(aDomino);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDomino(Domino aDomino)
  {
    boolean wasRemoved = false;
    //Unable to remove aDomino, as it must always have a game
    if (this.equals(aDomino.getGame()))
    {
      return wasRemoved;
    }

    //game already at minimum (48)
    if (numberOfDominos() <= minimumNumberOfDominos())
    {
      return wasRemoved;
    }
    dominos.remove(aDomino);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetOneToMany */
  public boolean setApplication(Application aApplication)
  {
    boolean wasSet = false;
    if (aApplication == null)
    {
      return wasSet;
    }

    Application existingApplication = application;
    application = aApplication;
    if (existingApplication != null && !existingApplication.equals(aApplication))
    {
      existingApplication.removeGame(this);
    }
    application.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (gameOptions.size() > 0)
    {
      GameOption aGameOption = gameOptions.get(gameOptions.size() - 1);
      aGameOption.delete();
      gameOptions.remove(aGameOption);
    }
    
    while (drafts.size() > 0)
    {
      Draft aDraft = drafts.get(drafts.size() - 1);
      aDraft.delete();
      drafts.remove(aDraft);
    }
    
    Draft existingCurrentDraft = currentDraft;
    currentDraft = null;
    if (existingCurrentDraft != null)
    {
      existingCurrentDraft.delete();
    }
    Draft existingNextDraft = nextDraft;
    nextDraft = null;
    if (existingNextDraft != null)
    {
      existingNextDraft.delete();
    }
    while (players.size() > 0)
    {
      Player aPlayer = players.get(players.size() - 1);
      aPlayer.delete();
      players.remove(aPlayer);
    }
    
    while (kings.size() > 0)
    {
      King aKing = kings.get(kings.size() - 1);
      aKing.delete();
      kings.remove(aKing);
    }
    
    while (dominos.size() > 0)
    {
      Domino aDomino = dominos.get(dominos.size() - 1);
      aDomino.delete();
      dominos.remove(aDomino);
    }
    
    Application placeholderApplication = application;
    this.application = null;
    if(placeholderApplication != null)
    {
      placeholderApplication.removeGame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "numberOfPlayers" + ":" + getNumberOfPlayers()+ "," +
            "round" + ":" + getRound()+ "," +
            "currentRound" + ":" + getCurrentRound()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentDraft = "+(getCurrentDraft()!=null?Integer.toHexString(System.identityHashCode(getCurrentDraft())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "nextDraft = "+(getNextDraft()!=null?Integer.toHexString(System.identityHashCode(getNextDraft())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "application = "+(getApplication()!=null?Integer.toHexString(System.identityHashCode(getApplication())):"null");
  }
}