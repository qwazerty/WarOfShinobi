package com.wos.game;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;



public class Player {
	
	public static final float SPEED = 0.1f;
	private static Image	perso  	= null;
	private static Input	input 	= null;
	private static float 	x;
    private static float 	y;
    private static float	speedX;
    private static float 	speedY;
    private static int		clipX;
    private static int		clipY;
    private static int 		delay	= 0;
    private static boolean	scroll 	= false;
    private static boolean 	isValid;
    @SuppressWarnings("rawtypes")
	private static ArrayList array;
    private static char parse;
    
    // PUBLIC METHOD
    public static void Init(ArrayList<?> arr) throws SlickException
    {
    	array = arr;
    	perso = new Image("Data/perso/1/1.png");
    	int i = 2;
    	while (i < array.size() && (Integer) array.get(i) != Network.getID()) {
    		i += 6;
    	}
    	x = (Integer) array.get(i+1);
    	y = (Integer) array.get(i+2);
    }
    
	public static void UpdateInput(GameContainer gc, int delta)
    {
    	input = gc.getInput();
    	
    	speedX = 0f;
    	speedY = 0f;
    	isValid = false;
    	
    	if(input.isKeyDown(Input.KEY_Q))
        {
    		speedX = -SPEED;
    		isValid = IsValidMove();
    		
    		if (x < Map.getX())
        	{
        		scroll = true;
        	}
    		else if (x < gc.getScreenWidth() * 0.1  && isValid)
    		{
				Map.Scrolling(delta, Game.GAUCHE);
				scroll = true;
    		}
        }
 
        if(input.isKeyDown(Input.KEY_D) && !scroll)
        {        	
        	speedX = SPEED;
        	isValid = IsValidMove();
        	
        	if (x > Map.getX() + Map.getImage().getWidth() - perso.getWidth() / 4)
        	{
        		scroll = true;
        	}
        	else if (x > gc.getScreenWidth() * 0.6 && isValid)
        	{
				Map.Scrolling(delta, Game.DROITE);
				scroll = true;
        	}
        }
        
    	if(input.isKeyDown(Input.KEY_Z) && !scroll)
        {
    		speedX = 0f;
            speedY = -SPEED;
            isValid = IsValidMove();
            
    		if (y + 20 < Map.getY())
        	{
        		scroll = true;
        	}
    		else if (y < gc.getScreenHeight() * 0.1 && isValid)
    		{
				Map.Scrolling(delta, Game.HAUT);
				scroll = true;
    		}
        }
 
        if(input.isKeyDown(Input.KEY_S) && !scroll)
        {
        	speedX = 0f;
            speedY = SPEED;
            isValid = IsValidMove();
            
        	if (y > Map.getY() + Map.getImage().getHeight() - perso.getHeight() / 4)
        	{
        		scroll = true;
        	}
        	else if (y > gc.getScreenHeight() * 0.6 && isValid)
        	{
				Map.Scrolling(delta, Game.BAS);
				scroll = true;
        	}
        }
        
        if(!scroll && isValid)
        {
        	x += delta * speedX;
            y += delta * speedY;
        }
        
        scroll = false;
        
        Clip(delta);
    }

    @SuppressWarnings("unchecked")
	public static void UpdateNet(int delta)
    {
		array = Network.getPosition();
    	if (!Network.getReceived())
		{
			try {
				for (int i = 1 ; i <= (Integer)array.get(1) ; i++)
		    	{
			    	array.set(i*6 - 3, (Integer)array.get(i*6 - 3) + delta * (Integer)array.get(i*6 - 1));
			    	array.set(i*6 - 2, (Integer)array.get(i*6 - 2) + delta * (Integer)array.get(i*6));
		    	}
				Network.setPosition(array);
				Network.setReceived();
			} 
			catch (NullPointerException e) { e.printStackTrace(); }
			catch (IndexOutOfBoundsException e) { e.printStackTrace(); }
		}
    }
    
	public static void DrawNet(Graphics g)
    {
    	try {
    		array = Network.getPosition();
	    	for (int i = 1 ; i <= (Integer)array.get(1) ; i++)
	    	{
		    	g.setClip((Integer)array.get(i*6 - 3) + Map.getX(), (Integer)array.get(i*6 - 2) + Map.getY(), 32, 48);
		    	perso.draw((Integer)array.get(i*6 - 3) - ArrayToClipX((Integer)array.get(i*6 + 1)) + Map.getX(),
		    			(Integer)array.get(i*6 - 2) - ArrayToClipY((Integer)array.get(i*6 + 1)) + Map.getY());
		    	g.clearClip();
	    	}
    	}
    	catch (IndexOutOfBoundsException e) { }
    	catch (NullPointerException e) { }
    }
    
    public static int getX()
    {
    	return (int)x;
    }
    
    public static int getY()
    {
    	return (int)y;
    }
    
    public static float getSpeedX()
    {
    	return speedX;
    }
    
    public static float getSpeedY()
    {
    	return speedY;
    }
    
    public static int ClipToArray()
    {
    	return (clipX/32 * 10 + clipY/48);
    }
    
    // PRIVATE METHOD
    private static int ArrayToClipX(int clip)
    {
    	return ((clip/10) * 32);
    }
    
    private static int ArrayToClipY(int clip)
    {
    	return ((clip%10) * 48);
    }
    
    private static void Clip(int delta)
    {
    	
    	if(speedY > 0 && clipY != 0)
    	{
    		clipY = 0;
    	}
    	
    	if(speedX < 0 && clipY != 48)
    	{
    		clipY = 48;
    	}
    	
    	if(speedX > 0 && clipY != 96)
    	{
    		clipY = 96;
    	}
    	
    	if(speedY < 0 && clipY != 144)
    	{
    		clipY = 144;
    	}
    	
    	delay += delta;
    	
    	if (delay > 200)
    	{
    		if (speedX != 0 || speedY != 0)
    		{
	    		clipX = (clipX + 32) % 128;
	    		delay = 0;
    		}
    		else
    		{
    			clipX = 0;
    		}
    	}
    }
    
    private static boolean IsValidMove()
    {
    	try 
    	{
    		return !((speedX > 0 && (TestValid(44, 27) || TestValid(22, 27))) ||
    				(speedX < 0 && (TestValid(44, 5)  || TestValid(22, 5)))  ||
	    			(speedY > 0 && (TestValid(47, 7)  || TestValid(47, 25))) ||
	    			(speedY < 0 && (TestValid(20, 7)  || TestValid(20, 25))));
			
    	} catch (IndexOutOfBoundsException e) {
    		return false;
    	}
    }
    
    private static boolean TestValid(int offsetY, int offsetX)
    {
    	parse = Parser.getArray().get((int)(y - Map.getY() + offsetY + 32) / 32).charAt(((int)(x - Map.getX() + offsetX) / 32));
    	return (parse == '1') || (parse == 'e' && !input.isKeyDown(Input.KEY_SPACE));
    }
}
