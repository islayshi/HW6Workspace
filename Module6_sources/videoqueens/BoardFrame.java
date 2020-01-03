package videoqueens;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


class BoardFrame extends JFrame implements ActionListener
{
	private BoardPanel			boardPanel;
	private JToggleButton		runBtn;
	
	
	BoardFrame() 
	{
		boardPanel = new BoardPanel(8);
		add(boardPanel, BorderLayout.CENTER);
		
		JPanel pan = new JPanel();
		runBtn = new JToggleButton("Run");
		runBtn.addActionListener(this);
		pan.add(runBtn);
		add(pan, BorderLayout.NORTH);
		pack();
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == runBtn)
			boardPanel.solve();	
	}
	
	
	public static void main(String[] args)
	{
		BoardFrame frame = new BoardFrame();
		frame.setVisible(true);
	}
}
