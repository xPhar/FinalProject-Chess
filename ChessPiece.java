import javax.swing.JLabel;
import java.awt.Dimension;

import javax.swing.ImageIcon;

public class ChessPiece{
    private JLabel pieceLabel;
    private int value;
    private int identifier;
    private boolean hasMoved = false;
    private boolean color;
    private int xPos;
    private int yPos;
    private BoardSquare lastSquare;
    private BoardSquare currentSquare;
    static final boolean BLACK = false;
    static final boolean WHITE = true;
    static final int PAWN = 1;
    static final int KNIGHT = 2;
    static final int BISHOP = 3;
    static final int ROOK = 4;
    static final int QUEEN = 5;
    static final int KING = 6;

    public ChessPiece(int identifier, boolean color, BoardSquare startingSquare, int xPos, int yPos) {
        this.identifier = identifier;
        this.color = color;
        this.currentSquare = startingSquare;
        this.xPos = xPos;
        this.yPos = yPos;

        // Sets necessary values for the piece based on it's type
        if (identifier == PAWN) {
            this.value = 1;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhitePawn.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackPawn.png"));
            }
        }
        else if (identifier == KNIGHT) {
            this.value = 3;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteKnight.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackKnight.png"));
            }
        }
        else if (identifier == BISHOP) {
            this.value = 3;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteBishop.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackBishop.png"));
            }
        }
        else if (identifier == ROOK) {
            this.value = 5;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteRook.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackRook.png"));
            }
        }
        else if (identifier == QUEEN) {
            this.value = 9;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteQueen.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackQueen.png"));
            }
        }
        else if (identifier == KING) {
            this.value = 99;
            if (color == WHITE) {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/WhiteKing.png"));
            }
            else {
                this.pieceLabel = new JLabel(new ImageIcon("Pieces/BlackKing.png"));
            }
        }

        // Sets the piece to be 100px by 100px
        this.pieceLabel.setPreferredSize(new Dimension(100, 100));
    }

    /**
	 * REMOVE PIECE
     * This method removes this piece from the master piece
     * list in Main, removing it from targetting calculations.
	 */
    public void removePiece() {
        Main.getPieceList().remove(this);
    }

    /**
	 * GET VALUE
     * This method returns the value of this piece
	 */
    public int getValue() {
        return value;
    }

    /**
	 * GET IDENTIFIER
     * This method returns this piece's type-identifier
	 */
    public int getIdentifier() {
        return identifier;
    }

    /**
	 * GET LABEL
     * This method returns this piece's JLabel
	 */
    public JLabel getLabel() {
        return pieceLabel;
    }

    /**
	 * GET COLOR
     * This method returns the boolean color of this piece
	 */
    public boolean getColor() {
        return color;
    }
    
    /**
	 * GET BOARD SQUARE
     * This method returns this piece's current board square
	 */
    public BoardSquare getBoardSquare() {
        return currentSquare;
    }

    /**
	 * SET BOARD SQUARE
     * Calling this method updates this piece's square to the one passed
	 */
    public void setBoardSquare(BoardSquare square) {
        setLastSquare();
        this.currentSquare = square;
    }

    /**
	 * SET ROW
     * This method updates this piece's yPos to reflect the passed row
	 */
    public void setRow(int row) {
        this.yPos = row;
    }

    /**
	 * SET COLUMN
     * This method updates this piece's xPos to reflect the passed column
	 */
    public void setColumn(int column) {
        this.xPos = column;
    }

    /**
	 * PIECE MOVEED
     * Calling this method updates this piece to show that it has moved
	 */
    public void pieceMoved() {
        this.hasMoved = true;
    }

    /**
	 * PIECE HAS MOVED
     * This method returns true if this piece has moved from it's home square
	 */
    public boolean pieceHasMoved() {
        return hasMoved;
    }

    /**
     * SET LAST SQUARE
     * This method sets the last sqiare which this piece was located (defualts to starting square)
     */
    public void setLastSquare() {
        lastSquare = this.getBoardSquare();
    }

    /**
     * GET LAST SQUARE
     * This method returns the last square this piece was located at
     */
    public BoardSquare getLastSquare() {
        return this.lastSquare;
    }
}
