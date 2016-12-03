package com.expiredcode.FletcherRoyale;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FletcherRoyale extends GameScreen {
	
	MyUtils myu = new MyUtils();
	
	SpriteBatch batch;
	Texture wall, player,arrow;
	boolean done = false;
	
	Iterator it;
	Collider c;
	Arrow a;
	
	Player p,p1,p2,p3,p4;
	
	OrthographicCamera camera;
	
	Init i;
	
	FletcherRoyale()
	{
		(new Client()).start(); //starting the client thread
		
		i=new Init();
		
		batch = new SpriteBatch();
		wall = new Texture(Gdx.files.internal("data/wall.png"));
		player = new Texture(Gdx.files.internal("data/p1.png"));
		arrow = new Texture(Gdx.files.internal("data/arrow.png"));
			
		p1 = new Player(100,200,"P1");
		myu.players.add(p1);
		p2 = new Player(100,400,"P2");
		myu.players.add(p2);
		p3 = new Player(400,100,"P3");
		myu.players.add(p3);
		p4 = new Player(400,400,"P4");
		myu.players.add(p4);
		
		myu.p = p1;
		playerInit();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, myu.WORLD_WIDTH, myu.WORLD_HEIGHT);
	}

	@Override
	public void render (float deltatime) 
	{
		update(deltatime);
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		it=myu.walls.iterator();
		while(it.hasNext())
		{			
			c = (Collider) it.next();			
			batch.draw(wall, c.x,c.y,c.width,c.height);			
		}		
		it=myu.arrow.iterator();
		while(it.hasNext())
		{			
			a = (Arrow) it.next();	
			if(a.goingleft)
				batch.draw(arrow, a.pos.x+a.pw,a.pos.y,-a.pw,a.ph);
			else
				batch.draw(arrow, a.pos.x,a.pos.y,a.pw,a.ph);
		}
		it = myu.players.iterator();
		while(it.hasNext())
		{
			p = (Player) it.next();
			if(p.alive)
			if(p.goingleft)
				batch.draw(player, p.player.x+p.pw,p.player.y,-p.pw,p.ph);
			else
				batch.draw(player, p.player.x,p.player.y,p.pw,p.ph);
		}
		
	//	batch.draw(img,0,0);
		batch.end();
	}
	
	void update(float delta)
	{
		myu.p.update(delta);
		
		it=myu.arrow.iterator();
		while(it.hasNext())
		{			
			a = (Arrow) it.next();	
			a.update(delta);
		}
	}
	
	void playerInit()
	{
		//if(!m.players.isEmpty())
			if(myu.pname.equals("P1"))
				myu.p = myu.players.get(0);
			else
				if(myu.pname.equals("P2"))
					myu.p = myu.players.get(1);
				else
					if(myu.pname.equals("P3"))
						myu.p = myu.players.get(2);
					else
						if(myu.pname.equals("P4"))
							myu.p = myu.players.get(3);
	}
	
	
	public boolean isDone() {
		// TODO Auto-generated method stub
	
		return done;
	}
	
	@Override
	public void dispose()
	{
		
	}
}
