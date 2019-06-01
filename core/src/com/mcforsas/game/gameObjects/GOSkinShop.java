package com.mcforsas.game.gameObjects;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;

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

        for(int i = 0; i < 10; i++) {
            GOSkin skin = new GOSkin(this,i,false,0,0);
            skins.add(skin);
            level.addGameObject(skin);
        }

        skins.get(0).setSelectable(true);
        super.start();
    }

    protected void onClick(GOSkin skin){

    }
}
