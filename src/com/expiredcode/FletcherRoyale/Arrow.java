package com.expiredcode.FletcherRoyale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Arrow extends Collider
{
	MyUtils myu = new MyUtils();
	
	public Vector2 pos;
	
	boolean goingleft = false,alive;
	String name;
	
	float speed = 300;
	static float pw=64/2,ph=8/2;
	
	Collider c;
	
	Vector2 lowleft,topleft,topright,lowright;
	
	Iterator it;
	
	Arrow(String n, float x,float y,boolean left)
	{
		super(n, x, y, pw, ph);
		name=n;
		pos = new Vector2(x,y);
		goingleft = left;
		alive = true;
		
		lowleft = new Vector2();
		topleft = new Vector2();
		topright = new Vector2();
		lowright = new Vector2();
	}
	
	void update(float deltatime)
	{
		if(alive)
		{
			if(goingleft)
			{
				pos.x-=speed*deltatime;
				if(pos.x<-100)
					pos.x=myu.WORLD_WIDTH+50;
			}else
			{
				pos.x+=speed*deltatime;
				if(pos.x>myu.WORLD_WIDTH+50)
					pos.x=-100;
			}
			
			collision();			
		}
		
		
	}	
	
	public void collision()
	{
		//reset all collision checkers
		//	leftcollision=false; rightcollision=false; topcollision=false; lowcollision=false;
		it=myu.walls.iterator();
			
		//gravity
			
			
		//for(int i=0;i<col.size();i++)
		while(it.hasNext())
		{
			c=(Collider) it.next();
			lowleft.x=c.x; lowleft.y=c.y;
			topleft.x=c.x; topleft.y=c.y+c.height;
			lowright.x=c.x+c.width; lowright.y=c.y;
			topright.x=c.x+c.width; topright.y=c.y+c.height;
			
					
			
			//check x-axis 
			//left collision			
			//if(Math.abs(pos.x-lowright.x)<10) //per evitare concorrenze tra attivazione collisione laterale o top\bottom, si attiva quella più vicina
			if(pos.x<=lowright.x && pos.x>lowleft.x)
			{	
				if((pos.y>=lowright.y&&pos.y<topright.y)||(pos.y+ph>lowright.y&&pos.y+ph<=topright.y))
				{
					alive = false;
				}
			}
			
			//right collision
			//if(Math.abs(pos.x+pw-lowleft.x)<10)
			if(pos.x+pw>=lowleft.x&&pos.x+pw<lowright.x)
			{	//Gdx.app.log("a","a"+c.name);
				if((pos.y>=lowleft.y&&pos.y<topleft.y)||((pos.y+ph>lowleft.y)&&(pos.y+ph<topleft.y)))
				{
					alive = false;
				}				
			}
			
				/*
					if(ascending)
					{
						if(Math.abs(pos.y+ph-lowleft.y)<10)
						if((pos.y+ph>lowleft.y)&&(pos.y+ph<topleft.y))
							if(((pos.x>lowleft.x)&&(pos.x<lowright.x))||((pos.x+pw>lowleft.x)&&(pos.x+pw<lowright.x)))
							{
								
							}
					}else
						if(descending)
						{
							if(Math.abs(pos.y-topleft.y)<10)
							if(pos.y<=topleft.y&&pos.y>lowleft.y)
								if((pos.x>=topleft.x && pos.x<=topright.x)||(pos.x+pw>=topleft.x&&pos.x+pw<=topright.x))
								{
									
								}
						}			
			}*/	
		}
	}

}
