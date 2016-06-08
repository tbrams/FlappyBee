package dk.brams.flappybee.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import dk.brams.flappybee.FlappyBeeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=240;
		config.height=320;
		new LwjglApplication(new FlappyBeeGame(), config);
	}
}
