import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class Bot {
	private static final int GENAUIGKEIT = 20;
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static int[][] feldInt = new int[screenSize.height / GENAUIGKEIT][screenSize.width
			/ GENAUIGKEIT];
	private static Color[][] feldCol = new Color[screenSize.height
			/ GENAUIGKEIT][screenSize.width / GENAUIGKEIT];

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage()
				.getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e1) {
			e1.printStackTrace();
		}
		new Bot();

		addListeners();
	}

	public static void start() {
		BufferedImage bufferedImage;
		try {
			bufferedImage = new Robot().createScreenCapture(new Rectangle(
					screenSize));
			int[][] result = Converter
					.convertTo2DWithoutUsingGetRGB(bufferedImage);
			for (int i = 0; i < result.length; i += GENAUIGKEIT) {
				for (int j = 0; j < result[i].length; j += GENAUIGKEIT) {
					Color c = new Color(result[i][j]);
					feldCol[i / GENAUIGKEIT][j / GENAUIGKEIT] = c;
				}
				System.out.println();
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void interpret() {
		// 0=Gelände, 1=unser Nexus, 2=gegn. Nexus, 3=Lane, 4=Wegpunkt
		for (int i = 0; i < feldCol.length; i++) {
			for (int j = 0; j < feldCol[i].length; j++) {
				int r = feldCol[i][j].getRed();
				int g = feldCol[i][j].getGreen();
				int b = feldCol[i][j].getBlue();
				int wert = 0;

				if (r > 240 && g < 15 && b < 15) {
					wert = 2;
				} else if (r < 15 && g > 240 && b < 15) {
					wert = 1;
				} else if (r < 15 && g < 15 && b > 240) {
					wert = 3;
				} else if (r < 15 && g < 15 && b < 15) {
					wert = 0;
				} else if (r > 240 && g > 240 && b > 240) {
					wert = 4;
				}
				feldInt[i][j] = wert;
				if (wert == 0) {
					System.out.print("  ");
					continue;
				}
				System.out.print(wert + " ");
			}
			System.out.println();
		}
	}

	public static void addListeners() {
		NativeKeyListener keyHandler = new NativeKeyListener() {
			@Override
			public void nativeKeyPressed(NativeKeyEvent e) {
				if (e.getKeyCode() == NativeKeyEvent.VC_F12) {
					start();
					interpret();
				} else if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
					System.exit(0);
				}
			}

			public void nativeKeyReleased(NativeKeyEvent e) {
			}

			public void nativeKeyTyped(NativeKeyEvent e) {
			}

		};
		GlobalScreen.addNativeKeyListener(keyHandler);
	}
}
