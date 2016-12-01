import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.*;
import graphics.*;

// Compile all files with:
// javac $(find . -name "*.java")

public class Main {
	
	static int windowWidth = 250;
	
	public static void main(String[] args) {
		
		GameWindow gameWindow = new GameWindow(windowWidth);
		
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setVisible(true);
		
	}
	
}