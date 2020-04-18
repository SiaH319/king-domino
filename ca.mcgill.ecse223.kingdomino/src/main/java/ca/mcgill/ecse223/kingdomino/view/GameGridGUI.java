import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;

/**
 * @author Violet 
 *
 * This class contains the layout of the Kingdomino board grid
 * and the Place Domino button, Move Domino button
 * Rotate Domino button, Dicard Domino button
 * the Next button
 */
public class GameGridGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGridGUI window = new GameGridGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameGridGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(600, 600, 650, 700);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton b1=new JButton();  
		
	    JButton b2=new JButton();
	    
	    JButton b3=new JButton();  
	    
	    JButton b4=new JButton();  
	    
	    JButton b5=new JButton();  
	    
	    JButton b6=new JButton();  
	   
	    JButton b7=new JButton();  
	    
	    JButton b8=new JButton();  
	    
	    JButton b9=new JButton();  
	    
	    JButton b10=new JButton();  
		
	    JButton b11=new JButton();  
	    
	    JButton b12=new JButton();  
	    
	    JButton b13=new JButton();  
	    
	    JButton b14=new JButton();  
	    
	    JButton b15=new JButton();  
	    
	    JButton b16=new JButton();  
	    
	    JButton b17=new JButton();  
	    
	    JButton b18=new JButton();  
	    
	    JButton b19=new JButton();  
	   
	    JButton b20=new JButton(); 
	    JButton b21=new JButton();  
	    
	    JButton b22=new JButton();  
	    
	    JButton b23=new JButton();  
	    
	    JButton b24=new JButton(); 
	    
	    JButton b25=new JButton();
	    frame.getContentPane().setLayout(new GridLayout(0, 5, 0, 0));

	    
	          
	    frame.getContentPane().add(b1);
	    frame.getContentPane().add(b2);
	    frame.getContentPane().add(b3);
	    frame.getContentPane().add(b4);
	    frame.getContentPane().add(b5);  
	    frame.getContentPane().add(b6);
	    frame.getContentPane().add(b7);
	    frame.getContentPane().add(b8);
	    frame.getContentPane().add(b9);
	    frame.getContentPane().add(b10);
	    frame.getContentPane().add(b11);
	    frame.getContentPane().add(b12);
	    frame.getContentPane().add(b13);
	    frame.getContentPane().add(b14);  
	    frame.getContentPane().add(b15);
	    frame.getContentPane().add(b16);
	    frame.getContentPane().add(b17);
	    frame.getContentPane().add(b18);
	    frame.getContentPane().add(b19);
	    frame.getContentPane().add(b20);
	    frame.getContentPane().add(b21);  
	    frame.getContentPane().add(b22);
	    frame.getContentPane().add(b23);
	    frame.getContentPane().add(b24);
	    frame.getContentPane().add(b25);
	    
	    JButton btnNewButton = new JButton("Place Domino");
	    btnNewButton.setBackground(Color.ORANGE);
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		// Add controller - Place domino
	    	}
	    });
	    frame.getContentPane().add(btnNewButton);
	    
	    JButton btnNewButton_1 = new JButton("Move");
	    btnNewButton_1.setBackground(Color.PINK);
	    btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		// Add controller - Move
	    	}
	    });
	    frame.getContentPane().add(btnNewButton_1);
	    
	    JButton btnNewButton_2 = new JButton("Rotate");
	    btnNewButton_2.setBackground(Color.CYAN);
	    btnNewButton_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		// Add controller - Rotate
	    	}
	    });
	    frame.getContentPane().add(btnNewButton_2);
	    
	    JButton btnNewButton_3 = new JButton("Discard");
	    btnNewButton_3.setBackground(Color.MAGENTA);
	    btnNewButton_3.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		// Add controller - Discard
	    	}
	    });
	    frame.getContentPane().add(btnNewButton_3);
	    
	    JButton btnNewButton_4 = new JButton("Next");
	    btnNewButton_4.setBackground(Color.GRAY);
	    btnNewButton_4.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		// Add controller - Next
	    	}
	    });
	    frame.getContentPane().add(btnNewButton_4);
	    
	   
	    
	 
	    
	    frame.setSize(500,500);  
	    frame.setVisible(true);  
		
		
		/*JButton btnNewButtonNext = new JButton("Next");
		btnNewButtonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// todo link to the controller 
			}
			
		});
		btnNewButtonNext.setBounds(134, 65, 93, 23);
		frame.getContentPane().add(btnNewButtonNext, BorderLayout.SOUTH);
		
		JButton btnNewButtonPlace = new JButton("Place");
		btnNewButtonPlace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// todo link to the controller - place domino
			}
			
		});
		btnNewButtonNext.setBounds(134, 65, 93, 23);
		frame.getContentPane().add(btnNewButtonPlace, BorderLayout.NORTH);
		*/
	}

}
