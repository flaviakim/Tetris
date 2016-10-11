import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.*;
import graphics.*;

// Compile all files with:
// javac $(find . -name "*.java")

public class Main {
	
	// TODO: Only set window Height and calculate window Width with the game rows and the height, so all pieces are drawn as squares.
	static int windowHeight = 500;
	static int windowWidth = 250;
	
	public static void main(String[] args) {
		
		GameWindow gameWindow = new GameWindow(windowWidth, windowHeight);
		
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		
	}
	
}