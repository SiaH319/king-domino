package ca.mcgill.ecse223.kingdomino.controller;

import java.util.*;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;

/**Note that there are several calculation methods in this controller class.*/

public class CalculationController {
    /////////////////////////////        //////
    ///// ///Feature Methods////        //////
    ///////////////////////////        //////
    /**
     * Feature 19: Calculate Bonus Scores
     * As a player, I want the Kingdomino app to automatically calculate the bonus scores (for Harmony and
     * middle Kingdom) if those bonus scores were selected as a game option.
     * @author Yuta Youness Bellali
     * @param s, DisjointSet representing a given Player's kingdom
     * @param grid, Square[] array representing a given Player's kingdom
     * @param kingdom, a given player's assoicated Kingdom
     */
    public static void identifyPropertoes(DisjointSet s, Square[] grid, Kingdom kingdom){
    	System.out.println("-----------Identifying the properties----------");
        int pari,countsets=0;
        ArrayList<Properties> properties = new ArrayList<Properties>();
        
        Boolean[] arr = new Boolean[s.parArraySize()];
        Arrays.fill(arr, false);
        


        String[] setstrings = new String[s.parArraySize()];
        /* build string for each set */
        for (int i=0; i<s.parArraySize(); i++) {
            pari = s.find(i);
            if(grid[i].getTerrain()!=null){
                if (setstrings[pari]==null) {
                    setstrings[pari] = String.valueOf(i);
                    countsets+=1;
                } else {
                    setstrings[pari] += "," + i;

                }
            }
        }
        /* Add Properties */
        int size=kingdom.getProperties().size();
        int k=0;
        while(k<size) {
        	kingdom.getProperty(0).delete();// clear the properties because we will add them later on again
        	k++;
        }

        for (int i=0; i<s.parArraySize(); i++) {
            if (setstrings[i] != null) {

                Property p = new Property(kingdom);
                System.out.println("here");

                String[] containedSquareIndexes = setstrings[i].split(",");
                int p_Crown = 0;
                int p_size = 0;
                for(String index: containedSquareIndexes){
                    int squareIndex = Integer.parseInt(index);
                    p_Crown += grid[squareIndex].getCrown();
                    p_size++;
                    p.setPropertyType(grid[squareIndex].getTerrain());
                    int id = grid[squareIndex].getDominoId();
                    p.addIncludedDomino(getdominoByID(id));
                }
                System.out.println("Setting the number of crowns: "+p_Crown);
                System.out.println("and the size of prop: "+p_size);

                p.setCrowns(p_Crown);
                p.setSize(p_size);
            }
        }

    }

    /**
     * Feature 20: Calculate Property Scores
     * As a player, I want the Kingdomino app to automatically calculate the score for each of my property based upon
     * the size of that property and the number of crowns.
     * @author Yuta Youness Bellali
     * @param properties
     * @param player
     */
    public static void calculatePropertyScore(List<Property> properties, Player player) {
        int score = 0;
        int Totalscore = 0;
        System.out.println("----------Now checking each property----------");
        for (Property p: properties) {
            score = p.getSize() * p.getCrowns();
            System.out.println("Size: "+p.getSize()+"  Crowns: "+p.getCrowns());

            System.out.println("Score from that property is: "+score);
            Totalscore += score;
        }
        System.out.println("setting player score to :"+Totalscore);

        player.setPropertyScore(Totalscore);

    }

    /**
     * Feature 21: Calculate Bonus Scores
     * As a player, I want the Kingdomino app to automatically calculate the bonus scores (for Harmony and
     * middle Kingdom) if those bonus scores were selected as a game option.
     * @author Yuta Youness Bellali
     * @param currentGame
     * @param player
     */
    public static void CalculateBonusScore(Game currentGame, Player player) {

        int bonusAmount = currentGame.getSelectedBonusOptions().size();

        /***
         * code for: Harmony is selected A player will get 5 additional point if the
         * player did not discard any dominos Simply check if a kingdom contains 13
         * territories (all left tiles + castle)
         */

        if (bonusAmount == 1 && currentGame.getSelectedBonusOption(0).getOptionName().equalsIgnoreCase("harmony")) {

            List<KingdomTerritory> allTerritories = player.getKingdom().getTerritories();

            if (allTerritories.size() == 13)
                player.setBonusScore(5);

        }

        /***
         * Code for: Middle Kingdom The player will get an additional 10 points if its
         * castle is placed in the middle of their territory
         */

        if (bonusAmount == 1
                && currentGame.getSelectedBonusOption(0).getOptionName().equalsIgnoreCase("middle kingdom")) {

            Kingdom playersKingdom = player.getKingdom();

            boolean middle = false;

            List<KingdomTerritory> territories = playersKingdom.getTerritories();

            middle = verifyMiddleCastle(territories);

            if (middle) {

                player.setBonusScore(10);

            }

        }

        /***
         * Code for: If both Middle kingdom and harmony are selected
         */
        if (bonusAmount == 2) {

            // if harmony is selected
            List<KingdomTerritory> allTerritories = player.getKingdom().getTerritories();
            ;

            boolean middle = false;

            middle = verifyMiddleCastle(allTerritories);

            // calculate total Bonus Score
            if (middle && (allTerritories.size() != 13))
                player.setBonusScore(10);
            if (!middle && (allTerritories.size() == 13))
                player.setBonusScore(5);
            if (!middle && (allTerritories.size() != 13))
                player.setBonusScore(0);
            if (middle && (allTerritories.size() == 13))
                player.setBonusScore(15);
        }
    }
    public static void calculateCurrentPlayerScore() {
    	System.out.println("enterred the calculation");
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Player p= game.getNextPlayer();
        String player0Name = (p.getUser().getName());
        Square[] grid = GameController.getGrid(player0Name);
        DisjointSet s = GameController.getSet(player0Name);
        identifyPropertoes(s, grid, p.getKingdom());
        calculatePropertyScore(p.getKingdom().getProperties(),p);
        CalculateBonusScore(game, p);
        if(s==null || grid==null) {
        	System.out.println("Something wrong here");
        }
        
        }

    /**Feature 23+24
     * @author Mohamad
     * Given if there is a tie or not, we know which method to call
     * @param boolean noTie, tells if there is a tie or not
     * @param ScoreList, stores all players'scores 
     */
    public static void calculateRanking(Boolean noTie,ArrayList<Integer> ScoreList) {
       
        if(noTie) {
        	RankingNoTie(ScoreList);
        }
        
        else {
            RankingWithTie();
        }
    }
    /**
     * @author Mohamad
     * Sort the array of scores, since all scores are distinct (i.e no tie) we can get the payers from their scores.
     * Feature 23
     * 
     * @param ArralyList of Scores, stores the scores of all players
     */
    public static void RankingNoTie(ArrayList<Integer> ScoreList) {
    	
    	Collections.sort(ScoreList);//sort the score list
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        ArrayList<Player> Chosen = new ArrayList<Player>(); 
        for(int i=0;i<ScoreList.size();i++) {// since its in ascending order, last score is the ranked first
            int currentScore = ScoreList.get(i);
            Player player =getPlayer(currentScore,Chosen,game);
            player.setCurrentRanking(ScoreList.size()-i);
        }
    
    	
    }
    /**
     * 
     * Sort the array of players according to their scores
     * Feature 24
     * @author Mohamad
     * 
     */
    public static void RankingWithTie() {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();

    	ArrayList<Player> Chosen = new ArrayList<Player>(); //we are finding players through score
        System.out.println("enetered the resolve tie");     //since score are repetitive, we dont want to chose
        													//the same player more than once
        Player playerSorted[] = {getPlayer("green",game),getPlayer("pink",game),getPlayer("blue",game),getPlayer("yellow",game)};
        quicksort(playerSorted,0,playerSorted.length-1); //sort the players with respect to the score
        int position=1;
        for(int i=playerSorted.length-1;i>-1;i--) { // set positions but also check if other players score are equal
            playerSorted[i].setCurrentRanking(position);
            if(i<3) {
                int j=(i+1);
                if(playersEqual(playerSorted[i],playerSorted[j])) { //if score is equal set the same rank
                    playerSorted[i].setCurrentRanking(playerSorted[j].getCurrentRanking());
                    position--;
                }
            }

            position++;
        }
    }

    /////////////////////////////        //////
    ///// ///Helper Methods/////        //////
    ///////////////////////////        //////
    /***
     * Veryfying if castle is in the middle of Kingdom
     * 
     * @param territories
     * @return true or false
     */
    private static boolean verifyMiddleCastle(List<KingdomTerritory> territories) {
        boolean result = true;
        int x_max = -10;
        int x_min = 10;
        int y_max = -10;
        int y_min = 10;
        for (KingdomTerritory territory : territories) {

            int x_left = territory.getX();
            int y_left = territory.getY();
            x_max = Math.max(x_max, x_left);
            x_min = Math.min(x_min, x_left);
            y_max = Math.max(y_max, y_left);
            y_min = Math.min(y_min, y_left);

            if (territory instanceof DominoInKingdom) {
                int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left,
                        ((DominoInKingdom) territory).getDirection());
                int x_right = pos_right[0];
                int y_right = pos_right[1];
                result = result && (x_right <= 4 && x_right >= -4 && y_right <= 4 && y_right >= -4);

                x_max = Math.max(x_max, x_right);
                x_min = Math.min(x_min, x_right);
                y_max = Math.max(y_max, y_right);
                y_min = Math.min(y_min, y_right);
            }
        }

        return result && (y_max + y_min == 0) && (x_max + x_min == 0);
    }

    private static Domino getdominoByID(int id) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        for (Domino domino : game.getAllDominos()) {
            if (domino.getId() == id) {
                return domino;
            }
        }
        throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
    }

    /**
     *
     * Helper Method : Tells me if two players are equal
     * @author Mohamad
     * @param p2:Player 1
     * @param p1:Player 1
     * @return true if both have the same scores,crowns total and size of largest property, false otherwise
     */
    private static boolean playersEqual(Player p1,Player p2) {
        if((p1.getBonusScore()+p1.getPropertyScore())==(p2.getBonusScore()+p2.getPropertyScore())){
            if(largestProperty(p1)==largestProperty(p2)) {
                if(totalCrowns(p1)==totalCrowns(p2)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 
     * Helper Method : Swaps the standings of two given players
     * @author Mohamad
     * @param p1 :player 1
     * @param p2 :player 2
     */
    private static void swapStandings(Player p1, Player p2) {
        int i=p1.getCurrentRanking();
        int j=p2.getCurrentRanking();
        p2.setCurrentRanking(i);
        p1.setCurrentRanking(j);
    }
    private static char printTerrain(TerrainType terrainType){
        char c;
        switch(terrainType){
            case WheatField:
                c = 'W';
                break;
            case Mountain:
                c = 'M';
                break;
            case Lake:
                c = 'L';
                break;
            case Forest:
                c = 'F';
                break;
            case Grass:
                c = 'G';
                break;
            case Swamp:
                c = 'S';
                break;
            default:
                c = '/';
                break;
        }
        return c;
    }
    /**
     *
     * Helper method, gets the size of largest property
     * @author Mohamad
     * @param p : player
     * @return the size of the largest property
     */
    private static int largestProperty(Player p) {
        int max=0;
        if(p==null) {
            System.out.println("Player is null");
        }
        for(Property prop:p.getKingdom().getProperties()) {
            if(prop!=null) {
                if(prop.getSize()>max) {
                    max=prop.getSize();
                }
            }
        }
        return max;
    }
    /**
     *
     * Helper method: gets the total number of crowns
     * @author Mohamad
     * @param p : Player
     * @return the total number of crowns
     */
    private static int totalCrowns(Player p) {
        int total=0;
        if(p==null) {
            System.out.println("Player is null");
        }
        for(Property prop:p.getKingdom().getProperties()) {
            if(prop!=null) {
                total+=prop.getCrowns();
            }
        }
        return total;
    }
    /**
     * 
     * TODO Given a List of players already chosen, this method returns a players not chosen having a specific score.
     * @author Mohamad
     * @param int score
     * @param ArrayList AlreadyChosen
     * @param Game game
     * @return Player p
     */
    private static Player getPlayer(int score, ArrayList<Player> AlreadyChosen,Game game) {
        for(Player p:game.getPlayers()) {
            if((p.getBonusScore()+p.getPropertyScore())==score) {
                if(AlreadyChosen.contains(p)==false) {
                    AlreadyChosen.add(p);

                    return p;
                }
            }
        }


        return null;

    }
    private static Player getPlayer(String color,Game game) {
        switch(color) {
            case "green":
                for( Player p :game.getPlayers()) {
                    if(p.getColor().equals(Player.PlayerColor.Green)) {
                        return p;
                    }
                }
            case "pink":
                for( Player p :game.getPlayers()) {
                    if(p.getColor().equals(Player.PlayerColor.Pink)) {
                        return p;
                    }
                }
            case "blue":
                for( Player p :game.getPlayers()) {
                    if(p.getColor().equals(Player.PlayerColor.Blue)) {
                        return p;
                    }
                }
            case "yellow":
                for( Player p :game.getPlayers()) {
                    if(p.getColor().equals(Player.PlayerColor.Yellow)) {
                        return p;
                    }
                }
            case "yelow":
                for( Player p :game.getPlayers()) {
                    if(p.getColor().equals(Player.PlayerColor.Yellow)) {
                        return p;
                    }
                }

            default:
                throw new java.lang.IllegalArgumentException("Invalid color: " + color);
        }
    }
    /**
     * 
     * TODO Swap two players in an array of players given their respective index.
     * @author Mohamad
     * @param arr of players
     * @param i index
     * @param j index
     */
    private static void swap (Player[] arr, int i, int j) {
        Player temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Partition using Lomuto partition scheme
    /**
     * 
     * TODO given an array with the start index and end index, it returns the index of the pivot.
     * @author Mohamad
     * @param a array of players
     * @param start index
     * @param end index
     * @return pivot index
     */
    private static int partition(Player[] a, int start, int end)
    {
        // Pick rightmost element as pivot from the array
        Player pivot = a[end];

        // elements less than pivot will be pushed to the left of pIndex
        // elements more than pivot will be pushed to the right of pIndex
        // equal elements can go either way
        int pIndex = start;

        // each time we finds an element less than or equal to pivot,
        // pIndex is incremented and that element would be placed
        // before the pivot.
        for (int i = start; i < end; i++)
        {
            if ((a[i].getBonusScore()+a[i].getPropertyScore()) < (pivot.getBonusScore()+pivot.getPropertyScore())) {
                swap(a, i, pIndex);
                pIndex++;
            }
            else if((a[i].getBonusScore()+a[i].getPropertyScore()) == (pivot.getBonusScore()+pivot.getPropertyScore())) {
                if(largestProperty(a[i])<largestProperty(pivot)) {
                    swap(a, i, pIndex);
                    pIndex++;
                }
                else if(largestProperty(a[i])==largestProperty(pivot)) {
                    if(totalCrowns(a[i])<=totalCrowns(pivot)) {
                        swap(a, i, pIndex);
                        pIndex++;
                    }
                }
            }
        }

        // swap pIndex with Pivot
        swap(a, end, pIndex);

        // return pIndex (index of pivot element)
        return pIndex;
    }

    // Quicksort routine
    private static void quicksort(Player[] a ,int start, int end)
    {
        // base condition
        if (start >= end) {
            return;
        }

        // rearrange the elements across pivot
        int pivot = partition(a, start, end);

        // recur on sub-array containing elements less than pivot
        quicksort(a, start, pivot - 1);

        // recur on sub-array containing elements more than pivot
        quicksort(a, pivot + 1, end);
    }
}
