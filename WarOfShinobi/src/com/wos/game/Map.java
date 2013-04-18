package com.wos.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;



public class Map {
	
	private static Image map = null;
	private static float x	 = 0;
	private static float y	 = 0;
	
	public static void Init() throws SlickException
	{
		map = new Image("Data/map/img/1-1.png");
	}
	
	public static void Draw(Graphics g)
	{
		map.draw(x,y);
	}
	
	public static void Scrolling(int delta, int dir)
	{
		if (dir == Game.BAS)
			y -= delta * Player.SPEED * 0.8;
		
		if (dir == Game.GAUCHE)
			x += delta * Player.SPEED * 0.8;
		
		if (dir == Game.DROITE)
			x -= delta * Player.SPEED * 0.8;
		
		if (dir == Game.HAUT)
			y += delta * Player.SPEED * 0.8;
	}
	
	public static Image getImage()
	{
		return map;
	}
	
	public static int getX()
	{
		return (int) x;
	}

	public static int getY()
	{
		return (int) y;
	}
}
