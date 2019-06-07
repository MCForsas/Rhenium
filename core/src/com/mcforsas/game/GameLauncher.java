package com.mcforsas.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.CameraHandler;
import com.mcforsas.game.engine.handlers.FileHandler;
import com.mcforsas.game.gameObjects.GODigitRenderer;
import com.mcforsas.game.gameObjects.GOMeteor;
import com.mcforsas.game.gameObjects.GOSkin;
import com.mcforsas.game.gameObjects.GOSkinShop;
import com.mcforsas.game.levels.LVLMainMenu;
import com.mcforsas.game.levels.LVLPlanet;
import com.mcforsas.game.levels.LVLShop;

/**
 * Created by mcforsas on 19.4.21
 * Game object. All the config and constants should be set here
 */
public class GameLauncher extends Engine {
    public static final float MAX_ASPECT_DEVIATION = .2f;

    public static int BALANCE;
    public static int SKIN_SELECTED;
    public static LVLMainMenu lvlMainMenu;
    public static LVLShop lvlShop;
    public static LVLPlanet lvlPlanet;

    public static Music music;


    //Config here
    public GameLauncher() {
        FPS = 120;
        WORLD_WIDTH = 80;
        WORLD_HEIGHT = 160;
        RESOLUTION_H = 1080/4;
        RESOLUTION_V = 1920/4;
    }

    @Override
    public void create() {
        super.create();

        OrthographicCamera cameraHandler = new CameraHandler(2f,4f);
        renderHandler.setup(cameraHandler,
                new ExtendViewport(
                        WORLD_WIDTH,
                        WORLD_HEIGHT,
                        WORLD_WIDTH*(1+MAX_ASPECT_DEVIATION),
                        WORLD_HEIGHT*(1+MAX_ASPECT_DEVIATION),
                        cameraHandler
                )
        );

        fileHandler = new FileHandler("save.sav",false);
        gameData = fileHandler.loadGameData();

        BALANCE = fileHandler.getPreferencesInt("gems");
        SKIN_SELECTED = (Integer) fileHandler.getPreferences("skinSelected",Integer.class,0);
    }

    @Override
    protected void startGame() {
        lvlMainMenu = new LVLMainMenu();
        lvlShop = new LVLShop();
        lvlPlanet = new LVLPlanet();

        levelHandler.addLevel(lvlMainMenu);
        levelHandler.addLevel(lvlPlanet);
        levelHandler.addLevel(lvlShop);

        music = AssetHandler.getMusic("musMainTheme");
        music.setLooping(true);

        boolean isMusPlaying = (Boolean) GameLauncher.getFileHandler().getPreferences("music", Boolean.class,true);
        GameLauncher.setMusicPlaying(isMusPlaying);

        super.startGame();
    }

    @Override
    protected void loadAssets() {

        //region <Textures>
        assetHandler.addToQueue(Texture.class, "sprPlanet", "planet.png");
        assetHandler.addToQueue(Texture.class, "sprGem", "gem.png");
        assetHandler.addToQueue(Texture.class, "sprStars","stars.png");

        assetHandler.addToQueue(Texture.class, "sprButtonStart","buttons/button_start.png");
        assetHandler.addToQueue(Texture.class, "sprButtonRestart","buttons/button_restart.png");
        assetHandler.addToQueue(Texture.class, "sprButtonMenu","buttons/button_menu.png");
        assetHandler.addToQueue(Texture.class, "sprButtonShop","buttons/button_shop.png");

        //load digit textures
        for(int i = 0; i < 10; i++){
            assetHandler.addToQueue(Texture.class, GODigitRenderer.TEXTURE_PREFIX + i, "font_numbers/"+ i +".png");
        }

        //load skin textures
        for(int i = 0; i < GOSkinShop.SKIN_AMOUNT; i++) {
            assetHandler.addToQueue(Texture.class, GOSkin.SKIN_PREFIX + i, "car_skins/"+i+".png");
        }

        //load meteors
        assetHandler.addToQueue(Texture.class, GOMeteor.METEOR_PREFIX + 8, "meteors/meteor_8.png");
        assetHandler.addToQueue(Texture.class, GOMeteor.METEOR_PREFIX + 12, "meteors/meteor_12.png");
        assetHandler.addToQueue(Texture.class, GOMeteor.METEOR_PREFIX + 16, "meteors/meteor_16.png");

        //load craters
        assetHandler.addToQueue(Texture.class, GOMeteor.CRATER_PREFIX + 8, "craters/crater_8.png");
        assetHandler.addToQueue(Texture.class, GOMeteor.CRATER_PREFIX + 12, "craters/crater_12.png");
        assetHandler.addToQueue(Texture.class, GOMeteor.CRATER_PREFIX + 16, "craters/crater_16.png");

        //load shop icons
        assetHandler.addToQueue(Texture.class, "sprShopSelect","shop/select.png");
        assetHandler.addToQueue(Texture.class, "sprShopLocked","shop/locked.png");
        assetHandler.addToQueue(Texture.class, "sprMusic","music.png");
        //endregion

        //region <Sounds>
        assetHandler.addToQueue(Music.class, "musMainTheme","main_theme.wav");

        assetHandler.addToQueue(Sound.class,"sndButtonClick","button_click.ogg");
        assetHandler.addToQueue(Sound.class,"sndShopSelect","shop_select.ogg");
        assetHandler.addToQueue(Sound.class,"sndShopLocked","shop_locked.ogg");
        assetHandler.addToQueue(Sound.class,"sndShopUnlock","shop_unlock.ogg");
        assetHandler.addToQueue(Sound.class,"sndExplosion","explosion.ogg");
        assetHandler.addToQueue(Sound.class,"sndGemPickup","gem_pickup.ogg");
        assetHandler.addToQueue(Sound.class,"sndMeteorDrop","meteor_drop.ogg");
        assetHandler.addToQueue(Sound.class,"sndLoss","loss.ogg");

        //endregion
        super.loadAssets();
    }

    @Override
    public void dispose() {
        assetHandler.dispose();
        levelHandler.dispose();
        levelHandler.save(fileHandler,gameData);
        music.dispose();

        super.dispose();
    }

    public static void setMusicPlaying(boolean setPlaying){
        if(setPlaying){
            music.play();
        }else{
            music.stop();
        }

        fileHandler.putPreferencesBoolean("music",setPlaying);
    }

    public static boolean isMusicPlaying(){
        return music.isPlaying();
    }
}
