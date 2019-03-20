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
    private int[][] grey;
    private Color[][] currentImg;
    private long[][] E;
    private long[][] M;
    private int[][] seams;
    private int[][] seamsMatrix;


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
        if (seamsNum > 0) {
            transformToGrey();
            initCurrentImg();
        }
    }

    public BufferedImage resize() {
        return resizeOp.resize();
    }

    private BufferedImage reduceImageWidth() {
        for (int k = 0; k < seamsNum; k++) {
            calcE();
            calcEWithMask();
            calcM();
            storeSeams(1);
            removeSeams();
        }
        return paintResultImg();
    }

    private BufferedImage increaseImageWidth() {
        storeSeams(seamsNum);
        return null;
    }

    public BufferedImage showSeams(int seamColorRGB) {
        storeSeams(seamsNum);
        BufferedImage ans = workingImage;
        for (int[] seam : seams) {
            for (int i = 0; i < seams[0].length; i++) {
                ans.setRGB(seam[i], i, seamColorRGB);
            }
        }
        return ans;
    }

    public boolean[][] getMaskAfterSeamCarving() {
        return imageMask;
    }

    private BufferedImage paintResultImg() {
        BufferedImage img = new BufferedImage(currentImg[0].length, currentImg.length, workingImage.getType());
        for (int i = 0; i < currentImg.length; i++) {
            for (int j = 0; j < currentImg[0].length; j++) {
                img.setRGB(j, i, currentImg[i][j].getRGB());
            }
        }
        return img;
    }

    private void initCurrentImg() {
        Color[][] matrix = new Color[workingImage.getHeight()][workingImage.getWidth()];
        forEach((y, x) -> {
            matrix[y][x] = new Color(workingImage.getRGB(x, y));
        });
        currentImg = matrix;
    }

    private void transformToGrey() {
        BufferedImage greyImg = greyscale();
        int[][] matrix = new int[greyImg.getHeight()][greyImg.getWidth()];
        forEach((y, x) -> {
            matrix[y][x] = new Color(greyImg.getRGB(x, y)).getGreen();
        });
        grey = matrix;
    }

    private void storeSeams(int num) {
        seams = new int[num][grey.length];
        seamsMatrix = new int[grey.length][grey[0].length];
        int minIndex;
        for (int k = 0; k < num; k++) {
            minIndex = findMinBottomCell();
            for (int i = grey.length - 1; i >= 0; i--) {
                seams[k][i] = minIndex;
                M[i][minIndex] = Integer.MAX_VALUE;
                seamsMatrix[i][minIndex] = -1;
                minIndex = findMinFromUpperCells(i, minIndex);
            }
        }
        logger.log("finished store seams");
    }

    private int findMinFromUpperCells(int i, int minIndex) {
        long minL = Integer.MAX_VALUE, minU = Integer.MAX_VALUE, minR = Integer.MAX_VALUE;
        if (i > 0) {
            if (minIndex > 0)
                minL = M[i - 1][minIndex - 1];
            if (minIndex < M[0].length - 1)
                minR = M[i - 1][minIndex + 1];
            minU = M[i - 1][minIndex];

            if (minL < minU && minL < minR) {
                minIndex = minIndex - 1;
            } else if (minR < minL && minR < minU) {
                minIndex = minIndex + 1;
            }
        }
        return minIndex;
    }

    private int findMinBottomCell() {
        long minCost = Integer.MAX_VALUE;
        int minBottomIndex = -1;
        for (int j = 0; j < grey[0].length; j++) {
            if (M[grey.length - 1][j] < minCost) {
                minCost = M[grey.length - 1][j];
                minBottomIndex = j;
            }
        }
        return minBottomIndex;
    }

    private void calcE() {
        E = new long[grey.length][grey[0].length];
        int jRight, iDown, jLeft, iUp;
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[0].length; j++) {
                jRight = j + 1;
                iDown = i + 1;
                jLeft = j - 1;
                iUp = i - 1;

                if (iDown >= E.length) {
                    iDown = i - 1;
                }
                if (iUp < 0) {
                    iUp = i + 1;
                }
                if (jRight >= E[0].length) {
                    jRight = j - 1;
                }
                if (jLeft < 0) {
                    jLeft = j + 1;
                }
                int greyIJ = grey[i][j];
                E[i][j] = Math.abs(grey[i][jRight] - greyIJ) + Math.abs(grey[i][jLeft] - greyIJ) + Math.abs(grey[iDown][j] - greyIJ) + Math.abs(grey[iUp][j] - greyIJ);
            }
        }
    }

    private void removeSeams() {
        int[][] newGrey = new int[grey.length][grey[0].length - 1];
        Color[][] newImg = new Color[grey.length][grey[0].length - 1];
        boolean[][] newImageMask = new boolean[grey.length][grey[0].length - 1];
        int indexI = 0, indexJ;

        for (int i = 0; i < seamsMatrix.length; i++) {
            indexJ = 0;
            for (int j = 0; j < seamsMatrix[0].length; j++) {
                if (seamsMatrix[i][j] != -1) {
                    newGrey[i][indexJ] = grey[i][j];
                    newImg[i][indexJ] = currentImg[i][j];
                    newImageMask[i][indexJ] = imageMask[i][j];
                    indexJ++;
                }
            }
        }
        grey = newGrey;
        currentImg = newImg;
        imageMask = newImageMask;
    }

    private void addSeam() {
        BufferedImage ans = new BufferedImage(workingImage.getWidth() - 1, workingImage.getHeight() - 1, workingImage.getType());
        for (int i = 0; i < workingImage.getHeight(); i++) {
            for (int j = 0; j < workingImage.getWidth(); j++) {
                if (seamsMatrix[i][j] != -1) {
                    ans.setRGB(j, i, workingImage.getRGB(j, i));
                }
            }
        }
    }


    private void calcM() {
        M = new long[E.length][E[0].length];
        long Cr, Cl, Cu;
        long minL, minU, minR;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (i > 0 && (j + 1 >= M[0].length)) {
                    minL = M[i - 1][j - 1];
                    minU = M[i - 1][j];

                    //todo: grey[i][j - 1])?
                    Cl = Math.abs(/*grey[i][j + 1] -*/ grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j - 1]);
                    Cu = Math.abs(/*grey[i][j + 1] -*/ grey[i][j - 1]);

                    M[i][j] = E[i][j] + Math.min(minL + Cl, minU + Cu);

                } else if (i > 0 && j > 0) {
                    minL = M[i - 1][j - 1];
                    minU = M[i - 1][j];
                    minR = M[i - 1][j + 1];

                    Cl = Math.abs(grey[i][j + 1] - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j - 1]);
                    Cu = Math.abs(grey[i][j + 1] - grey[i][j - 1]);
                    Cr = Math.abs(grey[i][j + 1] - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j + 1]);

                    M[i][j] = E[i][j] + Math.min(Math.min(minL + Cl, minU + Cu), minR + Cr);

                } else if (i > 0 && j == 0) {
                    minU = M[i - 1][j];
                    minR = M[i - 1][j + 1];

                    //todo: grey[i][j + 1])?
                    Cu = Math.abs(grey[i][j + 1] /*- grey[i][j - 1]*/);
                    Cr = Math.abs(grey[i][j + 1] /*- grey[i][j - 1]*/) + Math.abs(grey[i - 1][j] - grey[i][j + 1]);

                    M[i][j] = E[i][j] + Math.min(minR + Cr, minU + Cu);

                } else if (i == 0) {
                    M[i][j] = E[i][j];
                }
            }
        }
        logger.log("finished calc M");
    }

    private void calcEWithMask() {
        for (int i = 0; i < imageMask.length; i++) {
            for (int j = 0; j < imageMask[0].length; j++) {
                if (imageMask[i][j] == true) {
                    E[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        logger.log("finished calc E with mask");
    }

}
