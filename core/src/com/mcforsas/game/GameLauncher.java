package com.mcforsas.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.handlers.CameraHandler;
import com.mcforsas.game.engine.handlers.FileHandler;
import com.mcforsas.game.gameObjects.GODigitRenderer;
import com.mcforsas.game.gameObjects.GOSkin;
import com.mcforsas.game.levels.LVLMainMenu;
import com.mcforsas.game.levels.LVLPlanet;
import com.mcforsas.game.levels.LVLShop;

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
        gameData = fileHandler.loadGameData();

    }

    @Override
    protected void startGame() {
        levelHandler.addLevel(new LVLShop());
        levelHandler.addLevel(new LVLMainMenu());
        levelHandler.addLevel(new LVLPlanet());
        super.startGame();
    }

    @Override
    protected void loadAssets() {
        assetHandler.addToQueue(Texture.class, "sprBadlogic", "badlogic.jpg");
        assetHandler.addToQueue(Texture.class, "sprExample", "example.jpg");

        assetHandler.addToQueue(Texture.class, "sprPlanet", "planet.png");
        assetHandler.addToQueue(Texture.class, "sprMeteor", "meteor.png");
        assetHandler.addToQueue(Texture.class, "sprRover", "rover.png");
        assetHandler.addToQueue(Texture.class, "sprGem", "gem.png");
        assetHandler.addToQueue(Texture.class, "sprCrater", "crater.png");
        assetHandler.addToQueue(Texture.class, "sprStars","stars.png");

        assetHandler.addToQueue(Texture.class, "sprButtonStart","button_start.png");
        assetHandler.addToQueue(Texture.class, "sprButtonRestart","button_restart.png");

        assetHandler.addToQueue(Music.class, "musExample","example.ogg");
        assetHandler.addToQueue(Sound.class, "sndExample","test.wav");
        assetHandler.addToQueue(Sound.class, "sndExplode","explode.mp3");
        assetHandler.addToQueue(Sound.class, "sndGem","gem.mp3");
        assetHandler.addToQueue(Sound.class, "sndGemPickup","gem_pickup.mp3");

        //load digit textures
        for(int i = 0; i < 10; i++){
            assetHandler.addToQueue(Texture.class, GODigitRenderer.TEXTURE_PREFIX + i, "font_numbers/"+ i +".png");
        }

        //load skin textures

        for(int i = 0; i < 10; i++){
            //TODO: set to skin dir
            assetHandler.addToQueue(Texture.class, GOSkin.SKIN_PREFIX + i, "rover.png");
        }

        super.loadAssets();
    }


}
