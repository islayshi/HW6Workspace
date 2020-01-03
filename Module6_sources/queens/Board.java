package queens;

import java.util.*;


class Board {
	private int					nCellsPerSide;
	private HashSet<Cell>		occupiedCells;
	

	private class Cell {
		private int				row;
		private int				col;
		
		Cell(int row, int col) {
			this.row = row;
			this.col = col;
		}
		
		// Compatible with hashCode(), so that Cells can be
		// collected in hash sets.
		public boolean equals(Object x) {
			Cell that = (Cell)x;
			return this.row == that.row  &&  this.col == that.col;
		}

		// Compatible with equals(), so that Cells can be
		// collected in hash sets.
		public int hashCode() {
			return row*10 + col;
		}
		
		// Returns true if this cell and that cell lie in same row,
		// column, or diagonal.
		boolean threatens(Cell that) {
			int rowDelta = this.row - that.row;		// 0 => same row
			int colDelta = this.col - that.col;		// 0 => same col
			
			if (rowDelta == 0  ||  colDelta == 0)
				return true;		// same row or col
			
			if (rowDelta == colDelta  ||  rowDelta == -colDelta)
				return true;		// same diagonal
			
			return false;
		}
	}  // End of inner class Cell
	
	
	// Ordinary Board constructor.
	public Board(int nCellsPerSide) {
		this.nCellsPerSide = nCellsPerSide;
		this.occupiedCells = new HashSet<Cell>();
	}
	
	
	// Constructor for generating next possibilities. Copies the input board,
	// then adds 1 more occupied cell.
	private Board(Board source, Cell addedCell) {
		this.nCellsPerSide = source.nCellsPerSide;
		this.occupiedCells = new HashSet<Cell>(source.occupiedCells);
		this.occupiedCells.add(addedCell);
	}
	
	
	public int getNOccupiedCells() {
		return occupiedCells.size();
	}
	
	
	public HashSet<Cell> getOccupiedCells() {
		return occupiedCells;
	}
	
	
	// 2 boards are equal iff they have identical occupied cells. Board instances 
	// will collected in hash sets, so compatible equals() and hashCode() are needed.
	public boolean equals(Object x) {
		Board that = (Board)x;
		return this.occupiedCells.equals(that.occupiedCells);
	}
	

	// Consistent with equals(). A board's hash code is the sum of 
	// the hash codes of its cells.
	public int hashCode() {
		int hash = 0;
		for (Cell cell: occupiedCells)
			hash += cell.hashCode();
		return hash;
	}
	
	
	// Returns true if no occupied cell threatens another occupied cell.
	public boolean isLegal()  {
		for (Cell c1: occupiedCells)
			for (Cell c2: occupiedCells)
				if (c1 != c2  &&  c1.threatens(c2))
					return false;
		return true;		
	}
	
	
	// Generates an array list of all possible next boards. 
	public ArrayList<Board> generateAllNextBoards() {
		ArrayList<Board> nextBoards = new ArrayList<Board>();
		
		for (int row=0; row<nCellsPerSide; row++) {
			for (int col=0; col<nCellsPerSide; col++) {
				Cell addedCell = new Cell(row, col);
				if (!occupiedCells.contains(addedCell)) {
					Board nextBoard = new Board(this, addedCell);
					nextBoards.add(nextBoard);
				}
			}
		}
		
		return nextBoards;
	}
	
	
	// Only used by toString().
	private boolean contains(int r, int c) {
		Cell cell = new Cell(r, c);
		return occupiedCells.contains(cell);
	}
	
	
	public String toString() {
		String s = "";
		for (int row=0; row<nCellsPerSide; row++) {
			for (int col=0; col<nCellsPerSide; col++) {
				if (contains(row, col))
					s += "Q";
				else
					s += ".";
			}
			s += "\n";
		}
		return s;
	}	
	
	
	public static void main(String[] args) {
		Board empty3 = new Board(3);
		ArrayList<Board> nextBoards = empty3.generateAllNextBoards();
		for (Board b: nextBoards)
		{
			System.out.println(b);
		}
		
	}
}
