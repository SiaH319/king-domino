/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 110 "../../../../../kingdomino.ump"
public class Kingdom
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TerrainType { WheatFiled, Lake, Forest, Grass, Mountain }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int SIZE = 5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Kingdom Attributes
  private int currentHeight;
  private int currentWidth;
  private int normalScore;
  private int bonusScore;
  private int totalScore;

  //Kingdom Associations
  private List<Square> squares;
  private List<Property> properties;
  private Castle castle;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Kingdom(int aNormalScore, int aBonusScore, int aTotalScore, Castle aCastle, Player aPlayer)
  {
    resetCurrentHeight();
    resetCurrentWidth();
    normalScore = aNormalScore;
    bonusScore = aBonusScore;
    totalScore = aTotalScore;
    squares = new ArrayList<Square>();
    properties = new ArrayList<Property>();
    if (aCastle == null || aCastle.getKingdom() != null)
    {
      throw new RuntimeException("Unable to create Kingdom due to aCastle");
    }
    castle = aCastle;
    if (aPlayer == null || aPlayer.getKingdom() != null)
    {
      throw new RuntimeException("Unable to create Kingdom due to aPlayer");
    }
    player = aPlayer;
  }

  public Kingdom(int aNormalScore, int aBonusScore, int aTotalScore, int aPositionForCastle, Color aColorForCastle, Square aCastlePositionForCastle, String aNameForPlayer, int aScoreForPlayer, int aRankInAllPlayersForPlayer, User aUserForPlayer, Game aGameForPlayer)
  {
    resetCurrentHeight();
    resetCurrentWidth();
    normalScore = aNormalScore;
    bonusScore = aBonusScore;
    totalScore = aTotalScore;
    squares = new ArrayList<Square>();
    properties = new ArrayList<Property>();
    castle = new Castle(aPositionForCastle, aColorForCastle, aCastlePositionForCastle, this);
    player = new Player(aNameForPlayer, aScoreForPlayer, aRankInAllPlayersForPlayer, this, aUserForPlayer, aGameForPlayer);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentHeight(int aCurrentHeight)
  {
    boolean wasSet = false;
    currentHeight = aCurrentHeight;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentHeight()
  {
    boolean wasReset = false;
    currentHeight = getDefaultCurrentHeight();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentWidth(int aCurrentWidth)
  {
    boolean wasSet = false;
    currentWidth = aCurrentWidth;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentWidth()
  {
    boolean wasReset = false;
    currentWidth = getDefaultCurrentWidth();
    wasReset = true;
    return wasReset;
  }

  public boolean setNormalScore(int aNormalScore)
  {
    boolean wasSet = false;
    normalScore = aNormalScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setBonusScore(int aBonusScore)
  {
    boolean wasSet = false;
    bonusScore = aBonusScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotalScore(int aTotalScore)
  {
    boolean wasSet = false;
    totalScore = aTotalScore;
    wasSet = true;
    return wasSet;
  }

  public int getCurrentHeight()
  {
    return currentHeight;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultCurrentHeight()
  {
    return 0;
  }

  public int getCurrentWidth()
  {
    return currentWidth;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultCurrentWidth()
  {
    return 0;
  }

  public int getNormalScore()
  {
    return normalScore;
  }

  public int getBonusScore()
  {
    return bonusScore;
  }

  public int getTotalScore()
  {
    return totalScore;
  }
  /* Code from template association_GetMany */
  public Square getSquare(int index)
  {
    Square aSquare = squares.get(index);
    return aSquare;
  }

  public List<Square> getSquares()
  {
    List<Square> newSquares = Collections.unmodifiableList(squares);
    return newSquares;
  }

  public int numberOfSquares()
  {
    int number = squares.size();
    return number;
  }

  public boolean hasSquares()
  {
    boolean has = squares.size() > 0;
    return has;
  }

  public int indexOfSquare(Square aSquare)
  {
    int index = squares.indexOf(aSquare);
    return index;
  }
  /* Code from template association_GetMany */
  public Property getProperty(int index)
  {
    Property aProperty = properties.get(index);
    return aProperty;
  }

  public List<Property> getProperties()
  {
    List<Property> newProperties = Collections.unmodifiableList(properties);
    return newProperties;
  }

  public int numberOfProperties()
  {
    int number = properties.size();
    return number;
  }

  public boolean hasProperties()
  {
    boolean has = properties.size() > 0;
    return has;
  }

  public int indexOfProperty(Property aProperty)
  {
    int index = properties.indexOf(aProperty);
    return index;
  }
  /* Code from template association_GetOne */
  public Castle getCastle()
  {
    return castle;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSquares()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfSquares()
  {
    return 25;
  }
  /* Code from template association_AddOptionalNToOne */
  public Square addSquare(int aPosition)
  {
    if (numberOfSquares() >= maximumNumberOfSquares())
    {
      return null;
    }
    else
    {
      return new Square(aPosition, this);
    }
  }

  public boolean addSquare(Square aSquare)
  {
    boolean wasAdded = false;
    if (squares.contains(aSquare)) { return false; }
    if (numberOfSquares() >= maximumNumberOfSquares())
    {
      return wasAdded;
    }

    Kingdom existingKingdom = aSquare.getKingdom();
    boolean isNewKingdom = existingKingdom != null && !this.equals(existingKingdom);
    if (isNewKingdom)
    {
      aSquare.setKingdom(this);
    }
    else
    {
      squares.add(aSquare);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSquare(Square aSquare)
  {
    boolean wasRemoved = false;
    //Unable to remove aSquare, as it must always have a kingdom
    if (!this.equals(aSquare.getKingdom()))
    {
      squares.remove(aSquare);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSquareAt(Square aSquare, int index)
  {  
    boolean wasAdded = false;
    if(addSquare(aSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSquares()) { index = numberOfSquares() - 1; }
      squares.remove(aSquare);
      squares.add(index, aSquare);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSquareAt(Square aSquare, int index)
  {
    boolean wasAdded = false;
    if(squares.contains(aSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSquares()) { index = numberOfSquares() - 1; }
      squares.remove(aSquare);
      squares.add(index, aSquare);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSquareAt(aSquare, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfProperties()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Property addProperty(int aId, TerrainType aType, int aNumberOfTiles, int aNumberOfCrowns, int aPropertyScore)
  {
    return new Property(aId, aType, aNumberOfTiles, aNumberOfCrowns, aPropertyScore, this);
  }

  public boolean addProperty(Property aProperty)
  {
    boolean wasAdded = false;
    if (properties.contains(aProperty)) { return false; }
    Kingdom existingKingdom = aProperty.getKingdom();
    boolean isNewKingdom = existingKingdom != null && !this.equals(existingKingdom);
    if (isNewKingdom)
    {
      aProperty.setKingdom(this);
    }
    else
    {
      properties.add(aProperty);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeProperty(Property aProperty)
  {
    boolean wasRemoved = false;
    //Unable to remove aProperty, as it must always have a kingdom
    if (!this.equals(aProperty.getKingdom()))
    {
      properties.remove(aProperty);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPropertyAt(Property aProperty, int index)
  {  
    boolean wasAdded = false;
    if(addProperty(aProperty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProperties()) { index = numberOfProperties() - 1; }
      properties.remove(aProperty);
      properties.add(index, aProperty);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePropertyAt(Property aProperty, int index)
  {
    boolean wasAdded = false;
    if(properties.contains(aProperty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfProperties()) { index = numberOfProperties() - 1; }
      properties.remove(aProperty);
      properties.add(index, aProperty);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPropertyAt(aProperty, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=squares.size(); i > 0; i--)
    {
      Square aSquare = squares.get(i - 1);
      aSquare.delete();
    }
    while (properties.size() > 0)
    {
      Property aProperty = properties.get(properties.size() - 1);
      aProperty.delete();
      properties.remove(aProperty);
    }
    
    Castle existingCastle = castle;
    castle = null;
    if (existingCastle != null)
    {
      existingCastle.delete();
    }
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "currentHeight" + ":" + getCurrentHeight()+ "," +
            "currentWidth" + ":" + getCurrentWidth()+ "," +
            "normalScore" + ":" + getNormalScore()+ "," +
            "bonusScore" + ":" + getBonusScore()+ "," +
            "totalScore" + ":" + getTotalScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "castle = "+(getCastle()!=null?Integer.toHexString(System.identityHashCode(getCastle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}