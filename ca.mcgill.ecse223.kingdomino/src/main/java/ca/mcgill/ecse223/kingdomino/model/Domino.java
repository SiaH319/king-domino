/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 62 "../../../../../kingdomino.ump"
public class Domino
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Domino> dominosById = new HashMap<Integer, Domino>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Domino Attributes
  private String leftTileInfo;
  private String rightTileInfo;
  private int id;

  //Domino Associations
  private DominoRole dominoRole;
  private Game game;
  private King king;
  private Draft draft;
  private Property property;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Domino(String aLeftTileInfo, String aRightTileInfo, int aId, DominoRole aDominoRole, Game aGame, King aKing, Draft aDraft, Property aProperty)
  {
    leftTileInfo = aLeftTileInfo;
    rightTileInfo = aRightTileInfo;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
    if (!setDominoRole(aDominoRole))
    {
      throw new RuntimeException("Unable to create Domino due to aDominoRole");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create domino due to game");
    }
    boolean didAddKing = setKing(aKing);
    if (!didAddKing)
    {
      throw new RuntimeException("Unable to create domino due to king");
    }
    boolean didAddDraft = setDraft(aDraft);
    if (!didAddDraft)
    {
      throw new RuntimeException("Unable to create domino due to draft");
    }
    boolean didAddProperty = setProperty(aProperty);
    if (!didAddProperty)
    {
      throw new RuntimeException("Unable to create domino due to property");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
      dominosById.remove(anOldId);
    }
    dominosById.put(aId, this);
    return wasSet;
  }

  public String getLeftTileInfo()
  {
    return leftTileInfo;
  }

  public String getRightTileInfo()
  {
    return rightTileInfo;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Domino getWithId(int aId)
  {
    return dominosById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }
  /* Code from template association_GetOne */
  public DominoRole getDominoRole()
  {
    return dominoRole;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public King getKing()
  {
    return king;
  }
  /* Code from template association_GetOne */
  public Draft getDraft()
  {
    return draft;
  }
  /* Code from template association_GetOne */
  public Property getProperty()
  {
    return property;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setDominoRole(DominoRole aNewDominoRole)
  {
    boolean wasSet = false;
    if (aNewDominoRole != null)
    {
      dominoRole = aNewDominoRole;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to domino
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (48)
    if (aGame.numberOfDominos() >= Game.maximumNumberOfDominos())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeDomino(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addDomino(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setKing(King aKing)
  {
    boolean wasSet = false;
    //Must provide king to domino
    if (aKing == null)
    {
      return wasSet;
    }

    //king already at maximum (12)
    if (aKing.numberOfDominos() >= King.maximumNumberOfDominos())
    {
      return wasSet;
    }
    
    King existingKing = king;
    king = aKing;
    if (existingKing != null && !existingKing.equals(aKing))
    {
      boolean didRemove = existingKing.removeDomino(this);
      if (!didRemove)
      {
        king = existingKing;
        return wasSet;
      }
    }
    king.addDomino(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setDraft(Draft aDraft)
  {
    boolean wasSet = false;
    //Must provide draft to domino
    if (aDraft == null)
    {
      return wasSet;
    }

    //draft already at maximum (4)
    if (aDraft.numberOfDominos() >= Draft.maximumNumberOfDominos())
    {
      return wasSet;
    }
    
    Draft existingDraft = draft;
    draft = aDraft;
    if (existingDraft != null && !existingDraft.equals(aDraft))
    {
      boolean didRemove = existingDraft.removeDomino(this);
      if (!didRemove)
      {
        draft = existingDraft;
        return wasSet;
      }
    }
    draft.addDomino(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setProperty(Property aProperty)
  {
    boolean wasSet = false;
    //Must provide property to domino
    if (aProperty == null)
    {
      return wasSet;
    }

    //property already at maximum (2)
    if (aProperty.numberOfDominos() >= Property.maximumNumberOfDominos())
    {
      return wasSet;
    }
    
    Property existingProperty = property;
    property = aProperty;
    if (existingProperty != null && !existingProperty.equals(aProperty))
    {
      boolean didRemove = existingProperty.removeDomino(this);
      if (!didRemove)
      {
        property = existingProperty;
        return wasSet;
      }
    }
    property.addDomino(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    dominosById.remove(getId());
    dominoRole = null;
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeDomino(this);
    }
    King placeholderKing = king;
    this.king = null;
    if(placeholderKing != null)
    {
      placeholderKing.removeDomino(this);
    }
    Draft placeholderDraft = draft;
    this.draft = null;
    if(placeholderDraft != null)
    {
      placeholderDraft.removeDomino(this);
    }
    Property placeholderProperty = property;
    this.property = null;
    if(placeholderProperty != null)
    {
      placeholderProperty.removeDomino(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "leftTileInfo" + ":" + getLeftTileInfo()+ "," +
            "rightTileInfo" + ":" + getRightTileInfo()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dominoRole = "+(getDominoRole()!=null?Integer.toHexString(System.identityHashCode(getDominoRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "king = "+(getKing()!=null?Integer.toHexString(System.identityHashCode(getKing())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "draft = "+(getDraft()!=null?Integer.toHexString(System.identityHashCode(getDraft())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "property = "+(getProperty()!=null?Integer.toHexString(System.identityHashCode(getProperty())):"null");
  }
}