// Import the GUI libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.colorchooser.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;
import java.text.DecimalFormat;

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

    private static JPanel contentPane;

    private static ChessPiece selectedPiece;

    private static ChessPiece lastMovedPiece = null;

    private static BoardSquare[][] boardSquares;

    private static ArrayList<ChessPiece> activePieces;

    private static int moveCount = 1;

    private static boolean colorToMove = ChessPiece.WHITE;

    private static ChessPiece whiteKing;
    private static ChessPiece blackKing;

    private static Timer clockTick;

    private static boolean waitingForPromotion = false;

    private static int whiteTime = 600000;
    private static JLabel whiteTimeLabel;
    private static int blackTime = 600000;
    private static JLabel blackTimeLabel;

    private static DecimalFormat secondsFormatter = new DecimalFormat("00");

    private static ArrayList<String> moveLog;

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
		contentPane = new JPanel ();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(1000, 800));

        // Create main board panel
        JPanel board = new JPanel();
        board.setSize(800, 800);
        board.setLayout(new GridLayout(8, 8));

        activePieces = new ArrayList<ChessPiece>(32);
        boardSquares = new BoardSquare[8][];

        for (int i = 0; i < 8; i++) {
            boardSquares[i] = new BoardSquare[8];
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                    boardSquares[i][j] = new BoardSquare(board, i, j, new Color(0xEFEFD2));
                }
                else {
                    boardSquares[i][j] = new BoardSquare(board, i, j, new Color(0x749454));
                }
            }
        }

        contentPane.add(board, BorderLayout.CENTER);

        // Create sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 800));
        sidebar.setBackground(new Color(0x1E1E25));

        Font labelFont = new Font("/Fonts/Lato-Bold.ttf", Font.BOLD, 24);
        Font timerFont = new Font("Oswald-Bold.ttf", Font.BOLD, 30);
        

        sidebar.add(Box.createRigidArea(new Dimension(200, 20)));

        // TODO: Make Icons
        JLabel blackPlayerLabel = new JLabel("Black");
        blackPlayerLabel.setFont(labelFont);
        blackPlayerLabel.setForeground(Color.WHITE);
        blackPlayerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(blackPlayerLabel);

        sidebar.add(Box.createRigidArea(new Dimension(200, 10)));

        blackTimeLabel = new JLabel(String.valueOf(blackTime / 1000 / 60 + ":" + secondsFormatter.format(blackTime / 1000 % 60)));
        blackTimeLabel.setBackground(new Color(0x000000));
        blackTimeLabel.setForeground(new Color(0xF0F0F0));
        blackTimeLabel.setOpaque(true);
        blackTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        blackTimeLabel.setFont(timerFont);
        sidebar.add(blackTimeLabel);

        sidebar.add(Box.createRigidArea(new Dimension(200, 50)));

        JLabel whitePlayerLabel = new JLabel("White");
        whitePlayerLabel.setFont(labelFont);
        whitePlayerLabel.setForeground(Color.WHITE);
        whitePlayerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(whitePlayerLabel);

        sidebar.add(Box.createRigidArea(new Dimension(200, 10)));

        whiteTimeLabel = new JLabel(String.valueOf(whiteTime / 1000 / 60 + ":" + secondsFormatter.format(whiteTime / 1000 % 60)));
        whiteTimeLabel.setBackground(new Color(0xF0F0F0));
        whiteTimeLabel.setForeground(new Color(0x101010));
        whiteTimeLabel.setOpaque(true);
        whiteTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        whiteTimeLabel.setFont(timerFont);
        sidebar.add(whiteTimeLabel);

        sidebar.add(Box.createVerticalGlue());

        JLabel moveLogTitle = new JLabel("Move Log");
        moveLogTitle.setFont(new Font("/Fonts/Lato-Bold.ttf", Font.BOLD, 12));
        moveLogTitle.setForeground(Color.WHITE);
        moveLogTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(moveLogTitle);

        JTextArea moveLog = new JTextArea(4, 1);
        moveLog.setMaximumSize(new Dimension(200, 150));
        moveLog.setPreferredSize(new Dimension(200, 150));
        moveLog.setBackground(new Color(0x303034));
        moveLog.setForeground(Color.WHITE);
        moveLog.setEditable(false);
        moveLog.setText("Test test test haha");
        sidebar.add(moveLog);

        contentPane.add(sidebar, BorderLayout.EAST);

        // Create starting gameboard
        resetBoard(boardSquares, activePieces);

		// Add the panel to the frame
		frame.setContentPane(contentPane);

		//size the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

        clockTick = new Timer(10, new TimerTick());
	}


    /**
     * HELPER METHODS
     * Methods that you create to manage repetitive tasks.
     */

    public static BoardSquare[][] getBoard() {
        return boardSquares;
    }

    public static ArrayList<ChessPiece> getPieceList() {
        return activePieces;
    }

    public static int getTurnNumber() {
        return moveCount;
    }

    public static boolean getTurn() {
        return colorToMove;
    }

    public static void toggleTurn() {
        colorToMove = !colorToMove;
        if (colorToMove == ChessPiece.WHITE) {
            moveCount++;
        }
    }

    public static boolean inCheck(ChessPiece king) {
        boolean kingColor = king.getColor();
        int kingxPos = king.getBoardSquare().getxPos();
        int kingyPos = king.getBoardSquare().getyPos();
        for (int i = kingxPos + 1; i < 8; i++) {
            if (!boardSquares[i][kingyPos].hasPiece()) {
                continue;
            }
            ChessPiece piece = boardSquares[i][kingyPos].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        for (int i = kingxPos - 1; i >= 0; i--) {
            if (!boardSquares[i][kingyPos].hasPiece()) {
                continue;
            }
            ChessPiece piece = boardSquares[i][kingyPos].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        for (int i = kingyPos + 1; i < 8; i++) {
            if (!boardSquares[kingxPos][i].hasPiece()) {
                continue;
            }
            ChessPiece piece = boardSquares[kingxPos][i].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        for (int i = kingyPos - 1; i >= 0; i--) {
            if (!boardSquares[kingxPos][i].hasPiece()) {
                continue;
            }
            ChessPiece piece = boardSquares[kingxPos][i].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        for (int i = 1; i < 8; i++) {
            if (kingxPos + i == 8 || kingyPos + i == 8) {
                break;
            }
            if (getBoard()[kingxPos + i][kingyPos + i].hasPiece()) {
                ChessPiece piece = getBoard()[kingxPos + i][kingyPos + i].getPiece();
                if (piece.getIdentifier() == ChessPiece.BISHOP || piece.getIdentifier() == ChessPiece.QUEEN) {
                    if (piece.getColor() != kingColor) {
                        return true;
                    }
                }
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (kingxPos + i == 8 || kingyPos - i == -1) {
                break;
            }
            if (getBoard()[kingxPos + i][kingyPos - i].hasPiece()) {
                ChessPiece piece = getBoard()[kingxPos + i][kingyPos - i].getPiece();
                if (piece.getIdentifier() == ChessPiece.BISHOP || piece.getIdentifier() == ChessPiece.QUEEN) {
                    if (piece.getColor() != kingColor) {
                        return true;
                    }
                }
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (kingxPos - i == -1 || kingyPos + i == 8) {
                break;
            }
            if (getBoard()[kingxPos - i][kingyPos + i].hasPiece()) {
                ChessPiece piece = getBoard()[kingxPos - i][kingyPos + i].getPiece();
                if (piece.getIdentifier() == ChessPiece.BISHOP || piece.getIdentifier() == ChessPiece.QUEEN) {
                    if (piece.getColor() != kingColor) {
                        return true;
                    }
                }
                break;
            }
        }
        for (int i = 1; i < 8; i++) {
            if (kingxPos - i == -1 || kingyPos - i == -1) {
                break;
            }
            if (getBoard()[kingxPos - i][kingyPos - i].hasPiece()) {
                ChessPiece piece = getBoard()[kingxPos - i][kingyPos - i].getPiece();
                if (piece.getIdentifier() == ChessPiece.BISHOP || piece.getIdentifier() == ChessPiece.QUEEN) {
                    if (piece.getColor() != kingColor) {
                        return true;
                    }
                }
                break;
            }
        }
        for (int i = kingyPos - 2; i <= kingyPos + 2; i++) {
            if (i < 0 || i > 7 || i == kingyPos) {
                continue;
            }
            if (i == kingyPos + 2 || i == kingyPos - 2) {
                if (kingxPos + 1 < 8 && getBoard()[kingxPos + 1][i].hasPiece() && getBoard()[kingxPos + 1][i].getPiece().getIdentifier() == ChessPiece.KNIGHT) {
                    if (getBoard()[kingxPos + 1][i].getPiece().getColor() != king.getColor()) {
                        return true;
                    }
                }
                if (kingxPos - 1 >= 0 && getBoard()[kingxPos - 1][i].hasPiece() && getBoard()[kingxPos - 1][i].getPiece().getIdentifier() == ChessPiece.KNIGHT) {
                    if (getBoard()[kingxPos - 1][i].getPiece().getColor() != king.getColor()) {
                        return true;
                    }
                }
            }
            else {
                if (kingxPos + 2 < 8 && getBoard()[kingxPos + 2][i].hasPiece() && getBoard()[kingxPos + 2][i].getPiece().getIdentifier() == ChessPiece.KNIGHT) {
                    if (getBoard()[kingxPos + 2][i].getPiece().getColor() != king.getColor()) {
                        return true;
                    }
                }
                if (kingxPos - 2 >= 0 && getBoard()[kingxPos - 2][i].hasPiece() && getBoard()[kingxPos - 2][i].getPiece().getIdentifier() == ChessPiece.KNIGHT) {
                    if (getBoard()[kingxPos - 2][i].getPiece().getColor() != king.getColor()) {
                        return true;
                    }
                }
            }
        }
        if (kingColor == ChessPiece.WHITE) {
            if (kingxPos - 1 >= 0 && kingxPos - 1 >= 0) {
                if (getBoard()[kingxPos - 1][kingyPos - 1].hasPiece()) {
                    if (getBoard()[kingxPos - 1][kingyPos - 1].getPiece().getIdentifier() == ChessPiece.PAWN) {
                        if (getBoard()[kingxPos - 1][kingyPos - 1].getPiece().getColor() != kingColor) {
                            return true;
                        }
                    }
                }
            }
            if (kingxPos - 1 >= 0 && kingyPos + 1 < 8) {
                if (getBoard()[kingxPos - 1][kingyPos + 1].hasPiece()) {
                    if (getBoard()[kingxPos - 1][kingyPos + 1].getPiece().getIdentifier() == ChessPiece.PAWN) {
                        if (getBoard()[kingxPos - 1][kingyPos + 1].getPiece().getColor() != kingColor) {
                            return true;
                        }
                    }
                }
            }
        }
        if (kingColor == ChessPiece.BLACK) {
            if (kingxPos + 1 < 8 && kingyPos + 1 < 8) {
                if (getBoard()[kingxPos + 1][kingyPos + 1].hasPiece()) {
                    if (getBoard()[kingxPos + 1][kingyPos + 1].getPiece().getIdentifier() == ChessPiece.PAWN) {
                        if (getBoard()[kingxPos + 1][kingyPos + 1].getPiece().getColor() != kingColor) {
                            return true;
                        }
                    }
                }
            }
            if (kingxPos + 1 < 8 && kingyPos - 1 >= 0) {
                if (getBoard()[kingxPos + 1][kingyPos - 1].hasPiece()) {
                    if (getBoard()[kingxPos + 1][kingyPos - 1].getPiece().getIdentifier() == ChessPiece.PAWN) {
                        if (getBoard()[kingxPos + 1][kingyPos - 1].getPiece().getColor() != kingColor) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static void resetBoard(BoardSquare[][] board, ArrayList<ChessPiece> pieceList) {
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

            pieceList.add(board[row1][0].setPiece(new ChessPiece(ChessPiece.ROOK, color, board[row1][0], row1, 0)));
            pieceList.add(board[row1][1].setPiece(new ChessPiece(ChessPiece.KNIGHT, color, board[row1][1], row1, 1)));
            pieceList.add(board[row1][2].setPiece(new ChessPiece(ChessPiece.BISHOP, color, board[row1][2], row1, 2)));
            pieceList.add(board[row1][3].setPiece(new ChessPiece(ChessPiece.QUEEN, color, board[row1][3], row1, 3)));
            pieceList.add(board[row1][4].setPiece(new ChessPiece(ChessPiece.KING, color, board[row1][4], row1, 4)));
            pieceList.add(board[row1][5].setPiece(new ChessPiece(ChessPiece.BISHOP, color, board[row1][5], row1, 5)));
            pieceList.add(board[row1][6].setPiece(new ChessPiece(ChessPiece.KNIGHT, color, board[row1][6], row1, 6)));
            pieceList.add(board[row1][7].setPiece(new ChessPiece(ChessPiece.ROOK, color, board[row1][7], row1, 7)));

            for (int j = 0; j < 8; j++) {
                pieceList.add(board[row2][j].setPiece(new ChessPiece(ChessPiece.PAWN, color, board[row2][j], row2, j)));
            }
        }
        whiteKing = board[7][4].getPiece();
        blackKing = board[0][4].getPiece();

        moveLog = new ArrayList<String>();
    }

    public static void updateHighLight(ChessPiece newHighlighted) {
        for (ChessPiece piece : getPieceList()) {
            if (piece != newHighlighted) {
                piece.getBoardSquare().removeHighlight();
            }
        }
        selectedPiece = newHighlighted;
        newHighlighted.getBoardSquare().setHighlighed();
    }

    public static ChessPiece getSelectedPiece() {
        return selectedPiece;
    }

    public static void resetTargetted() {
        for (BoardSquare[] row : getBoard()) {
            for (BoardSquare square : row) {
                square.removeHighlight();
            }
        }
        selectedPiece = null;
    }

    public static ChessPiece getBlackKing() {
        return blackKing;
    }

    public static ChessPiece getWhiteKing() {
        return whiteKing;
    }

    public static void startTimer() {
        clockTick.start();
    }

    public static void setLastMoved(ChessPiece piece) {
        lastMovedPiece = piece;
    }

    public static ChessPiece getLastMoved() {
        return lastMovedPiece;
    }

    public static boolean isWaitingForPromotion() {
        return waitingForPromotion;
    }

    public static void setWaitingForPromotion() {
        waitingForPromotion = true;
    }
    
    public static void removeWaitingForPromotion() {
        waitingForPromotion = false;
    }

    public static void revalidate() {
        contentPane.revalidate();
    }

    public static void endGame(boolean color) {
        if (color == ChessPiece.WHITE) {
            System.out.println("White Wins!");
        }
        else {
            System.out.println("Black Wins!");
        }
        System.out.println(moveLog);
    }

    public static void endGame(String result) {
        System.out.println("Draw - " + result + "!");
        System.out.println(moveLog);
    }

    public static String identifierToLetter(int identifier) {
        switch (identifier) {
            case 2:
                return "N";
            case 3:
                return "B";
            case 4:
                return "R";
            case 5:
                return "Q";
            case 6:
                return "K";
        }
        return "";
    }

    public static String columnNumberToLetter(int number) {
        switch (number) {
            case 0:
                return "a";
            case 1: 
                return "b";
            case 2:
                return "c";
            case 3:
                return "d";
            case 4:
                return "e";
            case 5:
                return "f";
            case 6:
                return "g";
            case 7:
                return "h";
        }
        return "";
    
    }

    public static void updateMoveLog(String currentMove) {
        moveLog.add(currentMove);
    }

    /**
     * EVENT LISTENERS
     * Subclasses that handle events (button clicks, mouse clicks and moves,
     * key presses, timer expirations)
     */


    private static class TimerTick extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
            if (colorToMove == ChessPiece.WHITE) {
                whiteTime -= 15;
            }
            else {
                blackTime -= 15;
            }
            whiteTimeLabel.setText(String.valueOf(whiteTime / 1000 / 60 + ":" + secondsFormatter.format(whiteTime / 1000 % 60)));
            blackTimeLabel.setText(String.valueOf(blackTime / 1000 / 60 + ":" + secondsFormatter.format(blackTime / 1000 % 60)));
        }
    }
}