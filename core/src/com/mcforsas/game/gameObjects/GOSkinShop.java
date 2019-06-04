package com.mcforsas.game.gameObjects;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameData;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.FileHandler;

import java.lang.reflect.GenericArrayType;
import java.util.HashMap;
import java.util.Vector;

/**
 * @author mcforsas on 19.6.1
 * @desc Handles skin transactions, displays numbers, etc.
 */
public class GOSkinShop extends GameObject {
    private Vector<GOSkin> skins;
    public static int SKIN_AMOUNT = 10;
    private float padding = 4f;
    private float touchY;
    private float scrollSpeed = .05f;
    private float scrollPos = -SKIN_AMOUNT * (GOSkin.skinDimensions + padding) + GOSkin.skinDimensions*3;

    private final int skinPrice = 100;

    private GODigitRenderer digitRenderer;

    @Override
    public void start() {
        this.x = 16f;

        skins = new Vector<GOSkin>();

        for(int i = 0; i < SKIN_AMOUNT; i++) {
            GOSkin skin = new GOSkin(
                    this,
                    i,
                    (Boolean) GameLauncher.getFileHandler().getPreferences("isSkinUnlocked"+i, Boolean.class,false),
                    x,
                    i * (GOSkin.skinDimensions + padding)
            );

            skins.add(skin);
            level.addGameObject(skin);
        }

        //Unlock the default sprite
        skins.get(0).setUnlocked(true);
        skins.get(GameLauncher.SKIN_SELECTED).setSelected(true);


        Engine.getInputHandler().addInputListener(this);

        for(int i = 0; i < SKIN_AMOUNT; i++){
            skins.get(i).setY(i * (GOSkin.skinDimensions + padding) + scrollPos);
        }

        digitRenderer = new GODigitRenderer(GameLauncher.BALANCE,-32f,64f);
        digitRenderer.setHeight(16f);
        digitRenderer.setX(digitRenderer.getX() - digitRenderer.getStringWidth()/2f);
        level.addGameObject(digitRenderer);

        super.start();
    }

    protected void onClick(GOSkin skin){

        if(!skin.isUnlocked()){
            if(GameLauncher.BALANCE >= skinPrice){
                GameLauncher.BALANCE -= skinPrice;
                skin.setUnlocked(true);
                skin.setSelected(true);
                setSelectedSkin(skin.getIndex());
                digitRenderer.setNumber(GameLauncher.BALANCE);
            }else{
                //TODO: play a sound
            }
        }else{
            setSelectedSkin(skin.getIndex());
        }
    }

    private void setSelectedSkin(int index){
        skins.get(GameLauncher.SKIN_SELECTED).setSelected(false); //unselect selected skin
        GameLauncher.SKIN_SELECTED = index;
        skins.get(index).setSelected(true);
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

        GameLauncher.getFileHandler().putPreferencesInt("skinSelected",GameLauncher.SKIN_SELECTED);
        super.save(fileHandler, gameData);
    }

    @Override
    public void end() {
        save(GameLauncher.getFileHandler(),GameLauncher.getGameData());
        super.end();
    }
}
