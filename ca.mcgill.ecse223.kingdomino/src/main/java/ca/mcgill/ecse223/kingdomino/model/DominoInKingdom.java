/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;

// line 71 "../../../../../Kingdomino.ump"
public class DominoInKingdom extends KingdomTerritory {

  //------------------------r
  // ENUMERATIONS
  //------------------------

  public enum DirectionKind { Up, Down, Left, Right }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DominoInKingdom Attributes
  private DirectionKind direction;

  //DominoInKingdom Associations
  private Domino domino;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DominoInKingdom(int aX, int aY, Kingdom aKingdom, Domino aDomino) {
    super(aX, aY, aKingdom);
    direction = DirectionKind.Up;
    if (!setDomino(aDomino)) {
      throw new RuntimeException("Unable to create DominoInKingdom due to aDomino");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  public static int[] getRightTilePosition(int x_left, int y_left, DirectionKind dir) {
    int x_right, y_right;
    switch (dir){
      case Down:
        x_right = x_left;
        y_right = y_left - 1;
        break;
      case Left:
        x_right = x_left - 1;
        y_right = y_left;
        break;
      case Right:
        x_right = x_left + 1;
        y_right = y_left;
        break;
      case Up:
        x_right = x_left;
        y_right = y_left + 1;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + dir);
    }
    return new int[]{x_right, y_right};
  }

  public boolean setDirection(DirectionKind aDirection) {
    boolean wasSet = false;
    direction = aDirection;
    wasSet = true;
    return wasSet;
  }

  /**
   * Measured wrt. the left tile of the domino
   */
  public DirectionKind getDirection() {
    return direction;
  }

  /* Code from template association_GetOne */
  public Domino getDomino() {
    return domino;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setDomino(Domino aNewDomino) {
    boolean wasSet = false;
    if (aNewDomino != null) {
      domino = aNewDomino;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete() {
    domino = null;
    super.delete();
  }

  public String toString() {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "direction" + "=" + (getDirection() != null ? !getDirection().equals(this)  ? getDirection().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "domino = "+(getDomino()!=null?Integer.toHexString(System.identityHashCode(getDomino())):"null");
  }
}