package com.expiredcode.FletcherRoyale;

import java.net.InetAddress;

public class ServerPlayer 
{
	String name;
	float x,y;
	boolean alive;
	boolean active; //true=there is a player associated with this object
	boolean goingLeft;
	
	float lastseen; 
	
	public InetAddress ip; 
	private int port=9870;
	
	ServerPlayer(String n)
	{
		name = n;
		alive = false;
		active = false;		
		x=0;
		y=0;
		goingLeft = false;
	}


}
