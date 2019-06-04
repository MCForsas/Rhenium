package com.mcforsas.game.levels;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.gameObjects.*;

/**
 * Created by mcforsas on 19.5.25
 * Main menu, from which the game starts.
 */
public class LVLMainMenu extends Level implements MenuButtonListener {

    private GOMenuButton menuButtonStart;
    private GOMenuButton menuButtonOptions;

    private GODigitRenderer digitRenderer;

    @Override
    public void start() {
        setWidth(Engine.getWorldWidth()/2f);
        setHeigth(Engine.getWorldHeight()/2f);

        menuButtonStart = new GOMenuButton(MenuButtonTypes.START, 0,0,this);
        digitRenderer = new GODigitRenderer(GameLauncher.BALANCE,0,4.5f);
        digitRenderer.setHeight(2f);
        digitRenderer.setX(digitRenderer.getX() - digitRenderer.getStringWidth()/2f);
        digitRenderer.setSpacing(.2f);

        sprite = new Sprite(AssetHandler.getTexture("sprStars"));
        sprite.setSize(heigth,heigth);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(0f,0f);

        addGameObject(menuButtonStart);
        addGameObject(digitRenderer);

        Engine.getRenderHandler().setCameraPosition(0,0);

        super.start();
    }

    @Override
    public void onClick(GOMenuButton menuButton) {

        //Go to planet level if the start button is pressed
        if(menuButton.getType() == MenuButtonTypes.START){
            GameLauncher.getLevelHandler().nextLevel();
        }
    }
}