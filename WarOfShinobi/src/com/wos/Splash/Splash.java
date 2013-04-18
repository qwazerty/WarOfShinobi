package com.wos.Splash;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Splash extends JFrame {

	public Splash(){
        this.setTitle("WarOfShinobi - Splash Screen");
        this.setSize(675, 497);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setContentPane(new Content());
        
        this.setVisible(true);
    }

}
