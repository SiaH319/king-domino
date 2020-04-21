package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.controller.TODomino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DominoQueueVisualizer extends JPanel {
    private List<DominoVisualizer> dominoVisualizers;
    private int yStart = 100;
    private int SPACING = 2;
    private int curDominoId;

    public DominoQueueVisualizer(List<TODomino> list){
        curDominoId = -1;
        dominoVisualizers = new ArrayList<>();
        for(TODomino domino: list){
            DominoVisualizer dominoVisualizer = new DominoVisualizer(domino,500,yStart);
            this.dominoVisualizers.add(dominoVisualizer);
            yStart += 200+SPACING;
        }
        init();
        repaint();
    }

    public void init(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int selectedDominoId = 0;
                for (DominoVisualizer dominoVisualizer : dominoVisualizers) {
                    if (dominoVisualizer.posy-100<y&&y<dominoVisualizer.posy+100) {
                        selectedDominoId = dominoVisualizer.getId();
                        System.out.println("Entered set selectedDomino id");
                        curDominoId = selectedDominoId;
                        System.out.println("curDomino id: "+curDominoId);
                        System.out.println("selected domino id"+selectedDominoId);
                        dominoVisualizer.setIsSelected(true);
                        break;
                    }
                }
                for (DominoVisualizer dominoVisualizer : dominoVisualizers) {
                    if (dominoVisualizer.getId() != selectedDominoId) {
                        dominoVisualizer.setIsSelected(false);
                    }
                }
                repaint();
            }
        });
    }

    private void doDrawing(Graphics g) {
        for(DominoVisualizer dominoVisualizer : dominoVisualizers){
            dominoVisualizer.doDrawing(g);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public int getCurDominoId(){
        return  curDominoId;
    }
}
