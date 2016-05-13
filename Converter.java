import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Converter {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] result = new int[height][width];

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				result[row][col] = image.getRGB(col, row);
			}
		}

		return result;
	}

	public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
				.getData();
		final int width = image.getWidth();
		final int height = image.getHeight();

		int[][] result = new int[height][width];
		for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += 1) {
			result[row][col] = pixels[pixel];
			col++;
			if (col == width) {
				col = 0;
				row++;
			}
		}
		return result;
	}

	public static int[][] interpret(int dpi) {
		Color[][] feldCol = new Color[screenSize.height / dpi][screenSize.width
				/ dpi];
		BufferedImage bufferedImage;
		try {
			bufferedImage = new Robot().createScreenCapture(new Rectangle(
					screenSize));
			int[][] result = Converter
					.convertTo2DWithoutUsingGetRGB(bufferedImage);
			for (int i = 0; i < result.length; i += dpi) {
				for (int j = 0; j < result[i].length; j += dpi) {
					Color c = new Color(result[i][j]);
					feldCol[i / dpi][j / dpi] = c;
				}
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}

		int[][] feldInt = new int[screenSize.height / dpi][screenSize.width
				/ dpi];
		for (int i = 0; i < feldCol.length; i++) {
			for (int j = 0; j < feldCol[i].length; j++) {
				int r = feldCol[i][j].getRed();
				int g = feldCol[i][j].getGreen();
				int b = feldCol[i][j].getBlue();
				int wert = 0;

				if (r > 240 && g < 15 && b < 15) {
					wert = 2; // gegn. nexus
				} else if (r < 15 && g > 240 && b < 15) {
					wert = 1; // unser nexus
				} else if (r < 15 && g < 15 && b > 240) {
					wert = 3; // lane
				} else if (r < 15 && g < 15 && b < 15) {
					wert = 0; // gelände
				} else if (r > 240 && g > 240 && b > 240) {
					wert = 4; // wegpunkt
				} else if (r > 240 && g > 240 && b < 10) {
					wert = 5; // minion
				} else if (r > 240 && g < 10 && b > 240) {
					wert = 6; // gegn. champ
				} else if (r < 10 && g > 240 && b > 240) {
					wert = 7; // freundl. champ
				}
				feldInt[i][j] = wert;
			}
		}
		return feldInt;
	}
}