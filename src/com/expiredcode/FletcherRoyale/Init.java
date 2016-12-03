package com.expiredcode.FletcherRoyale;

public class Init {
	
	MyUtils m = new MyUtils();
	
	Init()
	{
		
		m.walls.add(new Collider("ground",50,m.WORLD_HEIGHT-50,m.WORLD_WIDTH/3,60));
		m.walls.add(new Collider("ground",380,m.WORLD_HEIGHT-50,m.WORLD_WIDTH/3,60));
		
		m.walls.add(new Collider("w",-100,-50,150,250));
		m.walls.add(new Collider("w",0,m.WORLD_HEIGHT-150,50,250));
		m.walls.add(new Collider("w",m.WORLD_WIDTH-50,-50,150,250));
		m.walls.add(new Collider("w",m.WORLD_WIDTH-50,m.WORLD_HEIGHT-150,50,250));
		
	//	m.walls.add(new Collider("midlong",(m.WORLD_WIDTH/2),(m.WORLD_HEIGHT/2)-100,25,180));
		
		m.walls.add(new Collider("mid",110,m.WORLD_HEIGHT-200,120,25));
		m.walls.add(new Collider("mid",110,130,120,25));
		m.walls.add(new Collider("mid",(m.WORLD_WIDTH/2)-60,(m.WORLD_HEIGHT/2)-40,120,25));		
		m.walls.add(new Collider("mid",m.WORLD_WIDTH-230,m.WORLD_HEIGHT-200,120,25));
		m.walls.add(new Collider("mid",m.WORLD_WIDTH-230,130,120,25));
		
		m.walls.add(new Collider("ground",50,-10,m.WORLD_WIDTH/3,60));
		m.walls.add(new Collider("ground",380,-10,m.WORLD_WIDTH/3,60));
		
		
		
	}

}
