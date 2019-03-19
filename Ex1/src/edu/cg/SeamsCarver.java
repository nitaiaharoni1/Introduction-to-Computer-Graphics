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
            calcE();
            calcM();
            calcMWithMask();
            storeAllSeams();
        }
    }

    public BufferedImage resize() {
        return resizeOp.resize();
    }

    private BufferedImage reduceImageWidth() {
        for (int k = 0; k < seamsNum; k++) {
            storeSeams(1);
            calcSeamMatrix();
            removeSeams();
        }
        setForEachInputParameters();
        setForEachOutputParameters();
        popForEachParameters();
        return paintResultImg();
    }

    private BufferedImage increaseImageWidth() {
        storeSeams(seamsNum);
        return null;
    }

    public BufferedImage showSeams(int seamColorRGB) {
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
        BufferedImage img = new BufferedImage(currentImg.length, currentImg[0].length, workingImage.getType());
        for (int i = 0; i < currentImg.length; i++) {
            for (int j = 0; j < currentImg[0].length; j++) {
                img.setRGB(i, j, currentImg[i][j].getRGB());
            }
        }
        return img;
    }

    private void initCurrentImg() {
        currentImg = getColorMatrix(workingImage);
    }

    private void transformToGrey() {
        BufferedImage gr = greyscale();
        grey = getMatrixGreen(gr);
    }

    private void storeAllSeams() {
        storeSeams(seamsNum);
    }

    private void storeSeams(int num) {
        seams = new int[num][grey.length];
        for (int k = 0; k < num; k++) {
            long minCost = Integer.MAX_VALUE;
            int jIndex = -2;
            for (int j = 0; j < grey[0].length - 1; j++) {
                if (M[grey.length - 1][j] < minCost) {
                    minCost = M[grey.length - 1][j];
                    jIndex = j;
                }
            }
            for (int i = grey.length - 1; i >= 0; i--) {
                seams[k][i] = jIndex;
                M[i][jIndex] = Integer.MAX_VALUE;
                long minL = Integer.MAX_VALUE, minU= Integer.MAX_VALUE, minR = Integer.MAX_VALUE;
                if (i - 1 >= 0) {
                    if (jIndex - 1 > 0)
                        minL = M[i - 1][jIndex - 1];
                    if (jIndex + 1 < grey[0].length)
                        minR = M[i - 1][jIndex + 1];
                    minU = M[i - 1][jIndex];

                    if (minL < minU && minL < minR) {
                        jIndex = jIndex - 1;
                    } else if (minR < minL && minR < minU) {
                        jIndex = jIndex + 1;
                    }
                }
            }
        }
        logger.log("finished store seams");
    }

    private int[][] getMatrixGreen(BufferedImage img) {
        int[][] matrix = new int[img.getHeight()][img.getWidth()];
        forEach((y, x) -> matrix[y][x] = new Color(img.getRGB(x, y)).getGreen());
        return matrix;
    }

    private Color[][] getColorMatrix(BufferedImage img) {
        Color[][] matrix = new Color[img.getHeight()][img.getWidth()];
        forEach((y, x) -> {
            matrix[y][x] = new Color(img.getRGB(x, y));
        });
        return matrix;
    }

    private void calcE() {
        E = new long[grey.length][grey[0].length];
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[0].length; j++) {
                int J = j + 1, I = i + 1;
                if (i + 1 >= grey.length) {
                    I = i - 1;
                }
                if (j + 1 >= grey[0].length) {
                    J = j - 1;
                }
                E[i][j] = Math.abs(grey[i][J] - grey[i][j]) + Math.abs(grey[I][j] - grey[i][j]);
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
                    newGrey[indexI][indexJ] = grey[i][j];
                    newImg[indexI][indexJ] = currentImg[i][j];
                    newImageMask[indexI][indexJ] = imageMask[i][j];
                    indexJ++;
                }
            }
            indexI++;
        }
        grey = newGrey;
        currentImg = newImg;
        imageMask = newImageMask;
    }

    private void addSeam() {
        calcSeamsMatrix();
        BufferedImage ans = new BufferedImage(workingImage.getWidth() - 1, workingImage.getHeight() - 1, workingImage.getType());
        for (int i = 0; i < workingImage.getHeight(); i++) {
            for (int j = 0; j < workingImage.getWidth(); j++) {
                if (seamsMatrix[i][j] != -1) {
                    ans.setRGB(j, i, workingImage.getRGB(j, i));
                }
            }
        }
    }

    private void calcSeamMatrix() {
        seamsMatrix = new int[grey.length][grey[0].length];
        for (int i = 0; i < grey.length; i++) {
            seamsMatrix[i][seams[0][i]] = -1;
        }

    }

    private void calcSeamsMatrix() {
        seamsMatrix = new int[grey[0].length][grey.length];
        for (int k = 0; k < seams.length; k++) {
            for (int i = 0; i < grey.length; i++) {
                seamsMatrix[i][seams[k][i]] = -1;
            }
        }
    }

    private void calcM() {
        M = new long[E.length][E[0].length];
        long Cr, Cl, Cu;
        long minL, minU, minR;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                if (i > 0 && j + 1 == M[0].length) {
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

    private void calcMWithMask() {
        for (int i = 0; i < imageMask.length; i++) {
            for (int j = 0; j < imageMask[0].length; j++) {
                if (imageMask[i][j] == true) {
                    M[i][j] = Integer.MAX_VALUE;
                }
            }
        }
        logger.log("finished calc M with mask");
    }

}
