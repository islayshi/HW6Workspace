package videoqueens;

import java.util.*;
import java.awt.*;
import javax.swing.*;


class BoardPanel extends JPanel
{
	private final static int		CELL_SIDE_PIX		=  80;
	private final static int		QUEEN_DIAM_PIX		=  60;
	private final static int		QUEEN_INSET_PIX		= (CELL_SIDE_PIX - QUEEN_DIAM_PIX) / 2;
	private final static Color		BLACK_BG			= Color.GRAY;
	private final static Color		WHITE_BG			= new Color(50, 50, 50);
	private final static Color[]	QUEEN_COLORS		=
	{
		new Color(125, 0, 255), Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, Color.PINK
	};
	
	
	private int						nCellsPerSide;
	private Board					board;
	private Solver					solver;
	
	
	BoardPanel(int nCellsPerSide)
	{
		this.nCellsPerSide = nCellsPerSide;
		this.board = new Board(nCellsPerSide);
		solver = new Solver(nCellsPerSide, this);
		
		int pref = CELL_SIDE_PIX * nCellsPerSide;
		setPreferredSize(new Dimension(pref, pref));
	}
	
	
	public void paintComponent(Graphics g)
	{
		// Paint board. NW corner is white.
		int y = 0;
		for (int row=0; row<nCellsPerSide; row++)
		{
			boolean nextIsWhite = row % 2 == 0;
			int x = 0;
			for (int col=0; col<nCellsPerSide; col++)
			{
				Color fill = nextIsWhite  ?  WHITE_BG  :  BLACK_BG;
				g.setColor(fill);
				nextIsWhite = !nextIsWhite;
				g.fillRect(x, y, CELL_SIDE_PIX, CELL_SIDE_PIX);
				x += CELL_SIDE_PIX;
			}
			y += CELL_SIDE_PIX;
		}
		
		// Paint queens.
		if (board != null)
		{
			for (Cell cell: board.getCells())
			{
				g.setColor(QUEEN_COLORS[cell.getAge()]);
				int[] coords = cell.getCoords();
				int x = coords[1] * CELL_SIDE_PIX  +  QUEEN_INSET_PIX;
				y = coords[0] * CELL_SIDE_PIX  +  QUEEN_INSET_PIX;
				g.fillOval(x, y, QUEEN_DIAM_PIX, QUEEN_DIAM_PIX);
			}
		}
	}
	
	
	private class RunThread extends Thread
	{
		public void run()
		{
			setPriority(2);
			solver.solve();
		}
	}
	
	
	private class CheaterThread extends Thread
	{
		public void run()
		{
			while (true)
			{
				try
				{ 
					Thread.sleep(100);
				}
				catch (InterruptedException x) { }
				repaint();
			}
		}
	}
	
	
	public void solve()
	{
		(new CheaterThread()).start();
		(new RunThread()).start();
	}
	
	
	void setBoard(Board board)
	{
		
		this.board = board;
		repaint();
	}

	static void sop(Object x) { System.out.println(x); }
}
