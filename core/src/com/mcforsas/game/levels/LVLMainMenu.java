package com.mcforsas.game.levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.gameObjects.GOMenuButton;
import com.mcforsas.game.gameObjects.GOTextRenderer;
import com.mcforsas.game.gameObjects.MenuButtonListener;
import com.mcforsas.game.gameObjects.MenuButtonTypes;

/**
 * Created by mcforsas on 19.5.25
 * Main menu, from which the game starts.
 */
public class LVLMainMenu extends Level implements MenuButtonListener {

    private GOMenuButton menuButtonStart;
    private GOMenuButton menuButtonOptions;
    private GOTextRenderer textRenderer;

    @Override
    public void start() {
        menuButtonStart = new GOMenuButton(MenuButtonTypes.START, this.width/2,this.heigth/2,this);
        //textRenderer = new GOTextRenderer(GameLauncher.getAssetHandler().getFont("fntDigits"),this.width/2,this.heigth/2);
        textRenderer = new GOTextRenderer();


        addGameObject(menuButtonStart);
        addGameObject(textRenderer);
        addGameObject(new GOMenuButton(MenuButtonTypes.START, this.width/2,this.heigth/2,this));

        //DEBUG:
        //textRenderer.setText("1337!");

        super.start();
    }

    @Override
    public void onClick(GOMenuButton menuButton) {

        //Go to planet level if the start button is pressed
        if(menuButton.getType() == MenuButtonTypes.START){
            GameLauncher.getLevelHandler().nextLevel();
        }else{
            //DEBUG:
            //textRenderer.setText("Goodbye Shaun!");
        }
    }
}