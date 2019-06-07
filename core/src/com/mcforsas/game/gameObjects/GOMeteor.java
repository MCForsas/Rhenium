package com.mcforsas.game.gameObjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.*;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.levels.LVLPlanet;

/**
 * @author MCForsas @since 3/16/2019
 * Meteor object. Is spawned at intervals and lands if is close enough to the car object
 */
public class GOMeteor extends GameObject {

    private LVLPlanet LVLPlanet;
    private int size; //How big the meteor is
    private float speed; //How fast it moves
    private GOCar car;
    private float direction; //Which direction 0 - 360 it's heading to
    private float spawnDistance = 128f; //How far from the car it spawns
    private float explodeDistance; //The minimum distance to car before it explodes

    private int tick;
    private boolean onGround = false; //If it has already hit the ground

    private float rotationSpeed;

    public static final String METEOR_PREFIX = "sprMeteor";
    public static final String CRATER_PREFIX = "sprCrater";

    public GOMeteor(float depth, LVLPlanet LVLPlanet, GOCar car){
        this.LVLPlanet = LVLPlanet;
        this.car = car;

        setDepth(depth);
    }



    @Override
    public void start() {
        size = Utils.irandomRange(2,4)*4;
        speed = Utils.irandomRange(4,8)/10f;
        explodeDistance = Utils.irandomRange(32,128);
        rotationSpeed = Utils.irandomRange(30,30)/10 - 3f;

        sprite = new Sprite(AssetHandler.getTexture(METEOR_PREFIX + size)); //Get accordingly sized sprite

        /**
         * The way this object spawns is this: it chooses a random point on a circle around the car. Then it chooses
         * a direction to fly to. Then it's coordinates are set to a point around a circle where it cannot be seen
         * from a playable area.
         */
        float spawnPlace = Utils.irandom(360); //Where it spawns on circle around car object

        this.direction = (float) (90 - Utils.irandomRange(80,250)); //choose a direction to fly to;

        x = car.getX() + (float) Math.cos(Math.toRadians(spawnPlace)) * spawnDistance; //Position it self
        y = car.getY() + (float) Math.sin(Math.toRadians(spawnPlace)) * spawnDistance;

        sprite.setSize(size,size);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x,y);

        setDepth(-20);

        super.start();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(onGround) {
            //region <On ground>
            setDepth(50);
            //If meteor hit the ground, and is there for some tics, destroy it
            if(tick >= size*32){
                if(Utils.chance(LVLPlanet.getGemSpawnChance())){
                    LVLPlanet.addGameObject(new GOGem(x,y,this.depth, LVLPlanet,car));
                    if(Utils.distanceBetweenPoints(x,y,car.getX(), car.getY()) <= explodeDistance){
                        GameLauncher.getAssetHandler().getSound("sndMeteorDrop").play();
                    }
                }
                end();
            }

            //If it colides with car
            if(Utils.isSpriteCollisionRectangle(sprite, car.getSprite())){
                LVLPlanet.setGameOver(true);
            }

            //If the meteor get's outside the planet, destroy it
            if(Utils.distanceBetweenPoints(x,y, level.getWidth()/2f,level.getHeigth()/2f)
                    > LVLPlanet.getPlanetSize()/2f){
                end();
            }

            tick++;
            //endregion
        }else{
            //region <In the air>
            this.x += (float) Math.cos(Math.toRadians(direction)) * speed;
            this.y += (float) Math.sin(Math.toRadians(direction)) * speed;

            sprite.setRotation(sprite.getRotation() + rotationSpeed);

            //Ground the meteor if it's near the car and is not outside the planet
            if(Utils.distanceBetweenPoints(x,y,car.getX(), car.getY()) <= explodeDistance
                    && Utils.distanceBetweenPoints(x,y,level.getWidth()/2f,level.getHeigth()/2f)
                    <= LVLPlanet.getPlanetSize()/2f){
                setOnGround(true);

                if(Utils.distanceBetweenPoints(x,y,car.getX(),car.getY()) <= explodeDistance*.7f) {
                    GameLauncher.getAssetHandler().getSound("sndExplosion").play();
                }
            }
            //endregion
        }

        sprite.setPosition(x,y);

        //If meteor get's too far outside the visible area, destroy it
        if(Utils.distanceBetweenPoints(x,y,car.getX(),car.getY()) >= spawnDistance*2){
            end();
        }
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;

        //Change the texture to crater
        sprite.setTexture(AssetHandler.getTexture(CRATER_PREFIX + size));
        sprite.setRotation((Integer) Utils.choose(0,90,180,270));
    }
}