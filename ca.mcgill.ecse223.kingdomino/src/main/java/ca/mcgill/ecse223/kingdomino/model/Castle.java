/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 68 "../../../../../kingdomino.ump"
public class Castle
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { Green, Blue, Yellow, Pink }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Castle Attributes
  private int position;
  private Color color;

  //Castle Associations
  private Square castlePosition;
  private Kingdom kingdom;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Castle(int aPosition, Color aColor, Square aCastlePosition, Kingdom aKingdom)
  {
    position = aPosition;
    color = aColor;
    if (!setCastlePosition(aCastlePosition))
    {
      throw new RuntimeException("Unable to create Castle due to aCastlePosition");
    }
    if (aKingdom == null || aKingdom.getCastle() != null)
    {
      throw new RuntimeException("Unable to create Castle due to aKingdom");
    }
    kingdom = aKingdom;
  }

  public Castle(int aPosition, Color aColor, Square aCastlePosition, int aNormalScoreForKingdom, int aBonusScoreForKingdom, int aTotalScoreForKingdom, Player aPlayerForKingdom)
  {
    position = aPosition;
    color = aColor;
    boolean didAddCastlePosition = setCastlePosition(aCastlePosition);
    if (!didAddCastlePosition)
    {
      throw new RuntimeException("Unable to create castle due to castlePosition");
    }
    kingdom = new Kingdom(aNormalScoreForKingdom, aBonusScoreForKingdom, aTotalScoreForKingdom, this, aPlayerForKingdom);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Castle(int aPositionForCastle, ca.mcgill.ecse223.kingdomino.model.Color aColorForCastle,
		Square aCastlePositionForCastle, Kingdom aKingdom) {
}

public boolean setPosition(int aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public int getPosition()
  {
    return position;
  }

  public Color getColor()
  {
    return color;
  }
  /* Code from template association_GetOne */
  public Square getCastlePosition()
  {
    return castlePosition;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCastlePosition(Square aNewCastlePosition)
  {
    boolean wasSet = false;
    if (aNewCastlePosition != null)
    {
      castlePosition = aNewCastlePosition;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    castlePosition = null;
    Kingdom existingKingdom = kingdom;
    kingdom = null;
    if (existingKingdom != null)
    {
      existingKingdom.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "position" + ":" + getPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "castlePosition = "+(getCastlePosition()!=null?Integer.toHexString(System.identityHashCode(getCastlePosition())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null");
  }
}