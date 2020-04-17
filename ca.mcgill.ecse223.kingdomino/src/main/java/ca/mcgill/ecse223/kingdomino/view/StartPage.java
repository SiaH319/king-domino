package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class StartPage extends JFrame {
	
	public StartPage() {
		
		getContentPane().setBackground(new Color(141,121,81));
		buildStartPage();
		
	}
	
	public void buildStartPage() {
		
		
		this.setSize(1440, 720);

		this.setTitle("Welcome to Kingdomino!");

		this.setBackground(Color.GRAY);

		JLabel Welcome = new JLabel("L e t ' s   P l a y");

		Welcome.setFont(new Font("Arial", Font.PLAIN, 25));

		ImageIcon Kingdomino = new ImageIcon(getClass().getResource("KingDominoStart (2).png"));

		Image image = Kingdomino.getImage();

		Image edit = image.getScaledInstance(500,  200, java.awt.Image.SCALE_SMOOTH);

		Kingdomino = new ImageIcon(edit);

		JLabel main = new JLabel("", Kingdomino, JLabel.CENTER);

		JButton StartButton = new JButton("START GAME");

		StartButton.setBackground(Color.GRAY);
	
	

	GroupLayout layout = new GroupLayout(getContentPane());
	layout.setVerticalGroup(

	layout.createParallelGroup(Alignment.LEADING)

	.addGroup(layout.createSequentialGroup()

	.addGap(200)

	.addComponent(Welcome)

	.addGap(50)

	.addComponent(main)

	.addGap(20)

	.addComponent(StartButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)

	));

	layout.setHorizontalGroup(

	layout.createParallelGroup(Alignment.LEADING)

	.addGroup(layout.createSequentialGroup()

	.addGroup(layout.createParallelGroup(Alignment.LEADING)

    .addGroup(layout.createSequentialGroup()

	.addGap(300)

	.addComponent(main, GroupLayout.PREFERRED_SIZE, 800, GroupLayout.PREFERRED_SIZE))

	.addGroup(layout.createSequentialGroup()

	.addGap(450)

	.addComponent(Welcome))

	.addGroup(layout.createSequentialGroup()

	.addGap(550)

	.addComponent(StartButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))

	));

	

	getContentPane().setLayout(layout);

	layout.setAutoCreateGaps(true);

	layout.setAutoCreateContainerGaps(true);

	

}

}
