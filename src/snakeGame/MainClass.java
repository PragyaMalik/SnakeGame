package snakeGame;

import java.awt.Color;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		GamePanel gamePanel = new GamePanel();
		
		frame.setBounds(500, 100,500, 500);
		frame.setBackground(Color.GRAY);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gamePanel);
	}
}
