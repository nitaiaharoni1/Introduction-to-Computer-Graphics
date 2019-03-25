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
            findMinBottomCells(1);
            storeSeams();
            calcSeamMatrix(0);
            removeSeams();
        }
        return paintResultImg();
    }

    private BufferedImage increaseImageWidth() {
        calcE();
        calcM();
        findMinBottomCells(seamsNum);
        storeSeams();
        for (int k = 0; k < seamsNum; k++) {
            calcSeamMatrix(k);
            addSeams();
            updateSortedSeams(k);
        }
        return paintResultImg();
    }

    private void updateSortedSeams(int k) {
        for (int i = k + 1; i < seams.length; i++) {
            for (int j = 0; j < seams[0].length; j++) {
                seams[i][j]++;
            }
        }
    }

    /**
     * @param seamColorRGB
     * @return showSeamsImg
     * <p>
     * 1. Calculates Energy matrix
     * 2. Calculates Costs matrix
     * 3. Uses dynamic programming to find the optimal seam starting from the bottom row of the costs matrix
     * 4. For seamsNum stores each minimal seam in a matrix.
     * 5. Paints the seams on a duplicate of the working image in the seamColorRGB color
     */
    public BufferedImage showSeams(int seamColorRGB) {
        calcE();
        calcM();
        findMinBottomCells(seamsNum);
        storeSeams();
        BufferedImage showSeamsImg = duplicateWorkingImage();
        for (int[] seam : seams) {
            for (int i = 0; i < seams[0].length; i++) {
                showSeamsImg.setRGB(seam[i], i, seamColorRGB);
            }
        }
        return showSeamsImg;
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

    private void storeSeams() {
        int num = seams.length;
        int minIndex;
        for (int k = 0; k < num; k++) {
            minIndex = seams[k][seams[0].length - 1];
            for (int i = seams[0].length - 1; i >= 0; i--) {
                seams[k][i] = minIndex;
                M[i][minIndex] = Long.MAX_VALUE - 1000;
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

            min = Math.min(minU, Math.min(minL, minR));
            if (min == minU) {
            } else if (min == minL && minJ > 0) {
                minJ = minJ - 1;
            } else if (min == minR && minJ < M[0].length - 1) {
                minJ = minJ + 1;
            }
        }
        return minJ;
    }

    private void findMinBottomCells(int num) {
        seams = new int[num][inHeight];
        int minBottomIndex, bottomRow = M.length - 1;
        long minCost;
        for (int k = 0; k < num; k++) {
            minBottomIndex = -1;
            minCost = Long.MAX_VALUE - 1000;
            for (int j = 0; j < M[0].length; j++) {
                if (M[bottomRow][j] < minCost) {
                    minCost = M[bottomRow][j];
                    minBottomIndex = j;
                    if (minCost == 0) break;
                }
            }
            M[bottomRow][minBottomIndex] = Long.MAX_VALUE - 1000;
            seams[k][M.length - 1] = minBottomIndex;
        }
//        if (num != 1)
//            Arrays.sort(minIndexesBottom);
    }

    private void calcE() {
        E = new long[grey.length][grey[0].length];
        long greyIJ;
        int jRight, iDown;
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[0].length; j++) {
                greyIJ = grey[i][j];
                jRight = j + 1;
                iDown = i + 1;
                if (iDown >= E.length)
                    iDown = i - 1;
                if (jRight >= E[0].length)
                    jRight = j - 1;
                if (imageMask[i][j]) {
                    E[i][j] = Long.MAX_VALUE;
                } else {
                    E[i][j] = Math.abs(grey[i][jRight] - greyIJ) + Math.abs(grey[iDown][j] - greyIJ);
                }
                if (E[i][j] < 0) {
                    E[i][j] = Long.MAX_VALUE;
                }
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
                if ((i > 0) && (j == M[0].length - 1)) {
                    minL = M[i - 1][j - 1];
                    minU = M[i - 1][j];
                    Cl = Math.abs(255L - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j - 1]);
                    Cu = Math.abs(255L - grey[i][j - 1]);
                    minL = (minL + Cl < 0) ? Long.MAX_VALUE : (minL + Cl);
                    minU = (minU + Cu < 0) ? Long.MAX_VALUE : (minU + Cu);

                    M[i][j] = E[i][j] + Math.min(minU, minL);

                    //Inside
                } else if (i > 0 && j > 0) {
                    minL = M[i - 1][j - 1];
                    minU = M[i - 1][j];
                    minR = M[i - 1][j + 1];
                    Cl = Math.abs(grey[i][j + 1] - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j - 1]);
                    Cu = Math.abs(grey[i][j + 1] - grey[i][j - 1]);
                    Cr = Math.abs(grey[i][j + 1] - grey[i][j - 1]) + Math.abs(grey[i - 1][j] - grey[i][j + 1]);
                    minL = (minL + Cl < 0) ? Long.MAX_VALUE : (minL + Cl);
                    minR = (minR + Cr < 0) ? Long.MAX_VALUE : (minR + Cr);
                    minU = (minU + Cu < 0) ? Long.MAX_VALUE : (minU + Cu);

                    M[i][j] = E[i][j] + Math.min(minU, Math.min(minL, minR));

                    //Left Column
                } else if (i > 0 && j == 0) {
                    minU = M[i - 1][j];
                    minR = M[i - 1][j + 1];
                    Cu = Math.abs(grey[i][j + 1] - 255L);
                    Cr = Math.abs(grey[i][j + 1] - 255L) + Math.abs(grey[i - 1][j] - grey[i][j + 1]);
                    minR = (minR + Cr < 0) ? Long.MAX_VALUE : (minR + Cr);
                    minU = (minU + Cu < 0) ? Long.MAX_VALUE : (minU + Cu);

                    M[i][j] = E[i][j] + Math.min(minU, minR);

                    //Upper
                } else if (i == 0) {
                    M[i][j] = E[i][j];
                }

                if (M[i][j] < 0) {
                    M[i][j] = Long.MAX_VALUE;
                }
            }
        }
    }


}
