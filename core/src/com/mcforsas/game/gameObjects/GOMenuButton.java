package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameObject;
import com.mcforsas.game.engine.core.Utils;

/**
 * Created by mcforsas on 19.5.25
 * Menu button, used in menus. Duhhh.
 */
public class GOMenuButton extends GameObject {
    private MenuButtonTypes type;
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
        setDepth(-10);
        switch (type) {
            //TODO: replace with normal sprites
            case START:
                this.sprite = new Sprite(GameLauncher.getAssetHandler().getTexture("sprButtonStart"));
                this.sprite.setSize(24f,24f);
                this.sprite.setOriginCenter();
                this.sprite.setOriginBasedPosition(x,y);
                break;
            case RESTART:
                this.sprite = new Sprite(GameLauncher.getAssetHandler().getTexture("sprButtonRestart"));
                this.sprite.setSize(24f,24f);
                this.sprite.setOriginCenter();
                this.sprite.setOriginBasedPosition(x,y);
                break;
            case MAIN_MENU:
                //TODO: replace with sprite
                this.sprite = new Sprite(GameLauncher.getAssetHandler().getTexture("sprButtonStart"));
                this.sprite.setSize(24f,24f);
                this.sprite.setOriginCenter();
                this.sprite.setOriginBasedPosition(x,y);
                break;
            case SHOP:
                //TODO: replace with shop sprite
                this.sprite = new Sprite(GameLauncher.getAssetHandler().getTexture("sprButtonRestart"));
                this.sprite.setSize(24f,24f);
                this.sprite.setOriginCenter();
                this.sprite.setOriginBasedPosition(x,y);
                break;
        }

        super.start();
    }

    @Override
    public void update(float deltaTime) {
        sprite.setOriginBasedPosition(x,y);
        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);

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

    @Override
    public void end() {
        super.end();
    }
}
