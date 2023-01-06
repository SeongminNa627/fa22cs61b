package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF status;
    private boolean[][] open;
    private int size;
    private int count;
    private int top;
    private int bottom;
    public Percolation(int N){
        /**
         * Create a WQU, and 0 will denote the root of the top, and N^2 + 1 will denote the root of the bottom
         * WeightedQuickUnionUF status = new WeightedQuickUnionUF(N**2 + 2);
         * boolean[][] open = new boolean[n][n];
         */
        count = 0;
        top = size * size + 1;
        bottom = N * N + 1;
        size = N;
        status = new WeightedQuickUnionUF(size*size + 2);
        open = new boolean[size][size];
        for (int i = 1; i < size + 1; i ++){
            status.union(top, i);
            status.union(bottom, bottom - i);
        }

    }       // create N-by-N grid, with all sites initially blocked
    private int ID(int row, int col){
        return size * row + col + 1;
    }
    public void open(int row, int col){
        // open the site (row, col) if it is not open already
        /**
         * Check if the boolean in the array is false; (isOpen)
         * if it is, we check all the four neighbor sites if any of them is open.
         * for (int i = 0; i < 4; i ++ ){
         *      if (adjacentSite isOpen){
         *          connect(thisSite, adjacentSite);
         *          if (adjacentSite isFull){
         *              fullSite = adjacentSite;
         *              status.connect(thisSite, fullSite);
         *          }
         *      }
         * }
         * count ++;
         */

        // if the given site is not open yet, we open it.
        if (!(0 <= row && row < size) || !(0 <= col && col <size)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)){
            open[row][col] = true;
            /** Checking other sites adjacent to the given site.
             *  1. Check if the adjacent site is open.
             *      - if the adjacent site is open, update the status by joining the given site the adjacent site.
             *      - if the adjacent site is full, update the status by joining the given site the top.
             */
            for (int i = -1; i < 2; i = i + 1){
                for (int j = -1; j < 2; j = j + 1){
                    if (0 <= row + i && row + i < size && 0 <= col + j && col + j < size && i * j == 0){
                        int adjacent = ID(row + i, col +j);
                        int site = ID(row, col);
                        if (isOpen(row + i, col + j)){
                            status.union(site, adjacent);
                        }
                        if (isFull(row + i, col + j)){
                            status.union(site, 0);
                        }
                    }
                }
            }
            count ++;
        }
    }
    public boolean isOpen(int row, int col){
        if (!(0 <= row && row < size) || !(0 <= col && col <size)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        return open[row][col];
    }
    public boolean isFull(int row, int col){
        // is the site (row, col) full?
        /**
         * We keep track of both upper and lower sites. Make it sparse as possible.
         * int theSite = N * row + col;
         * connected(theSite,
         *
         */
//        return isConnected(theSite, 0);
        if (!(0 <= row && row < size) || !(0 <= col && col <size)){
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)){
            return false;
        }
        int ID = ID(row, col);
        return status.connected(ID, top);
    }
    public int numberOfOpenSites(){
        // number of open sites
        return count;
    }
    public boolean percolates(){
//        return true;
//        return connected(0, N(N-1));
        return status.connected(top, bottom);
    }              // does the system percolate?

}
