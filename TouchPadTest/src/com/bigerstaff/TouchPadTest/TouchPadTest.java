package com.bigerstaff.TouchPadTest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class TouchPadTest implements ApplicationListener {
	private OrthographicCamera camera;
	private Stage stage;
	private SpriteBatch batch;
	private Touchpad touchpad;
	private TouchpadStyle touchpadStyle;
	private Skin touchpadSkin;
	private Drawable touchBackground;
	private Drawable touchKnob;
	private Texture blockTexture;
	private Sprite blockSprite;
	private float blockSpeed;
	
	@Override
	public void create() {		
		batch = new SpriteBatch();
		//Create camera
		float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 10f*aspectRatio, 10f);
		
		//Create a touchpad skin	
		touchpadSkin = new Skin();
		//Set background image
		touchpadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
		//Set knob image
		touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
		//Create TouchPad Style
		touchpadStyle = new TouchpadStyle();
		//Create Drawable's from TouchPad skin
		touchBackground = touchpadSkin.getDrawable("touchBackground");
		touchKnob = touchpadSkin.getDrawable("touchKnob");
		//Apply the Drawables to the TouchPad Style
		touchpadStyle.background = touchBackground;
		touchpadStyle.knob = touchKnob;
		//Create new TouchPad with the created style
		touchpad = new Touchpad(10, touchpadStyle);
		//setBounds(x,y,width,height)
		touchpad.setBounds(15, 15, 200, 200);
		
		//Create a Stage and add TouchPad
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true, batch);
		stage.addActor(touchpad);			
		Gdx.input.setInputProcessor(stage);
		
		//Create block sprite
		blockTexture = new Texture(Gdx.files.internal("data/block.png"));
		blockSprite = new Sprite(blockTexture);
		//Set position to centre of the screen
		blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);

		blockSpeed = 5;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
    	Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        //Move blockSprite with TouchPad
        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY()*blockSpeed);
        
        //Draw
        batch.begin();
        blockSprite.draw(batch);
        batch.end();      
	    stage.act(Gdx.graphics.getDeltaTime());	    
	    stage.draw();
	    
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int width, int height) {		
	}
}
