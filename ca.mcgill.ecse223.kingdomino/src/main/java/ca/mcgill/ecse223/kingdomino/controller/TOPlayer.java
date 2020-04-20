/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.controller;

// line 17 "../../../../../TransferObjects.ump"
public class TOPlayer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOPlayer Attributes
  private String color;
  private int currentRanking;
  private int bonusScore;
  private int propertyScore;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOPlayer(String aColor, int aCurrentRanking, int aBonusScore, int aPropertyScore)
  {
    color = aColor;
    currentRanking = aCurrentRanking;
    bonusScore = aBonusScore;
    propertyScore = aPropertyScore;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColor(String aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentRanking(int aCurrentRanking)
  {
    boolean wasSet = false;
    currentRanking = aCurrentRanking;
    wasSet = true;
    return wasSet;
  }

  public boolean setBonusScore(int aBonusScore)
  {
    boolean wasSet = false;
    bonusScore = aBonusScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setPropertyScore(int aPropertyScore)
  {
    boolean wasSet = false;
    propertyScore = aPropertyScore;
    wasSet = true;
    return wasSet;
  }

  public String getColor()
  {
    return color;
  }

  public int getCurrentRanking()
  {
    return currentRanking;
  }

  public int getBonusScore()
  {
    return bonusScore;
  }

  public int getPropertyScore()
  {
    return propertyScore;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "color" + ":" + getColor()+ "," +
            "currentRanking" + ":" + getCurrentRanking()+ "," +
            "bonusScore" + ":" + getBonusScore()+ "," +
            "propertyScore" + ":" + getPropertyScore()+ "]";
  }
}