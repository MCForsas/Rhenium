package com.mcforsas.game.gameObjects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.*;
import com.mcforsas.game.engine.handlers.AssetHandler;
import com.mcforsas.game.engine.handlers.FileHandler;

/**
 * @author MCForsas @since 3/16/2019
 * Car object, which moves, is controlled by user
 */
public class GOCar extends GameObject {

    private float moveSpeed = .07f;
    private float direction = 90f; //Direction in degrees;
    private float turnSpeed =  3f;
    private int turnDirection = 0; //Variable used for touch input
    private boolean isControllable = true;
    private float size;

    private float speedHorizontal = 0, speedVertical = 0;

    public GOCar(float size, float x, float y, float depth, Level level){
        this.x = x;
        this.y = y;
        this.size = size;

        setDepth(depth);
        GameLauncher.getInputHandler().addInputListener(this);
    }

    @Override
    public void start() {
        sprite = new Sprite(AssetHandler.getTexture(GOSkin.SKIN_PREFIX + GameLauncher.SKIN_SELECTED));
        sprite.setSize(size,size);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(x,y);
        super.start();
    }

    @Override
    public void update(float deltaTime) {

        //Turn with A and D keys
        if(GameLauncher.getInputHandler().isKeyDown(Input.Keys.A)){
            turn(1);
        }else if(GameLauncher.getInputHandler().isKeyDown(Input.Keys.D)){
            turn(-1);
        }

        turn(turnDirection);

        //If direction is not in range of -360 to 360 it get's wrapped to the other end: 359 -> 360 -> -360 -> -359;
        direction = Utils.wrap(direction,-360,360);

        //Calculate how much the car needs to move accoding to direction it's facing and speed it's travelling at
        speedHorizontal = (float) Math.cos(Math.toRadians(direction)) * moveSpeed;
        speedVertical = (float) Math.sin(Math.toRadians(direction)) * moveSpeed;

        this.x += speedHorizontal;
        this.y += speedVertical;

        sprite.setPosition(x,y);
        sprite.setRotation(direction - 90);

        //Tell camera to follow the object
        GameLauncher.getRenderHandler().setCameraPosition(x,y);

        super.update(deltaTime);
    }

    /**
     * Turn in some direction;
     * @param sign -1 cc : 1 cw;
     */
    private void turn(int sign){
        if(isControllable) {
            direction += (sign * turnSpeed);
        }
    }

    /**
     * Turns the car in some direction. If the user taps on left side of the screen, car goes left, by setting turn
     * direction to -1;
     * @param x
     * @param y
     */
    @Override
    public void touchDown(float x, float y) {
        if ((GameLauncher.getInputHandler().getTouchX() < Gdx.graphics.getWidth()/2)) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
    }

    /**
     * If screen is not pressed anywhere, don't turn
     * @param x
     * @param y
     */
    @Override
    public void touchUp(float x, float y) {
        turnDirection = 0;
    }

    public boolean isControllable() {
        return isControllable;
    }

    public void setControllable(boolean controllable) {
        isControllable = controllable;
    }
}
