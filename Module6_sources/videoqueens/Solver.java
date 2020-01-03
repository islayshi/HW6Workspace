package videoqueens;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.JFrame;


class Solver 
{
	private int						nCellsPerSide;
	private HashSet<Board>			solutions;
	private BoardPanel				boardPanel;
	
	
	Solver(int nCellsPerSide, BoardPanel boardPanel)
	{
		this.nCellsPerSide = nCellsPerSide;
		this.boardPanel = boardPanel;
		solutions = new HashSet<Board>();
	}
	
	
	public HashSet<Board> solve()
	{
		Board emptyBoard = new Board(nCellsPerSide);
		solveRecurse(emptyBoard);
		return solutions;
	}
	
	
	// Returns true if done.
	private boolean solveRecurse(Board board)
	{
		Thread.yield();
		
		boardPanel.setBoard(board);
		
		BoardEvaluation eval = board.evaluate();
		if (eval == BoardEvaluation.ACCEPT)
		{
			solutions.add(board);
			return true;
		}
		else if (eval == BoardEvaluation.CONTINUE)	
		{
			ArrayList<Board> nextBoards = board.generateAllNextBoards();
			for (Board nextBoard: nextBoards)
				if (solveRecurse(nextBoard))
					return true;
			return false;
		}	
		else return false;
	}
}
