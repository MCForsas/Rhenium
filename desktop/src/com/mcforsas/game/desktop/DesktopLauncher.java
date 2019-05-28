package com.mcforsas.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;

public class DesktopLauncher {
	public static void main (String[] arg) {
		GameLauncher gameLauncher = new GameLauncher();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.foregroundFPS = gameLauncher.getFPS();
		config.width = gameLauncher.getResolutionH();
		config.height = gameLauncher.getResolutionV();
		new LwjglApplication(gameLauncher, config);
	}
}
