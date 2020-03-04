package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;

/**
 * @author Violet, Cecilia
 */
public class VerificationController {

    public static Boolean verifyCastleAjacency (Castle castle, DominoInKingdom domino) {
        if(castle == null)
            throw new java.lang.IllegalArgumentException("Castle is not created");
        if(domino == null)
            throw new java.lang.IllegalArgumentException("DominoInKingdom is not created");
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];

        return (isAdjacent(castle.getX(), castle.getY(), x_left, y_left) && noOverlappingWithCastle(castle, x_right, y_right))
                || (isAdjacent(castle.getX(), castle.getY(), x_right, y_right) && noOverlappingWithCastle(castle, x_left, y_left));
    }

    public static Boolean verifyNoOverlapping (Castle castle, Square[] grid, DominoInKingdom domino) {
        int x_left = domino.getX();
        int y_left = domino.getY();

        int[] pos_right = DominoInKingdom.getRightTilePosition(x_left, y_left, domino.getDirection());
        int x_right = pos_right[0];
        int y_right = pos_right[1];

        return noOverlappingWithCastle(castle, x_right, y_right)&& noOverlappingWithCastle(castle, x_left, y_left)
                && noOverlappingWithDomino(grid, x_right, y_right) &&  noOverlappingWithDomino(grid, x_left, y_left);
    }

    private static Boolean isAdjacent(int x1, int y1, int x2, int y2){
        return (x1 == x2 && Math.abs(y1 - y2) == 1)||(y1 == y2 && Math.abs(x1 - x2) == 1);
    }

    private static Boolean noOverlappingWithCastle (Castle castle, int x, int y) {
        return !((x == castle.getX()) && (y == castle.getY()));
    }

    private static Boolean noOverlappingWithDomino (Square[] grid, int x, int y) {
        return grid[Square.convertPositionToInt(x,y)].getTerrain() == null;
    }
}