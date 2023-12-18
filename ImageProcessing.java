import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
	public static void main(String[] args) {
    // The provided images are apple.jpg, flower.jpg, and kitten.jpg
		int[][] imageData = imgToTwoD("./apple.jpg");
    // Or load your own image using a URL!
		//int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
		viewImageData(imageData);
		int[][] trimmed = trimBorders(imageData, 60);
		twoDToImage(trimmed, "./trimmed_apple.jpg");
    int[][] negativePhoto = negativeColor(imageData);
    twoDToImage(negativePhoto, "./negative_apple.jpg");
    int[][] stretchedHorizontallyImage = stretchHorizontally(negativePhoto);
    twoDToImage(stretchedHorizontallyImage, "./stretched_negative_apple.jpg");
    int[][] flowerImg = imgToTwoD("./flower.jpg");
    int[][] shrunkFlower = shrinkVertically(flowerImg);
    twoDToImage(shrunkFlower, "./shrunk_flower.jpg");
    int[][] kittenImg = imgToTwoD("./kitten.jpg");
    int[][] invertedKitten = invertImage(kittenImg);
    twoDToImage(invertedKitten, "./inverted_kitten.jpg");
    int[][] repaintedFlower = colorFilter(flowerImg, -25, 20, 50);
    twoDToImage(repaintedFlower, "./repainted_flower.jpg");
    int[][] blankCanvas = new int[1024][1024];
    int[][] randomImg = paintRandomImage(blankCanvas);
    twoDToImage(randomImg, "./random_img.jpg");
    int[] rectangleRgba = {94, 27, 229, 205};
    int rectangleColor = getColorIntValFromRGBA(rectangleRgba);
    int[][] flowerRectangle = paintRectangle(flowerImg, 160, 440, 250, 500, rectangleColor);
    twoDToImage(flowerRectangle, "./flowerRectangle.jpg");
    int[][] kittenCensoredRectangles = generateRectangles(kittenImg, 4);
    twoDToImage(kittenCensoredRectangles, "./kitten_censored_rectangles.jpg");

    int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
    twoDToImage(allFilters, "./allFilters.jpg");
		// Painting with pixels
	}
	// Image Processing Methods
	public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		// Example Method
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}
	public static int[][] negativeColor(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] manipulatedImg = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[i].length; j++) {
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] = 255 - rgba[0];
        rgba[1] = 255 - rgba[1];
        rgba[2] = 255 - rgba[2];
        manipulatedImg[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return manipulatedImg;
	}
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] stretchedImg = new int[imageTwoD.length][imageTwoD[0].length*2];
    int position = 0;
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[i].length; j++) {
        position = j * 2;
        stretchedImg[i][position] = imageTwoD[i][j];
        stretchedImg[i][position + 1] = imageTwoD[i][j];
        }
    }
		return stretchedImg;
	}
	public static int[][] shrinkVertically(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] shrunkImg = new int[imageTwoD.length/2][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD[0].length; i++) {
      for (int j = 0; j < imageTwoD.length-1; j+=2) { 
        shrunkImg[j/2][i] = imageTwoD[j][i];
      }
    }
		return shrunkImg;
	}
	public static int[][] invertImage(int[][] imageTwoD) {
		// TODO: Fill in the code for this method
    int[][] invertedImg = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[i].length; j++) {
        invertedImg[i][j] = imageTwoD[(imageTwoD.length-1)-i][(imageTwoD[i].length-1)-j];
      }
    }
		return invertedImg;
	}
  //Testing colors for range 0-255
  public static int testColorRgb(int color) {
    if (color > 255) {
      color = 255;
    }
    else if (color < 0) {
      color = 0;
    }
    return color;
  }

	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
		// TODO: Fill in the code for this method
    int[][] colorFilteredImg = new int[imageTwoD.length][imageTwoD[0].length];
    for (int i = 0; i < imageTwoD.length; i++) {
      for (int j = 0; j < imageTwoD[i].length; j++) {
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        int newRed = rgba[0] + redChangeValue;
        rgba[0] = testColorRgb(newRed);
        int newGreen = rgba[1] + greenChangeValue;
        rgba[1] = testColorRgb(newGreen);
        int newBlue = rgba[2] + blueChangeValue;
        rgba[2] = testColorRgb(newBlue);
        colorFilteredImg[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return colorFilteredImg;
	}
	// Painting Methods
	public static int[][] paintRandomImage(int[][] canvas) {
		// TODO: Fill in the code for this method
    Random rand = new Random();
    int[][] randomPaintedImg = new int[canvas.length][canvas[0].length];
    for (int i = 0; i < canvas.length; i++) {
      for (int j = 0; j < canvas[0].length; j++) {
        int newRed = rand.nextInt(256);
        int newGreen = rand.nextInt(256);
        int newBlue = rand.nextInt(256);
        int randAlpha = rand.nextInt(256);
        int[] newColorData = new int[]{newRed, newGreen, newBlue, randAlpha};
        randomPaintedImg[i][j] = getColorIntValFromRGBA(newColorData);
      }
    }
		return randomPaintedImg;
	}
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
		// TODO: Fill in the code for this method
    int[][] modifiedCanvas = canvas;
    for (int i = 0; i < canvas.length; i++) {
      for (int j = 0; j < canvas[0].length; j++) {
        if ((i >= rowPosition) && (i <= rowPosition + width)) {
          if ((j >= colPosition) && (j <= colPosition + height)) {
            modifiedCanvas[i][j] = color;
          }
        }
      }
    }
		return modifiedCanvas;
	}
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
		// TODO: Fill in the code for this method
    Random rand = new Random();
    for (int i = 0; i < numRectangles; i++) {
      int randomWidth = rand.nextInt(canvas[0].length);
      int randomHeight = rand.nextInt(canvas.length);
      int randomRowPos = rand.nextInt(canvas.length-randomHeight);
      int randomColPos = rand.nextInt(canvas[0].length-randomWidth);
      int[] rgba = {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 255};
      int randomColor = getColorIntValFromRGBA(rgba);
      canvas = paintRectangle(canvas, randomWidth, randomHeight, randomRowPos, randomColPos, randomColor);
    }
		return canvas;
	}
	// Utility Methods
	public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}
