package com.mcforsas.game.gameObjects;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.engine.core.*;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.levels.LVLPlanet;

/**
 * @author MCForsas @since 3/16/2019
 * Gem object, which can  be collected by car if it collides with it. Created sometimes, when meteor hit's the ground.
 */
public class GOGem extends GameObject {

    private int tick = 0; //How long the object exists for
    private int lifeLength = 500; //How long until it's destroyed
    private GOCar car;
    private LVLPlanet LVLPlanet;

    public GOGem(float x, float y, float depth, LVLPlanet level, GOCar car){
        sprite = new Sprite(AssetHandler.getTexture("sprGem"));

        this.x = x;
        this.y = y;

        this.car = car;
        this.LVLPlanet = level;

        setDepth(depth);
    }

    @Override
    public void start() {
        sprite.setSize(8,8);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x,y);
        sprite.setRotation((Integer) Utils.choose(0,90,180,270));

        super.start();
    }

    @Override
    public void update(float deltaTime) {

        //If it collides with car, notify level class to add to score and destroy itself
        if(Utils.isSpriteCollisionRectangle(sprite, car.getSprite())){
            LVLPlanet.onGemCollected();
            end();
        }


        if(tick >= lifeLength){
            end();
        }

        //If the gem get's outside the planet, destroy it
        if(Utils.distanceBetweenPoints(x,y, LVLPlanet.getWidth()/2, LVLPlanet.getHeigth()/2)
                > LVLPlanet.getPlanetSize()/2){
            end();
        }

        tick++;
    }
}
