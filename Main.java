// Import the GUI libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* FEATURE IDEAS:
 * Reset Button (with confirmation)
 * Change grid colours (colour picker)
 * Flip piece toggle (white vs black move)
 * Move Log
 * LONGSHOT: Create/Import PGN ?
 */ 

public class Main {
	/**
	 * MAIN METHOD
	 * This main method starts the GUI and runs the createMainWindow() method.
     * This method should not be changed.
	 */
	public static void main (String [] args) {
		javax.swing.SwingUtilities.invokeLater (new Runnable () {
			public void run () {
				createMainWindow ();
			}
		});
	}


	/**
	 * STATIC VARIABLES AND CONSTANTS
	 * Declare the objects and variables that you want to access across
     * multiple methods.
	 */



	/**
	 * CREATE MAIN WINDOW
     * This method is called by the main method to set up the main GUI window.
	 */
	private static void createMainWindow () {
		// Create and set up the window.
		JFrame frame = new JFrame ("Chess");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setResizable (false);
        frame.setSize(new Dimension(1000, 800));

		// The panel that will hold the components in the frame.
		JPanel contentPane = new JPanel ();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(1000, 800));

        // Create main board panel
        JPanel board = new JPanel();
        board.setSize(800, 800);
        board.setLayout(new GridLayout(8, 8));

        BoardSquare[][] boardSquares = new BoardSquare[8][];

        for (int i = 0; i < 8; i++) {
            boardSquares[i] = new BoardSquare[8];
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                    boardSquares[i][j] = new BoardSquare(board, i, j, new Color(0xEFEFD2), boardSquares);
                }
                else {
                    boardSquares[i][j] = new BoardSquare(board, i, j, new Color(0x749454), boardSquares);
                }
            }
        }

        contentPane.add(board, BorderLayout.CENTER);

        // Create sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 800));
        sidebar.setBackground(new Color(0x1E1E25));

        contentPane.add(sidebar, BorderLayout.EAST);

        // Create starting gameboard
        resetBoard(boardSquares);

		// Add the panel to the frame
		frame.setContentPane(contentPane);

		//size the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


    /**
     * HELPER METHODS
     * Methods that you create to manage repetitive tasks.
     */
    
     public static void resetBoard(BoardSquare[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].removePiece();
            }
        }
        for (int i = 0; i < 2; i++) {
            boolean color = ChessPiece.BLACK;
            int row1 = 0;
            int row2 = 1;

            if (i == 1) {
                color = ChessPiece.WHITE;
                row1 = 7;
                row2 = 6;
            }

            board[row1][0].setPiece(new ChessPiece(4, color, board[row1][0], row1, 0));
            board[row1][1].setPiece(new ChessPiece(2, color, board[row1][1], row1, 1));
            board[row1][2].setPiece(new ChessPiece(3, color, board[row1][2], row1, 2));
            board[row1][3].setPiece(new ChessPiece(5, color, board[row1][3], row1, 3));
            board[row1][4].setPiece(new ChessPiece(6, color, board[row1][4], row1, 4));
            board[row1][5].setPiece(new ChessPiece(3, color, board[row1][5], row1, 5));
            board[row1][6].setPiece(new ChessPiece(2, color, board[row1][6], row1, 6));
            board[row1][7].setPiece(new ChessPiece(4, color, board[row1][7], row1, 7));
            for (int j = 0; j < 8; j++) {
                board[row2][j].setPiece(new ChessPiece(1, color, board[row2][j], row2, j));
            }
        }
     }


    /**
     * EVENT LISTENERS
     * Subclasses that handle events (button clicks, mouse clicks and moves,
     * key presses, timer expirations)
     */

}