package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JLabel;



public class GameInformationPanel extends JPanel {
	
	// PROPERTIES
	
	final GameWindow window;
	JLabel scoreLabel;
	
	
	// INITIALISATION
	
	public GameInformationPanel (GameWindow window) {
		this.window = window;
		
		setPreferredSize(new Dimension((int)window.getSize().getWidth(), window.gameInfoPanelHeight));
        
        setVisible(true);
		
		addScoreLabel();
		
		System.out.println("GameInformationPanel created!");
	}
	
	
	// METHODS
	
	void addScoreLabel () {
    	scoreLabel = new JLabel ("Score: 0", javax.swing.SwingConstants.LEFT);
        //scoreLabel.setBackground(new Color (200, 200, 200, 100) );
        scoreLabel.setVisible(true);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(0, 10, (int)this.getSize().getWidth(), 40);
        this.add(scoreLabel);
    }
    
	
	public void paintComponent (Graphics g) {
        super.paintComponent(g);
		drawTopLine(g);
		updateScoreLabel(g);
    }
    
    void drawTopLine (Graphics g) {
    	g.setColor(Color.BLACK);
    	g.fillRect(0, 0, (int)this.getSize().getWidth(), 2);
    }
    
    void updateScoreLabel (Graphics g) {
    	scoreLabel.setText("Score: " + window.getGamePanel().getGame().getCurrentScore() + 
    			 	     "\nLevel: " + window.getGamePanel().getGame().getLevel());
    }
    
    
	
}






