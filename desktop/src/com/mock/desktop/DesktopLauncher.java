package com.mock.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mock.main.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Game.V_WIDTH = 480;
		Game.V_HEIGHT = 820;
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Game.TITLE;
		config.width = Game.V_WIDTH;
		config.height = Game.V_HEIGHT;
		new LwjglApplication(new Game(), config);
	}
}
