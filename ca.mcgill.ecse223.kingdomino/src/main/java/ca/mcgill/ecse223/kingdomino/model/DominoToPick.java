/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 78 "../../../../../kingdomino.ump"
public class DominoToPick extends DominoRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DominoToPick Attributes
  private int positionInDraft;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DominoToPick(int aPositionInDraft)
  {
    super();
    positionInDraft = aPositionInDraft;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPositionInDraft(int aPositionInDraft)
  {
    boolean wasSet = false;
    positionInDraft = aPositionInDraft;
    wasSet = true;
    return wasSet;
  }

  public int getPositionInDraft()
  {
    return positionInDraft;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "positionInDraft" + ":" + getPositionInDraft()+ "]";
  }
}