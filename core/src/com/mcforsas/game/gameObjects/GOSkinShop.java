package com.mcforsas.game.gameObjects;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameData;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.FileHandler;

import java.util.HashMap;
import java.util.Vector;

/**
 * @author mcforsas on 19.6.1
 * @desc Handles skin transactions, displays numbers, etc.
 */
public class GOSkinShop extends GameObject {
    private Vector<GOSkin> skins;
    public static int SKIN_AMOUNT = 10;
    private float padding = 1f;
    private float touchY;
    private float scrollSpeed = .05f;
    private float scrollPos = -SKIN_AMOUNT * (GOSkin.skinDimensions + padding) + GOSkin.skinDimensions*3;

    private int selected = GameLauncher.SKIN_SELECTED;
    private final int skinPrice = 100;

    @Override
    public void start() {

        skins = new Vector<GOSkin>();

        for(int i = 0; i < SKIN_AMOUNT; i++) {
            GOSkin skin = new GOSkin(
                    this,
                    i,
                    (Boolean) GameLauncher.getFileHandler().getPreferences("isSkinUnlocked"+i, Boolean.class,false),
                    0,
                    i * (GOSkin.skinDimensions + padding)
            );

            skins.add(skin);
            level.addGameObject(skin);
        }

        Engine.getInputHandler().addInputListener(this);

        for(int i = 0; i < SKIN_AMOUNT; i++){
            skins.get(i).setY(i * (GOSkin.skinDimensions + padding) + scrollPos);
        }

        super.start();
    }

    protected void onClick(GOSkin skin){

        if(skin.isUnlocked()){
            if(GameLauncher.BALANCE >= skinPrice){
                GameLauncher.BALANCE -= skinPrice;
                skin.setUnlocked(true);
                skin.setSelected(true);

                skins.get(GameLauncher.SKIN_SELECTED).setSelected(false); //unselect selected skin
                GameLauncher.SKIN_SELECTED = skin.getIndex();
            }else{
                //TODO: play a sound
            }
        }
    }

    @Override
    public void touchDown(float x, float y) {
        touchY = y;
        super.touchDown(x, y);
    }

    @Override
    public void touchDragged(float x, float y) {
        super.touchDragged(x, y);
        for(int i = 0; i < SKIN_AMOUNT; i++){
            skins.get(i).setY(i * (GOSkin.skinDimensions + padding) + scrollPos);
        }

        scrollPos +=  (y -touchY)*scrollSpeed;
        scrollPos = Utils.clamp(scrollPos,-SKIN_AMOUNT * (GOSkin.skinDimensions + padding) + GOSkin.skinDimensions*3, -GOSkin.skinDimensions*2);
    }

    @Override
    public void save(FileHandler fileHandler, GameData gameData) {
        for(int i = 0; i < SKIN_AMOUNT; i++) {
            GameLauncher.getFileHandler().putPreferencesBoolean("isSkinUnlocked"+i,skins.get(i).isUnlocked());
        }

        GameLauncher.getFileHandler().putPreferencesInt("skinSelected",selected);
        super.save(fileHandler, gameData);
    }

    @Override
    public void end() {
        save(GameLauncher.getFileHandler(),GameLauncher.getGameData());
        super.end();
    }
}
