import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Converter {

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
}