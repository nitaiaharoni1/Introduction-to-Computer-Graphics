package edu.cg;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SeamsCarver extends ImageProcessor {

    // MARK: An inner interface for functional programming.
    @FunctionalInterface
    interface ResizeOperation {
        BufferedImage resize();
    }

    // MARK: Fields
    private int seamsNum;
    private ResizeOperation resizeOp;
    boolean[][] imageMask;
    //Todo: change names and order
    private int[][] grey;
    private long[][] M = new long[inHeight][inWidth];
    private int[][] minPaths = new int[inHeight][inWidth];
    private int[][] xIndices;
    private boolean[][] shiftedMask;
    private int[][] seams = new int[seamsNum][inHeight];
    private int k = 0;
    private boolean[][] maskAfterSeamCarving = null;
    //Todo: untill here

    public SeamsCarver(Logger logger, BufferedImage workingImage, int outWidth, RGBWeights rgbWeights,
                       boolean[][] imageMask) {
        super((s) -> logger.log("Seam carving: " + s), workingImage, rgbWeights, outWidth, workingImage.getHeight());

        seamsNum = Math.abs(outWidth - inWidth);
        this.imageMask = imageMask;
        if (inWidth < 2 | inHeight < 2)
            throw new RuntimeException("Can not apply seam carving: workingImage is too small");
        if (seamsNum > inWidth / 2)
            throw new RuntimeException("Can not apply seam carving: too many seams...");
        // Setting resizeOp by with the appropriate method reference
        if (outWidth > inWidth)
            resizeOp = this::increaseImageWidth;
        else if (outWidth < inWidth)
            resizeOp = this::reduceImageWidth;
        else
            resizeOp = this::duplicateWorkingImage;

        // TODO: You may initialize your additional fields and apply some preliminary calculations.

        //Todo: change this section
        logger.log("begins preliminary calculations.");
        if (seamsNum > 0) {
            logger.log("initializes some additional fields.");
            transformToGrey();
            initXIndices();
            shiftedMask = imageMask;
            for (int i = k; i < seamsNum; i++) {
                calcM();
                logger.log("finds seam no: " + (k + 1) + ".");
                removeSeam();
            }
            //Todo: until here
            logger.log("preliminary calculations were ended.");
        }
    }

    public BufferedImage resize() {
        return resizeOp.resize();
    }

    private BufferedImage reduceImageWidth() {
        // TODO: Implement this method, remove the exception.
        throw new UnimplementedMethodException("reduceImageWidth");
    }

    private BufferedImage increaseImageWidth() {
        // TODO: Implement this method, remove the exception.
        throw new UnimplementedMethodException("increaseImageWidth");
    }

    public BufferedImage showSeams(int seamColorRGB) {
/*        final BufferedImage ans = this.duplicateWorkingImage();
        if (this.numOfSeams > 0) {
            int[][] seams;
            for (int length = (seams = this.seams).length, i = 0; i < length; ++i) {
                final int[] seam = seams[i];
                final Object o;
                final int x;
                final BufferedImage bufferedImage;
                this.forEachHeight(y -> {
                    x = o[y];
                    bufferedImage.setRGB(x, y, seamColorRGB);
                    return;
                });
            }
        }
        return ans;*/
        return null;
    }

    public boolean[][] getMaskAfterSeamCarving() {
        // TODO: Implement this method, remove the exception.
        // This method should return the mask of the resize image after seam carving. Meaning,
        // after applying Seam Carving on the input image, getMaskAfterSeamCarving() will return
        // a mask, with the same dimensions as the resized image, where the mask values match the
        // original mask values for the corresponding pixels.
        // HINT:
        // Once you remove (replicate) the chosen seams from the input image, you need to also
        // remove (replicate) the matching entries from the mask as well.
        throw new UnimplementedMethodException("getMaskAfterSeamCarving");
    }

    private void transformToGrey() {
        BufferedImage gr = greyscale();
        grey = new int[inHeight][inWidth];
        forEach((x, y) -> {
            grey[y][x] = new Color(gr.getRGB(x, y)).getGreen();
        });
    }

    private boolean[][] duplicateWorkingMask() {
        final boolean[][] res = new boolean[this.inHeight][this.inWidth];
        this.forEach((x, y) -> res[y][x] = this.imageMask[y][x]);
        return res;
    }

    private long calcE(int y, int x) {
        int nextX = (x + 1 < inWidth - k) ? (x + 1) : (x - 1);
        int nextY = (y + 1 < inHeight) ? (y + 1) : (y - 1);
        long forbidden = shiftedMask[y][x] ? 2147483647L : 0L;
        return Math.abs(grey[y][nextX] - grey[y][x]) + Math.abs(grey[nextY][x] - grey[y][x]) + forbidden;
    }

    private void removeSeam() {
        logger.log("looking for the \"x\" index of the bottom row that holds the minimal cost.");
        int minX = 0;
        for (int x = 0; x < inWidth - k; ++x) {
            if (M[inHeight - 1][x] < M[inHeight - 1][minX]) {
                minX = x;
            }
        }
        logger.log("minX = " + minX + ".");
        logger.log("constructs the path of the minimal seam.");
        logger.log("stores the path.");
        for (int y = inHeight - 1; y > -1; --y) {
            seams[k][y] = xIndices[y][minX];
            final int greyColor = grey[y][minX];
            if (minX > 0) {
                grey[y][minX - 1] = (grey[y][minX - 1] + greyColor) / 2;
            }
            if (minX + 1 < inWidth - k) {
                grey[y][minX + 1] = (grey[y][minX + 1] + greyColor) / 2;
            }
            //shiftLeft(y, minX);
            minX = minPaths[y][minX];
        }
        logger.log("removes the seam.");
    }

    private void calcM() {
        logger.log("calculates the costs matrix \"M\".");
        setForEachWidth(inWidth - k);
        pushForEachParameters();
        forEach((x, y) -> {
            mCell cell = getMCell(x, y);
            minPaths[y][x] = cell.minPath;
            M[y][x] = calcE(x, y) + cell.minCost;
        });
        popForEachParameters();
    }

    private void shiftLeft(final int y, final int seamX) {
        for (int x = seamX + 1; x < this.inWidth - this.k; ++x) {
            this.xIndices[y][x - 1] = this.xIndices[y][x];
            this.grey[y][x - 1] = this.grey[y][x];
            this.shiftedMask[y][x - 1] = this.shiftedMask[y][x];
        }
    }

    private void initXIndices() {
        this.logger.log("creates a 2D matrix of original \"x\" indices.");
        this.xIndices = new int[this.inHeight][this.inWidth];
        this.forEach((x, y) -> this.xIndices[y][x] = x);
    }


    private mCell getMCell(int y, int x) {
        long minCost = 0L;
        int minPath = x;
        if (y > 0) {
            long mv = M[y - 1][x];
            long cr;
            long cl;
            long cv;
            if (x > 0 & x + 1 < inWidth - k) {
                cv = (cl = (cr = Math.abs(grey[y][x - 1] - grey[y][x + 1])));
            } else {
                cv = (cl = (cr = 255L));
            }
            long ml;
            if (x > 0) {
                cl += Math.abs(grey[y - 1][x] - grey[y][x - 1]);
                ml = M[y - 1][x - 1];
            } else {
                cl = 0L;
                ml = 2147483647L;
            }
            long mr;
            if (x + 1 < inWidth - k) {
                cr += Math.abs(grey[y - 1][x] - grey[y][x + 1]);
                mr = M[y - 1][x + 1];
            } else {
                cr = 0L;
                mr = 2147483647L;
            }
            long sumL = ml + cl;
            long sumV = mv + cv;
            long sumR = mr + cr;
            minCost = Math.min(Math.min(sumL, sumV), sumR);
            if (minCost == sumR & x + 1 < inWidth - k) {
                minPath = x + 1;
            } else if (minCost == sumL & x > 0) {
                minPath = x - 1;
            }
        }
        return new mCell(minCost, minPath);
    }

    private static class mCell {
        int minPath;
        long minCost;

        mCell(long minCost, int minPath) {
            this.minPath = minPath;
            this.minCost = minCost;
        }
    }

}
