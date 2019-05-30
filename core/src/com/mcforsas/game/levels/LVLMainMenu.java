package com.mcforsas.game.levels;

import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.gameObjects.*;

/**
 * Created by mcforsas on 19.5.25
 * Main menu, from which the game starts.
 */
public class LVLMainMenu extends Level implements MenuButtonListener {

    private GOMenuButton menuButtonStart;
    private GOMenuButton menuButtonOptions;

    @Override
    public void start() {
        setWidth(width/2f);
        setHeigth(heigth/2f);
        menuButtonStart = new GOMenuButton(MenuButtonTypes.START, 0,0,this);

        addGameObject(menuButtonStart);

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