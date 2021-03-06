package edu.cg;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessor extends FunctioalForEachLoops {

    // MARK: fields
    public final Logger logger;
    public final BufferedImage workingImage;
    public final RGBWeights rgbWeights;
    public final int inWidth;
    public final int inHeight;
    public final int workingImageType;
    public final int outWidth;
    public final int outHeight;

    // MARK: constructors
    public ImageProcessor(Logger logger, BufferedImage workingImage, RGBWeights rgbWeights, int outWidth,
                          int outHeight) {
        super(); // initializing for each loops...

        this.logger = logger;
        this.workingImage = workingImage;
        this.rgbWeights = rgbWeights;
        inWidth = workingImage.getWidth();
        inHeight = workingImage.getHeight();
        workingImageType = workingImage.getType();
        this.outWidth = outWidth;
        this.outHeight = outHeight;
        setForEachInputParameters();
    }

    public ImageProcessor(Logger logger, BufferedImage workingImage, RGBWeights rgbWeights) {
        this(logger, workingImage, rgbWeights, workingImage.getWidth(), workingImage.getHeight());
    }

    // MARK: change picture hue - example
    public BufferedImage changeHue() {
        logger.log("Prepareing for hue changing...");

        int r = rgbWeights.redWeight;
        int g = rgbWeights.greenWeight;
        int b = rgbWeights.blueWeight;
        int max = rgbWeights.maxWeight;

        BufferedImage ans = newEmptyInputSizedImage();

        forEach((y, x) -> {
            Color c = new Color(workingImage.getRGB(x, y));
            int red = r * c.getRed() / max;
            int green = g * c.getGreen() / max;
            int blue = b * c.getBlue() / max;
            Color color = new Color(red, green, blue);
            ans.setRGB(x, y, color.getRGB());
        });

        logger.log("Changing hue done!");

        return ans;
    }

    public final void setForEachInputParameters() {
        setForEachParameters(inWidth, inHeight);
    }

    public final void setForEachOutputParameters() {
        setForEachParameters(outWidth, outHeight);
    }

    public final BufferedImage newEmptyInputSizedImage() {
        return newEmptyImage(inWidth, inHeight);
    }

    public final BufferedImage newEmptyOutputSizedImage() {
        return newEmptyImage(outWidth, outHeight);
    }

    public final BufferedImage newEmptyImage(int width, int height) {
        return new BufferedImage(width, height, workingImageType);
    }

    // A helper method that deep copies the current working image.
    public final BufferedImage duplicateWorkingImage() {
        BufferedImage output = newEmptyInputSizedImage();
        setForEachInputParameters();
        forEach((y, x) -> output.setRGB(x, y, workingImage.getRGB(x, y)));

        return output;
    }

    public BufferedImage greyscale() {
        logger.log("Prepareing for greyscale changing...");
        int r = rgbWeights.redWeight;
        int g = rgbWeights.greenWeight;
        int b = rgbWeights.blueWeight;
        int total = rgbWeights.weightsAmount;
        BufferedImage res = newEmptyInputSizedImage();
        forEach((y, x) -> {
            Color c = new Color(workingImage.getRGB(x, y));
            int grey = (c.getRed() * r + c.getGreen() * g + c.getBlue() * b) / total;
            Color color = new Color(grey, grey, grey);
            res.setRGB(x, y, color.getRGB());
        });
        logger.log("Changing to grey done!");
        return res;
    }

    public BufferedImage nearestNeighbor() {
        logger.log("Prepareing for nearestNeighbor resizing...");
        BufferedImage res = newEmptyOutputSizedImage();
        setForEachOutputParameters();
        pushForEachParameters();
        forEach((y, x) -> {
            int y2 = Math.min((y * inHeight) / outHeight, inHeight - 1);
            int x2 = Math.min((x * inWidth) / outWidth, inWidth - 1);
            res.setRGB(x, y, workingImage.getRGB(x2, y2));
        });
        popForEachParameters();
        return res;
    }
}
