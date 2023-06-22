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

    private static String moveLog;
    private static JTextArea moveLogTextArea;

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

        // Create array/list to store all board squares/pieces
        activePieces = new ArrayList<ChessPiece>(32);
        boardSquares = new BoardSquare[8][];

        // Occupys each place in the array with a corresponding board square
        for (int i = 0; i < 8; i++) {
            boardSquares[i] = new BoardSquare[8];
            for (int j = 0; j < 8; j++) {
                // Alternates the colour of each square
                if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) {
                    boardSquares[i][j] = new BoardSquare(board, i, j, new Color(0xEFEFD2));
                }
                else {
                    boardSquares[i][j] = new BoardSquare(board, i, j, new Color(0x749454));
                }
            }
        }

        // Adds the board to the content pane
        contentPane.add(board, BorderLayout.CENTER);

        // Create sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.PAGE_AXIS));
        sidebar.setPreferredSize(new Dimension(200, 800));
        sidebar.setBackground(new Color(0x1E1E25));

        // Create fonts for multi-use on future JLabels
        Font labelFont = new Font("/Fonts/Lato-Bold.ttf", Font.BOLD, 24);
        Font timerFont = new Font("Oswald-Bold.ttf", Font.BOLD, 30);

        // Create a 20px tall blank space at the top of the sidebar
        sidebar.add(Box.createRigidArea(new Dimension(200, 20)));

        // TODO: Make Icons
        // Create label for the black player and add to side panel
        JLabel blackPlayerLabel = new JLabel("Black");
        blackPlayerLabel.setFont(labelFont);
        blackPlayerLabel.setForeground(Color.WHITE);
        blackPlayerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(blackPlayerLabel);

        // Create small spacing between player label and player timer
        sidebar.add(Box.createRigidArea(new Dimension(200, 10)));

        // Create player timer and add to side panel
        blackTimeLabel = new JLabel(String.valueOf(blackTime / 1000 / 60 + ":" + secondsFormatter.format(blackTime / 1000 % 60)));
        blackTimeLabel.setBackground(new Color(0x000000));
        blackTimeLabel.setForeground(new Color(0xF0F0F0));
        blackTimeLabel.setOpaque(true);
        blackTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        blackTimeLabel.setFont(timerFont);
        sidebar.add(blackTimeLabel);

        // Add 50px spacing
        sidebar.add(Box.createRigidArea(new Dimension(200, 50)));

        // Create white player label and add to sidebar
        JLabel whitePlayerLabel = new JLabel("White");
        whitePlayerLabel.setFont(labelFont);
        whitePlayerLabel.setForeground(Color.WHITE);
        whitePlayerLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(whitePlayerLabel);

        // Add 10px spacing
        sidebar.add(Box.createRigidArea(new Dimension(200, 10)));

        // Create player timer and add to side panel
        whiteTimeLabel = new JLabel(String.valueOf(whiteTime / 1000 / 60 + ":" + secondsFormatter.format(whiteTime / 1000 % 60)));
        whiteTimeLabel.setBackground(new Color(0xF0F0F0));
        whiteTimeLabel.setForeground(new Color(0x101010));
        whiteTimeLabel.setOpaque(true);
        whiteTimeLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        whiteTimeLabel.setFont(timerFont);
        sidebar.add(whiteTimeLabel);

        // Place remaining space of sidebar here; after timers and before move log
        sidebar.add(Box.createVerticalGlue());

        // Create title for the movelog
        JLabel moveLogTitle = new JLabel("Move Log");
        moveLogTitle.setFont(new Font("/Fonts/Lato-Bold.ttf", Font.BOLD, 12));
        moveLogTitle.setForeground(Color.WHITE);
        moveLogTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        sidebar.add(moveLogTitle);

        // Create text area for displaying move log and add to bottom of sidebar
        moveLogTextArea = new JTextArea();
        moveLogTextArea.setFont(new Font("Oswald-Bold.ttf", Font.BOLD, 12));
        moveLogTextArea.setMaximumSize(new Dimension(200, 200));
        moveLogTextArea.setPreferredSize(new Dimension(200, 200));
        moveLogTextArea.setBackground(new Color(0x303034));
        moveLogTextArea.setForeground(Color.WHITE);
        moveLogTextArea.setEditable(false);
        moveLogTextArea.setLineWrap(true);
        moveLogTextArea.setWrapStyleWord(true);
        sidebar.add(moveLogTextArea);

        // Add sidebar to content pane on right edge
        contentPane.add(sidebar, BorderLayout.EAST);

        // Create starting gameboard
        resetBoard(boardSquares, activePieces);

        // Create timer which ticks every 10ms, responsible for updating player's timers
        clockTick = new Timer(10, new TimerTick());

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

    /**
	 * GET BOARD
	 * This method returns the boardSquares array which contains every column of board squares
     * This allows each square to be accessed
	 */
    public static BoardSquare[][] getBoard() {
        return boardSquares;
    }

    /**
	 * GET PIECE LIST
	 * This method returns the piece list, containing all active pieces on the game board
	 */
    public static ArrayList<ChessPiece> getPieceList() {
        return activePieces;
    }

    /**
	 * GET TURN NUMBER
	 * This method returns the current turn number in integer form
	 */
    public static int getTurnNumber() {
        return moveCount;
    }

    /**
	 * GET TURN
	 * This method returns which color is currently permitted to move
	 */
    public static boolean getTurn() {
        return colorToMove;
    }

    /**
	 * TOGGLE TURN
	 * When called, this method swaps which colour is permitted to move, and toggles the turn number
     * if it becomes white's turn
	 */
    public static void toggleTurn() {
        colorToMove = !colorToMove;
        if (colorToMove == ChessPiece.WHITE) {
            moveCount++;
        }
    }

    /**
	 * IN CHECK
	 * This method returns true if the given king is in check
     * This is checked by checking each square that could potentially hold a piece attacking it,
     * then checking if the piece is of the correct type and colour
	 */
    public static boolean inCheck(ChessPiece king) {
        boolean kingColor = king.getColor();
        int kingxPos = king.getBoardSquare().getxPos();
        int kingyPos = king.getBoardSquare().getyPos();
        // For each square in a line under the king
        for (int i = kingxPos + 1; i < 8; i++) {
            if (!boardSquares[i][kingyPos].hasPiece()) {
                continue;
            }
            // If the square has a piece and it is a rook or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
            ChessPiece piece = boardSquares[i][kingyPos].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        // For each square in a line above the king
        for (int i = kingxPos - 1; i >= 0; i--) {
            if (!boardSquares[i][kingyPos].hasPiece()) {
                continue;
            }
            // If the square has a piece and it is a rook or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
            ChessPiece piece = boardSquares[i][kingyPos].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        // For each square to the right of the king
        for (int i = kingyPos + 1; i < 8; i++) {
            if (!boardSquares[kingxPos][i].hasPiece()) {
                continue;
            }
            // If the square has a piece and it is a rook or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
            ChessPiece piece = boardSquares[kingxPos][i].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        // For each square to the left of the king
        for (int i = kingyPos - 1; i >= 0; i--) {
            if (!boardSquares[kingxPos][i].hasPiece()) {
                continue;
            }
            // If the square has a piece and it is a rook or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
            ChessPiece piece = boardSquares[kingxPos][i].getPiece();
            if (piece.getColor() != kingColor) {
                if (piece.getIdentifier() == ChessPiece.ROOK || piece.getIdentifier() == ChessPiece.QUEEN) {
                    return true;
                }
            }
            break;
        }
        // For each square diagonally down-right from the king
        for (int i = 1; i < 8; i++) {
            if (kingxPos + i == 8 || kingyPos + i == 8) {
                break;
            }
            // If the square has a piece and it is a bishop or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
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
        // For each square diagonally down-left from the king
        for (int i = 1; i < 8; i++) {
            if (kingxPos + i == 8 || kingyPos - i == -1) {
                break;
            }
            // If the square has a piece and it is a bishop or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
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
        // For each square diagonally up-right from the king
        for (int i = 1; i < 8; i++) {
            if (kingxPos - i == -1 || kingyPos + i == 8) {
                break;
            }
            // If the square has a piece and it is a bishop or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
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
        // For each square diagonally up-left from the king
        for (int i = 1; i < 8; i++) {
            if (kingxPos - i == -1 || kingyPos - i == -1) {
                break;
            }
            // If the square has a piece and it is a bishop or queen, return true, otherwise break loop; as the
            // squares behind do not need to be checked
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
        // Checks  each square in a 2x1 L shaped radius from the king (a knights range)
        for (int i = kingyPos - 2; i <= kingyPos + 2; i++) {
            // If in line with the king, skip as no targetting squares lie along this line
            if (i < 0 || i > 7 || i == kingyPos) {
                continue;
            }
            // When 2 squares to the left or right of the king
            if (i == kingyPos + 2 || i == kingyPos - 2) {
                // Check squares 1 above / below the king
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
            // When 1 square to the left or right of the king
            else {
                // Check squares 2 above / below the king
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
            // Check the squares diagonally up-left and up-right from the king for a pawn attacking
            if (kingxPos - 1 >= 0 && kingyPos - 1 >= 0) {
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
            // Check the squares diagonally down-left and down-right from the king for a pawn attacking
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
        // If making it to this point, no piece is attacking the king, and it is not in check
        return false;
    }
    
    /**
	 * RESET BOARD
	 * This method resets the board to a fresh game state,
     * resetting score, timers, and pieces
	 */
    public static void resetBoard(BoardSquare[][] board, ArrayList<ChessPiece> pieceList) {
        // For each square in the board, remove it's piece
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j].removePiece();
            }
        }
        // For both black and white
        for (int i = 0; i < 2; i++) {
            // Defaults options to black values for first pass
            boolean color = ChessPiece.BLACK;
            int row1 = 0;
            int row2 = 1;

            // On second pass, changes variables to corresponding values for white
            if (i == 1) {
                color = ChessPiece.WHITE;
                row1 = 7;
                row2 = 6;
            }

            // Adds each piece along the home row (1 / 8) for the current colour
            pieceList.add(board[row1][0].setPiece(new ChessPiece(ChessPiece.ROOK, color, board[row1][0], row1, 0)));
            pieceList.add(board[row1][1].setPiece(new ChessPiece(ChessPiece.KNIGHT, color, board[row1][1], row1, 1)));
            pieceList.add(board[row1][2].setPiece(new ChessPiece(ChessPiece.BISHOP, color, board[row1][2], row1, 2)));
            pieceList.add(board[row1][3].setPiece(new ChessPiece(ChessPiece.QUEEN, color, board[row1][3], row1, 3)));
            pieceList.add(board[row1][4].setPiece(new ChessPiece(ChessPiece.KING, color, board[row1][4], row1, 4)));
            pieceList.add(board[row1][5].setPiece(new ChessPiece(ChessPiece.BISHOP, color, board[row1][5], row1, 5)));
            pieceList.add(board[row1][6].setPiece(new ChessPiece(ChessPiece.KNIGHT, color, board[row1][6], row1, 6)));
            pieceList.add(board[row1][7].setPiece(new ChessPiece(ChessPiece.ROOK, color, board[row1][7], row1, 7)));

            // For each square along the second row, add a pawn
            for (int j = 0; j < 8; j++) {
                pieceList.add(board[row2][j].setPiece(new ChessPiece(ChessPiece.PAWN, color, board[row2][j], row2, j)));
            }
        }

        // Set king variables for future accessability
        whiteKing = board[7][4].getPiece();
        blackKing = board[0][4].getPiece();

        // Reset move log
        moveLog = "";
    }

    /**
	 * UPDATE HIGHLIGHT
	 * This method removes highlighting from all pieces, and highlights the requested piece
	 */
    public static void updateHighLight(ChessPiece newHighlighted) {
        for (ChessPiece piece : getPieceList()) {
            if (piece != newHighlighted) {
                piece.getBoardSquare().removeHighlight();
            }
        }
        selectedPiece = newHighlighted;
        newHighlighted.getBoardSquare().setHighlighed();
    }

    /**
	 * GET SELECTED PIECE
	 * This method returns the currently selected piece
	 */
    public static ChessPiece getSelectedPiece() {
        return selectedPiece;
    }

    /**
	 * RESET TARGETTED
	 * This method removes highlighting from all pieces, and sets the selected piece to null
	 */
    public static void resetTargetted() {
        // For each square, remove it's highlight
        for (BoardSquare[] row : getBoard()) {
            for (BoardSquare square : row) {
                square.removeHighlight();
            }
        }
        // Reset selected piece
        selectedPiece = null;
    }

    /**
	 * GET BLACK KING
     * This method returns the black king
	 */
    public static ChessPiece getBlackKing() {
        return blackKing;
    }

    /**
	 * GET WHITE KING
     * This method returns the white king
	 */
    public static ChessPiece getWhiteKing() {
        return whiteKing;
    }

    /**
	 * START TIMER
     * Calling this method starts a swing timer which updates each players (visual) timer
	 */
    public static void startTimer() {
        clockTick.start();
    }

    /**
	 * SET LAST MOVED
     * Calling this method sets the last moved piece to the one passed
	 */
    public static void setLastMoved(ChessPiece piece) {
        lastMovedPiece = piece;
    }

    /**
	 * GET LAST MOVED
     * This method returns the last moved piece
	 */
    public static ChessPiece getLastMoved() {
        return lastMovedPiece;
    }

    /**
	 * IS WAITING FOR PROMOTION
     * This method returns true if the user is prompted for promotion
	 */
    public static boolean isWaitingForPromotion() {
        return waitingForPromotion;
    }

    /**
	 * SET WAITING FOR PROMOTION
     * Calling this method sets the wait for promotion to true
	 */
    public static void setWaitingForPromotion() {
        waitingForPromotion = true;
    }
    
    /**
	 * REMOVE WAITING FOR PROMOTION
     * Calling this method sets the wait for promotion to false
	 */
    public static void removeWaitingForPromotion() {
        waitingForPromotion = false;
    }

    /**
	 * REVALIDATE
     * Calling this method revalidates the content pane, ideally making the gui accurate
	 */
    public static void revalidate() {
        contentPane.revalidate();
    }

    /**
	 * END GAME
     * This method initiates the end game sequence, informing the user that checkmate
     * was delivered, as well as who did it. The winning side is the side passed in.
     * This method also stops the game timers
	 */
    public static void endGame(boolean color) {
        if (color == ChessPiece.WHITE) {
            System.out.println("White Wins!");
        }
        else {
            System.out.println("Black Wins!");
        }
        for (BoardSquare[] column : Main.getBoard()) {
            for (BoardSquare square : column) {
                square.getPanel().removeMouseListener(square);
            }
        }
        clockTick.stop();
        // Close game
        System.exit(0);
    }

    /**
	 * END GAME
     * This method initiates the end game sequence, informing the user that a
     * draw was reached. The reason for the draw's occurance is passed in.
     * This method also stops the game timers
	 */
    public static void endGame(String result) {
        System.out.println("Draw - " + result + "!");
        System.out.println(moveLog);
        // Close game
        System.exit(0);
    }

    /**
	 * IDENTIFIER TO LETTER
     * This method returns a piece's PGN-letter designation from it's identifier. 
     * In the case of a pawn, a blank character is returned.
	 */
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

    /**
	 * COLUMN NUMBER TO LETTER
     * This method returns the respective column letter based
     * on a passed in position.
	 */
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

    /**
	 * UPDATE MOVE LOG
     * This method adds a passed in move to the game move log,
     * then updates the GUI to reflect as such.
	 */
    public static void updateMoveLog(String currentMove) {
        moveLog += currentMove;
        moveLogTextArea.setText(moveLog);
    }

    /**
     * EVENT LISTENERS
     * Subclasses that handle events (button clicks, mouse clicks and moves,
     * key presses, timer expirations)
     */

    /**
	 * TIMER TICK
     * This timer handles the game clocks for both players, ticking every 10ms and updating both
     * the back-end time, as well as updating the GUI. Calls game-end if a player loses on time
     * (time <= 0). Lowers time by 15ms due to overhead while calling, giving a closer to 1:1 representation
	 */
    private static class TimerTick extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
            if (colorToMove == ChessPiece.WHITE) {
                whiteTime -= 15;
                if (whiteTime <= 0) {
                    endGame(ChessPiece.BLACK);
                }
            }
            else {
                blackTime -= 15;
                if (blackTime <= 0) {
                    endGame(ChessPiece.WHITE);
                }
            }
            // Update labels
            whiteTimeLabel.setText(String.valueOf(whiteTime / 1000 / 60 + ":" + secondsFormatter.format(whiteTime / 1000 % 60)));
            blackTimeLabel.setText(String.valueOf(blackTime / 1000 / 60 + ":" + secondsFormatter.format(blackTime / 1000 % 60)));
        }
    }
}