/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 101 "../../../../../kingdomino.ump"
public class Property
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TerrainType { WheatFiled, Lake, Forest, Grass, Mountain }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Property> propertysById = new HashMap<Integer, Property>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Property Attributes
  private int id;
  private TerrainType type;
  private int numberOfTiles;
  private int numberOfCrowns;
  private int propertyScore;

  //Property Associations
  private List<Domino> dominos;
  private Kingdom kingdom;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Property(int aId, TerrainType aType, int aNumberOfTiles, int aNumberOfCrowns, int aPropertyScore, Kingdom aKingdom)
  {
    type = aType;
    numberOfTiles = aNumberOfTiles;
    numberOfCrowns = aNumberOfCrowns;
    propertyScore = aPropertyScore;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id");
    }
    dominos = new ArrayList<Domino>();
    boolean didAddKingdom = setKingdom(aKingdom);
    if (!didAddKingdom)
    {
      throw new RuntimeException("Unable to create property due to kingdom");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Property(int aId, ca.mcgill.ecse223.kingdomino.model.Kingdom.TerrainType aType, int aNumberOfTiles,
		int aNumberOfCrowns, int aPropertyScore, Kingdom aKingdom) {
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
      propertysById.remove(anOldId);
    }
    propertysById.put(aId, this);
    return wasSet;
  }

  public boolean setNumberOfTiles(int aNumberOfTiles)
  {
    boolean wasSet = false;
    numberOfTiles = aNumberOfTiles;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumberOfCrowns(int aNumberOfCrowns)
  {
    boolean wasSet = false;
    numberOfCrowns = aNumberOfCrowns;
    wasSet = true;
    return wasSet;
  }

  public boolean setPropertyScore(int aPropertyScore)
  {
    boolean wasSet = false;
    propertyScore = aPropertyScore;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static Property getWithId(int aId)
  {
    return propertysById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public TerrainType getType()
  {
    return type;
  }

  public int getNumberOfTiles()
  {
    return numberOfTiles;
  }

  public int getNumberOfCrowns()
  {
    return numberOfCrowns;
  }

  public int getPropertyScore()
  {
    return propertyScore;
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
  public Kingdom getKingdom()
  {
    return kingdom;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominos()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominos()
  {
    return 2;
  }
  /* Code from template association_AddOptionalNToOne */
  public Domino addDomino(String aLeftTileInfo, String aRightTileInfo, int aId, DominoRole aDominoRole, Game aGame, King aKing, Draft aDraft)
  {
    if (numberOfDominos() >= maximumNumberOfDominos())
    {
      return null;
    }
    else
    {
      return new Domino(aLeftTileInfo, aRightTileInfo, aId, aDominoRole, aGame, aKing, aDraft, this);
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

    Property existingProperty = aDomino.getProperty();
    boolean isNewProperty = existingProperty != null && !this.equals(existingProperty);
    if (isNewProperty)
    {
      aDomino.setProperty(this);
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
    //Unable to remove aDomino, as it must always have a property
    if (!this.equals(aDomino.getProperty()))
    {
      dominos.remove(aDomino);
      wasRemoved = true;
    }
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
  /* Code from template association_SetOneToMany */
  public boolean setKingdom(Kingdom aKingdom)
  {
    boolean wasSet = false;
    if (aKingdom == null)
    {
      return wasSet;
    }

    Kingdom existingKingdom = kingdom;
    kingdom = aKingdom;
    if (existingKingdom != null && !existingKingdom.equals(aKingdom))
    {
      existingKingdom.removeProperty(this);
    }
    kingdom.addProperty(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    propertysById.remove(getId());
    for(int i=dominos.size(); i > 0; i--)
    {
      Domino aDomino = dominos.get(i - 1);
      aDomino.delete();
    }
    Kingdom placeholderKingdom = kingdom;
    this.kingdom = null;
    if(placeholderKingdom != null)
    {
      placeholderKingdom.removeProperty(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "numberOfTiles" + ":" + getNumberOfTiles()+ "," +
            "numberOfCrowns" + ":" + getNumberOfCrowns()+ "," +
            "propertyScore" + ":" + getPropertyScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null");
  }
}