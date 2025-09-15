package brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Map {
	
	public int map[][];
	public int en, boy;
	
	public Map(int satir, int sutun) {
		map=new int[satir][sutun];
		for(int []map1 : map) {
			for(int i=0; i<map[0].length; i++) {
				map1[i]=1;
			}
		}
		en=540/sutun;
		boy=150/satir;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[0].length; j++) {
				if(map[i][j]>0) {
					g.setColor(Color.red);
					g.fillRect(j*en+80, i*boy+50, en, boy);
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j*en+80, i*boy+50, en, boy);
				}
			}
		}
	}
	
	public void setBrickValue(int value, int satir, int sutun) {
		map[satir][sutun]=value;
	}

}
