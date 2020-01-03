package videoqueens;


class Cell
{
	private int				row;
	private int				col;
	private int				age;
	
	Cell(int row, int col, int age)
	{
		this.row = row;
		this.col = col;
		this.age = age;
	}
	
	
	public boolean equals(Object x)
	{
		Cell that = (Cell)x;
		return this.row == that.row  &&  this.col == that.col;
	}
	
	
	public int hashCode()
	{
		return row*10 + col;
	}
	
	
	int[] getCoords()
	{
		return new int[] { row, col };
	}
	
	
	int getAge()
	{
		return age;
	}
	
	
	boolean threatens(Cell that)
	{
		int rowDelta = this.row - that.row;
		int colDelta = this.col - that.col;
		
		if (rowDelta == 0  ||  colDelta == 0)
			return true;		// same row or col
		
		if (rowDelta == colDelta  ||  rowDelta == -colDelta)
			return true;		// same diagonal
		
		return false;
	}
} 
