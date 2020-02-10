/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import java.util.*;

// line 94 "../../../../../kingdomino.ump"
public class GameOption
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OptionType { Dynasty, Harmony, MiddleKingdom }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<OptionType, GameOption> gameoptionsByOptiontype = new HashMap<OptionType, GameOption>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameOption Attributes
  private OptionType optiontype;
  private String desctiption;
  private boolean activated;

  //GameOption Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameOption(OptionType aOptiontype, String aDesctiption, boolean aActivated, Game aGame)
  {
    desctiption = aDesctiption;
    activated = aActivated;
    if (!setOptiontype(aOptiontype))
    {
      throw new RuntimeException("Cannot create due to duplicate optiontype");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create gameOption due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public GameOption(ca.mcgill.ecse223.kingdomino.model.OptionType aOptiontype, String aDesctiption, boolean aActivated,
		Game aGame) {
}

public boolean setOptiontype(OptionType aOptiontype)
  {
    boolean wasSet = false;
    OptionType anOldOptiontype = getOptiontype();
    if (hasWithOptiontype(aOptiontype)) {
      return wasSet;
    }
    optiontype = aOptiontype;
    wasSet = true;
    if (anOldOptiontype != null) {
      gameoptionsByOptiontype.remove(anOldOptiontype);
    }
    gameoptionsByOptiontype.put(aOptiontype, this);
    return wasSet;
  }

  public boolean setActivated(boolean aActivated)
  {
    boolean wasSet = false;
    activated = aActivated;
    wasSet = true;
    return wasSet;
  }

  /**
   * enum OptionType { Dynasty, Harmony,Dynasty }
   */
  public OptionType getOptiontype()
  {
    return optiontype;
  }
  /* Code from template attribute_GetUnique */
  public static GameOption getWithOptiontype(OptionType aOptiontype)
  {
    return gameoptionsByOptiontype.get(aOptiontype);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithOptiontype(OptionType aOptiontype)
  {
    return getWithOptiontype(aOptiontype) != null;
  }

  public String getDesctiption()
  {
    return desctiption;
  }

  public boolean getActivated()
  {
    return activated;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isActivated()
  {
    return activated;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    //Must provide game to gameOption
    if (aGame == null)
    {
      return wasSet;
    }

    //game already at maximum (3)
    if (aGame.numberOfGameOptions() >= Game.maximumNumberOfGameOptions())
    {
      return wasSet;
    }
    
    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      boolean didRemove = existingGame.removeGameOption(this);
      if (!didRemove)
      {
        game = existingGame;
        return wasSet;
      }
    }
    game.addGameOption(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    gameoptionsByOptiontype.remove(getOptiontype());
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeGameOption(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "desctiption" + ":" + getDesctiption()+ "," +
            "activated" + ":" + getActivated()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "optiontype" + "=" + (getOptiontype() != null ? !getOptiontype().equals(this)  ? getOptiontype().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}