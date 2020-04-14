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

    public static int convertPositionToInt (int x, int y) {
        return (4 - y) * 9 + (x + 4);
    }

    /**
     * Split a domino with dstatus PlacedInKingdom into two squares and add then to Square list
     * @param domino
     * @param squares
     * @return grid positions of domino tiles
     */
    public static int[] splitPlacedDomino (DominoInKingdom domino, Square[] squares) {
    	if(domino==null) {
    		System.out.println("the dominoInKingdom is null");
    	}
        int x_left = domino.getX();
        int y_left = domino.getY();
        int pos_left = convertPositionToInt(x_left, y_left);
        squares[pos_left] = new Square(x_left, y_left,domino.getDomino().getLeftCrown(),
                domino.getDomino().getLeftTile(), domino.getDomino().getId());

        int x_right, y_right;
        DominoInKingdom.DirectionKind dir = domino.getDirection();
        int[] xy_right= DominoInKingdom.getRightTilePosition(x_left,y_left,dir);
        x_right = xy_right[0];
        y_right = xy_right[1];
        int pos_right = convertPositionToInt(x_right,y_right);
        squares[pos_right] = new Square(x_right, y_right,domino.getDomino().getRightCrown(),
                domino.getDomino().getRightTile(), domino.getDomino().getId());
        return new int[]{pos_left,pos_right};
    }


    public void setCrown (int crown) {
        this.crown = crown;
    }

    public TerrainType getTerrain() {
        return terrain;
    }
    public int getPosition() {
        return position;
    }
    public int getDominoId() {
        return dominoId;
    }
    public int getCrown() {
        return crown;
    }
}
