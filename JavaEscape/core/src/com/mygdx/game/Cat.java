package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Cat extends Texture {
    private static final FileHandle INTERNALPATH = Gdx.files.internal("Cat/cat.png");
    private float x = 300;
    private float y = 500;
    private int width = 115;
    private int height = 115;
    private Dialog catDialog, catDialogOptionA, catDialogRoom2;
    private TextButton buttondialogOptionA, buttondialogOptionB;

    private final CollisionRect collisionRect = new CollisionRect(x, y, width, height);
    private final Sound catSound = Gdx.audio.newSound(Gdx.files.internal("Cat/catSound.mp3"));
    private final Sound catSound2 = Gdx.audio.newSound(Gdx.files.internal("Cat/catSound2.mp3"));
    private boolean dialogState;

    public Cat()
    {
        super(INTERNALPATH);
    }
    public void createDialog(final Stage stage, Skin skin) {
        dialogState = false;

        catDialog = new Dialog("CAT: Miauw! Feed me please!", skin);
        buttondialogOptionA = new TextButton("Feed him", skin);
        buttondialogOptionB = new TextButton("Ignore him", skin);
        catDialog.getButtonTable().add(buttondialogOptionA, buttondialogOptionB);
        catDialogOptionA = new Dialog("CAT: Thank you, let's go to the next room!", skin);
        catDialogOptionA.button("OK!");

        catDialogRoom2 = new Dialog("CAT: We are in the second room!" , skin);
        catDialogRoom2.text("If I remember correctly, I think some of the owners of the house hid a key in the grass.");
        catDialogRoom2.setSize(650, 100);
        catDialogRoom2.setPosition(300, 350);
        catDialogRoom2.button("Let me find it");

        buttondialogOptionA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catSound2.play(5);
                catDialog.remove();
                buttondialogOptionA.remove();
                buttondialogOptionB.remove();
                catDialogOptionA.show(stage);
            }
        });
        buttondialogOptionB.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                catDialog.remove();
            }
        });
        catDialogOptionA.getButtonTable().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialogState = true;
                stage.addActor(catDialogRoom2);
            }
        });
    }
    public Dialog getCatDialog()
    {
        return catDialog;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public boolean isDialogState()
    {
        return dialogState;
    }

    public CollisionRect getCollisionRect()
    {
        return collisionRect;
    }

    public Sound getCatSound()
    {
        return catSound;
    }
}
