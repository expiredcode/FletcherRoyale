package com.expiredcode.FletcherRoyale;

import java.io.*;
import java.net.*;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


public class Client extends Thread
{
	private Thread t=null;
	static MyUtils m = new MyUtils();
	private int state = 0;
	
	private DatagramSocket clientSocket,svSocket;
	private DatagramPacket sendPacket,receivePacket;
	
	byte[] receiveData = new byte[1024];
	byte[] sendData;
	
	private long timeDelay = System.currentTimeMillis();
	private long delay,lastSeen= System.currentTimeMillis();
	
	private boolean alive = false;
	
	private InetAddress IPAddress; 
	private int port=9870;
	
	private Iterator it;
	
	private Player p;
	
	private String[] s,pl;
	private String tip;
	private FileHandle han;
	
	public void start()//Client()
	{
		Gdx.app.log("tuo", "inclient");
		try{
			han=Gdx.files.internal("data/serverIp.txt");
			tip = han.readString();			
			//Gdx.app.log("tuo", tip);
			IPAddress = InetAddress.getByName(tip);//"localhost"); 
			//Gdx.app.log("tuo", "byname");
			clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(30);
			svSocket = new DatagramSocket(9876);
			svSocket.setSoTimeout(30);
		}catch(Exception e){Gdx.app.log("tuo", e.getMessage());}		
		
		//Gdx.app.log("tuo", "pre-loop");
		//mainLoop();
		if(t==null)
		{
			t=new Thread(this,"client");
			t.start();
		}
	}
	
	public void run()
	{
		/*try{
				clientSocket = new DatagramSocket();
				clientSocket.setSoTimeout(30);
				svSocket = new DatagramSocket();
				svSocket.setSoTimeout(30);
			}catch(Exception e){Gdx.app.log("tuo", e.getMessage());}		*/
		
		while(true)
		{
			Gdx.app.log("state", ""+state);
			
			switch(state)
			{
				case 0:{
					establishConnection();					
				}break;
				case 1:{  //game loop scene updating
					update();
					isLive();
				}break;
				case 2:{
					//terminateConnection();
				}break;
			}
			
			//clientSocket.close();
			//svSocket.close();
		}		
		
	}
	
	private void establishConnection()
	{
		try{
			send("Hello!1");
			timeDelay = System.currentTimeMillis();
			while(true)
			{
				s=receive().split("\\!");			//"OK!P1!data"
				if(s[0].equals("OK"))
				{
					state = 1;
					m.pname=s[1];					
						
					return;
				}else				
					if(System.currentTimeMillis()-timeDelay>500)
					{
						return;
					}
			}			
		}catch(Exception e){}		
	}
	
	private void update()
	{	
		try{
			unpackAndUpdate(receive());
		}catch(Exception e){}
		
		delay=System.currentTimeMillis()-timeDelay;
		if(delay>50)
		{
			send(build());
			timeDelay = System.currentTimeMillis();
		}
	}
	
	private String receive()
	{
		receiveData = new byte[1024];
		try{
			receivePacket = new DatagramPacket(receiveData,receiveData.length);
			svSocket.receive(receivePacket);
			
			
				Gdx.app.log("duh", "hell "+new String(receivePacket.getData()));
				
			lastSeen= System.currentTimeMillis();			
			return new String(receivePacket.getData());				
		}catch(Exception e){ return "";	}		
	}
	
	private void send(String s)
	{
		try{
			byte[] sendData = s.getBytes();			
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,IPAddress, port);			
			clientSocket.send(sendPacket);
			Gdx.app.log("OUT",s);
		}catch(Exception e){Gdx.app.log("e",""+e.toString());}		
	}
	
	private String build()
	{
		String st="";		
		
		it = m.arrow.iterator();
		
		st+=m.p.name+"!"+m.p.alive+"!"+m.p.player.x+"!"+m.p.player.y+"!"+m.p.goingleft;
		
		
		return st;//"prova$player1";
	}
	
	private void unpackAndUpdate(String s)	//p.name+"!"+p.alive+"!"+p.x+"!"+p.y+"!"+p.goingLeft+"!"+p.active+"$";
	{
		String[] data = s.replaceAll("\\s+", "").split("\\$");	
		it = m.players.iterator();
		
		for(int i = 0; i<4;i++)
		{
			pl = data[i].split("\\!");
			p=m.players.get(i);
			if(!p.name.equals(m.p.name))
			{
				p.alive = Boolean.parseBoolean(pl[1]);
				p.player.x = Float.parseFloat(pl[2]);
				p.player.y = Float.parseFloat(pl[3]);
				p.goingleft = Boolean.parseBoolean(pl[4]);
				p.active = Boolean.parseBoolean(pl[5]);
			}
			
		}
	}
	
	private boolean isLive()
	{
		if(System.currentTimeMillis()-lastSeen>5000&&alive)
		{
			Gdx.app.log("err","Server timed out");
			state=0;//cambiare in state=2??
			alive=false;
			return false;
		}		
		return true;
	}
	
}
