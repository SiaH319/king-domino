/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.controller;

// line 9 "../../../../../TransferObjects.ump"
public class TODomino
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TODomino Attributes
  private int id;
  private String leftTileType;
  private String rightTileType;
  private int numLeftTileCrown;
  private int numRightTileCrown;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TODomino(int aId, String aLeftTileType, String aRightTileType, int aNumLeftTileCrown, int aNumRightTileCrown)
  {
    id = aId;
    leftTileType = aLeftTileType;
    rightTileType = aRightTileType;
    numLeftTileCrown = aNumLeftTileCrown;
    numRightTileCrown = aNumRightTileCrown;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setLeftTileType(String aLeftTileType)
  {
    boolean wasSet = false;
    leftTileType = aLeftTileType;
    wasSet = true;
    return wasSet;
  }

  public boolean setRightTileType(String aRightTileType)
  {
    boolean wasSet = false;
    rightTileType = aRightTileType;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumLeftTileCrown(int aNumLeftTileCrown)
  {
    boolean wasSet = false;
    numLeftTileCrown = aNumLeftTileCrown;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumRightTileCrown(int aNumRightTileCrown)
  {
    boolean wasSet = false;
    numRightTileCrown = aNumRightTileCrown;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getLeftTileType()
  {
    return leftTileType;
  }

  public String getRightTileType()
  {
    return rightTileType;
  }

  public int getNumLeftTileCrown()
  {
    return numLeftTileCrown;
  }

  public int getNumRightTileCrown()
  {
    return numRightTileCrown;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "leftTileType" + ":" + getLeftTileType()+ "," +
            "rightTileType" + ":" + getRightTileType()+ "," +
            "numLeftTileCrown" + ":" + getNumLeftTileCrown()+ "," +
            "numRightTileCrown" + ":" + getNumRightTileCrown()+ "]";
  }
}