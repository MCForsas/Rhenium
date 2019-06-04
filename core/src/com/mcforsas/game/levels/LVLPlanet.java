package com.mcforsas.game.levels;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcforsas.game.GameLauncher;
import com.mcforsas.game.engine.core.*;
import com.mcforsas.game.engine.handlers.FileHandler;
import com.mcforsas.game.gameObjects.*;

/**
 * @author MCForsas @since 3/16/2019
 * Level holds game objects, varibles and shows planet sprite.
 */
public class LVLPlanet extends Level implements MenuButtonListener {

    private float planetScale;
    private float shrinkProgress; //How much the planet has shrunken
    private float shrinkSpeed;

    private int meteorSpawnPeriod; //What's the tick period between meteor spawning
    private int difficultyLevel;
    private int difficultyIncreasePeriod; //How often the difficulty increases
    private int gemSpawnChance; //Percentage of chance, that a gem will spawn when meteor lands

    private GOCar car;
    private GODigitRenderer digitRenderer;
    private float fontSize;

    private float carSize;

    private int tick;

    private boolean gameOver;

    private GOMenuButton menuButtonRestart;
    private GOMenuButton menuButtonMainMenu;
    private Sprite background;

    @Override
    public void start() {
        gameOver = false;
        planetScale = 4f;
        shrinkSpeed = .00005f;
        meteorSpawnPeriod = 25;
        difficultyLevel = 0;
        difficultyIncreasePeriod = 300;
        gemSpawnChance = 30;
        fontSize = 1f;
        tick = 0;
        carSize = 1f;
        setDepth(100);



        sprite = new Sprite(Engine.getAssetHandler().getTexture("sprPlanet"));
        background = new Sprite(Engine.getAssetHandler().getTexture("sprStars"));

        sprite.setSize(Engine.getWorldWidth()*planetScale,Engine.getWorldWidth()*planetScale);
        sprite.setOriginCenter();
        sprite.setOriginBasedPosition(getWidth()/2,getHeigth()/2);

        background.setSize(getHeigth(),getHeigth());
        background.setOriginCenter();
        background.setRotation((Float) Utils.choose(0f,90f,180f,270f));

        car = new GOCar(carSize,getWidth()/2,getHeigth()/2,50,this);
        addGameObject(car);

        digitRenderer = new GODigitRenderer(GameLauncher.BALANCE,width/2,10f);
        digitRenderer.setHeight(fontSize);
        digitRenderer.setSpacing(.2f);

        addGameObject(digitRenderer);


        super.start();

    }

    @Override
    public void update(float deltaTime) {

        if(gameOver){
            menuButtonRestart.setX(Engine.getRenderHandler().getCamera().position.x);
            menuButtonRestart.setY(Engine.getRenderHandler().getCamera().position.y + 3f);
            menuButtonMainMenu.setX(Engine.getRenderHandler().getCamera().position.x);
            menuButtonMainMenu.setY(Engine.getRenderHandler().getCamera().position.y - 3f);
        }


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
            setGameOver(true);
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

        //Set digit renderer position
        digitRenderer.setX(Engine.getRenderHandler().getCamera().position.x);
        digitRenderer.setY(
                Engine.getRenderHandler().getCamera().position.y +
                Engine.getRenderHandler().getViewport().getWorldHeight()/2 - fontSize*1.5f
        );

        digitRenderer.setX(digitRenderer.getX() - digitRenderer.getStringWidth()/2);
        background.setOriginBasedPosition(Engine.getRenderHandler().getCamera().position.x,Engine.getRenderHandler().getCamera().position.y);

        tick++;

        super.update(deltaTime);
    }

    @Override
    public void render(SpriteBatch spriteBatch, float deltaTime) {
        background.draw(spriteBatch);
        super.render(spriteBatch, deltaTime);
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
        if(!gameOver)
            GameLauncher.getAssetHandler().getSound("sndGemPickup").play();
            setScore(getScore()+1);
    }

    @Override
    public void dispose() {
        end();
        super.dispose();
    }

    @Override
    public void onClick(GOMenuButton menuButton) {
        switch (menuButton.getType()){
            case MAIN_MENU:
                //end();
                GameLauncher.getLevelHandler().setCurrentLevel(GameLauncher.lvlMainMenu);
                break;
            case RESTART:
                GameLauncher.getLevelHandler().restartLevel();
            default:
        }
    }

    @Override
    public void save(FileHandler fileHandler, GameData gameData) {
        fileHandler.putPreferencesInt("gems",GameLauncher.BALANCE);
        super.save(fileHandler, gameData);
    }

    public int getScore() {
        return GameLauncher.BALANCE;
    }

    private void setScore(int score) {
        GameLauncher.BALANCE = score;
        digitRenderer.setNumber(score);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        //Save the score if it's not gameOver yet.
        if(gameOver == true && this.gameOver == false){
            save(GameLauncher.getFileHandler(), GameLauncher.getGameData());
            car.setControllable(false);
            menuButtonRestart = new GOMenuButton(MenuButtonTypes.RESTART,
                    Engine.getRenderHandler().getCamera().position.x,
                    Engine.getRenderHandler().getCamera().position.y + 3f,
                    this
            );

            menuButtonMainMenu = new GOMenuButton(MenuButtonTypes.MAIN_MENU,
                    Engine.getRenderHandler().getCamera().position.x,
                    Engine.getRenderHandler().getCamera().position.y - 3f,
                    this
            );

            addGameObject(menuButtonRestart);
            addGameObject(menuButtonMainMenu);
            GameLauncher.getAssetHandler().getSound("sndExplode").play();
        }

        this.gameOver = gameOver;
    }

    @Override
    public void end() {
        save(GameLauncher.getFileHandler(),GameLauncher.getGameData());
        super.end();
    }
}
