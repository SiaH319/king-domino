Feature: Set Game Options
  As a player, I want to configure the designated options of the Kingdomino game 
  including the number of players (2, 3 or 4) 
  and the bonus scoring options.

  Scenario Outline: Configuring game
    When I start configuring a new game
    When I set the number of players to <numplayer>
    When I "<isUsingHarmony>" select Harmony
    When I "<isUsingMiddleKingdom>" select Middle Kingdom
    Then I shall have <numplayer> players in
    Then I "<isUsingHarmony>" see Harmony as active bonus
    Then I "<isUsingMiddleKingdom>" see Middle Kingdom as active bonus

    Examples: 
      | numplayer | isUsingHarmony | isUsingMiddleKingdom |
      |         4 | do not         | do not               |
      |         4 | do             | do not               |
      |         4 | do not         | do                   |
      |         4 | do             | do                   |
      #|         3 | do not         | do not               |
      #|         3 | do             | do                   |
      #|         2 | do not         | do not               |
      #|         2 | do             | do                   |
