
package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Violet, Cecilia
 */
public class VerificationController {

    /**
     * Feature 14: test if the current preplaced domino is next to castle.
     * @author Cecilia Jiang
     * @param castle
     * @param domino
     * @return true if adjacent to castle, false otherwise
     */
    public static boolean verifyCastleAdjacency (Castle castle, DominoInKingdom domino) {
        if(castle == null)
            throw new java.lang.IllegalArgumentException("Castle is not created");
        if(domino == null)
            throw new java.lang.IllegalArgumentException("DominoInKingdom is not created");
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];

        return (isAdjacentToCastle(castle.getX(), castle.getY(), x_left, y_left) && noOverlappingWithCastle(castle, x_right, y_right))
                || (isAdjacentToCastle(castle.getX(), castle.getY(), x_right, y_right) && noOverlappingWithCastle(castle, x_left, y_left));
    }

    /**
     * Feature 15: test if the current preplaced domino is next to a territory.
     * @author Cecilia Jiang
     * @param castle
     * @param grid
     * @param domino
     * @return true if adjacent to KingdomTerritory excluding castle, false otherwise
     */
    public static boolean verifyNeighborAdjacency(Castle castle, Square[] grid, DominoInKingdom domino) {
        if(castle == null)
            throw new java.lang.IllegalArgumentException("Castle is not created");
        if(grid == null)
            throw new java.lang.IllegalArgumentException("grid is not created");
        if(domino == null)
            throw new java.lang.IllegalArgumentException("DominoInKingdom is not created");

        ArrayList<Integer> arr1 = getAdjacentSquareIndexesLeft(castle, grid, domino);
        ArrayList<Integer> arr2 = getAdjacentSquareIndexesRight(castle, grid, domino);
        return arr1.size() > 0 || arr2.size() > 0;
    }

    public static ArrayList<Integer> getAdjacentSquareIndexesLeft(Castle castle, Square[] grid, DominoInKingdom domino){
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];
        ArrayList<Integer> result= new ArrayList<>();
        int[][] adjacentXYList = {{0,1},{0,-1},{1,0},{-1,0}};
        for (int i = 0;i < 4; i++) {
            int x_a = x_left + adjacentXYList[i][0];
            int y_a = y_left + adjacentXYList[i][1];
            if (!(x_a == x_right && y_a == y_right)) {
                int index = Square.convertPositionToInt(x_a, y_a);
                if (index >= 0 && index < 81 && isAdjacenttoGridSquare(castle, grid[index], domino.getDomino().getLeftTile()))
                    result.add(index);
            }
        }
        return result;

    }

    public static ArrayList<Integer> getAdjacentSquareIndexesRight(Castle castle, Square[] grid, DominoInKingdom domino){
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];
        ArrayList<Integer> result= new ArrayList<>();
        int[][] adjacentXYList = {{0,1},{0,-1},{1,0},{-1,0}};
        for (int i = 0;i < 4; i++) {
            int x_a = x_right + adjacentXYList[i][0];
            int y_a = y_right + adjacentXYList[i][1];
            if (!(x_a == x_left && y_a == y_left)) {
                int index = Square.convertPositionToInt(x_a, y_a);
                if (index >= 0 && index < 81 && isAdjacenttoGridSquare(castle, grid[index], domino.getDomino().getRightTile()))
                    result.add(index);
            }
        }
        return result;
    }

    /**
     * Feature 16: test if the current grid size if out of bound. It returns false if any single tile of a domino's x,y
     * coordinate is not within [-4, 4]. It also returns false when its size grows beyond 5 in either x or y direction.
     * @author Cecilia Jiang
     * @param territories
     * @return true if grid size is valid, false otherwise
     */
    public static boolean verifyGridSize(List<KingdomTerritory> territories){
        boolean result = true;
        int x_max = -10;
        int x_min = 10;
        int y_max = -10;
        int y_min = 10;

        for (KingdomTerritory territory: territories) {
                int x_left = territory.getX();
                int y_left = territory.getY();
                x_max = Math.max(x_max,x_left);
                x_min = Math.min(x_min,x_left);
                y_max = Math.max(y_max,y_left);
                y_min = Math.min(y_min,y_left);

                if (territory instanceof DominoInKingdom) {
                    int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, ((DominoInKingdom) territory).getDirection());
                    int x_right = pos_right[0];
                    int y_right = pos_right[1];
                    result = result && (x_right <=4 && x_right >= -4 && y_right <= 4 && y_right >= -4);

                    x_max = Math.max(x_max,x_right);
                    x_min = Math.min(x_min,x_right);
                    y_max = Math.max(y_max,y_right);
                    y_min = Math.min(y_min,y_right);
            }
        }

        return result && (y_max - y_min <= 4) && (x_max - x_min <= 4);
    }
    /**
     * Feature 17: Test if the preplaced domino overlaps existing castle or territory
     * @author Cecilia Jiang
     * @param castle
     * @param grid
     * @param domino
     * @return true if no overlapping, false otherwise
     */
    public static boolean verifyNoOverlapping (Castle castle, Square[] grid, DominoInKingdom domino) {
        if(castle == null)
            throw new java.lang.IllegalArgumentException("Castle is not created");
        if(grid == null)
            throw new java.lang.IllegalArgumentException("grid is not created");
        if(domino == null)
            throw new java.lang.IllegalArgumentException("DominoInKingdom is not created");
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];

        return noOverlappingWithCastle(castle, x_right, y_right)&& noOverlappingWithCastle(castle, x_left, y_left)
                && noOverlappingWithDomino(grid, x_right, y_right) &&  noOverlappingWithDomino(grid, x_left, y_left);
    }


    private static boolean isAdjacenttoGridSquare(Castle castle, Square square, TerrainType dominoTerrainType) {
        return square.getTerrain() == dominoTerrainType && square.getPosition() != Square.convertPositionToInt(castle.getX()
        ,castle.getY());
    }

    private static boolean isAdjacentToCastle(int x1, int y1, int x2, int y2) {
        return (x1 == x2 && Math.abs(y1 - y2) == 1) || (y1 == y2 && Math.abs(x1 - x2) == 1);
    }

    private static boolean noOverlappingWithCastle (Castle castle, int x, int y) {
        return !((x == castle.getX()) && (y == castle.getY()));
    }

    private static boolean noOverlappingWithDomino (Square[] grid, int x, int y) {
        int position = Square.convertPositionToInt(x,y);
        if (position < 0 || position > 80)
            throw new java.lang.IllegalArgumentException("The domino's placement does not follow grid size rule");
        return grid[position].getTerrain() == null;
    }
}