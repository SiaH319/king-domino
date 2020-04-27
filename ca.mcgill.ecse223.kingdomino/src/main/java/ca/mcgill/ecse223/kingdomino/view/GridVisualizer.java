package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GridVisualizer extends JPanel {
    private int posx = 0;
    private int posy = 50;
    private int SQUARE_HEIGHT = 150;
    private int y_max = 2;
    private int y_min = -2;
    private int x_max = 2;
    private int x_min = -2;
    private int SMALL_SQUARE_SIZE = 50;
    private double currentPosRectX = posx+2.5*150- SMALL_SQUARE_SIZE/2;
    private double currentPosRectY = posy+2.5*150- SMALL_SQUARE_SIZE/2;

    private HashMap<Integer,String> squareTerrainTypes = new HashMap<>();
    private ArrayList<Rectangle2D> squareList = new ArrayList<>();
    private Rectangle2D currentPosRect;

    public GridVisualizer(Square[] grid){
        for(int y = 2; y >= -2; y--)
            for(int x = -2; x <= 2; x++){
                int index = Square.convertPositionToInt(x,y);
                squareTerrainTypes.put(index, KingdominoController.getSquareTerrainTypeInString(grid[index]));
            }
        currentPosRect = new Rectangle2D.Double(currentPosRectX, currentPosRectY, SMALL_SQUARE_SIZE, SMALL_SQUARE_SIZE);
        //this.setBounds(0,0,0,0);
    }

    public void setGrid(Square[] grid){
        for(int y = 2; y >= -2; y--)
            for(int x = -2; x <= 2; x++){
                int index = Square.convertPositionToInt(x,y);
                squareTerrainTypes.put(index, KingdominoController.getSquareTerrainTypeInString(grid[index]));
            }
    }

    public void init(){

    }

    private BufferedImage getImage(String terrainType){
        String picPath = "";
        BufferedImage image;
        switch(terrainType){
            case "Forest":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Forest.PNG";
                break;
            case "Grass":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Grass.PNG";
                break;
            case "Lake":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Lake.PNG";
                break;
            case "Mountain":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Mountain.PNG";
                break;
            case "Swamp":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Swamp.PNG";
                break;
            case "Wheat":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Wheat.PNG";
                break;
            case "Castle":
                picPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Castle.jpg";
                break;
        }
        try {
            image = ImageIO.read(new File(picPath));
            return image;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean moveUp(){
        if(this.y_max == 4) return false;
        this.y_max +=1;
        this.y_min +=1;
        repaint();
        return true;
    }

    public boolean moveDown(){
        if(this.y_min == -4) return false;
        this.y_max -=1;
        this.y_min -=1;
        repaint();
        return true;
    }

    public boolean moveLeft(){
        if(this.x_min == -4) return false;
        this.x_max -=1;
        this.x_min -=1;
        repaint();
        return true;
    }

    public boolean moveRight(){
        if(this.x_max == 4) return false;
        this.x_max +=1;
        this.x_min +=1;
        repaint();
        return true;
    }

    public boolean moveUpCursor(){
        if(this.y_max == 4) return false;
        this.currentPosRectY -= 150;
        repaint();
        return true;
    }

    public boolean moveDownCursor(){
        if(this.y_min == -4) return false;
        this.currentPosRectY += 150;
        repaint();
        return true;
    }

    public boolean moveLeftCursor(){
        if(this.x_min == -4) return false;
        this.currentPosRectX -= 150;
        repaint();
        return true;
    }

    public boolean moveRightCursor(){
        if(this.x_max == 4) return false;
        this.currentPosRectX += 150;
        repaint();
        return true;
    }


    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.BLACK);
        for(int y = y_max; y >= y_min;y--)
        for(int x = x_min; x <= x_max;x++){
            String path = squareTerrainTypes.get(Square.convertPositionToInt(x,y));
            if(x==0 && y ==0)
                path = "Castle";
            Rectangle2D rectangle1 = new Rectangle2D.Double(posx+1+(x-x_min)*150, posy+1+(y_max-y)*150, SQUARE_HEIGHT, SQUARE_HEIGHT);
            if (path != "/") {
                BufferedImage image = getImage(path);
                g2d.drawImage(image,(int)rectangle1.getX(),(int)rectangle1.getY(),this);
                squareList.add(rectangle1);
            }else{
                g2d.drawRect((int)rectangle1.getX(),(int)rectangle1.getY(),SQUARE_HEIGHT,SQUARE_HEIGHT);
            }

        }
        g2d.setColor(Color.red);
        g2d.fillRect((int)currentPosRectX,(int)currentPosRectY,SMALL_SQUARE_SIZE,SMALL_SQUARE_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

}
