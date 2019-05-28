package com.mcforsas.game.levels;

import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Level;
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
        setWidth(width/2f);
        setHeigth(heigth/2f);
        menuButtonStart = new GOMenuButton(MenuButtonTypes.START, this.width/2,this.heigth/3,this);

        addGameObject(menuButtonStart);
        super.start();
    }

    @Override
    public void onClick(GOMenuButton menuButton) {

        //Go to planet level if the start button is pressed
        if(menuButton.getType() == MenuButtonTypes.START){
            GameLauncher.getLevelHandler().nextLevel();
        }
    }

    @Override
    public void touchDown(float x, float y) {
        super.touchDown(x, y);
    }
}