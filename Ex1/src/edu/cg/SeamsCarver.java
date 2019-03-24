package edu.cg;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

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
    private Color[][] currentImg;
    private Color[][] seamsMatrix;
    private long[][] grey;
    private long[][] E;
    private long[][] M;
    private int[][] seams;


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
            calcM();
            storeSeams(1);
            calcSeamMatrix(0);
            removeSeams();
        }
        return paintResultImg();
    }

    private BufferedImage increaseImageWidth() {
        calcE();
        calcM();
        storeSeams(seamsNum);
        for (int k = 0; k < seamsNum; k++) {
            calcSeamMatrix(k);
            addSeams();
            updateSortedSeams();
        }
        return paintResultImg();
    }

    private void updateSortedSeams() {
        for (int k = 0; k < seams.length; k++) {
            for (int i = 0; i < seams[0].length; i++) {
                seams[k][i] += 1;
            }
        }
    }

    private void sortSeams() {
        int[][] sorted = new int[seams.length][seams[0].length];
        for (int k = 0; k < seams.length; k++) {
            long min = Long.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < seams.length; j++) {
                if (seams[j][0] < min) {
                    min = seams[j][0];
                    minIndex = j;
                }
            }
            sorted[k] = seams[minIndex].clone();
            seams[minIndex][0] = Integer.MAX_VALUE;
        }
        seams = sorted;
    }

    public BufferedImage showSeams(int seamColorRGB) {
        calcE();
        calcM();
        storeSeams(seamsNum);
        sortSeams();
        BufferedImage res = workingImage;
        for (int[] seam : seams) {
            for (int i = 0; i < seams[0].length; i++) {
                res.setRGB(seam[i], i, seamColorRGB);
            }
        }
        return res;
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
        Color[][] matrix1 = new Color[workingImage.getHeight()][workingImage.getWidth()];
        Color[][] matrix2 = new Color[workingImage.getHeight()][workingImage.getWidth()];
        forEach((y, x) -> {
            matrix1[y][x] = new Color(workingImage.getRGB(x, y));
            matrix2[y][x] = new Color(workingImage.getRGB(x, y));
        });
        currentImg = matrix1;
        seamsMatrix = matrix2;
    }

    private void transformToGrey() {
        BufferedImage greyImg = greyscale();
        long[][] matrix = new long[greyImg.getHeight()][greyImg.getWidth()];
        forEach((y, x) -> matrix[y][x] = new Color(greyImg.getRGB(x, y)).getGreen());
        grey = matrix;
    }

    private void storeSeams(int num) {
        seams = new int[num][grey.length];
        int[] minIndexesBottom;
        int minIndex;
        minIndexesBottom = findMinBottomCellsSorted(num);
        for (int k = 0; k < minIndexesBottom.length; k++) {
            minIndex = minIndexesBottom[k];
            for (int i = grey.length - 1; i >= 0; i--) {
                seams[k][i] = minIndex;
                M[i][minIndex] = Long.MAX_VALUE;
                minIndex = findMinFromUpperCells(i, minIndex);
            }
        }
    }

    private int findMinFromUpperCells(int i, int minJ) {
        long minL = Long.MAX_VALUE, minR = Long.MAX_VALUE, minU, min;
        if (i > 0) {
            if (minJ > 0)
                minL = M[i - 1][minJ - 1];
            if (minJ < M[0].length - 1)
                minR = M[i - 1][minJ + 1];
            minU = M[i - 1][minJ];

            min = Math.min(Math.min(minL, minR), minU);
            if (min == minU) {
            } else if (min == minL && minJ > 0) {
                minJ -= 1;
            } else if (min == minR && minJ < M[0].length - 1) {
                minJ += 1;
            }
        }
        return minJ;
    }

    private int[] findMinBottomCellsSorted(int num) {
        int[] minIndexesBottom = new int[num];
        int minBottomIndex, bottomRow = M.length - 1;
        long minCost;
        for (int k = 0; k < num; k++) {
            minBottomIndex = -1;
            minCost = Long.MAX_VALUE;
            for (int j = 0; j < M[0].length; j++) {
                if (M[bottomRow][j] < minCost) {
                    minCost = M[bottomRow][j];
                    minBottomIndex = j;
                    if (minCost == 0) break;
                }
            }
            M[bottomRow][minBottomIndex] = Long.MAX_VALUE;
            minIndexesBottom[k] = minBottomIndex;
        }
        if (num != 1)
            Arrays.sort(minIndexesBottom);
        return minIndexesBottom;
    }

    private void calcE() {
        E = new long[grey.length][grey[0].length];
        long greyIJ, maskCost;
        int jRight, iDown, jLeft, iUp;
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[0].length; j++) {
                greyIJ = grey[i][j];
                maskCost = 0;
                jRight = j + 1;
                iDown = i + 1;
//                jLeft = j - 1;
//                iUp = i - 1;
                if (iDown >= E.length)
                    iDown = i;
//                if (iUp <0 )
//                    iUp = i;
                if (jRight >= E[0].length)
                    jRight = j;
//                if (jLeft < 0)
//                    jLeft = j;
                if (imageMask[i][j])
                    maskCost = Long.MAX_VALUE;
                E[i][j] = Math.abs(grey[i][jRight] - greyIJ) /*+ Math.abs(grey[i][jLeft] - greyIJ)*/ + Math.abs(grey[iDown][j] - greyIJ)/*+ Math.abs(grey[iUp][j] - greyIJ)*/ + maskCost;
            }
        }
    }

    private void removeSeams() {
        long[][] newGrey = new long[grey.length][grey[0].length - 1];
        Color[][] newImg = new Color[currentImg.length][currentImg[0].length - 1];
        Color[][] newSeamsMat = new Color[currentImg.length][currentImg[0].length - 1];
        boolean[][] newImageMask = new boolean[imageMask.length][imageMask[0].length - 1];
        int indexJ;
        for (int i = 0; i < seamsMatrix.length; i++) {
            indexJ = 0;
            for (int j = 0; j < seamsMatrix[0].length; j++) {
                if (seamsMatrix[i][j] != null) {
                    newGrey[i][indexJ] = grey[i][j];
                    indexJ = getIndexJ(newImg, newSeamsMat, newImageMask, indexJ, i, j);
                }
            }
        }
        grey = newGrey;
        currentImg = newImg;
        imageMask = newImageMask;
        seamsMatrix = newSeamsMat;
    }

    private void addSeams() {
        Color[][] newImg = new Color[currentImg.length][currentImg[0].length + 1];
        Color[][] newSeamsMat = new Color[currentImg.length][currentImg[0].length + 1];
        boolean[][] newImageMask = new boolean[imageMask.length][imageMask[0].length + 1];
        int indexJ;
        for (int i = 0; i < seamsMatrix.length; i++) {
            indexJ = 0;
            for (int j = 0; j < seamsMatrix[0].length; j++) {
                if (seamsMatrix[i][j] == null) {
                    indexJ = getIndexJ(newImg, newSeamsMat, newImageMask, indexJ, i, j);
                    indexJ = getIndexJ(newImg, newSeamsMat, newImageMask, indexJ, i, j);
                } else {
                    indexJ = getIndexJ(newImg, newSeamsMat, newImageMask, indexJ, i, j);
                }
            }
        }
        currentImg = newImg;
        seamsMatrix = newSeamsMat;
        imageMask = newImageMask;
    }

    private int getIndexJ(Color[][] newImg, Color[][] newSeamsMat, boolean[][] newImageMask, int indexJ, int i, int j) {
        newImg[i][indexJ] = currentImg[i][j];
        newSeamsMat[i][indexJ] = currentImg[i][j];
        newImageMask[i][indexJ] = imageMask[i][j];
        indexJ++;
        return indexJ;
    }

    private void calcSeamMatrix(int k) {
        for (int i = 0; i < seams[0].length; i++) {
            seamsMatrix[i][seams[k][i]] = null;
        }
    }


    private void calcM() {
        M = new long[E.length][E[0].length];
        long Cr, Cl, Cu;
        long minL, minU, minR;
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                //Right Column
                if (i > 0 && j + 1 >= M[0].length) {
                    minL = M[i - 1][j - 1];
                    minU = M[i - 1][j];

                    //todo: grey[i][j - 1])?
                    Cl = Math.abs(255L - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j - 1]);
                    Cu = Math.abs(255L - grey[i][j - 1]);

                    M[i][j] = E[i][j] + Math.min(minL + Cl, minU + Cu);

                    //Inside
                } else if (i > 0 && j > 0) {
                    minL = M[i - 1][j - 1];
                    minU = M[i - 1][j];
                    minR = M[i - 1][j + 1];

                    Cl = Math.abs(grey[i][j + 1] - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j - 1]);
                    Cu = Math.abs(grey[i][j + 1] - grey[i][j - 1]);
                    Cr = Math.abs(grey[i][j + 1] - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j + 1]);

                    M[i][j] = E[i][j] + Math.min(Math.min(minL + Cl, minU + Cu), minR + Cr);

                    //Left Column
                } else if (i > 0 && j == 0) {
                    minU = M[i - 1][j];
                    minR = M[i - 1][j + 1];

                    //todo: grey[i][j + 1])?
                    Cu = Math.abs(grey[i][j + 1] - 255L);
                    Cr = Math.abs(grey[i][j + 1] - 255L) + Math.abs(grey[i - 1][j] - grey[i][j + 1]);

                    M[i][j] = E[i][j] + Math.min(minR + Cr, minU + Cu);

                    //Upper
                } else if (i == 0) {
                    M[i][j] = E[i][j];
                }
            }
        }
    }


}
