package Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	int map[][];
	int brickWidth;
	int brickHeight;
	
	public MapGenerator (int num_row, int num_col) {
		map = new int[num_row][num_col];
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				map[i][j]=1;
			}
			brickWidth = 540/num_col;
			brickHeight = 150/num_row;
		}
	}
	public void draw(Graphics2D g) {
		for(int i=0; i<map.length; i++) {
			for(int j=0; j<map[i].length; j++) {
				//System.out.print(map[i][j]);
				g.setColor(Color.white);
				if(map[i][j]==1) {
					g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				}
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.BLACK);
				g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
			}
		}
		//System.out.println("\n");
		
	}
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
		//System.out.println(row+","+col);
	}
}
