/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.controller;

// line 3 "../../../../../TransferObjects.ump"
public class TOUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOUser Attributes
  private String name;
  private int playedGames;
  private int wonGames;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOUser(String aName, int aPlayedGames, int aWonGames)
  {
    name = aName;
    playedGames = aPlayedGames;
    wonGames = aWonGames;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayedGames(int aPlayedGames)
  {
    boolean wasSet = false;
    playedGames = aPlayedGames;
    wasSet = true;
    return wasSet;
  }

  public boolean setWonGames(int aWonGames)
  {
    boolean wasSet = false;
    wonGames = aWonGames;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getPlayedGames()
  {
    return playedGames;
  }

  public int getWonGames()
  {
    return wonGames;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "playedGames" + ":" + getPlayedGames()+ "," +
            "wonGames" + ":" + getWonGames()+ "]";
  }
}