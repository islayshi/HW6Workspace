package queens;

import java.util.*;


public class Solver {
	private int						nCellsPerSide;
	private HashSet<Board>			solutions;
	
	
	public Solver(int nCellsPerSide) {
		this.nCellsPerSide = nCellsPerSide;
		solutions = new HashSet<Board>();
	}
	
	
	public HashSet<Board> solve() {
		Board emptyBoard = new Board(nCellsPerSide);
		solveRecurse(emptyBoard);
		return solutions;
	}
	
	
	//
	// Evaluates a board. Returns:
	// 		ACCEPT if board is legal and complete (all queens are on the board)
	// 		CONTINUE if board is legal and incomplete (not all queens are on the board)
	//		ABANDON if board is illegal, so there is no point to continuing 
	//
	private BoardEvaluation evaluate(Board board) {		
		if (!board.isLegal())
			return BoardEvaluation.ABANDON;					// illegal
		else if (board.getNOccupiedCells() == nCellsPerSide)
			return BoardEvaluation.ACCEPT;					// legal and complete
		else
			return BoardEvaluation.CONTINUE;			        // legal but incomplete
	}
	
	
	// Adds accepted solutions to the solutions set. This algorithm might find a
	// solution many times; but because solutions is a set, only unique solutions
	// will be reported.
	private void solveRecurse(Board board) {
		BoardEvaluation eval = evaluate(board);
		if (eval == BoardEvaluation.ABANDON)
			return;
		else if (eval == BoardEvaluation.ACCEPT)
		{
			solutions.add(board);
		}
		else {
			ArrayList<Board> nextBoards = board.generateAllNextBoards();
			for (Board nextBoard: nextBoards)
				solveRecurse(nextBoard);
		}	
	}
	
	public static void main(String[] args)
	{
		System.out.println("START");
		Solver solver = new Solver(4);
		HashSet<Board> solutions = solver.solve();
		for (Board solution: solutions)
			System.out.println("------------ SOLUTION:\n" + solution);
		System.out.println(solutions.size() + " solutions");
		System.out.println("DONE");
	}
}
