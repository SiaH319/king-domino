/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 41 "../../../../../kingdomino.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Color { Green, Blue, Yellow, Pink }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Player> playersByName = new HashMap<String, Player>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private int score;
  private int rankInAllPlayers;

  //Player Associations
  private List<King> kings;
  private Kingdom kingdom;
  private User user;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, int aScore, int aRankInAllPlayers, Kingdom aKingdom, User aUser, Game aGame)
  {
    score = aScore;
    rankInAllPlayers = aRankInAllPlayers;
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name");
    }
    kings = new ArrayList<King>();
    if (aKingdom == null || aKingdom.getPlayer() != null)
    {
      throw new RuntimeException("Unable to create Player due to aKingdom");
    }
    kingdom = aKingdom;
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create player due to user");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  public Player(String aName, int aScore, int aRankInAllPlayers, int aNormalScoreForKingdom, int aBonusScoreForKingdom, int aTotalScoreForKingdom, Castle aCastleForKingdom, User aUser, Game aGame)
  {
    name = aName;
    score = aScore;
    rankInAllPlayers = aRankInAllPlayers;
    kings = new ArrayList<King>();
    kingdom = new Kingdom(aNormalScoreForKingdom, aBonusScoreForKingdom, aTotalScoreForKingdom, aCastleForKingdom, this);
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create player due to user");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create player due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      playersByName.remove(anOldName);
    }
    playersByName.put(aName, this);
    return wasSet;
  }

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setRankInAllPlayers(int aRankInAllPlayers)
  {
    boolean wasSet = false;
    rankInAllPlayers = aRankInAllPlayers;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static Player getWithName(String aName)
  {
    return playersByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
  }

  public int getScore()
  {
    return score;
  }

  public int getRankInAllPlayers()
  {
    return rankInAllPlayers;
  }
  /* Code from template association_GetMany */
  public King getKing(int index)
  {
    King aKing = kings.get(index);
    return aKing;
  }

  public List<King> getKings()
  {
    List<King> newKings = Collections.unmodifiableList(kings);
    return newKings;
  }

  public int numberOfKings()
  {
    int number = kings.size();
    return number;
  }

  public boolean hasKings()
  {
    boolean has = kings.size() > 0;
    return has;
  }

  public int indexOfKing(King aKing)
  {
    int index = kings.indexOf(aKing);
    return index;
  }
  /* Code from template association_GetOne */
  public Kingdom getKingdom()
  {
    return kingdom;
  }
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfKingsValid()
  {
    boolean isValid = numberOfKings() >= minimumNumberOfKings() && numberOfKings() <= maximumNumberOfKings();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfKings()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfKings()
  {
    return 2;
  }
  /* Code from template association_AddMNToOnlyOne */
  public King addKing(Color aColor, int aCurrentPosition, int aUntillHisTurn, Game aGame)
  {
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return null;
    }
    else
    {
      return new King(aColor, aCurrentPosition, aUntillHisTurn, aGame, this);
    }
  }

  public boolean addKing(King aKing)
  {
    boolean wasAdded = false;
    if (kings.contains(aKing)) { return false; }
    if (numberOfKings() >= maximumNumberOfKings())
    {
      return wasAdded;
    }

    Player existingPlayer = aKing.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);

    if (isNewPlayer && existingPlayer.numberOfKings() <= minimumNumberOfKings())
    {
      return wasAdded;
    }

    if (isNewPlayer)
    {
      aKing.setPlayer(this);
    }
    else
    {
      kings.add(aKing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeKing(King aKing)
  {
    boolean wasRemoved = false;
    //Unable to remove aKing, as it must always have a player
    if (this.equals(aKing.getPlayer()))
    {
      return wasRemoved;
    }

    //player already at minimum (1)
    if (numberOfKings() <= minimumNumberOfKings())
    {
      return wasRemoved;
    }
    kings.remove(aKing);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addKingAt(King aKing, int index)
  {  
    boolean wasAdded = false;
    if(addKing(aKing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKings()) { index = numberOfKings() - 1; }
      kings.remove(aKing);
      kings.add(index, aKing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveKingAt(King aKing, int index)
  {
    boolean wasAdded = false;
    if(kings.contains(aKing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfKings()) { index = numberOfKings() - 1; }
      kings.remove(aKing);
      kings.add(index, aKing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addKingAt(aKing, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setUser(User aUser)
  {
    boolean wasSet = false;
    if (aUser == null)
    {
      return wasSet;
    }

    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser))
    {
      existingUser.removePlayer(this);
    }
    user.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to player
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (4)
    if (aGame.numberOfPlayers() >= Game.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removePlayer(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    playersByName.remove(getName());
    for(int i=kings.size(); i > 0; i--)
    {
      King aKing = kings.get(i - 1);
      aKing.delete();
    }
    Kingdom existingKingdom = kingdom;
    kingdom = null;
    if (existingKingdom != null)
    {
      existingKingdom.delete();
    }
    User placeholderUser = user;
    this.user = null;
    if(placeholderUser != null)
    {
      placeholderUser.removePlayer(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "score" + ":" + getScore()+ "," +
            "rankInAllPlayers" + ":" + getRankInAllPlayers()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "kingdom = "+(getKingdom()!=null?Integer.toHexString(System.identityHashCode(getKingdom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}