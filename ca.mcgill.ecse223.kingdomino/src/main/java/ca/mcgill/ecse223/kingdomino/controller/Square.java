package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;

public class Square {
    private TerrainType terrain;
    private int position;
    private int crown;
    private int dominoId;

    public Square (int x, int y) {
        this.terrain = null;
        this.position = convertPositionToInt(x, y);
        this.crown = 0;
        this.dominoId = -1;
    }

    public Square(int x, int y, int crown, TerrainType terrain, int dominoId){
        this.terrain = terrain;
        this.position = convertPositionToInt(x, y);
        this.crown = crown;
        this.dominoId = dominoId;
    }

    public int[] convertPositionFromInt(int position){
        int[] pos = new int[2];
        pos[0] = position % 10;
        pos[1] = position / 10;
        return pos;
    }

    public int convertPositionToInt (int x, int y) {
        return (4 - y) * 9 + x;
    }

    public void splitPlacedDomino (DominoInKingdom domino, Square[] squares) {
        int x_left = domino.getX();
        int y_left = domino.getY();
        int pos_left = convertPositionToInt(x_left, y_left);
        squares[pos_left] = new Square(x_left, y_left,domino.getDomino().getLeftCrown(),
                domino.getDomino().getLeftTile(), domino.getDomino().getId());

        int x_right, y_right;
        DominoInKingdom.DirectionKind dir = domino.getDirection();
        switch (dir){
            case Down:
                x_right = x_left;
                y_right = y_left - 1;
                break;
            case Left:
                x_right = x_left - 1;
                y_right = y_left;
                break;
            case Right:
                x_right = x_left + 1;
                y_right = y_left;
                break;
            case Up:
                x_right = x_left;
                y_right = y_left + 1;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dir);
        }
        int pos_right = convertPositionToInt(x_right,y_right);
        squares[pos_right] = new Square(x_right, y_right,domino.getDomino().getRightCrown(),
                domino.getDomino().getRightTile(), domino.getDomino().getId());
    }

    /**
     * Setter for int crown
     * @param crown
     */
    public void setCrownNumber (int crown) {
        this.crown = crown;
    }
}
