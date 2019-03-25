public BufferedImage showSeams(int seamColorRGB) 

@param seamColorRGB
@return showSeamsImg

1. Calculates Energy matrix
2. Calculates Costs matrix
3. Uses dynamic programming to find the optimal seam starting from the bottom row of the costs matrix
4. For seamsNum stores each minimal seam in a matrix.
5. Paints the seams on a duplicate of the working image in the seamColorRGB color 