package com.mygdx.gamestates;

import java.io.IOException;

import resCode.SimpleButton;
import handlers.Animation;
import handlers.Assets;
import handlers.SaveAndLoad;
import handlers.SingletonVandC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class LevelMenu extends GameState {

	SpriteBatch batch;	
	SingletonVandC singleton;
	OrthographicCamera cam;

	private int moving = 0;
	private int robotX = -2;

	private SimpleButton levelOne;
	private SimpleButton levelTwo;
	private SimpleButton levelThree;
	private SimpleButton levelFour;
	private SimpleButton levelFive;
	private SimpleButton levelSix;
	private SimpleButton levelSeven;
	private SimpleButton levelEight;
	private SimpleButton levelNine;
	private SimpleButton levelTen;
	private SimpleButton blockedLevel;

	private float pix, piy;

	Animation robot;

	public LevelMenu(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		SingletonVandC.currentLevel = 0;
		try {
			SaveAndLoad.loadGame();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		singleton = SingletonVandC.getSingleton();
		batch = new SpriteBatch();
		cam = new OrthographicCamera();

		robot = new Animation();

		TextureRegion[] sprites = TextureRegion.split(Assets.manager.get(Assets.robotRight), 21, 21)[0];
		robot.setFrames(sprites,  1 / 12f);

		cam.setToOrtho(false, singleton.SCREEN_WIDTH, singleton.SCREEN_HEIGHT);
		cam.update();

		pix = singleton.SCREEN_WIDTH / 12;
		piy = singleton.SCREEN_HEIGHT / 9;

		levelOne = new SimpleButton(Assets.manager.get(Assets.levelOne), pix, 7 * piy, piy, piy);
		levelTwo = new SimpleButton(Assets.manager.get(Assets.levelTwo), 2 * pix, 7 * piy, piy, piy);
		levelThree = new SimpleButton(Assets.manager.get(Assets.levelThree), 3 * pix, 7 * piy, piy, piy);
		levelFour = new SimpleButton(Assets.manager.get(Assets.levelFour), 4 * pix, 7 * piy, piy, piy);
		levelFive = new SimpleButton(Assets.manager.get(Assets.levelFive), 5 * pix, 7 * piy, piy, piy);
		levelSix = new SimpleButton(Assets.manager.get(Assets.levelSix), 6 * pix, 7 * piy, piy, piy);
		levelSeven = new SimpleButton(Assets.manager.get(Assets.levelSeven), 7 * pix, 7 * piy, piy, piy);
		levelEight = new SimpleButton(Assets.manager.get(Assets.levelEight), 8 * pix, 7 * piy, piy, piy);
		levelNine = new SimpleButton(Assets.manager.get(Assets.levelNine), 9 * pix, 7 * piy, piy, piy);
		levelTen = new SimpleButton(Assets.manager.get(Assets.levelTen), 10 * pix, 7 * piy, piy, piy);

		blockedLevel = new SimpleButton(Assets.manager.get(Assets.blockedLevel), pix, 5 * piy, piy, piy);
	}

	@Override
	public void update(float dt) {
		if (moving == 0 && robotX < singleton.SCREEN_WIDTH / 14)
			robotX += 10;
		if (moving == 1)
			robotX += 10;
		
		robot.update(dt);
		
		if (SingletonVandC.currentLevel == 0)
			handleInput();
		
		if (robotX > singleton.SCREEN_WIDTH)
			gameStateManager.setState(singleton.PLAY);
	}

	@Override
	public void render() {
		Gdx.gl20.glClearColor(217 / (float) 256, 208 / (float) 256, 179 / (float) 256, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		float frac = singleton.SCREEN_WIDTH / 600;

		batch.setProjectionMatrix(cam.combined);

		batch.begin();
		batch.draw(Assets.manager.get(Assets.levelMenu), 0, 0, singleton.SCREEN_WIDTH, singleton.SCREEN_HEIGHT);
		batch.draw(robot.getFrame(), robotX, singleton.SCREEN_HEIGHT / 4.5f, robot.getFrame().getRegionWidth() * 2 * frac, robot.getFrame().getRegionHeight() * 2 * frac);

		levelOne.draw(batch);
		drawLevel(levelTwo, 2);
		drawLevel(levelThree, 3);
		drawLevel(levelFour, 4);
		drawLevel(levelFive, 5);
		drawLevel(levelSix, 6);
		drawLevel(levelSeven, 7);
		drawLevel(levelEight, 8);
		drawLevel(levelNine, 9);
		drawLevel(levelTen, 10);

		blockedLevel.draw(batch,(int) (pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (2 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (3 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (4 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (5 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (6 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (7 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (8 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (9 * pix),(int) (5 * piy));
		blockedLevel.draw(batch,(int) (10 * pix),(int) (5 * piy));

		batch.end();
	}

	private void drawLevel(SimpleButton string, int i) {
		if (isAccessible(i))
			string.draw(batch);
		else
			blockedLevel.draw(batch, string.getX(), string.getY());
	}

	private boolean isAccessible(int i) {
		if (i < 2)
			return true;
		if (SingletonVandC.totalScore[i - 2] > -1)
			return true;
		return false;
	}

	@Override
	public void handleInput() {
		SingletonVandC.currentLevel = 0;
		if (!Gdx.input.justTouched())
			return;		
				
		if (touched(levelOne) && isAccessible(1))
		{
			SingletonVandC.currentLevel = 1;
			moving = 1;
		}
		else if (touched(levelTwo) && isAccessible(2))
		{
			SingletonVandC.currentLevel = 2;
			moving = 1;
		}
		else if (touched(levelThree) && isAccessible(3))
		{
			SingletonVandC.currentLevel = 3;
			moving = 1;
		}
		else if (touched(levelFour) && isAccessible(4))
		{
			SingletonVandC.currentLevel = 4;
			moving = 1;
		}
		else if (touched(levelFive) && isAccessible(5))
		{
			SingletonVandC.currentLevel = 5;
			moving = 1;
		}
		else if (touched(levelSix) && isAccessible(6))
		{
			SingletonVandC.currentLevel = 6;
			moving = 1;
		}
		else if (touched(levelSeven) && isAccessible(7))
		{
			SingletonVandC.currentLevel = 7;
			moving = 1;
		}
		else if (touched(levelEight) && isAccessible(8))
		{
			SingletonVandC.currentLevel = 8;
			moving = 1;
		}
		else if (touched(levelNine) && isAccessible(9))
		{
			SingletonVandC.currentLevel = 9;
			moving = 1;
		}
		else if (touched(levelTen) && isAccessible(10))
		{
			SingletonVandC.currentLevel = 10;
			moving = 1;
		}
	}

	private boolean touched(SimpleButton level) {
		if (Gdx.input.getX() > level.getX() && Gdx.input.getX() < (level.getX() + level.getWidth()) && Gdx.input.getY() < (singleton.SCREEN_HEIGHT - level.getY()) && Gdx.input.getY() > (singleton.SCREEN_HEIGHT - (level.getY() + level.getHeight())))
			return true;
		return false;
	}


	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
