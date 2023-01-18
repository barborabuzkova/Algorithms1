import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF findUf;
    private WeightedQuickUnionUF percUf;
    private int num = 0; // count open squares
    private int size; // keep track of n

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new boolean[n][n];
        size = n;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i - 1][j - 1] = false; // closed is false, open is true
            }
        }
        findUf = new WeightedQuickUnionUF(n * n + 1);
        percUf = new WeightedQuickUnionUF(n * n + 2);
    }

    private int topIndex() {
        return size * size;
    }
    private int bottomIndex() {
        return size * size + 1;
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }
        if (!grid[row - 1][col - 1]) { // if not already open
            grid[row - 1][col - 1] = true;
            num++;
            if (row != size) { // below
                if (grid[row][col - 1]) {
                    findUf.union(convert(row, col - 1), convert(row - 1, col - 1));
                    percUf.union(convert(row, col - 1), convert(row - 1, col - 1));
                }
            } else {
                percUf.union(bottomIndex(), convert(row - 1, col - 1)); // bottom union
            }
            if (row != 1) { // above
                if (grid[row - 2][col - 1]) {
                    findUf.union(convert(row - 2, col - 1), convert(row - 1, col - 1));
                    percUf.union(convert(row - 2, col - 1), convert(row - 1, col - 1));
                }
            } else {
                findUf.union(topIndex(), convert(row - 1, col - 1)); // top union
                percUf.union(topIndex(), convert(row - 1, col - 1)); // top union
            }
            if (col != size) { // right
                if (grid[row - 1][col]) {
                    findUf.union(convert(row - 1, col), convert(row - 1, col - 1));
                    percUf.union(convert(row - 1, col), convert(row - 1, col - 1));
                }
            }
            if (col != 1) { // left
                if (grid[row - 1][col - 2]) {
                    findUf.union(convert(row - 1, col - 2), convert(row - 1, col - 1));
                    percUf.union(convert(row - 1, col - 2), convert(row - 1, col - 1));
                }
            }
        }
    }

    /**
     * Converts from 2D grid to 1D array
     *
     * @param row 0 to n-1
     * @param col 0 to n-1
     * @return 2d coordinates 0 to n^2-1
     */
    private int convert(int row, int col) {
        return row * size + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full? (connected to top)
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }
        return findUf.find(convert(row - 1, col - 1)) == findUf.find(topIndex());
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return num;
    }

    // does the system percolate?
    public boolean percolates() {
        return percUf.find(bottomIndex()) == percUf.find(topIndex());
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(1, 2);
        p.open(3, 3);
        p.open(3, 2);
        p.open(4, 5);
        p.open(4, 4);
        p.open(4, 3);
        p.open(5, 1);
        p.open(4, 1);
        p.open(1, 1);
        assert p.isOpen(1, 1) : "should be open";
        assert p.isOpen(4, 4) : "should be open";
        assert !p.isOpen(1, 3) : "should be false";
        assert p.isFull(1, 1) : "should be full";
        assert !p.isFull(4, 4);
        assert 9 == p.numberOfOpenSites();
        assert !p.percolates();
        p.open(2, 2);
        p.open(5, 3);
        assert p.percolates();
        assert !p.isFull(5, 1);
    }
}
