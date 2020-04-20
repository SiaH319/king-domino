package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TODomino;
import ca.mcgill.ecse223.kingdomino.controller.TOPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DominoVisualizer extends JPanel {
    private int id;
    private String leftTileType;    //Wheat,Forest,Grass,Lake,Mountain,Swamp,Wheat
    private String rightTileType;
    private int numLeftTileCrown;
    private int numRightTileCrown;
    private boolean isPressedByUser;
    private String dominoInfoString;
    private TODomino domino;

    // UI elements
    private int SQUARE_HEIGHT = 200;
    public int posx;
    public int posy;
    private String dir; //up,right,down,left
    private List<Rectangle2D> squares = new ArrayList<Rectangle2D>();
    private BufferedImage imageLeft;
    private BufferedImage imageRight;
    private int SPACING = 2;


    public DominoVisualizer(TODomino domino,int POSX, int POSY){
        super();
        init(domino,POSX,POSY);
    }

    private void init(TODomino domino,int POSX, int POSY) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for (Rectangle2D rectangle : squares) {
                    if (rectangle.contains(x, y)) {
                        isPressedByUser = true;
                        break;
                    }
                }
                repaint();
            }
        });
        this.posx = POSX;
        this.posy = POSY;
        this.dir = "right";
        getInfoFromTODomino(domino);
        setImage();
    }

    public void doDrawing(Graphics g) {
        System.out.println("Entered doDrawing");
        if (domino != null) {
            System.out.println("Entered doDrawing");
            Graphics2D g2d = (Graphics2D) g.create();

            BasicStroke thinStroke = new BasicStroke(3);
            g2d.setStroke(thinStroke);
            squares.clear();
            Rectangle2D rectangle1 = new Rectangle2D.Double(posx - SQUARE_HEIGHT/2.0, posy - SQUARE_HEIGHT / 2.0, SQUARE_HEIGHT, SQUARE_HEIGHT);
            g2d.drawImage(imageLeft,(int)rectangle1.getX(),(int)rectangle1.getY(),this);
            squares.add(rectangle1);


            //TODO: Adjust accordign to direction
            Rectangle2D rectangle2;
            rectangle2 = new Rectangle2D.Double(posx + SQUARE_HEIGHT/2.0, posy - SQUARE_HEIGHT / 2.0, SQUARE_HEIGHT, SQUARE_HEIGHT);
            squares.add(rectangle2);
            g2d.drawImage(imageRight,(int)rectangle2.getX(),(int)rectangle2.getY(),this);

            //Set Selected by which player String
            TOPlayer player = KingdominoController.getASelectedDominosPlayer(this.id);
            String selectedStr = "Selected by: ";
            if(player!=null)
                selectedStr += player.getColor();
            else
                selectedStr += "None";
            g2d.drawString(selectedStr,(int)(posx + 1.5 * SQUARE_HEIGHT), posy);


            //Set border
            if(isPressedByUser){
                Rectangle2D rectangle = new Rectangle2D.Double(posx - SQUARE_HEIGHT/2.0 - SPACING, posy - SQUARE_HEIGHT / 2.0, 2*SQUARE_HEIGHT, SQUARE_HEIGHT);
                g2d.setColor(Color.black);
                g2d.draw(rectangle);
            }
        }
    }

    public void getInfoFromTODomino(TODomino domino){
        this.domino = domino;
        this.leftTileType = domino.getLeftTileType();
        this.rightTileType = domino.getRightTileType();
        this.numLeftTileCrown = domino.getNumLeftTileCrown();
        this.numRightTileCrown = domino.getNumRightTileCrown();
        this.id = domino.getId();
        this.dominoInfoString = "Domino Id:" +this.id+" Left Tile: "+this.leftTileType+" "+this.numLeftTileCrown+
                " Right Tile: "+this.rightTileType+" "+this.numRightTileCrown;
    }

    public void setImage(){
        String leftTilePicPath = "";
        switch(this.leftTileType){
            case "Forest":
                leftTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Forest.PNG";
                break;
            case "Grass":
                leftTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Grass.PNG";
                break;
            case "Lake":
                leftTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Lake.PNG";
                break;
            case "Mountain":
                leftTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Mountain.PNG";
                break;
            case "Swamp":
                leftTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Swamp.PNG";
                break;
            case "Wheat":
                leftTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Wheat.PNG";
                break;
        }
        String rightTilePicPath = "";
        switch(this.rightTileType){
            case "Forest":
                rightTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Forest.PNG";
                break;
            case "Grass":
                rightTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Grass.PNG";
                break;
            case "Lake":
                rightTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Lake.PNG";
                break;
            case "Mountain":
                rightTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Mountain.PNG";
                break;
            case "Swamp":
                rightTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Swamp.PNG";
                break;
            case "Wheat":
                rightTilePicPath = "src/main/java/ca/mcgill/ecse223/kingdomino/view/Wheat.PNG";
                break;
        }
        try {
            imageLeft = ImageIO.read(new File(leftTilePicPath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            imageRight = ImageIO.read(new File(rightTilePicPath));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int getId(){
        return this.id;
    }

    public void setIsSelected(boolean isSelected){
        this.isPressedByUser = isSelected;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }


}
