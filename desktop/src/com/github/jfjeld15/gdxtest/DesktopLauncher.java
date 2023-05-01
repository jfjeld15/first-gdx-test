package com.github.jfjeld15.gdxtest;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(30);
		config.setTitle("BALLS");
		config.setResizable(false);
		config.setWindowedMode(640, 480);
		new Lwjgl3Application(new FirstGdxTest(), config);
	}
}
