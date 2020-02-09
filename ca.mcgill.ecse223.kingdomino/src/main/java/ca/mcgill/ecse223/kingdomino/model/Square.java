/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 122 "../../../../../kingdomino.ump"
public class Square
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TerrainType { WheatFiled, Lake, Forest, Grass, Mountain }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Square Attributes
  private TerrainType type;
  private int position;
  private int propertyId;

  //Square Associations
  private List<Square> Adjacent;
  private Kingdom kingdom;
  private Square square;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Square(int aPosition, Kingdom aKingdom)
  {
    position = aPosition;
    resetPropertyId();
    Adjacent = new ArrayList<Square>();
    boolean didAddKingdom = setKingdom(aKingdom);
    if (!didAddKingdom)
    {
      throw new RuntimeException("Unable to create square due to kingdom");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(TerrainType aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setPropertyId(int aPropertyId)
  {
    boolean wasSet = false;
    propertyId = aPropertyId;
    wasSet = true;
    return wasSet;
  }

  public boolean resetPropertyId()
  {
    boolean wasReset = false;
    propertyId = getDefaultPropertyId();
    wasReset = true;
    return wasReset;
  }

  public TerrainType getType()
  {
    return type;
  }

  public int getPosition()
  {
    return position;
  }

  public int getPropertyId()
  {
    return propertyId;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultPropertyId()
  {
    return -1;
  }
  /* Code from template association_GetMany */
  public Square getAdjacent(int index)
  {
    Square aAdjacent = Adjacent.get(index);
    return aAdjacent;
  }

  public List<Square> getAdjacent()
  {
    List<Square> newAdjacent = Collections.unmodifiableList(Adjacent);
    return newAdjacent;
  }

  public int numberOfAdjacent()
  {
    int number = Adjacent.size();
    return number;
  }

  public boolean hasAdjacent()
  {
    boolean has = Adjacent.size() > 0;
    return has;
  }

  public int indexOfAdjacent(Square aAdjacent)
  {
    int index = Adjacent.indexOf(aAdjacent);
    return index;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
  }
  /* Code from template association_GetOne */
  public Square getSquare()
  {
    return square;
  }

  public boolean hasSquare()
  {
    boolean has = square != null;
    return has;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAdjacent()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfAdjacent()
  {
    return 4;
  }
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addAdjacent(Square aAdjacent)
  {
    boolean wasAdded = false;
    if (Adjacent.contains(aAdjacent)) { return false; }
    if (numberOfAdjacent() >= maximumNumberOfAdjacent())
    {
      return wasAdded;
    }

    Square existingSquare = aAdjacent.getSquare();
    if (existingSquare == null)
    {
      aAdjacent.setSquare(this);
    }
    else if (!this.equals(existingSquare))
    {
      existingSquare.removeAdjacent(aAdjacent);
      addAdjacent(aAdjacent);
    }
    else
    {
      Adjacent.add(aAdjacent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAdjacent(Square aAdjacent)
  {
    boolean wasRemoved = false;
    if (Adjacent.contains(aAdjacent))
    {
      Adjacent.remove(aAdjacent);
      aAdjacent.setSquare(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAdjacentAt(Square aAdjacent, int index)
  {  
    boolean wasAdded = false;
    if(addAdjacent(aAdjacent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdjacent()) { index = numberOfAdjacent() - 1; }
      Adjacent.remove(aAdjacent);
      Adjacent.add(index, aAdjacent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAdjacentAt(Square aAdjacent, int index)
  {
    boolean wasAdded = false;
    if(Adjacent.contains(aAdjacent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAdjacent()) { index = numberOfAdjacent() - 1; }
      Adjacent.remove(aAdjacent);
      Adjacent.add(index, aAdjacent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAdjacentAt(aAdjacent, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setKingdom(Kingdom aKingdom)
  {
    boolean wasSet = false;
    //Must provide kingdom to square
    if (aKingdom == null)
    {
      return wasSet;
    }

    //kingdom already at maximum (25)
    if (aKingdom.numberOfSquares() >= Kingdom.maximumNumberOfSquares())
    {
      return wasSet;
    }
    
    Kingdom existingKingdom = kingdom;
    kingdom = aKingdom;
    if (existingKingdom != null && !existingKingdom.equals(aKingdom))
    {
      boolean didRemove = existingKingdom.removeSquare(this);
      if (!didRemove)
      {
        kingdom = existingKingdom;
        return wasSet;
      }
    }
    kingdom.addSquare(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setSquare(Square aSquare)
  {
    boolean wasSet = false;
    if (aSquare != null && aSquare.numberOfAdjacent() >= Square.maximumNumberOfAdjacent())
    {
      return wasSet;
    }

    Square existingSquare = square;
    square = aSquare;
    if (existingSquare != null && !existingSquare.equals(aSquare))
    {
      existingSquare.removeAdjacent(this);
    }
    if (aSquare != null)
    {
      aSquare.addAdjacent(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !Adjacent.isEmpty() )
    {
      Adjacent.get(0).setSquare(null);
    }
    Kingdom placeholderKingdom = kingdom;
    this.kingdom = null;
    if(placeholderKingdom != null)
    {
      placeholderKingdom.removeSquare(this);
    }
    if (square != null)
    {
      Square placeholderSquare = square;
      this.square = null;
      placeholderSquare.removeAdjacent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "position" + ":" + getPosition()+ "," +
            "propertyId" + ":" + getPropertyId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null");
  }
}