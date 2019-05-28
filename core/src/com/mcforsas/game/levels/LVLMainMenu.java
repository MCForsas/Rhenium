package com.mcforsas.game.levels;

import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.gameObjects.GOMenuButton;
import com.mcforsas.game.gameObjects.MenuButtonListener;
import com.mcforsas.game.gameObjects.MenuButtonTypes;

/**
 * Created by mcforsas on 19.5.25
 * Main menu, from which the game starts.
 */
public class LVLMainMenu extends Level implements MenuButtonListener {

    private GOMenuButton menuButtonStart;
    private GOMenuButton menuButtonOptions;

    @Override
    public void start() {
        addGameObject(new GOMenuButton(MenuButtonTypes.START, this.width/2,this.heigth/2,this));
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