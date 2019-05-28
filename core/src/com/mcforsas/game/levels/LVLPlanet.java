package com.mcforsas.game.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.Engine;
import com.mcforsas.game.engine.core.GameData;
import com.mcforsas.game.engine.core.Level;
import com.mcforsas.game.engine.core.Utils;
import com.mcforsas.game.engine.handlers.FileHandler;
import com.mcforsas.game.gameObjects.GOCar;
import com.mcforsas.game.gameObjects.GOMenuButton;
import com.mcforsas.game.gameObjects.GOMeteor;
import com.mcforsas.game.gameObjects.MenuButtonListener;

/**
 * @author MCForsas @since 3/16/2019
 * Level holds game objects, varibles and shows planet sprite.
 */
public class LVLPlanet extends Level implements MenuButtonListener {

    private float planetScale = 1.5f;
    private float shrinkProgress = 0f; //How much the planet has shrunken
    private float shrinkSpeed = .00005f;

    private int meteorSpawnPeriod = 25; //What's the tick period between meteor spawning
    private int difficultyLevel = 0;
    private int difficultyIncreasePeriod = 300; //How often the difficulty increases
    private int gemSpawnChance = 5; //Percentage of chance, that a gem will spawn when meteor lands

    private GOCar car;

    private int tick = 0;
    private int score = 0;

    private boolean gameOver = false;

    @Override
    public void start() {
        setDepth(100);

        sprite = new Sprite(Engine.getAssetHandler().getTexture("sprPlanet"));
        sprite.setSize(getWidth()*planetScale,getWidth()*planetScale);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(getWidth()/2,getHeigth()/2);

        car = new GOCar(.5f,getWidth()/2,getHeigth()/2,50,this);
        addGameObject(car);

        super.start();

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        //Shrink the planet until it's just little enough to drive on
        if(shrinkProgress < .7f) {
            shrinkProgress += shrinkSpeed;
        }else {
            //TODO: game over?
        }

        //Ease in planet's shrinkige
        //TODO: review if it's needed
        planetScale = Utils.easeIn(1f - shrinkProgress);
        sprite.setScale(planetScale);



        //Check if car is outside of planet:
        if(Utils.distanceBetweenPoints(car.getX(), car.getY(), getWidth()/2, getHeigth()/2) >= getPlanetDiameter()/2){
            car.setControllable(false);
        }

        //Spawn meteors
        if(tick%meteorSpawnPeriod == 0){
            for(int i  = 0; i < difficultyLevel; i++) {
                addGameObject(new GOMeteor(70, this, car));
            }
        }

        //Increase difficulty and cap it at meteor spawn period, so it's balanced: more often meteor spawns, less often
        //the difficulty increases
        if(tick%difficultyIncreasePeriod == 0 && difficultyLevel <= meteorSpawnPeriod){
            difficultyLevel++;
        }

        //TODO: change to normal variable
        if(!car.isControllable()){
            if(!gameOver){
                save(GameLauncher.getFileHandler(), GameLauncher.getGameData());
                gameOver = true;
            }
        }

        tick++;

    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        super.render(spriteBatch, deltaTime);
        if(gameOver){

        }
    }

    /**
     * Return planet's diameter in units.
     * @return planetsDiameter
     */
    public float getPlanetDiameter(){
        return planetScale * (sprite.getWidth() + sprite.getHeight())/2;
    }

    public int getGemSpawnChance() {
        return gemSpawnChance;
    }

    /**
     * On gem collection increase the score
     */
    public void onGemCollected(){
        this.score++;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void onClick(GOMenuButton menuButton) {
        GameLauncher.getLevelHandler().previousLevel();
    }

    @Override
    public void save(FileHandler fileHandler, GameData gameData) {
        fileHandler.putPreferencesInt("gems",score);
        super.save(fileHandler, gameData);
    }
}
