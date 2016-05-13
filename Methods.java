import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Methods {
	public static Point wpTop;
	public static Point wpBot;
	public static Point wpMid;
	public static Point gegnNexus;

	public static void main(String[] args) {

	}

	public static void setupPositions(int[][] minimap) {
//		int[][] minimap = Converter.interpret(1);
		
		List<Point> pixelsWpTop = new ArrayList<Point>();
		List<Point> pixelsWpBot = new ArrayList<Point>();
		List<Point> pixelsWpMid = new ArrayList<Point>();
		List<Point> pixelsNex = new ArrayList<Point>();

		for (int i = 0; i < minimap.length; i++) {
			for (int j = 0; j < minimap[i].length; j++) {
				if (minimap[i][j] == 4) {
					if (pixelsWpTop.size() == 0
							|| pixelsWpTop.contains(new Point(i, j - 1))
							|| pixelsWpTop.contains(new Point(i - 1, j))) {
						pixelsWpTop.add(new Point(i, j));
					} else if (pixelsWpMid.size() == 0
							|| pixelsWpMid.contains(new Point(i, j - 1))
							|| pixelsWpMid.contains(new Point(i - 1, j))) {
						pixelsWpMid.add(new Point(i, j));
					} else if (pixelsWpBot.size() == 0
							|| pixelsWpBot.contains(new Point(i, j - 1))
							|| pixelsWpBot.contains(new Point(i - 1, j))) {
						pixelsWpBot.add(new Point(i, j));
					}
				} else if (minimap[i][j] == 2) {
					pixelsNex.add(new Point(i, j));
				}
			}
		}

		int x = 0;
		int y = 0;
		for (Point p : pixelsWpTop) {
			x += p.getX();
			y += p.getY();
		}
		wpTop = new Point(x / pixelsWpTop.size(), y / pixelsWpTop.size());

		x = 0;
		y = 0;
		for (Point p : pixelsWpMid) {
			x += p.getX();
			y += p.getY();
		}
		wpMid = new Point(x / pixelsWpMid.size(), y / pixelsWpMid.size());

		x = 0;
		y = 0;
		for (Point p : pixelsWpBot) {
			x += p.getX();
			y += p.getY();
		}
		wpBot = new Point(x / pixelsWpBot.size(), y / pixelsWpBot.size());

		x = 0;
		y = 0;
		for (Point p : pixelsNex) {
			x += p.getX();
			y += p.getY();
		}
		gegnNexus = new Point(x / pixelsNex.size(), y / pixelsNex.size());
	}

	public void moveToLane() {

	}
}
