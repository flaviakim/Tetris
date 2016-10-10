import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.*;
import graphics.*;

public class Main {
	
	static int windowHeight = 500;
	static int windowWidth = 250;
	
	public static void main(String[] args) {
		
		GameWindow gameWindow = new GameWindow(windowWidth, windowHeight);
		
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		
		/*
		JFrame frame = new JFrame(gameTitle);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		TetrisPanel tetrisPanel = new TetrisPanel();
		frame.add(tetrisPanel, BorderLayout.CENTER);
		
		frame.setSize(tetrisPanel.GetPlayingFieldWidth(), tetrisPanel.GetPlayingFieldHeight());
		frame.setVisible(true);
		*/
	}
	
}