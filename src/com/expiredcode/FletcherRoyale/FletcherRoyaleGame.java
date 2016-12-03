package com.expiredcode.FletcherRoyale;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class FletcherRoyaleGame extends Game {

	MyUtils m = new MyUtils();
	
	@Override
	public void render()
	{

		GameScreen currentScreen = getScreen();

        // update the screen
        currentScreen.render(Gdx.graphics.getDeltaTime());

        // When the screen is done we change to the
        // next screen. Ideally the screen transitions are handled
        // in the screen itself or in a proper state machine.
        if (currentScreen.isDone()) 
        {
               /* // dispose the resources of the current screen
               currentScreen.dispose();
              switch(m.screen)
                {
                	case 0:{
                				setScreen(new Home());
                		
                			}break;
                	
                	case 1:{
                				setScreen(new Level());
                		
                			}break;/*
                	case 2:{
                				setScreen(new GameOver());                		
                	}break;/*
                	
                	case 3:{
                		setScreen(new LevelTwo());                    		
                	}break;
                	
                	case 4:{
                		setScreen(new LevelThree());
                	}break;
                }*/
        }		
	}
	@Override
	public void create() 
	{
		setScreen(new FletcherRoyale());
		//setScreen(new Home());		
	}
	
	@Override
	public void dispose()
	{
		
	}
	@Override
    public GameScreen getScreen () 
	{
            return (GameScreen)super.getScreen();
    }
}
