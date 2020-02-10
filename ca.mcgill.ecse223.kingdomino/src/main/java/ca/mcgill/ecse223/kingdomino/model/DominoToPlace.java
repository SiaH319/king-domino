/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 83 "../../../../../kingdomino.ump"
public class DominoToPlace extends DominoRole
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlacementState { Provisional, Discarded, Finished }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DominoToPlace Attributes
  private PlacementState placementState;
  private int orientation;
  private int position;

  //DominoToPlace Associations
  private Square RightTile;
  private Square LeftTile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DominoToPlace(PlacementState aPlacementState, int aOrientation, int aPosition, Square aRightTile, Square aLeftTile)
  {
    super();
    placementState = aPlacementState;
    orientation = aOrientation;
    position = aPosition;
    if (!setRightTile(aRightTile))
    {
      throw new RuntimeException("Unable to create DominoToPlace due to aRightTile");
    }
    if (!setLeftTile(aLeftTile))
    {
      throw new RuntimeException("Unable to create DominoToPlace due to aLeftTile");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPlacementState(PlacementState aPlacementState)
  {
    boolean wasSet = false;
    placementState = aPlacementState;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrientation(int aOrientation)
  {
    boolean wasSet = false;
    orientation = aOrientation;
    wasSet = true;
    return wasSet;
  }

  public boolean setPosition(int aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  /**
   * enum PlacementState {Provisional, Discarded, Finished};
   */
  public PlacementState getPlacementState()
  {
    return placementState;
  }

  public int getOrientation()
  {
    return orientation;
  }

  public int getPosition()
  {
    return position;
  }
  /* Code from template association_GetOne */
  public Square getRightTile()
  {
    return RightTile;
  }
  /* Code from template association_GetOne */
  public Square getLeftTile()
  {
    return LeftTile;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRightTile(Square aNewRightTile)
  {
    boolean wasSet = false;
    if (aNewRightTile != null)
    {
      RightTile = aNewRightTile;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setLeftTile(Square aNewLeftTile)
  {
    boolean wasSet = false;
    if (aNewLeftTile != null)
    {
      LeftTile = aNewLeftTile;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    RightTile = null;
    LeftTile = null;
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "orientation" + ":" + getOrientation()+ "," +
            "position" + ":" + getPosition()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "placementState" + "=" + (getPlacementState() != null ? !getPlacementState().equals(this)  ? getPlacementState().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "RightTile = "+(getRightTile()!=null?Integer.toHexString(System.identityHashCode(getRightTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "LeftTile = "+(getLeftTile()!=null?Integer.toHexString(System.identityHashCode(getLeftTile())):"null");
  }
}