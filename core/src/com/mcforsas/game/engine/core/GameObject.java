package com.mcforsas.game.engine.core;

import com.mcforsas.game.engine.handlers.FileHandler;

/**
 * Created by MCForsas on 3/16/2019
 * Game object. Can be used with game logic and so on.
 */
public abstract class GameObject extends Renderable{
    protected Level level;
    protected boolean isPaused = false;

    public void update(float deltaTime){
        super.update();
    }

    /**
     * Called on game save. Object should save all of it's details for later use;
     * @param fileHandler
     * @param gameData
     */
    public void save(FileHandler fileHandler, GameData gameData){

    }

    public Level getLevel() {
        return level;
    }

    @Override
    public void end() {
        level.removeGameObject(this);
        super.end();
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
