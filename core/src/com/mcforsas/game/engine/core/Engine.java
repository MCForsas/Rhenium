package com.mcforsas.game.engine.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.engine.handlers.*;

import java.util.NoSuchElementException;

/**
 * @author MCForsas @since 3/16/2019
 * Main game class holds all game constants, main game loop and handlers
 */

public class Engine extends ApplicationAdapter {
	//Constants
	protected static int FPS;
	protected static float WORLD_WIDTH, WORLD_HEIGHT;
	protected static int RESOLUTION_H = 1280, RESOLUTION_V = 720;

	//Main handlers
	protected static RenderHandler renderHandler;
	protected static LevelHandler levelHandler;
	protected static AssetHandler assetHandler;
	protected static InputHandler inputHandler;
	protected static GameData gameData;
	protected static FileHandler fileHandler;

	protected SpriteBatch spriteBatch;

	public Engine(){
		FPS = 60;
		WORLD_WIDTH = 320;
		WORLD_HEIGHT = 180;
	}

	@Override
	public void create () {
		renderHandler = new RenderHandler();
		levelHandler = new LevelHandler();
		assetHandler = new AssetHandler();
		inputHandler = new InputHandler();
		gameData = new GameData();

		spriteBatch = new SpriteBatch();
		Gdx.input.setInputProcessor(inputHandler);

		Thread assetLoadingThread = new Thread(new QeueuLoader()); //Loads assets on a separate thread
		Gdx.app.postRunnable(assetLoadingThread);

	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		update(deltaTime);
		renderHandler.render(spriteBatch, deltaTime);
	}

	/**
	 * Updates game ie: game logic
	 * @param deltaTime time that passed since last render update.
	 */
	protected void update(float deltaTime){
		levelHandler.update(deltaTime);
	}
	
	@Override
	public void dispose () {
		levelHandler.dispose();
		assetHandler.dispose();
		fileHandler.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		renderHandler.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
		levelHandler.setPaused(true);
	}

	@Override
	public void resume(){
		super.resume();
		levelHandler.setPaused(false);
	}

	public static void save(){
		levelHandler.save(fileHandler,gameData);
	}

	/**
	 * After all the assets are loaded and main object created, start the game - setup default.
	 */
	protected void startGame(){
		try {
			levelHandler.startFirstLevel();
		}catch (NoSuchElementException e){
			e.printStackTrace();
		}
	}

	protected void loadAssets(){
		assetHandler.startLoadingQueue();
		startGame();
	}

	//region <Getters>
	public static RenderHandler getRenderHandler() {
		return renderHandler;
	}

	public static LevelHandler getLevelHandler() {
		return levelHandler;
	}

	public static AssetHandler getAssetHandler() {
		return assetHandler;
	}

	public static InputHandler getInputHandler() {
		return inputHandler;
	}

	public static GameData getGameData() {
		return gameData;
	}

	public static FileHandler getFileHandler() {
		return fileHandler;
	}

	//endregion

	public static int getFPS() {
		return FPS;
	}

	public static float getWorldWidth() {
		return WORLD_WIDTH;
	}

	public static float getWorldHeight() {
		return WORLD_HEIGHT;
	}

	public static int getResolutionH() {
		return RESOLUTION_H;
	}

	public static int getResolutionV() {
		return RESOLUTION_V;
	}


	private final class QeueuLoader implements Runnable {
		@Override
		public void run() {
			loadAssets();
		}
	}
}
