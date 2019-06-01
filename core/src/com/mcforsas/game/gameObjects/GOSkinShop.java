package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.Game;
import com.mcforsas.game.engine.core.GameObject;

import java.util.Vector;

/**
 * @author mcforsas on 19.6.1
 * @desc Handles skin transactions, displays numbers, etc.
 */
public class GOSkinShop extends GameObject {
    private Vector<GOSkin> skins;

    @Override
    public void start() {
        skins = new Vector<GOSkin>();
        super.start();
    }

    protected void onClick(GOSkin skin){

    }
}
