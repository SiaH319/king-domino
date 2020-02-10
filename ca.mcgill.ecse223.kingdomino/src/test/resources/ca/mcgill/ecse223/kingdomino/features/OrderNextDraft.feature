Feature: Order next draft of dominoes
  As a player, I want the Kingdomino app to automatically order the
  revealed next draft of dominoes in increasing order with respect to their numbers so that I know which are
  the more valuable domino

  #drafts are represented by their domino ids separated by commans and no spaces
  Scenario Outline: Ordering the next draft before the players start
    Given the game is initialized for next draft of dominoes
    Given the revealed next draft is "<unorderedids>"
    Then the status of the next draft is sorted
    Then The order of dominoes in the draft will be "<orderedids>"
    Then the status of the next draft is sorted

    Examples: 
      | unorderedids | orderedids |
      |      8,9,5,2 |    2,5,8,9 |
