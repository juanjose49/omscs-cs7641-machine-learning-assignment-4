package burlap.assignment4.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import burlap.assignment4.BasicGridWorld;
import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;
import burlap.oomdp.visualizer.ObjectPainter;

public class AgentPainter implements ObjectPainter{
	
	protected int[][] map;
	
	public AgentPainter(int[][] map){
		this.map = map;
	}
	@Override
	public void paintObject(Graphics2D g2, State s, ObjectInstance ob,
							float cWidth, float cHeight) {
		
		//agent will be filled in gray
		g2.setColor(Color.GRAY);

		//set up floats for the width and height of our domain
		float fWidth = this.map.length;
		float fHeight = this.map[0].length;

		//determine the width of a single cell on our canvas
		//such that the whole map can be painted
		float width = cWidth / fWidth;
		float height = cHeight / fHeight;

		int ax = ob.getIntValForAttribute(BasicGridWorld.ATTX);
		int ay = ob.getIntValForAttribute(BasicGridWorld.ATTY);

		//left coordinate of cell on our canvas
		float rx = ax*width;

		//top coordinate of cell on our canvas
		//coordinate system adjustment because the java canvas
		//origin is in the top left instead of the bottom right
		float ry = cHeight - height - ay*height;

		//paint the rectangle
		g2.fill(new Ellipse2D.Float(rx, ry, width, height));


	}
	}


