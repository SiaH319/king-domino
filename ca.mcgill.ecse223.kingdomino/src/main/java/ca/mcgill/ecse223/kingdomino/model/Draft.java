/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 55 "../../../../../kingdomino.ump"
public class Draft
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DraftStatus { Current, Next, Unpicked }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Draft> draftsById = new HashMap<Integer, Draft>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Draft Attributes
  private DraftStatus draftStatus;
  private int id;

  //Draft Associations
  private List<Domino> dominos;
  private Game game;
  private Game current;
  private Game nextRound;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Draft(DraftStatus aDraftStatus, int aId, Game aGame, Game aCurrent, Game aNextRound)
  {
    draftStatus = aDraftStatus;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
    dominos = new ArrayList<Domino>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create draft due to game");
    }
    if (aCurrent == null || aCurrent.getCurrentDraft() != null)
    {
      throw new RuntimeException("Unable to create Draft due to aCurrent");
    }
    current = aCurrent;
    if (aNextRound == null || aNextRound.getNextDraft() != null)
    {
      throw new RuntimeException("Unable to create Draft due to aNextRound");
    }
    nextRound = aNextRound;
  }

  public Draft(DraftStatus aDraftStatus, int aId, Game aGame, int aNumberOfPlayersForCurrent, int aCurrentRoundForCurrent, Draft aNextDraftForCurrent, Application aApplicationForCurrent, int aNumberOfPlayersForNextRound, int aCurrentRoundForNextRound, Draft aCurrentDraftForNextRound, Application aApplicationForNextRound)
  {
    draftStatus = aDraftStatus;
    id = aId;
    dominos = new ArrayList<Domino>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create draft due to game");
    }
    current = new Game(aNumberOfPlayersForCurrent, aCurrentRoundForCurrent, this, aNextDraftForCurrent, aApplicationForCurrent);
    nextRound = new Game(aNumberOfPlayersForNextRound, aCurrentRoundForNextRound, aCurrentDraftForNextRound, this, aApplicationForNextRound);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Draft(ca.mcgill.ecse223.kingdomino.model.Game.DraftStatus aDraftStatusForCurrentDraft, int aIdForCurrentDraft,
		Game aGameForCurrentDraft, Game aCurrent, Game aNextRoundForCurrentDraft) {
}

public boolean setDraftStatus(DraftStatus aDraftStatus)
  {
    boolean wasSet = false;
    draftStatus = aDraftStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      draftsById.remove(anOldId);
    }
    draftsById.put(aId, this);
    return wasSet;
  }

  /**
   * enum DraftStatus { Current, Next, Unpicked}
   */
  public DraftStatus getDraftStatus()
  {
    return draftStatus;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Draft getWithId(int aId)
  {
    return draftsById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
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
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Game getCurrent()
  {
    return current;
  }
  /* Code from template association_GetOne */
  public Game getNextRound()
  {
    return nextRound;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfDominosValid()
  {
    boolean isValid = numberOfDominos() >= minimumNumberOfDominos() && numberOfDominos() <= maximumNumberOfDominos();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominos()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominos()
  {
    return 4;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Domino addDomino(String aLeftTileInfo, String aRightTileInfo, int aId, DominoRole aDominoRole, Game aGame, King aKing, Property aProperty)
  {
    if (numberOfDominos() >= maximumNumberOfDominos())
    {
      return null;
    }
    else
    {
      return new Domino(aLeftTileInfo, aRightTileInfo, aId, aDominoRole, aGame, aKing, this, aProperty);
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

    Draft existingDraft = aDomino.getDraft();
    boolean isNewDraft = existingDraft != null && !this.equals(existingDraft);

    if (isNewDraft && existingDraft.numberOfDominos() <= minimumNumberOfDominos())
    {
      return wasAdded;
    }

    if (isNewDraft)
    {
      aDomino.setDraft(this);
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
    //Unable to remove aDomino, as it must always have a draft
    if (this.equals(aDomino.getDraft()))
    {
      return wasRemoved;
    }

    //draft already at minimum (3)
    if (numberOfDominos() <= minimumNumberOfDominos())
    {
      return wasRemoved;
    }
    dominos.remove(aDomino);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDominoAt(Domino aDomino, int index)
  {  
    boolean wasAdded = false;
    if(addDomino(aDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominos()) { index = numberOfDominos() - 1; }
      dominos.remove(aDomino);
      dominos.add(index, aDomino);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDominoAt(Domino aDomino, int index)
  {
    boolean wasAdded = false;
    if(dominos.contains(aDomino))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDominos()) { index = numberOfDominos() - 1; }
      dominos.remove(aDomino);
      dominos.add(index, aDomino);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDominoAt(aDomino, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to draft
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (12)
    if (aGame.numberOfDrafts() >= Game.maximumNumberOfDrafts())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeDraft(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addDraft(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    draftsById.remove(getId());
    for(int i=dominos.size(); i > 0; i--)
    {
      Domino aDomino = dominos.get(i - 1);
      aDomino.delete();
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeDraft(this);
    }
    Game existingCurrent = current;
    current = null;
    if (existingCurrent != null)
    {
      existingCurrent.delete();
    }
    Game existingNextRound = nextRound;
    nextRound = null;
    if (existingNextRound != null)
    {
      existingNextRound.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "draftStatus" + "=" + (getDraftStatus() != null ? !getDraftStatus().equals(this)  ? getDraftStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "current = "+(getCurrent()!=null?Integer.toHexString(System.identityHashCode(getCurrent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "nextRound = "+(getNextRound()!=null?Integer.toHexString(System.identityHashCode(getNextRound())):"null");
  }
}