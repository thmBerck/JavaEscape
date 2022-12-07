package com.mygdx.game;

public class GameState {
    private boolean startMovingPerms;
    public enum GameRooms {ONE, TWO, THREE}
    private GameRooms gameState;

    public boolean isStartMovingPerms()
    {
        return startMovingPerms;
    }

    public void setStartMovingPerms(boolean startMovingPerms)
    {
        this.startMovingPerms = startMovingPerms;
    }

    public GameRooms getGameState()
    {
        return gameState;
    }

    public void setGameState(GameRooms gameState)
    {
        this.gameState = gameState;
    }

}
