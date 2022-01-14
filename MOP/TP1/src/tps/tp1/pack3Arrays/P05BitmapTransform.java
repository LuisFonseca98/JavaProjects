package tps.tp1.pack3Arrays;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Image Transform - reduce image
 * 
 * 
 */
public class P05BitmapTransform {

	/**
	 * Main method - execution entry point
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {

					// read the image to memory
					BufferedImage image = ImageIO.read(P05BitmapTransform.class.getResource("images/image1.jpg"));

					// show original image
					buildFrameForImage(image, "Original image");

					// show a copy with only blue component
					buildFrameForImage(copyImage(image), "Copy with just blue");

					// show a mirrored image
					buildFrameForImage(mirrorImage(image), "Mirrored image");

					// show a reduced image
					buildFrameForImage(reduceImage(image), "Reduced image");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Method that copies the image, apply some pixel transformation.
	 * 
	 * @param image
	 *            the image to be transformed
	 * @return a new image
	 */
	public static BufferedImage copyImage(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

		int height = image.getHeight();
		int width = image.getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixelRGB = image.getRGB(x, y);
				int newPixelColor = pixelRGB;

				// TODO testar com uma só linha activada, entre as seguintes
				newPixelColor = pixelRGB & 0xFF; // só azul
				// newPixelColor = pixelRGB & 0xFF00; // só verde
				// newPixelColor = pixelRGB & 0xFF0000; // só vermelho

				newImage.setRGB(x, y, newPixelColor);
			}
		}
		return newImage;
	}

	/**
	 * Returns a mirrored image
	 * 
	 * @param image
	 *            the image to be transformed
	 * @return the mirrored Image
	 */
	public static BufferedImage mirrorImage(BufferedImage image) {
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

		int height = image.getHeight();
		int width = image.getWidth();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width / 2; x++) {
				int pixelRGB = image.getRGB(x, y);
				newImage.setRGB(x, y, pixelRGB);
				newImage.setRGB(width - x - 1, y, pixelRGB);
			}
		}

		return newImage;
	}

	/**
	 * Return the received image reduce by half
	 * 
	 * @param image
	 *            the image to be transformed
	 * @return the reduced image
	 */
	public static BufferedImage reduceImage(BufferedImage image) {
		// cria uma nova imagem com o width e height da imagem original
		// TODO
		BufferedImage newImage = new BufferedImage(image.getWidth()/2, image.getHeight()/2, image.getType());

		int height = image.getHeight();
		int width = image.getWidth();
		

		for(int i = 0; i<width; i+=2) {
			for(int j = 0; j<height-1; j+=2) {
				
				
				
				int rgb1 = image.getRGB(i, j);
				int rgb2 = image.getRGB(i, j+1);
				int rgb3 = image.getRGB(i+1, j);
				int rgb4 = image.getRGB(i+1, j+1);
				
				int red = (getRGBRed(rgb1) + getRGBRed(rgb2) + getRGBRed(rgb3) + getRGBRed(rgb4)) / 4;
				int green = (getRGBGreen(rgb1) + getRGBGreen(rgb2) + getRGBGreen(rgb3) + getRGBGreen(rgb4)) / 4;
				int blue = (getRGBBlue(rgb1) + getRGBBlue(rgb2) + getRGBBlue(rgb3) + getRGBBlue(rgb4)) / 4;
				
				

				
				int rgb = 0;
				
				rgb = setRGBRed(rgb,red);
				rgb = setRGBGreen(rgb,green);
				rgb = setRGBBlue(rgb, blue);
				
				System.out.println("valor normal" + rgb1);
				System.out.println(rgb);
				
				
				 newImage.setRGB(i/2, j/2, rgb);
				
				
			}
			
		}

		return newImage;
	}

	/**
	 * Shows one image in a new frame
	 */
	public static void buildFrameForImage(BufferedImage image, String title) throws IOException {
		// the frame
		JFrame frame = new JFrame(title);

		// frame should be disposed when we press close button
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// set frame size
		frame.setSize(400, 300);

		// center the frame
		frame.setLocationRelativeTo(null);

		// show the image inside a label
		ImageIcon img = new ImageIcon(image);
		JLabel label = new JLabel(img, JLabel.CENTER);
		// add the label to frame
		frame.add(label);

		// turn frame visible
		frame.setVisible(true);
	}

	/**
	 * gets blue value from RGB pixel value
	 */
	static int getRGBBlue(int rgb) {
		return (rgb) & 0xFF;
	}

	/**
	 * gets green value from RGB pixel value
	 */
	static int getRGBGreen(int rgb) {
		return (rgb >> 8) & 0xFF;
	}

	/**
	 * gets red value from RGB pixel value
	 */
	static int getRGBRed(int rgb) {
		return (rgb >> 16) & 0xFF;
	}

	/**
	 * sets blue value in the received RGB pixel value
	 * 
	 * @return the new pixel RGB value
	 */
	static int setRGBBlue(int rgb, int blue) {
		return (rgb & 0xFFFFFF00) | (blue & 0xFF);
	}

	/**
	 * sets green value in the received RGB pixel value
	 * 
	 * @return the new pixel RGB value
	 */
	static int setRGBGreen(int rgb, int green) {
		return (rgb & 0xFFFF00FF) | ((green & 0xFF) << 8);
	}

	/**
	 * sets red value in the received RGB pixel value
	 * 
	 * @return the new pixel RGB value
	 */
	static int setRGBRed(int rgb, int red) {
		return ((red & 0xFF) << 16) | rgb & 0xFF00FFFF;
	}

}
