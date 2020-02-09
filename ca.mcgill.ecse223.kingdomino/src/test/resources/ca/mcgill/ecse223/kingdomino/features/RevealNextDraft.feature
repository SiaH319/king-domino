Feature: Reveal Next Draft
  As a player, I want the Kingdomino app to automatically reveal the
  next four dominoes once the previous round is finished

  #the current draft is assumed to be the one the players were taking the kings from initially before the round ended
  #nextdraft initially is the draft the players were placing their kings on until the round ended
  #next four dominoes are the next four dominoes in the pile which will form the next next draft
  Scenario Outline: Revealing the next draft when there are dominoes still in the pile
    Given dominoes are shuffled with fixed shuffle
    Given the top 5 dominoes in my pile have IDs (id9, id10, id11, id12,id13)
    Given There has been "<draftnum>" drafts
    Given there is a current draft
    Given there is an existing next draft(id4,id5,id6,id7)
    When reveal next draft is initiated
    Then dominoes "<list_of_ids>" are revealed to the players
    Then a new draft is created with "<list_of_ids>"
    Then the next draft is now the revealed one "<list_of_ids>"
    Then the dominoes are face down
    Then the top domino of the pile is ID "<topId>"

    Examples: 
      | draftnum | list_of_ids | topId |
      |        2 |  9,10,11,12 |    13 |

  Scenario Outline: Revealing the next draft when there are no more dominoes still in the pile
    Given dominoes are shuffled with fixed shuffle
    Given this is a "<num_players>" game
    Given there has been "<draftnum>" drafts
    When reveal next draft is initiated
    Then The game is over

    Examples: 
      | num_players | draftnum |
      |           2 |        6 |
      |           3 |        9 |
      |           4 |       12 |
