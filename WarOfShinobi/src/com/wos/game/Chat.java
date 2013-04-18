package com.wos.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Chat {

	private static String message;
	private static int deltaTotal;
	private static Input input;
	private static int x,i;
	private static boolean focused;
	
	public static void init(GameContainer container) throws SlickException {
		message = "";
		deltaTotal = 0;
		focused = false;
	}

	public static void render(GameContainer container, Graphics g) {
		g.setColor(Color.black);
        g.drawString(message, 42, 642);
        i = 1;
        while (i < Network.getChat().size()) {
        	g.drawString(Network.getChat().get(i) + " : " + Network.getChat().get(i + 1), 42, 100 + 10 * i);
        	i += 2;
        }
	}

	public static void update(GameContainer c, int delta) {
		input = c.getInput();
		if (focused) {
			x = 0;
			i = 14;
			if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				focused = false;
				x = -1;
				message = message.substring(0, message.length() - 1);
			}
			
			while (i <= 57 && x == 0) {
				if (input.isKeyDown(i)) 
				{
					//deltaTotal += delta;
					if (input.isKeyPressed(i) || deltaTotal > 1000)
					{
						if (input.isKeyPressed(i))
							deltaTotal = 0;
						
						x = slickToAscii(i);
						if (x != 0) {
							message = message.substring(0, message.length() - 1);
							message += (char) x + "|";
						}
						else 
						{
							x = 42;
							if (message.length() > 1 && i == 14) {
								message = message.substring(0, message.length() - 2);
								message += "|";
							}
							else if (i == 28) {
								message = message.substring(0, message.length() - 1);
								Network.AddChat(message);
								message = "";
								focused = false;
							}
						}
					}
				}
				i++;
			}
		}
		else if (input.isKeyPressed(Input.KEY_ENTER)) {
			focused = true;
			message += "|";
		}
	}

	public static int slickToAscii(int key) {
		int ascii = 0;
		switch(key) {
			case 30: ascii = 'a'; break;
			case 48: ascii = 'b'; break;
			case 46: ascii = 'c'; break;
			case 32: ascii = 'd'; break;
			case 18: ascii = 'e'; break;
			case 33: ascii = 'f'; break;
			case 34: ascii = 'g'; break;
			case 35: ascii = 'h'; break;
			case 23: ascii = 'i'; break;
			case 36: ascii = 'j'; break;
			case 37: ascii = 'k'; break;
			case 38: ascii = 'l'; break;
			case 50: ascii = 'm'; break;
			case 49: ascii = 'n'; break;
			case 24: ascii = 'o'; break;
			case 25: ascii = 'p'; break;
			case 16: ascii = 'q'; break;
			case 19: ascii = 'r'; break;
			case 31: ascii = 's'; break;
			case 20: ascii = 't'; break;
			case 22: ascii = 'u'; break;
			case 47: ascii = 'v'; break;
			case 17: ascii = 'w'; break;
			case 45: ascii = 'x'; break;
			case 21: ascii = 'y'; break;
			case 44: ascii = 'z'; break;
			case 57: ascii = ' '; break;
			default: ascii = 0;   break;
		}
		return ascii;
	}

}