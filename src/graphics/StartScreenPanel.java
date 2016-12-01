package graphics;


import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

import java.net.URL;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class StartScreenPanel extends JPanel implements KeyListener, ActionListener {

    // PROPERTIES
    
	static final float[] speeds = {1f, 2f, 4f};
	
	/**
	 * The parent window in which this GamePanel is in.
	 */
	final GameWindow window;
	
	JButton[] playButtons;
	
	JButton playButtonEasy;
	JButton playButtonMedium;
	JButton playButtonHard;
	
	
    // INITIALIZING
	
    
    /**
     * Creates the panel that creates and displays the start screen.
     * @param window The window this panel is in.
     */
    StartScreenPanel(GameWindow window) throws Exception {
        this.window = window;

        //window.getContentPane().add(this);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
	
		setBorder(new EmptyBorder(15, 30, 15, 30));
		setBackground(Color.BLACK);
		
		playButtons = new JButton[3];
		BufferedImage[] playImages = new BufferedImage[3];
		playImages[0] = ImageIO.read(getClass().getResource("/resources/images/playButton_green.png"));
		playImages[1] = ImageIO.read(getClass().getResource("/resources/images/playButton_yellow.png"));
		playImages[2] = ImageIO.read(getClass().getResource("/resources/images/playButton_red.png"));
		
		for (int i = 0; i < playButtons.length; i++) {
			playButtons[i] = new JButton();
			playButtons[i].setBorderPainted(false);
			playButtons[i].setMargin(new Insets(0,0,0,0));
			playButtons[i].setContentAreaFilled(false);
			playButtons[i].addActionListener(this);
			add(playButtons[i]);
			playButtons[i].setIcon(new ImageIcon(playImages[i]));
		}
		
        // TODO: Add Button/Checkbox for enabling/disabling dropped Shape.
        
        repaint();
        
    }


    // KEYEVENTS

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_1:
                startGame(speeds[0]);
                break;
            case KeyEvent.VK_2:
                startGame(speeds[1]);
                break;
            case KeyEvent.VK_3:
                startGame(speeds[2]);
                break;
            case KeyEvent.VK_SPACE:
                // TODO: enable/disable dopped Shape. 
                break;
            default:
                break;
        }
    }
    
    
    // ACTION EVENTS
	
	@Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == playButtons[0]) {
			startGame(speeds[0]);
		} else
		if (e.getSource() == playButtons[1]) {
			startGame(speeds[1]);
		} else
		if (e.getSource() == playButtons[2]) {
			startGame(speeds[2]);
		}
		
	}
	
	
	
	
	// MISC METHODS

    private void startGame(float speed) {
        window.startGame(speed);
    }



}