package com.expiredcode.FletcherRoyale;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server 
{
	
	private DatagramSocket clientSocket,cSocket;
	private DatagramPacket sendPacket,receivePacket;
	
	byte[] receiveData = new byte[1024];
	byte[] sendData;
	
	private long timeDelay = System.currentTimeMillis();
	private long delay,lastSeen= System.currentTimeMillis();
	
	private InetAddress IPAddress; 
	private int port=9876;
	
	List<ServerPlayer> sp = new ArrayList();
	Iterator it;
	ServerPlayer p;	
	
	private String[] data;
	private String temp;
	
	public static void main(String[] args)
	{
		
		new Server().MainLoop();
	}
	
	void MainLoop()
	{
		sp.add(new ServerPlayer("P1"));
		sp.add(new ServerPlayer("P2"));
		sp.add(new ServerPlayer("P3"));
		sp.add(new ServerPlayer("P4"));	
		try{
			clientSocket = new DatagramSocket();
			clientSocket.setSoTimeout(30);
			cSocket = new DatagramSocket(9870);
			cSocket.setSoTimeout(30);
		}catch(Exception e){System.out.println(e.toString());}
		
		while(true)
		{
			update();
		}
	}
	
	private void update()
	{	
		try{
			unpackAndUpdate(receive());
		}catch(Exception e){}
		
		delay=System.currentTimeMillis()-timeDelay;
		//try {send("bu",InetAddress.getByName("127.0.0.1"));}catch(Exception e){System.out.println(e.toString());}
		if(delay>50)
		{
			temp=build();			
			it = sp.iterator();
			
			while(it.hasNext())
			{
				p = (ServerPlayer) it.next();
				if(p.active==true)
				{
					send(temp,p.ip);
				}
			}
			timeDelay = System.currentTimeMillis();
		}
	}
	
	private String receive()
	{
		receiveData = new byte[1024];
		try{
			receivePacket = new DatagramPacket(receiveData,receiveData.length);
			cSocket.receive(receivePacket);		
				System.out.println(new String(receivePacket.getData()).replaceAll("\\s+", ""));
			lastSeen= System.currentTimeMillis();
			return new String(receivePacket.getData());//+"!"+receivePacket.getAddress();
		}catch(Exception e){ return "";	}		
	}	
	
	private void send(String s,InetAddress ip)
	{
		try{
			byte[] sendData = s.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,ip, port);			
			clientSocket.send(sendPacket);					
			System.out.println("sent "+s+" to "+ip.getHostAddress());
		}catch(Exception e){System.out.println(e.toString());}		
	}
	
	private String build()
	{
		String st="";	
		it=sp.iterator();
		while(it.hasNext())
		{
			p = (ServerPlayer) it.next();
			st+=p.name+"!"+p.alive+"!"+p.x+"!"+p.y+"!"+p.goingLeft+"!"+p.active+"$";
		}
		
		
		return st;//"prova$player1";
	}
	
	private void unpackAndUpdate(String s)
	{
		data = s.replaceAll("\\s+", "").split("\\!");	
		if(data[0].equals("Hello"))
		{	System.out.println("Hellooo");
			NewPlayer();
			System.out.println("sv "+data[0]);
		}
		else
		{
			it = sp.iterator();
			while(it.hasNext())
			{
				p = (ServerPlayer) it.next();
				if(p.name.equals(data[0])) // [m.p.name+"!"+m.p.alive+"!"+m.p.player.x+"!"+m.p.player.y+"!"+m.p.goingleft;]
				{
					p.alive = Boolean.parseBoolean(data[1]);
					p.x = Float.parseFloat(data[2]);
					p.y = Float.parseFloat(data[3]); 
					p.goingLeft = Boolean.parseBoolean(data[4]);
				}
			}
		}
	}
	
	private void NewPlayer()
	{
		it = sp.iterator();
		
		while(it.hasNext())
		{
			p = (ServerPlayer) it.next();
			if(p.name.equals("P4")&&p.active==true)
				send("ERROR0",receivePacket.getAddress());
			else
			{
				if(p.active==false)
				{
					p.ip = receivePacket.getAddress();
					p.lastseen=System.currentTimeMillis();
					p.alive = true;
					p.active = true;
					
					send("OK!"+p.name,p.ip);
					System.out.println(""+p.name+" connected..");
					return;
				}
				
			}
		}		
	}

}
