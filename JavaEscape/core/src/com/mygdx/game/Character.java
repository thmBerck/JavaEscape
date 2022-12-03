package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.w3c.dom.Text;

import java.util.HashMap;

public class Character {
    private Texture textureFront = new Texture(Gdx.files.internal("frontside.gif"));
    private Texture textureBehind = new Texture(Gdx.files.internal("backside.gif"));
    private Texture textureLeft = new Texture(Gdx.files.internal("leftview.gif"));
    private Texture textureRight = new Texture(Gdx.files.internal("rightview.gif"));

    private int x;
    private int y;
    int xSpeed;
    int ySpeed;
    public Character(int charSpeedX, int charSpeedY)
    {
        this.xSpeed = charSpeedX;
        this.ySpeed = charSpeedY;
    }
    public void drawCharacter(SpriteBatch batch) {
        batch.draw(textureFront, x, y);
    }

    public void moveNorth(SpriteBatch batch) {
        if(y+ySpeed < Gdx.graphics.getHeight() - 150)
            y += ySpeed;
        batch.draw(textureBehind, x, y);


    }
    public void moveSouth(SpriteBatch batch) {
        if(y-ySpeed > -70)
            y -= ySpeed;
        batch.draw(textureFront, x, y);
    }
    public void moveWest(SpriteBatch batch) {
        if(x-xSpeed > -50)
            x -= xSpeed;
        batch.draw(textureLeft, x, y);

    }
    public void moveEast(SpriteBatch batch) {
        if(x+xSpeed < Gdx.graphics.getWidth() -250)
            x += xSpeed;
        batch.draw(textureRight, x, y);
    }



    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
