/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 49 "../../../../../kingdomino.ump"
public class King
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { Green, Blue, Yellow, Pink }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //King Attributes
  private Color color;
  private int currentPosition;
  private int untillHisTurn;

  //King Associations
  private List<Domino> dominos;
  private Game game;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public King(Color aColor, int aCurrentPosition, int aUntillHisTurn, Game aGame, Player aPlayer)
  {
    color = aColor;
    currentPosition = aCurrentPosition;
    untillHisTurn = aUntillHisTurn;
    dominos = new ArrayList<Domino>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create king due to game");
    }
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create king due to player");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public King(ca.mcgill.ecse223.kingdomino.model.Game.Color aColor, int aCurrentPosition, int aUntillHisTurn, Game aGame,
		Player aPlayer) {
}

public King(ca.mcgill.ecse223.kingdomino.model.Player.Color aColor, int aCurrentPosition, int aUntillHisTurn,
		Game aGame, Player aPlayer) {
}

public boolean setCurrentPosition(int aCurrentPosition)
  {
    boolean wasSet = false;
    currentPosition = aCurrentPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setUntillHisTurn(int aUntillHisTurn)
  {
    boolean wasSet = false;
    untillHisTurn = aUntillHisTurn;
    wasSet = true;
    return wasSet;
  }

  public Color getColor()
  {
    return color;
  }

  public int getCurrentPosition()
  {
    return currentPosition;
  }

  public int getUntillHisTurn()
  {
    return untillHisTurn;
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
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDominos()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDominos()
  {
    return 12;
  }
  /* Code from template association_AddOptionalNToOne */
  public Domino addDomino(String aLeftTileInfo, String aRightTileInfo, int aId, DominoRole aDominoRole, Game aGame, Draft aDraft, Property aProperty)
  {
    if (numberOfDominos() >= maximumNumberOfDominos())
    {
      return null;
    }
    else
    {
      return new Domino(aLeftTileInfo, aRightTileInfo, aId, aDominoRole, aGame, this, aDraft, aProperty);
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

    King existingKing = aDomino.getKing();
    boolean isNewKing = existingKing != null && !this.equals(existingKing);
    if (isNewKing)
    {
      aDomino.setKing(this);
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
    //Unable to remove aDomino, as it must always have a king
    if (!this.equals(aDomino.getKing()))
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to king
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfKings() >= Game.maximumNumberOfKings())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeKing(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addKing(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    //Must provide player to king
    if (aPlayer == null)
    {
      return wasSet;
    }

    //player already at maximum (2)
    if (aPlayer.numberOfKings() >= Player.maximumNumberOfKings())
    {
      return wasSet;
    }
    
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      boolean didRemove = existingPlayer.removeKing(this);
      if (!didRemove)
      {
        player = existingPlayer;
        return wasSet;
      }
    }
    player.addKing(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=dominos.size(); i > 0; i--)
    {
      Domino aDomino = dominos.get(i - 1);
      aDomino.delete();
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeKing(this);
    }
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removeKing(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "currentPosition" + ":" + getCurrentPosition()+ "," +
            "untillHisTurn" + ":" + getUntillHisTurn()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "color" + "=" + (getColor() != null ? !getColor().equals(this)  ? getColor().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}