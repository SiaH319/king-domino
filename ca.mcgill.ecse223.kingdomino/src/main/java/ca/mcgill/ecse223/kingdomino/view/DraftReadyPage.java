package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.controller.GameplayController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DraftReadyPage extends JFrame {
    private JButton revealFirstDraft;

    public DraftReadyPage(){
        init();
    }

    private void init(){
        //Global Setting
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        setSize(1600, 900);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);
        revealFirstDraft = new JButton();
        revealFirstDraft.setText("Reveal First Draft");
        revealFirstDraft.setFont(font);

        //Action Listeners
        revealFirstDraft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revealFirstDraftActionPerformed(evt);
            }
        });

        //Create Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(revealFirstDraft));
        layout.setVerticalGroup(layout.createParallelGroup().addComponent(revealFirstDraft));
    }

    private void revealFirstDraftActionPerformed(ActionEvent evt){

        GameplayController.triggerEventsInSM("draftReady");
        this.setVisible(false);
        new SelectDominoPage().setVisible(true);
    }
}
