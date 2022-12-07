package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.w3c.dom.Text;

import java.util.HashMap;

public class Character {


    private final Texture textureFront = new Texture(Gdx.files.internal("Character/frontside.gif"));
    private final Texture textureBehind = new Texture(Gdx.files.internal("Character/backside.gif"));
    private final Texture textureLeft = new Texture(Gdx.files.internal("Character/leftview.gif"));
    private final Texture textureRight = new Texture(Gdx.files.internal("Character/rightview.gif"));
    private final int width = 285;
    private final int height = 280;

    private final CollisionRect collisionRect = new CollisionRect(this.x, this.y, width, height);

    private int x, y;
    int xSpeed;
    int ySpeed;
    public Character(int charSpeedX, int charSpeedY) {
        this.xSpeed = charSpeedX;
        this.ySpeed = charSpeedY;
    }
    public void drawCharacter(SpriteBatch batch) {
        batch.draw(textureFront, x, y);
    }

    public void moveNorth(SpriteBatch batch) {
        if(y+ySpeed < Gdx.graphics.getHeight() - 150) {
            y += ySpeed;
        }
        batch.draw(textureBehind, x, y);
        collisionRect.move(x, y);
    }
    public void moveSouth(SpriteBatch batch) {
        if(y-ySpeed > -70) {
            y -= ySpeed;
        }
        batch.draw(textureFront, x, y);
        collisionRect.move(x, y);
    }
    public void moveWest(SpriteBatch batch) {
        if(x-xSpeed > -50) {
            x -= xSpeed;
        }
        batch.draw(textureLeft, x, y);
        collisionRect.move(x, y);

    }
    public void moveEast(SpriteBatch batch) {
        if(x+xSpeed < Gdx.graphics.getWidth() - 250) {
            x += xSpeed;
        }
        batch.draw(textureRight, x, y);
        collisionRect.move(x, y);
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
    public CollisionRect getCollisionRect()
    {
        return collisionRect;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

}
