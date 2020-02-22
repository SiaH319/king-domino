Feature: Order next draft of dominoes
  As a player, I want the Kingdomino app to automatically order the
  revealed next draft of dominoes in increasing order with respect to their numbers so that I know which are
  the more valuable domino

  #drafts are represented by their domino ids separated by commans and no spaces
  Scenario Outline: Ordering the next draft before the players start
    Given the game is initialized for next draft of dominoes
    Given the revealed next draft is "<unorderedids>"
    When the ordering of the dominoes in the next draft is initiated
    Then the status of the next draft is sorted
    Then the order of dominoes in the draft will be "<orderedids>"

    Examples: 
      | unorderedids     | orderedids     |
      |      8,9,5,2     |    2,5,8,9     |
      |      12,25,40,41 |    12,25,40,41 |
      |      45,32,10,13 |    10,13,32,45 |
      |      1,47,22,33  |    1,22,33,47  |
