package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;

/**
 * Created by mcforsas on 19.5.25
 * Menu button, used in menus. Duhhh.
 */
public class GOMenuButton extends GameObject {
    private MenuButtonTypes type;
    private float x, y;
    private MenuButtonListener listener;

    public GOMenuButton(MenuButtonTypes type, float x, float y, MenuButtonListener listener) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.listener = listener;

        GameLauncher.getInputHandler().addInputListener(this);
    }

    @Override
    public void start() {
        switch (type) {
            case START:
                this.sprite = new Sprite(GameLauncher.getAssetHandler().getTexture("sprBadlogic"));
                this.sprite.setSize(3f,3f);
                this.sprite.setOriginCenter();
                this.sprite.setOriginBasedPosition(0,0);
                break;
            case PAUSE:
                break;
        }

        super.start();
    }

    public MenuButtonTypes getType() {
        return type;
    }

    public void setType(MenuButtonTypes type) {
        this.type = type;
    }

    @Override
    public void touchUp(float x, float y) {

        if(Utils.isOnSprite(sprite,x,y)){
            listener.onClick(this);
        }

        super.touchUp(x, y);
    }
}
