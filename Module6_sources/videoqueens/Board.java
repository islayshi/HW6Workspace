package videoqueens;

import java.util.*;


class Board {
	private int					nCellsPerSide;
	private HashSet<Cell>		occupiedCells;
	
	
	// Ordinary constructor.
	public Board(int nCellsPerSide)
	{
		this.nCellsPerSide = nCellsPerSide;
		this.occupiedCells = new HashSet<Cell>();
	}
	
	
	// Constructor for generating next possibilities. Copies the input board,
	// then adds 1 more occupied cell.
	private Board(Board source, Cell addedCell)
	{
		this.nCellsPerSide = source.nCellsPerSide;
		this.occupiedCells = new HashSet<Cell>(source.occupiedCells);
		this.occupiedCells.add(addedCell);
		this.toString();
	}
	
	
	public boolean equals(Object x)
	{
		Board that = (Board)x;
		return this.occupiedCells.equals(that.occupiedCells);
	}
	
	
	public int hashCode()
	{
		int hash = 0;
		for (Cell cell: occupiedCells)
			hash += cell.hashCode();
		return hash;
	}
	
	
	// Returns true if no occupied cell threatens another occupied cell.
	boolean isLegal() 
	{
		for (Cell c1: occupiedCells)
			for (Cell c2: occupiedCells)
				if (c1 != c2  &&  c1.threatens(c2))
					return false;
		return true;		
	}
	
	
	// Generates an array list of all possible next moves. 
	ArrayList<Board> generateAllNextBoards()
	{
		ArrayList<Board> nextBoards = new ArrayList<Board>();
		
		for (int row=0; row<nCellsPerSide; row++)
		{
			for (int col=0; col<nCellsPerSide; col++)
			{
				Cell addedCell = new Cell(row, col, occupiedCells.size());
				if (!occupiedCells.contains(addedCell))
				{
					Board nextBoard = new Board(this, addedCell);
					nextBoards.add(nextBoard);
				}
			}
		}
		
		return nextBoards;
	}
	
	
	//
	// Evaluates this board. Returns:
	// 		ACCEPT if this board is legal and complete (all queens are on the board)
	// 		CONTINUE if this board is legal and incomplete (not all queens are on the board)
	//		ABANDON if this board is illegal, so there is no point to continuing 
	//
	BoardEvaluation evaluate()
	{		
		if (!isLegal())
			return BoardEvaluation.ABANDON;	
		else if (occupiedCells.size() == nCellsPerSide)
			return BoardEvaluation.ACCEPT;	
		else
			return BoardEvaluation.CONTINUE;
	}
	
	
	private boolean contains(int r, int c)
	{
		Cell cell = new Cell(r, c, -1);				// eauqls() ignores age
		return occupiedCells.contains(cell);
	}
	
	
	public String toString()
	{
		String s = "";
		for (int row=0; row<nCellsPerSide; row++)
		{
			for (int col=0; col<nCellsPerSide; col++)
			{
				if (contains(row, col))
					s += "Q";
				else
					s += ".";
			}
			s += "\n";
		}
		return s;
	}	
	
	
	HashSet<Cell> getCells()
	{
		return occupiedCells;
	}
	
	
	void add(Cell cell)
	{
		occupiedCells.add(cell);
	}
}
