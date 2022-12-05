package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;

public class MyGdxGame extends ApplicationAdapter {
	private Sound sound;
	private Texture background;
	private Character character;
	private SpriteBatch batch;

	private Texture cat;
	private Stage stage;
	private Skin skin;
	private Dialog dialog;
	private static float GAME_WIDTH;
	private static float GAME_HEIGHT;
	private boolean gameState;

	@Override
	public void create () {
		GAME_WIDTH = Gdx.graphics.getWidth();
		GAME_HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("Scene2D/uiskin.json"));

		Gdx.input.setInputProcessor(stage);

		//Background
		background = new Texture(Gdx.files.internal("Background/background.jpg"));

		//The cat
		cat = new Texture(Gdx.files.internal("Cat/cat.png"));

		//Main character
		character = new Character(5, 5);

		//startup sound
		sound = Gdx.audio.newSound(Gdx.files.internal("Background/game-start.mp3"));
		sound.play(10);

		//Graphics (scene2d)

		final TextButton textButton = new TextButton("JavaEscape - Click here to start.", skin, "default");
		textButton.setSize(500, 100);
		textButton.setPosition(GAME_WIDTH/2- textButton.getWidth()/2, GAME_HEIGHT/2);

		final Dialog dialog = new Dialog("Clicked Message", skin, "default");
		dialog.setSize(100, 100);
		dialog.setPosition(150, 200);

		stage.addActor(textButton);

		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.show(stage);
				gameState = true;
			}
		});
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}


	/**
	 * Background is drew 4 times to fill up the screen. I couldn't find a way to fill up the whole screen without having quality loss.
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Background
		batch.begin();
		batch.draw(background,0,0);
		batch.draw(background, 0, 360);
		batch.draw(background,546,0);
		batch.draw(background,546,360);


		//Cat
		batch.draw(cat, 300, 500, 115, 115);

		//Graphics scene2d


		//Character movement
		if(Gdx.input.isKeyPressed(Input.Keys.W) && gameState){
			character.moveNorth(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)&& gameState){
			character.moveSouth(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)&& gameState){
			character.moveEast(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.A)&& gameState){
			character.moveWest(batch);
		}
		else{
			character.drawCharacter(batch);
		}

		batch.end();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		sound.dispose();
		stage.dispose();
	}
}
