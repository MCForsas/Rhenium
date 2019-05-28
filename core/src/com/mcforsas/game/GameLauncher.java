package com.mcforsas.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.handlers.CameraHandler;
import com.mcforsas.game.engine.handlers.FileHandler;
import com.mcforsas.game.levels.LVLMainMenu;
import com.mcforsas.game.levels.LVLPlanet;

/**
 * Created by mcforsas on 19.4.21
 * Game object. All the config and constants should be set here
 */
public class GameLauncher extends Engine {
    private float maxAspectDeviation = .2f;
    //Config here
    public GameLauncher() {
        FPS = 120;
        WORLD_WIDTH = 90*maxAspectDeviation;
        WORLD_HEIGHT = 160*maxAspectDeviation;
        RESOLUTION_H = 1080/4;
        RESOLUTION_V = 1920/4;
    }

    @Override
    public void create() {
        super.create();

        OrthographicCamera cameraHandler = new CameraHandler(.1f,.1f);
        renderHandler.setup(
                cameraHandler,
                new ExtendViewport(WORLD_WIDTH/2, WORLD_HEIGHT/2, cameraHandler),
                maxAspectDeviation
        );

        fileHandler = new FileHandler("save.sav",false);
        gameData = fileHandler.load();

    }

    @Override
    protected void startGame() {
        levelHandler.addLevel(new LVLMainMenu());
        levelHandler.addLevel(new LVLPlanet());
        super.startGame();
    }

    @Override
    protected void loadAssets() {
        assetHandler.addToQueue(Texture.class, "sprBadlogic", "badlogic.jpg");
        assetHandler.addToQueue(Texture.class, "sprExample", "example.jpg");
        assetHandler.addToQueue(Texture.class, "sprPlanet", "planet.png");

        assetHandler.addToQueue(Music.class, "musExample","example.ogg");
        assetHandler.addToQueue(Sound.class, "sndExample","test.wav");
        super.loadAssets();
    }


}
