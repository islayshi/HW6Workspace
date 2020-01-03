package sudoku;

import java.util.*;

public class Grid {
	private int[][] values;

	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++) {
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i = 0; i < 9; i++) {
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}

	@Override
	public String toString() {
		String s = "";
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char) ('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	//
	// Copy ctor. Duplicates its source. You'll call this 9 times in next9Grids.
	//
	Grid(Grid src) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++)
			for (int i = 0; i < 9; i++)
				values[j][i] = src.values[j][i];
	}

	// Finds an empty member of values[][]. Returns an array list of 9 grids that
	// look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current
	// grid is full. Donâ€™t change
	// â€œthisâ€? grid. Build 9 new grids.
	//
	//
	// Example: if this grid = 1........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	//
	// Then the returned array list would contain:
	//
	// 11....... 12....... 13....... 14....... and so on 19.......
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	//
	public ArrayList<Grid> next9Grids() {
		int xOfNextEmptyCell = 0;
		int yOfNextEmptyCell = 0;

		// Find x,y of an empty cell.
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (values[r][c] == 0) {
					xOfNextEmptyCell = r;
					yOfNextEmptyCell = c;

				}
			}
		}

		// Construct array list to contain 9 new grids.
		ArrayList<Grid> grids = new ArrayList<Grid>();

		// Create 9 new grids as described in the comments above. Add them to grids.
		int valueToAdd = 1;
		for (int i = 0; i < 9; i++) {
			Grid grid = new Grid(this);
			grid.values[xOfNextEmptyCell][yOfNextEmptyCell] = valueToAdd;
			grids.add(grid);
			valueToAdd++;
			System.out.println(grid);
		}

		return grids;
	}

	public int[][] getValues() {
		return values;
	}

	public boolean containsNonZeroRepeat(int[] list) {

		ArrayList seen = new ArrayList();
		for (int value : list) {
			if (seen.contains(value) && value != 0) {
				return true;
			}
			seen.add(value);
		}
		return false;

	}

	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal() {

		// Check every row. If you find an illegal row, return false.
		for (int i = 0; i < 9; i++) {
			if (containsNonZeroRepeat(values[i])) {
				return false;
			}
		}

		// Check every column. If you find an illegal column, return false.
		for (int i = 0; i < 9; i++) {
			int[] arr = new int[9];
			for (int j = 0; j < 9; j++) {
				arr[j] = values[j][i];
			}
			if (containsNonZeroRepeat(arr)) {
				return false;
			}
		}

		// Check every block. If you find an illegal block, return false.
		// use multipliers of three to navigate through each block and
		// save redundant code
		for (int blockY = 0; blockY < 3; blockY++) {
			for (int blockX = 0; blockX < 3; blockX++) {
				int[] arr = new int[9];
				int counter = 0;
				for (int r = blockX * 3; r < blockX * 3 + 3; r++) {
					for (int c = blockY * 3; c < blockY * 3 + 3; c++) {
						arr[counter] = values[r][c];
						counter++;
					}
				}
				if (containsNonZeroRepeat(arr) == true)
					return false;
			}
		}

		// All rows/cols/blocks are legal.
		return true;
	}

	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (values[i][j] == 0) {
					return false;
				}
			}
		}
		return true;
	}

	//
	// Returns true if x is a Grid and, for every (i,j),
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x) {
		if (x instanceof Grid) {
			Grid newX = (Grid) x;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (newX.getValues()[i][j] != this.values[i][j]) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}

	}
}