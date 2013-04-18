package com.wos.game;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;



public class Game extends BasicGame{
 
	public static final int BAS 	= 2;
	public static final int GAUCHE 	= 4;
	public static final int DROITE 	= 6;
	public static final int HAUT 	= 8;

    public Game()
    {
        super("WarOfShinobi - Game");
    }
 
    @Override
    public void init(GameContainer gc) throws SlickException {
    	gc.setShowFPS(true);
    	
    	Network.Init();
    	Map.Init();
    	Parser.Init();
    	Chat.init(gc);
    }
 
    public void update(GameContainer gc, int delta)
			throws SlickException
    {
        Player.UpdateNet(delta);
        Player.UpdateInput(gc, delta);
        Chat.update(gc, delta);
    }
 
    public void render(GameContainer gc, Graphics g)
			throws SlickException
    {
        Map.Draw(g);
        Player.DrawNet(g);
        Chat.render(gc, g);
    }
    
}