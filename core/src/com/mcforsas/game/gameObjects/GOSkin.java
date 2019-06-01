package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;

/**
 * @author mcforsas on
 * @desc Replace this text by description, of what this code is for
 */
public class GOSkin extends GameObject {
    private GOSkinShop shop;
    private int index;
    private Sprite border;
    private boolean isUnlocked;

    public GOSkin(GOSkinShop shop, int index, boolean isUnlocked) {
        this.shop = shop;
        this.index = index;
        this.isUnlocked = isUnlocked;

        this.border = new Sprite(GameLauncher.getAssetHandler().getTexture());
    }

    @Override
    public void touchUp(float x, float y) {

        if(Utils.isOnSprite(sprite,x,y)){
            shop.onClick(this);
        }

        super.touchUp(x, y);
    }
}
