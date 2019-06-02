package com.mcforsas.game.gameObjects;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;

import java.util.Vector;

/**
 * @author mcforsas on 19.6.1
 * @desc Handles skin transactions, displays numbers, etc.
 */
public class GOSkinShop extends GameObject {
    private Vector<GOSkin> skins;
    private int selectedSkin;
    public static int SKIN_AMOUNT = 10;
    private float padding = 1f;

    @Override
    public void start() {

        skins = new Vector<GOSkin>();

        for(int i = 0; i < SKIN_AMOUNT; i++) {
            GOSkin skin = new GOSkin(this,i,false,0,i * (GOSkin.skinDimensions + padding));
            skins.add(skin);
            level.addGameObject(skin);
        }

        level.addGameObject(new GOShopButton(this,-1,0,4f));
        level.addGameObject(new GOShopButton(this,1,0,-4f));

        Engine.getInputHandler().addInputListener(this);
        super.start();
    }

    protected void onClick(GOSkin skin){

    }

    @Override
    public void touchDragged(float x, float y) {
        super.touchDragged(x, y);
    }
}
