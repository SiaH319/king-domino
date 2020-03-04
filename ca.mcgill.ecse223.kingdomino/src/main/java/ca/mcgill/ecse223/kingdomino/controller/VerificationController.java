package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

/**
 * @author Violet, Cecilia
 */
public class VerificationController {

    public static boolean verifyCastleAjacency (Castle castle, DominoInKingdom domino) {
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
    public static boolean verifyNeighborAdjacency(Square[] grid, DominoInKingdom domino) {
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];

        int[][] adjacentXYList = {{0,1},{0,-1},{1,0},{-1,0}};
        boolean result = false;
        for (int i = 0;i < 4; i++) {
            int x_a = x_left + adjacentXYList[i][0];
            int y_a = x_left + adjacentXYList[i][0];
            if(!(x_a==x_right && y_a == y_right)){
                int index = Square.convertPositionToInt(x_a, y_a);
                result = result || isAdjacenttoGridSquare(grid[index], domino.getDomino().getLeftTile());
            }
        }

        for (int i = 0;i < 4; i++) {
            int x_a = x_right + adjacentXYList[i][0];
            int y_a = x_right + adjacentXYList[i][0];
            if(!(x_a==x_left && y_a == y_left)){
                int index = Square.convertPositionToInt(x_a, y_a);
                result = result || isAdjacenttoGridSquare(grid[index], domino.getDomino().getRightTile());
            }
        }
        return result;
    }

    public static boolean verifyNoOverlapping (Castle castle, Square[] grid, DominoInKingdom domino) {
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];

        return noOverlappingWithCastle(castle, x_right, y_right)&& noOverlappingWithCastle(castle, x_left, y_left)
                && noOverlappingWithDomino(grid, x_right, y_right) &&  noOverlappingWithDomino(grid, x_left, y_left);
    }

    //TODO: 所以老宅的位置和新来的domino贴着的话是算还是不算
    private static boolean isAdjacenttoGridSquare(Square square, TerrainType dominoTerrainType) {
        return square.getTerrain()==dominoTerrainType;
    }

    private static boolean isAdjacentToCastle(int x1, int y1, int x2, int y2){
        return (x1 == x2 && Math.abs(y1 - y2) == 1)||(y1 == y2 && Math.abs(x1 - x2) == 1);
    }

    private static boolean noOverlappingWithCastle (Castle castle, int x, int y) {
        return !((x == castle.getX()) && (y == castle.getY()));
    }

    private static boolean noOverlappingWithDomino (Square[] grid, int x, int y) {
        return grid[Square.convertPositionToInt(x,y)].getTerrain() == null;
    }
}