package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.GameplayController;
import ca.mcgill.ecse223.kingdomino.controller.KingdominoController;
import ca.mcgill.ecse223.kingdomino.controller.TOUser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class CreateUserPage extends JFrame {
    //New user UI Elements
    private JLabel createUserText;
    private JTextField newUserName;
    private JButton createUserButton;
    private JLabel errorMessage;

    //Existing User Data Table UI Elements
    private JTable userTable;
    private JScrollPane userTableScrollPane;
    private DefaultTableModel userTableDtm;
    private String[] userTableColumnNames = {"Name", "PlayedGames", "WonGames"};
    private static final int HEIGHT_OVERVIEW_TABLE = 200;


    //Back to main menu button
    private JButton backButton;
    private HashMap<Integer, TOUser> existingUsers;

    public CreateUserPage(){
        init();
    }

    public void init(){
        //Global Settings
        Font font = new Font("Helvetica Neue", Font.PLAIN, 24);
        this.setSize(1600, 900);
        setTitle("Kingdomino Application");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setFont(font);

        //Init UI elements
        createUserText = new JLabel();
        createUserText.setText("Create a new user: ");
        createUserText.setFont(font);
        newUserName= new JTextField();
        newUserName.setText("");
        newUserName.setFont(font);
        createUserButton= new JButton();
        createUserButton.setText("Create");
        createUserButton.setFont(font);
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.red);
        backButton = new JButton();
        backButton.setText("Back");
        backButton.setFont(font);

        userTable = new JTable() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!c.getBackground().equals(getSelectionBackground())) {
                    Object obj = getModel().getValueAt(row, column);
                    if (obj instanceof java.lang.String) {
                        String str = (String)obj;
                        c.setBackground(str.endsWith("sick)") ? Color.RED : str.endsWith("repair)") ? Color.YELLOW : Color.WHITE);
                    }
                    else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        };
        userTable.setPreferredSize(new Dimension(1600,650));
        userTable.setRowHeight(30);
        userTable.setFont(font);
        userTableScrollPane = new JScrollPane(userTable);
        this.add(userTableScrollPane);
        Dimension d = userTable.getPreferredSize();
        userTableScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
        userTableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        userTableScrollPane.setFont(font);

        //Add Action Listeners
        // listeners for create a user
        createUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createUser(evt);
            }
        });

        backButton.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMainPage();
            }
        });

        //Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setVerticalGroup(
                layout.createSequentialGroup().
                        addGroup(layout.createParallelGroup().
                                addComponent(createUserText).
                                addComponent(newUserName).
                                addComponent(createUserButton)).
                        addComponent(errorMessage).
                        addComponent(userTableScrollPane).
                        addComponent(backButton)
        );
        layout.linkSize(SwingConstants.HORIZONTAL, createUserButton, createUserText);
        layout.setHorizontalGroup(
                layout.createParallelGroup().
                        addComponent(userTableScrollPane).
                        addGroup(layout.createSequentialGroup().
                                addComponent(createUserText).
                                addComponent(newUserName).
                                addComponent(createUserButton)).
                addComponent(errorMessage).addComponent(backButton));

        refreshUserTable();
//        pack();
    }

    public void refresh(){
        this.newUserName.setText("");
        refreshUserTable();
    }

    public void refreshUserTable(){
        userTableDtm = new DefaultTableModel(0, 0);
        userTableDtm.setColumnIdentifiers(userTableColumnNames);
        userTable.setModel(userTableDtm);

            for (TOUser item : KingdominoController.getAllTOUsers()) {
                String userName =item.getName();
                String numPlayedGame = String.valueOf(item.getPlayedGames());
                String numWonGames = String.valueOf(item.getWonGames());

                Object[] obj = {userName,numPlayedGame,numWonGames};
                userTableDtm.addRow(obj);
            }

        Dimension d = userTable.getPreferredSize();
        userTableScrollPane.setPreferredSize(new Dimension(d.width, HEIGHT_OVERVIEW_TABLE));
    }

    private void createUser(ActionEvent evt) {
        String name = this.newUserName.getText();
        System.out.println("Name: "+name);
        GameplayController.triggerCreateNewUser(name);
        System.out.println("Kingdomino Application User size: "+ KingdominoApplication.getKingdomino().getUsers().size());
        errorMessage.setText(GameplayController.getError());
        GameplayController.setError("");
        refresh();
    }

    private void backToMainPage(){
        this.setVisible(false);
        new MainMenuPage().setVisible(true);
    }
}
