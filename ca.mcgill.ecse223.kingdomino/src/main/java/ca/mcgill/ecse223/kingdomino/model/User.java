/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 15 "../../../../../kingdomino.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByUsername = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private int totalGamesPlayed;
  private int totalWins;

  //User Associations
  private List<Player> players;
  private Application application;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, Application aApplication)
  {
    // line 20 "../../../../../kingdomino.ump"
    if(aUsername.isEmpty() || aUsername.equals("") || aUsername == null){
       			throw new RuntimeException("The username must be specified.");
       		}
    // END OF UMPLE BEFORE INJECTION
    resetTotalGamesPlayed();
    resetTotalWins();
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username");
    }
    players = new ArrayList<Player>();
    boolean didAddApplication = setApplication(aApplication);
    if (!didAddApplication)
    {
      throw new RuntimeException("Unable to create user due to application");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    // line 20 "../../../../../kingdomino.ump"
    if(aUsername.isEmpty() || aUsername.equals("") || aUsername == null){
       			throw new RuntimeException("The username must be specified.");
       		}
    // END OF UMPLE BEFORE INJECTION
    String anOldUsername = getUsername();
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      usersByUsername.remove(anOldUsername);
    }
    usersByUsername.put(aUsername, this);
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setTotalGamesPlayed(int aTotalGamesPlayed)
  {
    boolean wasSet = false;
    totalGamesPlayed = aTotalGamesPlayed;
    wasSet = true;
    return wasSet;
  }

  public boolean resetTotalGamesPlayed()
  {
    boolean wasReset = false;
    totalGamesPlayed = getDefaultTotalGamesPlayed();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setTotalWins(int aTotalWins)
  {
    boolean wasSet = false;
    totalWins = aTotalWins;
    wasSet = true;
    return wasSet;
  }

  public boolean resetTotalWins()
  {
    boolean wasReset = false;
    totalWins = getDefaultTotalWins();
    wasReset = true;
    return wasReset;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithUsername(String aUsername)
  {
    return usersByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  public int getTotalGamesPlayed()
  {
    return totalGamesPlayed;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultTotalGamesPlayed()
  {
    return 0;
  }

  public int getTotalWins()
  {
    return totalWins;
  }
  /* Code from template attribute_GetDefaulted */
  public int getDefaultTotalWins()
  {
    return 0;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetOne */
  public Application getApplication()
  {
    return application;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Player addPlayer(String aName, int aScore, int aRankInAllPlayers, Kingdom aKingdom, Game aGame)
  {
    return new Player(aName, aScore, aRankInAllPlayers, aKingdom, this, aGame);
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    User existingUser = aPlayer.getUser();
    boolean isNewUser = existingUser != null && !this.equals(existingUser);
    if (isNewUser)
    {
      aPlayer.setUser(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a user
    if (!this.equals(aPlayer.getUser()))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setApplication(Application aApplication)
  {
    boolean wasSet = false;
    if (aApplication == null)
    {
      return wasSet;
    }

    Application existingApplication = application;
    application = aApplication;
    if (existingApplication != null && !existingApplication.equals(aApplication))
    {
      existingApplication.removeUser(this);
    }
    application.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usersByUsername.remove(getUsername());
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
    Application placeholderApplication = application;
    this.application = null;
    if(placeholderApplication != null)
    {
      placeholderApplication.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "totalGamesPlayed" + ":" + getTotalGamesPlayed()+ "," +
            "totalWins" + ":" + getTotalWins()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "application = "+(getApplication()!=null?Integer.toHexString(System.identityHashCode(getApplication())):"null");
  }
}