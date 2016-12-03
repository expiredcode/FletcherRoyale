package com.expiredcode.FletcherRoyale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player 
{
	//int WORLD_WIDTH = 800, WORLD_HEIGHT = 480;
	
	MyUtils myu = new MyUtils();
	
	public Vector3 player = new Vector3();
	public Vector2 temp = new Vector2();
	
	String name;
	
	Collider c;
	Arrow a;
	
	Vector2 lowleft,topleft,topright,lowright;
	
	boolean alive = true;
	boolean active = true;
	boolean grounded = true;
	boolean goingleft = false;
	boolean ascending=false,descending=false;
	boolean leftcollision,rightcollision,topcollision,lowcollision;
	boolean end =false,cont=false;
	boolean done = false;
	boolean canhang=true, hang = false,righthang=false,lefthang=false,w=false;
	
	List<Collider> col = new ArrayList<Collider>();
	Iterator it;
	
	float speed=160,jumpspeed=315,cjs; //cjs->current jump speed?
	float delta = 0,enddelta=0,shootdelay=100;
	float pw=52/2f,ph=64/2f,groundLevel=42;
	float hangdelay=0,hanging=0,jd=1;
	float a_1,a_2,a_3;
	float life =100;
	
	Player(float x,float y,String n)	
	{
		name=n;
		player.set(x, y, 0);
		leftcollision=false;
		rightcollision=false;
		topcollision=false;
		lowcollision=false;
		lowleft = new Vector2();
		topleft = new Vector2();
		topright = new Vector2();
		lowright = new Vector2();
	}
	
	void update(float deltatime)
	{
		
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			w=true;
			if(!ascending&&((!descending&&grounded)||hang))
			{	if(jd>0.28)
				{
					ascending=true;
					grounded = false;
					descending = false;
					hang=false;
					if(lefthang)
						player.x=player.x+2;
					if(righthang)
						player.x=player.x-2;
					cjs=0;
					jd=0;
				}else
					jd+=deltatime;
			}
			if(hangdelay<0.01)
				descending=false;
			
		}else w=false;
		//Gdx.app.log("p", ""+player.toString());
		if(Gdx.input.isKeyPressed(Input.Keys.A)&&!leftcollision)
		{
			player.x-=speed*deltatime;
			goingleft=true;
	//		////Gdx.app.log("A", "A");
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)&&!rightcollision)
		{
			player.x+=speed*deltatime;
			goingleft=false;
	//		////Gdx.app.log("D", "D");
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
		{
			if(shootdelay>0.35)
			{
				shootdelay=0;
				if(goingleft)
				{
					myu.arrow.add(new Arrow(name+"arrow",player.x-10,(player.y+ph/2.5f),goingleft));
				}else{
					myu.arrow.add(new Arrow(name+"arrow",player.x+pw+10,(player.y+ph/2.5f),goingleft));
				}
			}else{
				shootdelay+=deltatime;
			}
		}
		
		if(ascending)
		{
		//	if(player.y < WORLD_HEIGHT)
		//	{
				cjs+=10;
				player.y += (jumpspeed-cjs)*deltatime;
				if(jumpspeed-cjs<0)
				{
					ascending=false;
					descending = true;
				}
				
		//	}
			
		}else
			if(descending&&!hang)
			{
				player.y-= 210*deltatime;
				////Gdx.app.log("gravity",""+250*deltatime);
				if(player.y<0)
				{
					player.y=myu.WORLD_HEIGHT;//groundLevel;
					grounded = true;
					descending = false;
					jd=1;
				}
			}
		if(player.x-5<0-pw)
			player.x=myu.WORLD_WIDTH-5;
		
		if(player.x+5>myu.WORLD_WIDTH)
			player.x=0-pw+5;
		
		//Gdx.app.log("x",""+player.x);
		
		if(Gdx.input.isTouched())
		{
			float x,y;
			x = Gdx.input.getX();
			y = myu.WORLD_HEIGHT - Gdx.input.getY(); 
			//camera.position.x+336,camera.position.y+198);
			if(x>736&&x<768)
				if(y>438&&y<470)
				{
					myu.screen=1;
					done=true;
				}
		}
		if(hangdelay>0.3)
		{
			canhang=true;
			//descending=true;
			//hangdelay=0;
		}
		else
		{
			hangdelay+=deltatime;
		}
		if(hangdelay>0.01)
			descending=true;
		//Gdx.app.log("a", ""+hanging);
		if(hanging>1&&hang)
		{
			hang=false;
			//hanging=0;
			if(lefthang)
				player.x=player.x+2;
			if(righthang)
				player.x=player.x-2;
		}
		else
			hanging+=deltatime;
		
		arrowcollision();
		collision();
	}
	
	
	public void collision()
	{
		//reset all collision checkers
		leftcollision=false; rightcollision=false; topcollision=false; lowcollision=false;
		it=myu.walls.iterator();
		
		//gravity
		if(grounded&&player.y>groundLevel)
		{
			player.y-=10;
			grounded = false;
			descending=true;
		}
		
		//for(int i=0;i<col.size();i++)
		while(it.hasNext())
		{
			c=(Collider) it.next();
			lowleft.x=c.x; lowleft.y=c.y;
			topleft.x=c.x; topleft.y=c.y+c.height;
			lowright.x=c.x+c.width; lowright.y=c.y;
			topright.x=c.x+c.width; topright.y=c.y+c.height;
			
						
			//if !grounded check y-axis
		/*	if(!grounded)
			{
				//top collision
			*/	
			
			//invertito controllo collisioni sx dx con top e bottom
			
			
			//check x-axis 
			//left collision			
			if(Math.abs(player.x-lowright.x)<10) //per evitare concorrenze tra attivazione collisione laterale o top\bottom, si attiva quella più vicina
			if(player.x<=lowright.x && player.x>lowleft.x)
			{	
				if((player.y>=lowright.y&&player.y<topright.y)||(player.y+ph>lowright.y&&player.y+ph<=topright.y))
				{
					/*if(c.name.equals("arrow"))
					{
						it.remove();
					}else{*/
						
						if(w&&((ascending||descending)&&(canhang)))
						{
							if(ascending)
							{
								ascending=false;
								descending=true;
							}
							if(!hang)
								hanging=0;
							hang=true;
							lefthang=true;
							leftcollision=true;
							
							canhang=false;
							//hangdelay=0;
							player.x=lowright.x;
							
						}else{
							leftcollision=true;
							player.x=lowright.x+1;
						}
					//}
					
					//	Gdx.app.log("Lcollision", c.name+player.toString());
					//	return;
					if(check(c))
					{
						
					}
				}
			}
			
			//right collision
			if(Math.abs(player.x+pw-lowleft.x)<10)
			if(player.x+pw>=lowleft.x&&player.x+pw<lowright.x)
			{	//Gdx.app.log("a","a"+c.name);
				if((player.y>=lowleft.y&&player.y<topleft.y)||((player.y+ph>lowleft.y)&&(player.y+ph<topleft.y)))
				{
					//Gdx.app.log("a","b"+c.name);
				/*	if(c.name.equals("arrow"))
					{
						it.remove();
					}else{*/
					if(w&&((ascending||descending)&&(canhang)))
					{
						if(ascending)
						{
							ascending=false;
							descending=true;
						}
						if(!hang)
							hanging=0;
						hang=true;
						righthang=true;
						rightcollision=true;
						
						canhang=false;
					//	hangdelay=0;
						player.x=lowleft.x-pw;
						
					}else{
						rightcollision=true;
						player.x=lowleft.x-1-pw;
					}
					//}
					//Gdx.app.log("Rcollision", c.name+player.toString());
					//	return;
					if(check(c))
					{
						it.remove();
					}		
				}				
			}
			
			
				if(ascending)
				{
					if(Math.abs(player.y+ph-lowleft.y)<10)
					if((player.y+ph>lowleft.y)&&(player.y+ph<topleft.y))
						if(((player.x>lowleft.x)&&(player.x<lowright.x))||((player.x+pw>lowleft.x)&&(player.x+pw<lowright.x)))
						{
							/*if(c.name.equals("arrow"))
							{
								it.remove();
							}else{*/
							topcollision = true;
							ascending = false;
							descending = true;						
							cjs=0;
							player.y=lowleft.y-1-ph;
						//	Gdx.app.log("Tcollision", c.name);
							//			return;
							if(check(c))
							{
								it.remove();
							}
						//	}
						}
				}else
					if(descending)
					{
						if(Math.abs(player.y-topleft.y)<10)
						if(player.y<=topleft.y&&player.y>lowleft.y)
							if((player.x>=topleft.x && player.x<=topright.x)||(player.x+pw>=topleft.x&&player.x+pw<=topright.x)||
									(player.x<topleft.x && player.x+pw>topright.x))
							{/*if(c.name.equals("arrow"))
							{
								it.remove();
							}else{*/
								lowcollision = true;
								player.y=topleft.y+1;
								grounded=true;
								descending=false;
								jd=1;
								//Gdx.app.log("collision", c.name+player.toString());
							
								if(check(c))
								{
									it.remove();
								}
							//}
							//	continue;
							}
					}				
		}
		if(!rightcollision&&righthang)
		{
			hang=false;
			righthang=false;
			hangdelay=0;
		}
		if(!leftcollision&&lefthang)
		{
			hang=false;
			lefthang=false;
			hangdelay=0; 
		}
	}
	
	public void arrowcollision()
	{
		it = myu.arrow.iterator();
		
		while(it.hasNext())
		{
			a = (Arrow)it.next();
			
				a_1=a.pos.x+a.pw/4;
				a_2=a.pos.x+a.pw/2;
				a_3=a.pos.x+(a.pw/4)*3;
				
				if((a.pos.y+a.ph/2)>player.y&&(a.pos.y+a.ph/2)<player.y+ph)
				{
					if((a_1>player.x&&a_1<player.x+ph)||(a_2>player.x&&a_2<player.x+ph)||(a_3>player.x&&a_3<player.x+ph))
						if(!a.alive)
						{
							it.remove();
						}
						else
						{
							if(!a.name.contains(name))
							{
								life-=Math.random()*50;
								it.remove();
							}
							if(life<0)
							{
								//remove from arena
								alive = false;
							}
							
						}
					
				}
			
			
		}
	}

	boolean check(Collider c)
	{
		if(c.name.equals("o"))
		{
			
			return true;
			
		}else
			if(c.name.equals("n"))
			{				
				
				return true;
				
			}else
				if(c.name.equals("e"))
				{
					
					return true;
				}
		return false;
	}

}
