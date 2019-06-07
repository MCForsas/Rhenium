package com.mcforsas.game.levels;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.RenderHandler;
import com.mcforsas.game.gameObjects.*;

/**
 * Created by mcforsas on 19.5.25
 * Main menu, from which the game starts.
 */
public class LVLMainMenu extends Level implements MenuButtonListener {

    private GOMenuButton menuButtonStart;
    private GOMenuButton menuButtonShop;
    private GOMenuButton menuButtonMusic;

    private GODigitRenderer digitRenderer;

    @Override
    public void start() {

        menuButtonStart = new GOMenuButton(MenuButtonTypes.START, width/2f,80,this);
        menuButtonShop = new GOMenuButton(MenuButtonTypes.SHOP, width/2f,32,this);
        menuButtonMusic = new GOMenuButton(MenuButtonTypes.MUSIC, width-12f,heigth-12f,this);

        digitRenderer = new GODigitRenderer(GameLauncher.BALANCE,width/2,128);
        digitRenderer.setHeight(16f);
        digitRenderer.setX(digitRenderer.getX() - digitRenderer.getStringWidth()/2f);

        sprite = new Sprite(AssetHandler.getTexture("sprStars"));
        sprite.setSize(heigth*(1+GameLauncher.MAX_ASPECT_DEVIATION),heigth*(1+GameLauncher.MAX_ASPECT_DEVIATION));
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(width/2,heigth/2);

        addGameObject(menuButtonStart);
        addGameObject(menuButtonShop);
        addGameObject(menuButtonMusic);

        addGameObject(digitRenderer);

        Engine.getRenderHandler().setCameraPosition(width/2,heigth/2);
        super.start();
    }

    @Override
    public void onClick(GOMenuButton menuButton) {
        switch (menuButton.getType()) {
            case START:
                GameLauncher.getLevelHandler().setCurrentLevel(GameLauncher.lvlPlanet);
                break;
            case SHOP:
                GameLauncher.getLevelHandler().setCurrentLevel(GameLauncher.lvlShop);
                break;
            case MUSIC:
                GameLauncher.setMusicPlaying(Utils.flipBoolean(GameLauncher.isMusicPlaying()));
                break;
        }
    }
}