package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Text;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.io.*;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.Gdx.gl;

public class MyGdxGame extends ApplicationAdapter {
	private Sound startupSound;
	private Texture background1,background2;
	private Character character;
	private Cat cat;
	private SpriteBatch batch;
	private Stage stage;
	private Skin skin;
	public static float GAME_WIDTH;
	public static float GAME_HEIGHT;
	private CollisionRect collisionRectRoom2;

	private Dialog endDialog, statDialog;

	private long startTime, totalTime;
	private String time;
	private GameState gameState;

	private Random random = new Random();

	@Override
	public void create () {
		// GameState
		gameState = new GameState();
		gameState.setGameState(GameState.GameRooms.ONE);
		gameState.setStartMovingPerms(false);
		//Game width and height to use for Actor and Texture positioning.
		GAME_WIDTH = Gdx.graphics.getWidth();
		GAME_HEIGHT = Gdx.graphics.getHeight();

		//Creation of the batch, stage and skin that will be the base of the whole game.
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		skin = new Skin(Gdx.files.internal("Scene2D/uiskin.json"));
		Gdx.input.setInputProcessor(stage);

		//CollisionRect for the room 2 mini-game.
		collisionRectRoom2 = new CollisionRect(random.nextInt(1000), random.nextInt(650), 5, 5);

		//Backgrounds
		background1 = new Texture(Gdx.files.internal("Background/background.jpg"));
		background2 = new Texture(Gdx.files.internal("Background/background2.jpg"));

		//The cat
		cat = new Cat();


		//Main character
		character = new Character(5, 5);
		gameState.setGameState(GameState.GameRooms.ONE);

		//startup sound
		startupSound = Gdx.audio.newSound(Gdx.files.internal("Background/game-start.mp3"));
		startupSound.play(10);

		//Graphics (Scene2D)

		//Textbutton "JavaEscape - Click here to start."
		final TextButton textButton = new TextButton("JavaEscape - Click here to start.", skin, "default");
		textButton.setSize(500, 100);
		textButton.setPosition(GAME_WIDTH/2- textButton.getWidth()/2, GAME_HEIGHT/2);
		stage.addActor(textButton);

		//First Dialog "JavaEscape - Escape the room!"
		final Dialog dialog = new Dialog("JavaEscape - Escape the room!", skin, "default");
		dialog.setSize(650, 280);
		dialog.setPosition(GAME_WIDTH/2-dialog.getWidth()/2, 300);
		dialog.getContentTable().add(createTable(skin));
		dialog.button("GO!");

		//catDialog
		cat.createDialog(stage, skin);

		//End Dialog "You won the game"
		endDialog = new Dialog("You won the game! You found the hidden key! Congratulations.", skin);
		endDialog.hide();
		endDialog.button("Let me see the stats!");
		stage.addActor(endDialog);

		//Statistics Dialog
		statDialog = new Dialog("Here are you statistics.", skin);
		statDialog.hide();
		statDialog.button("Ok");
		stage.addActor(statDialog);


		//Listeners
		dialog.getButtonTable().addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				gameState.setStartMovingPerms(true);
				startTime = System.currentTimeMillis();
			}
		});
		textButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				textButton.remove();
				stage.addActor(dialog);
			}
		});
		endDialog.getButtonTable().addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				totalTime = System.currentTimeMillis() - startTime;
				System.out.println(totalTime);
				time = "Time to complete the game: " + totalTime/60000 + "m " + (totalTime/1000) + "s";
				statDialog.text(time);
				statDialog.setSize(650, 400);
				statDialog.show(stage);
			}
		});
		statDialog.getButtonTable().addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				System.exit(0);
			}
		});

	}
	public Table createTable(Skin skin) {
		Table table = new Table(skin);
		table.add(new Label("Hello!", skin));
		table.row();
		table.add(new Label("I am Thomas, the creator of the game.", skin) );
		table.row();
		table.add(new Label("The goal of the game is to escape the rooms.", skin));
		table.row();
		table.add(new Label("You will find clues and items scattered around the room that will help you escape.", skin));
		table.row();
		table.add(new Label("The first room is simple, just to get you into the feeling of the game.", skin));
		table.row();
		table.row();
		table.add(new Label("Controls: ", skin));
		table.row();
		table.add(new Label("W A S D to move. E to interact with something on your path, like a cat. :) ", skin));
		table.row();
		table.add(new Label("Press the button 'GO!' to start the game.", skin));

		return table;
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
		gl.glClearColor(1, 1, 1, 1);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		//Backgrounds for Rooms
		if(gameState.getGameState() == GameState.GameRooms.ONE) {
			batch.draw(background1,0,0);
			batch.draw(background1, 0, 360);
			batch.draw(background1,546,0);
			batch.draw(background1,546,360);
		}
		if(gameState.getGameState() == GameState.GameRooms.TWO) {
			batch.draw(background2, 0, 0);
			batch.draw(background2, 0, 360);
			batch.draw(background2, 546, 0);
			batch.draw(background2, 546, 360);

		}
		if(cat.isDialogState()) {
			gameState.setGameState(GameState.GameRooms.TWO);
		}
		//Cat
		batch.draw(cat, cat.getX(), cat.getY(), cat.getWidth(), cat.getHeight());

		//Character movement & interaction
		if(Gdx.input.isKeyPressed(Input.Keys.W) && gameState.isStartMovingPerms()){
			character.moveNorth(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)&& gameState.isStartMovingPerms()){
			character.moveSouth(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)&& gameState.isStartMovingPerms()){
			character.moveEast(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.A)&& gameState.isStartMovingPerms()){
			character.moveWest(batch);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.E) && gameState.isStartMovingPerms()) {
			if(gameState.getGameState() == GameState.GameRooms.ONE) {
				if(character.getCollisionRect().collidesWith(cat.getCollisionRect())) {
					cat.getCatSound().play(5);
					cat.getCatDialog().show(stage);
				}
			}
			else if(gameState.getGameState() == GameState.GameRooms.TWO) {
				if(character.getCollisionRect().collidesWith(collisionRectRoom2)) {
					endDialog.show(stage);
				}
			}
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
		background1.dispose();
		startupSound.dispose();
		stage.dispose();
	}
}
