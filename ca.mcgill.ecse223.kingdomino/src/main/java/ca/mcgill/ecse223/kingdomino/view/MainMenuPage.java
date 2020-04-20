package ca.mcgill.ecse223.kingdomino.view;

/**
 * Main Menu Page
 * @authr Cecilia Jiang
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainMenuPage extends JFrame {
    private static final long serialVersionUID = -4426310869335015542L;

    //////////////////////////////////////////////
    ///////////UI Elements///////////////////////
    ////////////////////////////////////////////
    private JButton createNewUser;
    private JButton startAGame;
    private JButton loadGame;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel upPanel;
    private JLabel pageTitle;
    //Class constructor
    public MainMenuPage(){
        initComponents();
    }

    public void initComponents(){
        int border = 100;
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        Font font2 = new Font("Handwriting - Dakota", Font.PLAIN, 40);
        //Init UI Texts
        createNewUser = new JButton();
        createNewUser.setText("User stats");
        createNewUser.setFont(font);
        startAGame = new JButton();
        startAGame.setText("Start A New Game");
        startAGame.setFont(font);
        loadGame = new JButton();
        loadGame.setText("Load");
        loadGame.setFont(font);
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(40, 50));
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(40, 50));
        upPanel = new JPanel();
        upPanel.setPreferredSize(new Dimension(40, 10));
        pageTitle = new JLabel();
        pageTitle.setText("Main Menu");
        pageTitle.setFont(font2);
        //Set Default
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kingdomino Application");
        setSize(1600, 900);

        //Add Action Listeners
        // listeners for create new user button
        createNewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initCreateUserPage(evt);
            }
        });

        //Listener for create new game button
        startAGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initCreateNewGamePage(evt);
            }
        });

        //SetLayOut
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(upPanel)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(leftPanel)
                                .addGroup(layout.createSequentialGroup().
                                        addComponent(pageTitle).
                                        addComponent(createNewUser).
                                        addComponent(startAGame).
                                        addComponent(loadGame))
                                .addComponent(rightPanel))


        );

        layout.setHorizontalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup()
                                .addComponent(upPanel)
                                .addComponent(leftPanel)).
                        addGroup(layout.createParallelGroup().
                                addComponent(pageTitle).
                                addComponent(createNewUser).
                                addComponent(startAGame).
                                addComponent(loadGame))
                .addComponent(rightPanel)

        );
    }

    private void initCreateUserPage(ActionEvent evt) {
        this.setVisible(false);
        new CreateUserPage().setVisible(true);
    }

    private void initCreateNewGamePage(ActionEvent evt) {
        this.setVisible(false);
        new CreateNewGamePage().setVisible(true);
    }

}
