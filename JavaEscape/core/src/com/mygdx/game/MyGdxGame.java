package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;

public class MyGdxGame extends ApplicationAdapter {
	private Texture background;
	private Character character;
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture(Gdx.files.internal("background.jpg"));
		character = new Character(5, 5);
	}

	/**
	 * Background is drew 4 times to fill up the screen. I couldn't find a way to fill up the whole screen without having quality loss.
	 */
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background,0,0);
		batch.draw(background, 0, 360);
		batch.draw(background,546,0);
		batch.draw(background,546,360);
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			character.moveNorth(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)){
			character.moveSouth(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)){
			character.moveEast(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.A)){
			character.moveWest(batch);
		}
		else{
			character.drawCharacter(batch);
		}

		batch.end();
//


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
