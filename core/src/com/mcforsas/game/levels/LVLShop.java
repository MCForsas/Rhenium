package com.mcforsas.game.levels;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.gameObjects.GOSkinShop;

/**
 * Created by mcforsas on 19.5.30
 * Shop where you can buy new skins for cars.
 */
public class LVLShop extends Level {
    private int score;

    @Override
    public void start() {

        setWidth(Engine.getWorldWidth()/2f);
        setHeigth(Engine.getWorldHeight()/2f);

        addGameObject(new GOSkinShop());

        sprite = new Sprite(AssetHandler.getTexture("sprStars"));
        sprite.setSize(heigth,heigth);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(0f,0f);

        GameLauncher.getRenderHandler().setCameraPosition(0,0);
        setDepth(100);
        super.start();
    }

    @Override
    public void end() {
        super.end();
    }
}
