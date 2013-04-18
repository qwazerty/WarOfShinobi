package com.wos.Splash;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Content extends JPanel{
	
	public void paintComponent(Graphics g){
		
		try {
            Image splash = ImageIO.read(new File("Data/menu/splashScreen.png"));
            g.drawImage(splash, 0, 0, this);
	    } catch (IOException e) {
	            e.printStackTrace();
	    }

		Font font = new Font("Courrier", Font.BOLD, 14);
		g.setFont(font);
		g.drawString("War of Shinobi V0.0", 10, 35);
		g.drawString("Copyright Qwazerty", 10, 469);

	}

}
