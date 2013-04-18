import com.wos.Splash.Splash;
import com.wos.game.Game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class WarOfShinobi {

	public static void main(String[] args) throws SlickException {
		//*
		Splash splash = new Splash();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		splash.dispose();
		//*/
		
		AppGameContainer app =
			new AppGameContainer( new Game() );
 
         app.setDisplayMode(1024, 700, false);
         app.start();
         
	}

}
