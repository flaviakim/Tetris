package graphics;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.URL;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class StartScreenPanel extends JPanel implements KeyListener, ActionListener {

    // PROPERTIES
	
	/**
	 * The parent window in which this GamePanel is in.
	 */
	private GameWindow window;
	
	private JButton playButton;
	
	
    // INITIALIZING
	
    
    /**
     * Creates the panel that creates and displays the start screen.
     * @param window The window this panel is in.
     */
    StartScreenPanel(GameWindow window) throws Exception {
        this.window = window;

        window.getContentPane().add(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        setVisible(true);
	
		setBorder(new EmptyBorder(15, 30, 15, 30));
		setBackground(Color.BLACK);
		
		playButton = new JButton();
		playButton.setBorderPainted(false);
		playButton.setMargin(new Insets(0,0,0,0));
		playButton.setContentAreaFilled(false);
		playButton.addActionListener(this);
		add(playButton);
	
		BufferedImage bi1 = ImageIO.read(getClass().getResource("/resources/images/playButton_green.png"));
		playButton.setIcon(new ImageIcon(bi1));
		BufferedImage bi2 = ImageIO.read(getClass().getResource("/resources/images/playButton_yellow.png"));
		playButton.setRolloverIcon(new ImageIcon(bi2));
		BufferedImage bi3 = ImageIO.read(getClass().getResource("/resources/images/playButton_red.png"));
		playButton.setPressedIcon(new ImageIcon(bi3));
		
        // TODO: Add more Buttons.
        
    }


    // KEYEVENTS

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                startGame();
                break;
            default:
                break;
        }
    }
    
    
    // ACTION EVENTS
	
	@Override
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == playButton) startGame();
	}
	
	
	// MISC METHODS

    private void startGame() {
        window.startGame();
    }



}